
package report.models_view.nodes;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import report.layoutControllers.EstimateController.Est;
import report.models_view.data_utils.decimalFormatters.DoubleDFormatter;


public class TabModel {
    private Est enumEst;
    private TitledStackModel faundationTP, wallsTP, roofTP, apertureTP, finishingWorkTP;
    private VBox baseVBox;
    private ScrollPane scrollPane;
    private SumLabel sumLable;
    private DoubleBinding sumDouble;

//Constructors ========================================================================================================
    
    private TabModel() {}
    
    public TabModel(Est enumEst) {
        this.enumEst = enumEst;
        createTitledPane();
    }


//Getter/Setter=====================================================================================================
    public VBox getBaseVBox() {
        return baseVBox;
    }


    public SumLabel getSumLabel() {
        return sumLable;
    }
    public Double getSumLabelValue() {

        return sumDouble.getValue();
    }


//Methots ===========================================================================================================    
    private void createTitledPane(){
       
        baseVBox = new VBox();
        scrollPane = new ScrollPane();
        faundationTP    = new TitledStackModel("ФУНДАМЕНТ",         enumEst);
        wallsTP         = new TitledStackModel("СТЕНЫ, ПЕРЕКРЫТИЯ", enumEst);
        roofTP          = new TitledStackModel("КРОВЛЯ",            enumEst);
        apertureTP      = new TitledStackModel("ПРОЕМЫ",            enumEst);
        finishingWorkTP = new TitledStackModel("ОТДЕЛОЧНЫЕ РАБОТЫ", enumEst);
        
        sumLable = new SumLabel(new Insets(5,10,10,10));
//        sumLable.textProperty().bind(new StringBinding(){
//            {
//                super.bind(
//                        faundationTP.getLabelProperty(),
//                        wallsTP.getLabelProperty(),
//                        roofTP.getLabelProperty(),
//                        apertureTP.getLabelProperty(),
//                        finishingWorkTP.getLabelProperty());
//            }
//
//            @Override
//            protected String computeValue() {
//                double f1 = DecimalFormatter.stringToDouble(faundationTP.getLabelProperty().getValueSafe());
//                double f2 = DecimalFormatter.stringToDouble(wallsTP.getLabelProperty().getValueSafe());
//                double f3 = DecimalFormatter.stringToDouble(roofTP.getLabelProperty().getValueSafe());
//                double f4 = DecimalFormatter.stringToDouble(apertureTP.getLabelProperty().getValueSafe());
//                double f5 = DecimalFormatter.stringToDouble(finishingWorkTP.getLabelProperty().getValueSafe());
//
////                return faundationTP.getLabelProperty().getValue();
//                return DecimalFormatter.formatNumber(f1 + f2 + f3+ f4 + f5);
//            }
//        });
        sumDouble =  new DoubleBinding(){
            {
                super.bind(
                        faundationTP.getLabelDoubleProperty(),
                        wallsTP.getLabelDoubleProperty(),
                        roofTP.getLabelDoubleProperty(),
                        apertureTP.getLabelDoubleProperty(),
                        finishingWorkTP.getLabelDoubleProperty());
            }

            @Override
            protected double computeValue() {
                double f1 = faundationTP.getLabelDoubleProperty().get();
                double f2 = wallsTP.getLabelDoubleProperty().get();
                double f3 = roofTP.getLabelDoubleProperty().get();
                double f4 = apertureTP.getLabelDoubleProperty().get();
                double f5 = finishingWorkTP.getLabelDoubleProperty().get();
//                return faundationTP.getLabelProperty().getValue();
                return f1 + f2 + f3+ f4 + f5;
            }
        };

        sumLable.textProperty().bind(new StringBinding(){
            { super.bind(sumDouble); }

            @Override
            protected String computeValue() {
                return new DoubleDFormatter().toString(sumDouble.get());
            }
        });

        baseVBox.getChildren().addAll(faundationTP,wallsTP,roofTP,apertureTP,finishingWorkTP);


       
    }
       
//    public void updateContextMenus(){
//        faundationTP    .updateContextMenu();
//        wallsTP         .updateContextMenu();      
//        roofTP          .updateContextMenu();      
//        apertureTP      .updateContextMenu();   
//        finishingWorkTP .updateContextMenu();
//        
//    }
    
    public void setEditable(boolean param){
        faundationTP    .getModelTable().setEditable(param);
        wallsTP         .getModelTable().setEditable(param);    
        roofTP          .getModelTable().setEditable(param);  
        apertureTP      .getModelTable().setEditable(param);  
        finishingWorkTP .getModelTable().setEditable(param);
    }
    
    public void updateTablesItems(){
        faundationTP    .updateTableItems();
        wallsTP         .updateTableItems();    
        roofTP          .updateTableItems();  
        apertureTP      .updateTableItems();  
        finishingWorkTP .updateTableItems();
    }
    
 // inner class all sum lable =================================================================================================================
    class SumLabel extends Label {

        SumLabel(Insets insets) {
            setPadding(insets);
            init_Properties();
        }
        
        SumLabel() {
            setPadding(new Insets(0,0,0,0));
            init_Properties();
        }
        
        private void init_Properties(){
            setFont(Font.font("Arial", FontWeight.BOLD, 15));
            setTextFill(Color.MAROON);
            setMaxWidth(Double.MAX_VALUE);
            setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        }
        
        
    }
}
