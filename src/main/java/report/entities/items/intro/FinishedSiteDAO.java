package report.entities.items.intro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.items.KS.KS_Dao;
import report.models.sql.SqlConnector;
import report.usage_strings.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinishedSiteDAO {

    public ObservableList<FinishedSiteTIV> getList() {

        ObservableList<FinishedSiteTIV> list = FXCollections.observableArrayList();

        String psmtmtString = "SELECT  "
                + " S.[SiteNumber] "
                + ",S.[TypeHome]  "
                + ",S.[SiteTypeID]"
                + ",F.[TypeName] "
                + ",F.[SaleCost] "
                + ",F.[SmetCost] "
                + "FROM [Site] S "
                + "Inner Join [FinPlan] F ON S.[SiteTypeID] = F.[TypeID] "
                + "WHERE S.[StatusPayment] ='не оплачено' "
                + "AND S.[dell] = 0 ";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            pstmt.execute();

            ResultSet rs = pstmt.getResultSet();

            while (rs.next()) {
                list.add(new FinishedSiteTIV(
                        rs.getObject(SQL.Common.SITE_NUMBER).toString(),
                        rs.getObject(SQL.Common.TYPE_HOME).toString(),
                        rs.getDouble(SQL.Site.SMET_COST),
                        rs.getDouble(SQL.Site.SMET_COST)
                ));


            }

        } catch (SQLException ex) {
            Logger.getLogger(KS_Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
