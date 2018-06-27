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
import report.layout.controllers.estimate.new_estimate.service.SumPropertyContainer;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.BuildingPart;
import report.models.counterpaties.DocumentType;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.views.ViewFx;

import java.net.URL;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class SumLabelGridController extends AbstractInitializable {

    public final static String BASE_STACK_PANE = "baseStackPaneTableView";
    public final static String CHANGE_STACK_PANE = "changedStackPaneTableView";

    private DocumentType documentType;

    @Autowired
    private EstimateService estimateService;
    @Autowired
    private ApplicationContextProvider context;

    @FXML
    private VBox tableVbox;
    @FXML
    private Label sumLabel;

    private final Map<String, DoubleProperty> stackViewMap = new HashMap<>();
    private final Map<BuildingPart, ViewFx<StackPane, ? extends SumPropertyContainer<DoubleProperty>>> tables = new EnumMap<>(BuildingPart.class);
//    private final DoubleProperty sum = new SimpleDoubleProperty();

    public void putContent(BuildingPart key, ViewFx<StackPane, ? extends SumPropertyContainer<DoubleProperty>> content) {
        tables.put(key, content);
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public void initData() {
        final String beanName = choseBean(documentType);
        Arrays.asList(BuildingPart.values()).forEach(bp -> {
            final ViewFx<StackPane, ? extends SumPropertyContainer<DoubleProperty>> stackPane = context.getBean(beanName);
            final SumPropertyContainer<DoubleProperty> controller = stackPane.getController();
            if (estimateService.getEstimateData().isContains(documentType, bp.getValue())) {
                controller.initData(documentType, bp.getValue());
            }
            tableVbox.getChildren().add(stackPane.getView());
            stackViewMap.put(bp.getValue(), stackPane.getController().property());
        });
        bindProperties();
    }

    private void bindProperties() {
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

    private String choseBean(DocumentType type) {
        if (type.equals(DocumentType.BASE)) {
            return BASE_STACK_PANE;

        } else if (type.equals(DocumentType.CHANGED)) {
            return CHANGE_STACK_PANE;
        }
        throw new IllegalArgumentException();
    }
}
