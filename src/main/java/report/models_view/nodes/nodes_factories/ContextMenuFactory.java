
package report.models_view.nodes.nodes_factories;

import javafx.scene.control.ContextMenu;
import report.entities.items.estimate.TableViewItemEstDAO;
import report.layoutControllers.addEstimateRow.AddEstimateRowController;
import report.layoutControllers.estimate.EstimateController.Est;
import report.models_view.nodes.node_helpers.StageCreator;
import report.usage_strings.PathStrings;
import report.models_view.nodes.ContextMenuOptional;
import report.models_view.nodes.node_wrappers.TableWrapper;


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
                        addSiteRowLayout.getStage().show();
                    })
                    .addRemoveMenuItem()
                    .addSeparator()
                    .addSaveMenuItem().addActionEventHandler(event -> Est.Common.updatePreviewTable())
                    .addUndoMenuItem()
                    .addSeparator()
                    .addPrintSmeta()
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
                    addSiteRowLayout.getStage().show();
                })
                .addSeparator()
                .addSaveMenuItem()
                .addUndoMenuItem()
                .addSeparator()
                .addPrintSmeta()
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
                .setDAO(new TableViewItemEstDAO(enumEst))
//                    .setEnum(enumEst)
                .addPrintSmeta()
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
    public static ContextMenu getCommonSU(TableWrapper tableWrapperView){
        return ContextMenuOptional.newBuilder()
                .setTable(tableWrapperView)
                .addSaveMenuItem()
                .addUndoMenuItem()
                .build();
    }
    
    
    
    
    
    /**
     * Create ContextMenu to "KS" TableViews (TableKS). 
   
     * @param enumEst   (enumeration)
     * @param treeTableView (TableWrapper extends TableView)
     * @return ContextMenu
     */
//    public static ContextMenu geKS (Est enumEst, TableWrapper treeTableView){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(new TableViewItemKSDAO(enumEst))
//                    .setTable(treeTableView)
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
    
    

    
    
//    /**
//     * Create ContextMenu to "OSR" TableViews. 
//   
//     * @param treeTableView (TableWrapper extends TableView)
//     * @return ContextMenu
//     */
//    public static ContextMenu getOSR (TableWrapper treeTableView){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(new TableViewItemOSRDAO())
//                    .setTable(treeTableView)
//                               .addRemoveMenuItem()
//                               .addSeparator()
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
    
//    /**
//     * Create ContextMenu to "OSR" TableViews. 
//     
//     * @param treeTableView (TableWrapper extends TableView)
//     * @return ContextMenu
//     */
//    public static ContextMenu getVariable (TableWrapper treeTableView){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(new ItemVariableDAO())
//                    .setTable(treeTableView)
//                               .addRemoveMenuItem()
//                               .addSeparator()
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
//    /**
//     * Create ContextMenu to "OSR" TableContractor. 
//     *<br><b>Contain ONLY Print MenuItem</b>
//     * @param treeTableView (TableWrapper extends TableView)
//     * @return ContextMenu
//     */
//    public static ContextMenu getContractor(TableWrapper treeTableView){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(new TableViewItemContractorDAO())
//                    .setTable(treeTableView)
//                               .addRemoveMenuItem()
//                               .addSeparator()
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
    
    

    
    
    
        /**
     * Create Common ContextMenu with 
     * <b >DELETE, SAVE, UNDO </b> - options.
     *
     * @param treeTableView (TableWrapper extends TableView)
     * @param commonDao       (TableViewItemDAO)
     * @return ContextMenu
     */
//    public static ContextMenu getSite(TableWrapper treeTableView, TableViewItemDAO commonDao){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(commonDao)
//                    .setTable(treeTableView)
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
    
}
