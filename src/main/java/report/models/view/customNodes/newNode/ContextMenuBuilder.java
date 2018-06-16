package report.models.view.customNodes.newNode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import report.models.view.customNodes.ContextMenuOptional;

public class ContextMenuBuilder extends ContextMenu {

    private ContextMenuBuilder(){
    }

    public static Builder newBuilder(){
        return new Builder();
    }
    public static class Builder {
        private final  ContextMenu contextMenu;
        private MenuItem CURRENT_MENU_ITEM;

        private Builder(){
            contextMenu = new ContextMenu();
        }

        public Builder addMenuItem(String text) {
            MenuItem specialMenuItem = new MenuItem(text);
            contextMenu. getItems().add(specialMenuItem);
            CURRENT_MENU_ITEM = specialMenuItem;
            return this;
        }

        public Builder addSeparator() {
            contextMenu.getItems().add(new SeparatorMenuItem());
            return this;
        }


        public Builder setOnAction(EventHandler<ActionEvent> eventHandler) {
            CURRENT_MENU_ITEM.setOnAction(eventHandler);
            return this;
        }

        public Builder addActionEventHandler(EventHandler<ActionEvent>  eventHandler) {
            CURRENT_MENU_ITEM.addEventHandler(ActionEvent.ACTION, eventHandler);
            return this;
        }

        public ContextMenu build(){
            return contextMenu;
        }

    }
}
