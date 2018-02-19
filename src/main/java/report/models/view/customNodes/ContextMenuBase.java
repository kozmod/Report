package report.models.view.customNodes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import report.models.view.wrappers.Reverting;


public class ContextMenuBase extends ContextMenu{

    Reverting bindBaseNode;

    MenuItem saveMenuItem;
    MenuItem undoMenuItem;

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    public void setDisable_SaveUndoPrint_groupe(final boolean value){
        if(saveMenuItem != null)saveMenuItem.setDisable(value);
        if(undoMenuItem != null)undoMenuItem.setDisable(value);
    }

    /**
     * Builder Factory Method.
     * @return
     */
    public static Builder newBuilder(){
        return new ContextMenuBase().new Builder();
    }
    /***************************************************************************
     *                                                                         *
     * Builder                                                                 *
     *                                                                         *
     **************************************************************************/
    public class Builder {
        private MenuItem CURRENT_MENU_ITEM;

        Builder() {        }
        /**
         * Add SPECIAL MenuItem
         *
         * @param text
         * @return Builder
         */
        public Builder addSpecialMenuItem(String text) {
            MenuItem specialMenuItem = new MenuItem(text);
            getItems().add(specialMenuItem);
            CURRENT_MENU_ITEM = specialMenuItem;
            return this;
        }

        /**
         * <b>"SetOnAction"</b> to last MenuItem (CURRENT_MENU_ITEM)
         *
         * @param eventHendler
         * @return Builder
         */
        public Builder setOnAction(EventHandler eventHendler) {
            CURRENT_MENU_ITEM.setOnAction(eventHendler);
            return this;
        }

        /**
         * Add <b>ActionEventHandler</b> to last MenuItem (CURRENT_MENU_ITEM)
         *
         * @param eventHendler
         * @return Builder
         */
        public Builder addActionEventHandler(EventHandler eventHendler) {
            CURRENT_MENU_ITEM.addEventHandler(ActionEvent.ACTION, eventHendler);
            return this;
        }

        /**
         * Set node  to action.
         * @param node Reverting
         * @return Builder
         */
        public Builder setNode(Reverting node) {
            ContextMenuBase.this.bindBaseNode = node;
            return this;
        }

        /**
         * add 'SAVE' menu item into base
         * @return Builder
         */
        public Builder addSaveMenuItem() {
            saveMenuItem = new MenuItem("Сохранить изменения");
            this.CURRENT_MENU_ITEM = saveMenuItem;
            saveMenuItem.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                System.out.println("saveMenuItem " + ContextMenuBase.class);
                bindBaseNode.toBase();
                bindBaseNode.saveTableItems();
                setDisable_SaveUndoPrint_groupe(true);
            });
            getItems().add(saveMenuItem);
            return this;
        }
        /**
         * add 'UNDO' menu item into base
         * @return Builder
         */
        public Builder addUndoMenuItem() {
            undoMenuItem = new MenuItem("Отменить изменения");
            undoMenuItem.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                bindBaseNode.undoChangeItems();

                setDisable_SaveUndoPrint_groupe(true);
            });
            getItems().add(undoMenuItem);
            return this;
        }

        /**
         * add 'Separator' menu item into base
         * @return Builder
         */
        public Builder addSeparator() {
            getItems().add(new SeparatorMenuItem());
            return this;
        }
        /**
         * Build context menu
         * @return Builder
         */
        public ContextMenuBase build() {
            setDisable_SaveUndoPrint_groupe(true);
            return ContextMenuBase.this;
        }
    }
}
