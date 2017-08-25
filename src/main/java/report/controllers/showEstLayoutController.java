package report.controllers;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import report.controllers.root.rootLayoutController;
import report.view_models.nodes.TableWrapper;
import report.view_models.nodes_factories.FileChooserFactory;
import report.usege_strings.PathStrings;
import report.usege_strings.SQL;

import report.servises.StageCreator;
import report.view_models.data_models.DecimalFormatter;
import report.view_models.data_models.EpochDatePickerConverter;
import report.view_models.nodes_factories.TableFactory;
import report.entities.items.KS.TableItemKS;
import report.entities.items.site.TableItemPreview;
import report.entities.items.TableItem;
import report.entities.items.estimate.TableItemEst;
import report.models.printer.PrintKS;
import report.entities.ItemDAO;
import report.view_models.nodes.TabModel;
import report.view_models.nodes.TableWrapperEST;
import report.entities.items.estimate.ItemEstDAO;
import report.entities.items.KS.ItemKSDAO;
import report.view_models.nodes.ContextMenuOptional;
import report.entities.items.site.ItemSiteDAO;


public class showEstLayoutController implements Initializable {
    
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//                                                                                                  ENUM 
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    /**
     * 
     * Enumeration of "Estimate Tables". 
     * <br>
     * All contain PreviewObs(parameters of Site).
     * <br>
     * Base/Changed - contain "TableType" and  Map of Tables.
     * <br>
     * KS - contain  Map of KS for current Site/Contractor.
     * <br>
     * Common - contain only parameters.
     */
    public enum Est {
        Base(1), Changed(2),Additional(3),KS, Common;
        
        //Constructors ---------------------------------------------------------------------
        Est(){ }
        Est(int val){ taleType = val; }

        public void printALLSum(){

            this.tab.getSumLabelValue();
        }
        
        //Setter ---------------------------------------------------------------------------
        public void setPreviewObs(ObservableList<TableItemPreview> obsList){
            Common.previewTableObs  = obsList;
            Base.previewTableObs    = obsList;
            Changed.previewTableObs = obsList;
            KS.previewTableObs      = obsList;
            
        }
        
        private void setTab(TabModel tab){this.tab = tab;}
        
        //Getter ---------------------------------------------------------------------------
        public int getTaleType()   {return taleType;}
        public Map getTabMap()     {return  tabMap;}

        public <T> T getSiteSecondValue(String sqlField){
            return (T) previewTableObs.stream()
                    .filter(o -> o.getSqlColumn().equals(sqlField))
                    .findFirst()
                    .get()
                    .getSecondValue();
        }
        public TableItemPreview getItem(String sqlField){
            return previewTableObs.stream()
                    .filter(o -> o.getSqlColumn().equals(sqlField))
                    .findFirst()
                    .get();
        }
        
        public ObservableList<TableItemPreview> getPreviewObservableList(){return previewTableObs;}
        
        public   ObservableList getAllItemsList_Live(){
            Collection<List> tempCollection = this.getTabMap().values();
//            return (List) tempCollection.stream().flatMap(p -> p.stream()).collect(Collectors.toList());
            return  tempCollection.stream().flatMap(Collection::stream).collect(Collector.of(
                                                FXCollections::observableArrayList,
                                                ObservableList::add,
                                                (l1, l2) -> { l1.addAll(l2); return l1; })
                                                               );
        }
        
        public ObservableList<?  extends TableItem> findItemsList_DL(TableItem selectedItem){
            if(selectedItem != null)
            return allItems.stream()
                    .filter((TableItem i) -> i.equalsSuperCalss(selectedItem))
                    .sorted(Comparator.comparingLong(item -> item.getDateCreate().getTime()))
//                    .sorted((item1, item2) -> Long.compare(item1.getDateCreate().getTime(), item2.getDateCreate().getTime()))
                    .collect(collectingAndThen(toList(), FXCollections::observableArrayList));
            else
            return FXCollections.observableArrayList();
        }
       
        public void updateList_DL(ItemDAO dao){ allItems = dao.getList();}
//        public void updateList_DL(){ createTab();}
        
        
        
        //Update ---------------------------------------------------------------------------
        public void updatePreviewTable(){new ItemSiteDAO().dellAndInsert(previewTableObs);}
        
        public void updateTabData(){this.createTabMap(); this.tab.updateTablesItems(); }
 
        //Methots --------------------------------------------------------------------------
//        public boolean isExist(){return tabMap != null ?!(tabMap.isEmpty());}
        public boolean isExist(){return tabMap != null ? !(tabMap.isEmpty()): false;}
        
        public void setTabsEditable(){
            if(Changed.tabMap.isEmpty()) 
                 Base.tab.setEditable(true);
            else Base.tab.setEditable(false);
        }
                
        protected void createTabMap(){
            switch(this){
                case Base: 
                case Changed:
                        allItems = new ItemEstDAO(this).getList();
                        tabMap   = allItems.stream()
                                .filter(item  -> item.getDel() != 1 )
                                .sorted(Comparator.comparing(TableItem::getJM_name))
//                                .collect(Collectors.groupingBy(TableItem::getBildingPart));
                                .collect(Collectors.groupingBy(TableItem::getBildingPart,
//                                        TableItem::getBuildingPart,
                                         Collector.of(
                                                () -> FXCollections.observableArrayList(TableItemEst.extractor()),
                                                ObservableList::add,
                                                (l1, l2) -> { l1.addAll(l2); return l1; })
                                                               ));
                break;
                case KS:
                        allItems = new ItemKSDAO(this).getList();
                        tabMap   = allItems.stream()
                                .filter(item  -> item.getDel() != 1 )
                                .sorted(Comparator.comparing(TableItem::getJM_name))
                                .collect(Collectors.groupingBy(item -> ((TableItemKS)item).getKSNumber(),
                                         Collector.of(
                                                () -> FXCollections.observableArrayList(TableItemKS.extractor()),
                                                ObservableList::add,
                                                (l1, l2) -> { l1.addAll(l2); return l1; })
                                                               ));

//                                .collect(Collectors.groupingBy((TableItem item) -> ((TableItemKS)item).getKSNumber()));
                break;
                case Additional :
                    allItems = Base.allItems.stream()
                                .filter( item -> !Changed.allItems.contains(item))
                                .collect(Collector.of(
                                                () -> FXCollections.observableArrayList(TableItemEst.extractor()),
                                                ObservableList::add,
                                                (l1, l2) -> { l1.addAll(l2); return l1; })
                                                               );
                    tabMap   = allItems.stream()
                                .filter(item  -> item.getDel() != 1 )
                                .collect(Collectors.groupingBy(item -> ((TableItemEst)item).getBildingPart(),
//                                        TableItem::getBildingPart,
                                         Collector.of(
                                                () -> FXCollections.observableArrayList(),
                                                ObservableList::add,
                                                (l1, l2) -> { l1.addAll(l2); return l1; })
                                                               ));
                    
                break;
            }
            
        }
        
        public TableItem  findEqualsElevent(TableItem inpItem){
           return (TableItem) tabMap.values().stream()
                   .flatMap(mapItem -> ((List) mapItem).stream())
                   .filter(item -> ((TableItem)item).equalsSuperCalss(inpItem))
                   .findFirst()
                   .get();
        }
        
        //enum VAR --------------------------------------------------------------------------- 
        
        private int      taleType;
        private Map<Object, ObservableList<TableItem>> tabMap;
        private TabModel tab;
        private ObservableList<? extends TableItem> allItems;
        private ObservableList<TableItemPreview>    previewTableObs;
    }    
/*!******************************************************************************************************************
*                                                                                                       Fields
********************************************************************************************************************/  
    
    
    private rootLayoutController rootController;
    private ksAddLayoutController ksAddController;
   
    //preview table
    private Map<Integer, List<TableItemKS>> ksMap;
    private Map<String, List<TableItem>>    mapBase,mapChange;

    @FXML private Label            ksSumLabel, ksDateLabel, erroeLable;
    @FXML private VBox             baseVBox, changedVBox;
    @FXML private ListView<Object> listKS;
    @FXML private DatePicker       dateKSfrom, dateKSto;
    @FXML private GridPane         gridPaneKS, gridPaneAdditional;
    @FXML private ScrollPane       scrollPaneBase,scrollPaneChanged;
    @FXML private ComboBox         comboAdditional;
    @FXML private Tab              baseTab, changeTab, dopTab;



    
    private Label                 lableSumBase,lableSumChanged;    
    private TableWrapperEST<TableItemKS> tableKS = TableFactory.getKS();
    private TableWrapper tableWrapperAdditional = TableFactory.getAdditional();
    
    
/*!******************************************************************************************************************
*                                                                                                     Getter/Setter
********************************************************************************************************************/  
    
    public void setRootController(rootLayoutController rootController) {this.rootController = rootController;}

    public Tab getBaseTab()  {return baseTab;}
    public Tab getChangeTab(){return changeTab;}

/*!******************************************************************************************************************
*                                                                                                               INIT
********************************************************************************************************************/   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        Properties p = PropertiesFactory.getFormula();
//        System.out.println(p.getProperty("saleExpenses"));
//          Properties p = new ItemVariableDAO().getList();
//        System.out.println(p.getProperty("saleExpenses"))
        
//        scrollPaneBase.setPrefHeight(ScreenSize.height.getValue() - 200);
//        scrollPaneChanged.setPrefHeight(ScreenSize.height.getValue() - 200);
        
        dateKSfrom.setEditable(true);
        dateKSfrom.setConverter(new EpochDatePickerConverter());
        dateKSto.setEditable(true);
        dateKSto.setConverter(new EpochDatePickerConverter());
        
        init_EstLayoutTabs();
        
        //add Aditional table
        gridPaneAdditional.add(tableWrapperAdditional,0, 1, 2, 1);
        GridPane.setMargin(tableWrapperAdditional, new Insets(5,5,5,5));
        
        //add KS table
        ksSumLabel.textProperty().bind(Bindings.convert(tableKS.getSumProperty()));
        gridPaneKS.add(tableKS,1,2); 
        
        
        
      
    }
/*!******************************************************************************************************************
*                                                                                                            Methods
********************************************************************************************************************/

    public void init_EstLayoutTabs(/*ObservableList<PreviewTableItem> obs?/*String SiteNumber, String contName*/){
       //add site number and Contractor
//       Est.Common   .setPreviewObs(obs);
       Est.Base     .createTabMap();
       Est.Changed  .createTabMap();
       Est.KS       .createTabMap();
       Est.Additional       .createTabMap();
     //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  
    tableWrapperAdditional.setItems(Est.Additional.getAllItemsList_Live());

       
       if(Est.Base    .isExist())init_Est(Est.Base);    
       else init_Est_Null(Est.Base);
       if(Est.Changed .isExist())init_Est(Est.Changed); 
       else init_Est_Null(Est.Changed);        
  
       init_Additional();
       init_ksList();
    }
    
 
 
    private void init_Est_Null(Est enumEst){
        Text nullExtText = new Text();
            nullExtText.setId("nullExtText");                                   //CSS ID
        Button addFromModelButton = new Button();
        VBox vBox = new VBox(nullExtText,addFromModelButton);
            vBox.setAlignment(Pos.CENTER);
   
        switch(enumEst){
            case Base:
                nullExtText.setText("Для данного подрядчика отсутствует 'Изменённая Cмета'");
                addFromModelButton.setText("Добавить из SQL-Базы");
                ((ScrollPane)baseVBox.getChildren().get(0)).setContent(vBox);
                break;
            case Changed:
                nullExtText.setText("Для данного подрядчика отсутствует 'Изменённая Cмета'");
                addFromModelButton.setText("Добавить из 'Базовой Cметы'");
                ((ScrollPane) changedVBox.getChildren().get(0)).setContent(vBox);
                break;
            }
        
        addFromModelButton.setOnAction(event -> {
            if(!enumEst.getSiteSecondValue(SQL.Site.CONTRACTOR ).equals("-")
                    || !enumEst.getSiteSecondValue(SQL.Site.TYPE_HOME).equals("-")){
                new ItemEstDAO().insertEstNewTables(enumEst);
    //            init_Lists();
    //            if(enumEst == Est.Base)    init_EstBase();
    //            if(enumEst == Est.Changed) init_EstChaged();
                  enumEst.createTabMap();
                  init_Est(enumEst);

                  switch(enumEst){
                    case Base:
                        enumEst.getItem(SQL.Site.SMET_COST).setSecondValue(DecimalFormatter.stringToDouble(lableSumBase.getText()));
    //                    enumEst.getPrewiewItem(9).setSecondValue(lableSumBase.getText());
                        break;   
                    case Changed:
                        enumEst.getItem(SQL.Site.COST_HOUSE).setSecondValue(DecimalFormatter.stringToDouble(lableSumChanged.getText()));
                        break;
                  }
                  enumEst.updatePreviewTable();
                  rootController.getPreviewTable().refresh();
            }else{
                System.out.println("init_Est_Null - Button - addFromModelButton - if");
            }
        });
 
    }    

    private void init_Est(Est enumEst){

        TabModel tm = new TabModel(enumEst);
        enumEst.setTab(tm);
//        tm1 = new TabModel(enumEst.getTabMap());
      switch(enumEst){
        case Base:
            lableSumBase = tm.getSumLabel();
            lableSumBase.textProperty().addListener(change -> {
            
                enumEst.getItem(SQL.Site.SMET_COST).setSecondValue(lableSumBase.getText());
                enumEst.getItem(SQL.Site.SMET_COST).setSecondValue(tm.getSumLabelValue());
                rootController.getPreviewTable().refresh();
            });
            
            ((ScrollPane)baseVBox.getChildren().get(0)).setContent(tm.getBaseVBox());
            baseVBox.getChildren().add(lableSumBase);
            
            break;
        case Changed:
            lableSumChanged = tm.getSumLabel();
            lableSumChanged.textProperty().addListener(change -> {
            
                enumEst.getItem(SQL.Site.COST_HOUSE).setSecondValue(lableSumChanged.getText());
                enumEst.getItem(SQL.Site.COST_HOUSE).setSecondValue(tm.getSumLabelValue());
                rootController.getPreviewTable().refresh();
            });

            ((ScrollPane) changedVBox.getChildren().get(0)).setContent(tm.getBaseVBox());
            changedVBox.getChildren().add(lableSumChanged);
      }
        enumEst.setTabsEditable();
      
         
    }

    private void init_Additional(){
//        tableWrapperAdditional.setItems((ObservableList)Est.Base.getTabMap(). saveEst("ФУНДАМЕНТ"));
    }
    
    
    private void init_ksList(){
       
        ksMap = Est.KS.getTabMap();
        
        if(!ksMap.isEmpty())
             listKS.getItems().setAll(ksMap.keySet());
        else listKS.getItems().setAll("-КС отсутствуют-");
        
        //listener
        listKS.getSelectionModel().selectedItemProperty().addListener((Obs, oldValue, newValue) -> {
            Object KS_Number;
            if(newValue != null )
                 KS_Number = oldValue;
            else KS_Number = newValue;
            
            if(!"-КС отсутствуют-".equals(newValue) && newValue != null){
                //Undo Changes if changed items weren't SAVE
                if(tableKS.getMemento()!= null  
                   && !tableKS.getMemento().getSavedState().equals(tableKS.getItems())){ tableKS.undoChageItems(); }
                
                //Set tableItem and SAVE MEMENTO
                tableKS.setTableData((ObservableList) ksMap.get(newValue));
                ContextMenuOptional.setTableItemContextMenuListener(tableKS);
                
                //Set disable Context Menu after added Items
                ((ContextMenuOptional) tableKS.getContextMenu()).setDisable_SaveUndoPrint_groupe(true);
                
                //count values
                tableKS.getItems().stream().forEach(item ->{
                    ((TableItemKS)item).setRestOfValue(
                        (double)Est.Changed.findEqualsElevent(item).getValue() -
                        ksMap.values().stream()
                                  .flatMap(mapItem ->mapItem.stream())
                                  .filter(item::equalsSuperCalss)
                                  .mapToDouble(filtered -> filtered.getValue())
                                  .sum()
                    );
                });
               
                //Set date Lable of selected KS 
                ksDateLabel.setText(LocalDate.ofEpochDay(
                        ((TableItemKS)ksMap.get(newValue).get(0)).getKSDate()).toString());
                ksSumLabel.setVisible(true);
                ksDateLabel.setVisible(true);
            }
        });     
        
    }
/*!******************************************************************************************************************
*                                                                                                     Update
********************************************************************************************************************/  
    public void update_TapKS(){
        Est.KS.createTabMap();
        init_ksList();  
    }    

/*!******************************************************************************************************************
*                                                                                                     HANDLERS
********************************************************************************************************************/  
     
    @FXML //Add new KS
    private void handle_addKS(ActionEvent event) {
        if(Est.Changed.isExist()){
            StageCreator siteAddLayout = new StageCreator(PathStrings.Layout.ADD_KS, "Добавить KC").loadNewWindow();
            ksAddController = siteAddLayout.getController();
            ksAddController.setShowEstController(this);
            
            siteAddLayout.getStage().show();
        } else {
            System.out.println(">>> Измененая смета (Ch) НЕ существует !!! ");
        }
    }
    

    
 
    @FXML
    private void handle_FilterKS(ActionEvent event) {  
       
        ObservableMap<Integer, List<TableItemKS>> tempKsMap = FXCollections.observableHashMap() ;
            if (isInputValid()){
                int dateFrom  =(int) dateKSfrom.getValue().toEpochDay();
                int dateTo    =(int) dateKSto.getValue().toEpochDay();
                ksMap.entrySet().stream()
                     .filter(list -> list.getValue().get(0).getKSDate() >= dateFrom 
                                     && list.getValue().get(0).getKSDate() <= dateTo)
                     .forEachOrdered(entry -> tempKsMap.put(entry.getKey(), entry.getValue()));


                listKS.getItems().clear();
                listKS.getItems().setAll(tempKsMap.keySet());
                
     }/*else{
         System.out.println("Выберите даты");
     }*/
    }
    
    @FXML
    private void handle_AllKS(ActionEvent event) { 
                listKS.getItems().setAll(ksMap.keySet());

    }
    
    @FXML
    private void hanle_PrintKS(ActionEvent event) {
        File selectedFile = FileChooserFactory.Save.saveKS(listKS.getSelectionModel().getSelectedItem().toString());
        if(!listKS.getSelectionModel().isEmpty()
                && selectedFile != null
                ) {
            new PrintKS(tableKS.getItems(),selectedFile.toPath());



//            new PrintKS.Builder()
//                    .setObsKS(tableKS.getItems())
////                 .setObsPreTab(previewTableObs)
//                    .setKSnumber(listKS.getSelectionModel().getSelectedItem().toString())
//                    .setKSDate(ksDateLabel.getText())
//                    .build();
        }

    }
    
    @FXML //delete new KS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!NEED TO CHAGE
    private void handle_deleteKS(ActionEvent event) {
        if(!listKS.getSelectionModel().isEmpty() && listKS.getSelectionModel().getSelectedItem() != null){
            String selectedItemKS = listKS.getSelectionModel().getSelectedItem().toString();
            new ItemKSDAO().deleteKS(selectedItemKS);
            ksMap.remove(Integer.parseInt(selectedItemKS));
            listKS.getItems().clear();
            listKS.getItems().addAll(ksMap.keySet());
        }
    }

    
/*!******************************************************************************************************************
*                                                                                                     InputValidator
********************************************************************************************************************/      
         
    private boolean isInputValid() {
        String errorMessage = "";

            if (dateKSfrom.getValue() == null ){
                errorMessage += "'Дата поиска: с' ";
                dateKSfrom.setStyle("-fx-border-color: red;");
                
            } 
            if (dateKSto.getValue() == null){
                errorMessage += "'Дата поиска: по' ";
                
                dateKSto.setStyle("-fx-border-color: red;");
            }
            if(dateKSfrom.getValue() != null && dateKSto.getValue() != null 
                    && dateKSfrom.getValue().toEpochDay() > dateKSto.getValue().toEpochDay()){
                errorMessage += "'Неверный период' ";
                dateKSfrom.setStyle("-fx-border-color: red;");
                
            }
            

        if(errorMessage.length() >0){
            erroeLable.setText("Ошибки в полях: " +errorMessage);
            erroeLable.setVisible(true);   
        }

        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
                    visiblePause.setOnFinished(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            dateKSfrom.setStyle(null);
                            dateKSto.setStyle(null);
                            erroeLable.setVisible(false);
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
}
