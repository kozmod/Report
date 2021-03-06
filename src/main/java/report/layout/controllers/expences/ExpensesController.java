
package report.layout.controllers.expences;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import report.entities.items.expenses.ExpensesDao;
import report.entities.items.expenses.ExpensesTVI;
import report.entities.items.period.PeriodDao;
import report.entities.items.site.PreviewTIV;
import report.entities.items.site.SiteDao;
import report.layout.controllers.root.RootLayoutController;
import report.models.coefficient.Formula;
import report.models.coefficient.FormulaQuery;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.converters.dateStringConverters.LocalDayStringConverter;
import report.usage_strings.SQL;

import report.layout.controllers.estimate.EstimateController_old.Est;

import report.entities.items.period.PeriodTIV;
//import report.models.Formula_test;
import report.models.view.wrappers.table.TableWrapper;
import report.models.view.nodesFactories.ContextMenuFactory;
import report.models.view.customNodes.ContextMenuOptional;

public class ExpensesController implements Initializable {

    private RootLayoutController rootController;
    private Stage controllerStage;

    @FXML
    private TextField textExpTF, valueTF, textPeriodTF, contract_FinishTF, coeffTF;
    @FXML
    private GridPane siteTableGridPane, expensesTableGridPane, periodTableGridPane;
    @FXML
    private TableView siteTV, expensesTV, periodTV;


    private TableWrapper<PreviewTIV> siteTWrapper;
    private TableWrapper<ExpensesTVI> expensesTWrapper;
    private TableWrapper<PeriodTIV> periodTWrapper;

    @FXML
    private ComboBox typeCB;
    @FXML
    private DatePicker dateFromDP, dateToDP;
    @FXML
    private Button applyCoefButton, siteUndoButton, siteSaveButton, addExpensesButton, addPeriodButton;

    private final StringProperty SITE_NUMBER = new SimpleStringProperty(Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
    private final StringProperty CONTRACTOR = new SimpleStringProperty(Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
    private final StringProperty TYPE_HOME = new SimpleStringProperty(Est.Common.getSiteSecondValue(SQL.Common.TYPE_HOME));

    //    private final  DoubleProperty COEFFICIENT = new SimpleDoubleProperty(new FormulaQuery().getFormula().getQuantity());
    private final DoubleProperty COEFFICIENT = new SimpleDoubleProperty(Formula.formulaFromBase().computeCoefficient());

    /*!*******************************************************************************************************************
     *                                                                                                     PreConstructor
     ********************************************************************************************************************/ {
//        siteTWrapper.setTableData(FXCollections.observableArrayList(new PreviewTIV(Long.MIN_VALUE, "sss", "SSS", "s")));

        InvalidationListener l = (Observable observable) -> {
            if (CONTRACTOR.getValue().equals(SQL.Line) && TYPE_HOME.getValue().equals(SQL.Line)) {
                addExpensesButton.setDisable(true);
                addPeriodButton.setDisable(true);
            } else {
                addExpensesButton.setDisable(false);
                addPeriodButton.setDisable(false);
            }
        };
        CONTRACTOR.addListener(l);
        TYPE_HOME.addListener(l);
    }
    /*!******************************************************************************************************************
     *                                                                                                     Getter/Setter
     ********************************************************************************************************************/

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
        rootController.setTreeViewDisable(true);


    }

    public void setControllerStage(Stage stage) {
        this.controllerStage = stage;
        controllerStage.setOnCloseRequest((WindowEvent we) -> {
            System.out.println("Stage is closing");
            rootController.setTreeViewDisable(false);
//            rootController.update_TreeView();

        });
    }


    /*!*******************************************************************************************************************
     *                                                                                                              INIT
     ********************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        siteTWrapper = ExpensesControllerNodeUtils.decorProperty_Site(siteTV);
        expensesTWrapper = ExpensesControllerNodeUtils.decorProperty_Expenses(expensesTV);
        periodTWrapper = ExpensesControllerNodeUtils.decorProperty_JobPeriod(periodTV);

        siteTWrapper.setTableData(Est.Common.getPreviewObservableList());
        expensesTWrapper.setTableData(new ExpensesDao().getData());
        periodTWrapper.setTableData(new PeriodDao().getData());

        init_expensesTab();
        init_periodTab();
        siteButtonAccess();


    }

    private void init_expensesTab() {

        //Set Context Menu
        expensesTWrapper.setContextMenu(ContextMenuFactory.getCommonDSU(expensesTWrapper));
        ContextMenuOptional.setTableItemContextMenuListener(expensesTWrapper);
        expensesTWrapper.getContextMenu().getItems().get(2)
                .addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                    System.out.println("SAVEITEM ->>  report.layoutControllers.expences.ExpensesController.init_expensesTab()");
//                    COEFFICIENT.setQuantity(new FormulaQuery().getFormula().getQuantity());
                    COEFFICIENT.setValue(Formula.formulaFromBase().computeCoefficient());
                    System.out.println("COEF - >" + COEFFICIENT.getValue());
                });

        typeCB.getItems().addAll(" % ", " Руб. ");
        typeCB.setValue(" % ");


    }

    private void init_periodTab() {

        //Set Context Menu
        periodTWrapper.setContextMenu(ContextMenuFactory.getCommonDSU(periodTWrapper));
        ContextMenuOptional.setTableItemContextMenuListener(periodTWrapper);

//    //Set Date Pickers Converter
//    dateFromDP.setConverter(new EpochDatePickerConverter());
//    dateToDP.setConverter(new EpochDatePickerConverter());
        dateFromDP.setConverter(
                new LocalDayStringConverter()
        );
        dateToDP.setConverter(
                new LocalDayStringConverter()
        );

        //Set DateContract / FinishBuildin TextField
        contract_FinishTF.textProperty().bind(new StringBinding() {
            ObjectProperty dateComtract = Est.Changed.getSiteItem(SQL.Site.DATE_CONTRACT).getSecondProperty();
            ObjectProperty FinishBuilding = Est.Changed.getSiteItem(SQL.Site.FINISH_BUILDING).getSecondProperty();

            {
                super.bind(
                        dateComtract,
                        FinishBuilding
                );
            }

            @Override
            protected String computeValue() {
                return LocalDate.ofEpochDay((long) dateComtract.getValue()).toString()
                        + " / " +
                        LocalDate.ofEpochDay((long) FinishBuilding.getValue()).toString();
            }
        });

    }


    private void siteButtonAccess() {
        siteTWrapper.getItems().addListener((ListChangeListener<? super PreviewTIV>) c -> {
            System.out.println("Changed on " + c + " ExpensesController");
            siteUndoButton.setDisable(false);
            siteSaveButton.setDisable(false);
        });

        //Bind Formula to Textfield
        coeffTF.textProperty().bindBidirectional(COEFFICIENT, new DoubleStringConverter().format());

        //add Formula TF Listener
        coeffTF.textProperty().addListener(event -> {
            if (Est.Changed.isExist()) {
                if (!COEFFICIENT.getValue().equals(Est.Common.getSiteItem(SQL.Site.COEFFICIENT).getSecondValue()))
                    applyCoefButton.setDisable(false);
                else
                    applyCoefButton.setDisable(true);
            }
        });

        siteUndoButton.setDisable(true);
        siteSaveButton.setDisable(true);
        if (!Est.Common.getSiteSecondValue(SQL.Site.COEFFICIENT).equals(COEFFICIENT.get()))
            applyCoefButton.setDisable(false);
        else applyCoefButton.setDisable(true);

    }

    /*!*******************************************************************************************************************
     *                                                                                              INIT  Button handlers
     ********************************************************************************************************************/


//    private void init_coeffListener(){
//        coeffTF.textProperty().bindBidirectional(Formula_test.coefficient.getValueProperty(), new NumberStringConverter());
//        Formula_test.coefficient.getValueProperty().addListener(event ->{
//            if(Est.Changed.isExist())
//                computeCoefButton.setDisable(false);
//        });
//    }

    /*!*******************************************************************************************************************
     *                                                                                                            HANDLERS
     ********************************************************************************************************************/
    @FXML
    private void hendler_applySiteChanges(ActionEvent event) {

        new SiteDao().dellAndInsert(siteTWrapper.getItems());

        siteTWrapper.saveMemento();
        rootController.update_previewTable(Est.Common.getPreviewObservableList());

//            COEFFICIENT.setQuantity(new FormulaQuery().getFormula().getQuantity());
        COEFFICIENT.setValue(Formula.formulaFromBase().computeCoefficient());

        CONTRACTOR.setValue(Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
        siteUndoButton.setDisable(true);
        siteSaveButton.setDisable(true);
        rootController.update_SelectedTreeViewItem(
                Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR)
        );


    }

    @FXML
    private void hendler_cencelSiteChanges(ActionEvent event) {
        siteTWrapper.undoChangeItems();

        siteUndoButton.setDisable(true);
        siteSaveButton.setDisable(true);
        applyCoefButton.setDisable(true);
    }


    @FXML
    private void hendler_addExpenses(ActionEvent event) {                   //<===SQL connect
        int type = 0;
        if (typeCB.getSelectionModel().getSelectedItem().equals(" % ")) {
            type = 0;
        } else if (typeCB.getSelectionModel().getSelectedItem().equals(" Руб. ")) {
            type = 1;
        }
        expensesTWrapper.getItems().add(new ExpensesTVI
                .Builder()
                .setId(new Long(0))
                .setsiteNumber(SITE_NUMBER.getValue())
                .setContractor(CONTRACTOR.getValue())
                .setText(textExpTF.getText())
                .setType(type)
                .setValue(Double.parseDouble(valueTF.getText()))
                .build()
        );
        expensesTWrapper.refresh();
    }

    @FXML
    private void hendler_addJobPeriod(ActionEvent event) {                   //<===SQL connect
        periodTWrapper.getItems().add(new PeriodTIV
                .Builder()
                .setsiteNumber(SITE_NUMBER.getValue())
                .setContractor(CONTRACTOR.getValue())
                .setText(textPeriodTF.getText())
                .setDateFrom((int) dateFromDP.getValue().toEpochDay())
                .setDateTo((int) dateToDP.getValue().toEpochDay())
                .build()
        );
        periodTWrapper.refresh();
    }

    @FXML
    private void handler_applyCoeffToChanged(ActionEvent event) {
        Est.Common.getSiteItem(SQL.Site.COEFFICIENT).setSecondValue(COEFFICIENT.get());


        if (Est.Changed.isExist()) {
            new FormulaQuery().applyCoefficient(
                    Est.Changed.getSiteSecondValue(SQL.Common.SITE_NUMBER),
                    COEFFICIENT.getValue());
        }
        if (Est.Changed.isExist()) {
            Est.Changed.updateTabData();
            Est.Changed.printALLSum();

        }

        new SiteDao().dellAndInsert(siteTWrapper.getItems());
        siteTWrapper.saveMemento();
        siteTWrapper.refresh();

//        System.out.println(COEFFICIENT.getQuantity());

        applyCoefButton.setDisable(true);
        siteUndoButton.setDisable(true);
        siteSaveButton.setDisable(true);

    }

    //inner clsses +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//    class StringConverterTF extends StringConverter{
//
//        @Override
//        public String formatNumber(Object quantity) {
//            if(quantity != null){
//                return quantity.formatNumber();
//            }else {
//                return "";
//            }
//        }
//
//        @Override
//        public Float fromString(String string) {
//            if(!string.isEmpty() | string != null){
//            return Float.parseFloat(string);
//            }else {
//                return (float)0;
//            }
//        }
//        
//        TextFormatter getTextFormatter(){
//            TextFormatter tf = new TextFormatter(new StringConverterTF());
//            return tf;
//        }
//        
//
//    }


}
