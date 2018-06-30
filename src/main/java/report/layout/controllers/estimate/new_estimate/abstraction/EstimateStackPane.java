package report.layout.controllers.estimate.new_estimate.abstraction;

import javafx.beans.property.DoubleProperty;
import report.models.counterpaties.BuildingPart;

public interface EstimateStackPane extends SumPropertyContainer<DoubleProperty> {
    void setBuildingPart(BuildingPart buildingPart);
    BuildingPart getBuildingPart();
}
