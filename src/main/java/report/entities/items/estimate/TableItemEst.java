
package report.entities.items.estimate;


import java.sql.Timestamp;
import java.util.Date;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableItem;
import report.entities.items.TableClone;


public class TableItemEst extends TableItem {
    private final IntegerProperty tableType;
    private final BooleanProperty inKS;
    //Constructor
    public TableItemEst(
                    long id,
                    Timestamp dateCreate,
                    String siteNumber, 
                    String typeHome,   
                    String contractor, 
                    String JM_name,    
                    String JobOrMat,
                    String BindedJob, 
                    Double  value, 
                    String unit, 
                    Double  price_one, 
                    Double  price_sum,
                    String bildingPart,
                    int tableType,
                    boolean inKS
                    ) {
        super(id,dateCreate, siteNumber, typeHome, contractor, JM_name, JobOrMat, BindedJob, value, unit, price_one, price_sum, bildingPart);
        this.tableType  = new SimpleIntegerProperty(tableType);
        this.inKS = new SimpleBooleanProperty(inKS);
    }
    
// Clone CONSTRUCTOR implementation
        @Override
        public TableClone getClone() {
            TableClone clone = new TableItemEst(
                                    super.getId(),
                                    super.getDateCreate(),
                                    super.getSiteNumber(),
                                    super.getTypeHome(),
                                    super.getContractor(),
                                    super.getJM_name(),
                                    super.getJobOrMat(),
                                    super.getBindedJob(),
                                    super.getValue(),
                                    super.getUnit(),
                                    super.getPrice_one(),
                                    super.getPrice_sum(),
                                    super.getBildingPart(),
                                    this.getTableType(),
                                    this.getInKS()
                                    );
            return clone;
        }

//Builder        
    public static class Builder {
        private  long           id;
        private  Timestamp      dateCreate;
        private  StringProperty siteNumber; 
        private  StringProperty typeHome;
        private  StringProperty contractor; 
        private  StringProperty bildingPart;
        private  StringProperty JM_name;                          
        private  StringProperty JobOrMat;                          
        private  StringProperty BindedJob;                          
        private  DoubleProperty  value;                            
        private  StringProperty unit;                            
        private  DoubleProperty  price_one;                            
        private  DoubleProperty  price_sum; 
        private  IntegerProperty tableType;
        private  BooleanProperty inKS;
        
    
            public Builder setId         (long value_inp)    {this.id           = value_inp;                             return this;}
            public Builder setSiteNumber (String value_inp)  {this.siteNumber   = new SimpleStringProperty (value_inp);  return this;}
            public Builder setTypeHome   (String value_inp)  {this.typeHome     = new SimpleStringProperty (value_inp);  return this;}
            public Builder setContractor (String value_inp)  {this.contractor   = new SimpleStringProperty (value_inp);  return this;}
            public Builder setJM_name    (String value_inp)  {this.JM_name      = new SimpleStringProperty (value_inp);  return this;}
            public Builder setJobOrMat   (String value_inp)  {this.JobOrMat     = new SimpleStringProperty (value_inp);  return this;}
            public Builder setBindedJob  (String value_inp)  {this.BindedJob    = new SimpleStringProperty (value_inp);  return this;}
            public Builder setValue      (Double value_inp)   {this.value        = new SimpleDoubleProperty(value_inp);  return this;}      
            public Builder setUnit       (String value_inp)  {this.unit         = new SimpleStringProperty (value_inp);  return this;}
            public Builder setPrice_one  (Double value_inp)   {this.price_one    = new SimpleDoubleProperty(value_inp);  return this;}
            public Builder setPrice_sum  (Double value_inp)   {this.price_sum    = new SimpleDoubleProperty(value_inp);  return this;}
            public Builder setBildingPart(String value_inp)  {this.bildingPart  = new SimpleStringProperty (value_inp);  return this;}
            public Builder setTableType  (int value_inp)     {this.tableType    = new SimpleIntegerProperty(value_inp);  return this;}
            public Builder setDate       (Date value_inp)    {this.dateCreate   = dateCreate;                            return this;}
            public Builder setInKS       (boolean value_inp) {this.inKS         = new SimpleBooleanProperty(value_inp);  return this;}
            
            public TableItemEst build() {
                return new TableItemEst(
                      id,
                      dateCreate ,
                      siteNumber .getValue(), 
                      typeHome   .getValue(),   
                      contractor .getValue(), 
                      JM_name    .getValue(),    
                      JobOrMat   .getValue(),
                      BindedJob  .getValue(), 
                      value      .getValue(), 
                      unit       .getValue(), 
                      price_one  .getValue(),
                      price_sum  .getValue(),
                      bildingPart.getValue(),
                      tableType  .getValue(),
                      inKS       .getValue()
                      
                    );
                
            }  
        }
//Getter //Setter ===============================================================================
    
    public int   getTableType() {return tableType.get();}
    public void  setPrice_one (int value_inp) {tableType.set(value_inp);}
    
    public boolean getInKS()    {return inKS.get();}
//    public void    setInKS(boolean value_inp) {inKS.set(value_inp);}
//    public void    setNotInKS() {inKS.set(false);}
//    public void    setInKS()    {inKS.set(true);}
    public void    setInKS(boolean value_inp){inKS.set(value_inp);}
    
              
//Equakls AND hashCode ==========================================================================
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (super.value     != null ? super.value.hashCode()     : 0);
        hash = 23 * hash + (super.price_one != null ? super.price_one.hashCode() : 0);
        hash = 23 * hash + (super.price_sum != null ? super.price_sum.hashCode() : 0);
        hash = 23 * hash + (this.tableType  != null ? this.tableType.hashCode()  : 0);
            
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
            
        if (obj == null) {
            return false;
        }
        if (!TableItem.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TableItemEst other = (TableItemEst) obj;
        if ((super.siteNumber.get() == null) ? (other.siteNumber.getValue() != null) : !super.siteNumber.get().equals(other.siteNumber.get())) {
            return false;
        }
        if ((super.typeHome.get() == null) ? (other.typeHome.getValue() != null) : !super.typeHome.get().equals(other.typeHome.get())) {
            return false;
        }
        if ((super.contractor.get() == null) ? (other.contractor.getValue() != null) : !super.contractor.get().equals(other.contractor.get())) {
            return false;
        }
        if ((super.JM_name.get() == null) ? (other.JM_name.getValue() != null) : !super.JM_name.get().equals(other.JM_name.get())) {
            return false;
        }
        if ((super.JobOrMat.get() == null) ? (other.JobOrMat.get() != null) : !super.JobOrMat.get().equals(other.JobOrMat.get())) {
            return false;
        }
        if ((super.BindedJob.get() == null) ? (other.BindedJob.get() != null) : !super.BindedJob.get().equals(other.BindedJob.get())) {
            return false;
        }
        if (super.value.get() != other.value.get()) {
            return false;
        }
        if ((super.unit.get() == null) ? (other.unit.get() != null) : !super.unit.get().equals(other.unit.get())) {
            return false;
        }
        if (super.price_one.get() != other.price_one.get()) {
            return false;
        }
        if (super.price_sum.get() != other.price_sum.get()) {
            return false;
        }
        if ((super.bildingPart.get() == null) ? (other.bildingPart.getValue() != null) : !super.bildingPart.get().equals(other.bildingPart.get())) {
            return false;
        }
     
    return true;
    }
    
    /**
    *Extractor to observe changes in "Property" fields.
     * @return extractor
    */
    public static Callback<TableItem, Observable[]> extractor() {
        return (TableItem p) -> new Observable[]{p.getValueProprty(), p.getPrice_oneProprty()};
    }
    

 
}
