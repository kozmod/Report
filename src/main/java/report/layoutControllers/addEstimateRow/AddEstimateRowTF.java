package report.layoutControllers.addEstimateRow;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import report.entities.CommonDAO;
import report.entities.items.cb.AddEstTIV;
import report.entities.items.estimate.EstimateDAO;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models_view.nodes.cells.AddComboBoxTableCell;
import report.models_view.nodes.cells.AddTextFieldTableCell;
import report.models_view.nodes.node_wrappers.TableWrapperEST;
import report.models_view.nodes.nodes_factories.TableCellFactory;
import report.usage_strings.SQL;

public class AddEstimateRowTF {

    private AddEstimateRowTF(){}

    /**
     * Create TableWrapper to AddEstimateRowController.Contain columns and their options.
     * @return TableWrapper(child of TableView)
     */
    @SuppressWarnings("unchecked")
    public static TableWrapperEST<AddEstTIV> decorEst_add(TableView table){
        CommonDAO dao = new EstimateDAO();
        TableWrapperEST<AddEstTIV> tableWrapper = new TableWrapperEST<>(table, dao);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn checkBoxColumn = tableWrapper.addColumn(" * ","check");
        TableColumn<AddEstTIV, String>  JM_nameColumn  = tableWrapper.addColumn("Наименование работ/затрат", "JM_name");
        TableColumn<AddEstTIV, String>  BJobColumn     = tableWrapper.addColumn("Связанная работа", cellData -> cellData.getValue().bindJobProperty());
        TableColumn<AddEstTIV, Double>  valueColumn    = tableWrapper.addColumn("Кол-во",                    "quantity");
        TableColumn<AddEstTIV, String>  unitColumn     = tableWrapper.addColumn("Eд. изм.",                  "unit");
        TableColumn<AddEstTIV, Double>  priseOneColumn = tableWrapper.addColumn("Стоимость (за единицу)",    "priceOne");
        TableColumn<AddEstTIV, Double> priseSumColumn  = tableWrapper.addColumn("Стоимость (общая)",         "priceSum");
//

        checkBoxColumn.setMaxWidth(60);
        checkBoxColumn.setMinWidth(30);

        checkBoxColumn.setCellFactory(param -> TableCellFactory.getCheckValueCell());
        JM_nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        BJobColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
                dao.getDistinctOfColumn(SQL.Estimate.BINDED_JOB,"-")
        ));
//        BJobColumn.setOnEditCommit((TableColumn.CellEditEvent<AddEstTIV, String> t) ->{
//            AddEstTIV element = t.getRowValue();
//            String newValue = t.getNewValue();
//            element.setBindJob(newValue);
//            System.out.println("JOB or Mat " + element.getJobOrMat());
//
//        });
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        valueColumn.setOnEditCommit((TableColumn.CellEditEvent<AddEstTIV, Double> t) ->{
            AddEstTIV element = t.getRowValue();
            double newValue = t.getNewValue();
            element.setQuantity(newValue);
            double priceOne = element.getPriceOne();
            element.setPriceSum(newValue* priceOne);
        });
        unitColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
                dao.getDistinctOfColumn(SQL.Estimate.UNIT)
        ));
        priseOneColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        priseOneColumn.setOnEditCommit((TableColumn.CellEditEvent<AddEstTIV, Double> t) ->{
            AddEstTIV element = t.getRowValue();
            double newValue = t.getNewValue();
            element.setPriceOne(newValue);
            double quantity = element.getQuantity();
            element.setPriceSum(newValue* quantity);
        });
//        checkBoxColumn.setCellFactory(param -> TableCellFactory.getCheckValueCell());
//        JM_nameColumn.setCellFactory(param -> new AddTextFieldTableCell(
//                        tableWrapper.getSetAddingCells()
//                )
//        );
//        BJobColumn.setCellFactory(param -> new AddComboBoxTableCell(
//                        tableWrapper.getSetAddingCells()
//                        ,dao.getDistinctOfColumn(SQL.Estimate.BINDED_JOB,"-").toArray()
//                )
//        );
//        valueColumn.setCellFactory(param -> new AddTextFieldTableCell(new DoubleStringConverter(),tableWrapper.getSetAddingCells()));
//        unitColumn.setCellFactory(param -> new AddComboBoxTableCell(
//                        tableWrapper.getSetAddingCells()
//                        ,dao.getDistinctOfColumn(SQL.Estimate.UNIT).toArray()
//                )
//        );
//        priseOneColumn.setCellFactory(param -> new AddTextFieldTableCell(new DoubleStringConverter(),tableWrapper.getSetAddingCells()));
//        priseOneColumn.setOnEditCommit((TableColumn.CellEditEvent<AddEstTIV, Double> t) ->{
//            AddEstTIV element = t.getRowValue();
//            double newValue = t.getNewValue();
//            element.setPriceOne(newValue);
//            double quantity = element.getQuantity();
//            element.setPriceSum(newValue* quantity);
//            System.out.println("TEST  "+element.getPriceSum() + "                 "+ quantity);
//        });
////        priseSumColumn.setCellFactory(param -> new AddTextFieldTableCell(new DoubleStringConverter(),tableWrapper.getSetAddingCells()));
//        BJobColumn.setOnEditCommit((TableColumn.CellEditEvent<AddEstTIV, String> t) ->{
//            AddEstTIV element = t.getRowValue();
//            String newValue = t.getNewValue();
//            element.setBindJob(newValue);
//            switch (newValue){
//                case "-": element.setJobOrMat("Работа");
//                    break;
//                default:
//                    element.setJobOrMat("Материал");
//            }
//        });
        return  tableWrapper;
    }

}
