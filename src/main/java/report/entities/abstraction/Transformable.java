package report.entities.abstraction;

import javafx.collections.ObservableList;
import report.entities.items.DItem;

public interface Transformable<T> {
    ObservableList<T> reverse();
}
