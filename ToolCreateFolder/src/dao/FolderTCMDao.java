package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import dto.FolderDTO;
import dto.JobParameterDTO;
import dto.StorageDTO;
import dto.SubFolderDTO;

public class FolderTCMDao {
	
	public Boolean checkFolderExit(Connection conn, String subFolderId) throws Exception {
	    String sql = "SELECT COUNT(*) FROM tcm_sub_folder WHERE SUB_FOLDER_ID = ? AND ID_ICOM IS NULL";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, subFolderId);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                int count = rs.getInt(1);
	                return count > 0;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e; // ném ra ngoài cho dễ debug
	    }
	    return false;
	}
	public SubFolderDTO getSubFolder(Connection conn, String subFolderId) throws Exception {
	    String sql = "SELECT SUB_FOLDER_ID,P_SUB_FOLDER_ID,ID_ICOM,FOLDER_LEVEL,FOLDER_NAME FROM tcm_sub_folder WHERE SUB_FOLDER_ID = ?";
	    SubFolderDTO folder = null;
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setString(1, subFolderId);
	        try (ResultSet rs = ps.executeQuery()) {
	        	while (rs.next()) {
	        		folder = new SubFolderDTO();
		            folder.setFolderId(rs.getString("SUB_FOLDER_ID"));
		            folder.setpFolderId(rs.getString("P_SUB_FOLDER_ID"));
		            folder.setFolderLevel(rs.getInt("FOLDER_LEVEL"));
		            folder.setIdICom(rs.getInt("ID_ICOM"));
		            folder.setFolderName(rs.getString("FOLDER_NAME"));
		        }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	    return folder;
	}
	
	public void insertSubFolder(Connection conn, SubFolderDTO folder) throws Exception{
	    String insertQuery = "INSERT INTO TCM_SUB_FOLDER (SUB_FOLDER_ID, P_SUB_FOLDER_ID, ID_ICOM, FOLDER_LEVEL, FOLDER_NAME, CREATE_TIME) " +
	                         "VALUES (?, ?, ?, ?, ?, sysdate)";



	    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
	        insertStmt.setString(1, folder.getFolderId());
	        insertStmt.setString(2, folder.getpFolderId());
	        insertStmt.setInt(3, folder.getIdICom());
	        insertStmt.setInt(4, folder.getFolderLevel());
	        insertStmt.setString(5, folder.getFolderName());
	        insertStmt.executeUpdate();

	        System.out.println("Insert folder thành công: " + folder.getFolderId());
	    } catch (Exception e) {
	        System.err.println("Lỗi khi insert folder: " + folder.getFolderId());
	        e.printStackTrace();
	    }
	
	}

	
	
	public List<FolderDTO> getListFolderLast(Connection conn) throws Exception {
	    List<FolderDTO> folderList = new ArrayList<>();
	    String sql = "SELECT FOLDER_ID,\r\n"
	    		+ "       P_FOLDER_ID,\r\n"
	    		+ "        FOLDER_LEVEL,\r\n"
	    		+ "       ID_ICOMM\r\n"
	    		+ "FROM TCM_FOLDER\r\n"
	    		+ "WHERE CONNECT_BY_ISLEAF = 1  and ID_ICOMM IS NOT NULL\r\n"
	    		+ "START WITH FOLDER_ID = 'ROOT'\r\n"
	    		+ "CONNECT BY PRIOR FOLDER_ID = P_FOLDER_ID\r\n"
	    		+ "ORDER BY FOLDER_LEVEL ASC "
	    		+ "FETCH FIRST 2 ROWS ONLY";

	    try (PreparedStatement pstm = conn.prepareStatement(sql);
	         ResultSet rs = pstm.executeQuery()) {

	        while (rs.next()) {
	        	FolderDTO folder = new FolderDTO();
	            folder.setFolderId(rs.getString("FOLDER_ID"));
	            folder.setpFolderId(rs.getString("P_FOLDER_ID"));
	            folder.setFolderLevel(rs.getInt("FOLDER_LEVEL"));
	            folder.setIdICom(rs.getInt("ID_ICOMM"));
	            folderList.add(folder);
	        }

	    } catch (SQLException ex) {
	        Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, "Lỗi SQL khi getListFolder", ex);
	        throw ex; // ném lỗi ra ngoài cho service xử lý
	    }

	    Logger.getLogger(FolderTCMDao.class.getName()).info("getList==> " + sql);
	    return folderList;
	}
	
	public List<FolderDTO> getListFolder(Connection conn) throws Exception {
	    List<FolderDTO> folderList = new ArrayList<>();
	    String sql = "SELECT FOLDER_ID, P_FOLDER_ID, FOLDER_LEVEL, FOLDER_NAME\r\n"
	    		+ "            FROM TCM_FOLDER\r\n"
	    		+ "	    		WHERE ID_ICOMM IS NULL\r\n"
	    		+ "	    		START WITH FOLDER_ID = 'ROOT'\r\n"
	    		+ "	    		CONNECT BY PRIOR FOLDER_ID = P_FOLDER_ID\r\n"
	    		+ "	    		ORDER BY FOLDER_LEVEL";

	    try (PreparedStatement pstm = conn.prepareStatement(sql);
	         ResultSet rs = pstm.executeQuery()) {

	        while (rs.next()) {
	            FolderDTO folder = new FolderDTO();
	            folder.setFolderId(rs.getString("FOLDER_ID"));
	            folder.setpFolderId(rs.getString("P_FOLDER_ID"));
	            folder.setFolderLevel(rs.getInt("FOLDER_LEVEL"));
	            folder.setFolderName(rs.getString("FOLDER_NAME"));
	            folderList.add(folder);
	        }

	    } catch (SQLException ex) {
	        Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, "Lỗi SQL khi getListFolder", ex);
	        throw ex; // ném lỗi ra ngoài cho service xử lý
	    }

	    Logger.getLogger(FolderTCMDao.class.getName()).info("getList==> " + sql);
	    return folderList;
	}
	
	public Integer getIdFolderIComFromDB(Connection conn, String folderId) throws Exception {
	    Integer result = null;
	    String sql = "SELECT ID_ICOMM FROM TCM_FOLDER WHERE FOLDER_ID = ?";

	    try (PreparedStatement pstm = conn.prepareStatement(sql)) {
	        // Gán giá trị cho tham số ?
	        pstm.setString(1, folderId);

	        try (ResultSet rs = pstm.executeQuery()) {
	            if (rs.next()) {
	                result = rs.getInt("ID_ICOMM");
	            }
	        }
	    } catch (SQLException ex) {
	        Logger.getLogger(FolderTCMDao.class.getName())
	              .log(Level.SEVERE, "Lỗi SQL khi getIdFolderIComFromDB", ex);
	        throw ex; // Ném ra ngoài cho service xử lý
	    }

	    Logger.getLogger(FolderTCMDao.class.getName())
	          .info("getIdFolderIComFromDB==> " + sql + " [folderId=" + folderId + "]");
	    return result;
	}
	
	public int updateIdICommByFolderId(Connection conn, String folderId, Integer newIdIComm) throws Exception {
	    String sql = "UPDATE TCM_FOLDER SET ID_ICOMM = ?,STATUS_JOB = 'Y' WHERE FOLDER_ID = ?";
	    int rowsAffected = 0;

	    try (PreparedStatement pstm = conn.prepareStatement(sql)) {
	        pstm.setInt(1, newIdIComm);
	        pstm.setString(2, folderId);

	        rowsAffected = pstm.executeUpdate();

	    } catch (SQLException ex) {
	        Logger.getLogger(FolderTCMDao.class.getName())
	              .log(Level.SEVERE, "Lỗi SQL khi updateIdICommByFolderId", ex);
	        throw ex; // ném lỗi ra ngoài cho service xử lý
	    }

	    Logger.getLogger(FolderTCMDao.class.getName())
	          .info("updateIdICommByFolderId==> " + sql + " [ID_ICOMM=" + newIdIComm + ", folderId=" + folderId + "]");
	    return rowsAffected;
	}



	
	
	public List<FolderDTO> getListFolderPathTCM(Connection conn) throws Exception {
		List<FolderDTO> folderList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT *\r\n"
				+ "FROM (\r\n"
				+ "    SELECT FOLDER_ID,\r\n"
				+ "           P_FOLDER_ID,\r\n"
				+ "           FOLDER_LEVEL,FOLDER_NAME,\r\n"
				+ "           ID_ICOMM\r\n"
				+ "    FROM TCM_FOLDER\r\n"
				+ "    START WITH FOLDER_ID = 'ROOT'\r\n"
				+ "    CONNECT BY PRIOR FOLDER_ID = P_FOLDER_ID\r\n"
				+ "    ORDER BY FOLDER_LEVEL\r\n"
				+ ")\r\n"
				+ "WHERE ROWNUM < 3";
		try {
			pstm = conn.prepareCall(sql);
//            pstm.setString(3, processID);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FolderDTO folder = new FolderDTO();
				folder.setFolderId(rs.getString("FOLDER_ID"));
				folder.setFolderName(rs.getString("FOLDER_NAME"));
				folder.setpFolderId(rs.getString("P_FOLDER_ID"));
				folder.setFolderLevel(rs.getInt("FOLDER_LEVEL"));
				folderList.add(folder);
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return folderList;
		}
	}
	
	public List<FolderDTO> getPathCQTFromCategory(Connection conn) throws Exception {
		List<FolderDTO> folderList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "WITH cqt3_unique AS ( "
		        + "    SELECT * "
		        + "    FROM ( "
		        + "        SELECT cc3.*, "
		        + "               ROW_NUMBER() OVER (PARTITION BY cc3.MA_BAO ORDER BY cc3.TEN_CQT) AS rn "
		        + "        FROM TCM_DM_CQT cc3 "
		        + "        WHERE cc3.ORG_LEVEL = 3 "
		        + "          AND cc3.ORG_ACTION = 'N' "
		        + "    ) "
		        + "    WHERE rn = 1 "
		        + ") "
		        + "SELECT DISTINCT "
		        + "    cc.CDCONTENT || '/' || cc2.TEN_CQT || '/Cơ quan thuế' || '/' || cu.TEN_CQT || '/' || dg.FILE_TYPE_NAME || '/' || ft.SHORT_NAME AS folder_path, "
		        + "    cc.CDCONTENT || '/' || cc2.TEN_CQT || '/Cơ quan thuế' || '/' || cu.TEN_CQT || '/' || dg.FILE_TYPE_NAME || '/' || ft.FILE_NAME AS folder_path_name, "
		        + "    cc.CDVAL || '_' || cc2.MA_BAO || '_CQT_' || cu.MA_BAO || '_' || dg.FILE_TYPE_CODE || '_' || ft.FILE_CODE AS folder_code "
		        + "FROM TCM_CATEGORY_CODE cc "
		        + "JOIN TCM_DM_CQT cc2 ON cc2.ORG_LEVEL = 2 AND cc2.ORG_ACTION = 'N' "
		        + "JOIN cqt3_unique cu ON cu.MA_BAO_CHA = cc2.MA_BAO "
		        + "JOIN TCM_CATEGORY_DOC_GROUP dg ON dg.TRANSACTION_TYPE LIKE '%' || cc.CDVAL || '%' AND dg.STATUS = 'ACTV' "
		        + "JOIN TCM_CATEGORY_FILE_TYPE ft ON ft.FILE_TYPE_CODE = dg.FILE_TYPE_CODE "
		        + "WHERE cc.IDTYPE = 'FOLDER_LEVEL02' "
		        + "AND cc.CDVAL != 'HSK' "
//		        + "  AND cc.CDVAL = 'QLVB' "
//		        + "AND cc2.MA_BAO IN ('85700') "
//		        + "AND cc3.MA_BAO = '10100'"
		        ;


		try {
			pstm = conn.prepareCall(sql);
//            pstm.setString(3, processID);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FolderDTO folder = new FolderDTO();
				folder.setFolderId(rs.getString("folder_code"));
				folder.setFolderName(rs.getString("FOLDER_PATH_NAME"));
				folder.setPath(rs.getString("FOLDER_PATH"));
				folder.setFolderLevel(6);
				folderList.add(folder);
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return folderList;
		}
	}
	
	public List<FolderDTO> getPathNTLFromCategory(Connection conn) throws Exception {
		List<FolderDTO> folderList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT DISTINCT "
		        + "cc.CDCONTENT || '/' || cc2.TEN_CQT || '/Nhóm tài liệu' || '/' || dg.FILE_TYPE_NAME || '/' || ft.SHORT_NAME AS folder_path, "
		        + "cc.CDCONTENT || '/' || cc2.TEN_CQT || '/Nhóm tài liệu' || '/' || dg.FILE_TYPE_NAME || '/' || ft.FILE_NAME AS folder_path_name, "
		        + "cc.CDVAL || '_' || cc2.MA_BAO || '_NTL' || '_' || dg.FILE_TYPE_CODE || '_' || ft.FILE_CODE AS folder_code "
		        + "FROM TCM_CATEGORY_CODE cc "
		        + "JOIN TCM_DM_CQT cc2 ON cc2.ORG_LEVEL = 2 AND cc2.ORG_ACTION = 'N' "		    
		        + "JOIN TCM_CATEGORY_DOC_GROUP dg ON dg.TRANSACTION_TYPE LIKE '%' || cc.CDVAL || '%' AND dg.STATUS = 'ACTV' "
		        + "JOIN TCM_CATEGORY_FILE_TYPE ft ON ft.FILE_TYPE_CODE = dg.FILE_TYPE_CODE "
		        + "WHERE cc.IDTYPE = 'FOLDER_LEVEL02' "
		        + "AND cc.CDVAL != 'HSK' "
//		        + "AND cc.CDVAL = 'QLVB' "
//		        + "AND cc2.MA_BAO IN ('85700') "
//		        + "AND cc3.MA_BAO = '10100'"
		        ;
		try {
			pstm = conn.prepareCall(sql);
//            pstm.setString(3, processID);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FolderDTO folder = new FolderDTO();
				folder.setFolderId(rs.getString("folder_code"));
				folder.setFolderName(rs.getString("FOLDER_PATH_NAME"));
				folder.setPath(rs.getString("FOLDER_PATH"));
				folder.setFolderLevel(5);
				folderList.add(folder);
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return folderList;
		}
	}
	
	public List<FolderDTO> getPathPBanFromCategory(Connection conn) throws Exception {
		List<FolderDTO> folderList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT DISTINCT "
		        + "cc.CDCONTENT || '/' || cc2.TEN_CQT || '/' || dg.FILE_TYPE_NAME || '/' || ft.SHORT_NAME AS folder_path, "
		        + "cc.CDCONTENT || '/' || cc2.TEN_CQT || '/' || dg.FILE_TYPE_NAME || '/' || ft.FILE_NAME AS folder_path_name, "
		        + "cc.CDVAL || '_' || cc2.MA_CQT || '_' || dg.FILE_TYPE_CODE || '_' || ft.FILE_CODE AS folder_code "
		        + "FROM TCM_CATEGORY_CODE cc "
		        + "JOIN TCM_DM_CQT cc2 ON cc2.ORG_LEVEL = 2 AND cc2.ORG_ACTION = 'Y' "		    
		        + "JOIN TCM_CATEGORY_DOC_GROUP dg ON dg.TRANSACTION_TYPE LIKE '%' || cc.CDVAL || '%' AND dg.STATUS = 'ACTV' "
		        + "JOIN TCM_CATEGORY_FILE_TYPE ft ON ft.FILE_TYPE_CODE = dg.FILE_TYPE_CODE "
		        + "WHERE cc.IDTYPE = 'FOLDER_LEVEL02' "
		        + "AND cc.CDVAL != 'HSK' "
//		        + "AND cc.CDVAL = 'QLVB' "
//		        + "AND cc2.MA_BAO IN ('85700') "
//		        + "AND cc3.MA_BAO = '10100'"
		        ;
		try {
			pstm = conn.prepareCall(sql);
//            pstm.setString(3, processID);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FolderDTO folder = new FolderDTO();
				folder.setFolderId(rs.getString("folder_code"));
				folder.setFolderName(rs.getString("FOLDER_PATH_NAME"));
				folder.setPath(rs.getString("FOLDER_PATH"));
				folder.setFolderLevel(4);
				folderList.add(folder);
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return folderList;
		}
	}
	
	public List<FolderDTO> getPathCSFromCategoryHSK(Connection conn) throws Exception {
		List<FolderDTO> folderList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "WITH cqt3_unique AS ("
		           + "    SELECT * "
		           + "    FROM ( "
		           + "        SELECT cc3.*, "
		           + "               ROW_NUMBER() OVER (PARTITION BY cc3.MA_BAO ORDER BY cc3.TEN_CQT) AS rn "
		           + "        FROM TCM_DM_CQT cc3 "
		           + "        WHERE cc3.ORG_LEVEL = 3 "
		           + "          AND cc3.ORG_ACTION = 'N' "
		           + "    ) "
		           + "    WHERE rn = 1 "
		           + ") "
		           + "SELECT DISTINCT "
		           + "    cc.CDCONTENT || '/' || cc2.TEN_CQT || '/' || cu.TEN_CQT AS folder_path, "
		           + "    cc.CDCONTENT || '/' || cc2.TEN_CQT || '/' || cu.TEN_CQT AS folder_path_name, "
		           + "    cc.CDVAL || '_' || cc2.MA_BAO || '_' || cu.MA_BAO AS folder_code "
		           + "FROM TCM_CATEGORY_CODE cc "
		           + "JOIN TCM_DM_CQT cc2 ON cc2.ORG_LEVEL = 2 AND cc2.ORG_ACTION = 'N' "
		           + "JOIN cqt3_unique cu ON cu.MA_BAO_CHA = cc2.MA_BAO "
		           + "WHERE cc.IDTYPE = 'FOLDER_LEVEL02' "
		           + "  AND cc.CDVAL = 'HSK'"
		           ;

		try {
			pstm = conn.prepareCall(sql);
//            pstm.setString(3, processID);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FolderDTO folder = new FolderDTO();
				folder.setFolderId(rs.getString("folder_code"));
				folder.setPath(rs.getString("FOLDER_PATH"));
				folder.setFolderName(rs.getString("folder_path_name"));
				folder.setFolderLevel(3);
				folderList.add(folder);
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return folderList;
		}
	}
	
	public List<FolderDTO> getPathTinhFromCategoryHSK(Connection conn) throws Exception {
		List<FolderDTO> folderList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT DISTINCT"
				+ "    cc.CDCONTENT || '/' || cc2.TEN_CQT AS folder_path, "
				+ "    cc.CDCONTENT || '/' || cc2.TEN_CQT folder_path_name, "
				+ "    cc.CDVAL || '_' || cc2.MA_BAO AS folder_code "
				+ "FROM TCM_CATEGORY_CODE cc "
				+ "JOIN TCM_DM_CQT cc2 ON cc2.ORG_LEVEL = 2 AND cc2.ORG_ACTION = 'N' "
//				+ "JOIN TCM_DM_CQT cc3 ON cc3.ORG_LEVEL = 3 AND cc3.MA_BAO_CHA = cc2.MA_BAO AND cc3.ORG_ACTION = 'N' "
				+ "WHERE cc.IDTYPE = 'FOLDER_LEVEL02'"
				+ "  AND cc.CDVAL = 'HSK'";
//				+ "  and cc2.MA_BAO = 'KV21'";
		try {
			pstm = conn.prepareCall(sql);
//            pstm.setString(3, processID);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FolderDTO folder = new FolderDTO();
				folder.setFolderId(rs.getString("folder_code"));
				folder.setPath(rs.getString("FOLDER_PATH"));
				folder.setFolderName(rs.getString("folder_path_name"));
				folder.setFolderLevel(2);
				folderList.add(folder);
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return folderList;
		}
	}
	
	public List<FolderDTO> getPathPBanFromCategoryHSK(Connection conn) throws Exception {
		List<FolderDTO> folderList = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT DISTINCT"
				+ "    cc.CDCONTENT || '/' || cc2.TEN_CQT AS folder_path, "
				+ "    cc.CDCONTENT || '/' || cc2.TEN_CQT AS folder_path_name, "
				+ "    cc.CDVAL || '_' || cc2.MA_CQT AS folder_code "
				+ "FROM TCM_CATEGORY_CODE cc "
				+ "JOIN TCM_DM_CQT cc2 ON cc2.ORG_LEVEL = 2 AND cc2.ORG_ACTION = 'Y' "
//				+ "JOIN TCM_DM_CQT cc3 ON cc3.ORG_LEVEL = 3 AND cc3.MA_BAO_CHA = cc2.MA_BAO AND cc3.ORG_ACTION = 'N' "
				+ "WHERE cc.IDTYPE = 'FOLDER_LEVEL02'"
				+ "  AND cc.CDVAL = 'HSK'";
//				+ "  and cc2.MA_BAO = 'KV21'";
		try {
			pstm = conn.prepareCall(sql);
//            pstm.setString(3, processID);
			rs = pstm.executeQuery();
			while (rs.next()) {
				FolderDTO folder = new FolderDTO();
				folder.setFolderId(rs.getString("folder_code"));
				folder.setPath(rs.getString("FOLDER_PATH"));
				folder.setFolderName(rs.getString("folder_path_name"));
				folder.setFolderLevel(2);
				folderList.add(folder);
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return folderList;
		}
	}
	
	public List<StorageDTO> getAllStorageArea(Connection conn) throws Exception {
		List<StorageDTO> listStorage = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;

		String sql = "SELECT OBJECT_STORE_ID, TYPE,OBJECT_STORE_NAME,DESCRIPTION,REPOSITORY_ID FROM tcm_object_store";
		try {
			pstm = conn.prepareCall(sql);
//            pstm.setString(3, processID);
			rs = pstm.executeQuery();
			while (rs.next()) {
				StorageDTO storage = new StorageDTO();
				storage.setObjectStoreId(rs.getString("OBJECT_STORE_ID"));
				storage.setObjectStoreName(rs.getString("OBJECT_STORE_NAME"));
				storage.setObjectStoreDesc(rs.getString("DESCRIPTION"));
				storage.setRepositoryId(rs.getString("REPOSITORY_ID"));
				storage.setType(rs.getString("TYPE"));
				listStorage.add(storage);
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return listStorage;
		}
	}
	
	public void insertToTCMFolder(FolderDTO folder, Connection conn) throws Exception {
	    String checkQuery = "SELECT COUNT(1) FROM TCM_FOLDER WHERE FOLDER_ID = ?";
	    String insertQuery = "INSERT INTO TCM_FOLDER (FOLDER_ID, P_FOLDER_ID, FOLDER_NAME, FOLDER_CLASS, FOLDER_LEVEL, CREATE_BY, CREATE_TIME, STATUS, ROOT_FOLDER_ID) " +
	                         "VALUES (?, ?, ?, ?, ?, ?, sysdate, 'ACTV', 'ROOT')";

	    try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
	        checkStmt.setString(1, folder.getFolderId());
	        try (ResultSet rs = checkStmt.executeQuery()) {
	            if (rs.next() && rs.getInt(1) > 0) {
	                System.out.println("FOLDER_ID đã tồn tại: " + folder.getFolderId() + " -> Bỏ qua insert.");
	                return; // Dừng nếu đã tồn tại
	            }
	        }
	    }

	    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
	        insertStmt.setString(1, folder.getFolderId());
	        insertStmt.setString(2, folder.getpFolderId());
	        insertStmt.setString(3, folder.getFolderName());
	        insertStmt.setString(4, folder.getFolderClass());
	        insertStmt.setInt(5, folder.getFolderLevel());
	        insertStmt.setInt(6, 1); // create_by
	        insertStmt.executeUpdate();

	        System.out.println("Insert folder thành công: " + folder.getFolderId());
	    } catch (Exception e) {
	        System.err.println("Lỗi khi insert folder: " + folder.getFolderId());
	        e.printStackTrace();
	    }
	}

	
	public void insertToTCMFolderSP(FolderDTO folder, Connection conn) throws Exception {

		StringBuilder query = new StringBuilder();
		query.append(
				"INSERT INTO TCM_FOLDER_PATH (FOLDER_ID, FOLDER_PATH, FOLDER_PATH_NAME, FOLDER_CLASS, CREATE_TIME, FOLDER_LEVEL) VALUES (?, ?, ?, 'CommonFolder', sysdate,?)");

		System.out.println(new StringBuilder().append("query=:").append(query).toString());
		try (PreparedStatement preparedStatement = conn.prepareStatement(query.toString())){

			preparedStatement.setString(1, folder.getFolderId());
			preparedStatement.setString(2, folder.getPath());
			preparedStatement.setString(3, folder.getFolderName());	
			preparedStatement.setInt(4, folder.getFolderLevel());	
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			System.err.println(new StringBuilder().append("query2=:").append(query).toString());
			e.printStackTrace();
		}
	}
	
	public void updateStatusFolder(Connection conn, String status, String id) throws Exception {
        PreparedStatement pstm = null;
        //Connection conn = new JNDIConnection().getConnection();
        String sql = "";
        sql = "update TCM_FOLDER_PATH a set a.status = ? where a.FOLDER_ID = ? ";

        try {
            pstm = conn.prepareCall(sql);
            pstm.setString(1, status);
            pstm.setString(2, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Loi sql update updateStatusFolder  ");
            Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                }
            }
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                }
//            }
        }
    }
	
	public JobParameterDTO getJobParameter(Connection conn, Map map) throws Exception {
		JobParameterDTO jobParam = new JobParameterDTO();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String param_code = (String) map.get("param_code");
		String sql = "SELECT ID, PARAM_CODE, PARAM_NAME,PARAM_VALUE, STATUS from TCM_JOB_PARAMETER where STATUS = 'ACTV' and PARAM_CODE = ?";
		try {
			pstm = conn.prepareCall(sql);
            pstm.setString(1, param_code);
			rs = pstm.executeQuery();
			while (rs.next()) {
				jobParam.setId(rs.getInt("ID"));
				jobParam.setParam_code(rs.getString("PARAM_CODE"));
				jobParam.setParam_name(rs.getString("PARAM_VALUE"));
				jobParam.setParam_value(rs.getString("PARAM_VALUE"));
			}
			// System.out.println("sql map -------------"+sql);
			// System.out.println("user_kdt-------------"+userOfKDT);
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Loi sql Khong lay dc cbt_kdt map tms");
			Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
			throw ex;
		} finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
				}
			}
			System.out.println("getList==>" + sql);
			return jobParam;
		}
	}
	
	public void updateStatusJob(Connection conn, String status, String param_code) throws Exception {
        PreparedStatement pstm = null;
        //Connection conn = new JNDIConnection().getConnection();
        String sql = "";
        sql = "update TCM_JOB_PARAMETER a set a.PARAM_VALUE = ? where a.PARAM_CODE = ? ";

        try {
            pstm = conn.prepareCall(sql);
            pstm.setString(1, status);
            pstm.setString(2, param_code);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Loi sql update updateStatusFolder  ");
            Logger.getLogger(FolderTCMDao.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
