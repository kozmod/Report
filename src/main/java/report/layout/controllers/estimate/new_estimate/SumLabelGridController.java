package report.layout.controllers.estimate.new_estimate;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.BuildingPart;
import report.models.counterpaties.DocumentType;
import report.spring.views.ViewFx;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static report.spring.spring.components.ApplicationContextProvider.getBean;

public class SumLabelGridController implements Initializable {

    private DocumentType documentType;

    @Autowired
    private EstimateService estimateService;

    @FXML
    private VBox tableVbox;
    @FXML
    private Label sumLabel;



    private final DoubleProperty sum = new SimpleDoubleProperty();

    private Map<String,DoubleProperty> stackViewMap = new HashMap<>();

    public void initData(final DocumentType type){
        this.documentType = type;
        Arrays.asList(BuildingPart.values()).forEach(bp ->{
            final ViewFx<StackPane,BaseEstimateTableController> stackPane = getBean("baseStackPaneTableView");
            final BaseEstimateTableController controller = stackPane.getController();
            if(estimateService.getEstimateData().isContains(documentType,bp.getValue())){
                controller.initData(documentType,bp.getValue());
            }
            tableVbox.getChildren().add(stackPane.getView());
            stackViewMap.put(bp.getValue(),stackPane.getController().property());
        });
        bindProperties();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // not need
    }

    private void bindProperties(){
        final DoubleProperty[] doublePropertyList = stackViewMap.values().toArray(new DoubleProperty[0]);
        final DoubleBinding allSackSumProperty = new DoubleBinding() {
            {
                super.bind(doublePropertyList);
            }

            @Override
            protected double computeValue() {
                double sum = 0;
                for (DoubleProperty doubleProperty : doublePropertyList) {
                    sum += doubleProperty.get();
                }
                return sum;
            }
        };

        sumLabel.textProperty().bind(new StringBinding() {
            {
                super.bind(allSackSumProperty);
            }

            @Override
            protected String computeValue() {
                return new DoubleStringConverter().toString(allSackSumProperty.get());
            }
        });
    }
}
