package report.layout.controllers.estimate.new_estimate;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.estimate.EstimateTVI;
import report.layout.controllers.estimate.new_estimate.abstraction.AbstractInitializable;
import report.layout.controllers.estimate.new_estimate.abstraction.EstimateStackPane;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.BuildingPart;
import report.models.counterpaties.DocumentType;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.spring.configuration.controls.cells.EstimateDelElementsTableCell;
import report.spring.spring.configuration.controls.cells.InKsColoredCell;
import report.spring.spring.configuration.controls.contextmenu.CustomContextMenu;
import report.spring.spring.event.EstimateEditEvent;
import report.spring.spring.event.EstimateEditEventListener;
import report.spring.utils.FxTableUtils;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static report.spring.utils.FxTableUtils.addColumn;

public class ChangedEstimateTableController extends AbstractInitializable implements EstimateStackPane {

    private DocumentType documentType = DocumentType.CHANGED;
    private BuildingPart buildingPart;

    @Autowired
    private EstimateService estimateService;
    @Autowired
    private ApplicationContextProvider context;
    @Autowired
    private CustomContextMenu contextMenu;


    @Autowired
    private EventBus eventBus;
    @Autowired
    private EstimateEditEventListener eventListener;

    @FXML
    private StackPane stackPane;
    @FXML
    private Label sumLabel;
    @FXML
    private TitledPane titledPane;
    @FXML
    private TableView<EstimateTVI> tableView;
    @FXML
    private CheckBox editCheckBox;

    private final DoubleProperty sumValue = new SimpleDoubleProperty();

    @Override
    public DoubleProperty property() {
        return sumValue;
    }

    @Override
    public void setBuildingPart(BuildingPart buildingPart) {
        this.buildingPart = buildingPart;
    }

    @Override
    public BuildingPart getBuildingPart() {
        return this.buildingPart;
    }

    @Override
    public void initData() {
        titledPane.setText(buildingPart.getValue());
        tableView.setItems(
                estimateService.getEstimateMap(documentType).get(buildingPart.getValue())
        );
        computeColumnValue();
        initTableContextMenu();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initEventBus();
        sumLabel.textProperty()
                .bindBidirectional(sumValue, new DoubleStringConverter().format());
    }

    private void initEventBus(){
        eventListener.setEventConsumer(event -> {
            if(event.isEditing() && !Objects.equals(event.getSource(),this)){
                stackPane.setDisable(true);
            }else{
                stackPane.setDisable(false);
            }
        });
        editCheckBox.selectedProperty().addListener(
                (observable, oldValue, newValue) -> eventBus.post(new EstimateEditEvent(this, newValue))
        );
    }

    private void computeColumnValue() {
        final double sum = tableView.getItems()
                .stream()
                .mapToDouble(EstimateTVI::getPriceSum)
                .sum();
        sumValue.setValue(sum);
    }

    private void initTableView() {
        tableView.editableProperty().bind(editCheckBox.selectedProperty());

        TableColumn<EstimateTVI, String> jobMaterialNameColumn = addColumn(tableView, "Наименование работ/затрат", column -> column.getValue().JM_nameProperty());
        TableColumn<EstimateTVI, String> bindJobColumn = addColumn(tableView, "Связанная работа", column -> column.getValue().bindJobProperty());
        TableColumn<EstimateTVI, Double> quantityColumn = addColumn(tableView, "Кол-во", column -> column.getValue().quantityProperty().asObject());
        TableColumn<EstimateTVI, String> unitColumn = addColumn(tableView, "Eд. изм.", column -> column.getValue().unitProperty());
        TableColumn<EstimateTVI, Double> periceOneColumn = addColumn(tableView, "Стоимость (за единицу)", column -> column.getValue().priceOneProperty().asObject());
        TableColumn<EstimateTVI, Double> priceOneSumColumn = addColumn(tableView, "Стоимость (общая)", column -> column.getValue().priceSumProperty().asObject());
        TableColumn<EstimateTVI, Boolean> isInKSColumn = addColumn(tableView, "КС", column -> column.getValue().inKSProperty());

        jobMaterialNameColumn.setMinWidth(300);
        bindJobColumn.setMinWidth(160);

        quantityColumn.setEditable(true);


        isInKSColumn.setCellFactory(param -> context.getBean(InKsColoredCell.class));
        jobMaterialNameColumn.setCellFactory(
                param -> context.getBean(EstimateDelElementsTableCell.class).setDocumentType(documentType)
        );

        periceOneColumn.setOnEditCommit(tableColumn -> {
            AbstractEstimateTVI editingAbstractEstimateTVI = tableColumn.getTableView()
                    .getItems()
                    .get(tableColumn.getTablePosition().getRow());
            Double price_one = tableColumn.getNewValue();
            Double value = editingAbstractEstimateTVI.getQuantity();

            editingAbstractEstimateTVI.setPriceOne(price_one);
            editingAbstractEstimateTVI.setPriceSum(value * price_one);

            this.computeColumnValue();
            tableColumn.getTableView().refresh();
        });

        FxTableUtils.setTextFieldTableCell(new DoubleStringConverter(), periceOneColumn);
    }

    private void initTableContextMenu() {
        MenuItem save = contextMenu.addMenuItem("Сохранить");
        MenuItem undo = contextMenu.addMenuItem("Отменить изменения");
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

        editCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                contextMenu.applyState(0);
            }
        });

        tableView.contextMenuProperty().bind(
                Bindings.when(editCheckBox.selectedProperty())
                        .then((ContextMenu) contextMenu)
                        .otherwise((ContextMenu) null)
        );
        tableView.getItems().addListener((ListChangeListener<EstimateTVI>) observable -> {
            if (observable.next()) {
                if (observable.wasUpdated() || observable.wasAdded() || observable.wasRemoved()) {
                    contextMenu.applyState(1);
                }
            }
        });
    }


}
