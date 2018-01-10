
package report.entities.items.KS;

import java.sql.Timestamp;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Callback;
import report.entities.items.TableItem;


public class KS_TIV extends TableItem {
    
    private final IntegerProperty ksNumber; 
    private final IntegerProperty ksDate;
    private DoubleProperty restOfValue;

    //Constructor
    public KS_TIV(
            long        id,
            Timestamp   dateCreate,
            int         ksNumber,
            int         ksDate,
            String      siteNumber, 
            String      typeHome, 
            String      contractor, 
            String      JM_name, 
            String      JobOrMat, 
            String      BindedJob, 
            Double       value, 
            String      unit, 
            Double       price_one, 
            Double       price_sum, 
            String      bildingPart) {
        super(id ,dateCreate, siteNumber, typeHome, contractor, JM_name, JobOrMat, BindedJob, value, unit, price_one, price_sum, bildingPart);
        this.ksNumber       = new SimpleIntegerProperty(ksNumber);
        this.ksDate         = new SimpleIntegerProperty(ksDate);
        this.restOfValue = new SimpleDoubleProperty(0);
        
    }
 
// Clone CONSTRUCTOR implementation
        @Override
        public KS_TIV getClone() {
            KS_TIV clone = new KS_TIV(
                                    super.getId(),
                                    super.getDateCreate(),
                                    this.getKSNumber(),
                                    this.getKSDate(),
                                    super.getSiteNumber(),
                                    super.getTypeHome(),
                                    super.getContractor(),
                                    super.getJM_name(),
                                    super.getJobOrMat(),
                                    super.getBindJob(),
                                    super.getValue(),
                                    super.getUnit(),
                                    super.getPrice_one(),
                                    super.getPrice_sum(),
                                    super.getBuildingPart()
                                    );
            return clone;
        }
//Getter //Setter ===============================================================================    
        public int    getKSNumber() {return ksNumber.get();}
        public void   setKSNumber(int value_inp) {ksNumber.set(value_inp);}

        public int    getKSDate() {return ksDate.get();}
        public void   setKSDate(int value_inp) {ksDate.set(value_inp);}

        public double getRestOfValue() {return restOfValue.get();}
        public void   setRestOfValue(double valu_inp) {this.restOfValue.set(valu_inp);}
        
        
        
              
//Equakls AND hashCode ==========================================================================         
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 2 * hash + (this.ksDate     != null ? this.price_sum.hashCode()  : 0);
        hash = 2 * hash + (this.ksNumber   != null ? this.ksNumber.hashCode()   : 0);
        hash = 2 * hash + (super.value     != null ? super.value.hashCode()     : 0);
        hash = 2 * hash + (super.price_one != null ? super.price_one.hashCode() : 0);
        hash = 2 * hash + (super.price_sum != null ? super.price_sum.hashCode() : 0);
            
    return hash;
   }
          
    @Override
    public boolean equals(Object obj) {
            
        if (obj == null) {
            return false;
        }
        if (!KS_TIV.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final KS_TIV other = (KS_TIV) obj;
        if (this.ksNumber.get() != other.ksNumber.get()) {
            return false;
        }
        if (this.ksDate.get() != other.ksDate.get()) {
            return false;
        }
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
        if ((super.bindJob.get() == null) ? (other.bindJob.get() != null) : !super.bindJob.get().equals(other.bindJob.get())) {
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
        if ((super.buildingPart.get() == null) ? (other.buildingPart.getValue() != null) : !super.buildingPart.get().equals(other.buildingPart.get())) {
            return false;
        }
    return true;
    }
    
    
    public static Callback<TableItem, Observable[]> extractor() {
        return (TableItem p) -> new Observable[]{p.valueProperty()};
    }

}
