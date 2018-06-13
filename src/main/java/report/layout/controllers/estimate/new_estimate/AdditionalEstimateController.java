package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.Initializable;
import report.models.counterpaties.EstimateDocumentType;

import java.net.URL;
import java.util.ResourceBundle;

public class AdditionalEstimateController implements Initializable {

    private EstimateDocumentType estimateDocumentType;

    public void setEstimateDocumentType(EstimateDocumentType estimateDocumentType) {
        this.estimateDocumentType = estimateDocumentType;
        System.out.println(estimateDocumentType);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
