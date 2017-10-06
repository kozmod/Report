
package report.models_view.nodes;


import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import report.models.printer.PrintEstimate;
import report.entities.ItemDAO;


public class ContextMenuOptional extends ContextMenu{


//    private Est enumEst;
    private TableWrapper<?> tableWrapper;
    private ItemDAO dao;
    
    private MenuItem addMenuItem;      
    private MenuItem removeMenuItem;
    private MenuItem saveMenuItem; 
    private MenuItem undoMenuItem;
    private MenuItem printSmeta;
    
    public void setDisable_SaveUndoPrint_groupe(boolean value){
        if(saveMenuItem != null)saveMenuItem.setDisable(value);
        if(undoMenuItem != null)undoMenuItem.setDisable(value);
        if(printSmeta != null)  printSmeta.setDisable(!value);   
    }
    
    public static <S> void setTableItemContextMenuListener(TableWrapper<S> tableWrapper){
        tableWrapper.getItems().addListener((ListChangeListener.Change<? extends S> c) -> {
                System.out.println("Changed on " + c + " - ContextMenuOptional");
                if(c.next() && (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                                ((ContextMenuOptional) tableWrapper.getContextMenu()).setDisable_SaveUndoPrint_groupe(false);
                                
                                tableWrapper.refresh();
                }
        }); 
    }
    
//Constructor =========================================================================
    
    private ContextMenuOptional(){}
    
//Builder =============================================================================
    public static Builder newBuilder(){
       return new ContextMenuOptional().new Builder();
    }
    
    public class Builder{
        
        private MenuItem CURRENT_MENU_ITEM;
        
        private Builder(){}
        
        /**
         * Add SPECIAL MenuItem
         * @param text
         * @return Builder
         */
            public Builder addSpecialMenuItem(String text){
                MenuItem specialMenuItem    = new MenuItem(text);
                getItems().add(specialMenuItem);
                CURRENT_MENU_ITEM = specialMenuItem;
                return this;
            }
        /**
         * <b>"SetOnAction"</b> to last MenuItem (CURRENT_MENU_ITEM)
         * @param eventHendler
         * @return Builder
         */
            public Builder setOnAction(EventHandler eventHendler) {
                CURRENT_MENU_ITEM.setOnAction(eventHendler);
                return this;
            }
            
        /**
         * Add <b>ActionEventHandler</b> to last MenuItem (CURRENT_MENU_ITEM)
         * @param eventHendler
         * @return Builder
         */
            public Builder addActionEventHandler(EventHandler eventHendler) {
                CURRENT_MENU_ITEM.addEventHandler(ActionEvent.ACTION,eventHendler);
                return this;
            }
           
        
        public Builder setTable(TableWrapper tableWrapper) {
            ContextMenuOptional.this.tableWrapper = tableWrapper;
            ContextMenuOptional.this.dao = tableWrapper.getDAO();
            
        return this;
        }
        
        public Builder setDAO (ItemDAO dao) {
            ContextMenuOptional.this.dao = dao;
        return this;
        }
        
        
//        public Builder addEstAddMenuItem() {
//            addMenuItem    = new MenuItem("Добавить");
//            this.CURRENT_MENU_ITEM = addMenuItem;
//            
//            addMenuItem.setOnAction(event -> {
//                StageCreator addSiteRowLayout
//                    = new StageCreator("view/AddEstimateRowLayout.fxml", "Добавление строк");
//                AddEstimateRowController controllerAddRow = addSiteRowLayout.getController();
//                controllerAddRow.setRootTableView(tableWrapper);
//                addSiteRowLayout.getStage().show();
//            });
//            getItems().add(addMenuItem);
//        return this;
//        }
        
        public Builder addRemoveMenuItem() {
            removeMenuItem = new MenuItem("Удалить");
            this.CURRENT_MENU_ITEM = removeMenuItem;
            
//            removeMenuItem.setOnAction(event -> {
//                tableWrapper.getSelectionModel().getSelectedItems().forEach(toDelete -> {tableWrapper.getItems().remove(toDelete);});
//            });
            removeMenuItem.addEventHandler(ActionEvent.ACTION, (ActionEvent event) ->
                tableWrapper.getSelectionModel()
                        .getSelectedItems()
                        .forEach(toDelete -> tableWrapper.getItems().remove(toDelete))
            );
            removeMenuItem.disableProperty()
                .bind(Bindings
                        .isEmpty(ContextMenuOptional.this.tableWrapper.getSelectionModel().getSelectedItems()));
            getItems().add(removeMenuItem);
        return this;
        }
        
        public Builder addSaveMenuItem() {
            saveMenuItem   = new MenuItem("Сохранить изменения");
//            saveMenuItem   = new MenuItem("Сохранить изменения в SQL");
            this.CURRENT_MENU_ITEM = saveMenuItem;
//            saveMenuItem.setOnAction(event -> {
//
//                dao.dellAndInsert(tableWrapper);
//
//                tableWrapper.saveTableItems();
//                setDisable_SaveUndoPrint_groupe(true);
//                tableWrapper.refresh();
//            });
            saveMenuItem.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                System.out.println("saveMenuItem");
                dao.dellAndInsert(tableWrapper);

                tableWrapper.saveTableItems();
                setDisable_SaveUndoPrint_groupe(true);
                tableWrapper.refresh();

            });
            
            getItems().add(saveMenuItem);
        return this;
        }
        
        public Builder addUndoMenuItem() {
            undoMenuItem   = new MenuItem("Отменить изменения");

            undoMenuItem.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                tableWrapper.undoChangeItems();

                setDisable_SaveUndoPrint_groupe(true);
            });

//            undoMenuItem.setOnAction(event -> {
//                tableWrapper.undoChangeItems();
//
//                setDisable_SaveUndoPrint_groupe(true);
//            });
            getItems().add(undoMenuItem);
        return this;
        }
        
        public Builder addPrintSmeta() {
            printSmeta     = new MenuItem("Выгрузить смету");
            printSmeta.setOnAction(event -> {
               new PrintEstimate(dao);
               System.out.println("printSmeta");
            });
            getItems().add(printSmeta);
        return this;
        }
        
        public Builder addSeparator() {
            getItems().add(new SeparatorMenuItem());
        return this;
        }
        
        public ContextMenuOptional build(){
            setDisable_SaveUndoPrint_groupe(true);
//            setAction();
            return ContextMenuOptional.this;
        }
    
    }
    
    
// Getters /  ============================================================================

    public MenuItem getSaveMenuItem() {return saveMenuItem;}

    public void setDao(ItemDAO dao)   { this.dao = dao; }





}
