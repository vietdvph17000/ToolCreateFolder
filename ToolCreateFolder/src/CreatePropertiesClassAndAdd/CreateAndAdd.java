package CreatePropertiesClassAndAdd;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import createAndDeleteFolder.ReadXLS;
import createAndDeleteFolder.createAndDelete;


public class CreateAndAdd {

	public static void main(String[] args) {
	
		//8.Create Class Define
//		createAndDelete p8 = new createAndDelete();
//		System.out.println(p8.createDocClassDefine("Hso_Thue", "Document"));
//		System.out.println(p8.createDocClassDefine("KThue_TThue", "Hso_Thue"));
//		System.out.println(p8.createDocClassDefine("Dvu_DienTu_Tbao", "Hso_Thue"));
//		System.out.println(p8.createDocClassDefine("Dvu_DienTu", "Hso_Thue"));
//		System.out.println(p8.createDocClassDefine("KThue_TThue_Pluc", "Hso_Thue"));
//		System.out.println(p8.createDocClassDefine("iCaNhanTK", "KThue_TThue"));
//		System.out.println(p8.createDocClassDefine("iCaNhanTB", "Dvu_DienTu_Tbao"));
//		System.out.println(p8.createDocClassDefine("iCaNhanDK", "Dvu_DienTu"));
//		System.out.println(p8.createDocClassDefine("iCaNhanBK", "KThue_TThue_Pluc"));
//		System.out.println(p8.createDocClassDefine("ASCDocument", "Document"));
//		System.out.println(p8.createDocClassDefine("ETAX_Doc", "Document"));
//		System.out.println(p8.createDocClassDefine("DKDocument", "ETAX_Doc"));
//		System.out.println(p8.createDocClassDefine("DSDocument", "ETAX_Doc"));
//		System.out.println(p8.createDocClassDefine("HSKDocument", "ETAX_Doc"));
//		System.out.println(p8.createDocClassDefine("NTDocument", "ETAX_Doc"));
//		System.out.println(p8.createDocClassDefine("TBDocument", "ETAX_Doc"));
//		System.out.println(p8.createDocClassDefine("TKDocument", "ETAX_Doc"));
//		System.out.println("------------ CREATE CLASS END --------------");
		
		//9. Create Property Template
//		createAndDelete p8 = new createAndDelete();
//		ReadXLS readFileXLS = new ReadXLS();
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\AllProperties\\pros.xls";
//		File testRead = new File(sPath);
//		ArrayList<Object> arr = readFileXLS.readXLS(testRead);
//		Iterator it = arr.iterator();
//		while (it.hasNext()) {
//			ArrayList<String> member = (ArrayList<String>)it.next(); 
//			p8.createProperty(member.get(0),member.get(1), member.get(2), member.get(3));
//		}	
		
		//10. Add properties into Class
//		createAndDelete p8 = new createAndDelete();
//		ReadXLS readFileXLS = new ReadXLS();

//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_dn\\Class\\ASCDocument.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_dn\\Class\\DKDocument.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_dn\\Class\\DSDocument.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_dn\\Class\\HSKDocument.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_dn\\Class\\NTDocument.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_dn\\Class\\TBDocument.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_dn\\Class\\TKDocument.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\HSo_Thue.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\Dvu_DienTu.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\Dvu_DienTu_Tbao.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\KThue_TThue.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\KThue_TThue_PLuc.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\iCaNhanBK.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\iCaNhanDK.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\iCaNhanTB.xls";
//		String sPath = "C:\\Users\\Administrator\\Desktop\\Scrip Create\\ForAllProjects\\etax_cn\\Class\\iCaNhanTK.xls";
//		File testRead = new File(sPath);
//		testRead.getName().substring(0,testRead.getName().length()-4);
//		ArrayList<Object> arr = readFileXLS.readXLS(testRead);
//		p8.addPropIntoClass(arr, testRead.getName().substring(0,testRead.getName().length()-4));
	}

}
