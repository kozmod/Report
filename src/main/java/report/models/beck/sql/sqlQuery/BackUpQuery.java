/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.models.beck.sql.sqlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import report.layoutControllers.LogController;
import report.models.beck.sql.SQLconnector;
import report.usage_strings.PathStrings;

/**
 *
 * @author Gigabyte-PC
 */
public class BackUpQuery {
    
    public static void restore(String filePath){
        
//        String smtmtString ="use [master]; ALTER DATABASE [Test] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;"
//                + "use [master]; RESTORE DATABASE [Test] FROM DISK = ? "
//                + " WITH file=2,nounload,REPLACE, RECOVERY, STATS = 5 "
//                + " ALTER DATABASE [Test] SET MULTI_USER ";
        String smtmtString ="use [master]; ALTER DATABASE [Test] SET SINGLE_USER WITH ROLLBACK IMMEDIATE;"
                + "use [master]; RESTORE DATABASE [Test] FROM DISK = ? "
                + " WITH REPLACE, RECOVERY, STATS = 10 "
                + " ALTER DATABASE [Test] SET MULTI_USER ";

       
        try(Connection connection = SQLconnector.getInstance_master();
            PreparedStatement pstmt = connection.prepareStatement(smtmtString);) {
            pstmt.setString(1, filePath);
            pstmt.execute();
          
            SQLWarning warning = pstmt.getWarnings();
            while (warning != null){
                System.out.println(warning.getMessage());
                LogController.appendLogViewText(warning.getMessage());
                
                warning = warning.getNextWarning();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BackUpQuery.class.getName()).log(Level.SEVERE, null, ex);
            
        }
            
       
    }
     
    public static void backUp(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");
        LocalDateTime  now = LocalDateTime.now();
//         System.err.println(dtf.format(now));
        String psmtmtString =
            "USE [master]; BACKUP DATABASE Test TO DISK = ? "
               + "WITH FORMAT, MEDIANAME = 'Test_beckup', NAME = 'Full Backup of Test';";
       
        try(Connection connection = SQLconnector.getInstance(); 
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString)) {
            pstmt.setString(1,
                    new StringBuffer()
                            .append(System.getProperty("user.dir") )
                            .append("\\")
                            .append(PathStrings.Files.BACK_UP_SQL)
                            .append("\\BackUp_")
                            .append(dtf.format(now))
                            .append(".Bak ")
                            .toString());
            pstmt.execute();
            
            SQLWarning warning = pstmt.getWarnings();
            while (warning != null){
                System.out.println(warning.getMessage());
                LogController.appendLogViewText(warning.getMessage());
                
                warning = warning.getNextWarning();
                
                
                
            }
          
            
        } catch (SQLException ex) {
            Logger.getLogger(BackUpQuery.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public static void Drop(){
        
//         System.err.println(dtf.format(now));
        String smtString ="USE [master]; DROP DATABASE [Test]";
               
       
        try(Connection connection = SQLconnector.getInstance_master(); 
            Statement stmt = connection.createStatement();) {
            stmt.execute(smtString);
            SQLWarning warning = stmt.getWarnings();
            while (warning != null){
                System.out.println(warning.getMessage());
                warning = warning.getNextWarning();
            }
          
            
        } catch (SQLException ex) {
            Logger.getLogger(BackUpQuery.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
}
