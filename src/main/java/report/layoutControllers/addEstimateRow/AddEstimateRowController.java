
package report.layoutControllers.addEstimateRow;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.omg.CORBA.Object;
import report.entities.items.cb.AddEstTIV;
import report.entities.items.estimate.EstimateDAO;
import report.layoutControllers.estimate.EstimateController.Est;
import report.usage_strings.SQL;
import report.models.DiffList;

import report.models_view.nodes.node_helpers.InputValidator;

import report.entities.items.TableItem;
import report.entities.items.estimate.EstimateTVI;
import report.models_view.nodes.node_wrappers.TableWrapper;


public class AddEstimateRowController implements Initializable {

    private final Timestamp todayDate = new Timestamp(System.currentTimeMillis());

    private ObservableList<AddEstTIV> editObsList,
            baseObsList;

    private String bildingPart,
            siteNumber,
            contName,
            typeHome;
    private TableWrapper rootTableWrapper;
    private TableWrapper<AddEstTIV> elemTableWrapperView;
    private ObservableList additionalTable;
    /***************************************************************************
     *                                                                         *
     * FXML Var                                                                *
     *                                                                         *
     **************************************************************************/
    @FXML private TableView elemTableView;
    @FXML private Label    siteNumLabel,
            contHomeLabel,
            buildingPartLabel;


    /***************************************************************************
     *                                                                         *
     * Getters/Setters                                                         *
     *                                                                         *
     **************************************************************************/


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
    /***************************************************************************
     *                                                                         *
     * Init                                                                    *
     *                                                                         *
     **************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Init TableView
        elemTableWrapperView = AddEstimateRowTF.decorEst_add(elemTableView);

    }


    private void init_Labels(){
        siteNumLabel.setText(siteNumber);
        contHomeLabel.setText(contName);
        buildingPartLabel.setText(bildingPart);
    }


    // ATTENTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void init_diffObsList(){

        int check ;
//       baseObsList = getCheckObs(commonSQL_SELECT.getEstObs_base(siteNumber, contName,typeHome, buildingPart));
        baseObsList = new EstimateDAO().getBaseList(bildingPart);
        DiffList diflist = new DiffList(baseObsList, editObsList);

        ObservableList<AddEstTIV> result;
        if(diflist.exElements().size() > 0)
            result =   FXCollections.observableArrayList(diflist.exElements());
        else result  =   FXCollections.observableArrayList();
        check = baseObsList.size() - editObsList.size();

//        System.err.println("result size "+result.size());
        System.err.println("baseObsList  size "+baseObsList.size());
        System.err.println("editObsList  size "+editObsList.size());


        if(check >= 0) elemTableWrapperView.tableView().setItems(result);
        if(check < 0)System.out.println("Базовая меньше -> " + check);


    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    private ObservableList<AddEstTIV>  getCheckObs(ObservableList<TableItem> items){
        ObservableList<AddEstTIV>  checkedObsList = FXCollections.observableArrayList();
        if(items != null)
            for (TableItem obsItem : items){
                checkedObsList.add(new AddEstTIV(
                        0,
                        false,
                        todayDate,
                        obsItem.getSiteNumber(),
                        obsItem.getTypeHome(),
                        obsItem.getContractor(),
                        obsItem.getJM_name(),
                        obsItem.getJobOrMat(),
                        obsItem.getBindJob(),
                        obsItem.getQuantity(),
                        obsItem.getUnit(),
                        obsItem.getPriceOne(),
                        obsItem.getPriceSum(),
                        obsItem.getBuildingPart()
                ));
            }

        return checkedObsList;
    }

    public ObservableList<EstimateTVI>  getSelectedCheckObs(ObservableList<AddEstTIV> items){
        ObservableList<EstimateTVI>  checkedObsList = FXCollections.observableArrayList();

        for (AddEstTIV obsItem : items){
            if(obsItem.getCheck() == true){
                checkedObsList.add(new EstimateTVI
                                .Builder()
                                .setSiteNumber(obsItem.getSiteNumber())
                                .setTypeHome(obsItem.getTypeHome())
                                .setContractor(obsItem.getContractor())
                                .setJM_name(obsItem.getJM_name())
                                .setJobOrMat(obsItem.getJobOrMat())
                                .setBindedJob(obsItem.getBindJob())
                                .setValue(obsItem.getQuantity())
                                .setUnit(obsItem.getUnit())
                                .setPrice_one(obsItem.getPriceOne())
                                .setPrice_sum(obsItem.getPriceSum())
                                .setBildingPart(obsItem.getBuildingPart())
                                .setDate(todayDate)
                                .setTableType(1)
                                .setInKS(false)
                                .build()
                );
            }
        }
        return checkedObsList;
    }
    /***************************************************************************
     *                                                                         *
     * Handlers                                                                *
     *                                                                         *
     **************************************************************************/
    @FXML
    private void testADD(ActionEvent event) {
        elemTableWrapperView.getItems().add(new AddEstTIV(
                -1L,
                false,
                todayDate,
                siteNumber,
                typeHome,
                contName,
                "-",
                "-",
                "-",
                0d,
                "-",
                0d,
                0d,
                bildingPart
        ));
        elemTableView.setEditable(true);
    }
//    @FXML
//    private void testCOMIT(ActionEvent event) {
////        elemTableWrapperView.commitData();
//        elemTableView.setEditable(false);
//
//    }
//
//    @FXML
//    private void testCHECK_LIST(ActionEvent event) {
//        elemTableWrapperView.getItems().forEach(i -> System.out.println(i.toString()));
//    }
    @FXML
    private void handle_addMarkedRow(ActionEvent event) {
        ObservableList<EstimateTVI> selectedItems = getSelectedCheckObs(elemTableWrapperView.getItems())
                .stream()
                .filter(item ->!"-".equals(item.getUnit()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        rootTableWrapper.getItems().addAll(selectedItems);
        Stage appStage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
        appStage.close();
    }

    @FXML
    private void handle_cencelButton(ActionEvent event) {
        Stage appStage =(Stage) ((Node)(event.getSource())).getScene().getWindow();
        appStage.close();
    }
}
