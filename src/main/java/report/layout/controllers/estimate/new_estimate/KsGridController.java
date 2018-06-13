package report.layout.controllers.estimate.new_estimate;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import report.entities.items.KS.KS_TIV;
import report.models.view.wrappers.table.PriceSumTableWrapper;

import java.net.URL;
import java.util.ResourceBundle;

public class KsGridController implements Initializable {

//    @Autowired
//    private ViewFx<GridPane, AddKSController> addKsTabControllerViewFx;

    @FXML
    private Label
            ksSumLabel,
            ksDateLabel,
            erroeLable;

    @FXML
    private ListView<Object> listKS;

    @FXML
    private DatePicker
            dateKSfrom,
            dateKSto;

    @FXML
    private TableView
            tableKS,
            tableAdditional;

    @FXML
    private MenuItem addKsButton, deleteKs,printKS;


    private PriceSumTableWrapper<KS_TIV> tableKSWrapper;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtons();

    }

    public void initButtons(){
        addKsButton.setOnAction(e ->{
//            FxStageUtils.newStage(addKsTabControllerViewFx.getView());
        });
        deleteKs.setOnAction(e ->{
        });
        printKS.setOnAction(e ->{
        });
    }

}
