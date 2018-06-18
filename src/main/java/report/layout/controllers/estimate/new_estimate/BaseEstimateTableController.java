package report.layout.controllers.estimate.new_estimate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.estimate.EstimateTVI;
import report.layout.controllers.estimate.new_estimate.service.ComtaineSumProperty;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.DocumentType;
import report.models.mementos.TableMemento;

import report.models.view.customNodes.newNode.ContextMenuController;
import report.models.view.nodesFactories.TableCellFactory;
import report.spring.spring.components.ApplicationContextService;
import report.spring.utils.FxTableUtils;
import report.spring.views.ControlFx;

import java.net.URL;
import java.util.ResourceBundle;

import static report.spring.utils.FxTableUtils.addColumn;

public class BaseEstimateTableController implements Initializable,ComtaineSumProperty<DoubleProperty> {

    private DocumentType documentType;

    @Autowired
    private EstimateService estimateService;
//    @Autowired
//    private ApplicationContextService context;
    @Autowired
    private ControlFx<ContextMenu,ContextMenuController> contextMenu;


    @FXML
    private  Label sumLabel;
    @FXML
    private  TitledPane titledPane;
    @FXML
    private TableView<EstimateTVI> tableView;

    private TableMemento<EstimateTVI> memento;
    private final DoubleProperty sumValue = new SimpleDoubleProperty();


    public void initData(DocumentType documentType, String title){
        titledPane.setText(title);
        this.documentType = documentType;
        tableView.setItems(
                estimateService.getEstimateMap(documentType).get(title)
        );
        computeColumnValue();
        initTableContextMenu();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        sumLabel.textProperty()
                .bindBidirectional(sumValue, new DoubleStringConverter().format());
    }

    @Override
    public DoubleProperty property() {
        return sumValue;
    }

    public void computeColumnValue(){
        final double sum = tableView.getItems()
                .stream()
                .mapToDouble(EstimateTVI::getPriceSum)
                .sum();
        sumValue.setValue(sum);
    }

    private void initTableView(){
        TableColumn<EstimateTVI, String> jobMaterialNameColumn = addColumn(tableView, "Наименование работ/затрат", column -> column.getValue().JM_nameProperty());
        TableColumn<EstimateTVI, String> bindJobColumn = addColumn(tableView,"Связанная работа", column -> column.getValue().bindJobProperty());
        TableColumn<EstimateTVI, Double> quantityColumn = addColumn(tableView,"Кол-во", column -> column.getValue().quantityProperty().asObject());
        TableColumn<EstimateTVI, String> unitColumn = addColumn(tableView,"Eд. изм.", column -> column.getValue().unitProperty());
        TableColumn<EstimateTVI, Double> periceOneColumn = addColumn(tableView,"Стоимость (за единицу)", column -> column.getValue().priceOneProperty().asObject());
        TableColumn<EstimateTVI, Double> priceOneSumColumn = addColumn(tableView,"Стоимость (общая)", column -> column.getValue().priceSumProperty().asObject());
        TableColumn<EstimateTVI, Boolean> isInKSColumn = addColumn(tableView,"КС", column -> column.getValue().inKSProperty());

        jobMaterialNameColumn.setMinWidth(300);
        bindJobColumn.setMinWidth(160);

        quantityColumn.setEditable(true);
        periceOneColumn.setEditable(true);


        isInKSColumn.setCellFactory(param -> TableCellFactory.getInKsColoredCell());

        quantityColumn.setOnEditCommit(tableColumn -> {

            AbstractEstimateTVI editingAbstractEstimateTVI = tableColumn.getTableView()
                    .getItems()
                    .get(tableColumn.getTablePosition().getRow());

            Double price_one = editingAbstractEstimateTVI.getPriceOne();
            Double value = tableColumn.getNewValue();

            editingAbstractEstimateTVI.setQuantity(value);
            editingAbstractEstimateTVI.setPriceSum(value * price_one);

            this.computeColumnValue();
            tableColumn.getTableView().refresh();
        });

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

        FxTableUtils.setTextFieldTableCell(new DoubleStringConverter(),quantityColumn);
        FxTableUtils.setTextFieldTableCell(new DoubleStringConverter(),periceOneColumn);
        FxTableUtils.setTextFieldTableCell(new DoubleStringConverter(),priceOneSumColumn);

    }

    private void initTableContextMenu(){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem add = new MenuItem("Добавить строку");
        add.setOnAction(event -> {
//            ViewFx<GridPane,AddEstimateRowController> addEstimateView = context.getBean("addEstimateRowView");
//            FxStageUtils.newStage(addEstimateView.getView()).show();

        });

        MenuItem delete = new MenuItem("Удалить строку");
        delete.setOnAction(event -> {
            System.out.println("Удалить");
        });
        MenuItem save = new MenuItem("Сохранить");
        save.setOnAction(event -> {
            System.out.println("Сохранить");
        });
        save.setDisable(true);

        MenuItem undo = new MenuItem("Отменить изменения");
        undo.setOnAction(event -> {
            System.out.println("Отменить изменения");
            save.setDisable(true);
            undo.setDisable(true);
        });
        undo.setDisable(true);

        contextMenu.getItems().addAll(
                add,
                delete,
                new SeparatorMenuItem(),
                save,
                undo
        );

        tableView.setContextMenu(contextMenu);
        tableView.getItems().addListener((ListChangeListener<EstimateTVI>) observable -> {
            if (observable.next()) {
                if (observable.wasUpdated() || observable.wasAdded() || observable.wasRemoved()) {
                    save.setDisable(false);
                    undo.setDisable(false);
                }
            }
        });

    }


}
