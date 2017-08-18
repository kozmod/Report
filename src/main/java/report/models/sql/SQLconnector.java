
package report.models.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class SQLconnector {
    
    private static SQLconnector instance;
    
    private SQLconnector(){ };
    
    
    public static Connection getInstance(){
        if(instance == null)instance = new SQLconnector();
        return instance.connectToSQL();
    }
    
    public static Connection getInstance_master(){
        if(instance == null)instance = new SQLconnector();
        return instance.connectToSQL_master();
    }
 
    
    //connect  to Test
    private Connection connectToSQL(){
        Connection connection = null;
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Test;";
            connection = DriverManager.getConnection(url,"User","123456");
//            System.out.println(connection.isClosed());
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
           
//            Logger.getLogger(SQLconnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex);
//            Logger.getLogger(SQLconnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }
    
    
    //connect  to MASTER
    private  Connection connectToSQL_master(){
        Connection connection = null;
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=master;";
            connection = DriverManager.getConnection(url,"User","123456");
            
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
           
//            Logger.getLogger(SQLconnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex);
//            Logger.getLogger(SQLconnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

  
}
