package report.view_models.nodes_factories;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import report.entities.items.account.TableItemAcc;
import report.entities.items.fin_res.TableItemFinRes;
import report.entities.items.site.TableItemPreview;

import java.util.Objects;

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

//        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));
        table.setPrefHeight(450);

        ColumnCreator creator  = new ColumnCreator(table);
        TableColumn titleCol = creator.createColumn ("Параметр", "firstValue");
        TableColumn valueCol = creator.createColumn ("Значение", "secondValue");

        valueCol.setCellFactory(param -> TableCellFactory.getPreviewCell());

    }

    /**
     * Decorate Intro TableView (IntroLayoutController)
     * @param table
     */
    public static void decorIntroFinishedSite(TableView table){

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));


        ColumnCreator creator  = new ColumnCreator(table);
        TableColumn siteNumberCol = creator.createColumn ("Участок", "siteNumber");
        TableColumn typeHomeCol   = creator.createColumn ("ТипДома", "typeHome");
        TableColumn smetCostCol   = creator.createColumn ("Стоимоть", "smetCost");
        TableColumn saleCostCol   = creator.createColumn ("Цена", "saleCost");

        smetCostCol.setCellFactory(param -> TableCellFactory.getDecimalCell());
        saleCostCol.setCellFactory(param -> TableCellFactory.getDecimalCell());


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

    /**
     * Decorate KS_add TableView (ksAddLayoutController)
     */
    public static void decorAcc(TableView<TableItemAcc> table){
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        ColumnCreator<TableItemAcc> creator  = new ColumnCreator<>(table);
        TableColumn dateCol = creator.createColumn("Дата","date");
        TableColumn numCol  = creator.createColumn(" № ","num");

        //***************************  Client Column  ********************************
        TableColumn<TableItemAcc,Objects> ClientCol = new TableColumn<>("Клиент");
        TableColumn ITNCol        = creator.createColumnIntoColumn(ClientCol, "ИНН",         "ITN_Client");
        TableColumn nameClientCol = creator.createColumnIntoColumn(ClientCol, "Наименование","name_Client");
        TableColumn accClientCol  = creator.createColumnIntoColumn(ClientCol, "Счет",        "accNum_Client");
        table.getColumns().add(ClientCol);

        //***************************  Correspondent Column  ***************************
        TableColumn<TableItemAcc,Objects> CorCol = new TableColumn<>("Корреспондент");
        TableColumn BICCOrCol   = creator.createColumnIntoColumn(CorCol,"BIC",         "BIC_Cor");
        TableColumn accCorCol   = creator.createColumnIntoColumn(CorCol,"Счет",        "accNum_Cor");
        TableColumn nameCorCol  = creator.createColumnIntoColumn(CorCol,"Наименование","name_Cor");
        table.getColumns().add(CorCol);

        TableColumn VOCol   = creator.createColumn("ВО",        "VO");
        TableColumn dascCol = creator.createColumn("Содержание","description");

        //***************************  Turnover Column  ********************************
        TableColumn<TableItemAcc,Objects> TurnoverCol = new TableColumn<>("Обороты");
        TableColumn debCol          = creator.createColumnIntoColumn(TurnoverCol,"Дебет",  "deb");
        TableColumn credCol         = creator.createColumnIntoColumn(TurnoverCol,"Кредит", "cred");
        TableColumn outgoingRestCol = creator.createColumnIntoColumn(TurnoverCol,"Остаток","outgoingRest");
        table.getColumns().add(TurnoverCol);


        dateCol.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
        debCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
        credCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
        outgoingRestCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());


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
        private TableView<S> table;

        private ColumnCreator() {}
        private  ColumnCreator(TableView table)    {this.table = table;}



        private  <K> TableColumn<S,K> createColumn(String name, String fieldName){
            TableColumn<S,K> column = new TableColumn<>(name);
            column.setCellValueFactory(new PropertyValueFactory<>(fieldName));

            table.getColumns().add(column);
            return column;
        }

        private  <K> TableColumn<S,K> createColumnIntoColumn (TableColumn<S,Objects> parentCol, String name, String fieldName){
            TableColumn<S,K> column = new TableColumn<>(name);
            column.setCellValueFactory(new PropertyValueFactory<>(fieldName));

            parentCol.getColumns().add(column);
            return column;
        }
    }

}
