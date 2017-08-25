
package report.entities.items.site;

import java.util.Objects;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;


//Preview Tabble Items to ObsList
    public class TableItemPreview implements TableClone {
 
        private Long id;
        private String sqlColumn;
        private final SimpleStringProperty firstValue;                          
        private final SimpleObjectProperty secondValue;   
      
   
    
 
        public TableItemPreview(Long id, String sqlColumnName, String fValue, Object sValue) {
            this.id        = id;
            this.sqlColumn = sqlColumnName;
            this.firstValue  = new SimpleStringProperty(fValue);
            this.secondValue = new SimpleObjectProperty(sValue);
          
            
        }
        
        //Clone CONSTRUCTOR implementation
        @Override
        public TableClone getClone() {
            TableClone clone = new TableItemPreview(
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
        public void   setSqlColumn(String sqlColumn) {this.sqlColumn = sqlColumn;}
        
        public String getFirstValue() {return firstValue.get();} 
        public void setFirstValue(String value_inp) {firstValue.set(value_inp);}

        public Object getSecondValue() {return secondValue.get();}
        public void setSecondValue(Object value_inp) {secondValue.set(value_inp);}
        public ObjectProperty getSecondProperty() {return  secondValue;}

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
        final TableItemPreview other = (TableItemPreview) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sqlColumn, other.sqlColumn)) {
            return false;
        }

        if ((this.firstValue.get() == null) ? (other.firstValue.getValue() != null) : !this.firstValue.get().equals(other.firstValue.get())) {
            return false;
        }
        if ((this.secondValue.get() == null) ? (other.secondValue.getValue() != null) : !this.secondValue.get().equals(other.secondValue.get())) {
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
    public static Callback<TableItemPreview, Observable[]> extractor() {
        return (TableItemPreview p) -> new Observable[]{p.getSecondProperty()};
    }

   
    }
    