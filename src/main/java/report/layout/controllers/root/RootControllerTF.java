package report.layout.controllers.root;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;

class RootControllerTF implements TableFactory {

    private RootControllerTF() {
    }

    /**
     * Create TableWrapper(Preview TableWrapper) with 2 columns /Object/.
     *
     * @return TableWrapper(Preview TableWrapper)
     */
    static TableView getSite() {
        TableView table = new TableView();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));
        table.setPrefHeight(450);

        TableColumn titleCol = new TableColumn("Параметр");
        titleCol.setCellValueFactory(new PropertyValueFactory("firstValue"));

        TableColumn valueCol = new TableColumn("Значение");
        valueCol.setCellValueFactory(new PropertyValueFactory("secondValue"));
        valueCol.setCellFactory(param -> TableCellFactory.getPreviewCell());


        table.getColumns().addAll(titleCol, valueCol);

        return table;
    }


}
