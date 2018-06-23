
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
import report.models.view.wrappers.table.TableWrapper;

import java.util.Objects;

@Deprecated
public class SumColumnTableTitledStackModel<T extends TableWrapper & SummableColumnTable<DoubleProperty>> extends StackPane {

    private final Label sumLabel;
    private final TitledPane titledPane;
    private final DoubleProperty sumValue;
    private final T table;

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


    public SumColumnTableTitledStackModel(String title, T table) {
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
