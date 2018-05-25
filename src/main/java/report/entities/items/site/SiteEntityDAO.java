package report.entities.items.site;

import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.estimate.EstimateDAO;
import report.models.sql.SqlConnector;

import java.sql.*;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SiteEntityDAO implements CommonNamedDAO<SiteEntity> {
    private static final String TABLE_NAME = "Site_New";
    private static final int REQUIRED_FOUND_SITE_QUANTITY = 1;


    @Override
    public String getSqlTableName() {
        return TABLE_NAME;
    }

    @Override
    public <HEIR extends SiteEntity> HEIR getData() {
        throw new UnsupportedOperationException("Не реализовано");
    }


    public SiteEntity getDataByBusinessKey(String siteNumber, int countAgentId) {

        Optional<SiteEntity> siteTVI = Optional.empty();

        String sql = "SELECT " +
                "S.[id]," +
                "S.[SiteNumber]," +
                "S.[id_Count]," +
                "S.[SiteTypeID]," +
                "S.[TypeHome]," +
                "S.[NContract]," +
                "S.[DateContract]," +
                "S.[SmetCost]," +
                "S.[SumCost]," +
                "S.[FinishBuilding]," +
                "S.[CostHouse]," +
                "S.[CostSite]," +
                "S.[SaleClients]," +
                "S.[DebtClients]," +
                "S.[StatusPayment]," +
                "S.[StatusJobs]," +
                "S.[QueueBuilding]," +
                "S.[SaleHouse]," +
                "S.[NumberSession]," +
                "S.[k]," +
                "S.[dell]," +
                "S.[DateCreate], " +
                "CAN.[Name] as [CountAgentName] CAN " +
                " FROM [dbo].[Site_new] S " +
                " INNER JOIN [dbo].[DIC_Count_Name] CAN ON CAN.[id_Count] = S.[id_Count] " +
                " WHERE S.[SiteNumber] = ? AND S.[id_Count] = ? AND S.[dell] = 0";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement prst = connection.prepareStatement(sql)) {
            prst.setString(1, siteNumber);
            prst.setInt(2, countAgentId);

            if (prst.execute()) {
                int foundSites = 0;
                try (ResultSet rs = prst.getResultSet()) {
                    while (rs.next()) {
                        siteTVI = Optional.of(
                                new SiteEntity(
                                        rs.getInt("id"),
                                        rs.getString("SiteNumber"),
                                        rs.getString("TypeHome"),
                                        rs.getString("NContract"),
                                        rs.getInt("DateContract"),
                                        rs.getInt("id_Count"),
                                        rs.getInt("SiteTypeID"),
                                        rs.getString("QueueBuilding"),
                                        rs.getFloat("SmetCost"),
                                        rs.getFloat("SumCost"),
                                        rs.getFloat("CostHouse"),
                                        rs.getFloat("CostSite"),
                                        rs.getFloat("SaleClients"),
                                        rs.getFloat("DebtClients"),
                                        rs.getFloat("SaleHouse"),
                                        rs.getDouble("k"),
                                        rs.getString("StatusPayment"),
                                        rs.getString("StatusJobs"),
                                        rs.getInt("FinishBuilding")
                                )
                        );
                    }
                    foundSites++;
                    if (foundSites > REQUIRED_FOUND_SITE_QUANTITY) {
                        throw new IllegalArgumentException(
                                String.format("В результирующем сете было получено более %s сущностей Site_New", REQUIRED_FOUND_SITE_QUANTITY)
                        );
                    }

                }
            }
            ResultSet rs = prst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return siteTVI.orElseThrow(() -> new IllegalArgumentException("Участков по бизнес ключу(SiteNumber/ContractorId) не найдено"));
    }

    @Override
    public void delete(SiteEntity entry) {
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement("UPDATE [dbo].[Site_new] SET dell = 1 WHERE [id] = ? AND [dell] = 0;");) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                pstmt.setLong(1, entry.getId());

            pstmt.executeUpdate();
            //SQL commit
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(SiteEntity entry) {
        String sql ="INSERT INTO [dbo].[Site_new] " +
                "(" +
                " [SiteNumber]" +
                ",[TypeHome]" +
                ",[NContract]" +
                ",[DateContract]" +
                ",[SmetCost]" +
                ",[SumCost]" +
                ",[FinishBuilding]" +
                ",[CostHouse]" +
                ",[CostSite]" +
                ",[SaleClients]" +
                ",[DebtClients]" +
                ",[StatusPayment]" +
                ",[StatusJobs]" +
                ",[QueueBuilding]" +
                ",[SaleHouse]" +
                ",[k]" +
                ",[SiteTypeID]" +
                ",[id_Count]" +
                ")" +
                "VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
           if(Objects.nonNull(entry)){

           }
                pstmt.setString(1, entry.getSiteNumber());
                pstmt.setString(2, entry.getTypeHome());
                pstmt.setString(3, entry.getNumberOfContract());
                pstmt.setInt(4, entry.getDateOfContract());
                pstmt.setFloat(5, entry.getSmetCost());
                pstmt.setFloat(6, entry.getSumCost());
                pstmt.setInt(7, entry.getFinishBuilding());
                pstmt.setFloat(8, entry.getCostHouse());
                pstmt.setFloat(9, entry.getCostSite());
                pstmt.setFloat(10, entry.getSaleClient());
                pstmt.setFloat(11, entry.getDebtClient());
                pstmt.setString(12, entry.getStatusPayment());
                pstmt.setString(13, entry.getStatusJobs());
                pstmt.setString(14, entry.getQueueBuildings());
                pstmt.setFloat(15,entry.getSaleHouse());
                pstmt.setDouble(16, entry.getCoefficient());
                pstmt.setInt(17, entry.getSiteTypeId());
                pstmt.setInt(18, entry.getCountAgentId());

                int affectedRows = pstmt.executeUpdate();

                try (ResultSet generategKeys = pstmt.getGeneratedKeys();) {
                    if (generategKeys.next())
                        entry.setId(generategKeys.getLong(1));
                }
            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
        } catch (SQLException ex) {
            Logger.getLogger(SiteEntity.class.getName()).log(Level.SEVERE, null, ex);
        }finally {

        }

    }
}
