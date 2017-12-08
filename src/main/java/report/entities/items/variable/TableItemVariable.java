/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report.entities.items.variable;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;


public class TableItemVariable implements TableClone {
    private Long  id;
    private final StringProperty  text;    
    private final DoubleProperty  value;
    
    public TableItemVariable(long id, String text, double value) {
        this.id     = id;
        this.text   = new SimpleStringProperty(text);
        this.value  = new SimpleDoubleProperty(value);
    }

    /**
    * Clone CONSTRUCTOR implementation
    */
    @Override
    public TableClone getClone() {
        TableClone clone = new TableItemVariable(
                                            this.getID(),
                                            this.getText(),
                                            this.getValue()
                                            );
            return clone;
    }
    
    //Getters AND Setters --------------------------------------------------------------------------    
//    @Override
    public Long getID() {return id;}
//    @Override
    public void setID(Long id) {this.id = id;}
    
    public String getText() {return text.getValue();}
    public void   setText(String value_inp) {text.set(value_inp);}
    
    public Double getValue() {return value.getValue();}
    public void  setValue(double value_inp) {value.set(value_inp);}
    public DoubleProperty  getValueProprty (){return value;}
    
    //Equakls AND hashCode ==========================================================================
    @Override
    public int hashCode() {
        int hash = 10;
        hash = 10 * hash + (this.text   != null ? this.text.hashCode()          : 0);
        hash = 10 * hash + (this.value  != null ? this.value.hashCode()         : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!TableItemVariable.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TableItemVariable other = (TableItemVariable) obj;
        if ((this.text.get() == null) ? (other.text.getValue() != null) : !this.text.get().equals(other.text.get())) {
            return false;
        }
        if (this.value.get() != other.value.get()) {
            return false;
        }
    return true;
    }
    
    /**
    *Extractor to observe changes in "Property" fields.
    * 
     * @return Callback<TableItemVariable, Observable[]>
    */
    public static Callback<TableItemVariable, Observable[]> extractor() {
        return (TableItemVariable p) -> new Observable[]{p.getValueProprty()};
    }
}
