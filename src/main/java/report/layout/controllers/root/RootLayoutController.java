
package report.layout.controllers.root;

import java.io.File;

import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import report.layout.controllers.CorAccountController;
import report.layout.controllers.addSite.AddSiteController;
import report.layout.controllers.DeleteController;
import report.layout.controllers.LogController;
import report.layout.controllers.estimate.EstimateController_old;
import report.layout.controllers.expences.ExpensesController;
import report.layout.controllers.expences.ExpensesControllerNodeUtils;
import report.layout.controllers.finRes.FinResController;
import report.layout.controllers.intro.IntroLayoutController;
import report.layout.controllers.planning.PlanningController;
import report.models.view.nodesHelpers.InputValidator;
import report.models.view.nodesFactories.FileChooserFactory;
import report.models.view.wrappers.toString.ToStringWrapper;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.views.RootViewFx;
import report.spring.views.ViewFx;
import report.usage_strings.PathStrings;
import report.models.view.nodesHelpers.FxmlStage;
import report.models.sql.sqlQuery.BackUpQuery;
import report.entities.items.site.PreviewTIV;
import report.models.sql.sqlQuery.InsertFileXLSQuery;

public class RootLayoutController implements Initializable {

    @Autowired
    private RootViewFx rootViewFx;
    @Autowired
    private RootControllerNodeFactory rootControllerNodeFactory;

    private DeleteController delController;
    private AddSiteController siteAddController;
    private EstimateController_old estController;
    private ExpensesController expensesController;
    private IntroLayoutController introControllre;

    private RootControllerService rootService = new RootControllerService();

    @FXML
    private ComboBox<Object> comboQueueNum, comboSiteCondition;
    @FXML
    private TreeView<ToStringWrapper> treeViewSite;
    @FXML
    private TitledPane infoTitledPane, changesTitledPane;

    @FXML
    private MenuItem printEstToXmlMenuItem;

    @FXML
    TextField findSiteByNameTF;

    private TableView previewTable;
    private static final TableView changeTable = ExpensesControllerNodeUtils.getChangeView();

    /*!******************************************************************************************************************
     *                                                                                                      Getter/Setter
     ********************************************************************************************************************/
//    public void setReportApp(Report reportApp) {
//        this.reportApp = reportApp;
//    }

    public RootLayoutController getRootController() {
        return this;
    }

    public TableView getPreviewTable() {
        return previewTable;
    }


    public void setExpensesController(ExpensesController expensesController) {
        this.expensesController = expensesController;
        expensesController.setRootController(this);
    }

    public void setDelController(DeleteController delController) {
        this.delController = delController;
        delController.setRootController(this);
    }

    public void setEstController(EstimateController_old showEstController) {
        this.estController = showEstController;
        showEstController.setRootController(this);
    }

    /*!******************************************************************************************************************
     *                                                                                                     	    Update
     ********************************************************************************************************************/


    public void update_previewTable(ObservableList<PreviewTIV> items) {
        previewTable.refresh();
    }

    public static void update_changeTable(ObservableList item) {
        if (!item.isEmpty() && item != null) changeTable.setItems(item);
    }


    public void setTreeViewDisable(boolean v) {
        treeViewSite.setDisable(v);
    }

    public void update_SelectedTreeViewItem(String value) {
        //TODO сделать обновление
//        treeViewSite.getSelectionModel().getSelectedItem().setValue(value);
    }


    public void update_TreeView() {
        int item = treeViewSite.getSelectionModel().getSelectedIndex();
        treeViewSite.setRoot(rootService.getTreeViewList());
        treeViewSite.getSelectionModel().select(item);
    }


    /*!******************************************************************************************************************
     *                                                                                                          INITIALIZE
     ********************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        changesTitledPane.setContent(changeTable);
        printEstToXmlMenuItem.setDisable(true);


    }
    /*!******************************************************************************************************************
     *                                                                                                               INIT
     ********************************************************************************************************************/

    private void init_TreeComboBlock() {
        comboQueueNum.setItems(rootService.getComboQueueValues());
        comboSiteCondition.setItems(rootService.getComboSiteConditionValues());
    }


    private void init_siteTreeView() {
        treeViewSite.setRoot(rootService.getTreeViewList());
        treeViewSite.setCellFactory(rootControllerNodeFactory.getRootTreeViewCellFactory(this));
        treeViewSite.setShowRoot(false);
    }

    private void init_previewTable() {
        previewTable = rootControllerNodeFactory.getSite();
        infoTitledPane.setContent(previewTable);
    }


    /*!******************************************************************************************************************
     *                                                                                                     	    HANDLERS
     ********************************************************************************************************************/
//Menu ITEMS -->     
    @FXML
    private void handle_menuSqlConnect(ActionEvent event) {
        init_TreeComboBlock();
        init_siteTreeView();
        init_previewTable();
//        if (!(reportApp.getCenterController() instanceof IntroLayoutController)) {
        if (rootViewFx.centerControllerEquals(IntroLayoutController.class)) {

//            reportApp.<IntroLayoutController>getCenterController().updateTables();
            rootViewFx.<IntroLayoutController>getCenterController().updateTables();

        } else {

//            ViewFx<GridPane,IntroLayoutController> introLayoutController = beanFactory.getBean("introView",ViewFx.class);
            ViewFx<GridPane,IntroLayoutController> introLayoutController = ApplicationContextProvider.getBean("introView");
            introLayoutController.getController().updateTables();
            rootViewFx.setCenter(introLayoutController);
////
////            reportApp.initIntroLayout()
////                    .<IntroLayoutController>getController()
////                    .updateTables();
        }
    }

    @FXML
    private void handle_menuFileAccLoad(ActionEvent event) {

        File selectedFile = FileChooserFactory.openExcelFolder();

        if (selectedFile != null) {
            new InsertFileXLSQuery()
                    .insertRowsFromXls_Account(selectedFile.getPath());
        } else {
            System.out.println("Отмена FILE CH00SER xls/xlsx --- ACCOUNT");
        }
        System.out.println("1 - " + PathStrings.Files.EXCEL);
        System.out.println("2     " + System.getProperty("user.dir"));

    }


    @FXML
    private void handle_menuFileLoad(ActionEvent event) {
        File selectedFile = FileChooserFactory.openExcelFolder();
        if (selectedFile != null) {
            new InsertFileXLSQuery().insertRowsFromXls_Site_Numeric(selectedFile.getPath());
        } else {
            System.out.println("Отмена FILE CH00SER xls/xlsx");
        }
    }


    @FXML
    private void handle_menuFileBackup(ActionEvent event) {
        BackUpQuery.backUp();
    }

    @FXML
    private void handle_menuFileRestore(ActionEvent event) {
        File selectedFile = FileChooserFactory.openSqlBackUpFolder();

        if (selectedFile != null) {
            BackUpQuery.restore(selectedFile.getPath());
        } else {
            System.out.println("Отмена FILE CHUSER xls/xlsx");
        }

    }

    @FXML
    private void handle_menuFileDropBD(ActionEvent event) {
        BackUpQuery.Drop();

    }

    @FXML
    private void handle_AccountFilter(ActionEvent event) {
        new FxmlStage(PathStrings.Layout.COR_FILTER, "Фильр / Выписка").loadAndShowNewWindow();

    }

    @FXML
    private void handle_Log(ActionEvent event) {
        new FxmlStage(PathStrings.Layout.LOG, "Log").loadAndShowNewWindow();

    }

    @FXML
    private void handle_CommonProperties(ActionEvent event) {
        new FxmlStage(PathStrings.Layout.ALL_PROPERTIES, "Общие параметры").loadAndShowNewWindow();
    }

    @FXML
    private void handle_PrintToXML(ActionEvent event) {

        File selectedFile;
        if (EstimateController_old.Est.Base.isExist()
                && estController.getBaseTab().isSelected()) {
            selectedFile = FileChooserFactory.saveEst(EstimateController_old.Est.Base);
            if (selectedFile != null) {
                rootService.printEstBase(selectedFile);
                LogController.appendLogViewText("Базовая смета сохранена в файл");
            }
        } else if ((EstimateController_old.Est.Changed.isExist()
                && estController.getChangeTab().isSelected())) {
            selectedFile = FileChooserFactory.saveEst(EstimateController_old.Est.Changed);
            if (selectedFile != null) {
                rootService.printEstChange(selectedFile);
                LogController.appendLogViewText("Изменненная смета сохранена в файл");
            }
        } else {
            LogController.appendLogViewText("Не выбрана смета для печати");
        }

    }

    @FXML
    private void handle_AddSite(ActionEvent event) {
        FxmlStage siteAddLayout
                = new FxmlStage(PathStrings.Layout.SITE_ADD, "Добавить Участок").loadAndShowNewWindow();
        siteAddController = siteAddLayout.getController();
        siteAddController.setRootController(getRootController());


    }

    //Rite Border  BUTTONS -->
    @FXML
    private void handle_OkButton(ActionEvent event) {

        String combo_QueueNumValue, combo_SiteConditionValue;
        if (comboQueueNum.getValue() == null)
            combo_QueueNumValue = "%";
        else combo_QueueNumValue = comboQueueNum.getValue().toString();
        if (comboSiteCondition.getValue() == null)
            combo_SiteConditionValue = "%";
        else combo_SiteConditionValue = comboSiteCondition.getValue().toString();
        treeViewSite.setRoot(rootService.getTreeViewList(combo_QueueNumValue, combo_SiteConditionValue));
    }

    @FXML
    private void handle_FindSiteByName(ActionEvent event) {
        String findSiteNumber = findSiteByNameTF.getText();
        if (InputValidator.isSiteNumberValid(findSiteNumber) && findSiteNumber != null) {
            treeViewSite.setRoot(rootService.finTreeViewListWithOneElement(findSiteNumber));
        }


    }


    @FXML
    private void handle_FinResButton(ActionEvent event) {
//        new FxmlStage(PathStrings.Layout.FIN_RES).loadIntoRootBorderPaneCenter();
        ViewFx<GridPane, FinResController>  viewFx = ApplicationContextProvider.getBean("finResView");
        rootViewFx.getView().setCenter(viewFx.getView());


    }

    @FXML
    private void handle_PlanningButton(ActionEvent event) {
//        new FxmlStage(PathStrings.Layout.PLANNING).loadIntoRootBorderPaneCenter();
        ViewFx<TabPane, PlanningController>  viewFx = ApplicationContextProvider.getBean("planingView");
        rootViewFx.getView().setCenter(viewFx.getView());
    }


    @FXML
    private void handle_CommonInfButton(ActionEvent event) {
        ViewFx<GridPane, IntroLayoutController>  viewFx = ApplicationContextProvider.getBean("introView");
        rootViewFx.getView().setCenter(viewFx.getView());
        viewFx.getController().updateTables();
    }

    @FXML
    private void handle_accountButton(ActionEvent event) {
       ViewFx<GridPane, CorAccountController> viewFx = ApplicationContextProvider.getBean("corAccountView");
        rootViewFx.getView().setCenter(viewFx.getView());
    }
}

