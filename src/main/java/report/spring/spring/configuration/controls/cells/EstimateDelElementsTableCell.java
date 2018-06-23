package report.spring.spring.configuration.controls.cells;


import javafx.scene.control.TableCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import report.entities.items.estimate.EstimateTVI;
import report.layout.controllers.root.RootLayoutController;
import report.models.counterpaties.DocumentType;
import report.models.counterpaties.EstimateData;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EstimateDelElementsTableCell extends TableCell<EstimateTVI, String> {

    private final EstimateData estimateData;
    private DocumentType documentType;

    @Autowired
    public EstimateDelElementsTableCell(EstimateData estimateData) {
        this.estimateData = estimateData;

    }

    public EstimateDelElementsTableCell setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
        return this;
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
            this.setOnMouseEntered(null);
        } else {
            setText(item.toString());
            setOnMouseEntered(value -> {
                RootLayoutController.update_changeTable(
                        estimateData.getEquivalentDeletedItemsList(
                                documentType,
                                this.getTableView().getItems().get(this.getIndex())
                        )
                );
                System.out.println("ID EST =" + this.getTableView().getItems().get(this.getIndex()).getId());

            });
        }
    }
}