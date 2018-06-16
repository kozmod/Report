package report.models.counterpaties;

import javafx.collections.ObservableList;
import report.entities.items.AbstractEstimateTVI;

public interface DocumentData<T extends AbstractEstimateTVI> {
     void putDocument(DocumentType type, ObservableList<T> documents);
    ObservableList<T> get(DocumentType type);
}
