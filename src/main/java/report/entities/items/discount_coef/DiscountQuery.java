package report.entities.items.discount_coef;

import report.entities.CommonDAO;
import report.entities.items.expenses.TableViewItemExpensesDAO;
import report.entities.items.period.TableViewItemPeriodDAO;
import report.models.sql.SQLconnector;
import report.models_view.nodes.node_wrappers.AbstractTableWrapper;
import report.models_view.nodes.node_wrappers.DiscountTreeTableWrapper;
import report.usage_strings.SQL;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscountQuery implements CommonDAO<DiscountCoef, AbstractTableWrapper> {

    @Override
    public DiscountCoef getList() {
        DiscountCoef discountCoef = null;

        String sqlMarketRisk = " SELECT" +
                "[id] " +
                ",[SocialRisks]" +
                ",[InternalRisks]" +
                ",[ExternalRisks]" +
                ",[CyclicalChar]" +
                ",[DevStage]" +
                ",[Competition]" +
                ",[Regulation]" +
                ",[MarketBarriers] " +
                "FROM [dbo].[KD_MarketRisk] " +
                "WHERE [dell] = 0";
        String sqlSpecificRisk = "SELECT " +
                "[id] " +
                ",[KeyChar]" +
                ",[EntSize]" +
                ",[FinStruct]" +
                ",[ClientDiv]" +
                ",[OtherRisks] " +
                "FROM [dbo].[KD_SpecificRisk] " +
                "WHERE [dell] = 0";
        String sqlDiscountCoef = "SELECT  " +
                "[id] " +
                ",[RateOfReturn]" +
                ",[KD]" +
                ",[MarketRisks]" +
                ",[SpecificRisk] " +
                "FROM [dbo].[KD_Discount] " +
                " WHERE [dell] = 0";

        try(Connection connection = SQLconnector.getInstance();
            Statement stm = connection.createStatement()) {
            //set false SQL Autocommit
            MarketRisk mr = null;
            SpecificRisk sr = null;
            try(ResultSet rs =  stm.executeQuery(sqlMarketRisk)){

                while(rs.next())
                    mr = new MarketRisk(
                            rs.getLong  (SQL.Common.ID),
                            rs.getDouble(MarketRisk.SQL.SOCIAL_RISKS),
                            rs.getDouble(MarketRisk.SQL.INTERNAL_RISKS),
                            rs.getDouble(MarketRisk.SQL.EXTERNAL_RISKS),
                            rs.getDouble(MarketRisk.SQL.CYCLICAL_CHAR),
                            rs.getDouble(MarketRisk.SQL.DEV_STAGE),
                            rs.getDouble(MarketRisk.SQL.COMPETITION),
                            rs.getDouble(MarketRisk.SQL.REGULATION),
                            rs.getDouble(MarketRisk.SQL.MARKET_BARRIERS)
                    );
            }
            try(ResultSet rs =  stm.executeQuery(sqlSpecificRisk)){
                while(rs.next())
                sr = new SpecificRisk(
                        rs.getLong  (SQL.Common.ID),
                        rs.getDouble(SpecificRisk.SQL.KEY_CHAR),
                        rs.getDouble(SpecificRisk.SQL.ENTERPRISE_SIZE),
                        rs.getDouble(SpecificRisk.SQL.FIN_STRUCT),
                        rs.getDouble(SpecificRisk.SQL.CLIENT_DIV),
                        rs.getDouble(SpecificRisk.SQL.OTHER_RISKS)
                );
            }

            try(ResultSet rs =  stm.executeQuery(sqlDiscountCoef)){
                while(rs.next())
                    discountCoef = new DiscountCoef(
                        rs.getLong  (SQL.Common.ID),
                            rs.getDouble(DiscountCoef.SQL.RATE_OF_RETURN),
                            sr,
                            mr
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemPeriodDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  discountCoef;
    }


    public void delete(DiscountCoef disCoef){
        String sqlMarketRisk   = "update [dbo].[KD_MarketRisk]   SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        String sqlSpecificRisk = "update [dbo].[KD_SpecificRisk] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        String sqlDiscountCoef = "update [dbo].[KD_Discount]     SET dell = 1 WHERE [id] = ? AND [dell] = 0;";

        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement psMarketRisk = connection.prepareStatement(sqlMarketRisk);
            PreparedStatement psSpecificRisk= connection.prepareStatement(sqlSpecificRisk);
            PreparedStatement psDiscount = connection.prepareStatement(sqlDiscountCoef);
            ) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);

            psMarketRisk.setLong(1,disCoef.marketRisk().getId());
            psMarketRisk.executeUpdate();
            psSpecificRisk.setLong(1,disCoef.specificRisk().getId());
            psSpecificRisk.executeUpdate();
            psMarketRisk.setLong(1,disCoef.getId());
            psMarketRisk.executeUpdate();
            //SQL commit
            connection.commit();
            //add info to LogTextArea /LogController
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(DiscountCoef entry) {

    }

    @Override
    public String getTableString() {
        return null;
    }
}
