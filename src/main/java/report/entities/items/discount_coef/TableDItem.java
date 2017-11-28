
package report.entities.items.discount_coef;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;

import java.util.Objects;


//Preview Tabble Items to ObsList
public class TableDItem implements TableClone {

        private Long id;
        private final String sqlColumn;
        private final StringProperty firstValue;
        private final DoubleProperty secondValue;




    protected TableDItem(long id, String sqlColumnName, String fValue, double sValue) {
            this.id        = id;
            this.sqlColumn = sqlColumnName;
            this.firstValue  = new SimpleStringProperty(fValue);
            this.secondValue = new SimpleDoubleProperty(sValue);
        }

    public TableDItem(long id, String sqlColumn, String fValue, DoubleProperty secondValue) {
        this.id = id;
        this.sqlColumn = sqlColumn;
        this.firstValue  = new SimpleStringProperty(fValue);
        this.secondValue = secondValue;
    }

    //Clone CONSTRUCTOR implementation
        @Override
        public TableDItem getClone() {
            TableDItem clone = new TableDItem(
                                this.id,
                                this.sqlColumn,
                                this.getFirstValue(),
                                this.getSecondValue()
                                );
            return clone;  
        }



    //Getter / Setter
    @Override  public Long getId() {return id;}
    @Override  public void setId(Long id) {this.id = id;}


    public String getSqlColumn() {return sqlColumn;}

    public String getFirstValue() {
        return firstValue.get();
    }

    public StringProperty firstValueProperty() {
        return firstValue;
    }

    public void setFirstValue(String firstValue) {
        this.firstValue.set(firstValue);
    }

    public double getSecondValue() {
        return secondValue.get();
    }

    public DoubleProperty secondValueProperty() {
        return secondValue;
    }

    public void setSecondValue(double secondValue) {
        this.secondValue.set(secondValue);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 3 * hash + Objects.hashCode(this.id)                    >>>3;
        hash = 3 * hash + Objects.hashCode(this.sqlColumn)             >>>3;
        hash = 3 * hash + Objects.hashCode(this.firstValue.getValue()) >>>3;
        hash = 3 * hash + Objects.hashCode(this.secondValue.getValue())>>>3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TableDItem other = (TableDItem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sqlColumn, other.sqlColumn)) {
            return false;
        }

        if ((this.firstValue.get() == null) ? (other.firstValue.getValue() != null) : !this.firstValue.get().equals(other.firstValue.get())) {
            return false;
        }
        if ((this.secondValue.get() == 0) ? (other.secondValue.getValue() != null) : this.secondValue.get() != other.secondValue.get()) {
            return false;
        }
   
        return true;
    }

    @Override
    public String toString() {
        return "ID ->"+getId()+" FV ->"+getFirstValue() + " SV ->"+ getSecondValue();
    }

    /**
    *Extractor to observe changes in "Property" fields.
    * 
    * @return Callback<TableItemVariable, Observable[]>
    */
    public static Callback<TableDItem, Observable[]> extractor() {
        return (TableDItem p) -> new Observable[]{p.secondValueProperty()};
    }

   
    }
    