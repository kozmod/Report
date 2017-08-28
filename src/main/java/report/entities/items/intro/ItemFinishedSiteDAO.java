package report.entities.items.intro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.KS.ItemKSDAO;
import report.models.sql.SQLconnector;
import report.usege_strings.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ItemFinishedSiteDAO {

    public ObservableList<TableItemFinishedSite> getList(){

        ObservableList<TableItemFinishedSite> list = FXCollections.observableArrayList();

        String psmtmtString = "SELECT  "
                           + " S.[SiteNumber] "
                           + ",S.[TypeHome]  "
                           + ",S.[SiteTypeID]"
                           + ",F.[TypeName] "
                           + ",F.[SaleCost] "
                           + ",F.[SmetCost] "
                           + "FROM [Site] S "
                           + "Inner Join [FinPlan] F ON S.[SiteTypeID] = F.[TypeID] "
                           + "WHERE S.[StatusPayment] ='не оплачено' ";

        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            pstmt.execute();

            ResultSet rs = pstmt.getResultSet();

            while(rs.next()){
                list.add(new TableItemFinishedSite(
                        rs.getObject (SQL.Common.SITE_NUMBER).toString(),
                        rs.getObject (SQL.Common.TYPE_HOME).toString(),
                        rs.getDouble (SQL.Site.SMET_COST),
                        rs.getDouble (SQL.Site.SMET_COST)
                ));


            }

        } catch (SQLException ex) {
            Logger.getLogger(ItemKSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
