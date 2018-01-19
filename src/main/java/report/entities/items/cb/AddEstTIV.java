
package report.entities.items.cb;

import java.sql.Timestamp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import report.entities.items.Item;

/**
* 
* Item to CheckBox TableWrapper.
*Use into AddRowLayout (addSiteRowController)
* 
*/

public class AddEstTIV extends Item {

    private final BooleanProperty check;
    
    public AddEstTIV(
            long id,
            boolean check,
            Timestamp dateCreate, 
            String siteNumber,
            String typeHome,
            String contractor, 
            String JM_name,
            String JobOrMat, 
            String BindedJob, 
            Double value, 
            String unit, 
            Double price_one,
            Double price_sum, 
            String buildingPart) {
        super(id, dateCreate, siteNumber, typeHome, contractor, JM_name, JobOrMat, BindedJob, value, unit, price_one, price_sum, buildingPart);
        this.check = new SimpleBooleanProperty(check);
    }

    @Override
    public AddEstTIV getClone() {
        AddEstTIV clone = new AddEstTIV(
                                    super.getId(),
                                    this.getCheck(),
                                    super.getDateCreate(),
                                    super.getSiteNumber(),
                                    super.getTypeHome(),
                                    super.getContractor(),
                                    super.getJM_name(),
                                    super.getJobOrMat(),
                                    super.getBindJob(),
                                    super.getQuantity(),
                                    super.getUnit(),
                                    super.getPriceOne(),
                                    super.getPriceSum(),
                                    super.getBuildingPart()
                                    );
            return clone;
    }
    
    public boolean getCheck() {return check.get();}
    public void setCheck(boolean value_inp) {check.set(value_inp);}
    
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 3 * hash + (this.check  != null   ? this.check.hashCode() : 0);
            
        return hash;
    }

    @Override
    public String toString() {
        return "AddEstTIV{" +
                "check=" + check.getValue() +
                ", siteNumber=" + siteNumber.getValue()  +
                ", typeHome=" + typeHome.getValue() +
                ", contractor=" + contractor.getValue()  +
                ", buildingPart=" + buildingPart.getValue()  +
                ", JM_name=" + JM_name.getValue()  +
                ", JobOrMat=" + JobOrMat.getValue()  +
                ", bindJob=" + bindJob.getValue()  +
                ", quantity=" + quantity.getValue()  +
                ", unit=" + unit.getValue()  +
                ", priceOne=" + priceOne.getValue()  +
                ", priceSum=" + priceSum.getValue()  +
                '}';
    }
}
