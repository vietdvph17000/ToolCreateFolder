package api;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.databind.ObjectMapper;



public class TokenManager {
	private static String token;
	private static long tokenExpiryTime;
	
	public static String generateMsgId(String appId) {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String sequenceId = String.format("%07d", sequence.getAndIncrement());
		String msgId = appId + appId + timestamp + sequenceId;
		String encodedString = Base64.getEncoder().encodeToString(msgId.getBytes());
		return encodedString;
	}

	public static synchronized String getToken() {
		long now = System.currentTimeMillis();

		// Nếu token còn hạn ít nhất 1 phút thì dùng lại
		if (token != null && now < tokenExpiryTime) {
			return token;
		}

		// Hết hạn thì refresh
		refreshToken();
		return token;
	}

	public static void main(String[] args) {
		String a = getToken();
	}

	private static final AtomicLong sequence = new AtomicLong(1);


	private static void refreshToken() {
		try {
			Map<String, String> requestBody = new HashMap<>();
			CallAPIDocument env = new CallAPIDocument();
			String uriToken = env.getProperty("api.client.uri") + "/auth/connect/token";
			requestBody.put("grant_type", env.getProperty("api.client.granttype"));
			requestBody.put("client_id", env.getProperty("api.client.clientId"));
			requestBody.put("client_secret", env.getProperty("api.client.clientSecret"));
			requestBody.put("username", env.getProperty("api.client.username"));
			requestBody.put("password", env.getProperty("api.client.pass"));

//			String jsonInput = new ObjectMapper().writeValueAsString(requestBody);

			String formBody = "grant_type=" + URLEncoder.encode(env.getProperty("api.client.granttype"), "UTF-8")
			        + "&client_id=" + URLEncoder.encode(env.getProperty("api.client.clientId"), "UTF-8")
			        + "&client_secret=" + URLEncoder.encode(env.getProperty("api.client.clientSecret"), "UTF-8")
			        + "&username=" + URLEncoder.encode(env.getProperty("api.client.username"), "UTF-8")
			        + "&password=" + URLEncoder.encode(env.getProperty("api.client.pass"), "UTF-8");

			String response = CallAPIDocument.callApi(uriToken,null, formBody, 30000, null, "POST");
			Map<String, Object> responseMap = new ObjectMapper().readValue(response, Map.class);

			Integer expires_in = (Integer) responseMap.get("expires_in");
			if (expires_in == null ) {
				throw new RuntimeException("Failed to refresh token: ");
			}

			token = (String) responseMap.get("access_token");

			// expiredTime là mili-giây (string)
			long expiresInMs = ((Integer) responseMap.get("expires_in")).longValue() * 1000;
			// Trừ 1 phút để đảm bảo refresh sớm hơn hạn thật
			tokenExpiryTime = System.currentTimeMillis() + expiresInMs - 60_000L;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to refresh token: " + e.getMessage());
		}
	}
}
