package report.spring.spring.configuration.controls.contextmenu;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomContextMenu  extends ContextMenu {

    private Map<Integer,MenuState> states = new HashMap<>();

    public MenuItem addMenuItem(String text){
        MenuItem menuItem = new MenuItem(text);
        getItems().add(menuItem);
        return menuItem;
    }

    public void addSeparator(){
        getItems().add(new SeparatorMenuItem());
    }

    public void addState(int stateNumber, MenuState state){
        states.put(stateNumber,state);
    }

    public void applyState(int stateNumber){
        states.get(stateNumber).apply();
    }

}
