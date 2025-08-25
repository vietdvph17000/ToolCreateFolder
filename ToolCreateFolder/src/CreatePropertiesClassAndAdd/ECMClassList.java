package CreatePropertiesClassAndAdd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ECMClassList {
	public static class PROPERTY{
		public static class DATA_TYPE{
			public static final String INT = "integer"; 
			public static final String DATE = "datetime";
			public static final String FLOAT = "float";
			//public static final String BINARY = "binary"; // Chua ho tro
			public static final String STRING = "string";
			//public static final String OBJECT = "object"; // Chua ho tro
			public static final String BOOLEAN = "boolean";
		}
	}
	
	public static class CLASS_NAME{
		public static class ICN{
			public static final List<String> Hso_Thue = Arrays.asList("Hso_Thue", "Document");
			public static final List<String> KThue_TThue = Arrays.asList("KThue_TThue", "Hso_Thue");
			public static final List<String> Dvu_DienTu_Tbao = Arrays.asList("Dvu_DienTu_Tbao", "Hso_Thue");
			public static final List<String> Dvu_DienTu = Arrays.asList("Dvu_DienTu", "Hso_Thue");
			public static final List<String> KThue_TThue_Pluc = Arrays.asList("KThue_TThue_Pluc", "Hso_Thue");
			
			public static final List<String> iCaNhanTK = Arrays.asList("iCaNhanTK", "KThue_TThue");
			public static final List<String> iCaNhanTB = Arrays.asList("iCaNhanTB", "Dvu_DienTu_Tbao");
			public static final List<String> iCaNhanDK = Arrays.asList("iCaNhanDK", "Dvu_DienTu");
			public static final List<String> iCaNhanBK = Arrays.asList("iCaNhanBK", "KThue_TThue_Pluc");

		}
		
		public static class ETAX_DN{
			
			public static final List<String> ASCDocument = Arrays.asList("ASCDocument", "Document");
			public static final List<String> ETAX_Doc = Arrays.asList("ETAX_Doc", "Document");
			public static final List<String> DKDocument = Arrays.asList("DKDocument", "ETAX_Doc");
			public static final List<String> DSDocument = Arrays.asList("DSDocument", "ETAX_Doc");
			public static final List<String> HSKDocument = Arrays.asList("HSKDocument", "ETAX_Doc");
			public static final List<String> NTDocument = Arrays.asList("NTDocument", "ETAX_Doc");
			public static final List<String> TBDocument = Arrays.asList("TBDocument", "ETAX_Doc");
			public static final List<String> TKDocument = Arrays.asList("TKDocument", "ETAX_Doc");
			
		}
	}
	
}
