/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.models.coefficient;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Quantity {
     private static IntegerProperty  quantity ;

    public synchronized static IntegerProperty getQuantityProperty() {
        if(quantity == null ){
            quantity  = new SimpleIntegerProperty();
            updateFromBase();
        }
       return quantity;
    }
    public synchronized static Integer getQuantityValue() {
       return getQuantityProperty().intValue();
    }

    public synchronized static IntegerProperty updateFromBase() {
        quantity.setValue(new FormulaQuery().getSiteQuantity());
       return quantity;
    }








}
