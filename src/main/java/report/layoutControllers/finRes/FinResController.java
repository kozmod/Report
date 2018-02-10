
package report.layoutControllers.finRes;

import java.net.URL;
import java.util.ResourceBundle;
import static java.util.stream.Collectors.toCollection;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import report.entities.items.fin_res.FinResDAO;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.entities.items.fin_res.FinResTVI;
import report.models.converters.dateStringConverters.LocalDayStringConverter;


public class FinResController implements Initializable {

    @FXML private DatePicker dateFinResFrom, dateFinResTo;
    @FXML private TableView<FinResTVI>  finResTable;
    @FXML private TextField sumSmetCostTF, sumCostHouseTF, profitTF;

    private ObservableList<FinResTVI> finResObs = new FinResDAO().getList();


/*!******************************************************************************************************************
*                                                                                                               INIT
********************************************************************************************************************/

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Decorate and Init FinRes
        FinResControllerTF.decorFinRes(finResTable);
        finResTable.setItems(finResObs);

//        //Init DatePickers
        dateFinResFrom.setConverter(
                        new LocalDayStringConverter()
        );
        dateFinResTo.setConverter(
                        new LocalDayStringConverter()
        );

        //set TF quantity
        setTextFieldValue(sumSmetCostTF);
        setTextFieldValue(sumCostHouseTF);
        setTextFieldValue(profitTF);
    }




    private void setTextFieldValue(TextField textfield){
        float sumFloat = 0;
        for(FinResTVI item : finResTable.getItems()){
            if(textfield.equals(sumSmetCostTF)){
                sumFloat += item.getSmetCost();
            }else if (textfield.equals(sumCostHouseTF)){
                sumFloat += item.getCostHouse();

            }else if (textfield.equals(profitTF)){
                if(item.getProfit() != Float.NaN)
                    sumFloat += item.getProfit();
            }
        }
        textfield.setText(new DoubleStringConverter().toString(sumFloat));

    }
    

/*!******************************************************************************************************************
*                                                                                                     	    HANDLERT
********************************************************************************************************************/

    @FXML
    private void handle_FilterSite(ActionEvent event) {
        if (isInputValid()){
            finResTable.setItems(
                    finResObs.stream()
                            .filter(item -> item.getDateContract()>=(int) dateFinResFrom.getValue().toEpochDay()
                                    && item.getDateContract()<=(int) dateFinResTo.getValue().toEpochDay())
                            .collect(toCollection(FXCollections::observableArrayList)));
//            System.err.println(finResTable.getItems());
            setTextFieldValue(sumSmetCostTF);
            setTextFieldValue(sumCostHouseTF);
            setTextFieldValue(profitTF);
        }
    }

    @FXML
    private void handle_AllSite(ActionEvent event) {
        finResObs = new FinResDAO().getList();
        finResTable.setItems(finResObs);
        setTextFieldValue(sumSmetCostTF);
        setTextFieldValue(sumCostHouseTF);
        setTextFieldValue(profitTF);

    }

/*!******************************************************************************************************************
*                                                                                                     InputValidator
********************************************************************************************************************/
    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

            if (dateFinResFrom.getValue() == null ){
                errorMessage.append("'Дата поиска: с' ");
                dateFinResFrom.setEditable(true);
                dateFinResFrom.setStyle("-fx-border-color: red;");

            }
            if (dateFinResTo.getValue() == null){
                errorMessage.append("'Дата поиска: по' ");

                dateFinResTo.setStyle("-fx-border-color: red;");
            }
            if(dateFinResFrom.getValue() != null && dateFinResTo.getValue() != null
                    && dateFinResFrom.getValue().toEpochDay() > dateFinResTo.getValue().toEpochDay()){
                errorMessage.append("'Неверный период' ");
                dateFinResFrom.setStyle("-fx-border-color: red;");

            }


//        if(errorMessage.length() >0){
//            errorLabel.setText("Ошибки в полях: " +errorMessage);
//            errorLabel.setVisible(true);
//        }

        PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
        visiblePause.setOnFinished( e -> {
                        dateFinResFrom.setStyle(null);
                        dateFinResTo.setStyle(null);
                    });
        visiblePause.play();

        return errorMessage.length() == 0;
        }

  }

/*!******************************************************************************************************************
*                                                                                                     Inner Classes
********************************************************************************************************************/


    

