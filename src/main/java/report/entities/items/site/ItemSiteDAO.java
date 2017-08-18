
package report.entities.items.site;

import report.entities.items.estimate.ItemEstDAO;
import report.entities.ItemDAO;
import report.usege_strings.SQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.controllers.LogLayoutController;
import report.controllers.showEstLayoutController.Est;
import report.models.sql.SQLconnector;
import report.view_models.nodes.Table;


public class ItemSiteDAO implements ItemDAO<TableItemPreview, Table> {
    
    private String siteNumber, contractor;
    
    public ItemSiteDAO(String siteNumber, String contractor) {  
        this.siteNumber = siteNumber;
        this.contractor = contractor;
    }
    public ItemSiteDAO() {  
        if(Est.Common.isExist()){
            this.siteNumber = Est.Common.getSecondValue(SQL.Common.SITE_NUMBER);
            this.contractor = Est.Common.getSecondValue(SQL.Common.CONTRACTOR);
        }    
    }

    @Override
    public String getTableString() {return SQL.Tables.SITE;}
    
    @Override
    public ObservableList<TableItemPreview> getList(){
        
        long lBegin = System.currentTimeMillis();
        
        ObservableList<TableItemPreview> list = FXCollections.observableArrayList(TableItemPreview.extractor());
            String ResultSetString = "SELECT "
                       + " S.[id] "
                       + ",S.[SiteNumber] "
                       + ",S.[TypeHome] "
                       + ",S.[Contractor] "
                       + ",S.[DateContract] "
                       + ",S.[FinishBuilding] "
                       + ",S.[StatusJobs] "
                       + ",S.[StatusPayment] "
                       + ",S.[SaleClients] "
                       + ",S.[DebtClients] "
                       + ",S.[SmetCost] "
                       + ",S.[CostHouse] "
                       + ",S.[SaleHouse]"
                       + ",S.[CostSite] "
                       + ",S.[SumCost] "
                       + ",S.[QueueBuilding] "
                       + ",S.[NContract] "
                       + ",S.[k]"
                       +",T.[TypeName] AS SiteTypeID "
                       + "FROM dbo.[Site] S "
                       + "LEFT JOIN FinPlan T ON T.TypeID = S.SiteTypeID "
                       + "WHERE S.[SiteNumber] = ? "
                       + "AND   S.[Contractor] = ? "
                       + "AND   S.[dell] = 0";
                
        try(Connection connection = SQLconnector.getInstance();
                PreparedStatement prst = connection.prepareStatement(ResultSetString)) {
                prst.setString(1, siteNumber);
                prst.setString(2, contractor);

                ResultSet rs = prst.executeQuery();
                if(rs.next()){
//                   DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("dd.MM.YYYY");
                    list.addAll(//0
                           new TableItemPreview(rs.getLong("id"),SQL.Site.SITE_NUMBER, "Номер участка", rs.getObject(SQL.Common.SITE_NUMBER).toString()),
                           //1
                           new TableItemPreview(rs.getLong("id"),SQL.Site.TYPE_HOME, "Тип дома", rs.getObject(SQL.Common.TYPE_HOME).toString()),
                           //2
                           new TableItemPreview(rs.getLong("id"),SQL.Site.CONTRACTOR, "Субподрядчик", rs.getObject(SQL.Common.CONTRACTOR).toString()),
                           //3
                           new TableItemPreview(rs.getLong("id"),SQL.Site.SITE_TYPE_ID, "Класс Дома", rs.getObject(SQL.Site.SITE_TYPE_ID).toString()),                         
                           //4
                           new TableItemPreview(rs.getLong("id"),SQL.Site.DATE_CONTRACT, "Дата договора", rs.getLong(SQL.Site.DATE_CONTRACT)),
                           //5
                           new TableItemPreview(rs.getLong("id"),SQL.Site.FINISH_BUILDING, "Окончание строительства", rs.getLong(SQL.Site.FINISH_BUILDING)),
                           //6
                           new TableItemPreview(rs.getLong("id"),SQL.Site.STATUS_JOBS, "Статус строительства", rs.getObject(SQL.Site.STATUS_JOBS).toString()),
                           //7
                           new TableItemPreview(rs.getLong("id"),SQL.Site.STATUS_PAYMENT, "Статус оплаты", rs.getObject(SQL.Site.STATUS_PAYMENT).toString()),
                           //8
                           new TableItemPreview(rs.getLong("id"),SQL.Site.SALE_CLIENTS, "Оплачено клиентом", rs.getDouble(SQL.Site.SALE_CLIENTS)),
                           //9
                           new TableItemPreview(rs.getLong("id"),SQL.Site.DEBT_CLIENTS, "Долг клиента", rs.getDouble(SQL.Site.DEBT_CLIENTS)),
                           //10
                           new TableItemPreview(rs.getLong("id"),SQL.Site.SMET_COST, "Сметная стоимость", rs.getDouble(SQL.Site.SMET_COST)), 
                           //11
                           new TableItemPreview(rs.getLong("id"),SQL.Site.COST_HOUSE, "Стоимость дома", rs.getDouble(SQL.Site.COST_HOUSE)), //Продажная cебестоимость / CostHouse == SmetCost 
                           //12
                           new TableItemPreview(rs.getLong("id"),SQL.Site.SALE_HOUSE, "Цена продажи дома", rs.getDouble(SQL.Site.SALE_HOUSE)), // + SaleHouse "Цена продажи дома"
                           //13
                           new TableItemPreview(rs.getLong("id"),SQL.Site.COST_SITE, "Стоимость земли", rs.getDouble(SQL.Site.COST_SITE)),
                           //14
                           new TableItemPreview(rs.getLong("id"),SQL.Site.SUM_COST, "Сумма затрат", rs.getDouble(SQL.Site.SUM_COST)),
                           //15
                           new TableItemPreview(rs.getLong("id"),SQL.Site.QUEUE_BUILDING, "Очередь Строительства", rs.getString(SQL.Site.QUEUE_BUILDING)),
                           //16
                           new TableItemPreview(rs.getLong("id"),SQL.Site.N_CONTRACT, "№ Контракта", rs.getString(SQL.Site.N_CONTRACT)),
                           //17 k  - коэффициент умножения
                           new TableItemPreview(rs.getLong("id"),SQL.Site.COEFFICIENT, "Коэффициент", rs.getDouble(SQL.Site.COEFFICIENT))
                          
                           
                   );

                   //System.out.println(SiteInfoTable.get(0).getFirstValue());  
               }
                       

               
                
                
                    
            } catch (SQLException ex) {
               Logger.getLogger(ItemSiteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
      
        long lEnd = System.currentTimeMillis();
        System.err.println(lEnd - lBegin); 
        
       return list ;
    }



    @Override
    public void delete(Collection<TableItemPreview> items) {
        try(Connection connection   = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement("update [dbo].[Site] SET dell = 1 WHERE [id] = ? AND [dell] = 0;");) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                pstmt.setLong   (1, items.stream().findAny().get().getId());
                pstmt.execute();  
                
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogLayoutController
           items.forEach(item -> {
//                LogLayoutController.appendLogViewText("deleted EST item: "+ ((TableItem)item).getJM_name() 
//                                                         +" [JM/ "+((TableItem)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((TableItem)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
                });
            LogLayoutController.appendLogViewText(items.size() + " deleted");
            
        } catch (SQLException ex) {
            Logger.getLogger(ItemEstDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    @Override
    public void insert(Collection<TableItemPreview> items) {
       StringBuffer stringInsert = new StringBuffer("insert into [dbo].[Site]( ");
       StringBuffer stringValues = new StringBuffer(" VALUES(");
       String prefix = "";
            for(TableItemPreview item : items){
                
                stringInsert.append(prefix + "["+item.getSqlColumn()+"]");
                    if(!item.getSqlColumn().equals(SQL.Site.SITE_TYPE_ID)) 
                    stringValues.append(prefix + "?");
                    else 
                    stringValues.append(prefix + "(SELECT P.[TypeID] from [FinPlan] P WHERE P.[TypeName] = ? )");
                prefix = ",";
            }
            stringInsert.append( ")");
            stringValues.append(")");
            stringInsert.append(stringValues);
            
        System.err.println(stringInsert.toString());
        
       try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(stringInsert.toString(), 
                                                                   Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
                int i  = 1;
                for(TableItemPreview obsItem : items){
//                        System.out.println(item.getSecondValue().toString() + " # "+ i);                    
                        pstmt.setObject (i, obsItem.getSecondValue());
                        i++;
                }
                    int affectedRows = pstmt.executeUpdate();
                    
                    try( ResultSet generategKeys = pstmt.getGeneratedKeys();){
                        if(generategKeys.next())
                            for(TableItemPreview obsItem : items)
                                obsItem.setId(generategKeys.getLong(1));
                    } 
                
           //SQL commit
           connection.commit();
           //add info to LogTextArea / LogLayoutController
           items.forEach(item -> {
//                LogLayoutController.appendLogViewText("inserted item: "+ ((TableItem)item).getJM_name() 
//                                                         +" [JM/ "+((TableItem)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((TableItem)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((TableItem)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((TableItem)item).getContractor()   + "]");
                });
            LogLayoutController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(ItemEstDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
    }

    @Override
    public void dellAndInsert(Table table) {
        if(table.getItems() != null) {
                    delete(table.getItems());
                    insert(table.getItems());  }
    }
    
    public void dellAndInsert(Collection<TableItemPreview> collection) {
        if(collection != null && !collection.isEmpty()) {
                    delete(collection);
                    insert(collection);  
        }
    }




    
}
