package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

public class CallAPIDocument {
	private static final Properties propertiesCEAPI = new Properties();

    static {
        try (InputStream input = CallAPIDocument.class
                .getClassLoader()
                .getResourceAsStream("createAndDeleteFolder/CEAPI.properties")) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file TCM.properties trong classpath!");
            }
            propertiesCEAPI.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi load TCM.properties", e);
        }
    }
    
    public static String getProperty(String name) {
        return propertiesCEAPI.getProperty(name);
    }
	
	public static String callApi(String apiUrl,String token, String jsonBody, int timeOut, Map<String, String> params, String method) {
		String response = new String();
		try {
			if (params != null && !params.isEmpty()) {
	            StringBuilder urlWithParams = new StringBuilder(apiUrl);
	            urlWithParams.append("?");
	            for (Map.Entry<String, String> entry : params.entrySet()) {
	                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
	                    urlWithParams.append(entry.getKey())
	                                 .append("=")
	                                 .append(entry.getValue())
	                                 .append("&");
	                }
	            }
	            apiUrl = urlWithParams.substring(0, urlWithParams.length() - 1);
	        }
			// Debug thông tin URL và token
	        System.out.println("Calling API: " + apiUrl);
	        System.out.println("Method: " + method);
	        System.out.println("Token: " + token);
	        System.out.println("Request Body: " + jsonBody);
			
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			 // Thêm timeout
	        conn.setConnectTimeout(timeOut);
	        conn.setReadTimeout(timeOut);
	        
			String msgId = TokenManager.generateMsgId("TCM"+"|");
			conn.setRequestMethod(method);
			conn.setDoOutput(true);
			if(apiUrl.endsWith("/token")) {
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			}else {
				conn.setRequestProperty("Content-Type", "application/json");
			}
			
			conn.setRequestProperty("accept", "*/*");
			
			
			if(token != null) {
				conn.setRequestProperty("Authorization", "Bearer " + token);
			}
			
			// Gửi dữ liệu nếu có body
			// Gửi dữ liệu nếu có body
			if (jsonBody != null) {
			    try (OutputStream os = conn.getOutputStream()) {
			        byte[] input;
			        if (apiUrl.endsWith("/token")) {
			            // jsonBody ở đây chính là formBody kiểu: grant_type=xxx&client_id=xxx...
			            input = jsonBody.getBytes(StandardCharsets.UTF_8);
			        } else {
			            // jsonBody ở đây là chuỗi JSON
			            input = jsonBody.getBytes(StandardCharsets.UTF_8);
			        }
			        os.write(input, 0, input.length);
			    }
			}



			int responseCode = conn.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_ACCEPTED && responseCode != HttpURLConnection.HTTP_OK) {
				throw new Exception("Failed! HTTP status: " + responseCode + " " + conn.getResponseMessage() + "\n"
						+ getResponse(conn.getErrorStream()));
			}
			response = getResponse(conn.getInputStream());
			System.out.println("Response Code: " + responseCode);
			return response;
		} catch (Exception e) {
			System.err.println("Error calling API: " + apiUrl);
			e.printStackTrace();
		}
		return response.toString();
	}
	
	private static String getResponse(InputStream inputResponse) {
		if (inputResponse != null) {
			InputStreamReader in = null;
			BufferedReader br = null;
			try {
				in = new InputStreamReader(inputResponse, "UTF-8");
				br = new BufferedReader(in);
				StringBuffer str = new StringBuffer();
				String output;
				while ((output = br.readLine()) != null) {
					str.append(output);
				}
				return str.toString();
			} catch (Exception e) {
				System.out.println("getResponse error. " + e);
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (Exception e2) {
				}

				try {
					if (in != null)
						in.close();
				} catch (Exception e2) {
				}
			}
		}
		return "";
	}
}
