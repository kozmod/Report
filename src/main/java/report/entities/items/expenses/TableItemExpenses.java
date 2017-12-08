
package report.entities.items.expenses;


import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;

import java.util.Objects;


public class TableItemExpenses implements TableClone {
    
    private Long  id;
    private final StringProperty  siteNumber;    
    private final StringProperty  contractor;    
    private final StringProperty  text;    
    private final IntegerProperty type;                                                   
    private final DoubleProperty  value;
    
    public TableItemExpenses(
                    Long id,
                    String siteNumber,
                    String contractor,
                    String text,
                    int type,
                    Double value
                    ){
        this.id  = id;
        this.siteNumber   = new SimpleStringProperty(siteNumber);
        this.contractor   = new SimpleStringProperty(contractor);
        this.text         = new SimpleStringProperty(text);
        this.type         = new SimpleIntegerProperty(type);
        this.value        = new SimpleDoubleProperty(value);
    }
    
        //Clone  implementation
        @Override
        public  TableClone getClone() {
            TableClone clone = new TableItemExpenses
                           .Builder()
                           .setId(this.getID())
                           .setsiteNumber(this.getSiteNumber())
                           .setContractor(this.getContractor())
                           .setText(this.getText())
                           .setType(this.getType())
                           .setValue(this.getValue())
                           .build();
            return clone;
        }


    //Static BUILDER
    public static class Builder {
        private Long  id;
        private final StringProperty  siteNumber;    
        private final StringProperty  contractor; 
        private final StringProperty  text;    
        private final IntegerProperty type;                                                   
        private final DoubleProperty   value;

        public Builder() {
            this.siteNumber   = new SimpleStringProperty();
            this.contractor   = new SimpleStringProperty();
            this.text   = new SimpleStringProperty();
            this.type   = new SimpleIntegerProperty();
            this.value  = new SimpleDoubleProperty();
        }
        
            public Builder setId        (Long value_inp)    {this.id = value_inp;                 return this;}      
            public Builder setsiteNumber(String value_inp)  {this.siteNumber  .set(value_inp);    return this;}      
            public Builder setContractor(String value_inp)  {this.contractor  .set(value_inp);    return this;}      
            public Builder setText      (String value_inp)  {this.text        .set(value_inp);    return this;}      
            public Builder setType      (int value_inp)     {this.type        .set(value_inp);    return this;}
            public Builder setValue     (Double value_inp)  {this.value       .set(value_inp);    return this;}
        
            public TableItemExpenses build() {
                return new TableItemExpenses( 
                                    id,
                                    siteNumber.getValue(),
                                    contractor.getValue(),
                                    text.getValue(),   
                                    type.getValue(),                                                  
                                    value.getValue()
                                    );
            }  
        }
    
    
    //Getter / Setter
//    @Override  public Long getID() {return id;}
//    @Override  public void setID(Long id) {this.id = id;}
    public Long getID() {return id;}
    public void setID(Long id) {this.id = id;}
    
    public String getSiteNumber() {return siteNumber.get();} 
    public void   setSiteNumber(String value_inp) {siteNumber.set(value_inp);}
        
    public String getContractor() {return contractor.get();} 
    public void   setContractor(String value_inp) {contractor.set(value_inp);}
        
    public String         getText() {return text.get();} 
    public StringProperty getTextProperty(){return text;}
    public void           setText(String value_inp) {text.set(value_inp);}
        
    public int    getType(){return type.get();}        
    public void   setType(int value_inp){type.set(value_inp);}

    public Double         getValue() {return value.get();} 
    public DoubleProperty getValueProperty() {return value;} 
    public void           setValue (float value_inp) {value.set(value_inp);}
    
    
        @Override
        public int hashCode() {
            int hash = 3;
            hash = Long.valueOf(this.id).hashCode();
            hash = 3 * hash + (this.siteNumber.getValue()!= null ? this.siteNumber.getValue().hashCode()    : 0);
            hash = 3 * hash + (this.contractor.getValue()!= null ? this.contractor.getValue().hashCode()    : 0);
            hash = 3 * hash + (this.text.getValue()      != null ? this.text.getValue().hashCode()          : 0);
            hash = 3 * hash + (this.type.get());
            hash = 3 * hash + (this.value.get()          != 0 ? Double.valueOf(this.value.get()).hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            
            if (obj == null) {
                return false;
            }
            if (!TableItemExpenses.class.isAssignableFrom(obj.getClass())) {
                return false;
            }
            final TableItemExpenses other = (TableItemExpenses) obj;
            if (!Objects.equals(this.id, other.id))
                return false;
            if ((this.siteNumber.get() == null) ? (other.siteNumber.getValue() != null) : !this.siteNumber.get().equals(other.siteNumber.get())) {
                return false;
            }
            if ((this.contractor.get() == null) ? (other.contractor.getValue() != null) : !this.contractor.get().equals(other.contractor.get())) {
                return false;
            }
            if (this.type.get() != other.type.get()) {
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
    public static Callback<TableItemExpenses, Observable[]> extractor() {
        return (TableItemExpenses p) -> new Observable[]{p.getTextProperty(), p.getValueProperty()};
    }
}
