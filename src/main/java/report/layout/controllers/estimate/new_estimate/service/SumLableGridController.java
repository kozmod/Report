package report.layout.controllers.estimate.new_estimate.service;

import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import report.models.converters.numberStringConverters.DoubleStringConverter;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SumLableGridController implements Initializable {
    @FXML
    private VBox tableVbox;
    @FXML
    private Label sumLabel;

    private final DoubleProperty sum = new SimpleDoubleProperty();

    private Map<String,ComtaineSumProperty<DoubleProperty>> stackViewMap;


    public SumLableGridController(Map<String, ComtaineSumProperty<DoubleProperty>> stackViewMap) {
        this.stackViewMap = stackViewMap;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void bindProperties(){
        DoubleProperty[] doublePropertyList = (DoubleProperty[]) stackViewMap.values()
                .stream()
                .map(ComtaineSumProperty::property)
                .toArray();

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
