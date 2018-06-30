package report.layout.controllers.estimate.new_estimate;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import report.layout.controllers.estimate.new_estimate.abstraction.AbstractInitializable;
import report.layout.controllers.estimate.new_estimate.service.EstimateService;
import report.layout.controllers.estimate.new_estimate.abstraction.EstimateStackPane;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.counterpaties.DocumentType;
import report.spring.spring.components.ApplicationContextProvider;
import report.spring.views.ViewFx;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


public class SumLabelGridController extends AbstractInitializable {

    private DocumentType documentType;

    @Autowired
    private EstimateService estimateService;
    @Autowired
    private ApplicationContextProvider context;

    @FXML
    private VBox tableVbox;
    @FXML
    private Label sumLabel;

    private final List<ViewFx<StackPane, ? extends EstimateStackPane>> stackPanes = new LinkedList<>();
//    private final DoubleProperty sum = new SimpleDoubleProperty();


    @SafeVarargs
    public final void addContent(ViewFx<StackPane, ? extends EstimateStackPane>... stackPaneArray) {
        stackPanes.addAll(Arrays.asList(stackPaneArray));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (ViewFx<StackPane, ? extends EstimateStackPane> stackPane : stackPanes){
            tableVbox.getChildren().add(stackPane.getView());
        }
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public void initData() {
        stackPanes.forEach(stackPane -> {
            final EstimateStackPane controller = stackPane.getController();
            if (estimateService.getEstimateData().isContains(documentType, controller.getBuildingPart().getValue())) {
                ((AbstractInitializable) controller).initData();
            }
        });
        bindProperties();
    }

    private void bindProperties() {
        final DoubleProperty[] doublePropertyList = stackPanes.stream()
                .map(stackPane -> stackPane.getController().property())
                .toArray(DoubleProperty[]::new);
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
