
package report.models.view.customNodes.newNode;

import com.google.common.util.concurrent.AtomicDouble;
import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import report.entities.items.estimate.EstimateTVI;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.wrappers.table.PriceSumTableWrapper;

import java.math.BigDecimal;
import java.util.List;

@Deprecated
public class SumVboxModel<T extends PriceSumTableWrapper<EstimateTVI> > extends VBox {

    private List<SumColumnTableTitledStackModel<T>> listStackModels;
    private ScrollPane scrollPane;
    private Label sumLabel;
    private DoubleBinding allSackSumProperty;

    {
        scrollPane = new ScrollPane();

        sumLabel = new Label();
        sumLabel.setPadding(new Insets(0, 0, 0, 0));
        sumLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        sumLabel.setTextFill(Color.MAROON);
        sumLabel.setMaxWidth(Double.MAX_VALUE);
        sumLabel.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

    }

    public SumVboxModel(List<SumColumnTableTitledStackModel<T>> listStackModels) {
        this.listStackModels = listStackModels;
        getChildren().addAll(listStackModels);
        getChildren().addAll(sumLabel);
        bindDoubleProperty();
    }

    public void setEditable(boolean param) {
        listStackModels.forEach(stackModel -> stackModel.tableView().setEditable(param));
    }

    public List<SumColumnTableTitledStackModel<T>> getListStackModels() {
        return listStackModels;
    }

    private void bindDoubleProperty() {
        allSackSumProperty = new DoubleBinding() {
            {
                super.bind(stackModelListToArray());
            }

            @Override
            protected double computeValue() {
                double sum = 0;
                for (SumColumnTableTitledStackModel table : listStackModels) {
                    sum += table.sumValueProperty().get();
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

    private Observable[] stackModelListToArray() {
        Observable[] observables = new Observable[listStackModels.size()];
        for (int i = 0; i < listStackModels.size(); i++) {
            observables[i] = listStackModels.get(i).sumValueProperty();
        }
        return observables;
    }

}
