package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.Initializable;
import report.models.counterpaties.DocumentType;

import java.net.URL;
import java.util.ResourceBundle;

public class AdditionalEstimateController implements Initializable {

    private DocumentType documentType;

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
        System.out.println(documentType);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
