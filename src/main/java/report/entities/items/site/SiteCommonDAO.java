/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.entities.items.site;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.usage_strings.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TreeItem;
import report.models.sql.SqlConnector;


public class SiteCommonDAO {

    public SiteCommonDAO() {
    }

    /**
     * Get ArrayList to TreeView Of Sites and make TreeViewItem.
     *
     * @return
     */
    public TreeItem<String> getTreeObsList(String queueValue, String payment, String siteNumber) {    //DATA for TREEVIEW

        long lBegin = System.currentTimeMillis();

        ArrayList<PreviewTIV> siteList = new ArrayList<>();

        String psmtmtString = "SELECT [id],[SiteNumber], [Contractor]  FROM dbo.[Site] "
                + "WHERE [StatusPayment] LIKE ? "
                + "AND [QueueBuilding] LIKE ? "
                + "AND [SiteNumber] LIKE ? "
                + "AND [dell] = 0 "                                           //dell 0 - false / 1 - true
                + "ORDER BY [SiteNumber]";
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(psmtmtString)) {
            pstmt.setString(1, payment);
            pstmt.setString(2, queueValue);
            pstmt.setString(3, siteNumber);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                siteList.add(new PreviewTIV(
                        rs.getLong(SQL.Common.ID),
                        "",
                        rs.getObject(SQL.Common.SITE_NUMBER).toString(),
                        rs.getObject(SQL.Common.CONTRACTOR).toString()));

            }
        } catch (SQLException ex) {
            System.err.println("report.models.treeViewModel.getSiteData()");
            Logger.getLogger(SiteCommonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        TreeItem<String> rootNode = new TreeItem<>("Участки №");

        rootNode.setExpanded(true);
        for (PreviewTIV site : siteList) {
            TreeItem<String> ContratorLeaf = new TreeItem<String>(site.getSecondValue().toString());
            boolean found = false;
            for (TreeItem<String> siteNode : rootNode.getChildren()) {
                if (siteNode.getValue().contentEquals(site.getFirstValue())) {
                    siteNode.getChildren().add(ContratorLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> siteNode = new TreeItem<String>(site.getFirstValue());
                rootNode.getChildren().add(siteNode);
                siteNode.getChildren().add(ContratorLeaf);
            }
        }

        long lEnd = System.currentTimeMillis();
        System.out.println(lEnd - lBegin);

        return rootNode;
    }

    /**
     * Insert into SQL One "Nude" Site.
     *
     * @param siteNumb
     * @param queueNumb
     */
    public void insertSite(String siteNumb, String queueNumb, String siteType, String typeHome, int contractorConstId) {

        String psmtmtString = "INSERT INTO dbo.[Site_new] ("
                + "[SiteNumber]"
                + ",[QueueBuilding]"
                + ",[TypeHome]"
                + ",[id_Count]"
                + ",[StatusPayment]"
                + ",[NContract]"
                + ",[DateContract]"
                + ",[SmetCost]"
                + ",[SumCost]"
                + ",[FinishBuilding]"
                + ",[CostHouse]"
                + ",[CostSite]"
                + ",[SaleClients]"
                + ",[DebtClients]"
                + ",[StatusJobs]"
                + ",[SaleHouse] "
                + ",[SiteTypeID] "
                + ")"

                + " VALUES ("
                //SiteNumber/QueueBuilding/TypeHome/Contractor/StatusPeiment
                + " ?, ?, ?, ?, 'не оплачено', "
                //NContract/DateContract/SmetCost/SumCost/FinishBilding
                + " '', ?,'','', ? ,'','','','',"
                //StatusJobs/SaleHouse/incom/overheads/taxes
                + "'не начат',0.0,(SELECT P.[TypeID] FROM [FinPlan] P WHERE P.[TypeName] = ? ))";
//                               .
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            pstmt.setString(1, siteNumb);
            pstmt.setString(2, queueNumb);
            pstmt.setString(3, typeHome);
            pstmt.setInt(4, contractorConstId);

            pstmt.setInt(5, (int) LocalDate.now().toEpochDay());
            pstmt.setInt(6, (int) LocalDate.now().toEpochDay());
            pstmt.setString(7, siteType);

            pstmt.execute();
        } catch (SQLException ex) {

            Logger.getLogger(SiteCommonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete ONE Contractor from
     * <br><b>Site</b>
     * <br><b>SiteExpenses</b>
     * <br><b>SiteJobPeriod</b>
     * <br><b>Estimate</b>
     *
     * @param siteNumb
     * @param contName
     */
    public void dellContractor(String siteNumb, String contName) {

        String psmtmtString = "DECLARE " +
                "@siteNumber VARCHAR(10)," +
                "@contractor VARCHAR(100) " +
                "SET @siteNumber = ? " +
                "SET @contractor = ? "
                + "DELETE FROM dbo.[Site]          WHERE [SiteNumber]= @siteNumber  AND [Contractor] = @contractor  "
                + "DELETE FROM dbo.[SiteExpenses]  WHERE [SiteNumber]= @siteNumber  AND [Contractor] = @contractor  "
                + "DELETE FROM dbo.[SiteJobPeriod] WHERE [SiteNumber]= @siteNumber  AND [Contractor] = @contractor  "
                + "DELETE FROM dbo.[Estimate]      WHERE [SiteNumber]= @siteNumber  AND [Contractor] = @contractor  ";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            pstmt.setString(1, siteNumb);
            pstmt.setString(2, contName);
            pstmt.execute();


        } catch (SQLException ex) {
            Logger.getLogger(SiteCommonDAO.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public ObservableList<PreviewTIV> getListIntro() {


        ObservableList<PreviewTIV> list = FXCollections.observableArrayList(PreviewTIV.extractor());
        String ResultSetString = "BEGIN TRAN;"
                + "DECLARE @SumCostHouse DECIMAL(28,2) "
                + ", @SumSmetCost DECIMAL(28,2) "
                + ", @Quantity INT "
                + " SELECT @SumCostHouse = sum([CostHouse]) FROM [dbo].[Site]    WHERE dell = 0; \n"
                + " SELECT @SumSmetCost  = sum([SmetCost])  FROM [dbo].[Site]    WHERE dell = 0; \n"
                + " SELECT @Quantity     = sum([Quantity])  FROM [dbo].[FinPlan] WHERE dell = 0; \n"
                + " SELECT TOP 1 "
                + "id"
                + ",Date"
                + ",OutgoingRest"
                + ",@Quantity AS Quantity "
                + ",(@SumCostHouse - @SumSmetCost) AS SumProfit "
                + " FROM Account ORDER BY ID DESC \n"
                + " COMMIT TRAN; ";


        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement prst = connection.prepareStatement(ResultSetString)) {

            try(ResultSet rs = prst.executeQuery()) {
                if (rs.next()) {
                    list.addAll(
                            //0
                            new PreviewTIV(rs.getLong("id"), "Date", "Дата последней транзакции", rs.getLong("Date")),
                            //1
                            new PreviewTIV(rs.getLong("id"), "OutgoingRest", "Исходящий остаток", rs.getDouble("OutgoingRest")),
                            //2
                            new PreviewTIV(rs.getLong("id"), "Quantity", "Количество Участков", rs.getInt("Quantity")),
                            //3
                            new PreviewTIV(rs.getLong("id"), "SumProfit", "Суммарная прибыль", rs.getDouble("SumProfit"))

                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SiteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        return list;
    }

}
