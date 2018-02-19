package report.models.view.customNodes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import report.models.view.wrappers.BindBase;
import report.models.view.wrappers.Reverting;

public class ContextMenuBase<E extends BindBase & Reverting> extends ContextMenu{

    E bindBaseNode;

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
    /***************************************************************************
     *                                                                         *
     * Builder                                                                 *
     *                                                                         *
     **************************************************************************/
    public class Builder {
        private MenuItem CURRENT_MENU_ITEM;

        private Builder() {        }
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

        public Builder addUndoMenuItem() {
            undoMenuItem = new MenuItem("Отменить изменения");
            undoMenuItem.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                bindBaseNode.undoChangeItems();

                setDisable_SaveUndoPrint_groupe(true);
            });
            getItems().add(undoMenuItem);
            return this;
        }


        public Builder addSeparator() {
            getItems().add(new SeparatorMenuItem());
            return this;
        }

        public ContextMenuBase build() {
            setDisable_SaveUndoPrint_groupe(true);
            return ContextMenuBase.this;
        }
    }
}
