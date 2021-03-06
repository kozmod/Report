package report.layout.controllers.intro;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.wrappers.table.TableWrapper;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;

abstract class IntroControllerNodeUtils implements TableFactory {


    /**
     * Decorate PreviewT TableView (RootLayoutController)
     *
     * @param table
     */
    static void decorPreview(TableView table) {

        TableWrapper tableWrapper = new TableWrapper(table, null);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setPrefHeight(450);

        TableColumn titleCol = tableWrapper.addColumn("Параметр", "firstValue");
        TableColumn valueCol = tableWrapper.addColumn("Значение", "secondValue");

        valueCol.setCellFactory(param -> TableCellFactory.getPreviewCell());

    }


    /**
     * Decorate Intro TableView (IntroLayoutController)
     *
     * @param table
     */
    static void decorIntroFinishedSite(TableView table) {

        TableWrapper tableWrapper = new TableWrapper(table, null);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));


        TableColumn siteNumberCol = tableWrapper.addColumn("Участок", "siteNumber");
        TableColumn typeHomeCol = tableWrapper.addColumn("ТипДома", "typeHome");
        TableColumn smetCostCol = tableWrapper.addColumn("Стоимоть", "smetCost");
        TableColumn saleCostCol = tableWrapper.addColumn("Цена", "saleCost");

//        smetCostCol.setCellFactory(param -> TableCellFactory.getDecimalCell());
//        saleCostCol.setCellFactory(param -> TableCellFactory.getDecimalCell());

        TableFactory.setCellFactory(
                new DoubleStringConverter(),
                smetCostCol,
                saleCostCol
        );
    }

}
