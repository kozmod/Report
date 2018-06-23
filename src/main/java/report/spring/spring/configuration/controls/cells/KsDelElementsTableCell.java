package report.spring.spring.configuration.controls.cells;


import javafx.scene.control.TableCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import report.entities.items.KS.KsTIV;
import report.layout.controllers.root.RootLayoutController;
import report.models.counterpaties.DocumentType;
import report.models.counterpaties.KsData;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class KsDelElementsTableCell extends TableCell<KsTIV, String> {

    private final KsData ksData;
    private DocumentType documentType;

    @Autowired
    public KsDelElementsTableCell(KsData ksData) {
        this.ksData = ksData;
    }

    public KsDelElementsTableCell setDocumentType(DocumentType documentType) {
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
            setText(item);
            setOnMouseEntered(value -> {
                RootLayoutController.update_changeTable(
                        ksData.getDeletedItemsList(
                                documentType,
                                this.getTableView().getItems().get(this.getIndex())
                        )
                );
            });
        }
    }
}