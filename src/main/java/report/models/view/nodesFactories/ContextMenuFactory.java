
package report.models.view.nodesFactories;

import javafx.scene.control.ContextMenu;
import report.entities.items.estimate.EstimateDAO;
import report.layoutControllers.addEstimateRow.AddEstimateRowController;
import report.layoutControllers.estimate.EstimateController.Est;
import report.models.view.nodesHelpers.StageCreator;
import report.models.view.wrappers.tableWrappers.AbstractTableWrapper;
import report.usage_strings.PathStrings;
import report.models.view.customNodes.ContextMenuOptional;
import report.models.view.wrappers.tableWrappers.TableWrapper;


public class ContextMenuFactory {
    
    /**
     * Create ContextMenu to "Estimation" TableViews (TableEst). 
     *
     * @param tableWrapperView (TableWrapper extends TableView)
     * @return ContextMenu
     */
    public static ContextMenu getEst(TableWrapper tableWrapperView){
        if(!Est.Changed.isExist()){
            return ContextMenuOptional
                    .newBuilder()
                    .setTable(tableWrapperView)
                    .addSpecialMenuItem("Дабавить").addActionEventHandler(action -> {
                        StageCreator addSiteRowLayout
                                = new StageCreator(PathStrings.Layout.ADD_SITE_ROW, "Добавление строк")
                                .loadNewWindow();
                        AddEstimateRowController controllerAddRow = addSiteRowLayout.getController();
                        controllerAddRow.setRootTableView(tableWrapperView);
//                        addSiteRowLayout.getStage().show();
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
                    StageCreator addSiteRowLayout
                            = new StageCreator(PathStrings.Layout.ADD_SITE_ROW, "Добавление строк")
                            .loadNewWindow();
                    AddEstimateRowController controllerAddRow = addSiteRowLayout.getController();
                    controllerAddRow.setRootTableView(tableWrapperView);
//                                                            controllerAddRow.setAditionalTableView(Est.Additional.getAllItemsList_Live());
//                    addSiteRowLayout.getStage().show();
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
     *<br><b>Contain ONLY Print MenuItem</b>
     * @param enumEst   (enumeration)
     * @return ContextMenu
     */
    public static ContextMenu getEstPrint(Est enumEst){
        return ContextMenuOptional.newBuilder()
//                .setDAO(new EstimateDAO(enumEst))
//                    .setEnum(enumEst)
                .addPrintSmeta(new EstimateDAO(enumEst))
                .build();

    }
    
    /**
     * Create Common ContextMenu with
     * <b >DELETE, SAVE, UNDO </b> - options.
     *
     * @param tableWrapperView (TableWrapper extends TableView)
     * @return ContextMenu
     */
    public static ContextMenu getCommonDSU(TableWrapper tableWrapperView){
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
    public static ContextMenu getCommonSU(AbstractTableWrapper tableWrapperView){
        return ContextMenuOptional.newBuilder()
                .setTable(tableWrapperView)
                .addSaveMenuItem()
                .addUndoMenuItem()
                .build();
    }
    
    
    
    
    

}
