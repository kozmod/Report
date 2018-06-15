package report.layout.controllers.estimate.new_estimate.service;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.estimate.EstimateTVI;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.mementos.TableMemento;
import report.models.view.nodesFactories.TableCellFactory;
import report.spring.utils.FxTableUtils;

import java.net.URL;
import java.util.ResourceBundle;

import static report.spring.utils.FxTableUtils.addColumn;

public class BaseStackTableController implements Initializable,ComtaineSumProperty<DoubleProperty> {

    @Autowired
    private EstimateService estimateService;

    @FXML
    private  Label sumLabel;
    @FXML
    private  TitledPane titledPane;
    @FXML
    private TableView<EstimateTVI> tableView;

    private TableMemento<EstimateTVI> memento;
    private final DoubleProperty sumValue = new SimpleDoubleProperty();

    public void initData(String title,ObservableList<EstimateTVI> tableItems){
        titledPane.setText(title);
        tableView.setItems(tableItems);
        memento = new TableMemento<>(tableItems);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
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
        TableColumn<EstimateTVI, Boolean> isInKSColumn = addColumn(tableView,"КС", "inKS");

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


}
