package report.entities.items.account;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.models.sql.SQLconnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemAccDAO {

    public ObservableList<TableItemAcc> getList(int DateFrom, int DateTo) { //DATA for InfoTable

        ObservableList<TableItemAcc> AccTable =  FXCollections.observableArrayList();

        StringBuilder ResultSetString = new StringBuilder("SELECT "
                + "[Date]"
                + ",[Num]"
                + ",[ITN_Client]"
                + ",[Name_Client]"
                + ",[AccNum_Client]"
                + ",[BIC_Cor]"
                + ",[AccNum_Cor]"
                + ",[Name_Cor]"
                + ",[VO]"
                + ",[Description]"
                + ",[Deb]"
                + ",[Cred]"
                + ",[OutgoingRest]"
                + "FROM dbo.[Account]"
                + "WHERE [dell] = 0 ");
        if(DateFrom != 0 && DateTo != 0)
            ResultSetString.append("AND [Date] BETWEEN ")
                    .append(DateFrom)
                    .append(" and ")
                    .append(DateTo);

        try(Connection connection = SQLconnector.getInstance();
            Statement st = connection.createStatement();) {
            ResultSet rs = st.executeQuery(ResultSetString.toString());
            while(rs.next()){

//                            System.out.println(rs.getObject("ITN_Client").toString());
                AccTable.add(new TableItemAcc(
                                rs.getInt   ("Date"),
                                rs.getInt   ("Num"),
                                rs.getString("ITN_Client"),
                                rs.getObject("Name_Client").toString(),
                                rs.getObject("AccNum_Client").toString(),
                                rs.getObject("BIC_Cor").toString(),
                                rs.getObject("AccNum_Cor").toString(),
                                rs.getObject("Name_Cor").toString(),
                                rs.getInt   ("VO"),
                                rs.getObject("Description").toString(),
                                rs.getDouble ("Deb"),
                                rs.getDouble ("Cred"),
                                rs.getDouble ("OutgoingRest")
                        )
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemAccDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return AccTable ;
    }


}
