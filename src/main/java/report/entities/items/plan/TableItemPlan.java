
package report.entities.items.plan;

import java.sql.Timestamp;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;

/**
 * FACT
 * @author xxx
 */
public class TableItemPlan implements TableClone {

    private long id;
    private Timestamp dateCreate;

    protected final IntegerProperty  typeID;
    protected final StringProperty   type;
    protected final IntegerProperty  quantity;
    protected final DoubleProperty   SmetCostSum;
    protected final DoubleProperty   SaleCostSum;
    protected final DoubleProperty   profit;

    private final IntegerProperty  rest;
    private final DoubleProperty   SmetCost;
    private final DoubleProperty   SaleCost;

    public TableItemPlan(
            long      id, 
            Timestamp dateCreate,
            Integer   typeID,
            String    type, 
            Integer   quantity,
            Integer   rest, 
            Double     SmetCost,
            Double     SmetCostSum,
            Double     SaleCost,
            Double     SaleCostSum,
            Double     profit
    ) {
        this.id = id;
        this.dateCreate   = dateCreate;
        this.typeID       = new SimpleIntegerProperty(typeID);
        this.type         = new SimpleStringProperty(type);
        this.quantity     = new SimpleIntegerProperty(quantity);
        this.SmetCostSum  = new SimpleDoubleProperty(SmetCostSum);
        this.SaleCostSum  = new SimpleDoubleProperty(SaleCostSum);
        
        this.rest      = new SimpleIntegerProperty(rest);
        this.SmetCost  = new SimpleDoubleProperty(SmetCost);
        this.SaleCost  = new SimpleDoubleProperty(SaleCost);
        this.profit    = new SimpleDoubleProperty(profit);
    }
    
    //Clone CONSTRUCTOR
    @Override
    public TableClone getClone() {
         TableClone clone = new TableItemPlan(
                                        this.getID(),
                                        this.getDateCreate(),
                                        this.getTypeID(),
                                        this.getType(),
                                        this.getQuantity(),
                                            this.getRest(),
                                            this.getSmetCost(),
                                        this.getSmetCostSum(),
                                            this.getSaleCost(),
                                        this.getSaleCostSum(),
                                        this.getProfit()
                                    );
         return clone;
    }


    
    //Getter / Setter ==================================================================================
    
//    @Override
    public Long getID()                    {return this.id;}
//    @Override
    public void setID(Long id)             {this.id = id;  }

    public Timestamp       getDateCreate()            {return dateCreate;  }
    public void            setDateCreate(Timestamp v) {this.dateCreate = v;}
    
    public IntegerProperty getTypeIDProperty()       {return typeID;}
    public Integer         getTypeID()               {return typeID.getValue();}
    public void            setTypeID(Integer v)      {this.typeID.setValue(v); }
    
    public StringProperty  getTypeProperty()          {return type;}
    public String          getType()                  {return type.getValue();}
    public void            setType(String v)          {this.type.setValue(v); }

    public IntegerProperty getQuantityProperty()    {return quantity;}
    public Integer         getQuantity()            {return quantity.getValue();}
    public void            setQuantity(Integer v)   {this.quantity.setValue(v); }

    public DoubleProperty   getSmetCostSumProperty() {return SmetCostSum;}
    public Double           getSmetCostSum()         {return SmetCostSum.getValue();}
    public void             setSmetCostSum(Double v)  {this.SmetCostSum.setValue(v); }

    public DoubleProperty   getSaleCostSumProperty() {return SaleCostSum; }
    public Double           getSaleCostSum()         {return SaleCostSum.getValue();}
    public void             setSaleCostSum(Double v)  {this.SaleCostSum.setValue(v); }
    
    public IntegerProperty getRestProperty() {return rest;           }
    public Integer         getRest()         {return rest.getValue();}
    public void            setRest(Integer v){this.rest.setValue(v); }
    
    public DoubleProperty getSmetCostProperty() {return SmetCost;           }
    public Double         getSmetCost()         {return SmetCost.getValue();}
    public void           setSmetCost(Double v)  {this.SmetCost.setValue(v); }

    public DoubleProperty getSaleCostProperty() {return SaleCost; }
    public Double         getSaleCost()         {return SaleCost.getValue(); }
    public void           setSaleCost(Double v)  {this.SaleCost.setValue(v); }

    public Double         getProfit() { return profit.get();}
    public DoubleProperty getProfitProperty() {return profit;}
    public void           setProfit(double profit) {this.profit.set(profit);}

    @Override
    public int hashCode() {
        int hash = 33;
        hash = 3 * hash + (this.typeID      != null ? this.typeID.intValue()       : 0);
        hash = 3 * hash + (this.type        != null ? this.type.hashCode()         : 0);
        hash = 3 * hash + (this.quantity    != null ? this.quantity.intValue()     : 0);
        hash = 3 * hash + (this.rest        != null ? this.rest.intValue()         : 0);
        hash = 3 * hash + (this.SmetCost    != null ? this.SmetCost.intValue()     : 0);
        hash = 3 * hash + (this.SmetCostSum != null ? this.SmetCostSum.intValue()  : 0);
        hash = 3 * hash + (this.SaleCost    != null ? this.SaleCost.intValue()     : 0);
        hash = 3 * hash + (this.SaleCostSum != null ? this.SaleCostSum.intValue()  : 0);
        hash = 3 * hash + (this.profit      != null ? this.profit.intValue()       : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!TableItemPlan.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TableItemPlan other = (TableItemPlan) obj;
       
        if ((this.type.get() == null) ? (other.type.getValue() != null) : !this.type.get().equals(other.type.get())) {
            return false;
        }
        if (this.quantity.get() != other.quantity.get()) {
            return false;
        }
        if (this.rest.get() != other.rest.get()) {
            return false;
        }
        if (this.typeID.get() != other.typeID.get()) {
            return false;
        }
        if (this.SmetCostSum.get() != other.SmetCostSum.get()) {
            return false;
        }
        if (this.SaleCostSum.get() != other.SaleCostSum.get()) {
            return false;
        }
        if (this.SmetCost.get() != other.SmetCost.get()) {
            return false;
        }
        if (this.SaleCost.get() != other.SaleCost.get()) {
            return false;
        }
        if (this.profit.get() != other.profit.get()) {
            return false;
        }
    return true;
    }
        
    /**
    *Extractor 
     * @return extractor
    */
    public static Callback<TableItemPlan, Observable[]> extractor() {
        return (TableItemPlan p) -> new Observable[]{
            p.getTypeIDProperty(),
            p.getTypeProperty(),
            p.getQuantityProperty(),
            p.getSaleCostProperty(),
            p.getSmetCostProperty(),
            p.getSaleCostSumProperty(),
            p.getSmetCostSumProperty(),
            p.getProfitProperty()
        };
    }
    


    
}
