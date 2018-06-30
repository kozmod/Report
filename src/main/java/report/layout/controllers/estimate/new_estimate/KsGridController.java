package report.layout.controllers.estimate.new_estimate;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsTIV;
import report.layout.controllers.addKS.AddKsController;
import report.layout.controllers.estimate.new_estimate.abstraction.AbstractInitializable;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.DocumentType;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.spring.components.FxStageProvider;
import report.spring.spring.configuration.controls.cells.KsDelElementsTableCell;
import report.spring.spring.configuration.controls.contextmenu.CustomContextMenu;
import report.spring.spring.configuration.controls.models.TableMemento;
import report.spring.spring.event.EstimateEditEvent;
import report.spring.spring.event.EstimateEditEventListener;
import report.spring.utils.CollectionsUtils;
import report.spring.utils.FxTableUtils;
import report.spring.views.ViewFx;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import static report.spring.utils.FxTableUtils.addColumn;

public class KsGridController extends AbstractInitializable {

    private DocumentType documentType;
    private Map<Integer, ObservableList<KsTIV>> ksMap;

    @Autowired
    private EstimateService estimateService;
    @Autowired
    private ApplicationContextProvider context;
    @Autowired
    private ViewFx<GridPane, AddKsController> addKsView;
    @Autowired
    private FxStageProvider stageProvider;

    @Autowired
    private TableMemento<KsTIV> memento;
    @Autowired
    private CustomContextMenu contextMenu;

    @Autowired
    private EventBus eventBus;
    @Autowired
    private EstimateEditEventListener eventListener;


    @FXML
    private Label ksSumLabel, ksDateLabel, erroeLable;
    @FXML
    private ListView<Integer> ksListView;
    @FXML
    private DatePicker dateKSfrom, dateKSto;
    @FXML
    private TableView<KsTIV> ksTableView;
    @FXML
    private MenuItem addKsButton, deleteKs, printKS;
    @FXML
    private CheckBox editCheckBox;
    @FXML
    private Button filterByDateKsButton, allKsButton;

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }


    @Override
    public void initData() {
        ksMap = estimateService.getKsMap(documentType);
        Set<Integer> ksNumbers = ksMap.keySet();
        if (ksNumbers.isEmpty()) {
            ksListView.getItems().clear();
        } else {
            ksListView.setItems(FXCollections.observableArrayList(ksNumbers));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initButtons();
        initListView();

        initEditCheckbox();

    }

    @PostConstruct
    private void beanPostConstruct() {
        initEventBus();
        initTableContextMenu();
    }

    private void initButtons() {
        addKsButton.setOnAction(e -> {
            addKsView.getController().setDocumentType(documentType);
            addKsView.getController().setKsController(this);
            addKsView.getController().initData();
            stageProvider.showStage(addKsView);
        });
        deleteKs.setOnAction(e -> {
            if (ksListView.getSelectionModel().getSelectedItem() != null && !ksTableView.getItems().isEmpty()) {
                ObservableList<KsTIV> selectedIte = ksTableView.getItems();
                estimateService.insertKs(null,selectedIte);
                ksListView.getItems().remove(ksListView.getSelectionModel().getSelectedItem());
            }
        });
        printKS.setOnAction(e -> {
            //todo: add print ks
        });
    }

    private void initEventBus() {
        eventListener.setEventConsumer(event -> {
            if (event.isEditing() && !Objects.equals(event.getSource(), this)) {
//                stackPane.setDisable(true);
            } else {
//                stackPane.setDisable(false);
            }
        });

    }

    private void initEditCheckbox() {
        editCheckBox.selectedProperty().addListener(event -> {
            filterByDateKsButton.setDisable(editCheckBox.isSelected());
            allKsButton.setDisable(editCheckBox.isSelected());
            ksListView.setDisable(editCheckBox.isSelected());
        });
        editCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) -> eventBus.post(new EstimateEditEvent(this, newValue))
        );
        editCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                contextMenu.applyState(0);
            }
        });

    }

    private void initListView() {
        ksListView.setPlaceholder(
                new Text("KS - отсутствуют")
        );
        ksListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((Obs, oldValue, newValue) -> {
                    Integer ksNumber = Objects.isNull(newValue) ? oldValue : newValue;

                    ObservableList<KsTIV> ksItems = ksMap.get(ksNumber);
                    initTableViewBySelectedKsNumber(ksItems);
                    ksItems.forEach(item -> item.setRestOfValue(countQuantity(item)));
                    initLabelsBySelectedKsNumber(ksItems);


                });
    }

    private void initTableViewBySelectedKsNumber(ObservableList<KsTIV> ksItems) {
        if (CollectionsUtils.notBlank(ksItems)) {
            ksTableView.setItems(ksItems);
            memento.initState(ksTableView.getItems());
            ksTableView.getItems().addListener((ListChangeListener<KsTIV>) observable -> {
                if (observable.next()) {
                    if (observable.wasUpdated() || observable.wasAdded() || observable.wasRemoved()) {
                        contextMenu.applyState(1);
                    }
                }
            });
            computePriceSumValue();
        }
    }


    private void initLabelsBySelectedKsNumber(ObservableList<KsTIV> ksItems) {
        if (CollectionsUtils.notBlank(ksItems)) {
            ksDateLabel.setText(
                    dateFromEpoch(ksItems.get(0).getKSDate())
            );
            ksDateLabel.setVisible(true);
            ksSumLabel.setVisible(true);
        } else {
            ksTableView.getItems().clear();
            ksDateLabel.setVisible(false);
            ksSumLabel.setVisible(false);
        }
    }

    public void computePriceSumValue() {
        final double sum = ksTableView.getItems()
                .stream()
                .mapToDouble(AbstractEstimateTVI::getPriceSum)
                .sum();
        ksSumLabel.setText(Double.toString(sum));
    }

    private void initTableView() {

        TableColumn<KsTIV, String> jobMaterialNameColumn = addColumn(ksTableView, "Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn = addColumn(ksTableView, "Связанная работа", "bindJob");
        TableColumn BPartColumns = addColumn(ksTableView, "Часть", "buildingPart");
        TableColumn<KsTIV, Double> quantityColumn = addColumn(ksTableView, "Кол-во", "quantity");
        TableColumn unitColumn = addColumn(ksTableView, "Eд. изм.", "unit");
        TableColumn Price_oneColumn = addColumn(ksTableView, "Стоимость (за единицу)", "priceOne");
        TableColumn Price_sumColumn = addColumn(ksTableView, "Стоимость (общая)", "priceSum");
        TableColumn restOfValueColumn = addColumn(ksTableView, "Остаток (общий)", "restOfValue");

        jobMaterialNameColumn.setCellFactory(param ->
                context.getBean(KsDelElementsTableCell.class).setDocumentType(documentType)
        );
        quantityColumn.setEditable(true);
        FxTableUtils.setTextFieldTableCell(new DoubleStringConverter(), quantityColumn);

//        valueColumn.setOnEditCommit(column -> {
//            final KsTIV editingItem = column.getRowValue();
//            final double priceOne = editingItem.getPriceOne();
//            Optional<AbstractEstimateTVI> item = estimateService.findKsEqualsEstimateElement(documentType, editingItem);
//            if (item.isPresent()) {
//                final double valueInChanged = item.get().getQuantity();
//                final double restOfValue = valueInChanged - (summariseQuantity(editingItem) - column.getOldValue() + column.getNewValue());
//                editingItem.setQuantity(column.getNewValue());
//
//                //count new Price Sum
//                editingItem.setPriceSum(column.getNewValue() * priceOne);
//
////                Set new Rest of Value
//                editingItem.setRestOfValue(restOfValue);
//
//                computePriceSumValue();
//                column.getTableView().refresh();
//            }
//        });
        quantityColumn.setOnEditCommit(tableColumn -> {
            KsTIV editingAbstractEstimateTVI = tableColumn.getTableView()
                    .getItems()
                    .get(tableColumn.getTablePosition().getRow());


            Double price_one = editingAbstractEstimateTVI.getPriceOne();
            Double value = tableColumn.getNewValue();

            editingAbstractEstimateTVI.setQuantity(value);
            editingAbstractEstimateTVI.setPriceSum(value * price_one);
            editingAbstractEstimateTVI.setRestOfValue(countQuantity(editingAbstractEstimateTVI));

            this.computePriceSumValue();
            tableColumn.getTableView().refresh();
        });
        ksTableView.contextMenuProperty().bind(
                Bindings.when(editCheckBox.selectedProperty())
                        .then((ContextMenu) contextMenu)
                        .otherwise((ContextMenu) null)
        );
    }


    private double countQuantity(final KsTIV editingItem) {
        List<AbstractEstimateTVI> estimateEquivalentItem = estimateService.findEstimateEqualsEstimateElement(documentType, editingItem);
        List<AbstractEstimateTVI> ksEqualsEstimateItems = estimateService.findKsEqualsEstimateElement(documentType, editingItem);

        final double estimateSum = estimateEquivalentItem.stream()
                .mapToDouble(AbstractEstimateTVI::getQuantity)
                .sum();
        final double ksSum = ksEqualsEstimateItems.stream()
                .mapToDouble(AbstractEstimateTVI::getQuantity)
                .sum();
        return estimateSum - ksSum;
    }

    private void initTableContextMenu() {
        MenuItem save = contextMenu.addMenuItem("Сохранить");
        MenuItem undo = contextMenu.addMenuItem("Отменить изменения");
        save.setOnAction(event -> {
            System.out.println("Сохранить ");
            contextMenu.applyState(1);
        });
        undo.setOnAction(event -> {
            System.out.println("Отменить изменения");
            contextMenu.applyState(0);
        });

        contextMenu.addState(0, () -> {
            save.setDisable(true);
            undo.setDisable(true);
        });
        contextMenu.addState(1, () -> {
            save.setDisable(false);
            undo.setDisable(false);
        });
    }


    private String dateFromEpoch(long epoch) {
        return LocalDate.ofEpochDay(epoch).toString();
    }

//    private void comuputeRestIatems() {
//        ksListView.getSelectionModel()
//                .selectedItemProperty()
//                .addListener((Obs, oldValue, newValue) -> {
//                     //Set tableItem and SAVE MEMENTO
//                    memento.initState(ksTableView.getItems());
//                    ksListView.setContextMenu(contextMenu);
//
//
//                    //count values
//                    ksTableView.getItems().stream().forEach(item -> {
//
//                        estimateService.getKsData()
//                                .findEqualElement(documentType, item)
//                                .ifPresent(equivalentItem -> {
//                                    double sum = equivalentItem.getQuantity() -
//                                            ksMap.values().stream()
//                                                    .flatMap(mapItem -> mapItem.stream())
//                                                    .filter(item::businessKeyEquals)
//                                                    .mapToDouble(filtered -> filtered.getQuantity())
//                                                    .sum();
//                                    item.setRestOfValue(9999999);
//                                });
//                    });
//                });
//
//    }
}
