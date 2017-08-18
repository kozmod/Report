/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.entities.items.site;

import report.usege_strings.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TreeItem;
import report.models.sql.SQLconnector;


public class SiteCommonDAO {
    
    public SiteCommonDAO(){}

    /**
     * Get ArrayList to TreeView Of Sites and make TreeViewItem.
     * @param QueueValue
     * @param PaimentCondition
     * @return 
     */
    public  TreeItem<String> getTreeObsList(String QueueValue, String PaimentCondition){    //DATA for TREEVIEW
                
        long lBegin = System.currentTimeMillis();
               
        ArrayList<TableItemPreview> siteList = new ArrayList<>();
                
        String ResultSetString = "SELECT [id],[SiteNumber], [Contractor]  from dbo.[Site] "
                                   + "WHERE [StatusPayment] LIKE '" + PaimentCondition+ "' "
                                   + "And [QueueBuilding] LIKE '" + QueueValue +"' "
                                   + "And [dell] = 0 "                                           //dell 0 - false / 1 - true
                                   + "Order by [SiteNumber]";
            try (Connection connection = SQLconnector.getInstance();
                 Statement st = connection.createStatement();){
                ResultSet rs = st.executeQuery(ResultSetString);
                
                while(rs.next()){
                    siteList.add(new TableItemPreview( 
                                      rs.getLong  (SQL.Common.ID),
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
                for (TableItemPreview site : siteList) {
                    TreeItem<String> ContratorLeaf = new TreeItem<String>(site.getSecondValue().toString());
                    boolean found = false;
                        for (TreeItem<String> siteNode : rootNode.getChildren()) {
                            if (siteNode.getValue().contentEquals(site.getFirstValue())){
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
    public void insertSite(String siteNumb, String queueNumb, String siteType, String typeHome, String contractor){
               
            String psmtmtString ="INSERT into dbo.[Site] ("
                                                            + "[SiteNumber]"
                                                            + ",[QueueBuilding]"
                                                            + ",[TypeHome]"
                                                            + ",[Contractor]"
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

                                + " values ("
                                            //SiteNumber/QueueBuilding/TypeHome/Contractor/StatusPeiment
                                            + " ?, ?, ?, ?, 'не оплачено', "
                                            //NContract/DateContract/SmetCost/SumCost/FinishBilding
                                            +" '', ?,'','', ? ,'','','','',"
                                            //StatusJobs/SaleHouse/incom/overheads/taxes
                                            + "'не начат',0.0,(SELECT P.[TypeID] from [FinPlan] P WHERE P.[TypeName] = ? ))";
//                               .
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            pstmt.setString(1, siteNumb);
            pstmt.setString(2, queueNumb);
            pstmt.setString(3, typeHome);
            pstmt.setString(4, contractor);

            pstmt.setInt   (5, (int) LocalDate.now().toEpochDay());
            pstmt.setInt   (6, (int) LocalDate.now().toEpochDay());
            pstmt.setString(7, siteType);
    
            pstmt.execute();
        }  catch (SQLException ex) {
            
           Logger.getLogger(SiteCommonDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    /**
     * Delete ONE Contractor from 
     *<br><b>Site</b>
     *<br><b>SiteExpenses</b>
     *<br><b>SiteJobPeriod</b>
     *<br><b>Estimate</b>
     * @param siteNumb
     * @param contName 
     */
    public void dellContractor(String siteNumb, String contName){
        
        String psmtmtString ="DECLARE " +
                            "@siteNumber varchar(10)," +
                            "@contractor varchar(100) " +
                            "set @siteNumber = ? " +
                            "set @contractor = ? "
                + "DELETE FROM dbo.[Site]          WHERE [SiteNumber]= @siteNumber  and [Contractor] = @contractor  "
                + "DELETE FROM dbo.[SiteExpenses]  WHERE [SiteNumber]= @siteNumber  and [Contractor] = @contractor  "
                + "DELETE FROM dbo.[SiteJobPeriod] WHERE [SiteNumber]= @siteNumber  and [Contractor] = @contractor  "
                + "DELETE FROM dbo.[Estimate]      WHERE [SiteNumber]= @siteNumber  and [Contractor] = @contractor  ";
       
        try(Connection connection = SQLconnector.getInstance(); 
            PreparedStatement pstmt = connection.prepareStatement(psmtmtString);) {
            pstmt.setString(1, siteNumb);
            pstmt.setString(2, contName);
            pstmt.execute();
            
           
            
        } catch (SQLException ex) {
            Logger.getLogger(SiteCommonDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
            
       
    }
}
