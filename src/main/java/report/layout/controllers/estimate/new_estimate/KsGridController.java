package report.layout.controllers.estimate.new_estimate;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsTIV;
import report.layout.controllers.addKS.AddKsController;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.DocumentType;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.spring.components.FxStageProvider;
import report.spring.spring.configuration.controls.cells.KsDelElementsTableCell;
import report.spring.spring.configuration.controls.contextmenu.CustomContextMenu;
import report.spring.spring.configuration.controls.models.TableMemento;
import report.spring.utils.CollectionsUtils;
import report.spring.utils.FxTableUtils;
import report.spring.views.ViewFx;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import static report.spring.utils.FxTableUtils.addColumn;

public class KsGridController implements Initializable {

//    @Autowired
//    private Logger logger;

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

    public void initData(DocumentType documentType) {
        this.documentType = documentType;
        ksMap = estimateService.getKsMap(documentType);
        Set<Integer> ksNumbers = ksMap.keySet();
        if (ksNumbers.isEmpty()) {
            ksListView.setPlaceholder(
                    new Text("KS - отсутствуют")
            );
        } else {
            ksListView.setItems(FXCollections.observableArrayList(ksNumbers));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initButtons();
        initListView();

    }

    private void initButtons() {
        addKsButton.setOnAction(e -> {
            addKsView.getController().initData(documentType);
            stageProvider.showStage(addKsView);
        });
        deleteKs.setOnAction(e -> {
        });
        printKS.setOnAction(e -> {
        });
    }

    private void initListView() {
        ksListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((Obs, oldValue, newValue) -> {
                    Integer ksNumber = Objects.isNull(newValue) ? oldValue : newValue;
                    initKsTableBlock(ksMap.get(ksNumber));

                });
    }

    private void initKsTableBlock(ObservableList<KsTIV> ksItems) {
        if (CollectionsUtils.notBlank(ksItems)) {
            ksTableView.setItems(ksItems);
            memento.initState(ksTableView.getItems());
            ksTableView.setContextMenu(contextMenu);

            ksDateLabel.setText(
                    dateFromEpoch(ksItems.get(0).getKSDate())
            );
            ksDateLabel.setVisible(true);
            ksSumLabel.setVisible(true);

            computeColumnValue();
        } else {
            ksTableView.getItems().clear();
            ksDateLabel.setVisible(false);
            ksSumLabel.setVisible(false);
        }
    }

    public void computeColumnValue() {
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
        TableColumn<KsTIV, Double> valueColumn = addColumn(ksTableView, "Кол-во", "quantity");
        TableColumn unitColumn = addColumn(ksTableView, "Eд. изм.", "unit");
        TableColumn Price_oneColumn = addColumn(ksTableView, "Стоимость (за единицу)", "priceOne");
        TableColumn Price_sumColumn = addColumn(ksTableView, "Стоимость (общая)", "priceSum");
        TableColumn restPriceSumColumn = addColumn(ksTableView, "Остаток (общий)", "restOfValue");

        jobMaterialNameColumn.setCellFactory(param ->
                context.getBean(KsDelElementsTableCell.class).setDocumentType(documentType)
        );
        valueColumn.setEditable(true);
        FxTableUtils.setTextFieldTableCell(new DoubleStringConverter(), valueColumn);

        valueColumn.setOnEditCommit(column -> {
            final KsTIV editingItem = column.getRowValue();
            final double priceOne = editingItem.getPriceOne();
            Optional<AbstractEstimateTVI> item = estimateService.findEqualsEstimateElement(documentType, editingItem);
            if (item.isPresent()) {
                final double valueInChanged = item.get().getQuantity();
                final double restOfValue = valueInChanged - (summariseQuantity(editingItem) - column.getOldValue() + column.getNewValue());
                editingItem.setQuantity(column.getNewValue());

                //count new Price Sum
                editingItem.setPriceSum(column.getNewValue() * priceOne);

//                Set new Rest of Value
                editingItem.setRestOfValue(restOfValue);

                computeColumnValue();
                column.getTableView().refresh();
            }
        });
    }

    private void initTableContextMenu() {
        MenuItem save = contextMenu.addMenuItem("Сохранить");
        MenuItem undo = contextMenu.addMenuItem("Отменить изменения");
        undo.setOnAction(event -> {
            System.out.println("Отменить изменения");
//            contextMenu.applyState(0);
        });

        contextMenu.addState(0, () -> {
            save.setDisable(true);
            undo.setDisable(true);
        });
        contextMenu.addState(1, () -> {
            save.setDisable(false);
            undo.setDisable(false);
        });

//        ksListView.contextMenuProperty().bind(
//                Bindings.when(ksListView.it)
//                        .then((ContextMenu) contextMenu)
//                        .otherwise((ContextMenu) null)
//        );
        ksTableView.getItems().addListener((ListChangeListener<KsTIV>) observable -> {
            if (observable.next()) {
                if (observable.wasUpdated() || observable.wasAdded() || observable.wasRemoved()) {
                    contextMenu.applyState(1);
                }
            }
        });
    }

    private double summariseQuantity(final KsTIV editingItem) {
        return ksMap.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(editingItem::businessKeyEquals)
                .mapToDouble(AbstractEstimateTVI::getQuantity)
                .sum();
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
