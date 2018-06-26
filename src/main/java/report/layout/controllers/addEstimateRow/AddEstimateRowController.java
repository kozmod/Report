
package report.layout.controllers.addEstimateRow;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.cb.AddEstTIV;
import report.entities.items.estimate.EstimateDao;
import report.layout.controllers.estimate.EstimateController_old.Est;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.counterpaties.EstimateData;
import report.usage_strings.SQL;
import report.models.DiffList;

import report.entities.items.estimate.EstimateTVI;
import report.models.view.wrappers.table.TableWrapper;

public class AddEstimateRowController implements Initializable {

    @Autowired
    private EstimateData estimateData;

    private final Timestamp todayDate = new Timestamp(System.currentTimeMillis());

    private ObservableList<AddEstTIV> editObsList, baseObsList;

    private String buildingPart,
            siteNumber,
            contName,
            typeHome;
    private TableWrapper<AddEstTIV> elemTableWrapperView;

    @FXML
    private TableView<AddEstTIV> elemTableView;
    @FXML
    private Label siteNumLabel,
            contHomeLabel,
            buildingPartLabel;
    @FXML
    private Button addButton;

    private TableView<EstimateTVI> rootTableView;

    public void initData(TableView<EstimateTVI> rootTableView){
        this.rootTableView = rootTableView;
        this.editObsList = getCheckObs(rootTableView.getItems());
        this.siteNumber = estimateData.getSiteEntity().getSiteNumber();
        this.contName = estimateData.getSelectedCounterAgent().getName();
        this.typeHome = estimateData.getSiteEntity().getTypeHome();
        init_Labels();
        init_diffObsList();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Init TableView
        elemTableWrapperView = AddEstimateRowNodeUtils.decorEst_add(elemTableView);

    }

    private void init_Labels() {
        siteNumLabel.setText(siteNumber);
        contHomeLabel.setText(contName);
        buildingPartLabel.setText(buildingPart);
    }


    // ATTENTION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private void init_diffObsList() {

        int check;
//       baseObsList = getCheckObs(commonSQL_SELECT.getEstObs_base(siteNumber, contName,typeHome, buildingPart));
        baseObsList = new EstimateDao().getBaseList(buildingPart);
        DiffList diflist = new DiffList(baseObsList, editObsList);

        ObservableList<AddEstTIV> result;
        if (diflist.exElements().size() > 0)
            result = FXCollections.observableArrayList(diflist.exElements());
        else result = FXCollections.observableArrayList();
        check = baseObsList.size() - editObsList.size();
        System.err.println("baseObsList  size " + baseObsList.size());
        System.err.println("editObsList  size " + editObsList.size());


        if (check >= 0) elemTableWrapperView.tableView().setItems(result);
        if (check < 0) System.out.println("Базовая меньше -> " + check);


    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    private ObservableList<AddEstTIV> getCheckObs(ObservableList<EstimateTVI> abstractEstimateTVIS) {
        ObservableList<AddEstTIV> checkedObsList = FXCollections.observableArrayList();
        if (abstractEstimateTVIS != null)
            for (AbstractEstimateTVI obsAbstractEstimateTVI : abstractEstimateTVIS) {
                checkedObsList.add(new AddEstTIV(
                        0,
                        false,
                        todayDate,
                        obsAbstractEstimateTVI.getSiteNumber(),
                        obsAbstractEstimateTVI.getTypeHome(),
                        obsAbstractEstimateTVI.getContractor(),
                        obsAbstractEstimateTVI.getJM_name(),
                        obsAbstractEstimateTVI.getJobOrMat(),
                        obsAbstractEstimateTVI.getBindJob(),
                        obsAbstractEstimateTVI.getQuantity(),
                        obsAbstractEstimateTVI.getUnit(),
                        obsAbstractEstimateTVI.getPriceOne(),
                        obsAbstractEstimateTVI.getPriceSum(),
                        obsAbstractEstimateTVI.getBuildingPart()
                ));
            }

        return checkedObsList;
    }

    public ObservableList<EstimateTVI> getSelectedCheckObs(ObservableList<AddEstTIV> items) {
        ObservableList<EstimateTVI> checkedObsList = FXCollections.observableArrayList();

        for (AddEstTIV obsItem : items) {
            if (obsItem.getCheck() == true) {
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

    @FXML
    private void testADD(ActionEvent event) {
        if (!elemTableView.isEditable()) {
            addButton.setText("Ok");
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
                    buildingPart
            ));
            elemTableView.setEditable(true);
        } else {
            AddEstTIV lastItem = elemTableView.getItems().get(elemTableView.getItems().size() - 1);
            if (lastItem.getJM_name().equals("-")
                    || lastItem.getUnit().equals("-")
                    || lastItem.getPriceSum().equals(0)) {
                System.out.println("\033[34m ---> wrong edit in last added item");
                System.out.println(lastItem.toString());

            } else {
                addButton.setText("+");
                elemTableView.refresh();
                elemTableView.setEditable(false);
            }
        }

    }


    @FXML
    private void handle_addMarkedRow(ActionEvent event) {
        ObservableList<EstimateTVI> selectedItems = getSelectedCheckObs(elemTableWrapperView.getItems())
                .stream()
                .filter(item -> !"-".equals(item.getUnit()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        rootTableView.getItems().addAll(selectedItems);
        Stage appStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        appStage.close();
    }

    @FXML
    private void handle_cencelButton(ActionEvent event) {
        Stage appStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        appStage.close();
    }
}
