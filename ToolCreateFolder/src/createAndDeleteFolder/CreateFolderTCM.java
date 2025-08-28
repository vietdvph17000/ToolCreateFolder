package createAndDeleteFolder;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

import javax.activation.MimetypesFileTypeMap;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hpsf.PropertySet;
//import org.omg.CORBA.CTX_RESTRICT_SCOPE;

import com.filenet.api.admin.ClassDefinition;
import com.filenet.api.admin.LocalizedString;
import com.filenet.api.admin.PropertyDefinition;
import com.filenet.api.admin.PropertyDefinitionBoolean;
import com.filenet.api.admin.PropertyDefinitionDateTime;
import com.filenet.api.admin.PropertyDefinitionFloat64;
import com.filenet.api.admin.PropertyDefinitionInteger32;
import com.filenet.api.admin.PropertyDefinitionString;
import com.filenet.api.admin.PropertyTemplate;
import com.filenet.api.admin.PropertyTemplateBoolean;
import com.filenet.api.admin.PropertyTemplateDateTime;
import com.filenet.api.admin.PropertyTemplateFloat64;
import com.filenet.api.admin.PropertyTemplateObject;
import com.filenet.api.admin.PropertyTemplateString;
import com.filenet.api.admin.PropertyTemplateInteger32;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.collection.FolderSet;
import com.filenet.api.collection.PropertyDefinitionList;
import com.filenet.api.collection.StorageAreaSet;
import com.filenet.api.collection.StoragePolicySet;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.Cardinality;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Annotation;
import com.filenet.api.core.ContentElement;
import com.filenet.api.core.ContentReference;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Factory.FileStorageArea;
import com.filenet.api.core.Factory.StorageArea;
import com.filenet.api.core.Factory.StoragePolicy;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.property.FilterElement;
import com.filenet.api.property.PropertyFilter;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.Configuration;
import com.filenet.api.util.Id;
import com.filenet.api.util.UserContext;
import com.filenet.apiimpl.core.StorageAreaImpl;
import com.filenet.apiimpl.core.StoragePolicyImpl;
import com.ibm.ecm.extension.PluginServiceCallbacks;

import dao.FolderTCMDao;
import dao.TCM_Connection;
import dto.FolderDTO;
import dto.StorageDTO;


public class CreateFolderTCM {
	private String tcmDocClass;
	private String tcmURI;
	private String tcmUsername;
	private String tcmPassword;
	private String tcmDomain;
	private String tcmFolderClass;
	public Properties propertiesCEAPI = new Properties();
//	public static Connection conn = (Connection) new TCM_Connection().getConnection();
	public static FolderTCMDao dao = new FolderTCMDao();
	public CreateFolderTCM() throws IOException {
		InputStream input = CreateFolderTCM.class.getClassLoader().getResourceAsStream("createAndDeleteFolder/TCM.properties");
		propertiesCEAPI.load(input);
		this.tcmDocClass = propertiesCEAPI.getProperty("tcmDocClass");
		this.tcmURI = propertiesCEAPI.getProperty("tcmURI");
		this.tcmUsername = propertiesCEAPI.getProperty("tcmUsername");
		this.tcmPassword = propertiesCEAPI.getProperty("tcmPassword");
		this.tcmDomain = propertiesCEAPI.getProperty("tcmDomain");
		this.tcmFolderClass = propertiesCEAPI.getProperty("tcmFolderClass");
	}
	
	
//	public static void main(String[] args) throws Exception {
//		Connection conn = null;
//		conn = (Connection) new TCM_Connection().getConnection();
//		List<FolderDTO> listPath = new ArrayList();
////		createFolderPath(conn);
//		listPath = dao.getListFolderPathTCM(conn);
//		// Tách chuỗi thành danh sách
//		CreateFolderTCM p8 = new CreateFolderTCM();
//		List<StorageDTO> listObjSto =dao.getAllStorageArea(conn);
//		ArrayList<String> hsList = new ArrayList<String>();
//	
//		String tct = mappingPath2.TCT;
//		for(int i = 0; i < listPath.size(); i++){
//			boolean flag = false;
//			p8.createFolder(listPath.get(i), listObjSto, conn, flag);
//		}
//			System.out.println("-------------------CREATE XONG-----------------------");
//		
//	}
	
	public void xuLyCreateFolderTCM(Connection conn) throws Exception {
		
		List<FolderDTO> listPath = new ArrayList();	
//		createFolderPath(conn);
		listPath = dao.getListFolderPathTCM(conn);
		CreateFolderTCM p8 = new CreateFolderTCM();
		List<StorageDTO> listObjSto =dao.getAllStorageArea(conn);
		ArrayList<String> hsList = new ArrayList<String>();
	
		String tct = mappingPath2.TCT;
		for(int i = 0; i < listPath.size(); i++){			
			boolean flag = false;
			String folderId = listPath.get(i).getFolderId();
			p8.createFolder(listPath.get(i), listObjSto, conn, flag);	
			dao.updateStatusFolder(conn, "Y", folderId);
		}
    }
	
	
	private LocalizedString getLocalizedString(String text, String locale)
	{
	    LocalizedString locStr = Factory.LocalizedString.createInstance ();
	    locStr.set_LocalizedText(text);
	    locStr.set_LocaleName (locale);
	    return locStr;
	}

	private byte[] getBytes(InputStream is) throws IOException {

		int len;
		int size = 1024;
		byte[] buf;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1) {
				bos.write(buf, 0, len);
			}
			buf = bos.toByteArray();
		}
		return buf;
	}
	
//Tạo thư mục trong acce và lưu vào db
//	private void createFolder(FolderDTO folderDto, List<StorageDTO> listObjSto, Connection conn, boolean isFirstInsert) {
//	    if (folderDto == null || folderDto.getPath() == null || folderDto.getPath().trim().isEmpty()) {
//	        System.out.println("Không truyền tham số đường dẫn folder.");
//	        return;
//	    }
//
//	    String path = "TCM/" + folderDto.getPath();
//	    String path_name = "TCM/" + folderDto.getFolderName();
//	    int f_level = folderDto.getFolderLevel();
//	    String[] pathList = path.split("/");
//	    String[] pathNameList = path_name.split("/");
//	    String[] idComponents = folderDto.getFolderId().split("_");
//
////	    if (idComponents.length < 3) {
////	        System.out.println("Sai định dạng folderId: " + folderDto.getFolderId());
////	        return;
////	    }
//
//	    try {
//	        for (StorageDTO storageDTO : listObjSto) {
//	            ObjectStore store = getObjectStore(storageDTO.getObjectStoreName());
////	            Subject subject = UserContext.createSubject(store.getConnection(), tcmUsername, tcmPassword, null);
////	            UserContext userContext = UserContext.get();
////	            userContext.pushSubject(subject);
//
//	            try {
//	                com.filenet.api.core.Folder folderParent = store.get_RootFolder();
//
//	                for (int level = 0; level < pathList.length; level++) {
//	                    String folderName = pathList[level].trim();
//	                    String folderFullName = pathNameList[level].trim();
//	                    if (folderName.isEmpty()) continue;
//
//	                    FolderSet subFolders = folderParent.get_SubFolders();
//	                    boolean exists = false;
//
//	                    for (Iterator<?> it = subFolders.iterator(); it.hasNext(); ) {
//	                        com.filenet.api.core.Folder subFolder = (com.filenet.api.core.Folder) it.next();
//	                        if (folderName.equalsIgnoreCase(subFolder.get_FolderName())) {
//	                            folderParent = subFolder;
//	                            exists = true;
//	                            System.out.println("Đã tồn tại folder: " + folderName);
//	                            break;
//	                        }
//	                    }
//
//	                    if (!exists) {
//	                        String folderId = generateFolderId(idComponents, level, f_level);
//	                        String parentFolderId = generateParentFolderId(idComponents, level, f_level);
//
//	                        com.filenet.api.core.Folder newFolder = Factory.Folder.createInstance(store, folderDto.getFolderClass());
//	                        newFolder.set_Parent(folderParent);
//	                        newFolder.set_FolderName(folderName);
//	                        newFolder.getProperties().putValue("FolderCode", folderId);
//	                        newFolder.getProperties().putValue("FolderLevel", level+1);
//	                        newFolder.save(RefreshMode.REFRESH);
//
//	                        // Cập nhật DTO để insert DB nếu lần đầu
//	                        folderDto.setFolderName(folderFullName);
//	                        folderDto.setFolderId(folderId);
//	                        folderDto.setpFolderId(parentFolderId);
//	                        folderDto.setFolderLevel(level + 1);
//
//	                        System.out.println("Tạo thành công folder: " + folderName + " trong " + folderParent.get_FolderName());
//
//	                        folderParent = newFolder;
//
////	                        if (!isFirstInsert) {
////	                            dao.insertToTCMFolder(folderDto, conn);
////	                        }
//	                    }
//	                }
//
//	                isFirstInsert = true;
//	            } finally {
////	                userContext.popSubject();
//	            }
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//	}
	
	
//	Lưu vào db
	private void createFolder(FolderDTO folderDto, List<StorageDTO> listObjSto, Connection conn, boolean isFirstInsert) {
	    if (folderDto == null || folderDto.getPath() == null || folderDto.getPath().trim().isEmpty()) {
	        System.out.println("Không truyền tham số đường dẫn folder.");
	        return;
	    }

	    String path = "TCM/" + folderDto.getPath();
	    String path_name = "TCM/" + folderDto.getFolderName();
	    int f_level = folderDto.getFolderLevel();
	    String[] pathList = path.split("/");
	    String[] pathNameList = path_name.split("/");
	    String[] idComponents = folderDto.getFolderId().split("_");

	    try {
	        for (int level = 1; level < pathList.length; level++) {
	            String folderName = pathList[level].trim();
	            String folderFullName = pathNameList[level].trim();
	            if (folderName.isEmpty()) continue;

	            String folderId = generateFolderId(idComponents, level, f_level);
	            String parentFolderId = generateParentFolderId(idComponents, level, f_level);

	            // Kiểm tra folder đã tồn tại chưa
	            if (dao.isFolderExist(folderId, conn)) {
	                System.out.println("Đã tồn tại folder trong DB: " + folderFullName);
	                continue;
	            }

	            FolderDTO newFolderDto = new FolderDTO();
	            newFolderDto.setFolderName(folderFullName);
	            newFolderDto.setFolderId(folderId);
	            newFolderDto.setpFolderId(parentFolderId);
	            newFolderDto.setFolderLevel(level + 1);
	            newFolderDto.setFolderClass(folderDto.getFolderClass());
	            newFolderDto.setPath(String.join("/", Arrays.copyOfRange(pathList, 0, level + 1)));

	            dao.insertToTCMFolder(newFolderDto, conn);
	            System.out.println("Đã lưu folder vào DB: " + folderFullName);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private String generateFolderId(String[] idCpn, int level, int f_level) {
	    if(f_level == 6) {
	    	switch (level) {
	    	case 1: return idCpn[0];
	    	case 2: return idCpn[0] + "_" + idCpn[1];
	    	case 3: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[2];
	    	case 4: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[3];
	    	case 5: return idCpn[0] + "_" + idCpn[3] + "_" + idCpn[4];
	    	case 6: return idCpn[0] + "_" + idCpn[3] + "_" + idCpn[5];
	    	default: return UUID.randomUUID().toString(); // fallback
	    	}	    	
	    } else if(f_level == 5) {
	    	switch (level) {
	    	case 1: return idCpn[0];
	    	case 2: return idCpn[0] + "_" + idCpn[1];
	    	case 3: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[2];
	    	case 4: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[3];
	    	case 5: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[4];
	    	default: return UUID.randomUUID().toString(); // fallback
	    	}	    	
	    } else if(f_level == 4){
	    	switch (level) {
	    	case 1: return idCpn[0];
	    	case 2: return idCpn[0] + "_" + idCpn[1];
	    	case 3: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[2];
	    	case 4: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[3];
	    	default: return UUID.randomUUID().toString(); // fallback
	    	}	    	
	    } else{
	    	switch (level) {
	    	case 1: return idCpn[0];
	    	case 2: return idCpn[0] + "_" + idCpn[1];
	    	case 3: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[2];
	    	case 4: return idCpn[0] + "_" + idCpn[2] + "_" + idCpn[3];
	    	default: return UUID.randomUUID().toString(); // fallback
	    	}	    	
	    }
	}

	private String generateParentFolderId(String[] idCpn, int level, int f_level) {
		if(f_level == 6) {
	    	switch (level) {
	    	case 1: return "00000";
	    	case 2: return idCpn[0];
	    	case 3: return idCpn[0] + "_" + idCpn[1];
	    	case 4: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[2];
	    	case 5: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[3];
	    	case 6: return idCpn[0] + "_" + idCpn[3] + "_" + idCpn[4];
	    	default: return UUID.randomUUID().toString(); // fallback
	    	}	    	
	    } else if(f_level == 5) {
	    	switch (level) {
	    	case 1: return "00000";
	    	case 2: return idCpn[0];
	    	case 3: return idCpn[0] + "_" + idCpn[1];
	    	case 4: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[2];
	    	case 5: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[3];
	    	default: return UUID.randomUUID().toString(); // fallback
	    	}	    	
	    } else {
	    	switch (level) {
	    	case 1: return "00000";
	    	case 2: return idCpn[0];
	    	case 3: return idCpn[0] + "_" + idCpn[1];
	    	case 4: return idCpn[0] + "_" + idCpn[1] + "_" + idCpn[2];
	    	default: return UUID.randomUUID().toString(); // fallback
	    	}
	    }
	}

	
	private ObjectStore getObjectStore(String tcmObjectStoreName) {
		com.filenet.api.core.Connection conn = Factory.Connection.getConnection(tcmURI);
		Subject subject = UserContext.createSubject(conn, tcmUsername, tcmPassword, "FileNetP8WSI");
		UserContext uc = UserContext.get();
		uc.pushSubject(subject);
		Domain domain = Factory.Domain.fetchInstance(conn, tcmDomain, null);
		ObjectStore store = Factory.ObjectStore.fetchInstance(domain, tcmObjectStoreName, null);
		return store;
	}
	
}
