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


public class CreateFolderPathTCM {
	private String tcmDocClass;
	private String tcmURI;
	private String tcmUsername;
	private String tcmPassword;
	private String tcmDomain;
	private String tcmFolderClass;
	public Properties propertiesCEAPI = new Properties();
//	public static Connection conn = (Connection) new TCM_Connection().getConnection();
	public static FolderTCMDao dao = new FolderTCMDao();
	public CreateFolderPathTCM() throws IOException {
		InputStream input = CreateFolderPathTCM.class.getClassLoader().getResourceAsStream("createAndDeleteFolder/TCM.properties");
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
//			System.out.println("-------------------CREATE XONG-----------------------");
//		
//	}
	
	public static void createFolderPath(Connection conn) throws Exception {
		try {
			List<FolderDTO> listPathCQTCtg = new ArrayList();
			List<FolderDTO> listPathNTLCtg = new ArrayList();
			List<FolderDTO> listPathPBanCtg = new ArrayList();
			List<FolderDTO> listPathHSK_CS = new ArrayList();
			List<FolderDTO> listPathHSK_Tinh = new ArrayList();
			List<FolderDTO> listPathHSK_Pban = new ArrayList();
			listPathCQTCtg = dao.getPathCQTFromCategory(conn);
			listPathNTLCtg = dao.getPathNTLFromCategory(conn);
			listPathPBanCtg = dao.getPathPBanFromCategory(conn);
			listPathHSK_Pban = dao.getPathPBanFromCategoryHSK(conn);
			listPathHSK_CS = dao.getPathCSFromCategoryHSK(conn);
			listPathHSK_Tinh = dao.getPathTinhFromCategoryHSK(conn);
			for(FolderDTO folder : listPathCQTCtg) {
				dao.insertToTCMFolderSP(folder, conn);
			}	
			for(FolderDTO folder : listPathNTLCtg) {
				dao.insertToTCMFolderSP(folder, conn);
			}
			for(FolderDTO folder : listPathPBanCtg) {
				dao.insertToTCMFolderSP(folder, conn);
			}
			for(FolderDTO folder : listPathHSK_CS) {
				String path1 = folder.getPath()+"/Biên bản";
				String id1 = folder.getFolderId()+"_BBAN";
				String path2 = folder.getPath()+"/Phiếu đề nghị";
				String id2 = folder.getFolderId()+"_PHIEUDEN";
				folder.setPath(path1);
				folder.setFolderName(path1);
				folder.setFolderId(id1);
				dao.insertToTCMFolderSP(folder, conn);
				folder.setPath(path2);
				folder.setFolderName(path2);
				folder.setFolderId(id2);
				dao.insertToTCMFolderSP(folder, conn);
			}
			for(FolderDTO folder : listPathHSK_Pban) {
				String path1 = folder.getPath()+"/Biên bản";
				String id1 = folder.getFolderId()+"_BBAN";
				String path2 = folder.getPath()+"/Phiếu đề nghị";
				String id2 = folder.getFolderId()+"_PHIEUDEN";
				folder.setPath(path1);
				folder.setFolderName(path1);
				folder.setFolderId(id1);
				dao.insertToTCMFolderSP(folder, conn);
				folder.setPath(path2);
				folder.setFolderName(path2);
				folder.setFolderId(id2);
				dao.insertToTCMFolderSP(folder, conn);
			}
			for(FolderDTO folder : listPathHSK_Tinh) {
				String path1 = folder.getPath()+"/Biên bản";
				String id1 = folder.getFolderId()+"_BBAN";
				String path2 = folder.getPath()+"/Phiếu đề nghị";
				String id2 = folder.getFolderId()+"_PHIEUDEN";
				folder.setPath(path1);
				folder.setFolderName(path1);
				folder.setFolderId(id1);
				dao.insertToTCMFolderSP(folder, conn);
				folder.setPath(path2);
				folder.setFolderName(path2);
				folder.setFolderId(id2);
				dao.insertToTCMFolderSP(folder, conn);
			}
			dao.updateStatusJob(conn, "OFF", ConstantValue.PROPERTY.JOB_CREATE_PATH);
			System.out.println("-------------------CREATE XONG-----------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
