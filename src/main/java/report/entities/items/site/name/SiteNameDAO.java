package report.entities.items.site.name;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.abstraction.CommonDAO;
import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.estimate.EstimateDAO;
import report.entities.items.estimate.EstimateTVI;
import report.models.sql.SqlConnector;
import report.usage_strings.SQL;


import java.sql.*;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SiteNameDAO implements CommonDAO<Collection<SiteNameTIV>> {


    @Override
    public ObservableList<SiteNameTIV> getData() {
        return null;
    }


    public ObservableList<SiteNameTIV> getData(String contractorName) {

        ObservableList<SiteNameTIV> listSiteNameTIV = FXCollections.observableArrayList();
        String sql = "SELECT distinct [SiteNumber],[Contractor]  FROM [dbo].[Site] WHERE  [Contractor] = ? and dell = 0";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement prst = connection.prepareStatement(sql)) {
            prst.setString(1, contractorName);
            ResultSet rs = prst.executeQuery();

            while (rs.next()) {
                SiteNameTIV item = new SiteNameTIV(
                        rs.getString("SiteNumber")
                );
                listSiteNameTIV.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSiteNameTIV;
    }

    @Override
    public void delete(Collection<SiteNameTIV> entry) {

    }

    @Override
    public void insert(Collection<SiteNameTIV> entry) {

    }
}
