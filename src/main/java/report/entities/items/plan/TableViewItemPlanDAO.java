
package report.entities.items.plan;

import report.entities.TableViewItemDAO;
import report.entities.items.KS.TableViewItemKSDAO;
import report.models.coefficient.Quantity;
import report.models_view.nodes.node_wrappers.AbstractTableWrapper;
import report.usage_strings.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.layoutControllers.LogController;
import report.models.sql.SQLconnector;


public class TableViewItemPlanDAO implements TableViewItemDAO<TableItemPlan> {

   /**
    * 
    * @return  List of TableItem
    */
    @Override
    public String sqlTableName() {return SQL.Tables.FIN_PLAN;}
    
    /**
    * Get String of a Mirror (SQL.Tables).
    * @return  List of TableItem
    */
    @Override
    public ObservableList<TableItemPlan> getData() {
        ObservableList<TableItemPlan> list = FXCollections.observableArrayList(TableItemPlan.extractor());
        
//        String psmtmtString = " execute dbo.[getListPlan] ";

        String sqlString = "SELECT"
                +" F.[id]"
                +",F.[TypeName]"
                +",F.[TypeID]"
                +",F.[Quantity]"
                +",(F.[Quantity] - (SELECT COUNT(1) FROM  dbo.[Site] S where F.[TypeID] = S.[SiteTypeID] AND S.[dell] = 0)) AS [Rest]"
                +",F.[SmetCost]"
                +",F.[SmetCost]*F.[Quantity] AS [SmetCostSUM]"
                +",F.[SaleCost]"
                +",F.[SaleCost]*F.[Quantity]  AS [SaleCostSUM]"
                +",F.[NumberSession]"
                +",F.[DateCreate]"
                +" FROM dbo.[FinPlan] F"
                +" WHERE dell = 0";
        try(Connection connection = SQLconnector.getInstance();
                PreparedStatement pstmt = connection.prepareStatement(sqlString)) {
            pstmt.execute();
//            System.out.println(b);
            ResultSet rs = pstmt.getResultSet();
                while(rs.next()){
                    TableItemPlan item = new TableItemPlan(
                                    rs.getLong      (SQL.Common.ID),
                                    rs.getTimestamp (SQL.Common.DATE_CREATE),
                                    rs.getInt       (SQL.Plan.TYPE_ID),
                                    rs.getString    (SQL.Plan.TYPE_NAME),
                                    rs.getInt       (SQL.Plan.QUANTITY),
                                    rs.getInt       (SQL.Plan.REST),
                                    rs.getDouble    (SQL.Plan.SMET_COST),
                                    rs.getDouble    (SQL.Plan.SMET_COST_SUM),
                                    rs.getDouble    (SQL.Plan.SALE_COST),
                                    rs.getDouble    (SQL.Plan.SALE_COST_SUM),
                                    (rs.getDouble   (SQL.Plan.SALE_COST_SUM) - rs.getDouble (SQL.Plan.SMET_COST_SUM))
                                );
                    list.add(item);     
                }

        }  catch (SQLException ex) {
            Logger.getLogger(TableViewItemPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


        return list;
    }


    
    public ObservableList<TableItemFact> getListFact() {
        ObservableList<TableItemFact> list = FXCollections.observableArrayList();
        
//        String sqlString =
//                " SELECT "
//                + " F.[TypeID]      "
//                + " ,max(F.[TypeName])	as [TypeName]   "
//                + " ,count(1) 		as [Quantity] "
//                + " ,case when COUNT(1) = 0 then 0 else round(SUM(S.[SmetCost])/COUNT(1),2) end	as [SmetCost]	 "
//                + " ,round(SUM(S.[SmetCost]),2) 						as [SmetCostSum] "
//                + " ,case when COUNT(1) = 0 then 0 else  round(SUM(S.[SaleHouse])/COUNT(1),2) end as [SaleCost]    "
//                + " ,round(SUM(S.[SaleHouse]),2)   as [SaleCostSum] "
//                + " From dbo.[FinPlan] F          "
//                + " Inner JOIN dbo.[Site] S     "
//                + " ON F.[TypeID] = S.[SiteTypeID] "
//                + " and F.dell = 0 "
//                + " Where  "
//                + " S.dell = 0 "
//                + " Group by  "
//                + " F.[TypeID]  "
//                + " union all "
//                + " Select  "
//                + " F.[TypeID]      "
//                + " ,max(F.[TypeName])	 "
//                + " ,0 "
//                + " ,0 "
//                + " ,0 "
//                + " ,0 "
//                + " ,0 "
//                + " From dbo.[FinPlan] F                "
//                + " Inner JOIN dbo.[Site] S   "
//                + " ON F.[TypeID] = S.[SiteTypeID]  "
//                + " and F.dell = 0    "
//                + " group by   "
//                + " F.[TypeID]   "
//                + " having sum(cast(S.dell as int)) = count(F.[TypeID] )  ";

        String sqlString = " SELECT "
                + "F.[TypeID]"
                +",F.[TypeName]"
                +",ISNULL((SELECT COUNT(1)                            FROM  dbo.[Site] S WHERE F.[TypeID] = S.[SiteTypeID] AND S.[dell] = 0),0) AS [Quantity]"
                +",ISNULL((SELECT round(SUM(S.[SmetCost])/COUNT(1),2) FROM  dbo.[Site] S WHERE F.[TypeID] = S.[SiteTypeID] AND S.[dell] = 0),0) AS [SmetCost]"
                +",ISNULL((SELECT round(SUM(S.[SmetCost]),2)          FROM  dbo.[Site] S WHERE F.[TypeID] = S.[SiteTypeID] AND S.[dell] = 0),0) AS [SmetCostSum]"
                +",ISNULL((SELECT round(SUM(S.[SaleHouse])/COUNT(1),2)FROM  dbo.[Site] S WHERE F.[TypeID] = S.[SiteTypeID] AND S.[dell] = 0),0) AS [SaleCost]"
                +",ISNULL((SELECT round(SUM(S.[SaleHouse]),2)         FROM  dbo.[Site] S WHERE F.[TypeID] = S.[SiteTypeID] AND S.[dell] = 0),0) AS [SaleCostSum]"
                +",ISNULL((SELECT round(SUM(S.[CostHouse]),2)         FROM  dbo.[Site] S WHERE F.[TypeID] = S.[SiteTypeID] AND S.[dell] = 0),0) AS [CostHouseSum]"
                +" FROM  dbo.[FinPlan] F "
                +" WHERE  dell = 0 ";

        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sqlString);) {
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();
            
                while(rs.next()){
                    TableItemFact item = new TableItemFact(
                                    0, //id
                                    new Timestamp(0),
                                    rs.getInt       (SQL.Plan.TYPE_ID),
                                    rs.getString    (SQL.Plan.TYPE_NAME),
                                    rs.getInt       (SQL.Plan.QUANTITY),
                                    rs.getDouble     (SQL.Plan.SMET_COST),
                                    rs.getDouble     (SQL.Plan.SMET_COST_SUM),
                                    rs.getDouble     (SQL.Plan.COST_HOUSE_SUM),
                                    rs.getDouble     (SQL.Plan.SALE_COST),
                                    rs.getDouble     (SQL.Plan.SALE_COST_SUM),
                                    (rs.getDouble    (SQL.Plan.COST_HOUSE_SUM) - rs.getDouble   (SQL.Plan.SMET_COST_SUM))
//                                    (rs.getDouble    (SQL.Plan.SALE_COST_SUM) - rs.getDouble   (SQL.Plan.SMET_COST_SUM))
                                );
                    list.add(item);     
                }
   
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
//    public Map<String,List<TableItemPlan>> getMap(String s){
//        int i =  getListFact().stream().filter(o -> o.getQuantity().equals(s)).findFirst().get().getQuantity();
//
//        return new HashMap();
//    }

    @Override
    public void delete(Collection<TableItemPlan> entry) {
        System.out.println("PLAN DELET SIZE " + entry.size());
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement("update [dbo].[FinPlan] SET dell = 1 WHERE [id] = ? AND [dell] = 0;");) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItemPlan obsItem : entry) {
                    pstmt.setLong   (1, obsItem.getId());
                    pstmt.addBatch();  
                }
           pstmt.executeBatch();
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogController
           LogController.appendLogViewText(entry.size() + " deleted");
            
        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemPlanDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
    @Override
    public void insert(Collection<TableItemPlan> entry) {
        System.out.println("PLAN INS SIZE " + entry.size());
         String sql = "INSERT into [dbo].[FinPlan] "
                                    + "( " 
                                    + " [TypeID]" 
                                    + ",[TypeName]" 
                                    + ",[Quantity]" 
                                    + ",[SmetCost]" 
                                    + ",[SaleCost]" 
                                    + " ) " 
                                    + "VALUES(?,?,?,?,?)";
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(sql,
                                                                  Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                for (TableItemPlan obsItem : entry) {
                    pstmt.setInt   (1,  obsItem.getTypeID());
                    pstmt.setString(2,  obsItem.getType());
                    pstmt.setInt   (3,  obsItem.getQuantity());
                    pstmt.setDouble(4,  obsItem.getSmetCost());
                    pstmt.setDouble(5,  obsItem.getSaleCost());
                    
                    int affectedRows = pstmt.executeUpdate();
                    
                    try( ResultSet generategKeys = pstmt.getGeneratedKeys()){
                        if(generategKeys.next())
                            obsItem.setId(generategKeys.getLong(1));
                    }   
                }
           //SQL commit
           connection.commit();

        } catch (SQLException ex) {
            Logger.getLogger(TableViewItemKSDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void dellAndInsert(AbstractTableWrapper<Collection<TableItemPlan>> table) {
        TableViewItemDAO.super.dellAndInsert(table);
        //????????????????????????????????????????????
        Quantity.updateFromBase();
//        table. setDataFromBASE();
//        ContextMenuOptional.setTableItemContextMenuListener(table);
    }
}
