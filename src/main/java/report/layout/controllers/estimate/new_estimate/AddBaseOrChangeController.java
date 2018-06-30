package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.FXML;
import report.layout.controllers.estimate.new_estimate.abstraction.AbstractInitializable;
import report.models.counterpaties.DocumentType;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBaseOrChangeController extends AbstractInitializable {

    private DocumentType documentType;

    @FXML
    private Text estimateNameTest;

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(documentType.equals(DocumentType.BASE)){
            estimateNameTest.setValue("BASE");
        } else if(documentType.equals(DocumentType.CHANGED)){
            estimateNameTest.setValue("CHANGED");
        }

    }
}
