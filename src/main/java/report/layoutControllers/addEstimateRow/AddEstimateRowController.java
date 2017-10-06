
package report.layoutControllers.addEstimateRow;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import report.layoutControllers.estimate.EstimateController.Est;
import report.usage_strings.SQL;
import report.models_view.data_utils.DiffList;

import report.models_view.InputValidator;

import report.entities.items.TableItem;
import report.entities.items.cb.TableItemCB;
import report.entities.items.estimate.TableItemEst;
import report.models_view.nodes.TableWrapper;
import report.models_view.nodes_factories.TableFactory;
import report.entities.items.estimate.ItemEstDAO;


public class AddEstimateRowController implements Initializable {
    //   saveEst DATA(NOW)
    private final Timestamp todayDate = new Timestamp(System.currentTimeMillis());

    private ObservableList<TableItemCB> editObsList,
            baseObsList;

    private int    tableTpe;
    private String bildingPart,
            siteNumber,
            contName,
            typeHome;
    private TableWrapper rootTableWrapper,  elemTableWrapperView;
    private ObservableList additionalTable;
    @FXML private TableView elemTableView;
    @FXML private Label    siteNumLabel,
            contHomeLabel,
            buildingPartLabel;


    @FXML private TextField JM_maneTextField,
            bindedJobTextField,
            unitTextField,
            valueTextField,
            price_oneTextField;

    @FXML private ComboBox comboJobOrMat, comboUnit;


/*!******************************************************************************************************************
*                                                                                                Getter - Setter
********************************************************************************************************************/


    public void setRootTableView(TableWrapper t) {
        this.rootTableWrapper = t;
        this.editObsList = getCheckObs(t.getItems());
        this.bildingPart = t.getTitle();
        this.siteNumber  = Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER);
        this.contName    = Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR);
        this.typeHome    = Est.Common.getSiteSecondValue(SQL.Common.TYPE_HOME);
//        this.tableType   = enumEst.getTaleType();

        init_Labels();
        init_diffObsList();

    }

    public void setAditionalTableView(ObservableList t) {this.additionalTable = t; }

    /*!******************************************************************************************************************
    *                                                                                                           INIT
    ********************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Init TableView
        elemTableWrapperView = AddEstimateRowTF.decorEst_add(elemTableView);

        init_TextField();
        init_ComboBox();
    }




    private void init_Labels(){
        siteNumLabel.setText(siteNumber);
        contHomeLabel.setText(contName);
        buildingPartLabel.setText(bildingPart);
    }

    private void init_TextField() {
        bindedJobTextField.setVisible(false);
        bindedJobTextField.setDisable(true);

        unitTextField.setVisible(false);
        unitTextField.setDisable(true);
    }
    private void init_ComboBox(){
        ObservableList JobOrMatObs
                = FXCollections.observableArrayList ("Работа", "Материал");
        comboJobOrMat.setItems(JobOrMatObs);
        comboJobOrMat.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
//                System.out.println(comboJobOrMat.getValue());

                if(newValue.equals("Работа")){
                    bindedJobTextField.setText("-");
                    bindedJobTextField.setDisable(true);
                    bindedJobTextField.setVisible(false);

                } else {
                    bindedJobTextField.clear();
                    bindedJobTextField.setDisable(false);
                    bindedJobTextField.setVisible(true);
                    bindedJobTextField.setPromptText("Введите название работы");

                }

            }


        });
        ObservableList unitObs
                = FXCollections.observableArrayList(new ItemEstDAO().getDistinctOfColumn(SQL.Estimate.UNIT, "-Новый тип Ед. изм.-"));
//                = FXCollections.observableArrayList(new CommonQuery().getObsDISTINCT("Estimate", "Unit", this));
        comboUnit.setItems(unitObs);
        comboUnit.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String oldValue, String newValue) {
                if(newValue.equals("-Новый тип Ед. изм.-")){
                    unitTextField.clear();
                    unitTextField.setPromptText("Введите новые Ед. изм.");
                    unitTextField.setDisable(false);
                    unitTextField.setVisible(true);
                } else {
                    unitTextField.clear();
                    unitTextField.setDisable(true);
                    unitTextField.setVisible(false);
                    unitTextField.setText(newValue);

                }

            }


        });

    }

    // ATTENTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void init_diffObsList(){
        int check ;
//       baseObsList = getCheckObs(commonSQL_SELECT.getEstObs_base(siteNumber, contName,typeHome, bildingPart));
        baseObsList = new ItemEstDAO().getBaseList(bildingPart);
        DiffList diflist = new DiffList(baseObsList, editObsList);

        ObservableList<TableItem> result;
        if(diflist.exElements().size() > 0)
            result =   FXCollections.observableArrayList(diflist.exElements());
        else result  =   FXCollections.observableArrayList();
        check = baseObsList.size() - editObsList.size();

//        System.err.println("result size "+result.size());
        System.err.println("baseObsList  size "+baseObsList.size());
        System.err.println("editObsList  size "+editObsList.size());


        if(check >= 0) elemTableWrapperView.setItems(result);
        if(check < 0)System.out.println("Базовая меньше -> " + check);


    }

    /*!******************************************************************************************************************
    *                                                                                                           METH0TS
    ********************************************************************************************************************/
    private ObservableList<TableItemCB>  getCheckObs(ObservableList<TableItem> items){
        ObservableList<TableItemCB>  checkedObsList = FXCollections.observableArrayList();

        if(items != null)
            for (TableItem obsItem : items){
                checkedObsList.add(new TableItemCB(
                        0,
                        false,
                        todayDate,
                        obsItem.getSiteNumber(),
                        obsItem.getTypeHome(),
                        obsItem.getContractor(),
                        obsItem.getJM_name(),
                        obsItem.getJobOrMat(),
                        obsItem.getBindedJob(),
                        obsItem.getValue(),
                        obsItem.getUnit(),
                        obsItem.getPrice_one(),
                        obsItem.getPrice_sum(),
                        obsItem.getBildingPart()
                ));
            }

        return checkedObsList;
    }

    public ObservableList<TableItemEst>  getSelectedCheckObs(ObservableList<TableItem> items){
        ObservableList<TableItemEst>  checkedObsList = FXCollections.observableArrayList();

        for (TableItem obsItem : items){
            if(((TableItemCB)obsItem).getCheck() == true){
                checkedObsList.add(new TableItemEst
                                .Builder()
                                .setSiteNumber(obsItem.getSiteNumber())
                                .setTypeHome(obsItem.getTypeHome())
                                .setContractor(obsItem.getContractor())
                                .setJM_name(obsItem.getJM_name())
                                .setJobOrMat(obsItem.getJobOrMat())
                                .setBindedJob(obsItem.getBindedJob())
                                .setValue(obsItem.getValue())
                                .setUnit(obsItem.getUnit())
                                .setPrice_one(obsItem.getPrice_one())
                                .setPrice_sum(obsItem.getPrice_sum())
                                .setBildingPart(obsItem.getBildingPart())

                                .setDate(todayDate)
//                                    .setTableType(tableType)
                                .setTableType(1)
                                .setInKS(false)
                                .build()
                );
            }
        }
        return checkedObsList;
    }   
/*!******************************************************************************************************************
*                                                                                                           HANDLERS 
********************************************************************************************************************/

    @FXML
    private void handle_addRow(ActionEvent event) {
        String JM_name,
                JobOrMat = null,
                bindedLob = null,
                unit ;
        Double value,
                price_one,
                price_sum;

        if(isInputValid()){

            if (((String)comboJobOrMat.getValue()) != null ){
                JobOrMat = comboJobOrMat.getValue().toString();
            }
            if (((String)comboJobOrMat.getValue()) == "Работа"){
                bindedLob = "-";
            }else if(((String)comboJobOrMat.getValue()) == "Материал"){
                bindedLob = bindedJobTextField.getText();
            }
            if (((String)comboUnit.getValue()) != "-Новый тип Ед. изм.-"){
                unit = comboUnit.getValue().toString();
            }else{
                unit = unitTextField.getText();
            }

            JM_name   = JM_maneTextField.getText();
            value     = Double.parseDouble(valueTextField.getText());
            price_one = Double.parseDouble(price_oneTextField.getText());
            price_sum = price_one*value;


            elemTableWrapperView.getItems().add(new TableItemCB(
                    0,
                    false,
                    todayDate,
                    siteNumber,
                    typeHome,
                    contName,
                    JM_name,
                    JobOrMat,
                    bindedLob,
                    value,
                    unit,
                    price_one,
                    price_sum,
                    bildingPart
            ));
        }
    }

    @FXML
    private void handle_addMarkedRow(ActionEvent event) {
//        ((TableView)titledPaneModel.getModelTableView()
//        ).getItems().addAll(getSelectedCheckObs(elemTableWrapperView.getItems()));
        rootTableWrapper.getItems().addAll(getSelectedCheckObs(elemTableWrapperView.getItems()));
//        if(Est.Changed.isExist())
//           additionalTable.addAll(getSelectedCheckObs(elemTableWrapperView.getItems()))
//            ;


        Stage appStage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
        appStage.close();



    }

    @FXML
    private void handle_cencelButton(ActionEvent event) {

        Stage appStage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
        appStage.close();



    }
//Inner class ========================================================================================================    
//    public class CheckValueCell extends TableCell<ObsItems_Check, Boolean> {
//        private CheckBox checkBox;
//    
//        public CheckValueCell() { }
//
//        
//        @Override
//        protected void updateItem(Boolean item, boolean empty) {
//            super.updateItem(item, empty);
//            if (item != null) {
//                if (item instanceof Boolean) {
//                    createCheckBoxCell();
//                    checkBox.setSelected((boolean) item);
//                    setGraphic(checkBox);
//                } else {
//                    setGraphic(null);
//                }
//            } else {
//                setText(null);
//                setGraphic(null);
//            }
//            setAlignment(Pos.CENTER);
//        }
//         
//        void createCheckBoxCell(){
//            checkBox = new CheckBox();
//        
//            checkBox.selectedProperty().addListener(
//                (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
//                ((ObsItems_Check)getTableView().getItems().saveEst(getIndex())).setCheck(newValue);
//            });
//        }
//    }
/*!******************************************************************************************************************
*                                                                                                     InputValidator 
********************************************************************************************************************/

    private boolean isInputValid() {
        String errorMessage = "";
        if (JM_maneTextField.getText().length() == 0 || JM_maneTextField.getText() == null ){
            errorMessage += "'JM_maneTextField' ";
            JM_maneTextField.setStyle("-fx-border-color: red;");

        }else if(InputValidator.isStringValid(JM_maneTextField.getText()) == false){
            errorMessage += "'JM_maneTextField' ";
            JM_maneTextField.setStyle("-fx-border-color: red;");

        }

        if (valueTextField.getText().length() == 0 || valueTextField.getText() == null ){
            errorMessage += "'valueTextField  %' ";
            valueTextField.setStyle("-fx-border-color: red;");

        }else if(InputValidator.isStringValid(valueTextField.getText()) == false){
            errorMessage += "'valueTextField  %' ";
            valueTextField.setStyle("-fx-border-color: red;");

        }
        if (price_oneTextField.getText().length() == 0 || price_oneTextField.getText() == null ){
            errorMessage += "'price_oneTextField  %' ";
            price_oneTextField.setStyle("-fx-border-color: red;");

        }else if(InputValidator.isNumericValid(price_oneTextField.getText()) == false){

            errorMessage += "'price_oneTextField' ";

            price_oneTextField.setStyle("-fx-border-color: red;");
        }
        if (((String)comboJobOrMat.getValue()) == "Материал"){
            if(bindedJobTextField.getText().length() == 0 || bindedJobTextField.getText() == null ){
                errorMessage += "'bindedJobTextField' ";
                bindedJobTextField.setStyle("-fx-border-color: red;");
            } else if(InputValidator.isStringValid(bindedJobTextField.getText()) == false){
                errorMessage += "'bindedJobTextField' ";
                bindedJobTextField.setStyle("-fx-border-color: red;");
            }
        } else if(((String)comboJobOrMat.getValue()) == null){
            errorMessage += "'comboJobOrMat' ";
            comboJobOrMat.setStyle("-fx-border-color: red;");
        }
        if (((String)comboUnit.getValue()) == "-Новый тип Ед. изм.-"){
            if(unitTextField.getText().length() == 0 || unitTextField.getText() == null ){
                errorMessage += "'unitTextField' ";
                unitTextField.setStyle("-fx-border-color: red;");

            } else if(InputValidator.isStringValid(unitTextField.getText()) == false){
                errorMessage += "'unitTextField' ";
                unitTextField.setStyle("-fx-border-color: red;");
            }
        } else if(((String)comboUnit.getValue()) == null){
            errorMessage += "'comboUnit' ";
            comboUnit.setStyle("-fx-border-color: red;");
        }



        if(errorMessage.length() >0){
//            errorLabel.setText("Ошибки в полях:\n" + errorMessage);
//            errorLabel.setVisible(true);
        }

        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
        visiblePause.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                JM_maneTextField.setStyle(null);
                valueTextField.setStyle(null);
                price_oneTextField.setStyle(null);
                bindedJobTextField.setStyle(null);
                unitTextField.setStyle(null);
                comboJobOrMat.setStyle(null);
                comboUnit.setStyle(null);
//                            errorLabel.setVisible(false);

            }
        });
        visiblePause.play();

        if (errorMessage.length() == 0) {
            return true;
        } else {

            // Показываем сообщение об ошибке.
            return false;
        }
    }
//ObsItems_Checked========================================================================================================


//    public class ObsItems_Check {                                             
//        
//         //new value
//        private final StringProperty siteNumber; 
//        private final StringProperty typeHome;
//        private final StringProperty contractor; 
//        private final StringProperty bildingPart;
//       
//        
//        //old value
//        private final BooleanProperty check;
//        private final StringProperty JM_name;                          
//        private final StringProperty JobOrMat;                          
//        private final StringProperty BindedJob;                          
//        private final FloatProperty value;                            
//        private final StringProperty unit;                            
//        private final FloatProperty price_one;                            
//        private final FloatProperty price_sum;                            
// 
//        public ObsItems_Check(
//                              boolean check,
//                              String siteNumber, //new
//                              String typeHome,   //new
//                              String contractor, //new
//                              String JM_name, 
//                              String JobOrMat,
//                              String BindedJob, 
//                              float value, 
//                              String unit, 
//                              float price_one, 
//                              float price_sum,
//                              String bildingPart //new
//                            ) {
//            //new value
//            this.siteNumber    = new SimpleStringProperty(siteNumber);
//            this.typeHome      = new SimpleStringProperty(typeHome);
//            this.contractor    = new SimpleStringProperty(contractor);
//            this.bildingPart   = new SimpleStringProperty(bildingPart);
//            
//            //old value
//            this.check      = new SimpleBooleanProperty(check);
//            this.JM_name    = new SimpleStringProperty(JM_name);
//            this.JobOrMat   = new SimpleStringProperty(JobOrMat);
//            this.BindedJob  = new SimpleStringProperty(BindedJob);
//            this.value      = new SimpleFloatProperty(value);
//            this.unit       = new SimpleStringProperty(unit);
//            this.price_one  = new SimpleFloatProperty(price_one);
//            this.price_sum  = new SimpleFloatProperty(price_sum);
//        }
//
//        
//        public boolean getCheck() {return check.saveEst();}
//        public void setCheck(boolean value_inp) {check.set(value_inp);}
//     
//        public String getSiteNumber() {return siteNumber.saveEst();}
//        public void   setSiteNumber(String value_inp) {siteNumber.set(value_inp);}
//
//        public String getTypeHome() {return typeHome.saveEst();}
//        public void   setTypeHome(String value_inp) {typeHome.set(value_inp);}
//
//        public String getContractor() {return contractor.saveEst();}
//        public void   setContractor(String value_inp) {contractor.set(value_inp);}
//        
//        public String getJM_name() {return JM_name.saveEst();}
//        public void   setJM_name(String value_inp) {JM_name.set(value_inp);}
//      
//        public String getJobOrMat() {return JobOrMat.saveEst();}
//        public void   setJobOrMat(String value_inp) {JobOrMat.set(value_inp);}
//        
//        public String getBindedJob() {return BindedJob.saveEst();}
//        public void   setBindedJob(String value_inp) {BindedJob.set(value_inp);}
//
//        public float getValue() {return value.saveEst();}
//        public void  setValue(float value_inp) {value.set(value_inp);}
//        public FloatProperty getValueProperty() {return value;}
//        
//        public String getUnit() {return unit.saveEst();}
//        public void  setUnit(String value_inp) {unit.set(value_inp);}
//        
//        public float getPrice_one() {return price_one.saveEst();}
//        public void setPrice_one (float value_inp) {price_one.set(value_inp);}     
//        public FloatProperty getPrice_oneProperty() {return price_one;}
//       
//        public float getPrice_sum() {return price_sum.saveEst();}
//        public void setPrice_sum (float value_inp) {price_sum.set(value_inp);}
//        
//        public String getBildingPart() {return bildingPart.saveEst();}
//        public void   setBildingPart(String value_inp) {bildingPart.set(value_inp);}
//        
//        
//        @Override
//        public int hashCode() {
//            int hash = 3;
//            hash = 23 * hash + (this.value != null ? this.value.hashCode() : 0);
//            hash = 23 * hash + (this.price_one != null ? this.price_one.hashCode() : 0);
//            hash = 23 * hash + (this.price_sum != null ? this.price_sum.hashCode() : 0);
//            
//            return hash;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            final ObsItems_Check other = (ObsItems_Check) obj;
//            
//            if (obj == null) {
//                return false;
//            }
//            if (!ObsItems_Check.class.isAssignableFrom(obj.getClass())) {
//                return false;
//            }
//            if (this.check.saveEst() != other.check.saveEst()) {
//                return false;
//            }       
//            if ((this.siteNumber.saveEst() == null) ? (other.siteNumber.getValue() != null) : !this.siteNumber.saveEst().equals(other.siteNumber.saveEst())) {
//                return false;
//            }
//            if ((this.typeHome.saveEst() == null) ? (other.typeHome.getValue() != null) : !this.typeHome.saveEst().equals(other.typeHome.saveEst())) {
//                return false;
//            }
//            if ((this.contractor.saveEst() == null) ? (other.contractor.getValue() != null) : !this.contractor.saveEst().equals(other.contractor.saveEst())) {
//                return false;
//            }
//            if ((this.JM_name.saveEst() == null) ? (other.JM_name.getValue() != null) : !this.JM_name.saveEst().equals(other.JM_name.saveEst())) {
//                return false;
//            }
//            if ((this.JobOrMat.saveEst() == null) ? (other.JobOrMat.saveEst() != null) : !this.JobOrMat.saveEst().equals(other.JobOrMat.saveEst())) {
//                return false;
//            }
//            if ((this.BindedJob.saveEst() == null) ? (other.BindedJob.saveEst() != null) : !this.BindedJob.saveEst().equals(other.BindedJob.saveEst())) {
//                return false;
//            }
//            if (this.value.saveEst() != other.value.saveEst()) {
//                return false;
//            }
//            if ((this.unit.saveEst() == null) ? (other.unit.saveEst() != null) : !this.unit.saveEst().equals(other.unit.saveEst())) {
//                return false;
//            }
//            if (this.price_one.saveEst() != other.price_one.saveEst()) {
//                return false;
//            }
//            if (this.price_sum.saveEst() != other.price_sum.saveEst()) {
//                return false;
//            }
//            if ((this.bildingPart.saveEst() == null) ? (other.bildingPart.getValue() != null) : !this.bildingPart.saveEst().equals(other.bildingPart.saveEst())) {
//                return false;
//            }
//        return true;
//        }
//    } 


}
