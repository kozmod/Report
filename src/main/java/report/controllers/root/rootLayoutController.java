
package report.controllers.root;

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

import report.controllers.*;
import report.controllers.intro.IntroLayoutController;
import report.view_models.InputValidator;
import report.view_models.nodes_factories.FileChooserFactory;
import report.usege_strings.PathStrings;
import report.controllers.showEstLayoutController.Est;
import report.view_models.StageCreator;
import report.models.sql.sqlQuery.BackUpQuery;
import report.view_models.nodes_factories.TableFactory;
import report.entities.items.site.TableItemPreview;
import report.models.sql.sqlQuery.InsertFileXLSQuery;
import report.entities.items.site.ItemSiteDAO;


public class rootLayoutController implements Initializable {
    
    // ref to Report App      
    private Report                      reportApp;                                                                           
    private contAddLayoutController contAddController;
    private delLayoutController delController;
    private siteAddLayoutController siteAddController;
    private showEstLayoutController showEstController;
    private expensesLayoutController expensesController;
    private IntroLayoutController introControllre;

    private RootControllerService rootService = new RootControllerService(this);

    private String selectedTreeElemParent;
    private String selectedTreeElem;

    @FXML private ComboBox<Object> comboQueueNum, comboSiteCondition;
    @FXML private TreeView<String> treeViewSite;
    @FXML private TitledPane infoTitledPane, changesTitledPane;

    @FXML private MenuItem printEstToXmlMenuItem;

    @FXML Label lll;
    @FXML TextField findSiteByNameTF;

    private TableView previewTable;
    private static final TableView changeTable = TableFactory.getChangeView();

/*!******************************************************************************************************************
*                                                                                                      Getter/Setter
********************************************************************************************************************/
    public void setReportApp(Report reportApp) {                                
        this.reportApp = reportApp;
    }

    public rootLayoutController getRootController() {
        return this;
    }

    public TableView getPreviewTable() {
        return previewTable;
    }

/*!******************************************************************************************************************
*                                                                                                     	    Update
********************************************************************************************************************/

    
    public void update_previewTable(ObservableList<TableItemPreview> items){
        previewTable.refresh();
    }

    public static void update_changeTable(ObservableList item){
        if(!item.isEmpty() && item != null) changeTable.setItems(item);
    }


    public void setTreeViewDisable(boolean v){
        treeViewSite.setDisable(v);
    }


    public void update_TreeView(){
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

    private void init_TreeComboBlock(){
       comboQueueNum.setItems(rootService.getComboQueueValues());
       comboSiteCondition.setItems(rootService.getComboSiteConditionValues());
    }
   
       
    
    private void init_siteTreeView(){
        treeViewSite.setRoot(rootService.getTreeViewList());
        treeViewSite.setCellFactory(new TVCellFactory());
    }
    
    private void init_previewTable(){
        previewTable = TableFactory.getSite();
        infoTitledPane.setContent(previewTable);
    }


/*!******************************************************************************************************************
*                                                                                                     	    HANDLERS
********************************************************************************************************************/
//Menu ITEMS -->     
    @FXML
    private void handle_menuSqlConnect(ActionEvent event) {                   //<===SQL connect
        init_TreeComboBlock();
        init_siteTreeView();
        init_previewTable();

        if(!(reportApp.getCenterController() instanceof IntroLayoutController)) {
            reportApp.initIntroLayout()
                    .<IntroLayoutController>getController()
                    .updateTables();
        }else{
            reportApp.<IntroLayoutController>getCenterController().updateTables();
        }
    }
    
    @FXML
    private void handle_menuFileAccLoad(ActionEvent event) {

        File selectedFile = FileChooserFactory.Open.getExcel();

        if(selectedFile != null){
            new InsertFileXLSQuery()
                    .insertRowsFromXls_Account(selectedFile.getPath());
        }else{
            System.out.println("Отмена FILE CH00SER xls/xlsx --- ACCOUNT");
        }
        System.out.println( "1 - "+PathStrings.Files.EXCEL);
        System.out.println("2     " +System.getProperty("user.dir") );

    }
    
    
    @FXML
    private void handle_menuFileLoad(ActionEvent event) {
        File selectedFile = FileChooserFactory.Open.getExcel();
        
        if(selectedFile != null){
            new InsertFileXLSQuery().insertRowsFromXls_Site_Numeric(selectedFile.getPath());
//            System.out.println(selectedFile.getPath());
        }else{
            System.out.println("Отмена FILE CH00SER xls/xlsx");
        }
    }
    
    
    @FXML
    private void handle_menuFileBackup(ActionEvent event) {BackUpQuery.backUp();}
    
    @FXML
    private void handle_menuFileRestore(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(
//                new File( PathStrings.Files.BACK_UP_SQL));
//        fileChooser.setTitle("Open  File");
        File selectedFile = FileChooserFactory.Open.getSqlBackUp();
        
        if(selectedFile != null){
            BackUpQuery.restore(selectedFile.getPath());
        }else{
            System.out.println("Отмена FILE CHUSER xls/xlsx");
        }

    }
    
    @FXML
    private void handle_menuFileDropBD(ActionEvent event) {
        BackUpQuery.Drop();

    }
    
    @FXML
    private void handle_AccountFilter(ActionEvent event) {
        StageCreator corFilterLayout
                        = new StageCreator(PathStrings.Layout.COR_FILTER, "Фильр / Выписка").loadNewWindow();
         corFilterLayout.getStage().show();
        
    }
    
    @FXML
    private void handle_Log(ActionEvent event) {
        StageCreator logLayout
                        = new StageCreator(PathStrings.Layout.LOG, "Log").loadNewWindow();
        logLayout.getStage().show();
    }
    @FXML
    private void handle_CommonProperties(ActionEvent event) {
        StageCreator allPropLayout
                        = new StageCreator(PathStrings.Layout.ALL_PROPERTIES, "Общие параметры").loadNewWindow();
        allPropLayout.getStage().show();
    }

    @FXML
    private void handle_AddSite(ActionEvent event) {
        StageCreator siteAddLayout
                = new StageCreator(PathStrings.Layout.SITE_ADD, "Добавить Участок").loadNewWindow();
        siteAddController = siteAddLayout.getController();
        siteAddController.setRootController(getRootController());

        siteAddLayout.getStage().show();

    }

    @FXML
    private void handle_PrintToXML(ActionEvent event) {

        File selectedFile;
        if(Est.Base.isExist()
                && showEstController.getBaseTab().isSelected()) {
            selectedFile = FileChooserFactory.Save.saveEst(Est.Base);
            if (selectedFile != null) {
                rootService.printEstBase(selectedFile);
//                new PrintEstimate(Est.Base.getAllItemsList_Live(), selectedFile.toPath());
                LogLayoutController.appendLogViewText("Базовая смета сохранена в файл");
            }
        }else if((Est.Changed.isExist()
                && showEstController.getChangeTab().isSelected())) {
            selectedFile = FileChooserFactory.Save.saveEst(Est.Changed);
            if (selectedFile != null) {
                rootService.printEstChange(selectedFile);
//                new PrintEstimate(Est.Changed.getAllItemsList_Live(), selectedFile.toPath());
                LogLayoutController.appendLogViewText("Изменненная смета сохранена в файл");
            }
        }else{
            LogLayoutController.appendLogViewText("Не выбрана смета для печати");
        }

    }


    
//Rite Border  BUTTONS -->
    @FXML
    private void handle_OkButton(ActionEvent event) {

        String combo_QueueNumValue, combo_SiteConditionValue;
        if(comboQueueNum.getValue() == null)
             combo_QueueNumValue ="%";
        else combo_QueueNumValue = comboQueueNum.getValue().toString();
        if(comboSiteCondition.getValue() == null)
             combo_SiteConditionValue ="%";
        else combo_SiteConditionValue = comboSiteCondition.getValue().toString();
        treeViewSite.setRoot(rootService.getTreeViewList(combo_QueueNumValue, combo_SiteConditionValue));
    }

    @FXML
    private void handle_FindSiteByName(ActionEvent event) {
        String findSiteNumber = findSiteByNameTF.getText();
        if(InputValidator.isSiteNumberValid(findSiteNumber) && findSiteNumber != null){
            treeViewSite.setRoot(rootService.finTreeViewListWithOneElement(findSiteNumber));
        }


    }

    
    @FXML
    private void handle_FinResButton(ActionEvent event) {
//         StageCreator finResLayout  =
                        new StageCreator(PathStrings.Layout.FIN_RES,"").loadIntoRootBorderPaneCenter();
    }
    
    @FXML
    private void handle_accountButton(ActionEvent event) {  
//        StageCreator accLayout  =
                        new StageCreator(PathStrings.Layout.ACC,"").loadIntoRootBorderPaneCenter();
                     
        
    }





/*!******************************************************************************************************************
*                                                                                                     InputValidator
********************************************************************************************************************/



/*!******************************************************************************************************************
*                                                                                                     Inner Classes
********************************************************************************************************************/
 //inner class - TreeView Cell Factory =========================================================================
 class TVCellFactory implements Callback<TreeView<String>, TreeCell<String>> {

        @Override
        public TreeCell<String> call(TreeView<String> param) { 
            TreeCell<String> cell = new TreeCell<String>(){
                
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                    setText(null);
                    } else {
                    setText(getItem() == null ? "" : getItem()); 
                    }
                    setGraphic(null);
                    }
                
                };
            final ContextMenu contextMenu = new ContextMenu();
                
//            final MenuItem siteAddItem        = new MenuItem("Добавить участок")      {{ setDisable(true); }};
            final MenuItem siteEditItem       = new MenuItem("Редактировать")         {{ setDisable(true); }};
            final MenuItem contAddItem        = new MenuItem("Добавить субподрядчика"){{ setDisable(true); }};
            final SeparatorMenuItem separator = new SeparatorMenuItem()                   {{ setDisable(true); }};
            final MenuItem contDelItem        = new MenuItem("Удалить")               {{ setDisable(true); }};

            
//            siteAddItem.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    StageCreator siteAddLayout
//                            = new StageCreator(PathStrings.Layout.SITE_ADD, "Добавить Участок");
//                    siteAddController = siteAddLayout.getController();
//                    siteAddController.setRootController(getRootController());
//
//                    siteAddLayout.getStage().show();
//                }
//            });
            siteEditItem.setOnAction(new EventHandler<ActionEvent>() {  
                @Override  
                public void handle(ActionEvent event) {  
//                    previewTable.setEditable(true);
//                    previewTable.setEditColor();
                    StageCreator expensesLayout
                            = new StageCreator(PathStrings.Layout.EXPENSES,
                            "Свойства Участка № "
                                    + selectedTreeElemParent
                                    +" / "
                                    + selectedTreeElem)
                            .loadNewWindow();
                    
                    expensesController = expensesLayout.getController();
                    expensesController.setRootController(getRootController());
                    expensesController.setControllerStage(expensesLayout.getStage());
                    
//                    expensesController.setPreviewOBS(previewTableObs);
                    
                            
                    expensesLayout.getStage().setResizable(false);
                    expensesLayout.getStage().show();
                }  
            });
            contAddItem.setOnAction(new EventHandler<ActionEvent>() {  
                @Override  
                public void handle(ActionEvent event) {
                    StageCreator contAddLayout
                            = new StageCreator(PathStrings.Layout.CONT_ADD, "Добавить Субподрядчика")
                            .loadNewWindow();
                    contAddController = contAddLayout.getController();
                    contAddController.setRootController(getRootController());
                    contAddController.init_InfLabels(selectedTreeElemParent);
          
                    contAddLayout.getStage().show(); 
                    }  
            });
            contDelItem.setOnAction(new EventHandler<ActionEvent>() {  
                @Override  
                public void handle(ActionEvent event) {  
                    StageCreator delLayout
                            = new StageCreator(PathStrings.Layout.DEL_SITE, "Удалить")
                            .loadNewWindow();
                    delController = delLayout.getController();
                    delController.setRootController(getRootController());
                    delController.init_delLabels(selectedTreeElem, selectedTreeElemParent);
                
                    delLayout.getStage().show();
                }  
            });
            
            
            contextMenu.getItems().addAll( siteEditItem, contAddItem, separator, contDelItem);
            
            cell.contextMenuProperty().bind(  
                        Bindings.when(cell.emptyProperty())  
                        .then((ContextMenu)null)  
                        .otherwise(contextMenu)  
                    );  
            
            cell.setOnMouseClicked((MouseEvent event) -> {
                if(((TreeView)cell.getTreeView()).getTreeItem(cell.getIndex()).isLeaf()){
                    selectedTreeElemParent
                            = ((TreeView)cell.getTreeView()).getTreeItem(cell.getIndex()
                            ).getParent().getValue().toString();
                    selectedTreeElem
                            = ((TreeView)cell.getTreeView()).getTreeItem(cell.getIndex()
                            ).getValue().toString();
//                    siteAddItem  .setDisable(true);
                    siteEditItem .setDisable(false);
                    contAddItem  .setDisable(false);
                    separator    .setDisable(false);
                    contDelItem  .setDisable(false);
                    printEstToXmlMenuItem.setDisable(false);
                    
                    //Update Preview TableWrapper OBS-LIST
                    Est.Common.setPreviewObs(new ItemSiteDAO(selectedTreeElemParent,selectedTreeElem).getList());
//                    update_previewTable(Est.Common.getPreviewObservableList());
                    previewTable.setItems(Est.Common.getPreviewObservableList());
                    
                    showEstController = new StageCreator(PathStrings.Layout.SITE_EST,"")
                            .loadIntoRootBorderPaneCenter()
                            .getController();
                    showEstController.setRootController(getRootController());

                    
                    //Comput Coeffecient
//                        Formula.coeffSetter().setObsList(previewTableObs).setCoefficient();
//                    Formula_test.coefficient.computeCoef();
                    

                }else if(cell.getItem().equals("Участки №")
                        && !((TreeView)cell.getTreeView()).getTreeItem(cell.getIndex()).isLeaf()){
//                    siteAddItem  .setDisable(false);
                    siteEditItem .setDisable(true);
                    contAddItem  .setDisable(true);
                    separator    .setDisable(true);
                    contDelItem  .setDisable(true);

                } else{
                    siteEditItem .setDisable(true);
                    contAddItem  .setDisable(true);
                    separator    .setDisable(true);
                    contDelItem  .setDisable(true);

                }
            });
            return cell;
        }
    } 
}

