package report.entities.items.discount_coef;

import report.entities.abstraction.dao.MementoDao;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.expenses.ExpensesDao;
import report.entities.items.period.PeriodDao;
import report.models.sql.SqlConnector;
import report.usage_strings.SQL;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscountQuery implements MementoDao<DiscountCoef> {

    @Override
    public DiscountCoef getData() {
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

        try (Connection connection = SqlConnector.getInstance();
             Statement stm = connection.createStatement()) {
            //set false SQL Autocommit
            MarketRisk mr = null;
            SpecificRisk sr = null;
            try (ResultSet rs = stm.executeQuery(sqlMarketRisk)) {

                while (rs.next())
                    mr = new MarketRisk(
                            rs.getLong(SQL.Common.ID),
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
            try (ResultSet rs = stm.executeQuery(sqlSpecificRisk)) {
                while (rs.next())
                    sr = new SpecificRisk(
                            rs.getLong(SQL.Common.ID),
                            rs.getDouble(SpecificRisk.SQL.KEY_CHAR),
                            rs.getDouble(SpecificRisk.SQL.ENTERPRISE_SIZE),
                            rs.getDouble(SpecificRisk.SQL.FIN_STRUCT),
                            rs.getDouble(SpecificRisk.SQL.CLIENT_DIV),
                            rs.getDouble(SpecificRisk.SQL.OTHER_RISKS)
                    );
            }

            try (ResultSet rs = stm.executeQuery(sqlDiscountCoef)) {
                while (rs.next())
                    discountCoef = new DiscountCoef(
                            rs.getLong(SQL.Common.ID),
                            rs.getDouble(DiscountCoef.SQL.RATE_OF_RETURN),
                            sr,
                            mr
                    );
            }

        } catch (SQLException ex) {
            Logger.getLogger(PeriodDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return discountCoef;
    }


    public void delete(DiscountCoef disCoef) {
        System.out.println(disCoef.toString());
        String sqlMarketRisk = "UPDATE [dbo].[KD_MarketRisk]   SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        String sqlSpecificRisk = "UPDATE [dbo].[KD_SpecificRisk] SET dell = 1 WHERE [id] = ? AND [dell] = 0;";
        String sqlDiscountCoef = "UPDATE [dbo].[KD_Discount]     SET dell = 1 WHERE [id] = ? AND [dell] = 0;";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement psMarketRisk = connection.prepareStatement(sqlMarketRisk);
             PreparedStatement psSpecificRisk = connection.prepareStatement(sqlSpecificRisk);
             PreparedStatement psDiscount = connection.prepareStatement(sqlDiscountCoef);
        ) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);

            psMarketRisk.setLong(1, disCoef.marketRisk().getId());
            psMarketRisk.executeUpdate();
            psSpecificRisk.setLong(1, disCoef.specificRisk().getId());
            psSpecificRisk.executeUpdate();
            psDiscount.setLong(1, disCoef.getId());
            psDiscount.executeUpdate();
            //SQL commit
            connection.commit();
            //add info to LogTextArea /LogController
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(DiscountCoef discountCoef) {
        String sqlDiscountCoef = "INSERT INTO [dbo].[KD_Discount] " +
                "([RateOfReturn]" +
                ",[KD]" +
                ",[MarketRisks]" +
                ",[SpecificRisk]" +
                ",[dell]" +
                ")" +
                " VALUES(?,?,?,?,0)";

        String sqlMarketRisk = "INSERT INTO [dbo].[KD_MarketRisk] " +
                "([SocialRisks]" +
                ",[InternalRisks]" +
                ",[ExternalRisks]" +
                ",[CyclicalChar]" +
                ",[DevStage]" +
                ",[Competition]" +
                ",[Regulation]" +
                ",[MarketBarriers]" +
                ",[dell]" +
                ")" +
                " VALUES (?,?,?,?,?,?,?,?,0)";
        String sqlSpecificRisk = "INSERT INTO [dbo].[KD_SpecificRisk] " +
                "([KeyChar]" +
                ",[EntSize]" +
                ",[FinStruct]" +
                ",[ClientDiv]" +
                ",[OtherRisks]" +
                ",[dell]" +
                ")" +
                "  VALUES (?,?,?,?,?,0)";


        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement psMarketRisk = connection.prepareStatement(sqlMarketRisk, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psSpecificRisk = connection.prepareStatement(sqlSpecificRisk, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psDiscount = connection.prepareStatement(sqlDiscountCoef, Statement.RETURN_GENERATED_KEYS)) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            psMarketRisk.setDouble(1, discountCoef.marketRisk().getSocial());
            psMarketRisk.setDouble(2, discountCoef.marketRisk().getInternal());
            psMarketRisk.setDouble(3, discountCoef.marketRisk().getExternal());
            psMarketRisk.setDouble(4, discountCoef.marketRisk().getCyclicalChar());
            psMarketRisk.setDouble(5, discountCoef.marketRisk().getDevStage());
            psMarketRisk.setDouble(6, discountCoef.marketRisk().getCompetition());
            psMarketRisk.setDouble(7, discountCoef.marketRisk().getRegulation());
            psMarketRisk.setDouble(8, discountCoef.marketRisk().getMarketBarriers());
            psMarketRisk.executeUpdate();
//            try( ResultSet mrKeys = psMarketRisk.getGeneratedKeys()){
//                if(mrKeys.next()){
//                    discountCoef.marketRisk().setId(mrKeys.getLong(1));
//                }
//            }
            psSpecificRisk.setDouble(1, discountCoef.specificRisk().getKeyChar());
            psSpecificRisk.setDouble(2, discountCoef.specificRisk().getEntSize());
            psSpecificRisk.setDouble(3, discountCoef.specificRisk().getFinStruct());
            psSpecificRisk.setDouble(4, discountCoef.specificRisk().getClientDiv());
            psSpecificRisk.setDouble(5, discountCoef.specificRisk().getOtherRisks());
            psSpecificRisk.executeUpdate();
//            try(ResultSet srKeys = psSpecificRisk.getGeneratedKeys()){
//                if( srKeys.next() ){
//                    discountCoef.specificRisk().setId(srKeys.getLong(1));
//                }
//            }
            psDiscount.setDouble(1, discountCoef.rateOfReturnProperty().get());
            psDiscount.setDouble(2, discountCoef.kdProperty().get());
            psDiscount.setDouble(3, discountCoef.marketRisk().valueProperty().get());
            psDiscount.setDouble(4, discountCoef.specificRisk().valueProperty().get());
            psDiscount.executeUpdate();
            //SQL commit
//            try( ResultSet dcKeys = psDiscount.getGeneratedKeys()){
//                if( dcKeys.next()){
//                    discountCoef.setId(dcKeys.getLong(1));
//                }
//            }
            connection.commit();
            try (ResultSet mrKeys = psMarketRisk.getGeneratedKeys();
                 ResultSet srKeys = psSpecificRisk.getGeneratedKeys();
                 ResultSet dcKeys = psDiscount.getGeneratedKeys()) {
                if (mrKeys.next() & srKeys.next() & dcKeys.next()) {
                    discountCoef.marketRisk().setId(mrKeys.getLong(1));
                    discountCoef.specificRisk().setId(srKeys.getLong(1));
                    discountCoef.setId(dcKeys.getLong(1));
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(EstimateDao.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
