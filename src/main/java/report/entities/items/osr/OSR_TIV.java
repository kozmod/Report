
package report.entities.items.osr;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;

//import javax.persistence.*;


/**
 *
 * 
 */

//@Entity
//@Table(name="SiteOSR", schema="dbo")
//@Access(value = AccessType.PROPERTY)
public class OSR_TIV implements TableClone {

    private Long  id;
    private final StringProperty  text;    
    private final DoubleProperty   expenses;                                                   
    private final DoubleProperty   expensesPerHouse;

    public OSR_TIV(long id, String text, Double expenses, Double expensesPerHouse) {
        this.id               = id;
        this.text             = new SimpleStringProperty(text);
        this.expenses         = new SimpleDoubleProperty(expenses);
        this.expensesPerHouse = new SimpleDoubleProperty(expensesPerHouse);
    }




    /**
    * Clone CONSTRUCTOR implementation
    */
//    @Transient
    @Override
    public TableClone getClone() {
        TableClone clone = new OSR_TIV(
                                    this.getId(),
                                    this.getText(),
                                    this.getExpenses(),
                                    this.getExpensesPerHouse()
                                    );
            return clone;
    }
    
    //Getters AND Setters --------------------------------------------------------------------------
//    @Override
    public long getId() {return id;}
//    @Override
    public void setId(long id) {this.id = id;}


    public String getText() {return text.getValue();}
    public void   setText(String value_inp) {text.set(value_inp);}
    public StringProperty textProperty() { return text; }


    public Double getExpenses() {return expenses.getValue();}
    public void  setExpenses(double value_inp) {expenses.set(value_inp);}
    public DoubleProperty expensesProperty(){return expenses;}



    public Double getExpensesPerHouse() {return expensesPerHouse.getValue();}
    public void  setExpensesPerHouse(double value_inp) {expensesPerHouse.set(value_inp);}
    public DoubleProperty  expensesPerHouseProperty (){return expensesPerHouse;}



    //Equakls AND hashCode ==========================================================================
    @Override
    public int hashCode() {
        int hash = 2;
        hash = 1 * hash + this.text.get().hashCode();
        hash = 2 * hash + (this.expenses         != null ? Double.hashCode(this.expenses.get())         : 0);
        hash = 3 * hash + (this.expensesPerHouse != null ? Double.hashCode(this.expensesPerHouse.get()) : 0);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
   
        if (obj == null) {
            return false;
        }
        if (!OSR_TIV.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final OSR_TIV other = (OSR_TIV) obj;
        if ((this.text.get() == null) ? (other.text.getValue() != null) : !this.text.get().equals(other.text.get())) {
            return false;
        }
        if (this.expenses.get() != other.expenses.get()) {
            return false;
        }
        if (this.expensesPerHouse.get() != other.expensesPerHouse.get()) {
            return false;
        }
    return true;
    }
    
    /**
    *Extractor to observe changes in "Property" fields.
    */
    public static Callback<OSR_TIV, Observable[]> extractor() {
        return (OSR_TIV p) -> new Observable[]{p.expensesProperty()};
    }


    
}
