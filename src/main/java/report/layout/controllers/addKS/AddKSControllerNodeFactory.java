package report.layout.controllers.addKS;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.items.account.AccountTVI;
import report.models.converters.dateStringConverters.EpochDayStringConverter;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.wrappers.table.TableWrapper;
import report.models.view.nodesFactories.TableFactory;

import java.util.Objects;

public abstract class AddKSControllerNodeFactory {

    /**
     * Decorate KS_add TableView (AddKsController)
     */
    public static void decorAcc(TableView<AccountTVI> table) {

        TableWrapper tableWrapper = new TableWrapper(table, null);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn dateCol = tableWrapper.addColumn("Дата", "date");
        TableColumn numCol = tableWrapper.addColumn(" № ", "num");

        //***************************  Client Column  ********************************
        TableColumn<AccountTVI, Objects> ClientCol = new TableColumn<>("Клиент");
        TableColumn ITNCol = tableWrapper.addColumn(ClientCol, "ИНН", "ITN_Client");
        TableColumn nameClientCol = tableWrapper.addColumn(ClientCol, "Наименование", "name_Client");
        TableColumn accClientCol = tableWrapper.addColumn(ClientCol, "Счет", "accNum_Client");
        table.getColumns().add(ClientCol);

        //***************************  Correspondent Column  ***************************
        TableColumn<AccountTVI, Objects> CorCol = new TableColumn<>("Корреспондент");
        TableColumn BICCOrCol = tableWrapper.addColumn(CorCol, "BIC", "BIC_Cor");
        TableColumn accCorCol = tableWrapper.addColumn(CorCol, "Счет", "accNum_Cor");
        TableColumn nameCorCol = tableWrapper.addColumn(CorCol, "Наименование", "name_Cor");
        table.getColumns().add(CorCol);

        TableColumn VOCol = tableWrapper.addColumn("ВО", "VO");
        TableColumn dascCol = tableWrapper.addColumn("Содержание", "description");

        //***************************  Turnover Column  ********************************
        TableColumn<AccountTVI, Objects> TurnoverCol = new TableColumn<>("Обороты");
        TableColumn debCol = tableWrapper.addColumn(TurnoverCol, "Дебет", "deb");
        TableColumn credCol = tableWrapper.addColumn(TurnoverCol, "Кредит", "cred");
        TableColumn outgoingRestCol = tableWrapper.addColumn(TurnoverCol, "Остаток", "outgoingRest");
        table.getColumns().add(TurnoverCol);


//        dateCol.setCellFactory(cell -> TableCellFactory.getEpochDateCell());
//        debCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
//        credCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());
//        outgoingRestCol.setCellFactory(cell -> TableCellFactory.getDecimalCell());


        TableFactory.setCellFactory(
                new EpochDayStringConverter(),
                dateCol
        );

        TableFactory.setCellFactory(
                new DoubleStringConverter(),
                debCol,
                credCol,
                outgoingRestCol
        );


    }


    /**
     * Decorate KS_add TableView (AddKsController)
     */
    public static void decorAddKS(TableView table) {
        TableWrapper tableWrapper = new TableWrapper(table, null);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        TableColumn JM_nameColumn = tableWrapper.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn BPartColumn = tableWrapper.addColumn("Часть", "buildingPart");
        TableColumn BJobColumnn = tableWrapper.addColumn("Связанная работа", "bindJob");


    }
}
