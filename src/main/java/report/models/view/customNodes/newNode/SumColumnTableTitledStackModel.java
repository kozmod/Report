
package report.models.view.customNodes.newNode;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.wrappers.table.SummableColumnTable;

import java.util.Objects;


public class SumColumnTableTitledStackModel extends StackPane {

    private final Label sumLabel;
    private final TitledPane titledPane;
    private final DoubleProperty sumValue;
    private final SummableColumnTable<DoubleProperty> table;

    {
        sumLabel = new Label();
        titledPane = new TitledPane();

        titledPane.setExpanded(false);
        titledPane.setAnimated(false);
        getChildren().addAll(titledPane, sumLabel);
        setMargin(sumLabel,
                new Insets(5, 10, 0, 0)
        );
        setAlignment(Pos.TOP_RIGHT);
    }


    public SumColumnTableTitledStackModel(String title, SummableColumnTable<DoubleProperty> table) {
        titledPane.setText(title);
        this.sumValue = table.getProperty();
        this.table = table;
        titledPane.setContent(table.tableView());
        bingLabelProperty();
    }


    public DoubleProperty sumValueProperty() {
        return sumValue;
    }

    public TableView tableView() {
        return table.tableView();
    }

    public void computeSum(){
        if(Objects.nonNull(sumValue)){
            table.computeProperty();
        }else{
            throw new IllegalStateException("sumValue is NULL");
        }
    }

    private void bingLabelProperty() {
        sumLabel.textProperty().setValue(sumValue.getValue().toString());
        Bindings.bindBidirectional(sumLabel.textProperty(), sumValue, new DoubleStringConverter().format());
    }
}
