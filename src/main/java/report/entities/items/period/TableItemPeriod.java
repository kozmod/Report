
package report.entities.items.period;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;


public class TableItemPeriod implements TableClone {
    
    private Long  id;
    private final StringProperty  siteNumber;    
    private final StringProperty  contractor;    
    private final StringProperty  text;    
    private final IntegerProperty dateFrom;                                                   
    private final IntegerProperty dateTo;
    
    public TableItemPeriod(
                    Long id,
                    String siteNumber,
                    String contractor,
                    String text,
                    int dateFrom,
                    int dateTo
                    ){
        this.id = id;
        this.siteNumber   = new SimpleStringProperty(siteNumber);
        this.contractor   = new SimpleStringProperty(contractor);
        this.text         = new SimpleStringProperty(text);
        this.dateFrom     = new SimpleIntegerProperty(dateFrom);
        this.dateTo       = new SimpleIntegerProperty(dateTo);
    }
    
        //Clone  implementation
        @Override
        public  TableClone getClone() {
            TableClone clone = new TableItemPeriod(
                                this.getID(),
                                this.getSiteNumber(),
                                this.getContractor(),
                                this.getText(),
                                this.getDateFrom(),
                                this.getDateTo()
                                );
            return clone;
        }



    //Static BUILDER
    public static class Builder {
        private Long  id;
        private StringProperty  siteNumber;    
        private StringProperty  contractor; 
        private StringProperty  text;    
        private IntegerProperty dateFrom;                                                   
        private IntegerProperty dateTo;                                                   
        
            public Builder setId        (Long value_inp)    {this.id = value_inp;                                        return this;} 
            public Builder setsiteNumber(String value_inp)  {this.siteNumber  = new SimpleStringProperty (value_inp);    return this;}      
            public Builder setContractor(String value_inp)  {this.contractor  = new SimpleStringProperty (value_inp);    return this;}      
            public Builder setText      (String value_inp)  {this.text        = new SimpleStringProperty (value_inp);    return this;}      
            public Builder setDateFrom  (int value_inp)     {this.dateFrom    = new SimpleIntegerProperty(value_inp);    return this;}
            public Builder setDateTo    (int value_inp)     {this.dateTo      = new SimpleIntegerProperty(value_inp);    return this;}
         
            public TableItemPeriod build() {
                return new TableItemPeriod( 
                                    id,
                                    siteNumber.getValue(),
                                    contractor.getValue(),
                                    text.getValue(),   
                                    dateFrom.getValue(),                                                  
                                    dateTo.getValue()
                                    );
            }  
        }
    
    
    //Getter / Setter
//    @Override
    public Long getID() {return id;}
//    @Override
    public void setID(Long id) {this.id = id;}
    
    public String getSiteNumber() {return siteNumber.get();} 
    public void   setSiteNumber(String value_inp) {siteNumber.set(value_inp);}
        
    public String getContractor() {return contractor.get();} 
    public void   setContractor(String value_inp) {contractor.set(value_inp);}
        
    public String         getText() {return text.get();} 
    public StringProperty getTextProperty(){return text;} 
    public void           setText(String value_inp) {text.set(value_inp);}
        
    public int             getDateFrom() {return dateFrom.get();}  
    public IntegerProperty getDateFromProperty (){return dateFrom;}  
    public void            setDateFrom(int value_inp){dateFrom.set(value_inp);}
    
    public int             getDateTo(){ return dateTo.get();}    
    public IntegerProperty getDateToProperty (){return dateTo;}      
    public void            setDateTo(int value_inp){dateTo.set(value_inp);}


    
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 3 * hash + (this.dateFrom   != null ? this.dateFrom.hashCode() : 0);
            hash = 3 * hash + (this.dateTo     != null ? this.dateTo.hashCode() : 0);

            
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            
            if (obj == null) {
                return false;
            }
            if (!TableItemPeriod.class.isAssignableFrom(obj.getClass())) {
                return false;
            }
            final TableItemPeriod other = (TableItemPeriod) obj;
            if ((this.siteNumber.get() == null) ? (other.siteNumber.getValue() != null) : !this.siteNumber.get().equals(other.siteNumber.get())) {
                return false;
            }
            if ((this.contractor.get() == null) ? (other.contractor.getValue() != null) : !this.contractor.get().equals(other.contractor.get())) {
                return false;
            }
            if (this.dateFrom.get() != other.dateFrom.get()) {
                return false;
            }
            if (this.dateTo.get() != other.dateTo.get()) {
                return false;
            }

        return true;
        }
        
    /**
    *Extractor to observe changes in "Property" fields.
    * 
    * @return Callback<TableItemVariable, Observable[]>
    */
    public static Callback<TableItemPeriod, Observable[]> extractor() {
        return (TableItemPeriod p) -> new Observable[]{p.getTextProperty(), p.getDateFromProperty(), p.getDateToProperty()};
    }
}