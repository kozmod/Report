package report.entities.items.intro;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FinishedSiteTIV {

//    private long id;
//    private int  del;
//    private Timestamp dateCreate;

    protected final StringProperty siteNumber;
    protected final StringProperty typeHome;
    protected final DoubleProperty smetCost;
    protected final DoubleProperty saleCost;

    public FinishedSiteTIV(
//                          long id,
//                          int del,
//                          Timestamp dateCreate,
            String siteNumber,
            String typeHome,
            Double smetCost,
            Double saleCost) {
//        this.id  = id;
//        this.del = del;
//        this.dateCreate = dateCreate;
        this.siteNumber = new SimpleStringProperty(siteNumber);
        this.typeHome = new SimpleStringProperty(typeHome);
        this.smetCost = new SimpleDoubleProperty(smetCost);
        this.saleCost = new SimpleDoubleProperty(saleCost);
    }

//    public long getId()        {return id;}
//    public void setId(long id) { this.id = id;}
//
//    public int  getDel()        {return del;}
//    public void setDel(int del) {this.del = del;}
//
//    public Timestamp getDateCreate()                     {return dateCreate;}
//    public void      setDateCreate(Timestamp dateCreate) {this.dateCreate = dateCreate;}

    public String getSiteNumber() {
        return siteNumber.get();
    }

    public StringProperty siteNumberProperty() {
        return siteNumber;
    }

    public void setSiteNumber(String siteNumber) {
        this.siteNumber.set(siteNumber);
    }

    public String getTypeHome() {
        return typeHome.get();
    }

    public StringProperty typeHomeProperty() {
        return typeHome;
    }

    public void setTypeHome(String typeHome) {
        this.typeHome.set(typeHome);
    }

    public double getSmetCost() {
        return smetCost.get();
    }

    public DoubleProperty smetCostProperty() {
        return smetCost;
    }

    public void setSmetCost(double smetCost) {
        this.smetCost.set(smetCost);
    }

    public double getSaleCost() {
        return saleCost.get();
    }

    public DoubleProperty saleCostProperty() {
        return saleCost;
    }

    public void setSaleCost(double saleCost) {
        this.saleCost.set(saleCost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FinishedSiteTIV that = (FinishedSiteTIV) o;

//        if (id  != that.id)  return false;
//        if (del != that.del) return false;
//        if (dateCreate            != null ? dateCreate.getTime() != that.dateCreate.getTime()         : that.dateCreate            != null) return false;
        if (siteNumber.getValue() != null ? !siteNumber.getValue().equals(that.siteNumber.getValue()) : that.siteNumber.getValue() != null)
            return false;
        if (typeHome.getValue() != null ? !typeHome.getValue().equals(that.typeHome.getValue()) : that.typeHome.getValue() != null)
            return false;
        if (smetCost.getValue() != 0 ? !smetCost.getValue().equals(that.smetCost.getValue()) : that.smetCost.getValue() != 0)
            return false;
        return saleCost.getValue() != 0 ? saleCost.getValue().equals(that.saleCost.getValue()) : that.saleCost.getValue() == 0;
    }

    @Override
    public int hashCode() {
        int result = 10;
//        int result = (int) (id ^ (id >>> 32));
//        result = 3 * result + del;
//        result = 3 * result + (dateCreate != null ? dateCreate.hashCode()>>>16 : 0);
        result = 3 * result + (siteNumber != null ? siteNumber.hashCode() >>> 16 : 0);
        result = 3 * result + (typeHome != null ? typeHome.hashCode() >>> 16 : 0);
        result = 3 * result + (smetCost != null ? Double.valueOf(smetCost.get()).hashCode() : 0);
        result = 3 * result + (saleCost != null ? Double.valueOf(saleCost.get()).hashCode() : 0);
        return result;
    }
}
