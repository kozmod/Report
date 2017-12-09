
package report.entities.items.cb;

import java.sql.Timestamp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import report.entities.items.TableItem;

/**
* 
* TableItem to CheckBox TableWrapper.
*Use into AddRowLayout (addSiteRowController)
* 
*/

public class TableItemCB extends TableItem {

    private final BooleanProperty check;
    
    public TableItemCB(
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
    public TableItemCB getClone() {
        TableItemCB clone = new TableItemCB(
                                    super.getId(),
                                    this.getCheck(),
                                    super.getDateCreate(),
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
        return "TableItemCB{" +
                "check=" + check.getValue() +
                ", siteNumber=" + siteNumber.getValue()  +
                ", typeHome=" + typeHome.getValue() +
                ", contractor=" + contractor.getValue()  +
                ", buildingPart=" + buildingPart.getValue()  +
                ", JM_name=" + JM_name.getValue()  +
                ", JobOrMat=" + JobOrMat.getValue()  +
                ", bindJob=" + bindJob.getValue()  +
                ", value=" + value.getValue()  +
                ", unit=" + unit.getValue()  +
                ", price_one=" + price_one.getValue()  +
                ", price_sum=" + price_sum.getValue()  +
                '}';
    }
}
