
package report.view_models.nodes_factories;

import javafx.scene.control.ContextMenu;
import report.controllers.addSiteRowLayoutController;
import report.controllers.showEstLayoutController.Est;
import report.servises.StageCreator;
import report.entities.items.estimate.ItemEstDAO;
import report.usege_strings.PathStrings;
import report.view_models.nodes.ContextMenuOptional;
import report.view_models.nodes.Table;


public class ContextMenuFactory {
    
    /**
     * Create ContextMenu to "Estimation" TableViews (TableEst). 
     *
     * @param tableView (Table extends TableView)
     * @return ContextMenu
     */
    public static ContextMenu getEst(Table tableView){
        if(!Est.Changed.isExist()){
            return ContextMenuOptional
                    .newBuilder()
                    .setTable(tableView)
                    .addSpecialMenuItem("Дабавить").addActionEventHandler(action -> {
                        StageCreator addSiteRowLayout
                                = new StageCreator(PathStrings.Layout.ADD_SITE_ROW, "Добавление строк")
                                .loadNewWindow();
                        addSiteRowLayoutController controllerAddRow = addSiteRowLayout.getController();
                        controllerAddRow.setRootTableView(tableView);
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
                .setTable(tableView)
//                             .addEstAddMenuItem()
                .addSpecialMenuItem("Дабавить").addActionEventHandler(action -> {
                    StageCreator addSiteRowLayout
                            = new StageCreator(PathStrings.Layout.ADD_SITE_ROW, "Добавление строк")
                            .loadNewWindow();
                    addSiteRowLayoutController controllerAddRow = addSiteRowLayout.getController();
                    controllerAddRow.setRootTableView(tableView);
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
                .setDAO(new ItemEstDAO(enumEst))
//                    .setEnum(enumEst)
                .addPrintSmeta()
                .build();

    }
    
    /**
     * Create Common ContextMenu with
     * <b >DELETE, SAVE, UNDO </b> - options.
     *
     * @param tableView (Table extends TableView)
     * @return ContextMenu
     */
    public static ContextMenu getCommonDSU(Table tableView){
        return ContextMenuOptional.newBuilder()
//                    .setDAO(dao)
                .setTable(tableView)
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
     * @param tableView (Table extends TableView)
     * @return ContextMenu
     */    
    public static ContextMenu getCommonSU(Table tableView){
        return ContextMenuOptional.newBuilder()
                .setTable(tableView)
                .addSaveMenuItem()
                .addUndoMenuItem()
                .build();
    }
    
    
    
    
    
    /**
     * Create ContextMenu to "KS" TableViews (TableKS). 
   
     * @param enumEst   (enumeration)
     * @param tableView (Table extends TableView)
     * @return ContextMenu
     */
//    public static ContextMenu geKS (Est enumEst, Table tableView){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(new ItemKSDAO(enumEst))
//                    .setTable(tableView)
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
    
    

    
    
//    /**
//     * Create ContextMenu to "OSR" TableViews. 
//   
//     * @param tableView (Table extends TableView)
//     * @return ContextMenu
//     */
//    public static ContextMenu getOSR (Table tableView){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(new ItemOSRDAO())
//                    .setTable(tableView)
//                               .addRemoveMenuItem()
//                               .addSeparator()
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
    
//    /**
//     * Create ContextMenu to "OSR" TableViews. 
//     
//     * @param tableView (Table extends TableView)
//     * @return ContextMenu
//     */
//    public static ContextMenu getVariable (Table tableView){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(new ItemVariableDAO())
//                    .setTable(tableView)
//                               .addRemoveMenuItem()
//                               .addSeparator()
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
//    /**
//     * Create ContextMenu to "OSR" TableContractor. 
//     *<br><b>Contain ONLY Print MenuItem</b>
//     * @param tableView (Table extends TableView)
//     * @return ContextMenu
//     */
//    public static ContextMenu getContractor(Table tableView){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(new ItemContractorDAO())
//                    .setTable(tableView)
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
     * @param tableView (Table extends TableView)
     * @param dao       (ItemDAO)
     * @return ContextMenu
     */
//    public static ContextMenu getSite(Table tableView, ItemDAO dao){
//        return ContextMenuOptional.newBuilder()
//                    .setDAO(dao)
//                    .setTable(tableView)
//                             .addSaveMenuItem()
//                             .addUndoMenuItem()
//                    .build();         
//    }
    
}
