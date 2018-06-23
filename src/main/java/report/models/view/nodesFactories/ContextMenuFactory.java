
package report.models.view.nodesFactories;

import javafx.scene.control.ContextMenu;
import report.entities.items.estimate.EstimateDao;
import report.layout.controllers.addEstimateRow.AddEstimateRowController;
import report.layout.controllers.estimate.EstimateController_old.Est;
import report.models.view.nodesHelpers.FxmlStage;
import report.models.view.wrappers.ContainMemento;
import report.usage_strings.PathStrings;
import report.models.view.customNodes.ContextMenuOptional;
import report.models.view.wrappers.table.TableWrapper;


public class ContextMenuFactory {

    /**
     * Create ContextMenu to "Estimation" TableViews (TableEst).
     *
     * @param tableWrapperView (TableWrapper extends TableView)
     * @return ContextMenu
     */
    public static ContextMenu getEst(TableWrapper tableWrapperView) {
        if (!Est.Changed.isExist()) {
            return ContextMenuOptional
                    .newBuilder()
                    .setTable(tableWrapperView)
                    .addSpecialMenuItem("Дабавить").addActionEventHandler(action -> {
                        FxmlStage addSiteRowLayout
                                = new FxmlStage(PathStrings.Layout.ADD_SITE_ROW, "Добавление строк")
                                .loadAndShowNewWindow();
                        AddEstimateRowController controllerAddRow = addSiteRowLayout.getController();
//                        controllerAddRow.setRootTableView(tableWrapperView);
                    })
                    .addRemoveMenuItem()
                    .addSeparator()
                    .addSaveMenuItem().addActionEventHandler(event -> Est.Common.updatePreviewTable())
                    .addUndoMenuItem()
                    .addSeparator()
                    .addPrintSmeta(tableWrapperView.getDAO())
                    .build();
        }
        return ContextMenuOptional
                .newBuilder()
                .setTable(tableWrapperView)
//                             .addEstAddMenuItem()
                .addSpecialMenuItem("Дабавить").addActionEventHandler(action -> {
                    FxmlStage addSiteRowLayout
                            = new FxmlStage(PathStrings.Layout.ADD_SITE_ROW, "Добавление строк")
                            .loadAndShowNewWindow();
                    AddEstimateRowController controllerAddRow = addSiteRowLayout.getController();
//                    controllerAddRow.setRootTableView(tableWrapperView);
                })
                .addSeparator()
                .addSaveMenuItem()
                .addUndoMenuItem()
                .addSeparator()
                .addPrintSmeta(tableWrapperView.getDAO())
                .build();

    }

    /**
     * Create ContextMenu to "Estimation" TableViews (TableEst).
     * <br><b>Contain ONLY Print MenuItem</b>
     *
     * @param enumEst (enumeration)
     * @return ContextMenu
     */
    public static ContextMenu getEstPrint(Est enumEst) {
        return ContextMenuOptional.newBuilder()
//                .setDAO(new EstimateDao(enumEst))
//                    .setEnum(enumEst)
                .addPrintSmeta(new EstimateDao(enumEst))
                .build();

    }

    /**
     * Create Common ContextMenu with
     * <b >DELETE, SAVE, UNDO </b> - options.
     *
     * @param tableWrapperView (TableWrapper extends TableView)
     * @return ContextMenu
     */
    public static ContextMenu getCommonDSU(TableWrapper tableWrapperView) {
        return ContextMenuOptional.newBuilder()
//                    .setDAO(commonDao)
                .setTable(tableWrapperView)
                .addRemoveMenuItem()
                .addSeparator()
                .addSaveMenuItem()
                .addUndoMenuItem()
                .build();
    }

    /**
     * Create Common ContextMenu with
     * <b > SAVE, UNDO </b> - options.
     *
     * @param tableWrapperView (TableWrapper extends TableView)
     * @return ContextMenu
     */
    public static ContextMenu getCommonSU(ContainMemento tableWrapperView) {
        return ContextMenuOptional.newBuilder()
                .setTable(tableWrapperView)
                .addSaveMenuItem()
                .addUndoMenuItem()
                .build();
    }


}
