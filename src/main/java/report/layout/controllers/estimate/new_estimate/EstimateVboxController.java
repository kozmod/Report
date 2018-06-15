package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import report.entities.items.estimate.EstimateTVI;
import report.models.counterpaties.EstimateData;
import report.models.counterpaties.EstimateDocumentType;
import report.models.view.customNodes.newNode.SumVboxModel;
import report.models.view.wrappers.table.PriceSumTableWrapper;
import report.spring.views.ViewFx;

import java.net.URL;
import java.util.ResourceBundle;

public class EstimateVboxController implements Initializable {

    private EstimateDocumentType estimateDocumentType;
    private SumVboxModel<PriceSumTableWrapper<EstimateTVI>> sumVboxModel;

    @Autowired
    private EstimateData estimateData;

    public void setEstimateDocumentType(EstimateDocumentType estimateDocumentType) {
        this.estimateDocumentType = estimateDocumentType;
    }

    public void setSumVboxModel(SumVboxModel sumVboxModel) {
        this.sumVboxModel = sumVboxModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //not necessary
    }

    public void initData(){
//        fundament.tableView().setItems(estimateData.getDocuments().getByBiKey(docType,FUNDAMENT))
        sumVboxModel.getListStackModels().forEach(table -> {
            table.tableView().setItems(estimateData.getDocuments().getByBiKey(estimateDocumentType,table.tableView().getId()));
            table.computeSum();
        });
    }

}
