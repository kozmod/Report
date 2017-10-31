package report.entities.items.plan;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;
import report.entities.items.TableClone;

import java.sql.Timestamp;

public  class TableItemFact implements TableClone  {
    private long id;
    private Timestamp dateCreate;

    protected final IntegerProperty typeID;
    protected final StringProperty type;
    protected final IntegerProperty  quantity;
    private final DoubleProperty   SmetCost;
    protected final DoubleProperty SmetCostSum;
    private  final DoubleProperty costHouseSum;
    private final DoubleProperty   SaleCost;
    protected final DoubleProperty   SaleCostSum;
    protected final DoubleProperty   profit;


    public TableItemFact(
            long   id,
            Timestamp dateCreate,
            int    typeID,
            String type,
            int    quantity,
            double SmetCost,
            double SmetCostSum,
            double costHouseSum,
            double SaleCost,
            double SaleCostSum,
            double profit
    ) {
        this.id = id;
        this.dateCreate   = dateCreate;
        this.typeID       = new SimpleIntegerProperty(typeID);
        this.type         = new SimpleStringProperty(type);
        this.quantity     = new SimpleIntegerProperty(quantity);
        this.SmetCostSum  = new SimpleDoubleProperty(SmetCostSum);
        this.SaleCostSum  = new SimpleDoubleProperty(SaleCostSum);

        this.SmetCost  = new SimpleDoubleProperty(SmetCost);
        this.SaleCost  = new SimpleDoubleProperty(SaleCost);
        this.costHouseSum = new SimpleDoubleProperty(costHouseSum);
        this.profit    = new SimpleDoubleProperty(profit);
    }

    //Clone CONSTRUCTOR
    @Override
    public TableClone getClone() {
        TableClone clone = new TableItemFact(
                this.getId(),
                this.getDateCreate(),
                this.getTypeID(),
                this.getType(),
                this.getQuantity(),
                this.getSmetCost(),
                this.getSmetCostSum(),
                this.getCostHouseSum(),
                this.getSaleCost(),
                this.getSaleCostSum(),
                this.getProfit()
        );
        return clone;
    }



    //Getter / Setter ==================================================================================

    @Override public Long  getId()                    {return this.id;}
    @Override public void  setId(Long id)             {this.id = id;  }

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


    public DoubleProperty getSmetCostProperty() {return SmetCost;           }
    public Double         getSmetCost()         {return SmetCost.getValue();}
    public void           setSmetCost(Double v)  {this.SmetCost.setValue(v); }

    public DoubleProperty getSaleCostProperty() {return SaleCost; }
    public Double         getSaleCost()         {return SaleCost.getValue(); }
    public void           setSaleCost(Double v)  {this.SaleCost.setValue(v); }

    public Double         getProfit() { return profit.get();}
    public DoubleProperty getProfitProperty() {return profit;}
    public void           setProfit(double profit) {this.profit.set(profit);}

    public double getCostHouseSum() {
        return costHouseSum.get();
    }
    public DoubleProperty costHouseSumProperty() {
        return costHouseSum;
    }
    public void setCostHouseSum(double costHouseSum) {
        this.costHouseSum.set(costHouseSum);
    }

    @Override
    public int hashCode() {
        int hash = 33;
        hash = 3 * hash + (this.typeID      != null ? this.typeID.intValue()       : 0);
        hash = 3 * hash + (this.type        != null ? this.type.hashCode()         : 0);
        hash = 3 * hash + (this.quantity    != null ? this.quantity.intValue()     : 0);
        hash = 3 * hash + (this.SmetCost    != null ? this.SmetCost.intValue()     : 0);
        hash = 3 * hash + (this.SmetCostSum != null ? this.SmetCostSum.intValue()  : 0);
        hash = 3 * hash + (this.SaleCost    != null ? this.SaleCost.intValue()     : 0);
        hash = 3 * hash + (this.SaleCostSum != null ? this.SaleCostSum.intValue()  : 0);
        hash = 3 * hash + (this.profit      != null ? this.profit.intValue()       : 0);
        hash = 3 * hash + (this.costHouseSum != null ? this.costHouseSum.intValue()    : 0);

        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (!TableItemFact.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TableItemFact other = (TableItemFact) obj;

        if ((this.type.get() == null) ? (other.type.getValue() != null) : !this.type.get().equals(other.type.get())) {
            return false;
        }
        if (this.quantity.get() != other.quantity.get()) {
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
        if (this.costHouseSum.get() != other.costHouseSum.get()) {
            return false;
        }
        return true;
    }

    /**
     *Extractor
     * @return extractor
     */
    public static Callback<TableItemFact, Observable[]> extractor() {
        return (TableItemFact p) -> new Observable[]{
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
