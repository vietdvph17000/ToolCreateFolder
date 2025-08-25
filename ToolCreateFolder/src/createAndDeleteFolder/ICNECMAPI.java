package createAndDeleteFolder;
//package com.seatech.ibs.ecm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.security.auth.Subject;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.collection.DocumentSet;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.Connection;
import com.filenet.api.core.ContentElement;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.query.SearchSQL;
import com.filenet.api.query.SearchScope;
import com.filenet.api.util.UserContext;
import createAndDeleteFolder.CorpAppConstant;
import createAndDeleteFolder.*;
//import com.seatech.ibs.retail.pojo.dmucmabke.DMucMaBKeConstant;
//import com.seatech.ibs.retail.pojo.dmucmatbao.DMucMaTBaoConstant;
//import com.seatech.ibs.retail.pojo.dmucmatkhai.DMucMaTKhaiConstant;

public class ICNECMAPI  {
	private String ecmDocClass;
	private String ecmURI;
	private String ecmUsername;
	private String ecmPassword;
	private String ecmDomain;
	private String ecmFolder;
	private String ecmObjectStoreName;
	private String ecmQueryClause ;
	private static final String MA_TBAO_TKHOAN_GDICH_THUEDTU = "702";
	
	public Properties propertiesCEAPI = new Properties();

	public ICNECMAPI() throws IOException {
		InputStream input = ICNECMAPI.class.getClassLoader().getResourceAsStream("createAndDeleteFolder/CEAPI.properties");
		propertiesCEAPI.load(input);
		this.ecmDocClass = propertiesCEAPI.getProperty("ecmDocClass");
		this.ecmURI = propertiesCEAPI.getProperty("ecmURI");
		this.ecmUsername = propertiesCEAPI.getProperty("ecmUsername");
		this.ecmPassword = propertiesCEAPI.getProperty("ecmPassword");
		this.ecmDomain = propertiesCEAPI.getProperty("ecmDomain");
		this.ecmFolder = propertiesCEAPI.getProperty("ecmFolder");
		this.ecmObjectStoreName = propertiesCEAPI.getProperty("ecmObjectStoreName");
		this.ecmQueryClause = propertiesCEAPI.getProperty("ecmQueryClause");	
//		this.ecmQueryClause = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,IDTaiLieuVersionGoc,DateCreated,DateLastModified,ContentSize,Creator,TenTL,NgayTao,NguoiTao,DonVi,VersionTL,IDTL,ContentElements";
	}

	
	public static class DocClass {
		public static class ICANHAN{
			public static final String TO_KHAI = "iCaNhanTK";
			public static final String THONG_BAO = "iCaNhanTB";
			public static final String BANG_KE = "iCaNhanBK";
			public static final String HOSO = "iCaNhanDK";
		}
		
		public static class BPM{
			public static final String ASC = "ASCDocument";
		}
	}
	
	public static class DocFolder {
		public static class ICANHAN{
			public static final String TO_KHAI = "/ICANHAN/TO_KHAI"; 
			public static final String THONG_BAO = "/ICANHAN/THONG_BAO";
			public static final String TIN_TUC = "/ETAX/TIN_TUC";
			public static final String BANG_KE = "/ICANHAN/BANG_KE";
			public static final String HO_SO = "/ICANHAN/HO_SO";
			public static final String DANG_KY = "/ICANHAN/DANG_KY";
		}
		
		public static class BPM{
			public static final String ASC = "/ASCFOLDER";
		}
			
	}

	public static class QueryClause {
		public static class ICANHAN{
			public static final String TO_KHAI = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,CMT,TenNNT,MaGDich,LoaiTKhai,KieuKi,NgayNop,DonVi,ContentElements";
			public static final String THONG_BAO = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,CMT,TenNNT,MaGDichHSoLienQuan,SoThongBao,NgayGui,DonVi,ContentElements";
			public static final String BANG_KE = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,CMT,TenNNT,MaGDich,MaBangKe,NgayGui,DonVi,ContentElements";
			public static final String HO_SO = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,NgayTao,DonVi,ContentElements";
			
//			public static final String DANH_SACH = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,IDTaiLieuVersionGoc,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,MaBangKe,NgayGui,DonVi,ContentElements";
//			public static final String NOP_THUE = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,IDTaiLieuVersionGoc,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,SoChungTu,NgayNop,Donvi,ContentElements";
//			public static final String DANG_KY = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,IDTaiLieuVersionGoc,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,NgayGui,DonVi,ContentElements";
//			public static final String HSO_KHAC = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,IDTaiLieuVersionGoc,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,NgayGui,DonVi,ContentElements";
		}
		
		public static class BPM{
			public static final String ASC = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,TenTL,NgayTao,NguoiTao,DonVi,VersionTL,IDTL,ContentElements";
		}
	}
	
	public static class HashMapKey{
		public static final String ECM_FILE_CONTENT = "file_content";
		public static final String ECM_FILE_NAME = "file_name";
	}		

	
	
	public ICNECMAPI(String docClass, String docFolder, String metadataSearch){
		setEcmDocClass(docClass);
		setEcmFolder(docFolder);
		setEcmQueryClause(metadataSearch);
	}

	public static void main(String[] args) throws Exception {

//		InputStream input = new FileInputStream("resources/CEAPI.properties");
//		propertiesCEAPI.load(input);

		ICNECMAPI p8 = new ICNECMAPI();
		p8.setEcmDocClass("Document");

//		// 1.Put tai lieu vao ECM
//
//		Path path = Paths.get("D:/sample.xml");
//		byte[] data = Files.readAllBytes(path);
////
//		System.out.println("Put tai lieu vao ECM ");
		HashMap<String, String> putDocHashMapKey = new HashMap<String, String>();
		putDocHashMapKey.put("DocumentTitle", "changeDocCat.xml");
//		putDocHashMapKey.put("MST", "123456");
//		p8.setEcmDocClass(DocClass.ETAX.DANG_KY);
		String retDocumentID = p8.putDocument(putDocHashMapKey, "Hello World!!".getBytes());
		System.out.println(retDocumentID);
//
		System.out.println("================================== ");

		
//		 String docDelId = null;
//		
		 // 2.Search danh sach tai lieu co MaNNT=12345
//		 System.out.println("Search danh sach tai lieu");
//		 HashMap<String, String>searchDocHashMapKey = new HashMap<String,String>();
//		 searchDocHashMapKey.put("DocumentTitle", "test");
////		 searchDocHashMapKey.put("NgaySuaTuNgay", "02/04/2016");
////		 searchDocHashMapKey.put("NgaySuaDenNgay", "05/04/2016");
//		 //hoac tim theo ID
//		 searchDocHashMapKey.put("ID", "{307CC153-0000-C11B-9175-8F1B98925458}");
//		 ArrayList<String> arrKeys=p8.searchDocumentKeys(searchDocHashMapKey);
////		 ArrayList<String> arrKeys=p8.searchAllVersionDocumentKeys(searchDocHashMapKey);
////		 
//		 for (int i=0;i<arrKeys.size();i++)
//		 {
//			 System.out.println("File ID:"+arrKeys.get(i)
//					 .toString());
//		 }
//		 System.out.println("================================== ");
		
		
		// 3.Get Metadata cua tai lieu
//		 System.out.println("Get Metadata cua tai lieu ");
//		 HashMap<String, String> metaDocHashMapKey =
//		 p8.getDocumentMetaData("{40995154-0000-CB11-B01C-797E39B345A1}");
//		 for (String key : metaDocHashMapKey.keySet()) {
//		 System.out.println(key + ":" + metaDocHashMapKey.get(key));
//		 }
//		 System.out.println("================================== ");
//		
		
		// 4.Get tai lieu
//		System.out.println("Get tai lieu");
//		byte[] docBytes = p8.getDocumentContent("{A0DD7954-0A00-C82F-B614-ABAA84739977}");
//		// Ghi ra file
//		if (docBytes != null) {
//			System.out.print("");
//			FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\0300304312_10101_QTR17_04052016_resource.xml");
//			fos.write(docBytes);
//			fos.close();
//		}
//		System.out.println("================================== ");

		// 5.Replace tai lieu
//		File file = new File("D:\\change.xml");
//		System.out.println(p8.addVersion("{B0F24C54-0000-C414-94E8-7455D5AF2F7A}", file, true));

//		// 6.Delete tai lieu
//		// ---Del by Id
//		 p8.deleteDocById(docDelId);
//		// ---Del by properties
//		 HashMap<String, String>delMap = new HashMap<String, String>();
//		 delMap.put("DocumentTitle", "3.pdf");
//		 delMap.put("MaNNT", "12345");
//		 p8.deleteDocByProperties(delMap);

		// 7.Them phien ban cho tai lieu
		
//		//8.Put to khai to ECM
//		byte[] data = FileUtils.readFileToByteArray(new File("C:\\Users\\Administrator\\Desktop\\tkhai.xml"));
//		
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		
//		String IDECM = p8.putTKhaiETAXToECM("", "", "0100231226-999", "tét tong cuc", "11220160000004952", "C", "M", dateFormat.parse("09/05/2016 09:01:07"), "10107", data, "11220160000004952.xml");
//		System.out.print("Ma ECM"+ IDECM);
		
//		 HashMap<String, Object> metaDocHashMapKey  = p8.getTBaoETAXFromECM("{90CED355-0000-C079-93CE-1DF41E5A1BB1}");
//		 byte[] contentTKhaiECM = (byte[]) metaDocHashMapKey.get(HashMapKey.ECM_FILE_CONTENT);
//		 System.out.print(new String(contentTKhaiECM));		
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
			while ((len = is.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;
	}

	private DocumentSet searchDocuments(HashMap<String, String> arrSearchFields) {
		ObjectStore os = getObjectStore();
		SearchScope search = new SearchScope(os);
		SearchSQL sql = new SearchSQL();
		sql.setSelectList(ecmQueryClause);
		sql.setFromClauseInitialValue(ecmDocClass, "d", true);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Chi cho truy van trong 1 folder
		// String whereClause = "FoldersFiledIn='" + ecmFolder +"' AND ";
		String whereClause = "";
		for (String key : arrSearchFields.keySet()) {
			// DateCreated,		DateLastModified,
//			if(key.toLowerCase().equals("ngaysuatungay")){
//				whereClause += "NgaySua" + ">=" + greaterDate(arrSearchFields.get(key)) + "";
//			}else if(key.toLowerCase().equals("ngaysuadenngay")){
//				whereClause += "NgaySua" + "<=" + lessDate(arrSearchFields.get(key)) + "";
//			}else if(key.toLowerCase().equals("ngaytaotungay")){
//				whereClause += "NgayTaoDocument" + ">=" + greaterDate(arrSearchFields.get(key)) + "";
//			}else if(key.toLowerCase().equals("ngaytaodenngay")){
//				whereClause += "NgayTaoDocument" + "<=" + lessDate(arrSearchFields.get(key)) + "";
//			}else{
				whereClause += key + "='" + arrSearchFields.get(key) + "'";
//			}
			whereClause += " AND ";
		}
		whereClause += "IsCurrentVersion = true";
		whereClause += " AND ";
		
		sql.setWhereClause(whereClause.substring(0,
				whereClause.lastIndexOf(" AND ")));
		DocumentSet documents = (DocumentSet) search.fetchObjects(sql,
				new Integer(50), null, Boolean.valueOf(true));
		return documents;
	}
	
	private DocumentSet searchDocumentForDelete(HashMap<String, String> arrSearchFields) {
		ObjectStore os = getObjectStore();
		SearchScope search = new SearchScope(os);
		SearchSQL sql = new SearchSQL();
		sql.setSelectList(ecmQueryClause);
		sql.setFromClauseInitialValue(ecmDocClass, "d", true);
		// Chi cho truy van trong 1 folder
		// String whereClause = "FoldersFiledIn='" + ecmFolder +"' AND ";
		String whereClause = "";
		for (String key : arrSearchFields.keySet()) {
			whereClause += key + "='" + arrSearchFields.get(key) + "'";
			whereClause += " AND ";
		}
		
		sql.setWhereClause(whereClause.substring(0,
				whereClause.lastIndexOf(" AND ")));
		DocumentSet documents = (DocumentSet) search.fetchObjects(sql,
				new Integer(50), null, Boolean.valueOf(true));
		return documents;
	}
	
	private DocumentSet searchAllVersionDocuments(HashMap<String, String> arrSearchFields) {
		ObjectStore os = getObjectStore();
		SearchScope search = new SearchScope(os);
		SearchSQL sql = new SearchSQL();
		SearchSQL sqlSeries = new SearchSQL();
		sql.setSelectList(ecmQueryClause);
		sql.setFromClauseInitialValue(ecmDocClass, "d", true);
		sqlSeries.setSelectList(ecmQueryClause);
		sqlSeries.setFromClauseInitialValue(ecmDocClass, "d", true);
		// Chi cho truy van trong 1 folder
		// String whereClause = "FoldersFiledIn='" + ecmFolder +"' AND ";
		String whereClause = "";
		String whereClauseSeries = "";
		int countId = 0;
		for (String key : arrSearchFields.keySet()) {
			if(key.toLowerCase().equals("id")){
				countId++;
			}
//			if(key.toLowerCase().equals("ngaysuatungay")){
//				whereClause += "NgaySua" + ">=" + greaterDate(arrSearchFields.get(key)) + "";
//			}else if(key.toLowerCase().equals("ngaysuadenngay")){
//				whereClause += "NgaySua" + "<=" + lessDate(arrSearchFields.get(key)) + "";
//			}else if(key.toLowerCase().equals("ngaytaotungay")){
//				whereClause += "NgayTaoDocument" + ">=" + greaterDate(arrSearchFields.get(key)) + "";
//			}else if(key.toLowerCase().equals("ngaytaodenngay")){
//				whereClause += "NgayTaoDocument" + "<=" + lessDate(arrSearchFields.get(key)) + "";
//			}else{
				whereClause += key + "='" + arrSearchFields.get(key) + "'";
//			}
			whereClause += " AND ";
		}
		
		DocumentSet documents = null;
		if(countId==0){
			sql.setWhereClause(whereClause.substring(0,
					whereClause.lastIndexOf(" AND ")));
			documents = (DocumentSet) search.fetchObjects(sql,
					new Integer(50), null, Boolean.valueOf(true));
		}else{
			whereClause = "Id = "+ arrSearchFields.get("ID");
			sql.setWhereClause(whereClause);
			documents = (DocumentSet) search.fetchObjects(sql,
					new Integer(50), null, Boolean.valueOf(true));
			Iterator it = documents.iterator();
			String IDTaiLieuVersionGoc = "";
			while (it.hasNext()) {
				Document document = (Document) it.next();
				IDTaiLieuVersionGoc = document.getProperties().getStringValue("IDTaiLieuVersionGoc");
			}
			for (String key : arrSearchFields.keySet()) {
				if(key.toLowerCase().equals("id")){
					countId++;
				}
//				if(key.toLowerCase().equals("ngaysuatungay")){
//					whereClauseSeries += "NgaySua" + ">=" + greaterDate(arrSearchFields.get(key)) + "";
//					whereClauseSeries += " AND ";
//				}else if(key.toLowerCase().equals("ngaysuadenngay")){
//					whereClauseSeries += "NgaySua" + "<=" + lessDate(arrSearchFields.get(key)) + "";
//					whereClauseSeries += " AND ";
//				}else if(key.toLowerCase().equals("ngaytaotungay")){
//					whereClauseSeries += "NgayTaoDocument" + ">=" + greaterDate(arrSearchFields.get(key)) + "";
//					whereClauseSeries += " AND ";
//				}else if(key.toLowerCase().equals("ngaytaodenngay")){
//					whereClauseSeries += "NgayTaoDocument" + "<=" + lessDate(arrSearchFields.get(key)) + "";
//					whereClauseSeries += " AND ";
//				}
			}
			whereClauseSeries = "IDTaiLieuVersionGoc = '" + IDTaiLieuVersionGoc+"'";
			sqlSeries.setWhereClause(whereClauseSeries);
			documents = (DocumentSet) search.fetchObjects(sqlSeries,
					new Integer(50), null, Boolean.valueOf(true));
		}
		return documents;
	}

	private ObjectStore getObjectStore() {
		Connection conn = Factory.Connection
				.getConnection(ecmURI);
		Subject subject = UserContext.createSubject(conn,ecmUsername,ecmPassword,
				"FileNetP8WSI");
		UserContext uc = UserContext.get();
		uc.pushSubject(subject);
		Domain domain = Factory.Domain.fetchInstance(conn,ecmDomain, null);
		ObjectStore store = Factory.ObjectStore.fetchInstance(domain,ecmObjectStoreName,
				null);
		return store;
	}

	public String putDocument(HashMap<String, String> arrDocumentProperties,
			byte[] documentContent) throws Exception{

		ObjectStore os = getObjectStore();
		String docClass = ecmDocClass;
		String ecmFolderPath = ecmFolder;
		String docName = arrDocumentProperties.get("DocumentTitle");
		Document doc;
		// 1.Them tai lieu document
		doc = Factory.Document.createInstance(os, docClass);
		for (String key : arrDocumentProperties.keySet()) {
			Object temp = arrDocumentProperties.get(key);
			if(temp instanceof Date){
				doc.getProperties().putValue(key, (Date)temp);
			}
			else{
				doc.getProperties().putValue(key, arrDocumentProperties.get(key));
			}
		}

		ContentElementList cel = null;
		ContentTransfer ctNew = null;
		if (documentContent != null) {
			ctNew = Factory.ContentTransfer.createInstance();
			ByteArrayInputStream is = new ByteArrayInputStream(documentContent);
			ctNew.setCaptureSource(is);
			ctNew.set_RetrievalName(docName);
		}
		if (ctNew != null) {
			cel = Factory.ContentElement.createList();
			cel.add(ctNew);
		}
		if (cel != null)
			doc.set_ContentElements(cel);
		doc.save(RefreshMode.REFRESH);

		// 2.Dua tai lieu vao folder : ecmFolderPath
		ReferentialContainmentRelationship rcr = null;
		Folder fo = Factory.Folder.fetchInstance(os, ecmFolderPath, null);
		if (doc instanceof Document)
			rcr = fo.file((Document) doc, AutoUniqueName.AUTO_UNIQUE,
					((Document) doc).get_Name(),
					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
		rcr.save(RefreshMode.REFRESH);

		// 3.Check-in tai lieu
		doc.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
		if(docClass.equals("iCaNhanTK")){
			doc.getProperties().putValue("IdGocTKhai", doc.get_Id().toString());
		}
		doc.save(RefreshMode.REFRESH);
		doc.refresh();

		return doc.get_Id().toString();
	}
	
	public String putDocumentHasName(HashMap<String, String> arrDocumentProperties,
			byte[] documentContent, String name) throws Exception{

		ObjectStore os = getObjectStore();
		String docClass = ecmDocClass;
		String ecmFolderPath = ecmFolder;
		String docName = name;
		Document doc;
		// 1.Them tai lieu document
		doc = Factory.Document.createInstance(os, docClass);
		for (String key : arrDocumentProperties.keySet()) {
			Object temp = arrDocumentProperties.get(key);
			if(temp instanceof Date){
				doc.getProperties().putValue(key, (Date)temp);
			}
			else{
				doc.getProperties().putValue(key, arrDocumentProperties.get(key));
			}
		}

		ContentElementList cel = null;
		ContentTransfer ctNew = null;
		if (documentContent != null) {
			ctNew = Factory.ContentTransfer.createInstance();
			ByteArrayInputStream is = new ByteArrayInputStream(documentContent);
			ctNew.setCaptureSource(is);
			ctNew.set_RetrievalName(docName);
		}
		if (ctNew != null) {
			cel = Factory.ContentElement.createList();
			cel.add(ctNew);
		}
		if (cel != null)
			doc.set_ContentElements(cel);
		doc.save(RefreshMode.REFRESH);

		// 2.Dua tai lieu vao folder : ecmFolderPath
		ReferentialContainmentRelationship rcr = null;
		Folder fo = Factory.Folder.fetchInstance(os, ecmFolderPath, null);
		if (doc instanceof Document)
			rcr = fo.file((Document) doc, AutoUniqueName.AUTO_UNIQUE,
					((Document) doc).get_Name(),
					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
		rcr.save(RefreshMode.REFRESH);

		// 3.Check-in tai lieu
		doc.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
		if(docClass.equals("iCaNhanTK")){
			doc.getProperties().putValue("IdGocTKhai", doc.get_Id().toString());
		}
		doc.save(RefreshMode.REFRESH);
		doc.refresh();

		return doc.get_Id().toString();
	}

	public ArrayList<String> searchDocumentKeys(
			HashMap<String, String> arrSearchFields) throws Exception {
		if(arrSearchFields.size() == 0){
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		}else{
			DocumentSet documents = searchDocuments(arrSearchFields);
			Iterator it = documents.iterator();
			ArrayList<String> arrOfKeys = new ArrayList<String>();
			while (it.hasNext()) {
				Document document = (Document) it.next();
				arrOfKeys.add(document.get_Id().toString());
			}
			return arrOfKeys;
		}
	}
	
	public ArrayList<String> searchAllVersionDocumentKeys(
			HashMap<String, String> arrSearchFields) throws Exception{
		if(arrSearchFields.size() == 0){
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		}else{
			DocumentSet documents = searchAllVersionDocuments(arrSearchFields);
			Iterator it = documents.iterator();
			ArrayList<String> arrOfKeys = new ArrayList<String>();
			while (it.hasNext()) {
				Document document = (Document) it.next();
				arrOfKeys.add(document.get_Id().toString());
			}
			return arrOfKeys;
		}
	}

	public HashMap<String, String> getDocumentMetaData(String documentID) throws Exception{
		if(documentID == null){
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		}else{
		HashMap<String, String> hashMapKey = new HashMap<String, String>();
		hashMapKey.put("Id", documentID);
		DocumentSet documents = searchDocumentForDelete(hashMapKey);
		Iterator it = documents.iterator();
		HashMap hm = new HashMap();
		if (it.hasNext()) {
			Document document = (Document) it.next();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for(int i = 0; i < document.getProperties().toArray().length; i++){
				hm.put(document.getProperties().toArray()[i].getPropertyName(), propertyValue(document, i));
			}
		}
		return hm;
		}
	}
	
	private String propertyValue(Document document, int i){
		String strReturn = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String type = document.getProperties().toArray()[i].getClass().toString();
		if(type.contains("String")){
			strReturn = document.getProperties().toArray()[i].getStringValue();
		}
		if(type.contains("Boolean")){
			strReturn = document.getProperties().toArray()[i].getBooleanValue() == true ? "1":"0";
		}
		if(type.contains("Date")){
			strReturn = sdf.format(document.getProperties().toArray()[i].getDateTimeValue());
		}
		if(type.contains("Object")){
			strReturn = "Object type, không return giá trị String của thuộc tính này";
		}
		if(type.contains("Float64")){
			strReturn = String.valueOf(document.getProperties().toArray()[i].getFloat64Value());
		}
		if(type.contains("Id")){
			strReturn = document.getProperties().toArray()[i].getIdValue().toString();
		}
		if(type.contains("Stream")){
			strReturn = "Stream type, không return giá trị String của thuộc tính này";
		}
		if(type.contains("Integer32")){
			strReturn = String.valueOf(document.getProperties().toArray()[i].getInteger32Value());
		}
		if(strReturn == null){
			strReturn = "";
		}
		return strReturn;
	}
    
	/**
	 * hàm trả ra HashMap bao gồm content file dạng byte[], tên file lưu trong ECM
	 * @param documentID
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getDocumentContent(String documentID) throws Exception {
		if(documentID == null){
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		}else{
		Document document = null;
		HashMap<String, String> hashMapKey = new HashMap<String, String>();
		hashMapKey.put("Id", documentID);
		DocumentSet documents = searchDocumentForDelete(hashMapKey);
		Iterator it = documents.iterator();
		HashMap<String, Object> hashMapResult = new HashMap<String, Object>();
		byte[] retBytes = null;
		String fileName = null;
		if (it.hasNext()) {
			document = (Document) it.next();
			ContentElementList contents = document.get_ContentElements();
			fileName = getFileExtension(document.get_Name());
			ContentElement content;
			Iterator itContent = contents.iterator();
			if (itContent.hasNext()) {
				content = (ContentElement) itContent.next();
				InputStream inputStream = ((ContentTransfer) content)
						.accessContentStream();
				retBytes = getBytes(inputStream);
			}
		}
		
		hashMapResult.put(HashMapKey.ECM_FILE_CONTENT, retBytes);
		hashMapResult.put(HashMapKey.ECM_FILE_NAME, fileName);
		
		return hashMapResult;
		}
	}
	
	
	/**
	 * * hàm trả ra content file dạng String lưu trong ECM
	 * @param documentID
	 * @return
	 * @throws Exception
	 */
	public String getStringDocumentContent(String documentID) throws Exception {
		if(documentID == null){
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		}else{
		Document document = null;
		HashMap<String, String> hashMapKey = new HashMap<String, String>();
		hashMapKey.put("Id", documentID);
		DocumentSet documents = searchDocumentForDelete(hashMapKey);
		Iterator it = documents.iterator();
		byte[] retBytes = null;
		if (it.hasNext()) {
			document = (Document) it.next();
			ContentElementList contents = document.get_ContentElements();
			ContentElement content;
			Iterator itContent = contents.iterator();
			if (itContent.hasNext()) {
				content = (ContentElement) itContent.next();
				InputStream inputStream = ((ContentTransfer) content)
						.accessContentStream();
				retBytes = getBytes(inputStream);
			}
		}
		return new String(retBytes, "UTF-8");
		}
	}
	
	public String getDocumentNameContent(String documentID) throws Exception {
		if(documentID == null){
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		}else{
		Document document = null;
		HashMap<String, String> hashMapKey = new HashMap<String, String>();
		hashMapKey.put("Id", documentID);
		DocumentSet documents = searchDocumentForDelete(hashMapKey);
		Iterator it = documents.iterator();
		String retrievalName = null;
		if (it.hasNext()) {
			document = (Document) it.next();
			ContentElementList contents = document.get_ContentElements();
			ContentElement content;
			Iterator itContent = contents.iterator();
			if (itContent.hasNext()) {
				content = (ContentElement) itContent.next();
				retrievalName = content.getProperties().get("RetrievalName").toString();
				retrievalName = retrievalName.substring(retrievalName.indexOf("Value")+6,retrievalName.indexOf("IsDirty")-1);
			}
		}
		return retrievalName;
		}
	}

	public void deleteDocById(String documentID) throws Exception {
		if(documentID == null){
			System.out.println("**** Không truyền tham số delete ****");
		}else{
		Document document = null;
		HashMap<String, String> hashMapKey = new HashMap<String, String>();
		hashMapKey.put("Id", documentID);
		DocumentSet documents = searchDocumentForDelete(hashMapKey);
		Iterator it = documents.iterator();
		if (it.hasNext()) {
			document = (Document) it.next();
			document.delete();
			document.save(RefreshMode.NO_REFRESH);
		}
		}
	}

	public void deleteDocByProperties(HashMap<String, String> hashMapKey)
			throws Exception {
		if(hashMapKey.size() == 0){
			System.out.println("**** Không truyền tham số delete ****");
		}else{
		ArrayList<String> listDocId = this.searchDocumentKeys(hashMapKey);
		if (listDocId != null) {
			for (int i = 0; i < listDocId.size(); i++) {
				deleteDocById(listDocId.get(i));
			}
		}
		}
	}

	public String updateVersion(String documentID, File file, boolean version) throws Exception {
		if(documentID == null || file == null){
			System.out.println("**** Thiếu tham số ****");
			return null;
		}else{
		Document document = null;
		Document newDoc = null;
		HashMap<String, String> hashMapKey = new HashMap<String, String>();
		hashMapKey.put("Id", documentID);
		DocumentSet documents = searchDocuments(hashMapKey);
		
		Iterator it = documents.iterator();
		if (it.hasNext()) {
			document = (Document) it.next();
			String oldId = document.get_Id().toString();
			document.checkout(com.filenet.api.constants.ReservationType.EXCLUSIVE, null, null, null);
		    document.save(RefreshMode.REFRESH);
		    newDoc = (Document) document.get_Reservation();
		    
			ContentElementList contentElements = Factory.ContentElement.createList();
		    ContentTransfer ctObject = Factory.ContentTransfer.createInstance();
		    ctObject.setCaptureSource(new FileInputStream(file));
		    ctObject.set_RetrievalName(file.getName());
		    FileNameMap fileNameMap = URLConnection.getFileNameMap();
		    ctObject.set_ContentType(fileNameMap.getContentTypeFor(file.getName()));
		    contentElements.add(ctObject);
		    newDoc.set_ContentElements(contentElements);
		    if(version){
		    	newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
		    }else{
		    	newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
		    }
		    newDoc.save(RefreshMode.NO_REFRESH);
		    deleteDocById(oldId);
		}else{
			System.out.println("Tai lieu khong phai la phien ban hien hanh");
		}
			if(newDoc != null){
				return newDoc.get_Id().toString();
			}else{
				return null;
			}
		}
	}
	
	public String addVersion(String documentID, File file, boolean version) throws Exception {
		if(documentID == null || file == null){
			System.out.println("**** Thiếu tham số ****");
			return null;
		}else{
		Document document = null;
		Document newDoc = null;
		HashMap<String, String> hashMapKey = new HashMap<String, String>();
		hashMapKey.put("Id", documentID);
		DocumentSet documents = searchDocuments(hashMapKey);
		Iterator it = documents.iterator();
		if (it.hasNext()) {
			document = (Document) it.next();
			String oldId = document.get_Id().toString();
			document.checkout(com.filenet.api.constants.ReservationType.EXCLUSIVE, null, null, null);
		    document.save(RefreshMode.REFRESH);
		    newDoc = (Document) document.get_Reservation();
		    
			ContentElementList contentElements = Factory.ContentElement.createList();
		    ContentTransfer ctObject = Factory.ContentTransfer.createInstance();
		    ctObject.setCaptureSource(new FileInputStream(file));
		    ctObject.set_RetrievalName(file.getName());
		    FileNameMap fileNameMap = URLConnection.getFileNameMap();
		    ctObject.set_ContentType(fileNameMap.getContentTypeFor(file.getName()));
		    contentElements.add(ctObject);
		    newDoc.set_ContentElements(contentElements);
		    if(version){
		    	newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
		    }else{
		    	newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
		    }
		    newDoc.save(RefreshMode.NO_REFRESH);
		}else{
			System.out.println("Tai lieu khong phai la phien ban hien hanh");
		}
			if(newDoc != null){
				return newDoc.get_Id().toString();
			}else{
				return null;
			}
		}
	}
	
	 public String addVersion(String documentID, byte[] fileData, boolean version) throws Exception {
	        if (documentID == null || fileData == null) {
	            System.out.println("**** Thiếu tham số ****");
	            return null;
	        } else {
	            Document document = null;
	            Document newDoc = null;
	            HashMap<String, String> hashMapKey = new HashMap<String, String>();
	            hashMapKey.put("Id", documentID);
	            DocumentSet documents = searchDocuments(hashMapKey);
	            Iterator it = documents.iterator();
	            if (it.hasNext()) {
	                document = (Document) it.next();
	                String oldId = document.get_Id().toString();

	                document.checkout(com.filenet.api.constants.ReservationType.EXCLUSIVE, null, null, null);
	                document.save(RefreshMode.REFRESH);
	                newDoc = (Document) document.get_Reservation();

	                ContentElementList contentElements = Factory.ContentElement.createList();
	                ContentTransfer ctObject = Factory.ContentTransfer.createInstance();
	                ctObject.setCaptureSource(new ByteArrayInputStream(fileData));
	                contentElements.add(ctObject);
	                newDoc.set_ContentElements(contentElements);
	                if (version) {
	                    newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
	                } else {
	                    newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
	                }
	                newDoc.save(RefreshMode.NO_REFRESH);
	            } else {
	                System.out.println("Tai lieu khong phai la phien ban hien hanh");
	            }
	            if (newDoc != null) {
	                return newDoc.get_Id().toString();
	            } else {
	                return null;
	            }
	        }
	    }
	
	private String greaterDate(String strDate){
		return strDate.substring(6, 10) + strDate.substring(3, 5) + strDate.substring(0, 2) + "T000000Z";
	}
	
	private String lessDate(String strDate){
		return strDate.substring(6,10) + strDate.substring(3, 5) + strDate.substring(0, 2) + "T235959Z";
	}

	public String getEcmDocClass() {
		return ecmDocClass;
	}

	public void setEcmDocClass(String ecmDocClass) {
		this.ecmDocClass = ecmDocClass;
	}

	public String getEcmURI() {
		return ecmURI;
	}

	public void setEcmURI(String ecmURI) {
		this.ecmURI = ecmURI;
	}

	public String getEcmUsername() {
		return ecmUsername;
	}

	public void setEcmUsername(String ecmUsername) {
		this.ecmUsername = ecmUsername;
	}

	public String getEcmPassword() {
		return ecmPassword;
	}

	public void setEcmPassword(String ecmPassword) {
		this.ecmPassword = ecmPassword;
	}

	public String getEcmDomain() {
		return ecmDomain;
	}

	public void setEcmDomain(String ecmDomain) {
		this.ecmDomain = ecmDomain;
	}

	public String getEcmFolder() {
		return ecmFolder;
	}

	public void setEcmFolder(String ecmFolder) {
		this.ecmFolder = ecmFolder;
	}

	public String getEcmObjectStoreName() {
		return ecmObjectStoreName;
	}

	public void setEcmObjectStoreName(String ecmObjectStoreName) {
		this.ecmObjectStoreName = ecmObjectStoreName;
	}

	public String getEcmQueryClause() {
		return ecmQueryClause;
	}

	public void setEcmQueryClause(String ecmQueryClause) {
		this.ecmQueryClause = ecmQueryClause;
	}
	
	/**
	 * Ham luu to khai vao ECM
	 * @param MaNhomHSo Mã nhóm hồ sơ
	 * @param MaLoaiHSo Mã loại hồ sơ
	 * @param MST mã số thuế
	 * @param TenNNT Tên NNT
	 * @param MaGDich Mã giao dịch điện tử
	 * @param LoaiTKhai Loại tờ khai
	 * @param KieuKi Kiểu kỳ
	 * @param NgayNop Ngày nộp
	 * @param DonVi mã cơ quan thuế
	 * @param documentContent  Nội dung file lưu trong ECM
	 * @param tenTep tên tệp hồ sơ, trường hợp không có tên file thì lấy mã giao dịch điện tử + đuôi định dạng
	 * @return
	 */
	
	public String putTKhaiICNToECM(String MaHSo, String LoaiHSo, String MST, String CMT,
			String TenNNT, String MaGDich, String LoaiTKhai, String KieuKi, Date NgayNop, String DonVi,
			byte[] documentContent, String tenTep, String year) throws Exception {
		if (!checkFileExtension(tenTep))
			throw new NullPointerException("Tên tệp không đúng định dạng!");		
		String MaUD = CorpAppConstant.ICN_KEY;
		HashMap<String, String> arrDocumentProperties =
				GetHashMapTKhaiICN(MaUD, MaHSo, LoaiHSo, MST,CMT, TenNNT, MaGDich, LoaiTKhai, KieuKi,
						 NgayNop, DonVi, tenTep);
		this.setEcmFolder(getECMFolder(DonVi, MaHSo, year));
		this.setEcmDocClass(DocClass.ICANHAN.TO_KHAI);
		String ECMId = putDocument(arrDocumentProperties,documentContent) ; 
		return ECMId;
	}
	
	/**
	 * 
	 * @param MaUD Mã ứng dụng = CorpAppConstant.ICN_KEY
	 * @param MaNhomHSo Mã nhóm hồ sơ
	 * @param MaLoaiHSo Mã loại hồ sơ
	 * @param MST mã số thuế
	 * @param TenNNT Tên NNT
	 * @param MaGDich Mã giao dịch điện tử
	 * @param LoaiTKhai Loại tờ khai
	 * @param KieuKi Kiểu kỳ
	 * @param NgayNop Ngày nộp
	 * @param DonVi mã cơ quan thuế
	 * @param tenTep tên tệp hồ sơ, trường hợp không có tên file thì lấy mã giao dịch điện tử + đuôi định dạng
	 * @return
	 */
	public static HashMap GetHashMapTKhaiICN(String MaUD, String MaHSo, String LoaiHSo, String MST,String CMT,
			String TenNNT, String MaGDich, String LoaiTKhai, String KieuKi, Date NgayNop, String DonVi, String tenTep ){
		HashMap<Object, Object> arrDocumentProperties = new HashMap<Object, Object>();
		arrDocumentProperties.put("DocumentTitle", tenTep);  
		arrDocumentProperties.put("MaUD", MaUD);
		arrDocumentProperties.put("MaHSo", MaHSo);
		arrDocumentProperties.put("LoaiHSo", LoaiHSo);
		arrDocumentProperties.put("MST",MST);
		arrDocumentProperties.put("CMT",CMT);
		arrDocumentProperties.put("TenNNT", TenNNT);	
		arrDocumentProperties.put("MaGDich", MaGDich);
		arrDocumentProperties.put("LoaiTKhai", LoaiTKhai);
		arrDocumentProperties.put("KieuKi", KieuKi);
		arrDocumentProperties.put("NgayNop", NgayNop);
		arrDocumentProperties.put("DonVi", DonVi);
		  
		return arrDocumentProperties;
	}
	
	/**
	 * Get to khai trong ECM
	 * @param documentID
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getTKhaiICNFromECM(String documentID) throws Exception {
//		this.setEcmFolder(DocFolder.ICANHAN.TO_KHAI);
		this.setEcmDocClass(DocClass.ICANHAN.TO_KHAI);
		this.setEcmQueryClause(QueryClause.ICANHAN.TO_KHAI);
		return getDocumentContent(documentID);
	}
	
	
	/**
	 * Ham luu bang ke vao ECM
	 * @param MaHSo Mã hồ sơ
	 * @param LoaiHSo Loại hồ sơ
	 * @param MST MST
	 * @param TenNNT Tên NNT
	 * @param MaGDich Mã giao dịch điện tử
	 * @param MaBangKe Mã bảng kê
	 * @param NgayGui Ngày gửi
	 * @param DonVi CQT
	 * @param documentContent  Nội dung file lưu trong ECM
	 * @param tenTep tên tệp hồ sơ, trường hợp không có tên file thì lấy mã giao dịch điện tử + đuôi định dạng
	 * @return
	 */
	
	public String putBKeICNToECM(String MaHSo, String LoaiHSo, String MST,String CMT,String TenNNT, String MaGDich,	 
			String MaBangKe, Date NgayGui, String DonVi, byte[] documentContent, String tenTep,String year) throws Exception {
		if (!checkFileExtension(tenTep))
			throw new NullPointerException("Tên tệp không đúng định dạng!");		
		String MaUD = CorpAppConstant.ICN_KEY;
		HashMap<String, String> arrDocumentProperties =
				 GetHashMapBKeICN(MaUD, MaHSo, LoaiHSo, MST,CMT,TenNNT, MaGDich, MaBangKe, NgayGui, DonVi, tenTep);
		this.setEcmFolder(getECMFolder(DonVi,MaHSo,year));
		this.setEcmDocClass(DocClass.ICANHAN.BANG_KE);
		String ECMId = putDocument(arrDocumentProperties,documentContent) ; 
		return ECMId;
	}
	
	/**
	 * 
	 * @param MaUD Mã ứng dụng
	 * @param MaHSo Mã hồ sơ
	 * @param LoaiHSo Loại hồ sơ
	 * @param MST MST
	 * @param TenNNT Tên NNT
	 * @param MaGDich Mã giao dịch điện tử
	 * @param MaBangKe Mã bảng kê
	 * @param NgayGui Ngày gửi
	 * @param DonVi CQT
	 * @param tenTep tên tệp hồ sơ, trường hợp không có tên file thì lấy mã giao dịch điện tử + đuôi định dạng
	 * @return
	 */

	public static HashMap GetHashMapBKeICN(String MaUD, String MaHSo, String LoaiHSo, String MST,String CMT, String TenNNT, String MaGDich,	 
			String MaBangKe, Date NgayGui, String DonVi, String tenTep ){
		HashMap<Object, Object> arrDocumentProperties = new HashMap<Object, Object>();
		arrDocumentProperties.put("DocumentTitle", tenTep);  
		arrDocumentProperties.put("MaUD", MaUD);
		arrDocumentProperties.put("MaHSo", MaHSo);
		arrDocumentProperties.put("LoaiHSo", LoaiHSo);	
		arrDocumentProperties.put("MST", MST);	
		arrDocumentProperties.put("CMT", CMT);	
		arrDocumentProperties.put("TenNNT", TenNNT);	 
		arrDocumentProperties.put("MaGDich", MaGDich);	 
		arrDocumentProperties.put("MaBangKe", MaBangKe);	 
		arrDocumentProperties.put("NgayGui", NgayGui);
		arrDocumentProperties.put("DonVi", DonVi);
		  
		return arrDocumentProperties;
	}
	/**
	 * ham get bang ke trong ECM
	 * @param documentID
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getBKeICNFromECM(String documentID) throws Exception {
//		this.setEcmFolder(DocFolder.ICANHAN.BANG_KE);
		this.setEcmDocClass(DocClass.ICANHAN.BANG_KE);
		this.setEcmQueryClause(QueryClause.ICANHAN.BANG_KE);
		return getDocumentContent(documentID);
	}
	
	/**
	 * Ham luu thong bao vao ECM
	 * @param MaHSo Mã hồ sơ
	 * @param LoaiHSo Loại hồ sơ
	 * @param MST mã số thuế
	 * @param TenNNT Tên NNT
	 * @param MaGDichHSoLienQuan Mã giao dịch điện tử
	 * @param SoThongBao Số thông báo
	 * @param NgayGui Ngày gửi
	 * @param DonVi Mã cơ quan thuế
	 * @param documentContent Nội dung file lưu trong ECM
	 * @param tenTep tên tệp hồ sơ, trường hợp không có tên file thì lấy mã giao dịch điện tử + đuôi định dạng
	 * @return
	 * @throws Exception
	 */
	public String putTBaoICNToECM(String MaHSo, String LoaiHSo, String MST,String CMT, String TenNNT, 
			String MaGDichHSoLienQuan, String SoThongBao, Date NgayGui, String DonVi, byte[] documentContent, String tenTep,String year) throws Exception {
		if (!checkFileExtension(tenTep))
			throw new NullPointerException("Tên tệp không đúng định dạng!");		
		String MaUD = CorpAppConstant.ICN_KEY;
		HashMap<String, String> arrDocumentProperties =
				 GetHashMapTBaoICN(MaUD, MaHSo, LoaiHSo, MST,CMT,TenNNT, MaGDichHSoLienQuan, SoThongBao, NgayGui, DonVi, tenTep);
		this.setEcmFolder(getECMFolder(DonVi, MaHSo, year));
		this.setEcmDocClass(DocClass.ICANHAN.THONG_BAO);
		String ECMId = putDocument(arrDocumentProperties,documentContent) ; 
		return ECMId;
	}
	
	/**
	 * 
	 * @param MaUD Mã ứng dụng  = CorpAppConstant.ICN_KEY
	 * @param MaHSo Mã hồ sơ
	 * @param LoaiHSo Loại hồ sơ
	 * @param MST mã số thuế
	 * @param TenNNT Tên NNT
	 * @param MaGDichHSoLienQuan Mã giao dịch điện tử
	 * @param SoThongBao Số thông báo
	 * @param NgayGui Ngày gửi
	 * @param DonVi Mã cơ quan thuế
	 * @param documentContent Nội dung file lưu trong ECM
	 * @param tenTep tên tệp hồ sơ, trường hợp không có tên file thì lấy mã giao dịch điện tử + đuôi định dạng
	 * @return
	 * @throws Exception
	 */
	public static HashMap GetHashMapTBaoICN(String MaUD, String MaHSo, String LoaiHSo, String MST, String CMT, String TenNNT, 
			String MaGDichHSoLienQuan, String SoThongBao, Date NgayGui, String DonVi, String tenTep){
		HashMap<Object, Object> arrDocumentProperties = new HashMap<Object, Object>();
		arrDocumentProperties.put("DocumentTitle", tenTep);  
		arrDocumentProperties.put("MaUD", MaUD);
		arrDocumentProperties.put("MaHSo", MaHSo);
		arrDocumentProperties.put("LoaiHSo", LoaiHSo);
		arrDocumentProperties.put("MST", MST);
		arrDocumentProperties.put("CMT", CMT);
		arrDocumentProperties.put("TenNNT", TenNNT);
		arrDocumentProperties.put("MaGDichHSoLienQuan", MaGDichHSoLienQuan);
		arrDocumentProperties.put("SoThongBao", SoThongBao);
		arrDocumentProperties.put("NgayGui", NgayGui);
		arrDocumentProperties.put("DonVi", DonVi);
		  
		return arrDocumentProperties;
	}
	/**
	 * ham get thong bao trong ECM
	 * @param documentID
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getTBaoICNFromECM(String documentID) throws Exception {
		//this.setEcmFolder(DocFolder.ICANHAN.THONG_BAO);
		this.setEcmDocClass(DocClass.ICANHAN.THONG_BAO);
		this.setEcmQueryClause(QueryClause.ICANHAN.THONG_BAO);
		return getDocumentContent(documentID);
	}
	
//	/**
//	 * Ham luu ho so khac vao ECM
//	 * @param MaHSo Mã hồ sơ
//	 * @param LoaiHSo Loại hồ sơ
//	 * @param MST Mã số thuế
//	 * @param TenNNT Tên NNT
//	 * @param MaGDich Mã giao dịch điện tử
//	 * @param NgayGui Ngày gửi
//	 * @param DonVi Mã cơ quan thuế
//	 * @param documentContent Nội dung file lưu trong ECM
//	 * @param tenTep tên tệp hồ sơ , trường hợp không có tên file thì lấy mã giao dịch điện tử + đuôi định dạng
//	 * @return
//	 */
	public String putHSoKhacETAXToECM(String MaHSo, String LoaiHSo, String MST, String TenNNT, 
									 String MaGDich, Date NgayTaoHSo, String DonVi, byte[] documentContent, String tenTep,String year) throws Exception {
		if (!checkFileExtension(tenTep))
			throw new NullPointerException("Tên tệp không đúng định dạng!");		
		String MaUD = CorpAppConstant.ICN_KEY;
		HashMap<String, String> arrDocumentProperties =
				 GetHashHSoKhacETAX(MaUD, MaHSo, LoaiHSo, MST, TenNNT, MaGDich, NgayTaoHSo, DonVi, tenTep);
		this.setEcmFolder(getECMFolder(DonVi, MaHSo,year));
		this.setEcmDocClass(DocClass.ICANHAN.HOSO);
		String ECMId = putDocument(arrDocumentProperties,documentContent) ; 
		return ECMId;
	}
//	
//	/**
//	 * 
//	 * @param MaUD Mã ứng dụng
//	 * @param MaHSo Mã hồ sơ
//	 * @param LoaiHSo Loại hồ sơ
//	 * @param MST Mã số thuế
//	 * @param TenNNT Tên NNT
//	 * @param MaGDich Mã giao dịch
//	 * @param NgayGui Ngày 
//	 * @param DonVi Mã cơ quan thuế
//	 * @param tenTep tên tệp hồ sơ, trường hợp không có tên file thì lấy mã giao dịch điện tử + đuôi định dạng
//	 * @return
//	 */
	public static HashMap GetHashHSoKhacETAX(String MaUD, String MaHSo, String LoaiHSo, String MST, String TenNNT, 
			 String MaGDich, Date NgayTaoHSo, String DonVi , String tenTep){
		HashMap<Object, Object> arrDocumentProperties = new HashMap<Object, Object>();
		arrDocumentProperties.put("DocumentTitle", tenTep);  
		arrDocumentProperties.put("MaUD", MaUD);
		arrDocumentProperties.put("MaHSo", MaHSo);
		arrDocumentProperties.put("LoaiHSo", LoaiHSo);
		arrDocumentProperties.put("MST", MST);
		arrDocumentProperties.put("TenNNT", TenNNT);
		arrDocumentProperties.put("MaGDich", MaGDich);
		arrDocumentProperties.put("NgayTaoHSo", NgayTaoHSo);
		arrDocumentProperties.put("DonVi", DonVi);
//		  
		return arrDocumentProperties;
	}
//	
//	/**
//	 * ham get ho so khac trong ECM
//	 * @param documentID
//	 * @return
//	 * @throws Exception
//	 */
//	public HashMap<String, Object> getHSoKhacETAXFromECM(String documentID) throws Exception {
//		this.setEcmFolder(DocFolder.ETAX.ETAX_KHAC);
//		this.setEcmDocClass(DocClass.ETAX.HSO_KHAC);
//		this.setEcmQueryClause(QueryClause.ETAX.HSO_KHAC);
//		return getDocumentContent(documentID);
//	}
	
	public HashMap<String, Object> getTBaoBPMFromECM(String documentID) throws Exception {
		return getDocumentContent(documentID);
	}
	
	/**
	 * ham get dinh dang file dua theo ten file trong ECM
	 * trong truong hop ten file bi loi se tra ra gia tri mac dinh la ""
	 * @param fileName
	 * @return fileExtension
	 * @throws Exception
	 */
	public String getFileExtension(String fileName) throws Exception {
		if (fileName == null || "".equals(fileName))
			return "";
		int i = fileName.lastIndexOf('.');
		if (i > 0 && i < fileName.length() - 1) {
		    return fileName.substring(i+1);
		} else
			return "";
	}	
	/**
	 * ham check ten file truyen vao ECM. Ten file dung co dang: 1.xml
	 * trong truong hop ten file bi loi se tra ve false
	 * @param fileName
	 * @return boolean
	 * @throws Exception
	 */
	public boolean checkFileExtension(String fileName) throws Exception {
		Object fileExt = getFileExtension(fileName);
		if (fileExt != null && !"".equals(fileExt.toString()))
			return Pattern.compile("^(xml|xls|xlsx|doc|docx|pdf|jpg|png)$").matcher(fileExt.toString()).find();
		else
			return false;
	}		
	
	/**
	 * ham xoa thong bao trong ECM
	 * @param documentID
	 * @return
	 * @throws Exception
	 */
	public void deleteTBaoICNFromECM(String documentID) throws Exception {
		if(documentID == null){
			System.out.println("**** Không truyền tham số delete ****");
		}else{
		Document document = null;
		HashMap<String, String> hashMapKey = new HashMap<String, String>();
		hashMapKey.put("Id", documentID);
		DocumentSet documents = checkFileInDocument(hashMapKey);
		Iterator it = documents.iterator();
		if (it.hasNext()) {
			document = (Document) it.next();
			document.delete();
			document.save(RefreshMode.NO_REFRESH);
		}
		}
	}
	
	private DocumentSet checkFileInDocument(HashMap<String, String> arrSearchFields) {
		ObjectStore os = getObjectStore();
		SearchScope search = new SearchScope(os);
		SearchSQL sql = new SearchSQL();
		sql.setSelectList(QueryClause.ICANHAN.THONG_BAO);
		sql.setFromClauseInitialValue(DocClass.ICANHAN.THONG_BAO, "d", true);
		String whereClause = "";
		for (String key : arrSearchFields.keySet()) {
			whereClause += key + "='" + arrSearchFields.get(key) + "'";
			whereClause += " AND ";
		}
		
		sql.setWhereClause(whereClause.substring(0,
				whereClause.lastIndexOf(" AND ")));
		DocumentSet documents = (DocumentSet) search.fetchObjects(sql,
				new Integer(50), null, Boolean.valueOf(true));
		return documents;
	}
	/**
	 * tra ve duong dan folder tren ECM
	 * @param donvi (maCQT noi nop), loai ho so
	 * @return 
	 * @throws Exception
	 */	
	private String getECMFolder(String strDonVi, String maHso, String strYear) throws Exception {
		return    "/" + mappingPath.TCT 
				+ "/" + mappingPath.CQT.CQT_MAP.get(strDonVi) 
				+ "/" + getDir(maHso)				
				+ "/" + strYear;
	}
//	private String getECMFolderDKy(String strDonVi, String strLoaiHSo,String strYear) throws Exception {
//		return    "/" + MappingPath.TCT 
//				+ "/" + MappingPath.CQT.CQT_MAP.get(strDonVi) 
//				+ "/" + getDir(strLoaiHSo)
//				+ "/" + MappingPath.NGHIEPVU_HOSO.DANG_KY_THUE.DKY_SDUNG_DV
//				+ "/" + strYear
//				;
//	}
	/**
	 * tra ve duong dan folder tuong ung dua tren ma to khai, ma thong bao...
	 * truong hop ma khong co se throw Exception
	 * @param ma ho so
	 * @return 
	 * @throws Exception
	 */	
	public static String getDir(String maHSo) throws Exception {
//		if (Pattern.compile("^(" +DMucMaTKhaiConstant.TKHAI_02_LPTB+")$").matcher(maHSo).find())
//			return MappingPath.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.LPTB;
//		else if (Pattern.compile("^(" +DMucMaTKhaiConstant.TKHAI_01_TTS+"|"+DMucMaBKeConstant.BKE_01_BK_TTS+")$").matcher(maHSo).find())
//			return MappingPath.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.THUE_TSAN;
//		else if (Pattern.compile("^(" +DMucMaTKhaiConstant.TKHAI_01_BC_SDHD_CNKD+")$").matcher(maHSo).find())
//			return MappingPath.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.BCAO_AC;
//		else if (Pattern.compile("^("+DMucMaTKhaiConstant.TKHAI_05_KK_TNCN+"|" +DMucMaTKhaiConstant.TKHAI_09_KK_TNCN+"|" +DMucMaTKhaiConstant.TKHAI_01_CNKD+"|"+DMucMaTKhaiConstant.TKHAI_02_KK_XS+"|"+DMucMaTKhaiConstant.TKHAI_01_TKN_XSBHDC+"|"+DMucMaTKhaiConstant.TKHAI_02_KK_TNCN+"|"+DMucMaTKhaiConstant.TKHAI_02_QTT_TNCN+"|"+DMucMaTKhaiConstant.TKHAI_05_QTT_TNCN+"|"+DMucMaTKhaiConstant.TKHAI_05_DS_TNCN+"|"+DMucMaTKhaiConstant.TKHAI_02_TH+"|"+DMucMaTKhaiConstant.TKHAI_05_DK_TNCN+"|"+DMucMaTKhaiConstant.TKHAI_08B_KK_TNCN+"|"+DMucMaTKhaiConstant.TKHAI_06_KK_TNCN+"|"+DMucMaTKhaiConstant.TKHAI_13_KK_TNCN+")$").matcher(maHSo).find())
//			return MappingPath.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TNCN;
//		else if (maHSo.equals(DMucMaTBaoConstant.TBAO_03_TB_TDT) || maHSo.equals(DMucMaTBaoConstant.MA_TBAO_CAP_LAI_MAT_KHAU) || maHSo.equals(DMucMaTBaoConstant.MA_TBAO_01_TB_TDT) || maHSo.equals(DMucMaTBaoConstant.MA_TBAO_XNHAN_CAP_MST_QTT) || maHSo.equals(DMucMaTBaoConstant.MA_TBAO_XNHAN_KO_CAP_MST_QTT) || maHSo.equals(DMucMaTBaoConstant.MA_TBAO_XNHAN_CAP_MST_NPT))
//			return MappingPath.NGHIEPVU_HOSO.DICH_VU_DIEN_TU.THONG_BAO;
//		else if (maHSo.equals("DANG_KY"))
//			return MappingPath.NGHIEPVU_HOSO.DICH_VU_DIEN_TU.DANG_KY_DICH_VU;
//		else if(maHSo.equals(DMucMaTKhaiConstant.TKHAI_05_DK_TNCN))
//			return MappingPath.NGHIEPVU_HOSO.DANG_KY_THUE.TKHAI;
//		else if(maHSo.equals(DMucMaTBaoConstant.MA_TBAO_XNHAN_CAP_MST_QTT) || maHSo.equals(DMucMaTBaoConstant.MA_TBAO_XNHAN_KO_CAP_MST_QTT))
//			return MappingPath.NGHIEPVU_HOSO.DANG_KY_THUE.THONG_BAO;
//		else
//			throw new NullPointerException();
		return mappingPath.NGHIEPVU_HOSO.DANG_KY_THUE.THONG_BAO;
	}	
}
