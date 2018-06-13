
package report.layout.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import report.entities.items.correspondents.ItemCorDAO;


public class CorFilterController implements Initializable {

    @FXML
    private ListView<Object> allCorList, selectedCorList;

    ObservableList<Object> allItems, selectedItems, memento;
//INIT================================================================================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedItems = new ItemCorDAO().getListCor();
        memento = FXCollections.observableArrayList(selectedItems);
        allItems = new ItemCorDAO().getListAccount();

        allCorList.setItems(allItems);
        selectedCorList.setItems(selectedItems);

    }

// HANDLERS===========================================================================================================

    @FXML
    private void handle_saveButton(ActionEvent event) {
        new ItemCorDAO().dellAndInsert(memento, selectedItems);
//        commonSQL_DELETE.dellCor_Cor(FXCollections.observableArrayList( new DiffList(selectedItems, temp).changeCollectionNotContain()));
//        commonSQL_INSERT.insertIntoCor(FXCollections.observableArrayList(new DiffList(selectedItems, temp).baseCollectionNotContain()));

    }

    @FXML
    private void handle_addSelectedTableButton(ActionEvent event) {                   //add Selected List            
        if (allCorList.getSelectionModel().getSelectedItem() != null) {
            Object sItem_allJM = allCorList.getSelectionModel().getSelectedItem();
            allItems.remove(sItem_allJM);
            selectedItems.add(sItem_allJM);

            //WHY?
            allCorList.setItems(allItems);
            selectedCorList.setItems(selectedItems);
        }

    }

    @FXML
    private void handle_removeSelectedTableButton(ActionEvent event) {                 //remove Selected List          

        if (selectedCorList.getSelectionModel().getSelectedItem() != null) {
            Object sItem_allJM = selectedCorList.getSelectionModel().getSelectedItem();
            selectedItems.remove(sItem_allJM);
            allItems.add(sItem_allJM);

            allCorList.setItems(allItems);
        }
    }

    @FXML
    private void handle_doneButton(ActionEvent event) {
        Stage appStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        appStage.close();
    }
    //===============================================================================================================


}
