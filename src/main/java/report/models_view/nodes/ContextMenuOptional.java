
package report.models_view.nodes;


import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import report.entities.CommonDAO;
import report.entities.Reverse;
import report.entities.items.Clone;
import report.entities.items.DItem;
import report.models.printer.PrintEstimate;
import report.models_view.nodes.table_wrappers.AbstractTableWrapper;
import report.models_view.nodes.table_wrappers.DiscountTreeTableWrapper;
import report.models_view.nodes.table_wrappers.ReverseTableWrapper;
import report.models_view.nodes.table_wrappers.TableWrapper;


public class ContextMenuOptional extends ContextMenu{

    private AbstractTableWrapper<?> tableWrapper;
//    private CommonDAO commonDao;
    MenuItem saveMenuItem;
    MenuItem undoMenuItem;
    MenuItem printSmeta;
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    public void setDisable_SaveUndoPrint_groupe(final boolean value){
        if(saveMenuItem != null)saveMenuItem.setDisable(value);
        if(undoMenuItem != null)undoMenuItem.setDisable(value);
        if(printSmeta != null)  printSmeta.setDisable(!value);

    }
    /***************************************************************************
     *                                                                         *
     * Static Methods                                                            *
     *                                                                         *
     **************************************************************************/
    public static <S extends Clone> void setTableItemContextMenuListener(TableWrapper<S> tableWrapper){
        tableWrapper.getItems().addListener((ListChangeListener.Change<? extends S> c) -> {
            System.out.println("Changed on " + c + " - ContextMenuOptional");
            if(c.next() && (c.wasUpdated() || c.wasAdded() || c.wasRemoved())){
                ((ContextMenuOptional) tableWrapper.getContextMenu()).setDisable_SaveUndoPrint_groupe(false);
                tableWrapper.refresh();
            }
        });
    }
    public static  void setTableItemContextMenuListener(DiscountTreeTableWrapper tableWrapper){
        tableWrapper.tableView().getRoot().getValue().secondValueProperty().addListener((observable, oldValue, newValue) -> {
            ((ContextMenuOptional) tableWrapper.getContextMenu()).setDisable_SaveUndoPrint_groupe(false);
        });
    }
    public static <S extends Clone & Reverse> void setTableItemContextMenuListener(ReverseTableWrapper<S> tableWrapper, ObservableList<DItem> list){
        list.addListener((ListChangeListener.Change<? extends DItem> c) -> {
            ((ContextMenuOptional) tableWrapper.getContextMenu()).setDisable_SaveUndoPrint_groupe(false);
        });
    }

    /**
     * Builder Factory Method.
     * @return
     */
    public static Builder newBuilder(){
        return new ContextMenuOptional().new Builder();
    }

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    private ContextMenuOptional(){}

    /***************************************************************************
     *                                                                         *
     * Builder                                                                 *
     *                                                                         *
     **************************************************************************/
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


        public Builder setTable(AbstractTableWrapper tableWrapper) {
            ContextMenuOptional.this.tableWrapper = tableWrapper;
//            ContextMenuOptional.this.commonDao = tableWrapper.getDAO();

            return this;
        }

//        public Builder setDAO (TableViewItemDAO dao) {
//            ContextMenuOptional.this.commonDao = dao;
//            return this;
//        }


        @SuppressWarnings("unchecked")
        public Builder addRemoveMenuItem() {
            MenuItem removeMenuItem = new MenuItem("Удалить");
            this.CURRENT_MENU_ITEM = removeMenuItem;
            final TableWrapper wrapper = (TableWrapper)tableWrapper;
            removeMenuItem.addEventHandler(ActionEvent.ACTION, (ActionEvent event) ->
                    wrapper.tableView().getSelectionModel()
                            .getSelectedItems()
                            .forEach(toDelete -> wrapper.getItems().remove(toDelete))
            );
            removeMenuItem.disableProperty()
                    .bind(Bindings.isEmpty(wrapper.getSelectionModel().getSelectedItems())
                    );
            getItems().add(removeMenuItem);
            return this;
        }

        public Builder addSaveMenuItem() {
             saveMenuItem   = new MenuItem("Сохранить изменения");
            this.CURRENT_MENU_ITEM = saveMenuItem;
            saveMenuItem.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                System.out.println("saveMenuItem");
//                commonDao.dellAndInsert(tableWrapper.getMemento());
                tableWrapper.interactSQL();
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
            getItems().add(undoMenuItem);
            return this;
        }

        public Builder addPrintSmeta(CommonDAO dao) {
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
}
