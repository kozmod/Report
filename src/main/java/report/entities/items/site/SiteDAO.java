
package report.entities.items.site;

import report.entities.abstraction.CommonNamedDAO;
import report.entities.items.estimate.EstimateDAO;
import report.layout.controllers.LogController;
import report.models.sql.SqlConnector;
import report.usage_strings.SQL;

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
import report.layout.controllers.estimate.EstimateController.Est;


public class SiteDAO implements CommonNamedDAO<Collection<PreviewTIV>> {

    private String siteNumber, contractor;

    public SiteDAO(String siteNumber, String contractor) {
        this.siteNumber = siteNumber;
        this.contractor = contractor;
    }

    public SiteDAO() {
        if (Est.Common.isExist()) {
            this.siteNumber = Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER);
            this.contractor = Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR);
        }
    }

    @Override
    public String getSqlTableName() {
        return SQL.Tables.SITE;
    }

    @Override
    public ObservableList<PreviewTIV> getData() {

        long lBegin = System.currentTimeMillis();

        ObservableList<PreviewTIV> list = FXCollections.observableArrayList(PreviewTIV.extractor());
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
                + ",T.[TypeName] AS SiteTypeID "
                + "FROM dbo.[Site] S "
                + "LEFT JOIN FinPlan T ON T.TypeID = S.SiteTypeID "
                + "WHERE S.[SiteNumber] = ? "
                + "AND   S.[Contractor] = ? "
                + "AND   S.[dell] = 0";

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement prst = connection.prepareStatement(ResultSetString)) {
            prst.setString(1, siteNumber);
            prst.setString(2, contractor);

            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
//                   DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("dd.MM.YYYY");
                list.addAll(//0
                        new PreviewTIV(rs.getLong("id"), SQL.Site.SITE_NUMBER, "Номер участка", rs.getObject(SQL.Common.SITE_NUMBER).toString()),
                        //1
                        new PreviewTIV(rs.getLong("id"), SQL.Site.TYPE_HOME, "Тип дома", rs.getObject(SQL.Common.TYPE_HOME).toString()),
                        //2
                        new PreviewTIV(rs.getLong("id"), SQL.Site.CONTRACTOR, "Субподрядчик", rs.getObject(SQL.Common.CONTRACTOR).toString()),
                        //3
                        new PreviewTIV(rs.getLong("id"), SQL.Site.SITE_TYPE_ID, "Класс Дома", rs.getObject(SQL.Site.SITE_TYPE_ID).toString()),
                        //4
                        new PreviewTIV(rs.getLong("id"), SQL.Site.DATE_CONTRACT, "Дата договора", rs.getLong(SQL.Site.DATE_CONTRACT)),
                        //5
                        new PreviewTIV(rs.getLong("id"), SQL.Site.FINISH_BUILDING, "Окончание строительства", rs.getLong(SQL.Site.FINISH_BUILDING)),
                        //6
                        new PreviewTIV(rs.getLong("id"), SQL.Site.STATUS_JOBS, "Статус строительства", rs.getObject(SQL.Site.STATUS_JOBS).toString()),
                        //7
                        new PreviewTIV(rs.getLong("id"), SQL.Site.STATUS_PAYMENT, "Статус оплаты", rs.getObject(SQL.Site.STATUS_PAYMENT).toString()),
                        //8
                        new PreviewTIV(rs.getLong("id"), SQL.Site.SALE_CLIENTS, "Оплачено клиентом", rs.getDouble(SQL.Site.SALE_CLIENTS)),
                        //9
                        new PreviewTIV(rs.getLong("id"), SQL.Site.DEBT_CLIENTS, "Долг клиента", rs.getDouble(SQL.Site.DEBT_CLIENTS)),
                        //10
                        new PreviewTIV(rs.getLong("id"), SQL.Site.SMET_COST, "Сметная стоимость", rs.getDouble(SQL.Site.SMET_COST)),
                        //11
                        new PreviewTIV(rs.getLong("id"), SQL.Site.COST_HOUSE, "Стоимость дома", rs.getDouble(SQL.Site.COST_HOUSE)), //Продажная cебестоимость / CostHouse == SmetCost
                        //12
                        new PreviewTIV(rs.getLong("id"), SQL.Site.SALE_HOUSE, "Цена продажи дома", rs.getDouble(SQL.Site.SALE_HOUSE)), // + SaleHouse "Цена продажи дома"
                        //13
                        new PreviewTIV(rs.getLong("id"), SQL.Site.COST_SITE, "Стоимость земли", rs.getDouble(SQL.Site.COST_SITE)),
                        //14
                        new PreviewTIV(rs.getLong("id"), SQL.Site.SUM_COST, "Сумма затрат", rs.getDouble(SQL.Site.SUM_COST)),// >>>>>>>>>>> Delete
                        //18
                        new PreviewTIV(rs.getLong("id"), SQL.Site.TAXES_ALL, "Выплаченные налоги", 0),
                        //15
                        new PreviewTIV(rs.getLong("id"), SQL.Site.QUEUE_BUILDING, "Очередь Строительства", rs.getString(SQL.Site.QUEUE_BUILDING)),
                        //16
                        new PreviewTIV(rs.getLong("id"), SQL.Site.N_CONTRACT, "№ Контракта", rs.getString(SQL.Site.N_CONTRACT)),
                        //17 k  - коэффициент умножения
                        new PreviewTIV(rs.getLong("id"), SQL.Site.COEFFICIENT, "Коэффициент", rs.getDouble(SQL.Site.COEFFICIENT))


                );

                //System.out.println(SiteInfoTable.saveEst(0).getFirstValue());
            }


        } catch (SQLException ex) {
            Logger.getLogger(SiteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        long lEnd = System.currentTimeMillis();
        System.err.println(lEnd - lBegin);

        return list;
    }


    @Override
    public void delete(Collection<PreviewTIV> items) {
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement("update [dbo].[Site] SET dell = 1 WHERE [id] = ? AND [dell] = 0;");) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);
            pstmt.setLong(1, items.stream().findAny().get().getId());
            pstmt.execute();

            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
            items.forEach(item -> {
//                LogController.appendLogViewText("deleted EST item: "+ ((Item)item).getJM_name()
//                                                         +" [JM/ "+((Item)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((Item)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
            });
            LogController.appendLogViewText(items.size() + " deleted");

        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //TODO: в таблицу сайт теперь пишутся значения ID  подрядчика, а не строковое значение Подрядчика
    @Override
    public void insert(Collection<PreviewTIV> items) {
        //Build INSERT SQL String
        StringBuffer stringInsert = new StringBuffer("insert into [dbo].[Site_new]( ");
        StringBuffer stringValues = new StringBuffer(" VALUES(");
        String prefix = "";
        for (PreviewTIV item : items) {
            if (!item.getSqlColumn().equals(SQL.Site.TAXES_ALL)) {
                stringInsert.append(prefix + "[" + item.getSqlColumn() + "]");
                if (!item.getSqlColumn().equals(SQL.Site.SITE_TYPE_ID))
                    stringValues.append(prefix + "?");
                else
                    stringValues.append(prefix + "(SELECT P.[TypeID] from [FinPlan] P WHERE P.[TypeName] = ? )");
                prefix = ",";
            }
        }

        stringInsert.append(")");
        stringValues.append(")");
        stringInsert.append(stringValues);

        System.err.println(stringInsert.toString());

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(stringInsert.toString(),
                     Statement.RETURN_GENERATED_KEYS);) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);

            //Add data to Prepare Statement
            int i = 1;
            for (PreviewTIV obsItem : items) {
                if (!obsItem.getSqlColumn().equals(SQL.Site.TAXES_ALL)) {
                    pstmt.setObject(i, obsItem.getSecondValue());
                    i++;
                }
            }
            int affectedRows = pstmt.executeUpdate();

            try (ResultSet generategKeys = pstmt.getGeneratedKeys();) {
                if (generategKeys.next())
                    for (PreviewTIV obsItem : items)
                        obsItem.setId(generategKeys.getLong(1));
            }

            //SQL commit
            connection.commit();
            //add info to LogTextArea / LogController
            items.forEach(item -> {
//                LogController.appendLogViewText("inserted item: "+ ((Item)item).getJM_name()
//                                                         +" [JM/ "+((Item)item).getJobOrMat()      + "]"
//                                                         +" [BP/ "+((Item)item).getBindedJob()     + "]"
//                                                         +" [S#/ " + ((Item)item).getSiteNumber()  + "]"
//                                                         +" [C/ " + ((Item)item).getContractor()   + "]");
            });
            LogController.appendLogViewText(items.size() + " inserted");
        } catch (SQLException ex) {
            Logger.getLogger(EstimateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

//    @Override
//    public void dellAndInsert(AbstractTableWrapper<Collection<PreviewTIV>> tableWrapper) {
//        if(tableWrapper.getObservableItems() != null) {
//                    delete(tableWrapper.getObservableItems());
//                    insert(tableWrapper.getObservableItems());  }
//    }

    public void dellAndInsert(Collection<PreviewTIV> collection) {
        if (collection != null && !collection.isEmpty()) {
            delete(collection);
            insert(collection);
        }
    }


}
