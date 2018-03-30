
package report.models.view.customNodes;


import javafx.beans.property.DoubleProperty;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;
import report.entities.items.estimate.EstimateDAO;
import report.entities.items.estimate.EstimateTVI;
import report.layoutControllers.estimate.EstimateController.Est;
import report.layoutControllers.estimate.EstimateControllerTF;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.models.view.wrappers.tableWrappers.TableWrapper;
import report.models.view.wrappers.tableWrappers.TableWrapperEST;


public class TitledStackModel extends StackPane {

    private TableWrapperEST<EstimateTVI> tableViewWrapper;
    final private Label sumLabel = new Label();
    final private TitledPane titledPane = new TitledPane();
    final private Est enumEst;
    final private String title;


    {
        this.getChildren().addAll(titledPane, sumLabel);
        this.setAlignment(Pos.TOP_RIGHT);

    }

    /*!******************************************************************************************************************
     *                                                                                                    Getter - Setter
     ********************************************************************************************************************/
    public StringProperty getLabelProperty() {
        return sumLabel.textProperty();
    }

    public DoubleProperty getLabelDoubleProperty() {
        return tableViewWrapper.getSumProperty();
    }

    public TableWrapper getModelTable() {
        return tableViewWrapper;
    }

    /*!******************************************************************************************************************
     *                                                                                                        Constructor
     ********************************************************************************************************************/
    public TitledStackModel(String title, Est enumEst) {
        this.title = title;
        this.enumEst = enumEst;
        init_tableView();
        init_Lable();
        init_titledPane();

    }


    /*!******************************************************************************************************************
     *                                                                                                     Private Methods
     ********************************************************************************************************************/
    //1
    private void init_tableView() {
        tableViewWrapper = EstimateControllerTF.getEst(enumEst, title);

        if (enumEst.getTabMap().get(title) != null) {
            tableViewWrapper.setTableData((ObservableList) enumEst.getTabMap().get(title));
            ContextMenuOptional.setTableItemContextMenuListener(tableViewWrapper);
        } else {
            tableViewWrapper.setTableData(FXCollections.observableArrayList(EstimateTVI.extractor()));
            ContextMenuOptional.setTableItemContextMenuListener(tableViewWrapper);
        }

    }

    //2
    private void init_Lable() {
        TitledStackModel.setMargin(sumLabel, new Insets(5, 10, 0, 0));
        sumLabel.textProperty().setValue(tableViewWrapper.getSumProperty().getValue().toString());
        Bindings.bindBidirectional(sumLabel.textProperty(), tableViewWrapper.getSumProperty(), new DoubleStringConverter().format());
    }

    //3
    private void init_titledPane() {
        titledPane.setText(title);
        titledPane.setContent(tableViewWrapper.tableView());

        titledPane.setExpanded(false);
        titledPane.setAnimated(false);
    }


    public void updateTableItems() {
        tableViewWrapper.updateTableFromBASE(new EstimateDAO().getOneBildingPartList(enumEst, title));
    }


//Public Methots     ============================================================================

//    public void initContextMenu(ContextMenu cm){
//        
//            tableViewWrapper.setContextMenu(cm);
//            tableViewWrapper.getObservableItems().addListener((ListChangeListener.Change<? extends Item> c) -> {
//                System.out.println("Changed on " + c);
//                if(c.next() && 
//                        (c.wasUpdated() || c.wasAdded() || c.wasRemoved() )){
////                                ((ContextMenuBuilder)contexMenuEst).setDisable_SaveUndoPrint_groupe(false);
//                                ((ContextMenuOptional)cm).setDisable_SaveUndoPrint_groupe(false);
//                }
//        });
//    }

}
