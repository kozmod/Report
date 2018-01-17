
package report.entities.items;

import java.sql.Timestamp;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


//Estimate Tabble Items to ObsList
public abstract class ItemInterface implements CloneInterface {
    //new quantity
    private long id;
    private int  del;
    private Timestamp dateCreate;

    protected final StringProperty siteNumber;
    protected final StringProperty typeHome;
    protected final StringProperty contractor;
    protected final StringProperty buildingPart;
    protected final StringProperty JM_name;
    protected final StringProperty JobOrMat;
    protected final StringProperty bindJob;
    protected final DoubleProperty quantity;
    protected final StringProperty unit;
    protected final DoubleProperty priceOne;
    protected final DoubleProperty priceSum;


    public ItemInterface(
            long id,
            Timestamp dateCreate,
            String siteNumber,
            String typeHome,
            String contractor,
            String JM_name,
            String JobOrMat,
            String bindJob,
            Double quantity,
            String unit,
            Double priceOne,
            Double priceSum,
            String buildingPart

    ) {
        this.id = id;
        this.dateCreate    = dateCreate;
        this.siteNumber    = new SimpleStringProperty(siteNumber);
        this.typeHome      = new SimpleStringProperty(typeHome);
        this.contractor    = new SimpleStringProperty(contractor);
        this.JM_name       = new SimpleStringProperty(JM_name);
        this.JobOrMat      = new SimpleStringProperty(JobOrMat);
        this.bindJob = new SimpleStringProperty(bindJob);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.unit          = new SimpleStringProperty(unit);
        this.priceOne = new SimpleDoubleProperty(priceOne);
        this.priceSum = new SimpleDoubleProperty(priceSum);
        this.buildingPart = new SimpleStringProperty(buildingPart);

    }

//        Clone CONSTRUCTOR implementation
//        @Override
//        public  CloneInterface getClone() {
//            CloneInterface clone = new ItemInterface(
//                                    this.getSiteNumber(),
//                                    this.getTypeHome(),
//                                    this.getContractor(),
//                                    this.getJM_name(),
//                                    this.getJobOrMat(),
//                                    this.getBindJob(),
//                                    this.getQuantity(),
//                                    this.getUnit(),
//                                    this.getPriceOne(),
//                                    this.getPriceSum(),
//                                    this.getBuildingPart()
//                                    );
//            return clone;
//        }

//        //Static BUILDER
//    public static class Builder {
//        private  StringProperty siteNumber; 
//        private  StringProperty typeHome;
//        private  StringProperty contractor; 
//        private  StringProperty buildingPart;
//        private  StringProperty JM_name;                          
//        private  StringProperty JobOrMat;                          
//        private  StringProperty bindJob;
//        private  FloatProperty  quantity;
//        private  StringProperty unit;                            
//        private  FloatProperty  priceOne;
//        private  FloatProperty  priceSum;
//        
//    
//            public Builder setSiteNumber(String value_inp)  {this.siteNumber    = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setTypeHome(String value_inp)    {this.typeHome      = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setContractor(String value_inp)  {this.contractor    = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setJM_name(String value_inp)     {this.JM_name       = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setJobOrMat(String value_inp)    {this.JobOrMat      = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setBindJob(String value_inp)   {this.bindJob     = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setQuantity(float value_inp)        {this.quantity         = new SimpleFloatProperty (value_inp);  return this;}
//            public Builder setUnit(String value_inp)        {this.unit          = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setPriceOne (float value_inp)   {this.priceOne     = new SimpleFloatProperty (value_inp);  return this;}
//            public Builder setPriceSum(float value_inp)    {this.priceSum     = new SimpleFloatProperty (value_inp);  return this;}
//            public Builder setBuildingPart(String value_inp) {this.buildingPart   = new SimpleStringProperty(value_inp);  return this;}
//            
////            public ItemInterface build() {
////                return new ItemInterface(
////                      siteNumber .getQuantity(),
////                      typeHome   .getQuantity(),
////                      contractor .getQuantity(),
////                      JM_name    .getQuantity(),
////                      JobOrMat   .getQuantity(),
////                      bindJob  .getQuantity(),
////                      quantity      .getQuantity(),
////                      unit       .getQuantity(),
////                      priceOne  .getQuantity(),
////                      priceSum  .getQuantity(),
////                      buildingPart.getQuantity()
////                    );
////            }  
//        }

    //Getter / Setter
//  public Long getId() {return id;}
//  public void setId(Long id) {this.id = id;}
    @Override  public long getId() {return id;}
    @Override  public void setId(long id) {this.id = id;}

    public Timestamp getDateCreate() {return dateCreate;}
    public void      setDateCreate(Timestamp value_inp) {this.dateCreate = value_inp;}

    public String getSiteNumber() {return siteNumber.get();}
    public void   setSiteNumber(String value_inp) {siteNumber.set(value_inp);}
    public StringProperty siteNumberProperty() { return siteNumber;}

    public String getTypeHome() {return typeHome.get();}
    public void   setTypeHome(String value_inp) {typeHome.set(value_inp);}
    public StringProperty typeHomeProperty() {return typeHome;}

    public String getContractor() {return contractor.get();}
    public void   setContractor(String value_inp) {contractor.set(value_inp);}
    public StringProperty contractorProperty() { return contractor; }


    public String getJM_name() {return JM_name.get();}
    public void   setJM_name(String value_inp) {JM_name.set(value_inp);}
    public StringProperty JM_nameProperty() {return JM_name;}

    public String getJobOrMat() {return JobOrMat.get();}
//    public void   setJobOrMat(String value_inp) {JobOrMat.set(value_inp);}
    public StringProperty jobOrMatProperty() {return JobOrMat;}

//    public String getBindJob() {return bindJob.get();}
//    public void   setBindJob(String value_inp) {bindJob.set(value_inp);}
//    public StringProperty bindJobProperty() {return bindJob; }


    public String getBindJob() {
        return bindJob.get();
    }
    public void setBindJob(String bindJob) {
        this.bindJob.set(bindJob);
        if(bindJob.equals("-")){
            this.JobOrMat.setValue("Работа");
        }else{
            this.JobOrMat.setValue("Материал");
        }
    }
    public StringProperty bindJobProperty() {
        return bindJob;
    }

    public Double getQuantity()                {return quantity.get();}
    public void setQuantity(double value_inp) {
        quantity.set(value_inp);}
    public DoubleProperty quantityProperty() {return quantity;}

    public String getUnit() {return unit.get();}
    public void   setUnit(String value_inp) {unit.set(value_inp);}
    public StringProperty unitProperty() {return unit;}
    public Double getPriceOne() {return priceOne.get();}
    public void setPriceOne(double value_inp) {
        priceOne.set(value_inp);}
    public DoubleProperty priceOneProperty() {return priceOne;}

    public Double getPriceSum() {return priceSum.get();}
    public void setPriceSum(double value_inp) {
        priceSum.set(value_inp);}
    public DoubleProperty priceSumProperty() {return priceSum;}

    public String getBuildingPart() {return buildingPart.get();}
    public void setBuildingPart(String value_inp) {
        buildingPart.set(value_inp);}
    public StringProperty buildingPartProperty() {return buildingPart;}
    public int  getDel() {return del;}
    public void setDel(int del) {this.del = del;}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.quantity != null ? this.quantity.hashCode()     : 0);
        hash = 23 * hash + (this.priceOne != null ? this.priceOne.hashCode() : 0);
        hash = 23 * hash + (this.priceSum != null ? this.priceSum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        if (!ItemInterface.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ItemInterface other = (ItemInterface) obj;
        if ((this.siteNumber.get() == null) ? (other.siteNumber.getValue() != null) : !this.siteNumber.get().equals(other.siteNumber.get())) {
            return false;
        }
        if ((this.typeHome.get() == null) ? (other.typeHome.getValue() != null) : !this.typeHome.get().equals(other.typeHome.get())) {
            return false;
        }
        if ((this.contractor.get() == null) ? (other.contractor.getValue() != null) : !this.contractor.get().equals(other.contractor.get())) {
            return false;
        }
        if ((this.JM_name.get() == null) ? (other.JM_name.getValue() != null) : !this.JM_name.get().equals(other.JM_name.get())) {
            return false;
        }
        if ((this.JobOrMat.get() == null) ? (other.JobOrMat.get() != null) : !this.JobOrMat.get().equals(other.JobOrMat.get())) {
            return false;
        }
        if ((this.bindJob.get() == null) ? (other.bindJob.get() != null) : !this.bindJob.get().equals(other.bindJob.get())) {
            return false;
        }
        if (this.quantity.get() != other.quantity.get()) {
            return false;
        }
        if ((this.unit.get() == null) ? (other.unit.get() != null) : !this.unit.get().equals(other.unit.get())) {
            return false;
        }
        if (this.priceOne.get() != other.priceOne.get()) {
            return false;
        }
        if (this.priceSum.get() != other.priceSum.get()) {
            return false;
        }
        if ((this.buildingPart.get() == null) ? (other.buildingPart.getValue() != null) : !this.buildingPart.get().equals(other.buildingPart.get())) {
            return false;
        }
        return true;
    }

    // EQUIALS Entity on tableItem level//
    public boolean equalsSuperClass(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!ItemInterface.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ItemInterface other = (ItemInterface) obj;
        if ((this.siteNumber.get() == null) ? (other.siteNumber.getValue() != null) : !this.siteNumber.get().equals(other.siteNumber.get())) {
            return false;
        }
        if ((this.typeHome.get() == null) ? (other.typeHome.getValue() != null) : !this.typeHome.get().equals(other.typeHome.get())) {
            return false;
        }
        if ((this.contractor.get() == null) ? (other.contractor.getValue() != null) : !this.contractor.get().equals(other.contractor.get())) {
            return false;
        }
        if ((this.JM_name.get() == null) ? (other.JM_name.getValue() != null) : !this.JM_name.get().equals(other.JM_name.get())) {
            return false;
        }
        if ((this.JobOrMat.get() == null) ? (other.JobOrMat.get() != null) : !this.JobOrMat.get().equals(other.JobOrMat.get())) {
            return false;
        }
        if ((this.bindJob.get() == null) ? (other.bindJob.get() != null) : !this.bindJob.get().equals(other.bindJob.get())) {
            return false;
        }

        if ((this.unit.get() == null) ? (other.unit.get() != null) : !this.unit.get().equals(other.unit.get())) {
            return false;
        }
        if ((this.buildingPart.get() == null) ? (other.buildingPart.getValue() != null) : !this.buildingPart.get().equals(other.buildingPart.get())) {
            return false;
        }
        return true;
    }



    //Extractor
//   public static Callback<ItemInterface, Observable[]> extractor() {
//        return (ItemInterface p) -> new Observable[]{p.quantityProperty(), p.priceOneProperty()};
//    }


}
    
   