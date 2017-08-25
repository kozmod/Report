
package report.entities.items.cb;

import java.sql.Timestamp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import report.entities.items.TableClone;
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
            String bildingPart) {
        super(id, dateCreate, siteNumber, typeHome, contractor, JM_name, JobOrMat, BindedJob, value, unit, price_one, price_sum, bildingPart);
        this.check = new SimpleBooleanProperty(check);
    }

    @Override
    public TableClone getClone() {
        TableClone clone = new TableItemCB(
                                    super.getId(),
                                    this.getCheck(),
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
                                    super.getBildingPart()
                                    );
            return clone;
    }
    
    public boolean getCheck() {return check.get();}
    public void setCheck(boolean value_inp) {check.set(value_inp);}
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (super.value != null     ? super.value.hashCode() : 0);
        hash = 23 * hash + (super.price_one != null ? super.price_one.hashCode() : 0);
        hash = 23 * hash + (super.price_sum != null ? super.price_sum.hashCode() : 0);
            
        return hash;
    }
}
