package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.Initializable;
import report.models.counterpaties.EstimateDocumentType;

import java.net.URL;
import java.util.ResourceBundle;

public class EstimateVboxController implements Initializable {








    public static EstimateVboxController createEstimateVboxController(EstimateDocumentType estimateDocumentType){
        System.out.println(estimateDocumentType);
        return new EstimateVboxController();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //not necessary
    }
}
