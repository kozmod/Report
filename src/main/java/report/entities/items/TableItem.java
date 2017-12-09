
package report.entities.items;

import java.sql.Timestamp;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


//Estimate Tabble Items to ObsList
public abstract class TableItem implements TableClone {
    //new value
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
    protected final DoubleProperty value;
    protected final StringProperty unit;
    protected final DoubleProperty price_one;
    protected final DoubleProperty price_sum;


    public TableItem(
            long id,
            Timestamp dateCreate,
            String siteNumber,
            String typeHome,
            String contractor,
            String JM_name,
            String JobOrMat,
            String bindJob,
            Double  value,
            String unit,
            Double  price_one,
            Double  price_sum,
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
        this.value         = new SimpleDoubleProperty(value);
        this.unit          = new SimpleStringProperty(unit);
        this.price_one     = new SimpleDoubleProperty(price_one);
        this.price_sum     = new SimpleDoubleProperty(price_sum);
        this.buildingPart = new SimpleStringProperty(buildingPart);

    }

//        Clone CONSTRUCTOR implementation
//        @Override
//        public  TableClone getClone() {
//            TableClone clone = new TableItem(
//                                    this.getSiteNumber(),
//                                    this.getTypeHome(),
//                                    this.getContractor(),
//                                    this.getJM_name(),
//                                    this.getJobOrMat(),
//                                    this.getBindJob(),
//                                    this.getValue(),
//                                    this.getUnit(),
//                                    this.getPrice_one(),
//                                    this.getPrice_sum(),
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
//        private  FloatProperty  value;                            
//        private  StringProperty unit;                            
//        private  FloatProperty  price_one;                            
//        private  FloatProperty  price_sum;
//        
//    
//            public Builder setSiteNumber(String value_inp)  {this.siteNumber    = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setTypeHome(String value_inp)    {this.typeHome      = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setContractor(String value_inp)  {this.contractor    = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setJM_name(String value_inp)     {this.JM_name       = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setJobOrMat(String value_inp)    {this.JobOrMat      = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setBindJob(String value_inp)   {this.bindJob     = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setValue(float value_inp)        {this.value         = new SimpleFloatProperty (value_inp);  return this;}      
//            public Builder setUnit(String value_inp)        {this.unit          = new SimpleStringProperty(value_inp);  return this;}
//            public Builder setPrice_one (float value_inp)   {this.price_one     = new SimpleFloatProperty (value_inp);  return this;}
//            public Builder setPrice_sum(float value_inp)    {this.price_sum     = new SimpleFloatProperty (value_inp);  return this;}
//            public Builder setBuildingPart(String value_inp) {this.buildingPart   = new SimpleStringProperty(value_inp);  return this;}
//            
////            public TableItem build() {
////                return new TableItem(
////                      siteNumber .getValue(), 
////                      typeHome   .getValue(),   
////                      contractor .getValue(), 
////                      JM_name    .getValue(),    
////                      JobOrMat   .getValue(),
////                      bindJob  .getValue(),
////                      value      .getValue(), 
////                      unit       .getValue(), 
////                      price_one  .getValue(),
////                      price_sum  .getValue(),
////                      buildingPart.getValue()
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
    public void   setJobOrMat(String value_inp) {JobOrMat.set(value_inp);}
    public StringProperty jobOrMatProperty() {return JobOrMat;}

//    public String getBindJob() {return bindJob.get();}
//    public void   setBindJob(String value_inp) {bindJob.set(value_inp);}
//    public StringProperty bindJobProperty() {return bindJob; }


    public String getBindJob() {
        return bindJob.get();
    }
    public void setBindJob(String bindJob) {
        this.bindJob.set(bindJob);
    }
    public StringProperty bindJobProperty() {
        return bindJob;
    }

    public Double getValue()                {return value.get();}
    public void  setValue(double value_inp) {value.set(value_inp);}
    public DoubleProperty valueProperty() {return value;}

    public String getUnit() {return unit.get();}
    public void   setUnit(String value_inp) {unit.set(value_inp);}
    public StringProperty unitProperty() {return unit;}
    public Double getPrice_one() {return price_one.get();}
    public void  setPrice_one (double value_inp) {price_one.set(value_inp);}
    public DoubleProperty price_oneProperty() {return price_one;}

    public Double getPrice_sum() {return price_sum.get();}
    public void  setPrice_sum (double value_inp) {price_sum.set(value_inp);}
    public DoubleProperty price_sumProperty() {return price_sum;}

    public String getBuildingPart() {return buildingPart.get();}
    public void setBuildingPart(String value_inp) {
        buildingPart.set(value_inp);}
    public StringProperty buildingPartProperty() {return buildingPart;}
    public int  getDel() {return del;}
    public void setDel(int del) {this.del = del;}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.value     != null ? this.value.hashCode()     : 0);
        hash = 23 * hash + (this.price_one != null ? this.price_one.hashCode() : 0);
        hash = 23 * hash + (this.price_sum != null ? this.price_sum.hashCode() : 0);
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
        final TableItem other = (TableItem) obj;
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
        if (this.value.get() != other.value.get()) {
            return false;
        }
        if ((this.unit.get() == null) ? (other.unit.get() != null) : !this.unit.get().equals(other.unit.get())) {
            return false;
        }
        if (this.price_one.get() != other.price_one.get()) {
            return false;
        }
        if (this.price_sum.get() != other.price_sum.get()) {
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
        if (!TableItem.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final TableItem other = (TableItem) obj;
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
//   public static Callback<TableItem, Observable[]> extractor() {
//        return (TableItem p) -> new Observable[]{p.valueProperty(), p.price_oneProperty()};
//    }


}
    
   