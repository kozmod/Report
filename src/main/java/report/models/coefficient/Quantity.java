/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.models.coefficient;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Quantity {
     private final static IntegerProperty  quantity = new SimpleIntegerProperty();

    public synchronized static IntegerProperty getQuantityProperty() {
        Quantity.updateFromBase();
       return quantity;
    }
    public synchronized static int value() {
       return getQuantityProperty().intValue();
    }

    public synchronized static IntegerProperty updateFromBase() {
        quantity.setValue(new FormulaQuery().getSiteQuantity());
       return quantity;
    }








}
