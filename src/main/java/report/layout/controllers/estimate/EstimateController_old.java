package report.layout.controllers.estimate;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import report.entities.abstraction.dao.CommonDao;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KS_TIV;
import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.site.PreviewTIV;
import report.entities.items.site.SiteDao;
import report.layout.controllers.addKS.AddKSController;
import report.layout.controllers.root.RootLayoutController;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.converters.dateStringConverters.LocalDayStringConverter;
import report.models.view.wrappers.table.PriceSumTableWrapper;
import report.usage_strings.PathStrings;
import report.usage_strings.SQL;
import report.models.view.wrappers.table.TableWrapper;
import report.models.view.nodesFactories.FileChooserFactory;


import report.models.view.nodesHelpers.FxmlStage;

import report.models.printer.PrintKS;
import report.models.view.customNodes.TabModel;
import report.entities.items.KS.KsDao;
import report.models.view.customNodes.ContextMenuOptional;


public class EstimateController_old implements Initializable {

//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//                                                                                                  ENUM
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
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
        Base(1), Changed(2), Additional(3), KS, Common;

        //Constructors ---------------------------------------------------------------------
        Est() {
        }

        Est(int val) {
            taleType = val;
        }

        public void printALLSum() {
            this.tab.getSumLabelValue();
        }

        //Setter ---------------------------------------------------------------------------
        public void setSiteObs(ObservableList<PreviewTIV> siteObsList) {
            Common.previewTableObs = siteObsList;
            Base.previewTableObs = siteObsList;
            Changed.previewTableObs = siteObsList;
            KS.previewTableObs = siteObsList;

        }

        private void setTab(TabModel tab) {
            this.tab = tab;
        }

        //Getter ---------------------------------------------------------------------------
        public int getTableType() {
            return taleType;
        }

        public Map getTabMap() {
            return tabMap;
        }

        public <T> T getSiteSecondValue(String sqlField) {
            return (T) previewTableObs.stream()
                    .filter(o -> o.getSqlColumn().equals(sqlField))
                    .findFirst()
                    .get()
                    .getSecondValue();
        }

        public PreviewTIV getSiteItem(String sqlField) {
            return previewTableObs.stream()
                    .filter(o -> o.getSqlColumn().equals(sqlField))
                    .findFirst()
                    .get();
        }

        public ObservableList<PreviewTIV> getPreviewObservableList() {
            return previewTableObs;
        }

        public ObservableList getAllItemsList_Live() {
            Collection<List> tempCollection = this.getTabMap().values();
            return tempCollection.stream().flatMap(Collection::stream).collect(Collector.of(
                    FXCollections::observableArrayList,
                    ObservableList::add,
                    (l1, l2) -> {
                        l1.addAll(l2);
                        return l1;
                    })
            );
        }

        public ObservableList<? extends AbstractEstimateTVI> findItemsList_DL(AbstractEstimateTVI selectedAbstractEstimateTVI) {
            if (selectedAbstractEstimateTVI != null)
                return allItems.stream()
                        .filter((AbstractEstimateTVI i) -> i.equalsSuperClass(selectedAbstractEstimateTVI))
                        .sorted(Comparator.comparingLong(item -> item.getDateCreate().getTime()))
                        .collect(collectingAndThen(toList(), FXCollections::observableArrayList));
            else
                return FXCollections.observableArrayList();
        }

        public void updateList_DL(CommonDao dao) {
            allItems = (ObservableList<? extends AbstractEstimateTVI>) dao.getData();
        }

        //Update ---------------------------------------------------------------------------
        public void updatePreviewTable() {
            new SiteDao().dellAndInsert(previewTableObs);
        }

        public void updateTabData() {
            this.createTabMap();
            this.tab.updateTablesItems();
        }

        //Methots --------------------------------------------------------------------------
//        public boolean isExist(){return tabMap != null ?!(tabMap.isEmpty());}
        public boolean isExist() {
            return tabMap != null ? !(tabMap.isEmpty()) : false;
        }

        public void setTabsEditable() {
            if (Changed.tabMap.isEmpty())
                Base.tab.setEditable(true);
            else Base.tab.setEditable(false);
        }

        protected void createTabMap() {
            switch (this) {
                case Base:
                case Changed:
                    allItems = new EstimateDao(this).getData();
                    tabMap = allItems.stream()
                            .filter(item -> item.getDel() != 1)
                            .sorted(Comparator.comparing(AbstractEstimateTVI::getJM_name))
                            .collect(Collectors.groupingBy(AbstractEstimateTVI::getBuildingPart,
                                    Collector.of(
                                            () -> FXCollections.observableArrayList(EstimateTVI.extractor()),
                                            ObservableList::add,
                                            (l1, l2) -> {
                                                l1.addAll(l2);
                                                return l1;
                                            })
                            ));
                    break;
                case KS:
                    allItems = new KsDao(this).getData();
                    tabMap = allItems.stream()
                            .filter(item -> item.getDel() != 1)
                            .sorted(Comparator.comparing(AbstractEstimateTVI::getJM_name))
                            .collect(Collectors.groupingBy(item -> ((KS_TIV) item).getKSNumber(),
                                    Collector.of(
                                            () -> FXCollections.observableArrayList(KS_TIV.extractor()),
                                            ObservableList::add,
                                            (l1, l2) -> {
                                                l1.addAll(l2);
                                                return l1;
                                            })
                            ));

//                                .collect(Collectors.groupingBy((AbstractEstimateTVI item) -> ((KS_TIV)item).getKSNumber()));
                    break;
                case Additional:
                    allItems = Base.allItems.stream()
                            .filter(item -> !Changed.allItems.contains(item))
                            .collect(Collector.of(
                                    () -> FXCollections.observableArrayList(EstimateTVI.extractor()),
                                    ObservableList::add,
                                    (l1, l2) -> {
                                        l1.addAll(l2);
                                        return l1;
                                    })
                            );
                    tabMap = allItems.stream()
                            .filter(item -> item.getDel() != 1)
                            .collect(Collectors.groupingBy(item -> ((EstimateTVI) item).getBuildingPart(),
//                                        AbstractEstimateTVI::getBuildingPart,
                                    Collector.of(
                                            () -> FXCollections.observableArrayList(),
                                            ObservableList::add,
                                            (l1, l2) -> {
                                                l1.addAll(l2);
                                                return l1;
                                            })
                            ));
                    break;
            }

        }

        public AbstractEstimateTVI findEqualsElement(AbstractEstimateTVI inpAbstractEstimateTVI) {
            return (AbstractEstimateTVI) tabMap.values().stream()
                    .flatMap(mapItem -> ((List) mapItem).stream())
                    .filter(item -> ((AbstractEstimateTVI) item).equalsSuperClass(inpAbstractEstimateTVI))
                    .findFirst().orElse(null);
        }

        //enum VAR ---------------------------------------------------------------------------


        public CountAgentTVI getCountAgentTVI() {
            return countAgentTVI;
        }

        public void setCountAgentTVI(CountAgentTVI countAgentTVI) {
            this.countAgentTVI = countAgentTVI;
        }

        //TODO: сюда записываем CountAgentTVI  для последующего забра
        private CountAgentTVI countAgentTVI;
        private int taleType;
        private Map<Object, ObservableList<AbstractEstimateTVI>> tabMap;
        private TabModel tab;
        private ObservableList<? extends AbstractEstimateTVI> allItems;
        private ObservableList<PreviewTIV> previewTableObs;
    }
    /*!******************************************************************************************************************
     *                                                                                                       Fields
     ********************************************************************************************************************/


    private RootLayoutController rootController;
    private AddKSController ksAddController;

    //preview table
    private Map<Integer, List<KS_TIV>> ksMap;
    private Map<String, List<AbstractEstimateTVI>> mapBase, mapChange;

    @FXML
    private Label ksSumLabel,
            ksDateLabel, erroeLable;
    @FXML
    private VBox baseVBox, changedVBox;
    @FXML
    private ListView<Object> listKS;
    @FXML
    private DatePicker dateKSfrom, dateKSto;
//    @FXML
//    private GridPane gridPaneAdditional;
//    @FXML
//    private ScrollPane scrollPaneBase, scrollPaneChanged;
//    @FXML
//    private ComboBox comboAdditional;
    @FXML
    private Tab baseTab, changeTab, dopTab;
    @FXML
    private TableView tableKS, tableAdditional;

    private Label labelSumBase, labelSumChanged;
    private PriceSumTableWrapper<KS_TIV> tableKSWrapper;
    private TableWrapper tableAdditionalWrapper;


    /*!******************************************************************************************************************
     *                                                                                                     Getter/Setter
     ********************************************************************************************************************/

    public void setRootController(RootLayoutController rootController) {
        this.rootController = rootController;
    }

    public Tab getBaseTab() {
        return baseTab;
    }

    public Tab getChangeTab() {
        return changeTab;
    }

    /*!******************************************************************************************************************
     *                                                                                                               INIT
     ********************************************************************************************************************/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        scrollPaneBase.setPrefHeight(ScreenSize.height.getQuantity() - 200);
//        scrollPaneChanged.setPrefHeight(ScreenSize.height.getQuantity() - 200);

        dateKSfrom.setEditable(true);
//        dateKSfrom.setConverter(new EpochDatePickerConverter());
        dateKSfrom.setConverter(new LocalDayStringConverter());
        dateKSto.setEditable(true);
//        dateKSto.setConverter(new EpochDatePickerConverter());
        dateKSto.setConverter(new LocalDayStringConverter());

        //add Additional table
        tableAdditionalWrapper = EstimateControllerNodeUtils.decorAdditional(tableAdditional);
        init_EstLayoutTabs();
        //add KS table
        tableKSWrapper = EstimateControllerNodeUtils.decorKS(tableKS);
//        ksSumLabel.textProperty().bind(Bindings.convert(tableKSWrapper.getSumProperty()));
        ksSumLabel.textProperty().bindBidirectional(
                tableKSWrapper.getProperty(),
                new DoubleStringConverter().format()
        );


    }
    /*!******************************************************************************************************************
     *                                                                                                            Methods
     ********************************************************************************************************************/

    public void init_EstLayoutTabs(/*ObservableList<PreviewTableItem> obs?/*String SiteNumber, String contName*/) {
        //add site number and Contractor
        Est.Base.createTabMap();
        Est.Changed.createTabMap();
        Est.KS.createTabMap();
        Est.Additional.createTabMap();
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        tableAdditionalWrapper.tableView().setItems(Est.Additional.getAllItemsList_Live());


        if (Est.Base.isExist()) init_Est(Est.Base);
        else init_Est_Null(Est.Base);
        if (Est.Changed.isExist()) init_Est(Est.Changed);
        else init_Est_Null(Est.Changed);

        init_Additional();
        init_ksList();
    }


    private void init_Est_Null(Est enumEst) {
        Text nullExtText = new Text();
        nullExtText.setId("nullExtText");                                   //CSS ID
        Button addFromModelButton = new Button();
        VBox vBox = new VBox(nullExtText, addFromModelButton);
        vBox.setAlignment(Pos.CENTER);

        switch (enumEst) {
            case Base:
                nullExtText.setText("Для данного подрядчика отсутствует 'Базовая Cмета'");
                addFromModelButton.setText("Добавить из SQL-Базы");
                ((ScrollPane) baseVBox.getChildren().get(0)).setContent(vBox);
                break;
            case Changed:
                nullExtText.setText("Для данного подрядчика отсутствует 'Изменённая Cмета'");
                addFromModelButton.setText("Добавить из 'Базовой Cметы'");
                ((ScrollPane) changedVBox.getChildren().get(0)).setContent(vBox);
                break;
        }

        addFromModelButton.setOnAction(event -> {
            if (!enumEst.getSiteSecondValue(SQL.Site.CONTRACTOR).equals("-")
                    || !enumEst.getSiteSecondValue(SQL.Site.TYPE_HOME).equals("-")) {
                new EstimateDao().insertEstNewTables(enumEst);
                //            init_Lists();
                //            if(enumEst == Est.Base)    init_EstBase();
                //            if(enumEst == Est.Changed) init_EstChaged();
                enumEst.createTabMap();
                init_Est(enumEst);

                switch (enumEst) {
                    case Base:
                        enumEst.getSiteItem(SQL.Site.SMET_COST)
                                .setSecondValue(
                                        new DoubleStringConverter().fromString(labelSumBase.getText()));
                        //                    enumEst.getPrewiewItem(9).setSecondValue(labelSumBase.getText());
                        break;
                    case Changed:
                        enumEst.getSiteItem(SQL.Site.COST_HOUSE)
                                .setSecondValue(
                                        new DoubleStringConverter().fromString(labelSumChanged.getText()));
                        break;
                }
                enumEst.updatePreviewTable();
                rootController.getPreviewTable().refresh();
            } else {
                System.out.println("init_Est_Null - Button - addFromModelButton - if");
            }
        });

    }

    private void init_Est(Est enumEst) {

        TabModel tm = new TabModel(enumEst);
        enumEst.setTab(tm);
//        tm1 = new SumVboxModel(enumEst.getTabMap());
        switch (enumEst) {
            case Base:
                labelSumBase = tm.getSumLabel();
                labelSumBase.textProperty().addListener(change -> {

//                    enumEst.getSiteItem(SQL.Site.SMET_COST).setSecondValue(labelSumBase.getText());
                    enumEst.getSiteItem(SQL.Site.SMET_COST).setSecondValue(tm.getSumLabelValue());
                    rootController.getPreviewTable().refresh();
                });

                ((ScrollPane) baseVBox.getChildren().get(0)).setContent(tm.getBaseVBox());
                baseVBox.getChildren().add(labelSumBase);

                break;
            case Changed:
                labelSumChanged = tm.getSumLabel();
                labelSumChanged.textProperty().addListener(change -> {

//                    enumEst.getSiteItem(SQL.Site.COST_HOUSE).setSecondValue(labelSumChanged.getText());
                    enumEst.getSiteItem(SQL.Site.COST_HOUSE).setSecondValue(tm.getSumLabelValue());
                    rootController.getPreviewTable().refresh();
                });

                ((ScrollPane) changedVBox.getChildren().get(0)).setContent(tm.getBaseVBox());
                changedVBox.getChildren().add(labelSumChanged);
        }
        enumEst.setTabsEditable();


    }

    private void init_Additional() {
//        tableWrapperAdditional.setItems((ObservableList)Est.Base.getTabMap(). saveEst("ФУНДАМЕНТ"));
    }


    private void init_ksList() {

        ksMap = Est.KS.getTabMap();

        if (!ksMap.isEmpty())
            listKS.getItems().setAll(ksMap.keySet());
        else listKS.getItems().setAll("-КС отсутствуют-");

        //listener
        listKS.getSelectionModel().selectedItemProperty().addListener((Obs, oldValue, newValue) -> {
            Object KS_Number;
            if (newValue != null)
                KS_Number = oldValue;
            else KS_Number = newValue;

            if (!"-КС отсутствуют-".equals(newValue) && newValue != null) {
                //Undo Changes if changed items weren't SAVE
                if (tableKSWrapper.getMemento() != null
                        && !tableKSWrapper.getMemento().getSavedState().equals(tableKSWrapper.getItems())) {
                    tableKSWrapper.undoChangeItems();
                }

                //Set tableItem and SAVE MEMENTO
                tableKSWrapper.setTableData((ObservableList) ksMap.get(newValue));
                ContextMenuOptional.setTableItemContextMenuListener(tableKSWrapper);

                //Set disable Context Menu after added Items
                ((ContextMenuOptional) tableKSWrapper.getContextMenu()).setDisable_SaveUndoPrint_groupe(true);

                //count values
                tableKSWrapper.getItems().stream().forEach(item -> {

                    AbstractEstimateTVI equalsAbstractEstimateTVI = Est.Changed.findEqualsElement(item);
                    double sum = 0;
                    if (item != null && equalsAbstractEstimateTVI != null)
                        sum = equalsAbstractEstimateTVI.getQuantity() -
                                ksMap.values().stream()
                                        .flatMap(mapItem -> mapItem.stream())
                                        .filter(item::equalsSuperClass)
                                        .mapToDouble(filtered -> filtered.getQuantity())
                                        .sum();

                    item.setRestOfValue(sum);

//                                Est.Changed.findEqualsElement(item).getQuantity() -
//                                        ksMap.values().stream()
//                                                .flatMap(mapItem ->mapItem.stream())
//                                                .filter(item::equalsSuperClass)
//                                                .mapToDouble(filtered -> filtered.getQuantity())
//                                                .sum()
//                        );
                });

                //Set date Lable of selected KS
                ksDateLabel.setText(LocalDate.ofEpochDay(
                        ((KS_TIV) ksMap.get(newValue).get(0)).getKSDate()).toString());
                ksSumLabel.setVisible(true);
                ksDateLabel.setVisible(true);
            }
        });

    }

    /*!******************************************************************************************************************
     *                                                                                                     Update
     ********************************************************************************************************************/
    public void update_TapKS() {
        Est.KS.createTabMap();
        init_ksList();
    }

    /*!******************************************************************************************************************
     *                                                                                                     HANDLERS
     ********************************************************************************************************************/

    @FXML //Add new KS
    private void handle_addKS(ActionEvent event) {
        if (Est.Changed.isExist()) {
            FxmlStage siteAddLayout = new FxmlStage(PathStrings.Layout.ADD_KS, "Добавить KC").loadAndShowNewWindow();
            ksAddController = siteAddLayout.getController();
            ksAddController.setShowEstController(this);
//            siteAddLayout.getStage().show();
        } else {
            System.out.println(">>> Измененая смета (Ch) НЕ существует !!! ");
        }
    }


    @FXML
    private void handle_FilterKS(ActionEvent event) {

        ObservableMap<Integer, List<KS_TIV>> tempKsMap = FXCollections.observableHashMap();
        if (isInputValid()) {
            int dateFrom = (int) dateKSfrom.getValue().toEpochDay();
            int dateTo = (int) dateKSto.getValue().toEpochDay();
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
        File selectedFile = FileChooserFactory.saveKS(listKS.getSelectionModel().getSelectedItem().toString());
        ObservableList<KS_TIV> i = tableKSWrapper.getItems();
        if (!listKS.getSelectionModel().isEmpty()
                && selectedFile != null
                ) {
            new PrintKS(tableKSWrapper.getItems(),
//                    new ContractorDao().getOne(Est.KS.getSiteSecondValue(SQL.KS.CONTRACTOR)), //todo change !!!!
                    selectedFile.toPath()
            );
        }

    }

    @FXML //delete new KS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!NEED TO CHAGE
    private void handle_deleteKS(ActionEvent event) {
        if (!listKS.getSelectionModel().isEmpty() && listKS.getSelectionModel().getSelectedItem() != null) {
            String selectedItemKS = listKS.getSelectionModel().getSelectedItem().toString();
            new KsDao().deleteKS(selectedItemKS);
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

        if (dateKSfrom.getValue() == null) {
            errorMessage += "'Дата поиска: с' ";
            dateKSfrom.setStyle("-fx-border-color: red;");

        }
        if (dateKSto.getValue() == null) {
            errorMessage += "'Дата поиска: по' ";

            dateKSto.setStyle("-fx-border-color: red;");
        }
        if (dateKSfrom.getValue() != null && dateKSto.getValue() != null
                && dateKSfrom.getValue().toEpochDay() > dateKSto.getValue().toEpochDay()) {
            errorMessage += "'Неверный период' ";
            dateKSfrom.setStyle("-fx-border-color: red;");

        }


        if (errorMessage.length() > 0) {
            erroeLable.setText("Ошибки в полях: " + errorMessage);
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
