
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
public class TableItemOSR implements TableClone {

    private Long  id;
    private final StringProperty  text;    
    private final DoubleProperty   expenses;                                                   
    private final DoubleProperty   expensesPerHouse;

    public TableItemOSR(long id, String text, Double expenses, Double expensesPerHouse) {
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
        TableClone clone = new TableItemOSR(
                                    this.getId(),
                                    this.getText(),
                                    this.getExpenses(),
                                    this.getExpensesPerHouse()
                                    );
            return clone;
    }
    
    //Getters AND Setters --------------------------------------------------------------------------
    @Override public Long getId() {return id;}
    @Override public void setId(Long id) {this.id = id;}


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
        if (!TableItemOSR.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TableItemOSR other = (TableItemOSR) obj;
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
    public static Callback<TableItemOSR, Observable[]> extractor() {
        return (TableItemOSR p) -> new Observable[]{p.expensesProperty()};
    }


    
}
