package report.view_models.nodes_factories;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import report.entities.items.fin_res.TableItemFinRes;

/**
 * Class contain PUBLIC STATIC VOID Methods which decorate FXML-TableView.
 */
public class TableViewFxmlDecorator {


    /**
     * Decorate PreviewT TableView (rootLayoutController)
     * @param table
     */
    public static void decorPreview(TableView table){

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));
        table.setPrefHeight(450);

        ColumnCreator creator  = new ColumnCreator(table);
        TableColumn titleCol = creator.createColumn ("Параметр", "firstValue");
        TableColumn valueCol = creator.createColumn ("Значение", "secondValue");

        valueCol.setCellFactory(param -> TableCellFactory.getPreviewCell());

    }


    /**
     * Decorate FinRes TableView (finResLayoutController)
     * @param table
     */
    @SuppressWarnings("unchecked")
    public static void decorFinRes(TableView table){
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ColumnCreator<TableItemFinRes> creator  = new ColumnCreator<>(table);
        TableColumn siteNumberColumn  = creator.createColumn ("№ учстка", "siteNumber");
        TableColumn contractorColumn  = creator.createColumn ("Субподрядчик","contractor");
        TableColumn NContractColumn   = creator.createColumn ("№ договора", "NContract");
        TableColumn dateContColumn    = creator.createColumn ("Дата договора", "dateContract");
        TableColumn finishBuildColumn = creator.createColumn ("Окончание строительства", "finishBuilding");
        TableColumn smetCostColumn    = creator.createColumn ("Сметная стоимость", "smetCost");
        TableColumn costHouseColumn   = creator.createColumn ("Стоимость дома", "costHouse");
        TableColumn saleHouseColumn   = creator.createColumn ("Цена продажи","SaleHouse");
        TableColumn trueCostColumn    = creator.createColumn ("TRUECOST", "trueCost");
        TableColumn profitColumn      = creator.createColumn ("Прибыль", "profit");


        dateContColumn.setCellFactory   (cell -> TableCellFactory.getEpochDateCell());
        finishBuildColumn.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
        smetCostColumn.setCellFactory   (cell -> TableCellFactory.getDecimalCell());
        costHouseColumn.setCellFactory  (cell -> TableCellFactory.getDecimalCell());
        saleHouseColumn.setCellFactory  (cell -> TableCellFactory.getDecimalCell());
        trueCostColumn.setCellFactory   (cell -> TableCellFactory.getDecimalCell());
        profitColumn.setCellFactory     (cell -> TableCellFactory.getDecimalCell());
    }

    /**
     * Decorate KS_add TableView (ksAddLayoutController)
     */
    public static void decorAddKS(TableView table){
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ColumnCreator creator     = new ColumnCreator(table);
        TableColumn JM_nameColumn = creator.createColumn("Наименование работ/затрат", "JM_name");
        TableColumn BPartColumn   = creator.createColumn("Часть",                     "bildingPart");
        TableColumn BJobColumnn   = creator.createColumn("Связанная работа",          "BindedJob");


    }



/*!******************************************************************************************************************
*                                                                                                     Inner Classes
********************************************************************************************************************/

    /**
     * ColumnCreator:
     * <p>1. create Column and add PropertyValueFactory</p>
     * <p>2. add Column to table</p>
     * @param <S>
     */
    private static class ColumnCreator<S>{
        private TableView table;

        private ColumnCreator() {}
        public  ColumnCreator(TableView table) {this.table = table;}

        public  <K> TableColumn<S,K> createColumn(String name, String fieldName){
            TableColumn<S,K> column = new TableColumn(name);
            column.setCellValueFactory(new PropertyValueFactory(fieldName));

            table.getColumns().add(column);
            return column;
        }
    }

}
