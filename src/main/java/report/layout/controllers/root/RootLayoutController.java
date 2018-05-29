
package report.layout.controllers.root;

import java.io.File;

import javafx.scene.control.*;
import report.Report;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import report.layout.controllers.addSite.AddSiteController;
import report.layout.controllers.DeleteController;
import report.layout.controllers.LogController;
import report.layout.controllers.estimate.EstimateController;
import report.layout.controllers.expences.ExpensesController;
import report.layout.controllers.expences.ExpensesControllerUtils;
import report.layout.controllers.intro.IntroLayoutController;
import report.models.coefficient.FormulaQuery;
import report.models.coefficient.Formula;
import report.models.view.nodesHelpers.InputValidator;
import report.models.view.nodesFactories.FileChooserFactory;
import report.models.view.wrappers.toString.CounterAgentToStringWrapper;
import report.models.view.wrappers.toString.ToStringWrapper;
import report.usage_strings.PathStrings;
import report.models.view.nodesHelpers.StageCreator;
import report.models.sql.sqlQuery.BackUpQuery;
import report.entities.items.site.PreviewTIV;
import report.models.sql.sqlQuery.InsertFileXLSQuery;
import report.entities.items.site.SiteDao;
import report.usage_strings.SQL;


public class RootLayoutController implements Initializable {

    private Report reportApp;
    private DeleteController delController;
    private AddSiteController siteAddController;
    private EstimateController showEstController;
    private ExpensesController expensesController;
    private IntroLayoutController introControllre;

    private RootControllerService rootService = new RootControllerService(this);

    private String selectedTreeElemParent;
    private String selectedTreeElem;

    @FXML
    private ComboBox<Object> comboQueueNum, comboSiteCondition;
    @FXML
    private TreeView<ToStringWrapper> treeViewSite;
    @FXML
    private TitledPane infoTitledPane, changesTitledPane;

    @FXML
    private MenuItem printEstToXmlMenuItem;

    @FXML
    Label lll;
    @FXML
    TextField findSiteByNameTF;

    private TableView previewTable;
    private static final TableView changeTable = ExpensesControllerUtils.getChangeView();

    /*!******************************************************************************************************************
     *                                                                                                      Getter/Setter
     ********************************************************************************************************************/
    public void setReportApp(Report reportApp) {
        this.reportApp = reportApp;
    }

    public RootLayoutController getRootController() {
        return this;
    }

    public TableView getPreviewTable() {
        return previewTable;
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
        treeViewSite.setCellFactory(new TVCellFactory());
        treeViewSite.setShowRoot(false);
    }

    private void init_previewTable() {
        previewTable = RootControllerNodeUtils.getSite();
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
        if (!(reportApp.getCenterController() instanceof IntroLayoutController)) {
            reportApp.initIntroLayout()
                    .<IntroLayoutController>getController()
                    .updateTables();
        } else {
            reportApp.<IntroLayoutController>getCenterController().updateTables();
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
        new StageCreator(PathStrings.Layout.COR_FILTER, "Фильр / Выписка").loadNewWindow();

    }

    @FXML
    private void handle_Log(ActionEvent event) {
        new StageCreator(PathStrings.Layout.LOG, "Log").loadNewWindow();

    }

    @FXML
    private void handle_CommonProperties(ActionEvent event) {
        new StageCreator(PathStrings.Layout.ALL_PROPERTIES, "Общие параметры").loadNewWindow();
    }

    @FXML
    private void handle_PrintToXML(ActionEvent event) {

        File selectedFile;
        if (EstimateController.Est.Base.isExist()
                && showEstController.getBaseTab().isSelected()) {
            selectedFile = FileChooserFactory.saveEst(EstimateController.Est.Base);
            if (selectedFile != null) {
                rootService.printEstBase(selectedFile);
                LogController.appendLogViewText("Базовая смета сохранена в файл");
            }
        } else if ((EstimateController.Est.Changed.isExist()
                && showEstController.getChangeTab().isSelected())) {
            selectedFile = FileChooserFactory.saveEst(EstimateController.Est.Changed);
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
        StageCreator siteAddLayout
                = new StageCreator(PathStrings.Layout.SITE_ADD, "Добавить Участок").loadNewWindow();
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
        new StageCreator(PathStrings.Layout.FIN_RES).loadIntoRootBorderPaneCenter();
    }

    @FXML
    private void handle_PlanningButton(ActionEvent event) {
        new StageCreator(PathStrings.Layout.PLANNING).loadIntoRootBorderPaneCenter();
    }


    @FXML
    private void handle_CommonInfButton(ActionEvent event) {
        new StageCreator(PathStrings.Layout.INTRO)
                .loadIntoRootBorderPaneCenter()
                .<IntroLayoutController>getController()
                .updateTables();
    }

    @FXML
    private void handle_accountButton(ActionEvent event) {
        new StageCreator(PathStrings.Layout.ACC).loadIntoRootBorderPaneCenter();


    }

    /*!******************************************************************************************************************
     *                                                                                                     Inner Classes
     ********************************************************************************************************************/
    class TVCellFactory implements Callback<TreeView<ToStringWrapper>, TreeCell<ToStringWrapper>> {

        @Override
        public TreeCell<ToStringWrapper> call(TreeView<ToStringWrapper> param) {
            TreeCell<ToStringWrapper> cell = new TreeCell<ToStringWrapper>() {

                @Override
                protected void updateItem(ToStringWrapper item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setText(null);
                    } else {
                        setText(getItem() == null ? "" : getItem().toString());
                    }
                    setGraphic(null);
                }

            };
            final ContextMenu contextMenu = new ContextMenu();

            final MenuItem siteEditItem = new MenuItem("Редактировать") {{
                setDisable(true);
            }};
            final MenuItem contAddItem = new MenuItem("Добавить субподрядчика") {{
                setDisable(true);
            }};
            final SeparatorMenuItem separator = new SeparatorMenuItem() {{
                setDisable(true);
            }};
            final MenuItem contDelItem = new MenuItem("Удалить") {{
                setDisable(true);
            }};
            siteEditItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    StageCreator expensesLayout
                            = new StageCreator(PathStrings.Layout.EXPENSES,
                            "Свойства Участка № "
                                    + selectedTreeElemParent
                                    + " / "
                                    + selectedTreeElem)
                            .loadNewWindow();

                    expensesController = expensesLayout.getController();
                    expensesController.setRootController(getRootController());
                    expensesController.setControllerStage(expensesLayout.getStage());
                    expensesLayout.getStage().setResizable(false);
//                    expensesLayout.getStage().show();
                }
            });
            contAddItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    StageCreator contAddLayout
                            = new StageCreator(PathStrings.Layout.CONT_ADD, "Добавить Субподрядчика")
                            .loadNewWindow();
//                    contAddController = contAddLayout.getController();
//                    contAddController.setRootController(getRootController());
//                    contAddController.init_InfLabels(selectedTreeElemParent);
//                    contAddController.setTreeViewSite(treeViewSite);
                }
            });
            contDelItem.setOnAction(event -> {
                StageCreator delLayout
                        = new StageCreator(PathStrings.Layout.DEL_SITE, "Удалить")
                        .loadNewWindow();
                delController = delLayout.getController();
                delController.setRootController(getRootController());
                delController.init_delLabels(selectedTreeElem, selectedTreeElemParent);
//                    delLayout.getStage().show();
            });


            contextMenu.getItems().addAll(siteEditItem, contAddItem, separator, contDelItem);

            cell.contextMenuProperty().bind(
                    Bindings.when(cell.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            cell.setOnMouseClicked((MouseEvent event) -> {
                if (((TreeView) cell.getTreeView()).getTreeItem(cell.getIndex()).isLeaf()) {
                    selectedTreeElemParent = ((TreeView) cell.getTreeView())
                            .getTreeItem(cell.getIndex())
                            .getParent()
                            .getValue().toString();

                    CounterAgentToStringWrapper selectedItem = (CounterAgentToStringWrapper) cell.getTreeView().getTreeItem(cell.getIndex()).getValue();
                    RootControllerService.initCounterAgentHolder(selectedTreeElemParent,selectedItem.getEntity());

                    selectedTreeElem = selectedItem.getEntity().getName();
                    EstimateController.Est.Common.setCountAgentTVI(selectedItem.getEntity());

                    siteEditItem.setDisable(false);
                    contAddItem.setDisable(false);
                    separator.setDisable(false);
                    contDelItem.setDisable(false);
                    printEstToXmlMenuItem.setDisable(false);

                    //Update Preview TableWrapper OBS-LIST
                    EstimateController.Est.Common.setSiteObs(new SiteDao(selectedTreeElemParent, selectedItem.getEntity().getIdCountConst()).getData());
                    previewTable.setItems(EstimateController.Est.Common.getPreviewObservableList());

                    //create Coefficient
                    Formula formula = new FormulaQuery().getFormula();
                    //Set TAXES_ALL
                    EstimateController.Est.Common.getSiteItem(SQL.Site.TAXES_ALL).setSecondValue(formula.allTaxes());

                    showEstController = new StageCreator(PathStrings.Layout.SITE_EST)
                            .loadIntoRootBorderPaneCenter()
                            .getController();
                    showEstController.setRootController(getRootController());

                }
//                else if (cell.getItem().equals("Участки №")
//                        && !((TreeView) cell.getTreeView()).getTreeItem(cell.getIndex()).isLeaf()) {
//                    siteEditItem.setDisable(true);
//                    contAddItem.setDisable(true);
//                    separator.setDisable(true);
//                    contDelItem.setDisable(true);

//                }
                else {
                    siteEditItem.setDisable(true);
                    contAddItem.setDisable(true);
                    separator.setDisable(true);
                    contDelItem.setDisable(true);

                }
            });
            return cell;
        }
    }
}

