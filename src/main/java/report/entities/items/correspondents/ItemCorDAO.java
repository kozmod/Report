
package report.entities.items.correspondents;

import report.usage_strings.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.models.beck.sql.SQLconnector;
import report.models.DiffList;



public class ItemCorDAO {

    public ObservableList getListCor() {
        List<Object> listAll =  FXCollections.observableArrayList();
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement prst = connection.prepareStatement("Select DISTINCT [Name_Cor] from dbo.[Cor] Order by [Name_Cor]" );) {
            prst.execute();

            ResultSet resSet = prst.getResultSet();
            while(resSet.next()){
                if(!(resSet.getObject(SQL.Cor.NAME_COR) == null))
                    listAll.add(resSet.getObject(SQL.Cor.NAME_COR));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ItemCorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (ObservableList) listAll;
    }

    public ObservableList getListAccount() {
        List<Object> listAll =  FXCollections.observableArrayList();
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement prst = connection.prepareStatement("Select DISTINCT [Name_Cor] from dbo.[Account] "
                                                + "WHERE [Name_Cor] NOT IN (SELECT [Name_Cor] from dbo.[Cor] ) "
                                                + "Order by [Name_Cor]" );) {
            prst.execute();

            ResultSet resSet = prst.getResultSet();
            while(resSet.next()){
                if(!(resSet.getObject(SQL.Cor.NAME_COR) == null))
                    listAll.add(resSet.getObject(SQL.Cor.NAME_COR));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ItemCorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (ObservableList) listAll;
    }

    
    public void delete(Collection entry) {
        try(Connection connection = SQLconnector.getInstance(); 
            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM dbo.[Cor]  WHERE [Name_Cor]= ? ");) {
            connection.setAutoCommit(false);
                 for(Object item : entry){
                    pstmt.setObject(1, item);
                    pstmt.addBatch();
                }
            pstmt.executeBatch();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ItemCorDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }

    
    public void insert(Collection entry) {
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement("INSERT into dbo.[Cor] ([Name_Cor]) values ( ? )");) {
            connection.setAutoCommit(false);
                for(Object item : entry){
                    pstmt.setObject(1, item);
                    pstmt.addBatch();
                }
            pstmt.executeBatch();
            connection.commit();           
        }  catch (SQLException ex) {
            
           Logger.getLogger(ItemCorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void dellAndInsert(Collection selectedItems, Collection temp){
        DiffList diffList = new DiffList(selectedItems,temp);
        if(diffList.exElements() != null 
           || diffList.exElements().size() > 0) delete(diffList.exElements());
        if(diffList.newElements()  != null 
           || diffList.newElements().size()  > 0) insert(diffList.newElements());     
    }
    
}
