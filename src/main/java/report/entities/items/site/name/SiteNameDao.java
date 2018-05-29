package report.entities.items.site.name;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.abstraction.CommonDao;
import report.entities.items.estimate.EstimateDao;
import report.models.sql.SqlConnector;


import java.sql.*;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SiteNameDao implements CommonDao<Collection<SiteNameTIV>> {


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
            Logger.getLogger(EstimateDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSiteNameTIV;
    }

    @Override
    public void delete(Collection<SiteNameTIV> entry) {
        throw new UnsupportedOperationException(" Не реализованно ");

    }

    @Override
    public void insert(Collection<SiteNameTIV> entry) {
        throw new UnsupportedOperationException(" Не реализованно ");
    }
}
