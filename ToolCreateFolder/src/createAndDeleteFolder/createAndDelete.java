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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.activation.MimetypesFileTypeMap;
import javax.security.auth.Subject;

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
import com.filenet.api.core.Connection;
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
import com.filenet.api.util.Id;
import com.filenet.api.util.UserContext;
import com.filenet.apiimpl.core.StorageAreaImpl;
import com.filenet.apiimpl.core.StoragePolicyImpl;

import createAndDeleteFolder.createAndDelete.DocClass.ETAX;
import createAndDeleteFolder.mappingPath2.NGHIEPVU_HOSO.HOA_DON_DIEN_TU;

public class createAndDelete {

	//cty 22
//	private String ecmDocClass = "ASCDocument";
//	private String ecmURI = "http://192.168.10.22:9081/wsi/FNCEWS40MTOM";
//	private String ecmUsername = "ecmadmin";
//	private String ecmPassword = "bpm123";
//	private String ecmDomain = "tct_domain";
//	private String ecmFolder = "/ASCFOLDER";
//	private String ecmObjectStoreName = "tct_store";
//	private String ecmQueryClause = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,TenTL,NgayTao,NguoiTao,DonVi,VersionTL,IDTL ";

	//cty 22
//	private String ecmDocClass = "ASCDocument";
//	private String ecmURI = "http://192.168.1.178:9081/wsi/FNCEWS40MTOM";
//	private String ecmUsername = "etax_sync";
//	private String ecmPassword = "ecm123";
//	private String ecmDomain = "etax_domain";
//	private String ecmFolder = "/ASCFOLDER";
//	private String ecmObjectStoreName = "etax_store";
//	private String ecmQueryClause = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,TenTL,NgayTao,NguoiTao,DonVi,VersionTL,IDTL ";

		
	//cty etax
	 private String ecmDocClass = "ASCDocument";
	 private String ecmURI = "http://192.168.1.188:9080/wsi/FNCEWS40MTOM";
	 private String ecmUsername = "etax_sync";
	 private String ecmPassword = "ecm123";
	 private String ecmDomain = "etax_domain";
	 private String ecmFolder = "/ASCFOLDER";
	 private String ecmObjectStoreName = "etax_store";
	private String ecmQueryClause = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,IDTaiLieuVersionGoc,DateCreated,DateLastModified,ContentSize,Creator,TenTL,NgayTao,NguoiTao,DonVi,VersionTL,IDTL,ContentElements";

	//hddt test
//	private String ecmDocClass = "ASCDocument";
//	private String ecmURI = "http://10.0.139.2:9080/wsi/FNCEWS40MTOM";
//	private String ecmUsername = "ecmad";
//	private String ecmPassword = "ecmad12345";
//	private String ecmDomain = "hddt_domain";
//	private String ecmFolder = "/ASCFOLDER";
//	private String ecmObjectStoreName = "hddt_store";
//	private String ecmQueryClause = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,DonVi,LoaiHSo,MaUD,PbanHDon,TenHDon,MauHDon,KHieuHDon,SoHDon,NgayHDon,TThaiHDon,MoTaBMau,ContentElements";
	
	private ArrayList<String> storageAreaList = new ArrayList<String>();
	private ArrayList<String> storagePolicyList = new ArrayList<String>(); 

	public static class DocClass {

		public static class ETAX {

			public static final String TO_KHAI = "TKDocument";
			public static final String THONG_BAO = "TBDocument";
			public static final String DANH_SACH = "DSDocument";
			public static final String NOP_THUE = "NTDocument";
			public static final String DANG_KY = "DKDocument";
			public static final String HSO_KHAC = "HSKDocument";
		}

		public static class BPM {

			public static final String ASC = "ASCDocument";
		}
		
		public static class HDON_DTU {

			public static final String HDDT = "HDDT";
		}
	}

	public static class DocFolder {

		public static class ETAX {

			public static final String DANG_KY_DV = "/ETAX/DANG_KY_DV";
			public static final String ETAX_KHAC = "/ETAX/ETAX_KHAC";
			public static final String HOAN_THUE = "/ETAX/HOAN_THUE";
			public static final String HOI_DAP = "/ETAX/HOI_DAP";
			public static final String KE_KHAI = "/ETAX/KE_KHAI";
			public static final String NOP_THUE = "/ETAX/NOP_THUE";
			public static final String THONG_BAO = "/ETAX/THONG_BAO";
			public static final String TIN_TUC = "/ETAX/TIN_TUC";
		}

		public static class BPM {

			public static final String ASC = "/ASCFOLDER";
		}
	}

	public static class QueryClause {

		public static class ETAX {

			public static final String TO_KHAI = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaNhomHSo,MaLoaiHSo,MST,TenNNT,MaGDich,LoaiTKhai,KieuKi,NgayNop,DonVi ";
			public static final String THONG_BAO = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDichHSoLienQuan,SoThongBao,NgayGui,DonVi ";
			public static final String DANH_SACH = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,MaBangKe,NgayGui,DonVi ";
			public static final String NOP_THUE = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,SoChungTu,NgayNop,Donvi ";
			public static final String DANG_KY = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,NgayGui,DonVi ";
			public static final String HSO_KHAC = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,MaUD,MaHSo,LoaiHSo,MST,TenNNT,MaGDich,NgayGui,DonVi ";
		}

		public static class BPM {

			public static final String ASC = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,TenTL,NgayTao,NguoiTao,DonVi,VersionTL,IDTL ";
		}
		
		public static class HDON_DTU {

			private static final String HDDT = "Id,MimeType,Name,DocumentTitle,CurrentState,IsCurrentVersion,MajorVersionNumber,MinorVersionNumber,DateCreated,DateLastModified,ContentSize,Creator,DonVi,LoaiHSo,MaUD,PbanHDon,TenHDon,MauHDon,KHieuHDon,SoHDon,NgayHDon,TThaiHDon,MoTaBMau,ContentElements";
		}
		
	}

	public createAndDelete() {
	}

	public createAndDelete(String docClass, String docFolder, String metadataSearch) {
		setEcmDocClass(docClass);
		setEcmFolder(docFolder);
		setEcmQueryClause(metadataSearch);
	}
	
	
	public static void main(String[] args) throws Exception {
		Properties propertiesCEAPI = new Properties();
		 InputStream input = new
		 FileInputStream("resources/CEAPI.properties");
		 propertiesCEAPI.load(input);
		createAndDelete p8 = new createAndDelete();
		
//		ArrayList<String> listPath = new ArrayList<String>();
		
//		p8.getLastPathsForDelete(listPath, "/TCT2", "/TCT2", p8.getObjectStore(), true);
//		p8.getLastPathsForDelete(listPath, "/TCT3", "/TCT3", p8.getObjectStore(), true);
//		System.out.println("-------------------DELETE XONG-----------------------");
		 p8.setStorageAreaList(p8.areaSetName());
		// p8.setStoragePolicyList(p8.policySetName());
		 p8.createFolder(DocFolder.ETAX.DANG_KY_DV);
//		 p8.createFolder(DocFolder.ETAX.ETAX_KHAC);
//		 p8.createFolder(DocFolder.ETAX.HOAN_THUE);
//		 p8.createFolder(DocFolder.ETAX.HOI_DAP);
//		 p8.createFolder(DocFolder.ETAX.KE_KHAI);
//		 p8.createFolder(DocFolder.ETAX.NOP_THUE);
//		 p8.createFolder(DocFolder.ETAX.THONG_BAO);
//		 p8.createFolder(DocFolder.ETAX.TIN_TUC);
//		 p8.createFolder(DocFolder.BPM.ASC);
		
		ArrayList<String> cqtList = new ArrayList<String>();
		ArrayList<String> hsList = new ArrayList<String>();
		

		hsList.add( mappingPath2.NGHIEPVU_HOSO.AN_DINH_THUE.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.AN_DINH_THUE.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.DANG_KY_THUE.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.DANG_KY_THUE.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.DICH_VU_DIEN_TU.DANG_KY_DICH_VU );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.DICH_VU_DIEN_TU.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.DICH_VU_DIEN_TU.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.GIAI_QUYET_KHIEU_NAI.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.GIAI_QUYET_KHIEU_NAI.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.BCAO_AC );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.LPTB );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.THONG_BAO );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.THUE_TSAN );
		// 24062019 huyph add
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TKHAI_DANG_KY_THUE );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TNCN );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.VSPETRO );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.BCTC );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.PLPHI  );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.NTNN );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.MBAI );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.GTGT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TNDN );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TNGUYEN );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TTDB );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.BCHD );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TBHD );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TDIEN  );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.TTS  );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.BLAI );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.CTLN );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.DKTHUE );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.DDAI );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.XDAU );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.BVMT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.XSDT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KHAI_THUE_TINH_THUE.DKHI );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KIEM_TRA_THANH_TRA.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.KIEM_TRA_THANH_TRA.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.MIEN_GIAM_XOA_NO.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.MIEN_GIAM_XOA_NO.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.NOP_THUE.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.NOP_THUE.THONG_BAO );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.NOP_THUE.DKNHANG );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.NOP_THUE.GNT );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.QUYET_DINH_PHAT_HANH_CHINH.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.QUYET_DINH_PHAT_HANH_CHINH.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.THU_TUC_HOAN_THUE.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.THU_TUC_HOAN_THUE.THONG_BAO );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.THU_TUC_HOAN_THUE.HTHUE );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.TRACH_NHIEM_HOAN_THANH_NGHIA_VU_NOP_THUE.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.TRACH_NHIEM_HOAN_THANH_NGHIA_VU_NOP_THUE.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.UY_NHIEM_THU_THUE.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.UY_NHIEM_THU_THUE.THONG_BAO );
		
		hsList.add( mappingPath2.NGHIEPVU_HOSO.NO_THUE.FOLDER_PARENT );
		hsList.add( mappingPath2.NGHIEPVU_HOSO.NO_THUE.THONG_BAO );
		
//		hsList.add( mappingPath2.NGHIEPVU_HOSO.HOA_DON_DIEN_TU.FOLDER_PARENT );
//		hsList.add( mappingPath2.NGHIEPVU_HOSO.HOA_DON_DIEN_TU.BAN_HANG );
//		hsList.add( mappingPath2.NGHIEPVU_HOSO.HOA_DON_DIEN_TU.BAN_HANG_KHU_PHI_THUE_QUAN );
//		hsList.add( mappingPath2.NGHIEPVU_HOSO.HOA_DON_DIEN_TU.GTKT );
//		hsList.add( mappingPath2.NGHIEPVU_HOSO.HOA_DON_DIEN_TU.XUAT_KHO_GUI_BAN_DAI_LY );
//		hsList.add( mappingPath2.NGHIEPVU_HOSO.HOA_DON_DIEN_TU.XUAT_KHO_VAN_CHUYEN_NOI_BO );
		
//		cqtList.add( "HAN/VP.HAN");
//		cqtList.add( "HAN/HAN.BDI");
//		cqtList.add( "HAN/HAN.THO");
//		cqtList.add( "HAN/HAN.HKI");
//		cqtList.add( "HAN/HAN.LBI");
//		cqtList.add( "HAN/HAN.HBT");
//		cqtList.add( "HAN/HAN.HMA");
//		cqtList.add( "HAN/HAN.DDA");
//		cqtList.add( "HAN/HAN.TXU");
//		cqtList.add( "HAN/HAN.CGI");
//		cqtList.add( "HAN/HAN.SSO");
//		cqtList.add( "HAN/HAN.DAN");
//		cqtList.add( "HAN/HAN.GLA");
//		cqtList.add( "HAN/HAN.TLI");
//		cqtList.add( "HAN/HAN.TTR");
//		cqtList.add( "HAN/HAN.MLI");
//		cqtList.add( "HAN/HAN.HDO");
//		cqtList.add( "HAN/HAN.STA");
//		cqtList.add( "HAN/HAN.PTH");
//		cqtList.add( "HAN/HAN.DPH");
//		cqtList.add( "HAN/HAN.TTH");
//		cqtList.add( "HAN/HAN.HDU");
//		cqtList.add( "HAN/HAN.QOA");
//		cqtList.add( "HAN/HAN.TOA");
//		cqtList.add( "HAN/HAN.TTI");
//		cqtList.add( "HAN/HAN.MDU");
//		cqtList.add( "HAN/HAN.UHO");
//		cqtList.add( "HAN/HAN.PXU");
//		cqtList.add( "HAN/HAN.BVI");
//		cqtList.add( "HAN/HAN.CMY");
//		cqtList.add( "HAN/HAN.NTL");
//		cqtList.add( "HAN/HAN.BTL");
//		cqtList.add( "HPH/VP.HPH");
//		cqtList.add( "HPH/HPH.HBA");
//		cqtList.add( "HPH/HPH.NQU");
//		cqtList.add( "HPH/HPH.HAN");
//		cqtList.add( "HPH/HPH.LCH");
//		cqtList.add( "HPH/HPH.KAN");
//		cqtList.add( "HPH/HPH.DSO");
//		cqtList.add( "HPH/HPH.TNG");
//		cqtList.add( "HPH/HPH.ADU");
//		cqtList.add( "HPH/HPH.ALA");
//		cqtList.add( "HPH/HPH.KTH");
//		cqtList.add( "HPH/HPH.TLA");
//		cqtList.add( "HPH/HPH.VBA");
//		cqtList.add( "HPH/HPH.CHA");
//		cqtList.add( "HPH/HPH.BLV");
//		cqtList.add( "HPH/HPH.DKI");
//		cqtList.add( "HDU/VP.HDU");
//		cqtList.add( "HDU/HDU.HDU");
//		cqtList.add( "HDU/HDU.CLI");
//		cqtList.add( "HDU/HDU.NSA");
//		cqtList.add( "HDU/HDU.THA");
//		cqtList.add( "HDU/HDU.KMO");
//		cqtList.add( "HDU/HDU.KTH");
//		cqtList.add( "HDU/HDU.GLO");
//		cqtList.add( "HDU/HDU.TKY");
//		cqtList.add( "HDU/HDU.CGI");
//		cqtList.add( "HDU/HDU.BGI");
//		cqtList.add( "HDU/HDU.TMI");
//		cqtList.add( "HDU/HDU.NGI");
//		cqtList.add( "HYE/VP.HYE");
//		cqtList.add( "HYE/HYE.HYE");
//		cqtList.add( "HYE/HYE.MHA");
//		cqtList.add( "HYE/HYE.KCH");
//		cqtList.add( "HYE/HYE.ATH");
//		cqtList.add( "HYE/HYE.KDO");
//		cqtList.add( "HYE/HYE.PCU");
//		cqtList.add( "HYE/HYE.TLU");
//		cqtList.add( "HYE/HYE.VGI");
//		cqtList.add( "HYE/HYE.VLA");
//		cqtList.add( "HYE/HYE.YMY");
//		cqtList.add( "HNA/VP.HNA");
//		cqtList.add( "HNA/HNA.PLY");
//		cqtList.add( "HNA/HNA.DTI");
//		cqtList.add( "HNA/HNA.KBA");
//		cqtList.add( "HNA/HNA.LNH");
//		cqtList.add( "HNA/HNA.TLI");
//		cqtList.add( "HNA/HNA.BLU");
//		cqtList.add( "NDI/VP.NDI");
//		cqtList.add( "NDI/NDI.NDI");
//		cqtList.add( "NDI/NDI.VBA");
//		cqtList.add( "NDI/NDI.MLO");
//		cqtList.add( "NDI/NDI.YYE");
//		cqtList.add( "NDI/NDI.NTR");
//		cqtList.add( "NDI/NDI.TNI");
//		cqtList.add( "NDI/NDI.XTR");
//		cqtList.add( "NDI/NDI.GTH");
//		cqtList.add( "NDI/NDI.NHU");
//		cqtList.add( "NDI/NDI.HHA");
//		cqtList.add( "TBI/VP.TBI");
//		cqtList.add( "TBI/TBI.TBI");
//		cqtList.add( "TBI/TBI.QPH");
//		cqtList.add( "TBI/TBI.HHA");
//		cqtList.add( "TBI/TBI.TTH");
//		cqtList.add( "TBI/TBI.DHU");
//		cqtList.add( "TBI/TBI.VTH");
//		cqtList.add( "TBI/TBI.KXU");
//		cqtList.add( "TBI/TBI.THA");
//		cqtList.add( "NBI/VP.NBI");
//		cqtList.add( "NBI/NBI.NBI");
//		cqtList.add( "NBI/NBI.TDI");
//		cqtList.add( "NBI/NBI.NQU");
//		cqtList.add( "NBI/NBI.GVI");
//		cqtList.add( "NBI/NBI.HLU");
//		cqtList.add( "NBI/NBI.YMO");
//		cqtList.add( "NBI/NBI.YKH");
//		cqtList.add( "NBI/NBI.KSO");
//		cqtList.add( "HGI/VP.HGI");
//		cqtList.add( "HGI/HGI.HGI");
//		cqtList.add( "HGI/HGI.DVA");
//		cqtList.add( "HGI/HGI.MVA");
//		cqtList.add( "HGI/HGI.YMI");
//		cqtList.add( "HGI/HGI.QBA");
//		cqtList.add( "HGI/HGI.BME");
//		cqtList.add( "HGI/HGI.HSP");
//		cqtList.add( "HGI/HGI.VXU");
//		cqtList.add( "HGI/HGI.XMA");
//		cqtList.add( "HGI/HGI.QBI");
//		cqtList.add( "HGI/HGI.BQU");
//		cqtList.add( "CBA/VP.CBA");
//		cqtList.add( "CBA/CBA.CBA");
//		cqtList.add( "CBA/CBA.BLA");
//		cqtList.add( "CBA/CBA.HQU");
//		cqtList.add( "CBA/CBA.TNO");
//		cqtList.add( "CBA/CBA.TLI");
//		cqtList.add( "CBA/CBA.TKH");
//		cqtList.add( "CBA/CBA.NBI");
//		cqtList.add( "CBA/CBA.HAN");
//		cqtList.add( "CBA/CBA.QUY");
//		cqtList.add( "CBA/CBA.PHO");
//		cqtList.add( "CBA/CBA.HLA");
//		cqtList.add( "CBA/CBA.TAN");
//		cqtList.add( "CBA/CBA.BAL");
//		cqtList.add( "LCA/VP.LCA");
//		cqtList.add( "LCA/LCA.LCA");
//		cqtList.add( "LCA/LCA.MKH");
//		cqtList.add( "LCA/LCA.BXA");
//		cqtList.add( "LCA/LCA.BHA");
//		cqtList.add( "LCA/LCA.BTH");
//		cqtList.add( "LCA/LCA.SPA");
//		cqtList.add( "LCA/LCA.BYE");
//		cqtList.add( "LCA/LCA.VBA");
//		cqtList.add( "LCA/LCA.SMC");
//		cqtList.add( "BCA/VP.BCA");
//		cqtList.add( "BCA/BCA.BCA");
//		cqtList.add( "BCA/BCA.BBE");
//		cqtList.add( "BCA/BCA.PNA");
//		cqtList.add( "BCA/BCA.NSO");
//		cqtList.add( "BCA/BCA.CDO");
//		cqtList.add( "BCA/BCA.NRI");
//		cqtList.add( "BCA/BCA.BTH");
//		cqtList.add( "BCA/BCA.CMO");
//		cqtList.add( "LSO/VP.LSO");
//		cqtList.add( "LSO/LSO.LSO");
//		cqtList.add( "LSO/LSO.TDI");
//		cqtList.add( "LSO/LSO.VLA");
//		cqtList.add( "LSO/LSO.BGI");
//		cqtList.add( "LSO/LSO.BSO");
//		cqtList.add( "LSO/LSO.VQU");
//		cqtList.add( "LSO/LSO.CLO");
//		cqtList.add( "LSO/LSO.LBI");
//		cqtList.add( "LSO/LSO.CLA");
//		cqtList.add( "LSO/LSO.DLA");
//		cqtList.add( "LSO/LSO.HLU");
//		cqtList.add( "TQU/VP.TQU");
//		cqtList.add( "TQU/TQU.TQU");
//		cqtList.add( "TQU/TQU.NHA");
//		cqtList.add( "TQU/TQU.CHO");
//		cqtList.add( "TQU/TQU.HYE");
//		cqtList.add( "TQU/TQU.YSO");
//		cqtList.add( "TQU/TQU.SDU");
//		cqtList.add( "TQU/TQU.LBI");
//		cqtList.add( "YBA/VP.YBA");
//		cqtList.add( "YBA/YBA.YBA");
//		cqtList.add( "YBA/YBA.NLO");
//		cqtList.add( "YBA/YBA.LYE");
//		cqtList.add( "YBA/YBA.VYE");
//		cqtList.add( "YBA/YBA.MCC");
//		cqtList.add( "YBA/YBA.TYE");
//		cqtList.add( "YBA/YBA.YBI");
//		cqtList.add( "YBA/YBA.VCH");
//		cqtList.add( "YBA/YBA.TTA");
//		cqtList.add( "TNG/VP.TNG");
//		cqtList.add( "TNG/TNG.TNG");
//		cqtList.add( "TNG/TNG.SCO");
//		cqtList.add( "TNG/TNG.DHO");
//		cqtList.add( "TNG/TNG.VNH");
//		cqtList.add( "TNG/TNG.PLU");
//		cqtList.add( "TNG/TNG.DHY");
//		cqtList.add( "TNG/TNG.DTU");
//		cqtList.add( "TNG/TNG.PBI");
//		cqtList.add( "TNG/TNG.PYE");
//		cqtList.add( "PTH/VP.PTH");
//		cqtList.add( "PTH/PTH.VTR");
//		cqtList.add( "PTH/PTH.PTH");
//		cqtList.add( "PTH/PTH.DHU");
//		cqtList.add( "PTH/PTH.HHO");
//		cqtList.add( "PTH/PTH.TBA");
//		cqtList.add( "PTH/PTH.PNI");
//		cqtList.add( "PTH/PTH.CKH");
//		cqtList.add( "PTH/PTH.YLA");
//		cqtList.add( "PTH/PTH.TNO");
//		cqtList.add( "PTH/PTH.TSO");
//		cqtList.add( "PTH/PTH.TAS");
//		cqtList.add( "PTH/PTH.LTH");
//		cqtList.add( "PTH/PTH.TTH");
//		cqtList.add( "VPH/VP.VPH");
//		cqtList.add( "VPH/VPH.VYE");
//		cqtList.add( "VPH/VPH.PYE");
//		cqtList.add( "VPH/VPH.LTH");
//		cqtList.add( "VPH/VPH.TDA");
//		cqtList.add( "VPH/VPH.TDU");
//		cqtList.add( "VPH/VPH.VTU");
//		cqtList.add( "VPH/VPH.YLA");
//		cqtList.add( "VPH/VPH.BXU");
//		cqtList.add( "VPH/VPH.SLO");
//		cqtList.add( "BGI/VP.BGI");
//		cqtList.add( "BGI/BGI.BGI");
//		cqtList.add( "BGI/BGI.YTH");
//		cqtList.add( "BGI/BGI.TYE");
//		cqtList.add( "BGI/BGI.LNG");
//		cqtList.add( "BGI/BGI.HHO");
//		cqtList.add( "BGI/BGI.LGI");
//		cqtList.add( "BGI/BGI.SDO");
//		cqtList.add( "BGI/BGI.LNA");
//		cqtList.add( "BGI/BGI.VYE");
//		cqtList.add( "BGI/BGI.YDU");
//		cqtList.add( "BNI/VP.BNI");
//		cqtList.add( "BNI/BNI.BNI");
//		cqtList.add( "BNI/BNI.YPH");
//		cqtList.add( "BNI/BNI.QVO");
//		cqtList.add( "BNI/BNI.TDU");
//		cqtList.add( "BNI/BNI.TTH");
//		cqtList.add( "BNI/BNI.LTA");
//		cqtList.add( "BNI/BNI.TSO");
//		cqtList.add( "BNI/BNI.GBI");
//		cqtList.add( "QNI/VP.QNI");
//		cqtList.add( "QNI/QNI.HLO");
//		cqtList.add( "QNI/QNI.CPH");
//		cqtList.add( "QNI/QNI.UBI");
//		cqtList.add( "QNI/QNI.BLI");
//		cqtList.add( "QNI/QNI.MCA");
//		cqtList.add( "QNI/QNI.HHA");
//		cqtList.add( "QNI/QNI.TYE");
//		cqtList.add( "QNI/QNI.BCH");
//		cqtList.add( "QNI/QNI.VDO");
//		cqtList.add( "QNI/QNI.HBO");
//		cqtList.add( "QNI/QNI.DTR");
//		cqtList.add( "QNI/QNI.CTO");
//		cqtList.add( "QNI/QNI.QYE");
//		cqtList.add( "QNI/QNI.DHA");
//		cqtList.add( "DBI/VP.DBI");
//		cqtList.add( "DBI/DBI.DBP");
//		cqtList.add( "DBI/DBI.MLA");
//		cqtList.add( "DBI/DBI.MNH");
//		cqtList.add( "DBI/DBI.MTR");
//		cqtList.add( "DBI/DBI.TCH");
//		cqtList.add( "DBI/DBI.TGI");
//		cqtList.add( "DBI/DBI.DBI");
//		cqtList.add( "DBI/DBI.DBD");
//		cqtList.add( "DBI/DBI.MAN");
//		cqtList.add( "DBI/DBI.NPO");
//		cqtList.add( "LCH/VP.LCH");
//		cqtList.add( "LCH/LCH.MTE");
//		cqtList.add( "LCH/LCH.LCH");
//		cqtList.add( "LCH/LCH.PTH");
//		cqtList.add( "LCH/LCH.TDU");
//		cqtList.add( "LCH/LCH.SHO");
//		cqtList.add( "LCH/LCH.TUY");
//		cqtList.add( "LCH/LCH.TAY");
//		cqtList.add( "LCH/LCH.NNH");
//		cqtList.add( "SLA/VP.SLA");
//		cqtList.add( "SLA/SLA.SLA");
//		cqtList.add( "SLA/SLA.QNH");
//		cqtList.add( "SLA/SLA.MLA");
//		cqtList.add( "SLA/SLA.TCH");
//		cqtList.add( "SLA/SLA.BYE");
//		cqtList.add( "SLA/SLA.PYE");
//		cqtList.add( "SLA/SLA.MSO");
//		cqtList.add( "SLA/SLA.SMA");
//		cqtList.add( "SLA/SLA.YCH");
//		cqtList.add( "SLA/SLA.MCH");
//		cqtList.add( "SLA/SLA.SCO");
//		cqtList.add( "SLA/SLA.VHO");
//		cqtList.add( "HBI/VP.HBI");
//		cqtList.add( "HBI/HBI.HBI");
//		cqtList.add( "HBI/HBI.DBA");
//		cqtList.add( "HBI/HBI.MCH");
//		cqtList.add( "HBI/HBI.KSO");
//		cqtList.add( "HBI/HBI.LSO");
//		cqtList.add( "HBI/HBI.CPH");
//		cqtList.add( "HBI/HBI.KBO");
//		cqtList.add( "HBI/HBI.TLA");
//		cqtList.add( "HBI/HBI.LAS");
//		cqtList.add( "HBI/HBI.LTH");
//		cqtList.add( "HBI/HBI.YTH");
//		cqtList.add( "THO/VP.THO");
//		cqtList.add( "THO/THO.THO");
//		cqtList.add( "THO/THO.BSO");
//		cqtList.add( "THO/THO.SSO");
//		cqtList.add( "THO/THO.MLA");
//		cqtList.add( "THO/THO.QHO");
//		cqtList.add( "THO/THO.QSO");
//		cqtList.add( "THO/THO.BTH");
//		cqtList.add( "THO/THO.CTH");
//		cqtList.add( "THO/THO.LCH");
//		cqtList.add( "THO/THO.TTH");
//		cqtList.add( "THO/THO.NLA");
//		cqtList.add( "THO/THO.TXU");
//		cqtList.add( "THO/THO.NXU");
//		cqtList.add( "THO/THO.NTH");
//		cqtList.add( "THO/THO.VLO");
//		cqtList.add( "THO/THO.HTR");
//		cqtList.add( "THO/THO.NSO");
//		cqtList.add( "THO/THO.YDI");
//		cqtList.add( "THO/THO.THX");
//		cqtList.add( "THO/THO.HLO");
//		cqtList.add( "THO/THO.THA");
//		cqtList.add( "THO/THO.HHO");
//		cqtList.add( "THO/THO.DSO");
//		cqtList.add( "THO/THO.TSO");
//		cqtList.add( "THO/THO.QXU");
//		cqtList.add( "THO/THO.NCO");
//		cqtList.add( "THO/THO.TGI");
//		cqtList.add( "NAN/VP.NAN");
//		cqtList.add( "NAN/NAN.VIN");
//		cqtList.add( "NAN/NAN.CLO");
//		cqtList.add( "NAN/NAN.QPH");
//		cqtList.add( "NAN/NAN.QCH");
//		cqtList.add( "NAN/NAN.KSO");
//		cqtList.add( "NAN/NAN.QHO");
//		cqtList.add( "NAN/NAN.NDA");
//		cqtList.add( "NAN/NAN.THO");
//		cqtList.add( "NAN/NAN.TDU");
//		cqtList.add( "NAN/NAN.QLU");
//		cqtList.add( "NAN/NAN.TKY");
//		cqtList.add( "NAN/NAN.CCU");
//		cqtList.add( "NAN/NAN.YTH");
//		cqtList.add( "NAN/NAN.DCH");
//		cqtList.add( "NAN/NAN.ASO");
//		cqtList.add( "NAN/NAN.DLU");
//		cqtList.add( "NAN/NAN.TCH");
//		cqtList.add( "NAN/NAN.NLO");
//		cqtList.add( "NAN/NAN.NAD");
//		cqtList.add( "NAN/NAN.HNG");
//		cqtList.add( "NAN/NAN.HMA");
//		cqtList.add( "HTI/VP.HTI");
//		cqtList.add( "HTI/HTI.HTI");
//		cqtList.add( "HTI/HTI.HLI");
//		cqtList.add( "HTI/HTI.NXU");
//		cqtList.add( "HTI/HTI.DTH");
//		cqtList.add( "HTI/HTI.HSO");
//		cqtList.add( "HTI/HTI.CLO");
//		cqtList.add( "HTI/HTI.THA");
//		cqtList.add( "HTI/HTI.CXU");
//		cqtList.add( "HTI/HTI.HKH");
//		cqtList.add( "HTI/HTI.KAN");
//		cqtList.add( "HTI/HTI.KYA");
//		cqtList.add( "HTI/HTI.VQU");
//		cqtList.add( "HTI/HTI.LHA");
//		cqtList.add( "QBI/VP.QBI");
//		cqtList.add( "QBI/QBI.DHO");
//		cqtList.add( "QBI/QBI.THO");
//		cqtList.add( "QBI/QBI.MHO");
//		cqtList.add( "QBI/QBI.QTR");
//		cqtList.add( "QBI/QBI.BTR");
//		cqtList.add( "QBI/QBI.QNI");
//		cqtList.add( "QBI/QBI.LTH");
//		cqtList.add( "QBI/QBI.BDO");
//		cqtList.add( "QTR/VP.QTR");
//		cqtList.add( "QTR/QTR.DHA");
//		cqtList.add( "QTR/QTR.QTR");
//		cqtList.add( "QTR/QTR.VLI");
//		cqtList.add( "QTR/QTR.GLI");
//		cqtList.add( "QTR/QTR.CLO");
//		cqtList.add( "QTR/QTR.TPH");
//		cqtList.add( "QTR/QTR.HLA");
//		cqtList.add( "QTR/QTR.HHO");
//		cqtList.add( "QTR/QTR.DKR");
//		cqtList.add( "QTR/QTR.CCO");
//		cqtList.add( "TTH/VP.TTH");
//		cqtList.add( "TTH/TTH.HUE");
//		cqtList.add( "TTH/TTH.PDI");
//		cqtList.add( "TTH/TTH.QDI");
//		cqtList.add( "TTH/TTH.HTR");
//		cqtList.add( "TTH/TTH.PVA");
//		cqtList.add( "TTH/TTH.HTH");
//		cqtList.add( "TTH/TTH.PLO");
//		cqtList.add( "TTH/TTH.ALU");
//		cqtList.add( "TTH/TTH.NDO");
//		cqtList.add( "DAN/VP.DAN");
//		cqtList.add( "DAN/DAN.HCH");
//		cqtList.add( "DAN/DAN.TKH");
//		cqtList.add( "DAN/DAN.STR");
//		cqtList.add( "DAN/DAN.NHS");
//		cqtList.add( "DAN/DAN.LCH");
//		cqtList.add( "DAN/DAN.HVA");
//		cqtList.add( "DAN/DAN.HSA");
//		cqtList.add( "DAN/DAN.CLE");
//		cqtList.add( "QNA/VP.QNA");
//		cqtList.add( "QNA/QNA.TKY");
//		cqtList.add( "QNA/QNA.PNI");
//		cqtList.add( "QNA/QNA.HAN");
//		cqtList.add( "QNA/QNA.TGI");
//		cqtList.add( "QNA/QNA.DGI");
//		cqtList.add( "QNA/QNA.DLO");
//		cqtList.add( "QNA/QNA.DBA");
//		cqtList.add( "QNA/QNA.DXU");
//		cqtList.add( "QNA/QNA.NGI");
//		cqtList.add( "QNA/QNA.TBI");
//		cqtList.add( "QNA/QNA.QSO");
//		cqtList.add( "QNA/QNA.NSO");
//		cqtList.add( "QNA/QNA.HDU");
//		cqtList.add( "QNA/QNA.TPH");
//		cqtList.add( "QNA/QNA.PSO");
//		cqtList.add( "QNA/QNA.NTH");
//		cqtList.add( "QNA/QNA.BTM");
//		cqtList.add( "QNA/QNA.NTM");
//		cqtList.add( "QNG/VP.QNG");
//		cqtList.add( "QNG/QNG.QNG");
//		cqtList.add( "QNG/QNG.LSO");
//		cqtList.add( "QNG/QNG.BSO");
//		cqtList.add( "QNG/QNG.TBO");
//		cqtList.add( "QNG/QNG.TTR");
//		cqtList.add( "QNG/QNG.STI");
//		cqtList.add( "QNG/QNG.STA");
//		cqtList.add( "QNG/QNG.SHA");
//		cqtList.add( "QNG/QNG.TNG");
//		cqtList.add( "QNG/QNG.NHA");
//		cqtList.add( "QNG/QNG.MLO");
//		cqtList.add( "QNG/QNG.MDU");
//		cqtList.add( "QNG/QNG.DPH");
//		cqtList.add( "QNG/QNG.BTO");
//		cqtList.add( "BDI/VP.BDI");
//		cqtList.add( "BDI/BDI.QNH");
//		cqtList.add( "BDI/BDI.ALA");
//		cqtList.add( "BDI/BDI.HNH");
//		cqtList.add( "BDI/BDI.HAN");
//		cqtList.add( "BDI/BDI.PMY");
//		cqtList.add( "BDI/BDI.VTH");
//		cqtList.add( "BDI/BDI.PCA");
//		cqtList.add( "BDI/BDI.TSO");
//		cqtList.add( "BDI/BDI.ANH");
//		cqtList.add( "BDI/BDI.TPH");
//		cqtList.add( "BDI/BDI.VCA");
//		cqtList.add( "PHY/VP.PHY");
//		cqtList.add( "PHY/PHY.THO");
//		cqtList.add( "PHY/PHY.DXU");
//		cqtList.add( "PHY/PHY.SCA");
//		cqtList.add( "PHY/PHY.TAN");
//		cqtList.add( "PHY/PHY.SHO");
//		cqtList.add( "PHY/PHY.DHO");
//		cqtList.add( "PHY/PHY.TAH");
//		cqtList.add( "PHY/PHY.SHI");
//		cqtList.add( "PHY/PHY.PHO");
//		cqtList.add( "KHH/VP.KHH");
//		cqtList.add( "KHH/KHH.NTR");
//		cqtList.add( "KHH/KHH.VNI");
//		cqtList.add( "KHH/KHH.NHO");
//		cqtList.add( "KHH/KHH.DKH");
//		cqtList.add( "KHH/KHH.CRA");
//		cqtList.add( "KHH/KHH.KVI");
//		cqtList.add( "KHH/KHH.KSO");
//		cqtList.add( "KHH/KHH.TSA");
//		cqtList.add( "KHH/KHH.CLA");
//		cqtList.add( "KTU/VP.KTU");
//		cqtList.add( "KTU/KTU.KTU");
//		cqtList.add( "KTU/KTU.DGL");
//		cqtList.add( "KTU/KTU.NHO");
//		cqtList.add( "KTU/KTU.DTO");
//		cqtList.add( "KTU/KTU.KRA");
//		cqtList.add( "KTU/KTU.KPL");
//		cqtList.add( "KTU/KTU.DHA");
//		cqtList.add( "KTU/KTU.STH");
//		cqtList.add( "KTU/KTU.IHD");
//		cqtList.add( "KTU/KTU.TMR");
//		cqtList.add( "GLA/VP.GLA");
//		cqtList.add( "GLA/GLA.PLE");
//		cqtList.add( "GLA/GLA.KBA");
//		cqtList.add( "GLA/GLA.MYA");
//		cqtList.add( "GLA/GLA.CPA");
//		cqtList.add( "GLA/GLA.IGR");
//		cqtList.add( "GLA/GLA.AKH");
//		cqtList.add( "GLA/GLA.KCH");
//		cqtList.add( "GLA/GLA.DCO");
//		cqtList.add( "GLA/GLA.CPR");
//		cqtList.add( "GLA/GLA.CSE");
//		cqtList.add( "GLA/GLA.IPA");
//		cqtList.add( "GLA/GLA.APA");
//		cqtList.add( "GLA/GLA.KPA");
//		cqtList.add( "GLA/GLA.DDO");
//		cqtList.add( "GLA/GLA.DPO");
//		cqtList.add( "GLA/GLA.PTH");
//		cqtList.add( "GLA/GLA.CPU");
//		cqtList.add( "DLA/VP.DLA");
//		cqtList.add( "DLA/DLA.BMT");
//		cqtList.add( "DLA/DLA.EHL");
//		cqtList.add( "DLA/DLA.ESU");
//		cqtList.add( "DLA/DLA.KNA");
//		cqtList.add( "DLA/DLA.BHO");
//		cqtList.add( "DLA/DLA.BDO");
//		cqtList.add( "DLA/DLA.CMG");
//		cqtList.add( "DLA/DLA.EKA");
//		cqtList.add( "DLA/DLA.MDR");
//		cqtList.add( "DLA/DLA.KPA");
//		cqtList.add( "DLA/DLA.KAN");
//		cqtList.add( "DLA/DLA.KBO");
//		cqtList.add( "DLA/DLA.LAK");
//		cqtList.add( "DLA/DLA.CKU");
//		cqtList.add( "DLA/DLA.KBU");
//		cqtList.add( "DNO/VP.DNO");
//		cqtList.add( "DNO/DNO.CJU");
//		cqtList.add( "DNO/DNO.KNO");
//		cqtList.add( "DNO/DNO.DMI");
//		cqtList.add( "DNO/DNO.DSO");
//		cqtList.add( "DNO/DNO.DRL");
//		cqtList.add( "DNO/DNO.GNG");
//		cqtList.add( "DNO/DNO.GLO");
//		cqtList.add( "DNO/DNO.TDU");
//		cqtList.add( "HCM/VP.HCM");
//		cqtList.add( "HCM/HCM.QMO");
//		cqtList.add( "HCM/HCM.QHA");
//		cqtList.add( "HCM/HCM.QBA");
//		cqtList.add( "HCM/HCM.QBO");
//		cqtList.add( "HCM/HCM.QNA");
//		cqtList.add( "HCM/HCM.QSA");
//		cqtList.add( "HCM/HCM.QUB");
//		cqtList.add( "HCM/HCM.QTA");
//		cqtList.add( "HCM/HCM.QCH");
//		cqtList.add( "HCM/HCM.QMU");
//		cqtList.add( "HCM/HCM.QMM");
//		cqtList.add( "HCM/HCM.QMH");
//		cqtList.add( "HCM/HCM.GVA");
//		cqtList.add( "HCM/HCM.TBI");
//		cqtList.add( "HCM/HCM.TPH");
//		cqtList.add( "HCM/HCM.BTH");
//		cqtList.add( "HCM/HCM.PNH");
//		cqtList.add( "HCM/HCM.TDU");
//		cqtList.add( "HCM/HCM.BTA");
//		cqtList.add( "HCM/HCM.CCH");
//		cqtList.add( "HCM/HCM.HMO");
//		cqtList.add( "HCM/HCM.BCH");
//		cqtList.add( "HCM/HCM.NBE");
//		cqtList.add( "HCM/HCM.CGI");
//		cqtList.add( "HCM/HCM.TPTDU");
//		
//		cqtList.add( "LDO/VP.LDO");
//		cqtList.add( "LDO/LDO.DLA");
//		cqtList.add( "LDO/LDO.BLO");
//		cqtList.add( "LDO/LDO.LDU");
//		cqtList.add( "LDO/LDO.DDU");
//		cqtList.add( "LDO/LDO.DTR");
//		cqtList.add( "LDO/LDO.LHA");
//		cqtList.add( "LDO/LDO.BLA");
//		cqtList.add( "LDO/LDO.DLI");
//		cqtList.add( "LDO/LDO.DHU");
//		cqtList.add( "LDO/LDO.DTE");
//		cqtList.add( "LDO/LDO.CTI");
//		cqtList.add( "LDO/LDO.DRO");
//		cqtList.add( "NTH/VP.NTH");
//		cqtList.add( "NTH/NTH.PRA");
//		cqtList.add( "NTH/NTH.NSO");
//		cqtList.add( "NTH/NTH.NHA");
//		cqtList.add( "NTH/NTH.NPH");
//		cqtList.add( "NTH/NTH.BAI");
//		cqtList.add( "NTH/NTH.TBA");
//		cqtList.add( "NTH/NTH.TNA");
//		cqtList.add( "BPH/VP.BPH");
//		cqtList.add( "BPH/BPH.DPH");
//		cqtList.add( "BPH/BPH.PLO");
//		cqtList.add( "BPH/BPH.LNI");
//		cqtList.add( "BPH/BPH.BDO");
//		cqtList.add( "BPH/BPH.BDA");
//		cqtList.add( "BPH/BPH.BLO");
//		cqtList.add( "BPH/BPH.CHO");
//		cqtList.add( "BPH/BPH.DXO");
//		cqtList.add( "BPH/BPH.HQU");
//		cqtList.add( "BPH/BPH.BGM");
//		cqtList.add( "BPH/BPH.PRI");
//		cqtList.add( "TNI/VP.TNI");
//		cqtList.add( "TNI/TNI.TNI");
//		cqtList.add( "TNI/TNI.TBI");
//		cqtList.add( "TNI/TNI.TCH");
//		cqtList.add( "TNI/TNI.DMC");
//		cqtList.add( "TNI/TNI.CTH");
//		cqtList.add( "TNI/TNI.HTH");
//		cqtList.add( "TNI/TNI.BCA");
//		cqtList.add( "TNI/TNI.GDA");
//		cqtList.add( "TNI/TNI.TBA");
//		cqtList.add( "BDU/VP.BDU");
//		cqtList.add( "BDU/BDU.TDM");
//		cqtList.add( "BDU/BDU.BCA");
//		cqtList.add( "BDU/BDU.TUY");
//		cqtList.add( "BDU/BDU.TAN");
//		cqtList.add( "BDU/BDU.DAN");
//		cqtList.add( "BDU/BDU.PGI");
//		cqtList.add( "BDU/BDU.DTI");
//		cqtList.add( "BDU/BDU.BBA");
//		cqtList.add( "BDU/BDU.BTU");
//		cqtList.add( "DON/VP.DON");
//		cqtList.add( "DON/DON.BHO");
//		cqtList.add( "DON/DON.LKH");
//		cqtList.add( "DON/DON.TPH");
//		cqtList.add( "DON/DON.DQU");
//		cqtList.add( "DON/DON.VCU");
//		cqtList.add( "DON/DON.TBO");
//		cqtList.add( "DON/DON.TNH");
//		cqtList.add( "DON/DON.CMY");
//		cqtList.add( "DON/DON.XLO");
//		cqtList.add( "DON/DON.LTH");
//		cqtList.add( "DON/DON.NTR");
//		cqtList.add( "BTH/VP.BTH");
//		cqtList.add( "BTH/BTH.PTH");
//		cqtList.add( "BTH/BTH.TPH");
//		cqtList.add( "BTH/BTH.BBI");
//		cqtList.add( "BTH/BTH.HTB");
//		cqtList.add( "BTH/BTH.HTN");
//		cqtList.add( "BTH/BTH.TLI");
//		cqtList.add( "BTH/BTH.LGI");
//		cqtList.add( "BTH/BTH.HTA");
//		cqtList.add( "BTH/BTH.DLI");
//		cqtList.add( "BTH/BTH.PQU");
//		cqtList.add( "BRV/VP.BRV");
//		cqtList.add( "BRV/BRV.VTA");
//		cqtList.add( "BRV/BRV.BRI");
//		cqtList.add( "BRV/BRV.CDU");
//		cqtList.add( "BRV/BRV.XMO");
//		cqtList.add( "BRV/BRV.PMY");
//		cqtList.add( "BRV/BRV.LDI");
//		cqtList.add( "BRV/BRV.DDO");
//		cqtList.add( "BRV/BRV.CDA");
//		cqtList.add( "LAN/VP.LAN");
//		cqtList.add( "LAN/LAN.TAN");
//		cqtList.add( "LAN/LAN.THU");
//		cqtList.add( "LAN/LAN.VHU");
//		cqtList.add( "LAN/LAN.MHO");
//		cqtList.add( "LAN/LAN.TTH");
//		cqtList.add( "LAN/LAN.THO");
//		cqtList.add( "LAN/LAN.DHU");
//		cqtList.add( "LAN/LAN.DHO");
//		cqtList.add( "LAN/LAN.BLU");
//		cqtList.add( "LAN/LAN.THT");
//		cqtList.add( "LAN/LAN.CTH");
//		cqtList.add( "LAN/LAN.TTR");
//		cqtList.add( "LAN/LAN.CDU");
//		cqtList.add( "LAN/LAN.CGI");
//		cqtList.add( "LAN/LAN.KTU");
//		cqtList.add( "DTH/VP.DTH");
//		cqtList.add( "DTH/DTH.CLA");
//		cqtList.add( "DTH/DTH.SDE");
//		cqtList.add( "DTH/DTH.THO");
//		cqtList.add( "DTH/DTH.HNG");
//		cqtList.add( "DTH/DTH.TNO");
//		cqtList.add( "DTH/DTH.TBI");
//		cqtList.add( "DTH/DTH.TMU");
//		cqtList.add( "DTH/DTH.CAL");
//		cqtList.add( "DTH/DTH.LVO");
//		cqtList.add( "DTH/DTH.LVU");
//		cqtList.add( "DTH/DTH.CTH");
//		cqtList.add( "DTH/DTH.HNU");
//		cqtList.add( "AGI/VP.AGI");
//		cqtList.add( "AGI/AGI.LXU");
//		cqtList.add( "AGI/AGI.CDO");
//		cqtList.add( "AGI/AGI.APH");
//		cqtList.add( "AGI/AGI.TCH");
//		cqtList.add( "AGI/AGI.PTA");
//		cqtList.add( "AGI/AGI.CPH");
//		cqtList.add( "AGI/AGI.TBI");
//		cqtList.add( "AGI/AGI.TTO");
//		cqtList.add( "AGI/AGI.CMO");
//		cqtList.add( "AGI/AGI.CTH");
//		cqtList.add( "AGI/AGI.TSO");
//		cqtList.add( "TGI/VP.TGI");
//		cqtList.add( "TGI/TGI.MTH");
//		cqtList.add( "TGI/TGI.GCO");
//		cqtList.add( "TGI/TGI.TPH");
//		cqtList.add( "TGI/TGI.CTH");
//		cqtList.add( "TGI/TGI.CLA");
//		cqtList.add( "TGI/TGI.CGO");
//		cqtList.add( "TGI/TGI.CBE");
//		cqtList.add( "TGI/TGI.GCT");
//		cqtList.add( "TGI/TGI.GCD");
//		cqtList.add( "TGI/TGI.TPD");
//		cqtList.add( "TGI/TGI.CAL");
//		cqtList.add( "VLO/VP.VLO");
//		cqtList.add( "VLO/VLO.VLO");
//		cqtList.add( "VLO/VLO.LHO");
//		cqtList.add( "VLO/VLO.MTH");
//		cqtList.add( "VLO/VLO.BMI");
//		cqtList.add( "VLO/VLO.BTA");
//		cqtList.add( "VLO/VLO.TBI");
//		cqtList.add( "VLO/VLO.TON");
//		cqtList.add( "VLO/VLO.VLI");
//		cqtList.add( "BTR/VP.BTR");
//		cqtList.add( "BTR/BTR.BTR");
//		cqtList.add( "BTR/BTR.CTH");
//		cqtList.add( "BTR/BTR.CLA");
//		cqtList.add( "BTR/BTR.MCA");
//		cqtList.add( "BTR/BTR.MCB");
//		cqtList.add( "BTR/BTR.GTR");
//		cqtList.add( "BTR/BTR.BDA");
//		cqtList.add( "BTR/BTR.BTI");
//		cqtList.add( "BTR/BTR.TPH");
//		cqtList.add( "KGI/VP.KGI");
//		cqtList.add( "KGI/KGI.RGI");
//		cqtList.add( "KGI/KGI.KLU");
//		cqtList.add( "KGI/KGI.GTH");
//		cqtList.add( "KGI/KGI.HDA");
//		cqtList.add( "KGI/KGI.THI");
//		cqtList.add( "KGI/KGI.CTH");
//		cqtList.add( "KGI/KGI.GRI");
//		cqtList.add( "KGI/KGI.GQU");
//		cqtList.add( "KGI/KGI.ABI");
//		cqtList.add( "KGI/KGI.AMI");
//		cqtList.add( "KGI/KGI.VTH");
//		cqtList.add( "KGI/KGI.PQU");
//		cqtList.add( "KGI/KGI.KHA");
//		cqtList.add( "KGI/KGI.HTI");
//		cqtList.add( "KGI/KGI.UMT");
//		cqtList.add( "CTH/VP.CTH");
//		cqtList.add( "CTH/CTH.TNO");
//		cqtList.add( "CTH/CTH.OMO");
//		cqtList.add( "CTH/CTH.NKI");
//		cqtList.add( "CTH/CTH.BTH");
//		cqtList.add( "CTH/CTH.CRA");
//		cqtList.add( "CTH/CTH.VTH");
//		cqtList.add( "CTH/CTH.CDO");
//		cqtList.add( "CTH/CTH.PDI");
//		cqtList.add( "CTH/CTH.TLA");
//		cqtList.add( "HAG/VP.HAG");
//		cqtList.add( "HAG/HAG.VTH");
//		cqtList.add( "HAG/HAG.CTA");
//		cqtList.add( "HAG/HAG.CTH");
//		cqtList.add( "HAG/HAG.NBA");
//		cqtList.add( "HAG/HAG.PHI");
//		cqtList.add( "HAG/HAG.VIT");
//		cqtList.add( "HAG/HAG.LMY");
//		cqtList.add( "HAG/HAG.LOM");
//		cqtList.add( "TVI/VP.TVI");
//		cqtList.add( "TVI/TVI.TVI");
//		cqtList.add( "TVI/TVI.CLO");
//		cqtList.add( "TVI/TVI.CTH");
//		cqtList.add( "TVI/TVI.CKE");
//		cqtList.add( "TVI/TVI.TCA");
//		cqtList.add( "TVI/TVI.CNG");
//		cqtList.add( "TVI/TVI.TCU");
//		cqtList.add( "TVI/TVI.DHA");
//		cqtList.add( "TVI/TVI.DUH");
//		cqtList.add( "STR/VP.STR");
//		cqtList.add( "STR/STR.STR");
//		cqtList.add( "STR/STR.KSA");
//		cqtList.add( "STR/STR.LPH");
//		cqtList.add( "STR/STR.CLD");
//		cqtList.add( "STR/STR.MTU");
//		cqtList.add( "STR/STR.MXU");
//		cqtList.add( "STR/STR.TTR");
//		cqtList.add( "STR/STR.NNA");
//		cqtList.add( "STR/STR.VCH");
//		cqtList.add( "STR/STR.CTH");
//		cqtList.add( "STR/STR.TDE");
//		cqtList.add( "BLI/VP.BLI");
//		cqtList.add( "BLI/BLI.BLI");
//		cqtList.add( "BLI/BLI.HDA");
//		cqtList.add( "BLI/BLI.VLO");
//		cqtList.add( "BLI/BLI.HBI");
//		cqtList.add( "BLI/BLI.GRA");
//		cqtList.add( "BLI/BLI.PLO");
//		cqtList.add( "BLI/BLI.DHA");
//		cqtList.add( "CMA/VP.CMA");
//		cqtList.add( "CMA/CMA.CMA");
//		cqtList.add( "CMA/CMA.TBI");
//		cqtList.add( "CMA/CMA.UMI");
//		cqtList.add( "CMA/CMA.TVT");
//		cqtList.add( "CMA/CMA.PTA");
//		cqtList.add( "CMA/CMA.CNU");
//		cqtList.add( "CMA/CMA.DRO");
//		cqtList.add( "CMA/CMA.NCA");
//		cqtList.add( "CMA/CMA.NHI");
		
		cqtList.add( "HCM/VP.HCM.TPTDU");
		cqtList.add( "DNL/VP.DNL");

		String tct = mappingPath2.TCT;
		for(int i = 0; i < cqtList.size(); i++){
			
			if(cqtList.get(i).contains("/")){
				for(int j = 0; j < hsList.size(); j++){
					if(hsList.get(j).contains("/")){
						String pathHoSo = "/" + tct + "/" + cqtList.get(i) + "/" + hsList.get(j);
//						String path2009 = pathHoSo + "/2009";
//						String path2010 = pathHoSo + "/2010";
//						String path2011 = pathHoSo + "/2011";
//						String path2012 = pathHoSo + "/2012";
//						String path2013 = pathHoSo + "/2013";
//						String path2014 = pathHoSo + "/2014";
//						String path2015 = pathHoSo + "/2015";
//						String path2016 = pathHoSo + "/2016";
//						String path2017 = pathHoSo + "/2017";
//						String path2018 = pathHoSo + "/2018";
//						String path2019 = pathHoSo + "/2019";
//						String path2020 = pathHoSo + "/2020";
						String path2021 = pathHoSo + "/2021";
						String path2022 = pathHoSo + "/2022";
						String path2023 = pathHoSo + "/2023";
						String path2024 = pathHoSo + "/2024";
						String path2025 = pathHoSo + "/2025";
						String path2026 = pathHoSo + "/2026";
						String path2027 = pathHoSo + "/2027";
						String path2028 = pathHoSo + "/2028";
						String path2029 = pathHoSo + "/2029";
//						p8.createFolder(path2009);
//						p8.createFolder(path2010);
//						p8.createFolder(path2011);
//						p8.createFolder(path2012);
//						p8.createFolder(path2013);
//						p8.createFolder(path2014);
//						p8.createFolder(path2015);
//						p8.createFolder(path2016);
//						p8.createFolder(path2017);
//						p8.createFolder(path2018);
//						p8.createFolder(path2019);
//						p8.createFolder(path2020);
						p8.createFolder(path2021);
						p8.createFolder(path2022);
						p8.createFolder(path2023);
						p8.createFolder(path2024);
						p8.createFolder(path2025);
						p8.createFolder(path2026);
						p8.createFolder(path2027);
						p8.createFolder(path2028);
						p8.createFolder(path2029);
					}else{
						String pathNghiepVu = "/" + tct + "/" + cqtList.get(i) + "/" + hsList.get(j);
						p8.createFolder(pathNghiepVu);
					}
				}
			}else{				
				p8.createFolder("/" + tct + "/" + cqtList.get(i) + "/");
			}
		}
			System.out.println("-------------------CREATE XONG-----------------------");
			
			
//			String tct = mappingPath2.TCT;
//			
//			for(int i = 0; i < cqtList.size(); i++){
//				
//				if(cqtList.get(i).contains("/")){
//					for(int j = 0; j < hsList.size(); j++){
//						if(hsList.get(j).contains("/")){
//							String pathHoSo = "/" + tct + "/" + cqtList.get(i) + "/" + hsList.get(j);
//							String path2016 = pathHoSo + "/2017";
//							String path2017 = pathHoSo + "/2018";
//							p8.deleteFolderByPath( path2016, p8.getObjectStore());
//							p8.deleteFolderByPath(path2017, p8.getObjectStore());
//						}else{
//							String pathNghiepVu = "/" + tct + "/" + cqtList.get(i) + "/" + hsList.get(j);
//							//p8.deleteFolderByPath(pathNghiepVu, p8.getObjectStore());
//						}
//					}
//				}else{				
//					//p8.deleteFolderByPath("/" + tct + "/" + cqtList.get(i) + "/");
//				}
//			}
//				System.out.println("-------------------DELETE XONG-----------------------");
			
//			p8.getLastPathsForDelete(null, "/TCT", "/TCT", p8.getObjectStore(), true);
			
//		
//			
//		
//		for(Map.Entry<String, String> cqt : mappingPath.CQT.CQT_MAP.entrySet()){
//			for(Map.Entry<String, String> hs : mappingPath.HS.HS_MAP.entrySet()){
//				String pathCreate = "/"+tct+"/"+cqt.getValue()+"/"+hs.getValue()+"/";
//				p8.createFolder(pathCreate);
//			}
//			
//		}
		
//		com.filenet.api.core.Folder folderParent = p8.getObjectStore().get_RootFolder();
//		com.filenet.api.core.Folder folderParent = Factory.Folder.fetchInstance(p8.getObjectStore(), "/ETAX/TIN_TUC/", null);
//		DocumentSet ds = folderParent.get_ContainedDocuments();
//		ArrayList<String> listDocClass = new ArrayList<String>();
//		Iterator it = ds.iterator();
//		while(it.hasNext()){
//			Document doc = (Document) it.next();
//			listDocClass.add(doc.getClassName());
//			FolderSet fs = doc.get_FoldersFiledIn();
//			Iterator itfs = fs.iterator();
//
//			com.filenet.api.core.Folder fo = (com.filenet.api.core.Folder) fs.iterator().next();
//			fo.get_PathName();
//			String sf = ((com.filenet.api.core.Folder) fs.iterator().next()).get_PathName();
//			while(itfs.hasNext()){
//				com.filenet.api.core.Folder fol = (com.filenet.api.core.Folder) itfs.next();
//				fol.get_PathName();
//			}
//			
//		}
//		for(int i =0; i < listDocClass.size();i++){
//			System.out.println(listDocClass.get(i));
//		}

//		ArrayList<String> listPath = new ArrayList<String>();
//		listPath = p8.getLastPathsForDelete(listPath, "/Hà Nội", "/Hà Nội", p8.getObjectStore(), true);
//		for (int i = 0; i < listPath.size(); i++) {
//			System.out.println(listPath.get(i));
//		}

		// 1.Put tai lieu vao ECM

//		 Path path = Paths.get("D:/hoannc/Project/ICS_ Hoa don dien tu/XSD/XSD_2407/e01GTKT.xml");
//		 byte[] data = Files.readAllBytes(path);
//		
//		 System.out.println("Put tai lieu vao ECM ");
//		 HashMap<String, String> putDocHashMapKey = new HashMap<String,
//		 String>();
//		 putDocHashMapKey.put("DocumentTitle", "testHDDT.xml");
//		 putDocHashMapKey.put("DonVi", "10100");
//		 putDocHashMapKey.put("LoaiHSo", HOA_DON_DIEN_TU.GTKT);
//		// putDocHashMapKey.put("MST", "123456");
//		// p8.setEcmDocClass(DocClass.ETAX.DANG_KY);
//		// String retDocumentID = p8.putDocFileStorage(putDocHashMapKey, data,		 "ecm_object_store", "ecm_object_store_sp");
//		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		 String sDate = sdf.format(new Date());
//		 p8.setEcmDocClass(DocClass.HDON_DTU.HDDT);
//		 String retDocumentID = p8.putDocument(putDocHashMapKey, sDate , data);
//		 System.out.println(retDocumentID);
//		//
//		 System.out.println("================================== ");
		// String docDelId = null;
		//
		//// 2.Search danh sach tai lieu co MaNNT=12345
		// System.out.println("Search danh sach tai lieu");
		// HashMap<String, String>searchDocHashMapKey = new
		// HashMap<String,String>();
		//// searchDocHashMapKey.put("DocumentTitle", "file");
		//// searchDocHashMapKey.put("NgaySuaTuNgay", "02/04/2016");
		//// searchDocHashMapKey.put("NgaySuaDenNgay", "05/04/2016");
		// //hoac tim theo ID
		// searchDocHashMapKey.put("ID",
		// "{B09D7A57-0000-C915-BDB3-783EEAA54860}");
		// ArrayList<String> arrKeys=p8.searchDocumentKeys(searchDocHashMapKey);
		//// ArrayList<String>
		// arrKeys=p8.searchAllVersionDocumentKeys(searchDocHashMapKey);
		////
		// for (int i=0;i<arrKeys.size();i++)
		// {
		// System.out.println("File ID:"+arrKeys.get(i)
		// .toString());
		// }
		// System.out.println("================================== ");

		// ----------------- Search all document by class Document
		// --------------------
		// System.out.println("Search danh sach tai lieu");
		// ArrayList<String> arrKeys = p8.searchAllDocumentIDByClass();
		//
		// for (int i = 0; i < arrKeys.size(); i++) {
		// System.out.println("File ID:" + arrKeys.get(i)
		// .toString());
		//
		// }
		// System.out.println("================================== ");

		// 3.Get Metadata cua tai lieu
		// System.out.println("Get Metadata cua tai lieu ");
		// HashMap<String, String> metaDocHashMapKey =
		// p8.getDocumentMetaData("{40995154-0000-CB11-B01C-797E39B345A1}");
		// for (String key : metaDocHashMapKey.keySet()) {
		// System.out.println(key + ":" + metaDocHashMapKey.get(key));
		// }
		// System.out.println("================================== ");
		//
		// 4.Get tai lieu
//		 System.out.println("Get tai lieu");
//		 byte[] docBytes =
//		 p8.getDocumentContent("{40D9785D-0000-C71E-A918-547F71BEC419}");
//		 // Ghi ra file
//		 if (docBytes != null) {
//		 System.out.print("");
//		 FileOutputStream fos = new FileOutputStream("e:\\replace3333.xml");
//		 fos.write(docBytes);
//		 fos.close();
//		 }
//		 System.out.println("================================== ");
		// 5.Replace tai lieu
		// File file = new File("D:\\change.xml");
		// System.out.println(p8.addVersion("{B0F24C54-0000-C414-94E8-7455D5AF2F7A}",
		// file, true));
		// // 6.Delete tai lieu
		// // ---Del by Id
		// String docDelId = "{60B57A57-0000-CE1C-8299-D2DA96EA7E93}";
		// p8.deleteDocById(docDelId);
		// ---Del by properties
		// HashMap<String, String>delMap = new HashMap<String, String>();
		// delMap.put("DocumentTitle", "3.pdf");
		// delMap.put("MaNNT", "12345");
		// p8.deleteDocByProperties(delMap);
		// 7.Them phien ban cho tai lieu
		
		//8.Create Class Define
//		String classDef = "testClassDefine1";
//		createAndDelete p8 = new createAndDelete();
//		System.out.println(p8.createDocClassDefine("testClassDefine1", null));
		
		//9. Create Property Template
//		createAndDelete p8 = new createAndDelete();
//		readXLS readFileXLS = new readXLS();
//		String sPath = "D:/hoannc/Project/ECM/Tong hop thiet ke Metadata cac du an/testReadFile.xls";
//		File testRead = new File(sPath);
//		ArrayList<Object> arr = readFileXLS.readXLS(testRead);
//		Iterator it = arr.iterator();
//		while (it.hasNext()) {
//			ArrayList<String> member = (ArrayList<String>)it.next(); 
//			p8.createProperty(member.get(0),member.get(1), member.get(2), member.get(3));
//		}	
		
		//10. Add properties into Class
//		createAndDelete p8 = new createAndDelete();
//		readXLS readFileXLS = new readXLS();
//		String sPath = "D:/hoannc/Project/ECM/Tong hop thiet ke Metadata cac du an/testAddPropIntoClass.xls";
//		File testRead = new File(sPath);
//		ArrayList<Object> arr = readFileXLS.readXLS(testRead);
//		p8.addPropIntoClass(arr, classDef);
		
		
	}
	
	//Add properties into Class
	public void addPropIntoClass(ArrayList<Object> arrProps, String sClassDef){
		ObjectStore os = this.getObjectStore();
		ClassDefinition classDef = Factory.ClassDefinition.fetchInstance(os, sClassDef, null);
		// Get PropertyDefinitions property from the property cache                     
		PropertyDefinitionList propDefs = classDef.get_PropertyDefinitions(); 
		Iterator it = arrProps.iterator();
		while(it.hasNext()){
			ArrayList<String> member = (ArrayList<String>)it.next();
			String sType = member.get(1);
			String symbolName = member.get(0);
			if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.INT.toLowerCase())){
				com.filenet.api.admin.PropertyTemplateInteger32 proTem = (PropertyTemplateInteger32)searchProperty(symbolName);
				PropertyDefinitionInteger32 propDef = (PropertyDefinitionInteger32) proTem.createClassProperty();
				boolean b = propDefs.add(propDef);
				if(b)
					System.out.println("ADD <"+symbolName+"> : "+sType.toUpperCase()+" INTO "+sClassDef);
			}
			else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.DATE.toLowerCase())){
				com.filenet.api.admin.PropertyTemplateDateTime proTem = (PropertyTemplateDateTime) searchProperty(symbolName);
				PropertyDefinitionDateTime propDef = (PropertyDefinitionDateTime) proTem.createClassProperty();
				boolean b = propDefs.add(propDef);
				if(b)
					System.out.println("ADD <"+symbolName+"> : "+sType.toUpperCase()+" INTO "+sClassDef);
			}
			else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.STRING.toLowerCase())){
				com.filenet.api.admin.PropertyTemplateString proTem = (PropertyTemplateString) searchProperty(symbolName);
				PropertyDefinitionString propDef = (PropertyDefinitionString) proTem.createClassProperty();
				boolean b = propDefs.add(propDef);
				if(b)
					System.out.println("ADD <"+symbolName+"> : "+sType.toUpperCase()+" INTO "+sClassDef);
			}
			else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.FLOAT.toLowerCase())){
				com.filenet.api.admin.PropertyTemplateFloat64 proTem = (PropertyTemplateFloat64) searchProperty(symbolName);
				PropertyDefinitionFloat64 propDef = (PropertyDefinitionFloat64) proTem.createClassProperty();
				boolean b = propDefs.add(propDef);
				if(b)
					System.out.println("ADD <"+symbolName+"> : "+sType.toUpperCase()+" INTO "+sClassDef);
			}
			else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.BOOLEAN.toLowerCase())){
				com.filenet.api.admin.PropertyTemplateBoolean proTem = (PropertyTemplateBoolean) searchProperty(symbolName);
				PropertyDefinitionBoolean propDef = (PropertyDefinitionBoolean) proTem.createClassProperty();
				boolean b = propDefs.add(propDef);
				if(b)
					System.out.println("ADD <"+symbolName+"> : "+sType.toUpperCase()+" INTO "+sClassDef);
			}
			
		}
		classDef.save(RefreshMode.REFRESH);
				
	}
	
	//Search Property
	private Object searchProperty(String sSymbolName) {
		Object obj = null;
		ObjectStore os = getObjectStore();
		SearchScope search = new SearchScope(os);
		SearchSQL sql = new SearchSQL();
		sql.setSelectList("This, Id, DisplayName, SymbolicName");
		sql.setFromClauseInitialValue("PropertyTemplate", "d", true);
		String whereClause = " SymbolicName LIKE '"+sSymbolName+"'";
		sql.setWhereClause(whereClause);
		sql.toString();
		com.filenet.apiimpl.core.SubSetImpl props = (com.filenet.apiimpl.core.SubSetImpl) search.fetchObjects(sql, new Integer(50), null, Boolean.valueOf(true));
		Iterator it = props.iterator();
		while (it.hasNext()) {
			obj = it.next();
		}
		return obj;
	}
	
	//Create Property Template
	/***
	 * 
	 * @param sybName SymbolName - ten thuoc tinh MaUD, MST, TenNNT, DonVi....
	 * @param sMaxLength Maximum Lenght
	 * @param sType Kieu du lieu DateTime, String, Boolean, Object, Integer,...
	 * @param disName DisplayName - Ma ung dung, Ma so thue, Ten nguoi nop thue,....
	 * @return -1: Chua truyen type, 0:Kieu du lieu chua ho tro, 1: success INT, 2:success DATE, 3:success STRING, 4:success FOLAT, 5:success BOOLEAN
	 */
	public String createProperty(String sybName, String sMaxLength, String sType, String disName){
		String sReturn = "";	
		boolean exists = false;
		ObjectStore os = this.getObjectStore();
		LocalizedString displayNameProp = getLocalizedString(disName, os.get_LocaleName() );		
		if(sType == null || "".equalsIgnoreCase(sType)){
			sReturn = "-1";
			System.out.println("Thieu kieu du lieu");
		}
		else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.INT.toLowerCase()) && searchProperty(sybName) == null){
			com.filenet.api.admin.PropertyTemplateInteger32 proDef = Factory.PropertyTemplateInteger32.createInstance(os);
			proDef.set_Cardinality (Cardinality.SINGLE);
			proDef.set_IsValueRequired(Boolean.FALSE);
			proDef.set_SymbolicName(sybName);
			proDef.set_DisplayNames(Factory.LocalizedString.createList());
			proDef.get_DisplayNames().add(displayNameProp);
			proDef.set_DescriptiveTexts(Factory.LocalizedString.createList());
			proDef.get_DescriptiveTexts().add(displayNameProp);
			proDef.set_PropertyMaximumInteger32(Integer.valueOf(sMaxLength));
			proDef.save(RefreshMode.REFRESH);
			sReturn = "1";
			System.out.println("SUCCESS <"+sybName+"> : "+sType.toUpperCase());
		}
		else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.DATE.toLowerCase()) && searchProperty(sybName) == null){
			com.filenet.api.admin.PropertyTemplateDateTime proDef = Factory.PropertyTemplateDateTime.createInstance(os);
			proDef.set_Cardinality (Cardinality.SINGLE);
			proDef.set_IsValueRequired(Boolean.FALSE);
			proDef.set_SymbolicName(sybName);
			proDef.set_DisplayNames(Factory.LocalizedString.createList());
			proDef.get_DisplayNames().add(displayNameProp);
			proDef.set_DescriptiveTexts(Factory.LocalizedString.createList());
			proDef.get_DescriptiveTexts().add(displayNameProp);
			proDef.save(RefreshMode.REFRESH);
			sReturn = "2";
			System.out.println("SUCCESS <"+sybName+"> : "+sType.toUpperCase());
		}
		else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.STRING.toLowerCase()) && searchProperty(sybName) == null){
			com.filenet.api.admin.PropertyTemplateString proDef = Factory.PropertyTemplateString.createInstance(os);
			proDef.set_Cardinality (Cardinality.SINGLE);
			proDef.set_IsValueRequired(Boolean.FALSE);
			proDef.set_SymbolicName(sybName);
			proDef.set_DisplayNames(Factory.LocalizedString.createList());
			proDef.get_DisplayNames().add(displayNameProp);
			proDef.set_DescriptiveTexts(Factory.LocalizedString.createList());
			proDef.get_DescriptiveTexts().add(displayNameProp);
			proDef.set_MaximumLengthString(Integer.valueOf(sMaxLength));
			proDef.save(RefreshMode.REFRESH);
			sReturn = "3";
			System.out.println("SUCCESS <"+sybName+"> : "+sType.toUpperCase());
		}
		else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.FLOAT.toLowerCase()) && searchProperty(sybName) == null){
			com.filenet.api.admin.PropertyTemplateFloat64 proDef = Factory.PropertyTemplateFloat64.createInstance(os);
			proDef.set_Cardinality (Cardinality.SINGLE);
			proDef.set_IsValueRequired(Boolean.FALSE);
			proDef.set_SymbolicName(sybName);
			proDef.set_DisplayNames(Factory.LocalizedString.createList());
			proDef.get_DisplayNames().add(displayNameProp);
			proDef.set_DescriptiveTexts(Factory.LocalizedString.createList());
			proDef.get_DescriptiveTexts().add(displayNameProp);
			proDef.set_PropertyMaximumFloat64(Double.valueOf(sMaxLength));
			proDef.save(RefreshMode.REFRESH);
			sReturn = "4";
			System.out.println("SUCCESS <"+sybName+"> : "+sType.toUpperCase());
		}
		else if(sType.toLowerCase().equals(ConstantValue.PROPERTY.DATA_TYPE.BOOLEAN.toLowerCase()) && searchProperty(sybName) == null){
			com.filenet.api.admin.PropertyTemplateBoolean proDef = Factory.PropertyTemplateBoolean.createInstance(os);
			proDef.set_Cardinality (Cardinality.SINGLE);
			proDef.set_IsValueRequired(Boolean.FALSE);
			proDef.set_SymbolicName(sybName);
			proDef.set_DisplayNames(Factory.LocalizedString.createList());
			proDef.get_DisplayNames().add(displayNameProp);
			proDef.set_DescriptiveTexts(Factory.LocalizedString.createList());
			proDef.get_DescriptiveTexts().add(displayNameProp);
			proDef.save(RefreshMode.REFRESH);
			sReturn = "5";
			System.out.println("SUCCESS <"+sybName+"> : "+sType.toUpperCase());
		}
		else{
			sReturn = "0";
			if(searchProperty(sybName) != null)
				System.out.println("EXISTSED <"+sybName+"> : "+sType.toUpperCase());
			else
				System.out.println("Chua ho tro kieu du lieu nay");
		}	
		return sReturn;
	}
	
	
	//Create Document Define
	/***
	 * 
	 * @param className Ten class Define muon tao
	 * @param classParent Ten class cha, truyen vao null thi mac dinh class cha = "Document" cua ECM
	 * @return 0:SUCCESS, 1:EXISTS, 2:FAILURE
	 */
	public String createDocClassDefine(String className, String classParent){
		String sReturn = "";
		try{
			ObjectStore os = this.getObjectStore();
			if(classParent == null || "".equalsIgnoreCase(classParent)){
				classParent = "Document";
			}
			ClassDefinition classDefParent = Factory.ClassDefinition.fetchInstance(os, classParent, null);
			ClassDefinition classDef = classDefParent.createSubclass();
			classDef.set_SymbolicName(className);
			LocalizedString displayNameSubClass = getLocalizedString(className, os.get_LocaleName() );
			classDef.set_DisplayNames(Factory.LocalizedString.createList());
			classDef.get_DisplayNames().add(displayNameSubClass);
			LocalizedString descriptNameSubClass = getLocalizedString(className, os.get_LocaleName() );
			classDef.set_DescriptiveTexts(Factory.LocalizedString.createList());
			classDef.get_DescriptiveTexts().add(descriptNameSubClass);
			classDef.save(RefreshMode.REFRESH);
			sReturn = "0";
			System.out.println("Create <-- "+className+" --> SUCCESS.");
		}catch(Exception ex){
			if(ex.toString().contains("is not unique")){
				System.out.println("Error: Create Failure !!. \n       Class want create: <-- "+className+" --> already EXISTS");
				sReturn = "1";
			}else if(ex.toString().contains("E_BAD_CLASSID")){
				System.out.println("Error: Create Failure !!. \n       Class Parent : <-- "+classParent+" --> is INCORRECT");
				sReturn = "2";
			}
			else{
				System.out.println("Call HoanNC");
				sReturn = "3";
			}
		}
		return sReturn;
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
			// DateCreated, DateLastModified,
			// if(key.toLowerCase().equals("ngaysuatungay")){
			// whereClause += "NgaySua" + ">=" +
			// greaterDate(arrSearchFields.get(key)) + "";
			// }else if(key.toLowerCase().equals("ngaysuadenngay")){
			// whereClause += "NgaySua" + "<=" +
			// lessDate(arrSearchFields.get(key)) + "";
			// }else if(key.toLowerCase().equals("ngaytaotungay")){
			// whereClause += "NgayTaoDocument" + ">=" +
			// greaterDate(arrSearchFields.get(key)) + "";
			// }else if(key.toLowerCase().equals("ngaytaodenngay")){
			// whereClause += "NgayTaoDocument" + "<=" +
			// lessDate(arrSearchFields.get(key)) + "";
			// }else{
			whereClause += key + "='" + arrSearchFields.get(key) + "'";
			// }
			whereClause += " AND ";
		}
		whereClause += "IsCurrentVersion = true";
		whereClause += " AND ";

		sql.setWhereClause(whereClause.substring(0, whereClause.lastIndexOf(" AND ")));
		DocumentSet documents = (DocumentSet) search.fetchObjects(sql, new Integer(50), null, Boolean.valueOf(true));
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

		sql.setWhereClause(whereClause.substring(0, whereClause.lastIndexOf(" AND ")));
		DocumentSet documents = (DocumentSet) search.fetchObjects(sql, new Integer(50), null, Boolean.valueOf(true));
		return documents;
	}

	private void createFolder(String sPath) {
		if (sPath == null || "".equalsIgnoreCase(sPath.trim())) {
			System.out.println("Không truyền tham số đường dẫn folder");
		} else {
			String[] pathList = sPath.split("/");
			if (pathList.length > 0) {
				ObjectStore store = getObjectStore();
				int lenght = pathList.length;
				com.filenet.api.core.Folder folderParent = store.get_RootFolder();
				for (int i = 0; i < lenght; i++) {
					if (pathList[i] != null && !"".equalsIgnoreCase(pathList[i].trim())) {
						FolderSet fs = folderParent.get_SubFolders();
						Iterator it = fs.iterator();
						Boolean exsitFolder = false;
						while (it.hasNext()) {
							com.filenet.api.core.Folder memberFolder = (com.filenet.api.core.Folder) it.next();
							if (memberFolder.get_FolderName().equalsIgnoreCase(pathList[i].trim())) {
								// folderParent = (i == 0) ? folderParent :
								// memberFolder;
								folderParent = memberFolder;
								exsitFolder = true;
								break;
							}
						}
						if (exsitFolder) {
							String rootName = folderParent.get_Parent().get_FolderName();
							if (rootName == null) {
								rootName = "Root Folder";
							}
							System.out.println("Đã tồn tại foler: " + pathList[i] + " trong " + rootName);
						} else {
							com.filenet.api.core.Folder folder = Factory.Folder.createInstance(store, null);
							folder.set_Parent(folderParent);
							folder.set_FolderName(pathList[i]);
							folder.save(RefreshMode.REFRESH);
							String rootName = folderParent.get_FolderName();
							if (rootName == null) {
								rootName = "Root Folder";
							}
							System.out.println("Create thành công foler: " + pathList[i] + " trong "
									+ rootName);
							folderParent = folder;
						}
					}
				}
			}
		}
	}

	private void deleteFolderByPath(String sPath, ObjectStore store) {
		com.filenet.api.core.Folder folder = Factory.Folder.fetchInstance(store, sPath, null);
		folder.delete();
		System.out.println("Delete " + folder.get_PathName());
		folder.save(RefreshMode.NO_REFRESH);
	}

	private ArrayList<String> getLastPathsForDelete(ArrayList<String> listPath, String sPathChange, String sPathOrigin,
			ObjectStore store, Boolean continuous) {
		try {
			if (continuous) {
				com.filenet.api.core.Folder folder = Factory.Folder.fetchInstance(store, sPathChange, null);
				if (folder.get_SubFolders().isEmpty() && continuous) {
					listPath.add(folder.get_PathName());

					String pathCheck = folder.get_PathName();
					sPathChange = folder.get_Parent().get_PathName();
					deleteFolderByPath(folder.get_PathName(), store);
					if (pathCheck.equals(sPathOrigin.trim())) {
						continuous = false;
					} else {
						this.getLastPathsForDelete(listPath, sPathChange, sPathOrigin, store, continuous);
					}
				} else {
					FolderSet fs = folder.get_SubFolders();
					Iterator it = fs.iterator();
					while (it.hasNext() && continuous) {
						com.filenet.api.core.Folder memberFolder = (com.filenet.api.core.Folder) it.next();
						sPathChange = memberFolder.get_PathName();
						if (listPath.contains(sPathOrigin)) {
							break;
						}
						this.getLastPathsForDelete(listPath, sPathChange, sPathOrigin, store, continuous);
					}
				}
			} else {
				System.out.println(continuous);
			}
		} catch (Exception e) {
			System.out.println("Đường dẫn thư mục muốn xóa: "+ sPathOrigin +" không tồn tại");
		}

		return listPath;
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
			if (key.toLowerCase().equals("id")) {
				countId++;
			}
			// if(key.toLowerCase().equals("ngaysuatungay")){
			// whereClause += "NgaySua" + ">=" +
			// greaterDate(arrSearchFields.get(key)) + "";
			// }else if(key.toLowerCase().equals("ngaysuadenngay")){
			// whereClause += "NgaySua" + "<=" +
			// lessDate(arrSearchFields.get(key)) + "";
			// }else if(key.toLowerCase().equals("ngaytaotungay")){
			// whereClause += "NgayTaoDocument" + ">=" +
			// greaterDate(arrSearchFields.get(key)) + "";
			// }else if(key.toLowerCase().equals("ngaytaodenngay")){
			// whereClause += "NgayTaoDocument" + "<=" +
			// lessDate(arrSearchFields.get(key)) + "";
			// }else{
			whereClause += key + "='" + arrSearchFields.get(key) + "'";
			// }
			whereClause += " AND ";
		}

		DocumentSet documents = null;
		if (countId == 0) {
			sql.setWhereClause(whereClause.substring(0, whereClause.lastIndexOf(" AND ")));
			documents = (DocumentSet) search.fetchObjects(sql, new Integer(50), null, Boolean.valueOf(true));
		} else {
			whereClause = "Id = " + arrSearchFields.get("ID");
			sql.setWhereClause(whereClause);
			documents = (DocumentSet) search.fetchObjects(sql, new Integer(50), null, Boolean.valueOf(true));
			Iterator it = documents.iterator();
			String IDTaiLieuVersionGoc = "";
			while (it.hasNext()) {
				Document document = (Document) it.next();
				IDTaiLieuVersionGoc = document.getProperties().getStringValue("IDTaiLieuVersionGoc");
			}
			for (String key : arrSearchFields.keySet()) {
				if (key.toLowerCase().equals("id")) {
					countId++;
				}
				// if(key.toLowerCase().equals("ngaysuatungay")){
				// whereClauseSeries += "NgaySua" + ">=" +
				// greaterDate(arrSearchFields.get(key)) + "";
				// whereClauseSeries += " AND ";
				// }else if(key.toLowerCase().equals("ngaysuadenngay")){
				// whereClauseSeries += "NgaySua" + "<=" +
				// lessDate(arrSearchFields.get(key)) + "";
				// whereClauseSeries += " AND ";
				// }else if(key.toLowerCase().equals("ngaytaotungay")){
				// whereClauseSeries += "NgayTaoDocument" + ">=" +
				// greaterDate(arrSearchFields.get(key)) + "";
				// whereClauseSeries += " AND ";
				// }else if(key.toLowerCase().equals("ngaytaodenngay")){
				// whereClauseSeries += "NgayTaoDocument" + "<=" +
				// lessDate(arrSearchFields.get(key)) + "";
				// whereClauseSeries += " AND ";
				// }
			}
			whereClauseSeries = "IDTaiLieuVersionGoc = '" + IDTaiLieuVersionGoc + "'";
			sqlSeries.setWhereClause(whereClauseSeries);
			documents = (DocumentSet) search.fetchObjects(sqlSeries, new Integer(50), null, Boolean.valueOf(true));
		}
		return documents;
	}

	private ObjectStore getObjectStore() {
		Connection conn = Factory.Connection.getConnection(ecmURI);
		Subject subject = UserContext.createSubject(conn, ecmUsername, ecmPassword, "FileNetP8WSI");
		UserContext uc = UserContext.get();
		uc.pushSubject(subject);
		Domain domain = Factory.Domain.fetchInstance(conn, ecmDomain, null);
		ObjectStore store = Factory.ObjectStore.fetchInstance(domain, ecmObjectStoreName, null);
		return store;
	}

	public ArrayList<String> areaSetName() {
		ObjectStore store = getObjectStore();
		StorageAreaSet sas = store.get_StorageAreas();
		Iterator it = sas.iterator();
		ArrayList<String> areaList = new ArrayList<String>();
		while (it.hasNext()) {
			com.filenet.api.admin.StorageArea sa = (com.filenet.api.admin.StorageArea) it.next();
			areaList.add(sa.get_DisplayName());
		}
		return areaList;
	}

	public com.filenet.api.admin.StorageArea getStorageAreaSelected(String displayName) {
		if (displayName == null || "".equals(displayName.trim())) {
			System.out.println("**** Không truyền tham số StoragePolicy ****");
			return null;
		} else {
			ObjectStore store = getObjectStore();
			StorageAreaSet sas = store.get_StorageAreas();
			Iterator it = sas.iterator();
			com.filenet.api.admin.StorageArea saReturn = null;
			while (it.hasNext()) {
				com.filenet.api.admin.StorageArea sa = (com.filenet.api.admin.StorageArea) it.next();
				if (sa.get_DisplayName().equalsIgnoreCase(displayName)) {
					saReturn = sa;
				}
			}
			if (saReturn != null) {
				return saReturn;
			} else {
				return null;
			}
		}
	}

	public ArrayList<String> policySetName() {
		ObjectStore store = getObjectStore();
		StoragePolicySet sps = store.get_StoragePolicies();
		Iterator it = sps.iterator();
		ArrayList<String> policyList = new ArrayList<String>();
		while (it.hasNext()) {
			com.filenet.api.admin.StoragePolicy sp = (com.filenet.api.admin.StoragePolicy) it.next();
			policyList.add(sp.get_DisplayName());
		}
		return policyList;
	}

	public com.filenet.api.admin.StoragePolicy getStoragePolicySelected(String displayName) {
		if (displayName == null || "".equals(displayName.trim())) {
			System.out.println("**** Không truyền tham số StoragePolicy ****");
			return null;
		} else {
			ObjectStore store = getObjectStore();
			StoragePolicySet sps = store.get_StoragePolicies();
			Iterator it = sps.iterator();
			com.filenet.api.admin.StoragePolicy spReturn = null;
			while (it.hasNext()) {
				com.filenet.api.admin.StoragePolicy sp = (com.filenet.api.admin.StoragePolicy) it.next();
				if (sp.get_DisplayName().equalsIgnoreCase(displayName)) {
					spReturn = sp;
				}
			}
			if (spReturn != null) {
				return spReturn;
			} else {
				return null;
			}
		}
	}
	
	private String getDirKDTTT(String maCQT, String loaiHS, String sDate) throws Exception{
		String sDir = "";
		sDir += "/"+TCT;
		for(String key : CQT.CQT_MAP.keySet()){
			if(key.equalsIgnoreCase(maCQT)){
				sDir += "/" + CQT.CQT_MAP.get(key);
				break;
			}
		}
		sDir += "/" + loaiHS + "/" + sDate;
		
		return sDir;
	}

	public String putDocument(HashMap<String, String> arrDocumentProperties, String sDate, byte[] documentContent) throws Exception {

		ObjectStore os = getObjectStore();
		String docClass = ecmDocClass;
		String ecmFolderPath = getDirKDTTT(arrDocumentProperties.get("DonVi"), arrDocumentProperties.get("LoaiHSo"), sDate);
		String docName = arrDocumentProperties.get("DocumentTitle");
		
		Document doc;
		// 1.Them tai lieu document
		doc = Factory.Document.createInstance(os, docClass);
		for (String key : arrDocumentProperties.keySet()) {
			doc.getProperties().putValue(key, arrDocumentProperties.get(key));
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
		if (cel != null) {
			doc.set_ContentElements(cel);
		}
		doc.save(RefreshMode.REFRESH);

		// 2.Dua tai lieu vao folder : ecmFolderPath
		ReferentialContainmentRelationship rcr = null;
		Folder fo = Factory.Folder.fetchInstance(os, ecmFolderPath, null);
		if (doc instanceof Document) {
			rcr = fo.file((Document) doc, AutoUniqueName.AUTO_UNIQUE, ((Document) doc).get_Name(),
					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
		}
		rcr.save(RefreshMode.REFRESH);

		// 3.Check-in tai lieu
		doc.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
//		doc.getProperties().putValue("IDTaiLieuVersionGoc", doc.get_Id().toString());
		doc.save(RefreshMode.REFRESH);
		doc.refresh();

		return doc.get_Id().toString();
	}

//	public String putDocFileStorage(HashMap<String, String> arrDocumentProperties, byte[] documentContent,
//			String storageAreaName, String storagePolicyName) throws Exception {
//
//		ObjectStore os = getObjectStore();
//		String docClass = ecmDocClass;
//		String ecmFolderPath = ecmFolder;
//		String docName = arrDocumentProperties.get("DocumentTitle");
//		Document doc;
//		// 1.Them tai lieu document
//		doc = Factory.Document.createInstance(os, docClass);
//		for (String key : arrDocumentProperties.keySet()) {
//			doc.getProperties().putValue(key, arrDocumentProperties.get(key));
//		}
//
//		// 1.1 Set StorageArea va Storagepolicy
//		doc.set_StorageArea(getStorageAreaSelected(storageAreaName));
//		doc.set_StoragePolicy(getStoragePolicySelected(storagePolicyName));
//		ContentElementList cel = null;
//		ContentTransfer ctNew = null;
//		if (documentContent != null) {
//			ctNew = Factory.ContentTransfer.createInstance();
//			ByteArrayInputStream is = new ByteArrayInputStream(documentContent);
//			ctNew.setCaptureSource(is);
//			ctNew.set_RetrievalName(docName);
//		}
//		if (ctNew != null) {
//			cel = Factory.ContentElement.createList();
//			cel.add(ctNew);
//		}
//		if (cel != null) {
//			doc.set_ContentElements(cel);
//		}
//		doc.save(RefreshMode.REFRESH);
//
//		// 2.Dua tai lieu vao folder : ecmFolderPath
//		ReferentialContainmentRelationship rcr = null;
//		Folder fo = Factory.Folder.fetchInstance(os, ecmFolderPath, null);
//		if (doc instanceof Document) {
//			rcr = fo.file((Document) doc, AutoUniqueName.AUTO_UNIQUE, ((Document) doc).get_Name(),
//					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
//		}
//		rcr.save(RefreshMode.REFRESH);
//
//		// 3.Check-in tai lieu
//		doc.checkin(AutoClassify.AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
////		doc.getProperties().putValue("IDTaiLieuVersionGoc", doc.get_Id().toString());
//		doc.save(RefreshMode.REFRESH);
//		doc.refresh();
//
//		return doc.get_Id().toString();
//	}

	public ArrayList<String> searchDocumentKeys(HashMap<String, String> arrSearchFields) throws Exception {
		if (arrSearchFields.size() == 0) {
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		} else {
			DocumentSet documents = searchDocuments(arrSearchFields);
			Iterator it = documents.iterator();
			ArrayList<String> arrOfKeys = new ArrayList<String>();
			while (it.hasNext()) {
				Document document = (Document) it.next();
				// document.get_FoldersFiledIn();
				arrOfKeys.add(document.get_Id().toString());
			}
			return arrOfKeys;
		}
	}

	public ArrayList<String> searchAllDocumentIDByClass() throws Exception {
		ObjectStore os = getObjectStore();
		SearchScope search = new SearchScope(os);
		SearchSQL sql = new SearchSQL();
		sql.setSelectList(ecmQueryClause);
		sql.setFromClauseInitialValue(ecmDocClass, "d", true);
		// whereClause += "IsCurrentVersion = true";
		DocumentSet documents = (DocumentSet) search.fetchObjects(sql, new Integer(50), null, Boolean.valueOf(true));
		Iterator it = documents.iterator();
		ArrayList<String> arrOfKeys = new ArrayList<String>();
		while (it.hasNext()) {
			Document document = (Document) it.next();
			arrOfKeys.add(document.get_Id().toString());
			// document.getProperties().get("DocumentTitle").getClass().toString();
			// if(document.get_Id().toString().equals("{30A63456-0000-C117-94BB-7EA0B231EB17}")){
			// System.out.println("------------------update "+ ecmDocClass +" ID
			// = {30A63456-0000-C117-94BB-7EA0B231EB17}");
			// HashMap<String, String> putDocHashMapKey = new HashMap<String,
			// String>();
			// putDocHashMapKey.put("newLenght",
			// document.getProperties().getStringValue("DocumentTitle"));
			// updateMetadataDocument(document, putDocHashMapKey);
			// }
		}
		return arrOfKeys;
	}

	public String updateMetadataDocument(Document doc, HashMap<String, String> arrFieldsVal) throws Exception {
		if (arrFieldsVal.size() == 0) {
			System.out.println("**** Không truyền thuộc tính và giá trị update ****");
			return null;
		} else {
			for (String key : arrFieldsVal.keySet()) {
				String classProperty = doc.getProperties().get(key).getClass().toString();
				doc.getProperties().putValue(key, arrFieldsVal.get(key));
			}
			doc.save(RefreshMode.REFRESH);
		}
		return doc.get_Id().toString();
	}

	public HashMap<String, String> getDocumentMetaData(String documentID) throws Exception {
		if (documentID == null) {
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		} else {
			HashMap<String, String> hashMapKey = new HashMap<String, String>();
			hashMapKey.put("Id", documentID);
			DocumentSet documents = searchDocumentForDelete(hashMapKey);
			Iterator it = documents.iterator();
			HashMap hm = new HashMap();
			if (it.hasNext()) {
				Document document = (Document) it.next();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				for (int i = 0; i < document.getProperties().toArray().length; i++) {
					hm.put(document.getProperties().toArray()[i].getPropertyName(), propertyValue(document, i));
				}
			}
			return hm;
		}
	}

	private String propertyValue(Document document, int i) {
		String strReturn = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String type = document.getProperties().toArray()[i].getClass().toString();
		if (type.contains("String")) {
			strReturn = document.getProperties().toArray()[i].getStringValue();
		}
		if (type.contains("Boolean")) {
			strReturn = document.getProperties().toArray()[i].getBooleanValue() == true ? "1" : "0";
		}
		if (type.contains("Date")) {
			strReturn = sdf.format(document.getProperties().toArray()[i].getDateTimeValue());
		}
		if (type.contains("Object")) {
			strReturn = "Object type, không return giá trị String của thuộc tính này";
		}
		if (type.contains("Float64")) {
			strReturn = String.valueOf(document.getProperties().toArray()[i].getFloat64Value());
		}
		if (type.contains("Id")) {
			strReturn = document.getProperties().toArray()[i].getIdValue().toString();
		}
		if (type.contains("Stream")) {
			strReturn = "Stream type, không return giá trị String của thuộc tính này";
		}
		if (type.contains("Integer32")) {
			strReturn = String.valueOf(document.getProperties().toArray()[i].getInteger32Value());
		}
		if (strReturn == null) {
			strReturn = "";
		}
		return strReturn;
	}

	public byte[] getDocumentContent(String documentID) throws Exception {
		if (documentID == null) {
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		} else {
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
					InputStream inputStream = ((ContentTransfer) content).accessContentStream();
					retBytes = getBytes(inputStream);
				}
			}
			return retBytes;
		}
	}

	public String getStringDocumentContent(String documentID) throws Exception {
		if (documentID == null) {
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		} else {
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
					InputStream inputStream = ((ContentTransfer) content).accessContentStream();
					retBytes = getBytes(inputStream);
				}
			}
			return new String(retBytes, "UTF-8");
		}
	}

	public String getDocumentNameContent(String documentID) throws Exception {
		if (documentID == null) {
			System.out.println("**** Không truyền tham số tìm kiếm ****");
			return null;
		} else {
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
					retrievalName = retrievalName.substring(retrievalName.indexOf("Value") + 6,
							retrievalName.indexOf("IsDirty") - 1);
				}
			}
			return retrievalName;
		}
	}

	public void deleteDocById(String documentID) throws Exception {
		if (documentID == null) {
			System.out.println("**** Không truyền tham số delete ****");
		} else {
			Document document = null;
			HashMap<String, String> hashMapKey = new HashMap<String, String>();
			hashMapKey.put("Id", documentID);
			DocumentSet documents = searchDocumentForDelete(hashMapKey);
			Iterator it = documents.iterator();
			if (it.hasNext()) {
				document = (Document) it.next();
				document.delete();
				document.save(RefreshMode.NO_REFRESH);
				System.out.println("Delete document has ID: " + documentID);
			}
		}
	}

	public void deleteDocByProperties(HashMap<String, String> hashMapKey) throws Exception {
		if (hashMapKey.size() == 0) {
			System.out.println("**** Không truyền tham số delete ****");
		} else {
			ArrayList<String> listDocId = this.searchDocumentKeys(hashMapKey);
			if (listDocId != null) {
				for (int i = 0; i < listDocId.size(); i++) {
					deleteDocById(listDocId.get(i));
				}
			}
		}
	}

	public String updateVersion(String documentID, File file, boolean version) throws Exception {
		if (documentID == null || file == null) {
			System.out.println("**** Thiếu tham số ****");
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
				ctObject.setCaptureSource(new FileInputStream(file));
				ctObject.set_RetrievalName(file.getName());
				FileNameMap fileNameMap = URLConnection.getFileNameMap();
				ctObject.set_ContentType(fileNameMap.getContentTypeFor(file.getName()));
				contentElements.add(ctObject);
				newDoc.set_ContentElements(contentElements);
				if (version) {
					newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MINOR_VERSION);
				} else {
					newDoc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
				}
				newDoc.save(RefreshMode.NO_REFRESH);
				deleteDocById(oldId);
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

	public String addVersion(String documentID, File file, boolean version) throws Exception {
		if (documentID == null || file == null) {
			System.out.println("**** Thiếu tham số ****");
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
				ctObject.setCaptureSource(new FileInputStream(file));
				ctObject.set_RetrievalName(file.getName());
				FileNameMap fileNameMap = URLConnection.getFileNameMap();
				ctObject.set_ContentType(fileNameMap.getContentTypeFor(file.getName()));
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

	public String addVersion(String documentID, byte[] fileData, boolean version) throws Exception {
		if (documentID == null || fileData == null) {
			System.out.println("**** Thiếu tham số ****");
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

	private String greaterDate(String strDate) {
		return strDate.substring(6, 10) + strDate.substring(3, 5) + strDate.substring(0, 2) + "T000000Z";
	}

	private String lessDate(String strDate) {
		return strDate.substring(6, 10) + strDate.substring(3, 5) + strDate.substring(0, 2) + "T235959Z";
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

	public ArrayList<String> getStorageAreaList() {
		return storageAreaList;
	}

	public void setStorageAreaList(ArrayList<String> storageAreaList) {
		this.storageAreaList = storageAreaList;
	}

	public ArrayList<String> getStoragePolicyList() {
		return storagePolicyList;
	}

	public void setStoragePolicyList(ArrayList<String> storagePolicyList) {
		this.storagePolicyList = storagePolicyList;
	}

	
	public static String TCT = "TCT";
	
	public static class CQT {
		public static final HashMap<String, String> CQT_MAP = new HashMap<String, String>();
		
		static{
			CQT_MAP.put("10100", "HAN/VP.HAN");
			CQT_MAP.put("10101", "HAN/HAN.BDI");
			CQT_MAP.put("10103", "HAN/HAN.THO");
			CQT_MAP.put("10105", "HAN/HAN.HKI");
			CQT_MAP.put("10106", "HAN/HAN.LBI");
			CQT_MAP.put("10107", "HAN/HAN.HBT");
			CQT_MAP.put("10108", "HAN/HAN.HMA");
			CQT_MAP.put("10109", "HAN/HAN.DDA");
			CQT_MAP.put("10111", "HAN/HAN.TXU");
			CQT_MAP.put("10113", "HAN/HAN.CGI");
			CQT_MAP.put("10115", "HAN/HAN.SSO");
			CQT_MAP.put("10117", "HAN/HAN.DAN");
			CQT_MAP.put("10119", "HAN/HAN.GLA");
			CQT_MAP.put("10121", "HAN/HAN.TLI");
			CQT_MAP.put("10123", "HAN/HAN.TTR");
			CQT_MAP.put("10125", "HAN/HAN.MLI");
			CQT_MAP.put("10127", "HAN/HAN.HDO");
			CQT_MAP.put("10129", "HAN/HAN.STA");
			CQT_MAP.put("10131", "HAN/HAN.PTH");
			CQT_MAP.put("10133", "HAN/HAN.DPH");
			CQT_MAP.put("10135", "HAN/HAN.TTH");
			CQT_MAP.put("10137", "HAN/HAN.HDU");
			CQT_MAP.put("10139", "HAN/HAN.QOA");
			CQT_MAP.put("10141", "HAN/HAN.TOA");
			CQT_MAP.put("10143", "HAN/HAN.TTI");
			CQT_MAP.put("10145", "HAN/HAN.MDU");
			CQT_MAP.put("10147", "HAN/HAN.UHO");
			CQT_MAP.put("10149", "HAN/HAN.PXU");
			CQT_MAP.put("10151", "HAN/HAN.BVI");
			CQT_MAP.put("10153", "HAN/HAN.CMY");
			CQT_MAP.put("10155", "HAN/HAN.NTL");
			CQT_MAP.put("10157", "HAN/HAN.BTL");
			CQT_MAP.put("10300", "HPH/VP.HPH");
			CQT_MAP.put("10301", "HPH/HPH.HBA");
			CQT_MAP.put("10303", "HPH/HPH.NQU");
			CQT_MAP.put("10304", "HPH/HPH.HAN");
			CQT_MAP.put("10305", "HPH/HPH.LCH");
			CQT_MAP.put("10307", "HPH/HPH.KAN");
			CQT_MAP.put("10309", "HPH/HPH.DSO");
			CQT_MAP.put("10311", "HPH/HPH.TNG");
			CQT_MAP.put("10313", "HPH/HPH.ADU");
			CQT_MAP.put("10315", "HPH/HPH.ALA");
			CQT_MAP.put("10317", "HPH/HPH.KTH");
			CQT_MAP.put("10319", "HPH/HPH.TLA");
			CQT_MAP.put("10321", "HPH/HPH.VBA");
			CQT_MAP.put("10323", "HPH/HPH.CHA");
			CQT_MAP.put("10325", "HPH/HPH.BLV");
			CQT_MAP.put("10327", "HPH/HPH.DKI");
			CQT_MAP.put("10700", "HDU/VP.HDU");
			CQT_MAP.put("10701", "HDU/HDU.HDU");
			CQT_MAP.put("10703", "HDU/HDU.CLI");
			CQT_MAP.put("10705", "HDU/HDU.NSA");
			CQT_MAP.put("10707", "HDU/HDU.THA");
			CQT_MAP.put("10709", "HDU/HDU.KMO");
			CQT_MAP.put("10711", "HDU/HDU.KTH");
			CQT_MAP.put("10713", "HDU/HDU.GLO");
			CQT_MAP.put("10715", "HDU/HDU.TKY");
			CQT_MAP.put("10717", "HDU/HDU.CGI");
			CQT_MAP.put("10719", "HDU/HDU.BGI");
			CQT_MAP.put("10721", "HDU/HDU.TMI");
			CQT_MAP.put("10723", "HDU/HDU.NGI");
			CQT_MAP.put("10900", "HYE/VP.HYE");
			CQT_MAP.put("10901", "HYE/HYE.HYE");
			CQT_MAP.put("10903", "HYE/HYE.MHA");
			CQT_MAP.put("10905", "HYE/HYE.KCH");
			CQT_MAP.put("10907", "HYE/HYE.ATH");
			CQT_MAP.put("10909", "HYE/HYE.KDO");
			CQT_MAP.put("10911", "HYE/HYE.PCU");
			CQT_MAP.put("10913", "HYE/HYE.TLU");
			CQT_MAP.put("10915", "HYE/HYE.VGI");
			CQT_MAP.put("10917", "HYE/HYE.VLA");
			CQT_MAP.put("10919", "HYE/HYE.YMY");
			CQT_MAP.put("11100", "HNA/VP.HNA");
			CQT_MAP.put("11101", "HNA/HNA.PLY");
			CQT_MAP.put("11103", "HNA/HNA.DTI");
			CQT_MAP.put("11105", "HNA/HNA.KBA");
			CQT_MAP.put("11107", "HNA/HNA.LNH");
			CQT_MAP.put("11109", "HNA/HNA.TLI");
			CQT_MAP.put("11111", "HNA/HNA.BLU");
			CQT_MAP.put("11300", "NDI/VP.NDI");
			CQT_MAP.put("11301", "NDI/NDI.NDI");
			CQT_MAP.put("11303", "NDI/NDI.VBA");
			CQT_MAP.put("11305", "NDI/NDI.MLO");
			CQT_MAP.put("11307", "NDI/NDI.YYE");
			CQT_MAP.put("11309", "NDI/NDI.NTR");
			CQT_MAP.put("11311", "NDI/NDI.TNI");
			CQT_MAP.put("11313", "NDI/NDI.XTR");
			CQT_MAP.put("11315", "NDI/NDI.GTH");
			CQT_MAP.put("11317", "NDI/NDI.NHU");
			CQT_MAP.put("11319", "NDI/NDI.HHA");
			CQT_MAP.put("11500", "TBI/VP.TBI");
			CQT_MAP.put("11501", "TBI/TBI.TBI");
			CQT_MAP.put("11503", "TBI/TBI.QPH");
			CQT_MAP.put("11505", "TBI/TBI.HHA");
			CQT_MAP.put("11507", "TBI/TBI.TTH");
			CQT_MAP.put("11509", "TBI/TBI.DHU");
			CQT_MAP.put("11511", "TBI/TBI.VTH");
			CQT_MAP.put("11513", "TBI/TBI.KXU");
			CQT_MAP.put("11515", "TBI/TBI.THA");
			CQT_MAP.put("11700", "NBI/VP.NBI");
			CQT_MAP.put("11701", "NBI/NBI.NBI");
			CQT_MAP.put("11703", "NBI/NBI.TDI");
			CQT_MAP.put("11705", "NBI/NBI.NQU");
			CQT_MAP.put("11707", "NBI/NBI.GVI");
			CQT_MAP.put("11709", "NBI/NBI.HLU");
			CQT_MAP.put("11711", "NBI/NBI.YMO");
			CQT_MAP.put("11713", "NBI/NBI.YKH");
			CQT_MAP.put("11715", "NBI/NBI.KSO");
			CQT_MAP.put("20100", "HGI/VP.HGI");
			CQT_MAP.put("20101", "HGI/HGI.HGI");
			CQT_MAP.put("20103", "HGI/HGI.DVA");
			CQT_MAP.put("20105", "HGI/HGI.MVA");
			CQT_MAP.put("20107", "HGI/HGI.YMI");
			CQT_MAP.put("20109", "HGI/HGI.QBA");
			CQT_MAP.put("20111", "HGI/HGI.BME");
			CQT_MAP.put("20113", "HGI/HGI.HSP");
			CQT_MAP.put("20115", "HGI/HGI.VXU");
			CQT_MAP.put("20117", "HGI/HGI.XMA");
			CQT_MAP.put("20118", "HGI/HGI.QBI");
			CQT_MAP.put("20119", "HGI/HGI.BQU");
			CQT_MAP.put("20300", "CBA/VP.CBA");
			CQT_MAP.put("20301", "CBA/CBA.CBA");
			CQT_MAP.put("20303", "CBA/CBA.BLA");
			CQT_MAP.put("20305", "CBA/CBA.HQU");
			CQT_MAP.put("20307", "CBA/CBA.TNO");
			CQT_MAP.put("20309", "CBA/CBA.TLI");
			CQT_MAP.put("20311", "CBA/CBA.TKH");
			CQT_MAP.put("20313", "CBA/CBA.NBI");
			CQT_MAP.put("20315", "CBA/CBA.HAN");
			CQT_MAP.put("20317", "CBA/CBA.QUY");
			CQT_MAP.put("20318", "CBA/CBA.PHO");
			CQT_MAP.put("20319", "CBA/CBA.HLO");
			CQT_MAP.put("20321", "CBA/CBA.TAN");
			CQT_MAP.put("20323", "CBA/CBA.BAL");
			CQT_MAP.put("20500", "LCA/VP.LCA");
			CQT_MAP.put("20501", "LCA/LCA.LCA");
			CQT_MAP.put("20505", "LCA/LCA.MKH");
			CQT_MAP.put("20507", "LCA/LCA.BXA");
			CQT_MAP.put("20509", "LCA/LCA.BHA");
			CQT_MAP.put("20511", "LCA/LCA.BTH");
			CQT_MAP.put("20513", "LCA/LCA.SPA");
			CQT_MAP.put("20515", "LCA/LCA.BYE");
			CQT_MAP.put("20519", "LCA/LCA.VBA");
			CQT_MAP.put("20521", "LCA/LCA.SMC");
			CQT_MAP.put("20700", "BCA/VP.BCA");
			CQT_MAP.put("20701", "BCA/BCA.BCA");
			CQT_MAP.put("20703", "BCA/BCA.BBE");
			CQT_MAP.put("20704", "BCA/BCA.PNA");
			CQT_MAP.put("20705", "BCA/BCA.NSO");
			CQT_MAP.put("20707", "BCA/BCA.CDO");
			CQT_MAP.put("20709", "BCA/BCA.NRI");
			CQT_MAP.put("20711", "BCA/BCA.BTH");
			CQT_MAP.put("20713", "BCA/BCA.CMO");
			CQT_MAP.put("20900", "LSO/VP.LSO");
			CQT_MAP.put("20901", "LSO/LSO.LSO");
			CQT_MAP.put("20903", "LSO/LSO.TDI");
			CQT_MAP.put("20905", "LSO/LSO.VLA");
			CQT_MAP.put("20907", "LSO/LSO.BGI");
			CQT_MAP.put("20909", "LSO/LSO.BSO");
			CQT_MAP.put("20911", "LSO/LSO.VQU");
			CQT_MAP.put("20913", "LSO/LSO.CLO");
			CQT_MAP.put("20915", "LSO/LSO.LBI");
			CQT_MAP.put("20917", "LSO/LSO.CLA");
			CQT_MAP.put("20919", "LSO/LSO.DLA");
			CQT_MAP.put("20921", "LSO/LSO.HLU");
			CQT_MAP.put("21100", "TQU/VP.TQU");
			CQT_MAP.put("21101", "TQU/TQU.TQU");
			CQT_MAP.put("21103", "TQU/TQU.NHA");
			CQT_MAP.put("21105", "TQU/TQU.CHO");
			CQT_MAP.put("21107", "TQU/TQU.HYE");
			CQT_MAP.put("21109", "TQU/TQU.YSO");
			CQT_MAP.put("21111", "TQU/TQU.SDU");
			CQT_MAP.put("21113", "TQU/TQU.LBI");
			CQT_MAP.put("21300", "YBA/VP.YBA");
			CQT_MAP.put("21301", "YBA/YBA.YBA");
			CQT_MAP.put("21303", "YBA/YBA.NLO");
			CQT_MAP.put("21305", "YBA/YBA.LYE");
			CQT_MAP.put("21307", "YBA/YBA.VYE");
			CQT_MAP.put("21309", "YBA/YBA.MCC");
			CQT_MAP.put("21311", "YBA/YBA.TYE");
			CQT_MAP.put("21313", "YBA/YBA.YBI");
			CQT_MAP.put("21315", "YBA/YBA.VCH");
			CQT_MAP.put("21317", "YBA/YBA.TTA");
			CQT_MAP.put("21500", "TNG/VP.TNG");
			CQT_MAP.put("21501", "TNG/TNG.TNG");
			CQT_MAP.put("21503", "TNG/TNG.SCO");
			CQT_MAP.put("21505", "TNG/TNG.DHO");
			CQT_MAP.put("21507", "TNG/TNG.VNH");
			CQT_MAP.put("21509", "TNG/TNG.PLU");
			CQT_MAP.put("21511", "TNG/TNG.DHY");
			CQT_MAP.put("21513", "TNG/TNG.DTU");
			CQT_MAP.put("21515", "TNG/TNG.PBI");
			CQT_MAP.put("21517", "TNG/TNG.PYE");
			CQT_MAP.put("21700", "PTH/VP.PTH");
			CQT_MAP.put("21701", "PTH/PTH.VTR");
			CQT_MAP.put("21703", "PTH/PTH.PTH");
			CQT_MAP.put("21705", "PTH/PTH.DHU");
			CQT_MAP.put("21707", "PTH/PTH.HHO");
			CQT_MAP.put("21709", "PTH/PTH.TBA");
			CQT_MAP.put("21711", "PTH/PTH.PNI");
			CQT_MAP.put("21713", "PTH/PTH.CKH");
			CQT_MAP.put("21715", "PTH/PTH.YLA");
			CQT_MAP.put("21717", "PTH/PTH.TNO");
			CQT_MAP.put("21719", "PTH/PTH.TSO");
			CQT_MAP.put("21720", "PTH/PTH.TAS");
			CQT_MAP.put("21721", "PTH/PTH.LTH");
			CQT_MAP.put("21723", "PTH/PTH.TTH");
			CQT_MAP.put("21900", "VPH/VP.VPH");
			CQT_MAP.put("21901", "VPH/VPH.VYE");
			CQT_MAP.put("21902", "VPH/VPH.PYE");
			CQT_MAP.put("21903", "VPH/VPH.LTH");
			CQT_MAP.put("21904", "VPH/VPH.TDA");
			CQT_MAP.put("21905", "VPH/VPH.TDU");
			CQT_MAP.put("21907", "VPH/VPH.VTU");
			CQT_MAP.put("21909", "VPH/VPH.YLA");
			CQT_MAP.put("21913", "VPH/VPH.BXU");
			CQT_MAP.put("21915", "VPH/VPH.SLO");
			CQT_MAP.put("22100", "BGI/VP.BGI");
			CQT_MAP.put("22101", "BGI/BGI.BGI");
			CQT_MAP.put("22103", "BGI/BGI.YTH");
			CQT_MAP.put("22105", "BGI/BGI.TYE");
			CQT_MAP.put("22107", "BGI/BGI.LNG");
			CQT_MAP.put("22109", "BGI/BGI.HHO");
			CQT_MAP.put("22111", "BGI/BGI.LGI");
			CQT_MAP.put("22113", "BGI/BGI.SDO");
			CQT_MAP.put("22115", "BGI/BGI.LNA");
			CQT_MAP.put("22117", "BGI/BGI.VYE");
			CQT_MAP.put("22119", "BGI/BGI.YDU");
			CQT_MAP.put("22300", "BNI/VP.BNI");
			CQT_MAP.put("22301", "BNI/BNI.BNI");
			CQT_MAP.put("22303", "BNI/BNI.YPH");
			CQT_MAP.put("22305", "BNI/BNI.QVO");
			CQT_MAP.put("22307", "BNI/BNI.TDU");
			CQT_MAP.put("22309", "BNI/BNI.TTH");
			CQT_MAP.put("22311", "BNI/BNI.LTA");
			CQT_MAP.put("22313", "BNI/BNI.TSO");
			CQT_MAP.put("22315", "BNI/BNI.GBI");
			CQT_MAP.put("22500", "QNI/VP.QNI");
			CQT_MAP.put("22501", "QNI/QNI.HLO");
			CQT_MAP.put("22503", "QNI/QNI.CPH");
			CQT_MAP.put("22505", "QNI/QNI.UBI");
			CQT_MAP.put("22507", "QNI/QNI.BLI");
			CQT_MAP.put("22509", "QNI/QNI.MCA");
			CQT_MAP.put("22511", "QNI/QNI.HHA");
			CQT_MAP.put("22513", "QNI/QNI.TYE");
			CQT_MAP.put("22515", "QNI/QNI.BCH");
			CQT_MAP.put("22517", "QNI/QNI.VDO");
			CQT_MAP.put("22519", "QNI/QNI.HBO");
			CQT_MAP.put("22521", "QNI/QNI.DTR");
			CQT_MAP.put("22523", "QNI/QNI.CTO");
			CQT_MAP.put("22525", "QNI/QNI.QYE");
			CQT_MAP.put("22527", "QNI/QNI.DHA");
			CQT_MAP.put("30100", "DBI/VP.DBI");
			CQT_MAP.put("30101", "DBI/DBI.DBP");
			CQT_MAP.put("30103", "DBI/DBI.MLA");
			CQT_MAP.put("30104", "DBI/DBI.MNH");
			CQT_MAP.put("30111", "DBI/DBI.MTR");
			CQT_MAP.put("30113", "DBI/DBI.TCH");
			CQT_MAP.put("30115", "DBI/DBI.TGI");
			CQT_MAP.put("30117", "DBI/DBI.DBI");
			CQT_MAP.put("30119", "DBI/DBI.DBD");
			CQT_MAP.put("30121", "DBI/DBI.MAN");
			CQT_MAP.put("30123", "DBI/DBI.NPO");
			CQT_MAP.put("30200", "LCH/VP.LCH");
			CQT_MAP.put("30201", "LCH/LCH.MTE");
			CQT_MAP.put("30202", "LCH/LCH.LCH");
			CQT_MAP.put("30203", "LCH/LCH.PTH");
			CQT_MAP.put("30205", "LCH/LCH.TDU");
			CQT_MAP.put("30207", "LCH/LCH.SHO");
			CQT_MAP.put("30209", "LCH/LCH.TUY");
			CQT_MAP.put("30211", "LCH/LCH.TAY");
			CQT_MAP.put("30213", "LCH/LCH.NNH");
			CQT_MAP.put("30300", "SLA/VP.SLA");
			CQT_MAP.put("30301", "SLA/SLA.SLA");
			CQT_MAP.put("30303", "SLA/SLA.QNH");
			CQT_MAP.put("30305", "SLA/SLA.MLA");
			CQT_MAP.put("30307", "SLA/SLA.TCH");
			CQT_MAP.put("30309", "SLA/SLA.BYE");
			CQT_MAP.put("30311", "SLA/SLA.PYE");
			CQT_MAP.put("30313", "SLA/SLA.MSO");
			CQT_MAP.put("30315", "SLA/SLA.SMA");
			CQT_MAP.put("30317", "SLA/SLA.YCH");
			CQT_MAP.put("30319", "SLA/SLA.MCH");
			CQT_MAP.put("30321", "SLA/SLA.SCO");
			CQT_MAP.put("30323", "SLA/SLA.VHO");
			CQT_MAP.put("30500", "HBI/VP.HBI");
			CQT_MAP.put("30501", "HBI/HBI.HBI");
			CQT_MAP.put("30503", "HBI/HBI.DBA");
			CQT_MAP.put("30505", "HBI/HBI.MCH");
			CQT_MAP.put("30507", "HBI/HBI.KSO");
			CQT_MAP.put("30509", "HBI/HBI.LSO");
			CQT_MAP.put("30510", "HBI/HBI.CPH");
			CQT_MAP.put("30511", "HBI/HBI.KBO");
			CQT_MAP.put("30513", "HBI/HBI.TLA");
			CQT_MAP.put("30515", "HBI/HBI.LAS");
			CQT_MAP.put("30517", "HBI/HBI.LTH");
			CQT_MAP.put("30519", "HBI/HBI.YTH");
			CQT_MAP.put("40100", "THO/VP.THO");
			CQT_MAP.put("40101", "THO/THO.THO");
			CQT_MAP.put("40103", "THO/THO.BSO");
			CQT_MAP.put("40105", "THO/THO.SSO");
			CQT_MAP.put("40107", "THO/THO.MLA");
			CQT_MAP.put("40109", "THO/THO.QHO");
			CQT_MAP.put("40111", "THO/THO.QSO");
			CQT_MAP.put("40113", "THO/THO.BTH");
			CQT_MAP.put("40115", "THO/THO.CTH");
			CQT_MAP.put("40117", "THO/THO.LCH");
			CQT_MAP.put("40119", "THO/THO.TTH");
			CQT_MAP.put("40121", "THO/THO.NLA");
			CQT_MAP.put("40123", "THO/THO.TXU");
			CQT_MAP.put("40125", "THO/THO.NXU");
			CQT_MAP.put("40127", "THO/THO.NTH");
			CQT_MAP.put("40129", "THO/THO.VLO");
			CQT_MAP.put("40131", "THO/THO.HTR");
			CQT_MAP.put("40133", "THO/THO.NSO");
			CQT_MAP.put("40135", "THO/THO.YDI");
			CQT_MAP.put("40137", "THO/THO.THX");
			CQT_MAP.put("40139", "THO/THO.HLO");
			CQT_MAP.put("40141", "THO/THO.THA");
			CQT_MAP.put("40143", "THO/THO.HHO");
			CQT_MAP.put("40145", "THO/THO.DSO");
			CQT_MAP.put("40147", "THO/THO.TSO");
			CQT_MAP.put("40149", "THO/THO.QXU");
			CQT_MAP.put("40151", "THO/THO.NCO");
			CQT_MAP.put("40153", "THO/THO.TGI");
			CQT_MAP.put("40300", "NAN/VP.NAN");
			CQT_MAP.put("40301", "NAN/NAN.VIN");
			CQT_MAP.put("40303", "NAN/NAN.CLO");
			CQT_MAP.put("40305", "NAN/NAN.QPH");
			CQT_MAP.put("40307", "NAN/NAN.QCH");
			CQT_MAP.put("40309", "NAN/NAN.KSO");
			CQT_MAP.put("40311", "NAN/NAN.QHO");
			CQT_MAP.put("40313", "NAN/NAN.NDA");
			CQT_MAP.put("40314", "NAN/NAN.THO");
			CQT_MAP.put("40315", "NAN/NAN.TDU");
			CQT_MAP.put("40317", "NAN/NAN.QLU");
			CQT_MAP.put("40319", "NAN/NAN.TKY");
			CQT_MAP.put("40321", "NAN/NAN.CCU");
			CQT_MAP.put("40323", "NAN/NAN.YTH");
			CQT_MAP.put("40325", "NAN/NAN.DCH");
			CQT_MAP.put("40327", "NAN/NAN.ASO");
			CQT_MAP.put("40329", "NAN/NAN.DLU");
			CQT_MAP.put("40331", "NAN/NAN.TCH");
			CQT_MAP.put("40333", "NAN/NAN.NLO");
			CQT_MAP.put("40335", "NAN/NAN.NAD");
			CQT_MAP.put("40337", "NAN/NAN.HNG");
			CQT_MAP.put("40339", "NAN/NAN.HMA");
			CQT_MAP.put("40500", "HTI/VP.HTI");
			CQT_MAP.put("40501", "HTI/HTI.HTI");
			CQT_MAP.put("40503", "HTI/HTI.HLI");
			CQT_MAP.put("40505", "HTI/HTI.NXU");
			CQT_MAP.put("40507", "HTI/HTI.DTH");
			CQT_MAP.put("40509", "HTI/HTI.HSO");
			CQT_MAP.put("40511", "HTI/HTI.CLO");
			CQT_MAP.put("40513", "HTI/HTI.THA");
			CQT_MAP.put("40515", "HTI/HTI.CXU");
			CQT_MAP.put("40517", "HTI/HTI.HKH");
			CQT_MAP.put("40519", "HTI/HTI.KAN");
			CQT_MAP.put("40520", "HTI/HTI.KYA");
			CQT_MAP.put("40521", "HTI/HTI.VQU");
			CQT_MAP.put("40523", "HTI/HTI.LHA");
			CQT_MAP.put("40700", "QBI/VP.QBI");
			CQT_MAP.put("40701", "QBI/QBI.DHO");
			CQT_MAP.put("40703", "QBI/QBI.THO");
			CQT_MAP.put("40705", "QBI/QBI.MHO");
			CQT_MAP.put("40707", "QBI/QBI.QTR");
			CQT_MAP.put("40709", "QBI/QBI.BTR");
			CQT_MAP.put("40711", "QBI/QBI.QNI");
			CQT_MAP.put("40713", "QBI/QBI.LTH");
			CQT_MAP.put("40715", "QBI/QBI.BDO");
			CQT_MAP.put("40900", "QTR/VP.QTR");
			CQT_MAP.put("40901", "QTR/QTR.DHA");
			CQT_MAP.put("40903", "QTR/QTR.QTR");
			CQT_MAP.put("40905", "QTR/QTR.VLI");
			CQT_MAP.put("40907", "QTR/QTR.GLI");
			CQT_MAP.put("40909", "QTR/QTR.CLO");
			CQT_MAP.put("40911", "QTR/QTR.TPH");
			CQT_MAP.put("40913", "QTR/QTR.HLA");
			CQT_MAP.put("40915", "QTR/QTR.HHO");
			CQT_MAP.put("40917", "QTR/QTR.DKR");
			CQT_MAP.put("40919", "QTR/QTR.CCO");
			CQT_MAP.put("41100", "TTH/VP.TTH");
			CQT_MAP.put("41101", "TTH/TTH.HUE");
			CQT_MAP.put("41103", "TTH/TTH.PDI");
			CQT_MAP.put("41105", "TTH/TTH.QDI");
			CQT_MAP.put("41107", "TTH/TTH.HTR");
			CQT_MAP.put("41109", "TTH/TTH.PVA");
			CQT_MAP.put("41111", "TTH/TTH.HTH");
			CQT_MAP.put("41113", "TTH/TTH.PLO");
			CQT_MAP.put("41115", "TTH/TTH.ALU");
			CQT_MAP.put("41117", "TTH/TTH.NDO");
			CQT_MAP.put("50100", "DAN/VP.DAN");
			CQT_MAP.put("50101", "DAN/DAN.HCH");
			CQT_MAP.put("50103", "DAN/DAN.TKH");
			CQT_MAP.put("50105", "DAN/DAN.STR");
			CQT_MAP.put("50107", "DAN/DAN.NHS");
			CQT_MAP.put("50109", "DAN/DAN.LCH");
			CQT_MAP.put("50111", "DAN/DAN.HVA");
			CQT_MAP.put("50113", "DAN/DAN.HSA");
			CQT_MAP.put("50115", "DAN/DAN.CLE");
			CQT_MAP.put("50300", "QNA/VP.QNA");
			CQT_MAP.put("50301", "QNA/QNA.TKY");
			CQT_MAP.put("50302", "QNA/QNA.PNI");
			CQT_MAP.put("50303", "QNA/QNA.HAN");
			CQT_MAP.put("50304", "QNA/QNA.TGI");
			CQT_MAP.put("50305", "QNA/QNA.DGI");
			CQT_MAP.put("50307", "QNA/QNA.DLO");
			CQT_MAP.put("50309", "QNA/QNA.DBA");
			CQT_MAP.put("50311", "QNA/QNA.DXU");
			CQT_MAP.put("50313", "QNA/QNA.NGI");
			CQT_MAP.put("50315", "QNA/QNA.TBI");
			CQT_MAP.put("50317", "QNA/QNA.QSO");
			CQT_MAP.put("50318", "QNA/QNA.NSO");
			CQT_MAP.put("50319", "QNA/QNA.HDU");
			CQT_MAP.put("50321", "QNA/QNA.TPH");
			CQT_MAP.put("50323", "QNA/QNA.PSO");
			CQT_MAP.put("50325", "QNA/QNA.NTH");
			CQT_MAP.put("50327", "QNA/QNA.BTM");
			CQT_MAP.put("50329", "QNA/QNA.NTM");
			CQT_MAP.put("50500", "QNG/VP.QNG");
			CQT_MAP.put("50501", "QNG/QNG.QNG");
			CQT_MAP.put("50503", "QNG/QNG.LSO");
			CQT_MAP.put("50505", "QNG/QNG.BSO");
			CQT_MAP.put("50507", "QNG/QNG.TBO");
			CQT_MAP.put("50508", "QNG/QNG.TTR");
			CQT_MAP.put("50509", "QNG/QNG.STI");
			CQT_MAP.put("50511", "QNG/QNG.STA");
			CQT_MAP.put("50513", "QNG/QNG.SHA");
			CQT_MAP.put("50515", "QNG/QNG.TNG");
			CQT_MAP.put("50517", "QNG/QNG.NHA");
			CQT_MAP.put("50519", "QNG/QNG.MLO");
			CQT_MAP.put("50521", "QNG/QNG.MDU");
			CQT_MAP.put("50523", "QNG/QNG.DPH");
			CQT_MAP.put("50525", "QNG/QNG.BTO");
			CQT_MAP.put("50700", "BDI/VP.BDI");
			CQT_MAP.put("50701", "BDI/BDI.QNH");
			CQT_MAP.put("50703", "BDI/BDI.ALA");
			CQT_MAP.put("50705", "BDI/BDI.HNH");
			CQT_MAP.put("50707", "BDI/BDI.HAN");
			CQT_MAP.put("50709", "BDI/BDI.PMY");
			CQT_MAP.put("50711", "BDI/BDI.VTH");
			CQT_MAP.put("50713", "BDI/BDI.PCA");
			CQT_MAP.put("50715", "BDI/BDI.TSO");
			CQT_MAP.put("50717", "BDI/BDI.ANH");
			CQT_MAP.put("50719", "BDI/BDI.TPH");
			CQT_MAP.put("50721", "BDI/BDI.VCA");
			CQT_MAP.put("50900", "PHY/VP.PHY");
			CQT_MAP.put("50901", "PHY/PHY.THO");
			CQT_MAP.put("50903", "PHY/PHY.DXU");
			CQT_MAP.put("50905", "PHY/PHY.SCA");
			CQT_MAP.put("50907", "PHY/PHY.TAN");
			CQT_MAP.put("50909", "PHY/PHY.SHO");
			CQT_MAP.put("50911", "PHY/PHY.DHO");
			CQT_MAP.put("50912", "PHY/PHY.TAH");
			CQT_MAP.put("50913", "PHY/PHY.SHI");
			CQT_MAP.put("50915", "PHY/PHY.PHO");
			CQT_MAP.put("51100", "KHH/VP.KHH");
			CQT_MAP.put("51101", "KHH/KHH.NTR");
			CQT_MAP.put("51103", "KHH/KHH.VNI");
			CQT_MAP.put("51105", "KHH/KHH.NHO");
			CQT_MAP.put("51107", "KHH/KHH.DKH");
			CQT_MAP.put("51109", "KHH/KHH.CRA");
			CQT_MAP.put("51111", "KHH/KHH.KVI");
			CQT_MAP.put("51113", "KHH/KHH.KSO");
			CQT_MAP.put("51115", "KHH/KHH.TSA");
			CQT_MAP.put("51117", "KHH/KHH.CLA");
			CQT_MAP.put("60100", "KTU/VP.KTU");
			CQT_MAP.put("60101", "KTU/KTU.KTU");
			CQT_MAP.put("60103", "KTU/KTU.DGL");
			CQT_MAP.put("60105", "KTU/KTU.NHO");
			CQT_MAP.put("60107", "KTU/KTU.DTO");
			CQT_MAP.put("60108", "KTU/KTU.KRA");
			CQT_MAP.put("60109", "KTU/KTU.KPL");
			CQT_MAP.put("60111", "KTU/KTU.DHA");
			CQT_MAP.put("60113", "KTU/KTU.STH");
			CQT_MAP.put("60114", "KTU/KTU.IHD");
			CQT_MAP.put("60115", "KTU/KTU.TMR");
			CQT_MAP.put("60300", "GLA/VP.GLA");
			CQT_MAP.put("60301", "GLA/GLA.PLE");
			CQT_MAP.put("60303", "GLA/GLA.KBA");
			CQT_MAP.put("60305", "GLA/GLA.MYA");
			CQT_MAP.put("60307", "GLA/GLA.CPA");
			CQT_MAP.put("60309", "GLA/GLA.IGR");
			CQT_MAP.put("60311", "GLA/GLA.AKH");
			CQT_MAP.put("60313", "GLA/GLA.KCH");
			CQT_MAP.put("60315", "GLA/GLA.DCO");
			CQT_MAP.put("60317", "GLA/GLA.CPR");
			CQT_MAP.put("60319", "GLA/GLA.CSE");
			CQT_MAP.put("60320", "GLA/GLA.IPA");
			CQT_MAP.put("60321", "GLA/GLA.APA");
			CQT_MAP.put("60323", "GLA/GLA.KPA");
			CQT_MAP.put("60325", "GLA/GLA.DDO");
			CQT_MAP.put("60327", "GLA/GLA.DPO");
			CQT_MAP.put("60329", "GLA/GLA.PTH");
			CQT_MAP.put("60331", "GLA/GLA.CPU");
			CQT_MAP.put("60500", "DLA/VP.DLA");
			CQT_MAP.put("60501", "DLA/DLA.BMT");
			CQT_MAP.put("60503", "DLA/DLA.EHL");
			CQT_MAP.put("60505", "DLA/DLA.ESU");
			CQT_MAP.put("60507", "DLA/DLA.KNA");
			CQT_MAP.put("60509", "DLA/DLA.BHO");
			CQT_MAP.put("60511", "DLA/DLA.BDO");
			CQT_MAP.put("60513", "DLA/DLA.CMG");
			CQT_MAP.put("60515", "DLA/DLA.EKA");
			CQT_MAP.put("60517", "DLA/DLA.MDR");
			CQT_MAP.put("60519", "DLA/DLA.KPA");
			CQT_MAP.put("60523", "DLA/DLA.KAN");
			CQT_MAP.put("60525", "DLA/DLA.KBO");
			CQT_MAP.put("60531", "DLA/DLA.LAK");
			CQT_MAP.put("60537", "DLA/DLA.CKU");
			CQT_MAP.put("60539", "DLA/DLA.KBU");
			CQT_MAP.put("60600", "DNO/VP.DNO");
			CQT_MAP.put("60603", "DNO/DNO.CJU");
			CQT_MAP.put("60605", "DNO/DNO.KNO");
			CQT_MAP.put("60607", "DNO/DNO.DMI");
			CQT_MAP.put("60609", "DNO/DNO.DSO");
			CQT_MAP.put("60611", "DNO/DNO.DRL");
			CQT_MAP.put("60613", "DNO/DNO.GNG");
			CQT_MAP.put("60615", "DNO/DNO.GLO");
			CQT_MAP.put("60617", "DNO/DNO.TDU");
			CQT_MAP.put("70100", "HCM/VP.HCM");
			CQT_MAP.put("70101", "HCM/HCM.QMO");
			CQT_MAP.put("70103", "HCM/HCM.QHA");
			CQT_MAP.put("70105", "HCM/HCM.QBA");
			CQT_MAP.put("70107", "HCM/HCM.QBO");
			CQT_MAP.put("70109", "HCM/HCM.QNA");
			CQT_MAP.put("70111", "HCM/HCM.QSA");
			CQT_MAP.put("70113", "HCM/HCM.QUB");
			CQT_MAP.put("70115", "HCM/HCM.QTA");
			CQT_MAP.put("70117", "HCM/HCM.QCH");
			CQT_MAP.put("70119", "HCM/HCM.QMU");
			CQT_MAP.put("70121", "HCM/HCM.QMM");
			CQT_MAP.put("70123", "HCM/HCM.QMH");
			CQT_MAP.put("70125", "HCM/HCM.GVA");
			CQT_MAP.put("70127", "HCM/HCM.TBI");
			CQT_MAP.put("70128", "HCM/HCM.TPH");
			CQT_MAP.put("70129", "HCM/HCM.BTH");
			CQT_MAP.put("70131", "HCM/HCM.PNH");
			CQT_MAP.put("70133", "HCM/HCM.TDU");
			CQT_MAP.put("70134", "HCM/HCM.BTA");
			CQT_MAP.put("70135", "HCM/HCM.CCH");
			CQT_MAP.put("70137", "HCM/HCM.HMO");
			CQT_MAP.put("70139", "HCM/HCM.BCH");
			CQT_MAP.put("70141", "HCM/HCM.NBE");
			CQT_MAP.put("70143", "HCM/HCM.CGI");
			CQT_MAP.put("70300", "LDO/VP.LDO");
			CQT_MAP.put("70301", "LDO/LDO.DLA");
			CQT_MAP.put("70303", "LDO/LDO.BLO");
			CQT_MAP.put("70305", "LDO/LDO.LDU");
			CQT_MAP.put("70307", "LDO/LDO.DDU");
			CQT_MAP.put("70309", "LDO/LDO.DTR");
			CQT_MAP.put("70311", "LDO/LDO.LHA");
			CQT_MAP.put("70313", "LDO/LDO.BLA");
			CQT_MAP.put("70315", "LDO/LDO.DLI");
			CQT_MAP.put("70317", "LDO/LDO.DHU");
			CQT_MAP.put("70319", "LDO/LDO.DTE");
			CQT_MAP.put("70321", "LDO/LDO.CTI");
			CQT_MAP.put("70323", "LDO/LDO.DRO");
			CQT_MAP.put("70500", "NTH/VP.NTH");
			CQT_MAP.put("70501", "NTH/NTH.PRA");
			CQT_MAP.put("70503", "NTH/NTH.NSO");
			CQT_MAP.put("70505", "NTH/NTH.NHA");
			CQT_MAP.put("70507", "NTH/NTH.NPH");
			CQT_MAP.put("70509", "NTH/NTH.BAI");
			CQT_MAP.put("70511", "NTH/NTH.TBA");
			CQT_MAP.put("70513", "NTH/NTH.TNA");
			CQT_MAP.put("70700", "BPH/VP.BPH");
			CQT_MAP.put("70701", "BPH/BPH.DPH");
			CQT_MAP.put("70703", "BPH/BPH.PLO");
			CQT_MAP.put("70705", "BPH/BPH.LNI");
			CQT_MAP.put("70706", "BPH/BPH.BDO");
			CQT_MAP.put("70707", "BPH/BPH.BDA");
			CQT_MAP.put("70709", "BPH/BPH.BLO");
			CQT_MAP.put("70710", "BPH/BPH.CHO");
			CQT_MAP.put("70711", "BPH/BPH.DXO");
			CQT_MAP.put("70713", "BPH/BPH.HQU");
			CQT_MAP.put("70715", "BPH/BPH.BGM");
			CQT_MAP.put("70716", "BPH/BPH.PRI");
			CQT_MAP.put("70900", "TNI/VP.TNI");
			CQT_MAP.put("70901", "TNI/TNI.TNI");
			CQT_MAP.put("70903", "TNI/TNI.TBI");
			CQT_MAP.put("70905", "TNI/TNI.TCH");
			CQT_MAP.put("70907", "TNI/TNI.DMC");
			CQT_MAP.put("70909", "TNI/TNI.CTH");
			CQT_MAP.put("70911", "TNI/TNI.HTH");
			CQT_MAP.put("70913", "TNI/TNI.BCA");
			CQT_MAP.put("70915", "TNI/TNI.GDA");
			CQT_MAP.put("70917", "TNI/TNI.TBA");
			CQT_MAP.put("71100", "BDU/VP.BDU");
			CQT_MAP.put("71101", "BDU/BDU.TDM");
			CQT_MAP.put("71103", "BDU/BDU.BCA");
			CQT_MAP.put("71105", "BDU/BDU.TUY");
			CQT_MAP.put("71107", "BDU/BDU.TAN");
			CQT_MAP.put("71109", "BDU/BDU.DAN");
			CQT_MAP.put("71111", "BDU/BDU.PGI");
			CQT_MAP.put("71113", "BDU/BDU.DTI");
			CQT_MAP.put("71115", "BDU/BDU.BBA");
			CQT_MAP.put("71117", "BDU/BDU.BTU");
			CQT_MAP.put("71300", "DON/VP.DON");
			CQT_MAP.put("71301", "DON/DON.BHO");
			CQT_MAP.put("71302", "DON/DON.LKH");
			CQT_MAP.put("71303", "DON/DON.TPH");
			CQT_MAP.put("71305", "DON/DON.DQU");
			CQT_MAP.put("71307", "DON/DON.VCU");
			CQT_MAP.put("71308", "DON/DON.TBO");
			CQT_MAP.put("71309", "DON/DON.TNH");
			CQT_MAP.put("71311", "DON/DON.CMY");
			CQT_MAP.put("71313", "DON/DON.XLO");
			CQT_MAP.put("71315", "DON/DON.LTH");
			CQT_MAP.put("71317", "DON/DON.NTR");
			CQT_MAP.put("71500", "BTH/VP.BTH");
			CQT_MAP.put("71501", "BTH/BTH.PTH");
			CQT_MAP.put("71503", "BTH/BTH.TPH");
			CQT_MAP.put("71505", "BTH/BTH.BBI");
			CQT_MAP.put("71507", "BTH/BTH.HTB");
			CQT_MAP.put("71509", "BTH/BTH.HTN");
			CQT_MAP.put("71511", "BTH/BTH.TLI");
			CQT_MAP.put("71513", "BTH/BTH.LGI");
			CQT_MAP.put("71514", "BTH/BTH.HTA");
			CQT_MAP.put("71515", "BTH/BTH.DLI");
			CQT_MAP.put("71517", "BTH/BTH.PQU");
			CQT_MAP.put("71700", "BRV/VP.BRV");
			CQT_MAP.put("71701", "BRV/BRV.VTA");
			CQT_MAP.put("71703", "BRV/BRV.BRI");
			CQT_MAP.put("71705", "BRV/BRV.CDU");
			CQT_MAP.put("71707", "BRV/BRV.XMO");
			CQT_MAP.put("71709", "BRV/BRV.TTH");
			CQT_MAP.put("71711", "BRV/BRV.LDI");
			CQT_MAP.put("71712", "BRV/BRV.DDO");
			CQT_MAP.put("71713", "BRV/BRV.CDA");
			CQT_MAP.put("80100", "LAN/VP.LAN");
			CQT_MAP.put("80101", "LAN/LAN.TAN");
			CQT_MAP.put("80103", "LAN/LAN.THU");
			CQT_MAP.put("80105", "LAN/LAN.VHU");
			CQT_MAP.put("80107", "LAN/LAN.MHO");
			CQT_MAP.put("80109", "LAN/LAN.TTH");
			CQT_MAP.put("80111", "LAN/LAN.THO");
			CQT_MAP.put("80113", "LAN/LAN.DHU");
			CQT_MAP.put("80115", "LAN/LAN.DHO");
			CQT_MAP.put("80117", "LAN/LAN.BLU");
			CQT_MAP.put("80119", "LAN/LAN.THT");
			CQT_MAP.put("80121", "LAN/LAN.CTH");
			CQT_MAP.put("80123", "LAN/LAN.TTR");
			CQT_MAP.put("80125", "LAN/LAN.CDU");
			CQT_MAP.put("80127", "LAN/LAN.CGI");
			CQT_MAP.put("80129", "LAN/LAN.KTU");
			CQT_MAP.put("80300", "DTH/VP.DTH");
			CQT_MAP.put("80301", "DTH/DTH.CLA");
			CQT_MAP.put("80303", "DTH/DTH.SDE");
			CQT_MAP.put("80305", "DTH/DTH.THO");
			CQT_MAP.put("80307", "DTH/DTH.HNG");
			CQT_MAP.put("80309", "DTH/DTH.TNO");
			CQT_MAP.put("80311", "DTH/DTH.TBI");
			CQT_MAP.put("80313", "DTH/DTH.TMU");
			CQT_MAP.put("80315", "DTH/DTH.CAL");
			CQT_MAP.put("80317", "DTH/DTH.LVO");
			CQT_MAP.put("80319", "DTH/DTH.LVU");
			CQT_MAP.put("80321", "DTH/DTH.CTH");
			CQT_MAP.put("80323", "DTH/DTH.HNU");
			CQT_MAP.put("80500", "AGI/VP.AGI");
			CQT_MAP.put("80501", "AGI/AGI.LXU");
			CQT_MAP.put("80503", "AGI/AGI.CDO");
			CQT_MAP.put("80505", "AGI/AGI.APH");
			CQT_MAP.put("80507", "AGI/AGI.TCH");
			CQT_MAP.put("80509", "AGI/AGI.PTA");
			CQT_MAP.put("80511", "AGI/AGI.CPH");
			CQT_MAP.put("80513", "AGI/AGI.TBI");
			CQT_MAP.put("80515", "AGI/AGI.TTO");
			CQT_MAP.put("80517", "AGI/AGI.CMO");
			CQT_MAP.put("80519", "AGI/AGI.CTH");
			CQT_MAP.put("80521", "AGI/AGI.TSO");
			CQT_MAP.put("80700", "TGI/VP.TGI");
			CQT_MAP.put("80701", "TGI/TGI.MTH");
			CQT_MAP.put("80703", "TGI/TGI.GCO");
			CQT_MAP.put("80705", "TGI/TGI.TPH");
			CQT_MAP.put("80707", "TGI/TGI.CTH");
			CQT_MAP.put("80709", "TGI/TGI.CLA");
			CQT_MAP.put("80711", "TGI/TGI.CGO");
			CQT_MAP.put("80713", "TGI/TGI.CBE");
			CQT_MAP.put("80715", "TGI/TGI.GCT");
			CQT_MAP.put("80717", "TGI/TGI.GCD");
			CQT_MAP.put("80719", "TGI/TGI.TPD");
			CQT_MAP.put("80721", "TGI/TGI.CAL");
			CQT_MAP.put("80900", "VLO/VP.VLO");
			CQT_MAP.put("80901", "VLO/VLO.VLO");
			CQT_MAP.put("80903", "VLO/VLO.LHO");
			CQT_MAP.put("80905", "VLO/VLO.MTH");
			CQT_MAP.put("80907", "VLO/VLO.BMI");
			CQT_MAP.put("80908", "VLO/VLO.BTA");
			CQT_MAP.put("80909", "VLO/VLO.TBI");
			CQT_MAP.put("80911", "VLO/VLO.TON");
			CQT_MAP.put("80913", "VLO/VLO.VLI");
			CQT_MAP.put("81100", "BTR/VP.BTR");
			CQT_MAP.put("81101", "BTR/BTR.BTR");
			CQT_MAP.put("81103", "BTR/BTR.CTH");
			CQT_MAP.put("81105", "BTR/BTR.CLA");
			CQT_MAP.put("81107", "BTR/BTR.MCA");
			CQT_MAP.put("81108", "BTR/BTR.MCB");
			CQT_MAP.put("81109", "BTR/BTR.GTR");
			CQT_MAP.put("81111", "BTR/BTR.BDA");
			CQT_MAP.put("81113", "BTR/BTR.BTI");
			CQT_MAP.put("81115", "BTR/BTR.TPH");
			CQT_MAP.put("81300", "KGI/VP.KGI");
			CQT_MAP.put("81301", "KGI/KGI.RGI");
			CQT_MAP.put("81303", "KGI/KGI.KLU");
			CQT_MAP.put("81304", "KGI/KGI.GTH");
			CQT_MAP.put("81305", "KGI/KGI.HDA");
			CQT_MAP.put("81307", "KGI/KGI.THI");
			CQT_MAP.put("81309", "KGI/KGI.CTH");
			CQT_MAP.put("81311", "KGI/KGI.GRI");
			CQT_MAP.put("81313", "KGI/KGI.GQU");
			CQT_MAP.put("81315", "KGI/KGI.ABI");
			CQT_MAP.put("81317", "KGI/KGI.AMI");
			CQT_MAP.put("81319", "KGI/KGI.VTH");
			CQT_MAP.put("81321", "KGI/KGI.PQU");
			CQT_MAP.put("81323", "KGI/KGI.KHA");
			CQT_MAP.put("81325", "KGI/KGI.HTI");
			CQT_MAP.put("81327", "KGI/KGI.UMT");
			CQT_MAP.put("81500", "CTH/VP.CTH");
			CQT_MAP.put("81503", "CTH/CTH.TNO");
			CQT_MAP.put("81505", "CTH/CTH.OMO");
			CQT_MAP.put("81519", "CTH/CTH.NKI");
			CQT_MAP.put("81521", "CTH/CTH.BTH");
			CQT_MAP.put("81523", "CTH/CTH.CRA");
			CQT_MAP.put("81525", "CTH/CTH.VTH");
			CQT_MAP.put("81527", "CTH/CTH.CDO");
			CQT_MAP.put("81529", "CTH/CTH.PDI");
			CQT_MAP.put("81531", "CTH/CTH.TLA");
			CQT_MAP.put("81600", "HAG/VP.HAG");
			CQT_MAP.put("81601", "HAG/HAG.VTH");
			CQT_MAP.put("81603", "HAG/HAG.CTA");
			CQT_MAP.put("81605", "HAG/HAG.CTH");
			CQT_MAP.put("81607", "HAG/HAG.NBA");
			CQT_MAP.put("81608", "HAG/HAG.PHI");
			CQT_MAP.put("81609", "HAG/HAG.VIT");
			CQT_MAP.put("81611", "HAG/HAG.LMY");
			CQT_MAP.put("81612", "HAG/HAG.LOM");
			CQT_MAP.put("81700", "TVI/VP.TVI");
			CQT_MAP.put("81701", "TVI/TVI.TVI");
			CQT_MAP.put("81703", "TVI/TVI.CLO");
			CQT_MAP.put("81705", "TVI/TVI.CTH");
			CQT_MAP.put("81707", "TVI/TVI.CKE");
			CQT_MAP.put("81709", "TVI/TVI.TCA");
			CQT_MAP.put("81711", "TVI/TVI.CNG");
			CQT_MAP.put("81713", "TVI/TVI.TCU");
			CQT_MAP.put("81715", "TVI/TVI.DHA");
			CQT_MAP.put("81900", "STR/VP.STR");
			CQT_MAP.put("81901", "STR/STR.STR");
			CQT_MAP.put("81903", "STR/STR.KSA");
			CQT_MAP.put("81905", "STR/STR.LPH");
			CQT_MAP.put("81906", "STR/STR.CLD");
			CQT_MAP.put("81907", "STR/STR.MTU");
			CQT_MAP.put("81909", "STR/STR.MXU");
			CQT_MAP.put("81911", "STR/STR.TTR");
			CQT_MAP.put("81912", "STR/STR.NNA");
			CQT_MAP.put("81913", "STR/STR.VCH");
			CQT_MAP.put("81915", "STR/STR.CTH");
			CQT_MAP.put("81917", "STR/STR.TDE");
			CQT_MAP.put("82100", "BLI/VP.BLI");
			CQT_MAP.put("82101", "BLI/BLI.BLI");
			CQT_MAP.put("82103", "BLI/BLI.HDA");
			CQT_MAP.put("82105", "BLI/BLI.VLO");
			CQT_MAP.put("82106", "BLI/BLI.HBI");
			CQT_MAP.put("82107", "BLI/BLI.GRA");
			CQT_MAP.put("82109", "BLI/BLI.PLO");
			CQT_MAP.put("82111", "BLI/BLI.DHA");
			CQT_MAP.put("82300", "CMA/VP.CMA");
			CQT_MAP.put("82301", "CMA/CMA.CMA");
			CQT_MAP.put("82303", "CMA/CMA.TBI");
			CQT_MAP.put("82305", "CMA/CMA.UMI");
			CQT_MAP.put("82307", "CMA/CMA.TVT");
			CQT_MAP.put("82308", "CMA/CMA.PTA");
			CQT_MAP.put("82309", "CMA/CMA.CNU");
			CQT_MAP.put("82311", "CMA/CMA.DRO");
			CQT_MAP.put("82312", "CMA/CMA.NCA");
			CQT_MAP.put("82313", "CMA/CMA.NHI");



		}
	}
	
	public static class NGHIEPVU_HOSO {

		public static class KHAI_THUE_TINH_THUE {
			//chung folder cha level nghiep vu: Khai thuế, tính thuế
			public static final String FOLDER_PARENT = "KTHUE_TTHUE";
			
			public static final String TNCN = "KTHUE_TTHUE/TNCN";
			public static final String BCAO_AC = "KTHUE_TTHUE/BCAO_AC";

			public static final String LPTB = "KTHUE_TTHUE/LPTB";
			public static final String THONG_BAO = "KTHUE_TTHUE/THONG_BAO";
			public static final String THUE_TSAN = "KTHUE_TTHUE/THUE_TSAN";
			public static final String TKHAI_DANG_KY_THUE = "KTHUE_TTHUE/DKY_THUE";
			
		}
		
		public static class AN_DINH_THUE {
			//chung folder cha level nghiep vu: Ấn định thuế
			public static final String FOLDER_PARENT = "AN_DINH_THUE";
			
			public static final String THONG_BAO = "AN_DINH_THUE/THONG_BAO";
			
		}
		public static class NOP_THUE {
			//chung folder cha level nghiep vu: Nộp thuế
			public static final String FOLDER_PARENT = "NOP_THUE";
			
			public static final String THONG_BAO = "NOP_THUE/THONG_BAO";
			
		}
		public static class UY_NHIEM_THU_THUE {
			//chung folder cha level nghiep vu: Ủy nhiệm thu thuế
			public static final String FOLDER_PARENT = "UY_NHIEM";
			
			public static final String THONG_BAO = "UY_NHIEM/THONG_BAO";
			
		}
		public static class TRACH_NHIEM_HOAN_THANH_NGHIA_VU_NOP_THUE {
			//chung folder cha level nghiep vu: Trách nhiệm hoàn thành nghĩa vụ nộp thuế
			public static final String FOLDER_PARENT = "NGHIA_VU_NOP_THUE";
			
			public static final String THONG_BAO = "NGHIA_VU_NOP_THUE/THONG_BAO";
			
		}
		public static class MIEN_GIAM_XOA_NO {
			//chung folder cha level nghiep vu: Thủ tục miễn thuế, giảm thuế; xoá nợ tiền thuế, tiền phạt
			public static final String FOLDER_PARENT = "MIEN_GIAM_THUE";
			
			public static final String THONG_BAO = "MIEN_GIAM_THUE/THONG_BAO";
			
		}
		public static class THU_TUC_HOAN_THUE {
			//chung folder cha level nghiep vu: Thủ tục hoàn thuế, bù trừ thuế
			public static final String FOLDER_PARENT = "HOAN_THUE";
			
			public static final String THONG_BAO = "HOAN_THUE/THONG_BAO";
			
		}
		public static class KIEM_TRA_THANH_TRA {
			//chung folder cha level nghiep vu: Kiểm tra thuế, thanh tra thuế, quản lý rủi ro về thuế
			public static final String FOLDER_PARENT = "KTRA_TTRA";
			
			public static final String THONG_BAO = "KTRA_TTRA/THONG_BAO";
			
		}
		
		public static class GIAI_QUYET_KHIEU_NAI {
			//chung folder cha level nghiep vu: Giải quyết khiếu nại, tố cáo, khởi kiện liên quanđến thực hiện pháp luật thuế
			public static final String FOLDER_PARENT = "GQUYET_KNAI";
			
			public static final String THONG_BAO = "GQUYET_KNAI/THONG_BAO";
			
		}
		
		public static class DICH_VU_DIEN_TU {
			//chung folder cha level nghiep vu: Đăng ký thuế
			public static final String FOLDER_PARENT = "DVU_DIEN_TU";
			
			public static final String DANG_KY_DICH_VU = "DVU_DIEN_TU/DKY_DVU";
			public static final String THONG_BAO = "DVU_DIEN_TU/THONG_BAO";
		}
		
		public static class DANG_KY_THUE {
			//chung folder cha level nghiep vu: Đăng ký thuế
			public static final String FOLDER_PARENT = "DKY_THUE";
			
			public static final String THONG_BAO = "DKY_THUE/THONG_BAO";
		}
		
		public static class QUYET_DINH_PHAT_HANH_CHINH {
			//chung folder cha level nghiep vu: Quyết định phạt hành chính
			public static final String FOLDER_PARENT = "PHAT_HANH_CHINH";
			
			public static final String THONG_BAO = "PHAT_HANH_CHINH/THONG_BAO";
		
		}
		
		public static class HOA_DON_DIEN_TU {
			//chung folder cha level Hoa don dien tu
			public static final String FOLDER_PARENT = "HDON_DTU";
			
			public static final String GTKT = "HDON_DTU/GTKT";
			public static final String BAN_HANG = "HDON_DTU/BHANG";
			public static final String XUAT_KHO_GUI_BAN_DAI_LY = "HDON_DTU/XK_DLY";
			public static final String XUAT_KHO_VAN_CHUYEN_NOI_BO = "HDON_DTU/XK_VCHUYEN_NBO";
			public static final String BAN_HANG_KHU_PHI_THUE_QUAN = "HDON_DTU/BHANG_KPHI_TQUAN";
		
		}
		

		
	}	
	
	
}
