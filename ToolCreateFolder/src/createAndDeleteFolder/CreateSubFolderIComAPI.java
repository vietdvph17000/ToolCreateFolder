package createAndDeleteFolder;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import api.CallAPIDocument;
import api.TokenManager;
import dao.FolderTCMDao;
import dto.FolderDTO;
import dto.SubFolderDTO;

public class CreateSubFolderIComAPI implements Runnable {

	private FolderDTO folder;
	private Connection con;

	@SuppressWarnings("rawtypes")
	public CreateSubFolderIComAPI(Collection folderList, Connection conn) {
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
			Integer parentIComId = folder.getIdICom();
			if (parentIComId == null) {
				throw new RuntimeException("Folder gốc chưa có idICom: " + folderId);
			}

			LocalDate now = LocalDate.now(); // ngày hiện tại
			LocalDate nextMonthDate = now.plusMonths(1); // cộng thêm 1 tháng

			int year = nextMonthDate.getYear();
			String folderCodeYear = folderId + "_" + year;
			int baseLevel = folder.getFolderLevel();

			// --- kiểm tra/tạo folder năm ---
			SubFolderDTO folderYear = dao.getSubFolder(con, folderCodeYear);
			if (folderYear == null) {
				Integer idFolderICom = createFolderAPI(parentIComId, String.valueOf(year));
				if (idFolderICom == null) {
					throw new RuntimeException("Lỗi call API tạo folder năm cho folder: " + folderId);
				}

				folderYear = new SubFolderDTO();
				folderYear.setFolderId(folderCodeYear);
				folderYear.setpFolderId(folderId);
				folderYear.setFolderName(String.valueOf(year));
				folderYear.setFolderLevel(baseLevel + 1);
				folderYear.setIdICom(idFolderICom);

				dao.insertSubFolder(con, folderYear);
			}
			int month = nextMonthDate.getMonthValue();
			String monthName = String.format("%02d", month);
			String folderCodeMonth = folderCodeYear + "_" + monthName;

			SubFolderDTO folderMonth = dao.getSubFolder(con, folderCodeMonth);
			if (folderMonth == null) {
				if (folderYear.getIdICom() == null) {
					throw new RuntimeException("Folder năm chưa có idICom: " + folderCodeYear);
				}

				Integer idFolderICom = createFolderAPI(folderYear.getIdICom(), monthName);
				if (idFolderICom == null) {
					throw new RuntimeException("Lỗi call API tạo folder tháng cho folder: " + folderCodeYear);
				}

				folderMonth = new SubFolderDTO();
				folderMonth.setFolderId(folderCodeMonth);
				folderMonth.setpFolderId(folderYear.getFolderId());
				folderMonth.setFolderName(monthName);
				folderMonth.setFolderLevel(folderYear.getFolderLevel() + 1);
				folderMonth.setIdICom(idFolderICom);

				dao.insertSubFolder(con, folderMonth);
			}

			// --- kiểm tra/tạo 12 folder tháng ---
			/*
			 * for (int month = 1; month <= 12; month++) { String monthName =
			 * String.format("%02d", month); String folderCodeMonth = folderCodeYear + "_" +
			 * monthName;
			 * 
			 * SubFolderDTO folderMonth = dao.getSubFolder(con, folderCodeMonth); if
			 * (folderMonth == null) { if (folderYear.getIdICom() == null) { throw new
			 * RuntimeException("Folder năm chưa có idICom: " + folderCodeYear); }
			 * 
			 * Integer idFolderICom = createFolderAPI(folderYear.getIdICom(), monthName); if
			 * (idFolderICom == null) { throw new
			 * RuntimeException("Lỗi call API tạo folder tháng cho folder: " +
			 * folderCodeYear); }
			 * 
			 * folderMonth = new SubFolderDTO(); folderMonth.setFolderId(folderCodeMonth);
			 * folderMonth.setpFolderId(folderYear.getFolderId());
			 * folderMonth.setFolderName(monthName);
			 * folderMonth.setFolderLevel(folderYear.getFolderLevel() + 1);
			 * folderMonth.setIdICom(idFolderICom);
			 * 
			 * dao.insertSubFolder(con, folderMonth); }
			 * 
			 * }
			 */

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Integer createFolderAPI(Integer parent_id, String folderName) {
		Integer idFolderICom = null;
		try {
			Map<String, Object> requestBody = new HashMap<>();
			CallAPIDocument env = new CallAPIDocument();
			String uriFolder = env.getProperty("api.client.uri") + "/api/vtlt/v1/folder";
			requestBody.put("name", folderName);
			requestBody.put("parent_id", parent_id);
			requestBody.put("storage_partition_id", null);
			requestBody.put("replica_count", 0);
			String jsonInput = new ObjectMapper().writeValueAsString(requestBody);

			String response = CallAPIDocument.callApi(uriFolder, TokenManager.getToken(), jsonInput, 30000, null,
					"POST");
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

			idFolderICom = (Integer) resultMap.get("id");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idFolderICom;
	}

}
