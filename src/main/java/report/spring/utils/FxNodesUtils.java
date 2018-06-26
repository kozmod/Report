package report.spring.utils;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public abstract class FxNodesUtils {

    public static Tab newTab(String title, Node content) {
        Tab tab = new Tab();
        tab.setText(title);
        tab.setContent(content);
        tab.setClosable(false);
        return tab;
    }
}
