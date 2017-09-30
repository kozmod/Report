package report.entities.items.fin_res;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.models.sql.SQLconnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.*;

public class ItemFinResDAO {


    public ObservableList<TableItemFinRes> getList() { //DATA for InfoTable

        ObservableList<TableItemFinRes> FinResTable =  FXCollections.observableArrayList();

        StringBuilder ResultSetString = new StringBuilder("execute dbo.[FinResFormula]");

        try(Connection connection = SQLconnector.getInstance();
            Statement st = connection.createStatement();) {

            ResultSet rs = st.executeQuery(ResultSetString.toString());
            System.out.println();
            while(rs.next()){

//                            System.out.println("True cost   "+trueCost+ "   sh --> " + rs.getFloat ("SmetCost"));
//                            System.out.println(rs.getObject("ITN_Client").toString());
                FinResTable.add(new TableItemFinRes(
                                rs.getString("SiteNumber"),
                                rs.getString("Contractor"),
                                rs.getString("NContract"),
                                rs.getInt   ("DateContract"),
                                rs.getInt   ("FinishBuilding"),
                                rs.getDouble ("SmetCost"),
                                rs.getDouble ("CostHouse"),
                                rs.getDouble ("SaleHouse"),
                                rs.getDouble ("Res"),
                                rs.getDouble ("CostHouse") - rs.getDouble ("SmetCost"))
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemFinResDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return FinResTable ;
    }
}

