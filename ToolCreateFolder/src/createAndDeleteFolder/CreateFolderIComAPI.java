package createAndDeleteFolder;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import api.CallAPIDocument;
import api.TokenManager;
import dao.FolderTCMDao;
import dto.FolderDTO;

public class CreateFolderIComAPI implements Runnable{

	private FolderDTO folder;
	private Connection con;

	@SuppressWarnings("rawtypes")
	public CreateFolderIComAPI(Collection folderList, Connection conn) {
		folder = getObj(folderList);
		con = conn;
	}

	@SuppressWarnings("rawtypes")
	private synchronized FolderDTO getObj(Collection folderList) {
		FolderDTO rs = null;
		try {
			Iterator<?> itr = folderList.iterator();
			if (itr.hasNext()) {
			    rs = (FolderDTO) itr.next();
			    itr.remove();   
			}

		} catch (Exception ex) {
			rs = null;
		}
		return rs;
	}
	
	@Override
	public void run() {
		try {
			createFolder();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static FolderTCMDao dao = new FolderTCMDao();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createFolder() throws Exception {
		try {
			String folderId = folder.getFolderId();
			String pfolderId = folder.getpFolderId();
			Map<String, Object> requestBody = new HashMap<>();
			CallAPIDocument env = new CallAPIDocument();
			String uriFolder = env.getProperty("api.client.uri") + "/api/vtlt/v1/folder";
			requestBody.put("name", folder.getFolderName());
			// trường hợp folder root ban đầu sẽ là null
			//nếu không phải folder gốc thì là idFolderICom của folder cha
			if ("ROOT".equals(folderId)) {
	            requestBody.put("parent_id", null);
	        } else {
	            Integer parentIComId = dao.getIdFolderIComFromDB(con, pfolderId);
	            requestBody.put("parent_id", parentIComId);
	        }
			requestBody.put("storage_partition_id", null);
			requestBody.put("replica_count", 0);
			String jsonInput = new ObjectMapper().writeValueAsString(requestBody);
			
			String response = CallAPIDocument.callApi(uriFolder,TokenManager.getToken(), jsonInput, 30000, null, "POST");
			// parse JSON về map
			Map<String, Object> responseMap = new ObjectMapper().readValue(response, Map.class);

			// lấy statusCode
			Integer statusCode = (Integer) responseMap.get("statusCode");
			if (statusCode == null || statusCode != 200) {
			    throw new RuntimeException("API tạo folder thất bại: " + response);
			}

			// lấy object "result"
			Map<String, Object> resultMap = (Map<String, Object>) responseMap.get("result");
			if (resultMap == null) {
			    throw new RuntimeException("Không tìm thấy 'result' trong response: " + response);
			}

			Integer idFolderICom = (Integer) resultMap.get("id");
			
			//update idFolderICom vao bang tcm_folder
			dao.updateIdICommByFolderId(con, folderId, idFolderICom);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
