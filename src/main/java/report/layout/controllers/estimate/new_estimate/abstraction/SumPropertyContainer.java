package report.layout.controllers.estimate.new_estimate.abstraction;

import javafx.fxml.Initializable;
import report.models.counterpaties.BuildingPart;
import report.models.counterpaties.DocumentType;

public interface SumPropertyContainer<T>{
    T property();
}
