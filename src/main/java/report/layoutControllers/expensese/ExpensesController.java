
package report.layoutControllers.expensese;

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
import report.entities.items.expenses.TableViewItemExpensesDAO;
import report.entities.items.period.TableViewItemPeriodDAO;
import report.entities.items.site.SiteTableViewItemDAO;
import report.layoutControllers.root.RootLayoutController;
import report.models.coefficient.Formula;
import report.models.coefficient.FormulaQuery;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models.numberStringConverters.dateStringConverters.LocalDayStringConverter;
import report.usage_strings.SQL;

import report.layoutControllers.estimate.EstimateController.Est;

import report.entities.items.expenses.TableItemExpenses;
import report.entities.items.period.TableItemPeriod;
//import report.models.Formula_test;
import report.models_view.nodes.node_wrappers.TableWrapper;
import report.models_view.nodes.nodes_factories.ContextMenuFactory;
import report.entities.items.site.TableItemPreview;
import report.models_view.nodes.ContextMenuOptional;

public class ExpensesController implements Initializable {
    
    private RootLayoutController rootController;
    private Stage controllerStage;

    @FXML  private TextField  textExpTF, valueTF, textPeriodTF, contract_FinishTF, coeffTF;
    @FXML  private GridPane   siteTableGridPane, expensesTableGridPane, periodTableGridPane;
    @FXML  private TableView  siteTV,expensesTV, periodTV;




    private  TableWrapper<TableItemPreview> siteTWrapper ;
    private  TableWrapper<TableItemExpenses> expensesTWrapper ;
    private  TableWrapper<TableItemPeriod> periodTWrapper ;

    @FXML  private ComboBox typeCB;
    @FXML  private DatePicker dateFromDP, dateToDP;
    @FXML  private Button applyCoefButton,siteUndoButton, siteSaveButton,addExpensesButton,addPeriodButton;
    
    private final StringProperty SITE_NUMBER = new SimpleStringProperty(Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER));
    private final StringProperty CONTRACTOR  = new SimpleStringProperty(Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
    private final StringProperty TYPE_HOME   = new SimpleStringProperty(Est.Common.getSiteSecondValue(SQL.Common.TYPE_HOME));
    
//    private final  DoubleProperty COEFFICIENT = new SimpleDoubleProperty(new FormulaQuery().getFormula().getValue());
    private final  DoubleProperty COEFFICIENT = new SimpleDoubleProperty(Formula.formulaFromBase().computeCoefficient());
    /*!*******************************************************************************************************************
     *                                                                                                     PreConstructor
     ********************************************************************************************************************/ 
    {
//        siteTWrapper.setTableData(FXCollections.observableArrayList(new TableItemPreview(Long.MIN_VALUE, "sss", "SSS", "s")));


        InvalidationListener l = (Observable observable) -> {
            if(CONTRACTOR.getValue().equals(SQL.Line) && TYPE_HOME.getValue().equals(SQL.Line) ){
                addExpensesButton.setDisable(true);
                addPeriodButton.setDisable(true);
            }else{
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
        siteTWrapper = ExpensesControllerTF.decorProperty_Site(siteTV);
        expensesTWrapper = ExpensesControllerTF.decorProperty_Expenses(expensesTV);
        periodTWrapper = ExpensesControllerTF.decorProperty_JobPeriod(periodTV);

        siteTWrapper.setTableData(Est.Common.getPreviewObservableList());
        expensesTWrapper.setTableData(new TableViewItemExpensesDAO().getList());
        periodTWrapper.setTableData(new TableViewItemPeriodDAO().getList());

      init_expensesTab();
      init_periodTab();
      siteButtonAccess();

      
    }    
    
    private void init_expensesTab(){

        //Set Context Menu
        expensesTWrapper.setContextMenu(ContextMenuFactory.getCommonDSU(expensesTWrapper));
        ContextMenuOptional.setTableItemContextMenuListener(expensesTWrapper);
        expensesTWrapper.getContextMenu().getItems().get(2)
                .addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                    System.out.println("SAVEITEM ->>  report.layoutControllers.expensese.ExpensesController.init_expensesTab()");
//                    COEFFICIENT.setValue(new FormulaQuery().getFormula().getValue());
                    COEFFICIENT.setValue(Formula.formulaFromBase().computeCoefficient());
                    System.out.println("COEF - >"  + COEFFICIENT.getValue());
                });

        typeCB.getItems().addAll(" % ", " Руб. ");
        typeCB.setValue(" % ");
        
        
    }
    
    private void init_periodTab(){

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
    contract_FinishTF.textProperty().bind(new StringBinding(){
            ObjectProperty dateComtract   = Est.Changed.getSiteItem(SQL.Site.DATE_CONTRACT).getSecondProperty();
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
                       +" / "+
                       LocalDate.ofEpochDay((long) FinishBuilding.getValue()).toString();
            }
        });
   
    }
    

    private void siteButtonAccess(){
        siteTWrapper.getItems().addListener((ListChangeListener<? super TableItemPreview>) c -> {
            System.out.println("Changed on " + c + " ExpensesController" );
            siteUndoButton.setDisable(false);
            siteSaveButton.setDisable(false);
        });

        //Bind Formula to Textfield
        coeffTF.textProperty().bindBidirectional(COEFFICIENT,  new DoubleStringConverter().format());

        //add Formula TF Listener
        coeffTF.textProperty().addListener(event ->{
            if(Est.Changed.isExist() ) {
                if(!COEFFICIENT.getValue().equals(Est.Common.getSiteItem(SQL.Site.COEFFICIENT).getSecondValue()))
                    applyCoefButton.setDisable(false);
                else
                    applyCoefButton.setDisable(true);
            }
        });
        
        siteUndoButton .setDisable(true);
        siteSaveButton .setDisable(true);
        if(!Est.Common.getSiteSecondValue(SQL.Site.COEFFICIENT).equals(COEFFICIENT.get()))
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

            new SiteTableViewItemDAO().dellAndInsert(siteTWrapper);
       
            siteTWrapper.saveTableItems();
            rootController.update_previewTable(Est.Common.getPreviewObservableList());
            
//            COEFFICIENT.setValue(new FormulaQuery().getFormula().getValue());
            COEFFICIENT.setValue(Formula.formulaFromBase().computeCoefficient());

            CONTRACTOR.setValue(Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR));
            siteUndoButton .setDisable(true);
            siteSaveButton.setDisable(true);
            rootController.update_SelctedTreeViewItem(
                    Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR)
            );
            
            
    }
    
    @FXML   
    private void hendler_cencelSiteChanges(ActionEvent event) {
        siteTWrapper.undoChangeItems();
        
        siteUndoButton .setDisable(true);
        siteSaveButton .setDisable(true);
        applyCoefButton.setDisable(true);
    }
    
    
    @FXML
    private void hendler_addExpenses(ActionEvent event) {                   //<===SQL connect
        int type = 0;
        if(typeCB.getSelectionModel().getSelectedItem().equals(" % ")){
            type = 0;
        }else if(typeCB.getSelectionModel().getSelectedItem().equals(" Руб. ")){
            type = 1;
        }       
             expensesTWrapper.getItems().add(new TableItemExpenses
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
        periodTWrapper.getItems().add(new TableItemPeriod
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



        
        if(Est.Changed.isExist()){
            new FormulaQuery().applyCoefficient(
                Est.Changed.getSiteSecondValue(SQL.Common.SITE_NUMBER),
                Est.Changed.getSiteSecondValue(SQL.Common.CONTRACTOR),
                COEFFICIENT.getValue());
        }
        if(Est.Changed.isExist()){
            Est.Changed.updateTabData();
            Est.Changed.printALLSum();

        }

        new SiteTableViewItemDAO().dellAndInsert(siteTWrapper);
        siteTWrapper.saveTableItems();
        siteTWrapper.refresh();

//        System.out.println(COEFFICIENT.getValue());

        applyCoefButton.setDisable(true);
        siteUndoButton.setDisable(true);
        siteSaveButton.setDisable(true);
        
    }
    
    //inner clsses +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//    class StringConverterTF extends StringConverter{
//
//        @Override
//        public String formatNumber(Object value) {
//            if(value != null){
//                return value.formatNumber();
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
