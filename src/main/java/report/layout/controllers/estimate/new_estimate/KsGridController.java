package report.layout.controllers.estimate.new_estimate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.KS.KsTIV;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.DocumentType;
import report.models.view.wrappers.table.PriceSumTableWrapper;
import report.spring.utils.FxTableUtils;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;

import static report.spring.utils.FxTableUtils.addColumn;

public class KsGridController implements Initializable {

    private DocumentType documentType;

    @Autowired
    private EstimateService estimateService;

    @FXML
    private Label
            ksSumLabel,
            ksDateLabel,
            erroeLable;

    @FXML
    private ListView<Integer> ksListView;

    @FXML
    private DatePicker dateKSfrom, dateKSto;

    @FXML
    private TableView<KsTIV> ksTableView;

    @FXML
    private MenuItem addKsButton, deleteKs, printKS;


    private Map<Integer, ObservableList<KsTIV>> ksMap;

    private PriceSumTableWrapper<KsTIV> tableKSWrapper;

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
        computeColumnValue();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtons();


    }

    private void initButtons() {
        addKsButton.setOnAction(e -> {
//            FxStageUtils.newStage(addKsTabControllerViewFx.getView());
        });
        deleteKs.setOnAction(e -> {
        });
        printKS.setOnAction(e -> {
        });
    }

    private void initListView(){
        ksListView.getSelectionModel().selectedItemProperty().addListener((Obs, oldValue, newValue) -> {
            Integer ksNumber = Objects.isNull(newValue) ? oldValue : newValue;

        });
    }

    public void computeColumnValue(){
        final double sum = ksTableView.getItems()
                .stream()
                .mapToDouble(AbstractEstimateTVI::getPriceSum)
                .sum();
        ksSumLabel.setText(Double.toString(sum));
    }

    private void initTableView(){

        TableColumn<KsTIV, String> JM_nameColumn = addColumn(ksTableView,"Наименование работ/затрат", "JM_name");
        TableColumn BJobColumnn = addColumn(ksTableView,"Связанная работа", "bindJob");
        TableColumn BPartColumns = addColumn(ksTableView,"Часть", "buildingPart");
        TableColumn<KsTIV, Double> valueColumn = addColumn(ksTableView,"Кол-во", "quantity");
        TableColumn unitColumn = addColumn(ksTableView,"Eд. изм.", "unit");
        TableColumn Price_oneColumn = addColumn(ksTableView,"Стоимость (за единицу)", "priceOne");
        TableColumn Price_sumColumn = addColumn(ksTableView,"Стоимость (общая)", "priceSum");
        TableColumn restPriceSumColumn = addColumn(ksTableView,"Остаток (общий)", "restOfValue");

//        JM_nameColumn.setCellFactory(param -> TableCellFactory.getOnMouseEnteredTableCell(EstimateController_old.Est.KS));//todo припелить
        valueColumn.setEditable(true);
        FxTableUtils.setTextFieldTableCell(new DoubleStringConverter(),valueColumn);

        valueColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<KsTIV, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<KsTIV, Double> t) {
                KsTIV editingItem = t.getRowValue();
                Double priceOne = editingItem.getPriceOne();
                Double valueInChanged = estimateService.findEqualsEstimateElement(documentType,editingItem).getQuantity();

                //rest of Value in KS Lists
                double restOfValue = valueInChanged -
                        (ksMap.values()
                                .stream()
                                .flatMap(mapItem -> ((List) mapItem).stream())
                                .filter(editingItem::businessKeyEquals)
                                .mapToDouble(filtered -> ((AbstractEstimateTVI) filtered).getQuantity())
                                .sum()
                                - t.getOldValue() + t.getNewValue());

                //set Value
                editingItem.setQuantity(t.getNewValue());

                //count new Price Sum
                editingItem.setPriceSum(t.getNewValue() * priceOne);

//                Set new Rest of Value
                editingItem.setRestOfValue(restOfValue);

                computeColumnValue();
                t.getTableView().refresh();
            }
        });

    }
}
