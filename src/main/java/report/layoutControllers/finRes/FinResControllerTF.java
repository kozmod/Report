package report.layoutControllers.finRes;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.models.numberStringConverters.dateStringConverters.EpochDayStringConverter;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes_factories.TableFactory;

public class FinResControllerTF {

    private FinResControllerTF() {
    }

    /**
     * Decorate FinRes TableView (FinResController)
     * @param table
     */
    @SuppressWarnings("unchecked")
    public static void decorFinRes(TableView table){

        TableWrapper tableWrapper = new TableWrapper(table, null);
        tableWrapper.tableView().setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn siteNumberColumn  = tableWrapper.addColumn("№ учстка", "siteNumber");
        TableColumn contractorColumn  = tableWrapper.addColumn("Субподрядчик","contractor");
        TableColumn NContractColumn   = tableWrapper.addColumn("№ договора", "NContract");
        TableColumn dateContColumn    = tableWrapper.addColumn("Дата договора", "dateContract");
        TableColumn finishBuildColumn = tableWrapper.addColumn("Окончание строительства", "finishBuilding");
        TableColumn smetCostColumn    = tableWrapper.addColumn("Сметная стоимость", "smetCost");
        TableColumn costHouseColumn   = tableWrapper.addColumn("Стоимость дома", "costHouse");
        TableColumn saleHouseColumn   = tableWrapper.addColumn("Цена продажи","SaleHouse");
//        TableColumn trueCostColumn    = tableWrapper.addColumn("TRUECOST", "trueCost");
        TableColumn profitColumn      = tableWrapper.addColumn("Прибыль", "profit");


//        dateContColumn.setCellFactory   (cell -> TableCellFactory.getEpochDateCell());
//        finishBuildColumn.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
//        smetCostColumn.setCellFactory   (cell -> TableCellFactory.getDecimalCell());
//        costHouseColumn.setCellFactory  (cell -> TableCellFactory.getDecimalCell());
//        saleHouseColumn.setCellFactory  (cell -> TableCellFactory.getDecimalCell());
////        trueCostColumn.setCellFactory   (cell -> TableCellFactory.getDecimalCell());
//        profitColumn.setCellFactory     (cell -> TableCellFactory.getDecimalCell());
        TableFactory.setCellFactory(
              new EpochDayStringConverter(),
                dateContColumn,
                finishBuildColumn
        );

        TableFactory.setCellFactory(
                new DoubleStringConverter(),
                smetCostColumn,
                costHouseColumn,
                saleHouseColumn,
                profitColumn
        );
    }

}