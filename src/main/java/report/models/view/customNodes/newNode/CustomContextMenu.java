package report.models.view.customNodes.newNode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.util.EnumMap;
import java.util.Map;

@Deprecated
public class CustomContextMenu extends ContextMenu {

    private final Map<MenuItemType,MenuItem> menuItems;

    private CustomContextMenu(){
        menuItems = new EnumMap<>(MenuItemType.class);
    }

    public MenuItem getMenuItem(MenuItemType type){
      return  menuItems.get(type);
    }

    public  static Builder newBuilder(){
        return new CustomContextMenu().new Builder();
    }

    public  class Builder {
        private MenuItem CURRENT_MENU_ITEM;

        private Builder(){
        }

        public Builder addMenuItem(MenuItemType type,String menuText) {
            MenuItem menuItem = new MenuItem(menuText);
            if(CustomContextMenu.this.getItems().add(menuItem)){
                CustomContextMenu.this.menuItems.put(type,menuItem);
            }
            CURRENT_MENU_ITEM = menuItem;
            return this;
        }

        public Builder addSeparator() {
            CustomContextMenu.this.getItems().add(new SeparatorMenuItem());
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
            return CustomContextMenu.this;
        }

    }
}
