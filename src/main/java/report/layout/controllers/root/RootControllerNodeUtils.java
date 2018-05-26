package report.layout.controllers.root;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.xlsx4j.sml.Col;
import report.entities.items.site.PreviewTIV;
import report.entities.items.site.SiteDAO;
import report.entities.items.site.SiteEntity;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;
import report.services.common.CounterAgentHolder;
import report.usage_strings.SQL;

class RootControllerNodeUtils implements TableFactory {

    private RootControllerNodeUtils() {
    }

    /**
     * Create TableWrapper(Preview TableWrapper) with 2 columns /Object/.
     *
     * @return TableWrapper(Preview TableWrapper)
     */
    static TableView getSite() {
        TableView table = new TableView();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));
        table.setPrefHeight(450);

        TableColumn titleCol = new TableColumn("Параметр");
        titleCol.setCellValueFactory(new PropertyValueFactory("firstValue"));

        TableColumn valueCol = new TableColumn("Значение");
        valueCol.setCellValueFactory(new PropertyValueFactory("secondValue"));
        valueCol.setCellFactory(param -> TableCellFactory.getPreviewCell());


        table.getColumns().addAll(titleCol, valueCol);

        return table;
    }


    static ObservableList<PreviewTIV> getPreviewTIV() {

        final CounterAgentHolder counterAgentHolder = RootControllerService
                .getCounterAgentHolder()
                .orElseThrow(
                        () -> new IllegalArgumentException("CounterAgentHolder is NULL")
                );

        final String counterAgetNameWithForm = counterAgentHolder.getSelectedCounterAgen()
                .getForm()
                .concat(" ")
                .concat(counterAgentHolder.getSelectedCounterAgen().getName());

        final long siteId = counterAgentHolder.getSiteEntity().getId();
        final SiteEntity siteEntity = counterAgentHolder.getSiteEntity();

        final ObservableList<PreviewTIV> list = FXCollections.observableArrayList(PreviewTIV.extractor());

        list.addAll(
                //0
                new PreviewTIV(siteId, SQL.Site.SITE_NUMBER, "Номер участка", siteEntity.getSiteNumber()),
                //1
                new PreviewTIV(siteId, SQL.Site.TYPE_HOME, "Тип дома", siteEntity.getTypeHome()),
                //2
                new PreviewTIV(siteId, SQL.Site.CONTRACTOR, "Субподрядчик", counterAgetNameWithForm),
                //3
                new PreviewTIV(siteId, SQL.Site.SITE_TYPE_ID, "Класс Дома", siteEntity.getSiteTypeId()),
                //4
                new PreviewTIV(siteId, SQL.Site.DATE_CONTRACT, "Дата договора", siteEntity.getDateOfContract()),
                //5
                new PreviewTIV(siteId, SQL.Site.FINISH_BUILDING, "Окончание строительства", siteEntity.getFinishBuilding()),
                //6
                new PreviewTIV(siteId, SQL.Site.STATUS_JOBS, "Статус строительства", siteEntity.getStatusJobs()),
                //7
                new PreviewTIV(siteId, SQL.Site.STATUS_PAYMENT, "Статус оплаты", siteEntity.getStatusPayment()),
                //8
                new PreviewTIV(siteId, SQL.Site.SALE_CLIENTS, "Оплачено клиентом", siteEntity.getSaleClient()),
                //9
                new PreviewTIV(siteId, SQL.Site.DEBT_CLIENTS, "Долг клиента", siteEntity.getDebtClient()),
                //10
                new PreviewTIV(siteId, SQL.Site.SMET_COST, "Сметная стоимость", siteEntity.getSmetCost()),
                //11
                new PreviewTIV(siteId, SQL.Site.COST_HOUSE, "Стоимость дома", siteEntity.getCostHouse()), //Продажная cебестоимость / CostHouse == SmetCost
                //12
                new PreviewTIV(siteId, SQL.Site.SALE_HOUSE, "Цена продажи дома", siteEntity.getSaleHouse()), // + SaleHouse "Цена продажи дома"
                //13
                new PreviewTIV(siteId, SQL.Site.COST_SITE, "Стоимость земли", siteEntity.getCostSite()),
                //14
                new PreviewTIV(siteId, SQL.Site.SUM_COST, "Сумма затрат", siteEntity.getSumCost()),// >>>>>>>>>>> Delete
                //18
                new PreviewTIV(siteId, SQL.Site.TAXES_ALL, "Выплаченные налоги", 0),
                //15
                new PreviewTIV(siteId, SQL.Site.QUEUE_BUILDING, "Очередь Строительства", siteEntity.getQueueBuildings()),
                //16
                new PreviewTIV(siteId, SQL.Site.N_CONTRACT, "№ Контракта", siteEntity.getNumberOfContract()),
                //17 k  - коэффициент умножения
                new PreviewTIV(siteId, SQL.Site.COEFFICIENT, "Коэффициент", siteEntity.getCoefficient())
        );
        return list;
    }
}
