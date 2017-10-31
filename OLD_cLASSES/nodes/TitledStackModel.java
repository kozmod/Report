
package report.models_view.nodes;


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
import report.layoutControllers.showEstLayoutController.Est;
import report.models_view.data_utils.DecimalFormatter;
import report.models_view.nodes_factories.TableFactory;
import report.entities.items.estimate.TableItemEst;
import report.entities.items.estimate.ItemEstDAO;


public class TitledStackModel extends StackPane{
       
          private TableWrapperEST<TableItemEst> tableView;
    final private Label                  sumLabel   = new Label();
    final private TitledPane             titledPane = new TitledPane();
    final private Est                    enumEst;
    final private String                 title;
    

    {
        this.getChildren().addAll(titledPane,sumLabel);
        this.setAlignment(Pos.TOP_RIGHT);
    
    }

/*!******************************************************************************************************************
*                                                                                                    Getter - Setter 
********************************************************************************************************************/ 
    public StringProperty getLabelProperty() { return sumLabel.textProperty(); }

    public DoubleProperty getLabelDoubleProperty() { return tableView.getSumProperty(); }

    public TableWrapper getModelTable() { return tableView;}
        
/*!******************************************************************************************************************
*                                                                                                        Constructor
********************************************************************************************************************/ 
    public TitledStackModel (String title, Est enumEst) {
        this.title   = title;
        this.enumEst = enumEst;
        init_tableView();
        init_Lable();
        init_titledPane();
              
    }
    

/*!******************************************************************************************************************
*                                                                                                     Private Methods
********************************************************************************************************************/ 
    //1
    private void init_tableView(){
        tableView = TableFactory.getEst(enumEst);
        tableView.setTitle(title);
        
        if(enumEst.getTabMap().get(title) != null){
             tableView.setTableData((ObservableList) enumEst.getTabMap().get(title));
             ContextMenuOptional.setTableItemContextMenuListener(tableView);
        }else{
            tableView.setTableData(FXCollections.observableArrayList(TableItemEst.extractor()));
            ContextMenuOptional.setTableItemContextMenuListener(tableView);
        }

    }
    
    //2
    private void init_Lable(){
        TitledStackModel.setMargin(sumLabel, new Insets(5,10,0,0));
        sumLabel.textProperty().setValue( tableView.getSumProperty().getValue().toString());
        Bindings.bindBidirectional(sumLabel.textProperty(), tableView.getSumProperty(), DecimalFormatter.getDecimalFormat());
    }
    
    //3
    private void init_titledPane(){
        titledPane.setText(title);
        titledPane.setContent(tableView);
        
        titledPane.setExpanded(false);
        titledPane.setAnimated(false);      
    }


    

    public  void updateTableItems(){
        tableView.updateTableFromBASE(new ItemEstDAO().getOneBildingPartList(enumEst, title));
    }
    
    
//Public Methots     ============================================================================   

//    public void initContextMenu(ContextMenu cm){
//        
//            tableView.setContextMenu(cm);
//            tableView.getItems().addListener((ListChangeListener.Change<? extends TableItem> c) -> {
//                System.out.println("Changed on " + c);
//                if(c.next() && 
//                        (c.wasUpdated() || c.wasAdded() || c.wasRemoved() )){
////                                ((ContextMenuBuilder)contexMenuEst).setDisable_SaveUndoPrint_groupe(false);
//                                ((ContextMenuOptional)cm).setDisable_SaveUndoPrint_groupe(false);
//                }
//        });
//    }
    
}