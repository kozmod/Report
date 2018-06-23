package report.layout.controllers.estimate.new_estimate.service;

import javafx.fxml.Initializable;
import report.models.counterpaties.DocumentType;

public interface SumPropertyContainer<T> extends Initializable {
    T property();
    void initData(DocumentType documentType, String title);
}
