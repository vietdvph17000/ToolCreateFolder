package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import createAndDeleteFolder.CreateFolderTCM;

public class TCM_Connection {
	public  Connection getConnection(){
      Connection c=null;
      try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@192.168.10.105:1521/ecmdb";
        c =DriverManager.getConnection(url,"TCM_OWNER","TCM_OWNER");     
       // c.setAutoCommit(false);
      }
      catch (Exception e) 
      {
        System.out.println(e);
      }
      return c;
    }
	
//	public Properties propertiesCEAPI = new Properties();
//	Connection connection = null;
//    public  Connection getConnection() throws Exception {
//    	InputStream input = CreateFolderTCM.class.getClassLoader().getResourceAsStream("createAndDeleteFolder/TCM.properties");
//		propertiesCEAPI.load(input);
//		String ds = propertiesCEAPI.getProperty("dataSource");
//        try {
//            InitialContext context = new InitialContext();
//            DataSource dataSource = (DataSource) context.lookup(ds);
//            connection = dataSource.getConnection();
////            connection.setAutoCommit(false);
//        } catch (NamingException | SQLException e) {
//            throw e;
//        }
//        return connection;
//    }
}
