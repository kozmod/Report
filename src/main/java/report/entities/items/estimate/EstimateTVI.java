
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
import report.entities.items.Item;


public class EstimateTVI extends Item {
    private final IntegerProperty tableType;
    private final BooleanProperty inKS;

    //Constructor
    public EstimateTVI(
            long id,
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
            String bildingPart,
            int tableType,
            boolean inKS
    ) {
        super(id, dateCreate, siteNumber, typeHome, contractor, JM_name, JobOrMat, BindedJob, value, unit, price_one, price_sum, bildingPart);
        this.tableType = new SimpleIntegerProperty(tableType);
        this.inKS = new SimpleBooleanProperty(inKS);
    }

    // Clone CONSTRUCTOR implementation
    @Override
    public EstimateTVI getClone() {
        EstimateTVI clone = new EstimateTVI(
                super.getId(),
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
                super.getBuildingPart(),
                this.getTableType(),
                this.getInKS()
        );
        return clone;
    }

    //Builder
    public static class Builder {
        private long id;
        private Timestamp dateCreate;
        private StringProperty siteNumber;
        private StringProperty typeHome;
        private StringProperty contractor;
        private StringProperty bildingPart;
        private StringProperty JM_name;
        private StringProperty JobOrMat;
        private StringProperty BindedJob;
        private DoubleProperty value;
        private StringProperty unit;
        private DoubleProperty price_one;
        private DoubleProperty price_sum;
        private IntegerProperty tableType;
        private BooleanProperty inKS;


        public Builder setId(long value_inp) {
            this.id = value_inp;
            return this;
        }

        public Builder setSiteNumber(String value_inp) {
            this.siteNumber = new SimpleStringProperty(value_inp);
            return this;
        }

        public Builder setTypeHome(String value_inp) {
            this.typeHome = new SimpleStringProperty(value_inp);
            return this;
        }

        public Builder setContractor(String value_inp) {
            this.contractor = new SimpleStringProperty(value_inp);
            return this;
        }

        public Builder setJM_name(String value_inp) {
            this.JM_name = new SimpleStringProperty(value_inp);
            return this;
        }

        public Builder setJobOrMat(String value_inp) {
            this.JobOrMat = new SimpleStringProperty(value_inp);
            return this;
        }

        public Builder setBindedJob(String value_inp) {
            this.BindedJob = new SimpleStringProperty(value_inp);
            return this;
        }

        public Builder setValue(Double value_inp) {
            this.value = new SimpleDoubleProperty(value_inp);
            return this;
        }

        public Builder setUnit(String value_inp) {
            this.unit = new SimpleStringProperty(value_inp);
            return this;
        }

        public Builder setPrice_one(Double value_inp) {
            this.price_one = new SimpleDoubleProperty(value_inp);
            return this;
        }

        public Builder setPrice_sum(Double value_inp) {
            this.price_sum = new SimpleDoubleProperty(value_inp);
            return this;
        }

        public Builder setBildingPart(String value_inp) {
            this.bildingPart = new SimpleStringProperty(value_inp);
            return this;
        }

        public Builder setTableType(int value_inp) {
            this.tableType = new SimpleIntegerProperty(value_inp);
            return this;
        }

        public Builder setDate(Date value_inp) {
            this.dateCreate = dateCreate;
            return this;
        }

        public Builder setInKS(boolean value_inp) {
            this.inKS = new SimpleBooleanProperty(value_inp);
            return this;
        }

        public EstimateTVI build() {
            return new EstimateTVI(
                    id,
                    dateCreate,
                    siteNumber.getValue(),
                    typeHome.getValue(),
                    contractor.getValue(),
                    JM_name.getValue(),
                    JobOrMat.getValue(),
                    BindedJob.getValue(),
                    value.getValue(),
                    unit.getValue(),
                    price_one.getValue(),
                    price_sum.getValue(),
                    bildingPart.getValue(),
                    tableType.getValue(),
                    inKS.getValue()

            );

        }
    }
//Getter //Setter ===============================================================================

    public int getTableType() {
        return tableType.get();
    }

    public void setPrice_one(int value_inp) {
        tableType.set(value_inp);
    }

    public boolean getInKS() {
        return inKS.get();
    }

    //    public void    setInKS(boolean value_inp) {inKS.set(value_inp);}
//    public void    setNotInKS() {inKS.set(false);}
//    public void    setInKS()    {inKS.set(true);}
    public void setInKS(boolean value_inp) {
        inKS.set(value_inp);
    }


    //Equakls AND hashCode ==========================================================================
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 2 * hash + (super.quantity != null ? super.quantity.hashCode() >>> 3 : 0);
        hash = 2 * hash + (super.priceOne != null ? super.priceOne.hashCode() >>> 3 : 0);
        hash = 2 * hash + (super.priceSum != null ? super.priceSum.hashCode() >>> 3 : 0);
        hash = 2 * hash + (this.tableType != null ? this.tableType.hashCode() >>> 3 : 0);

        return hash;
    }

//    @Override
//    public boolean equals(Object obj) {
//
//        if (obj == null) {
//            return false;
//        }
//        if (!Item.class.isAssignableFrom(obj.getClass())) {
//            return false;
//        }
//        final EstimateTVI other = (EstimateTVI) obj;
//        if ((super.siteNumber.get() == null) ? (other.siteNumber.getQuantity() != null) : !super.siteNumber.get().equals(other.siteNumber.get())) {
//            return false;
//        }
//        if ((super.typeHome.get() == null) ? (other.typeHome.getQuantity() != null) : !super.typeHome.get().equals(other.typeHome.get())) {
//            return false;
//        }
//        if ((super.contractor.get() == null) ? (other.contractor.getQuantity() != null) : !super.contractor.get().equals(other.contractor.get())) {
//            return false;
//        }
//        if ((super.JM_name.get() == null) ? (other.JM_name.getQuantity() != null) : !super.JM_name.get().equals(other.JM_name.get())) {
//            return false;
//        }
//        if ((super.JobOrMat.get() == null) ? (other.JobOrMat.get() != null) : !super.JobOrMat.get().equals(other.JobOrMat.get())) {
//            return false;
//        }
//        if ((super.bindJob.get() == null) ? (other.bindJob.get() != null) : !super.bindJob.get().equals(other.bindJob.get())) {
//            return false;
//        }
//        if (super.quantity.get() != other.quantity.get()) {
//            return false;
//        }
//        if ((super.unit.get() == null) ? (other.unit.get() != null) : !super.unit.get().equals(other.unit.get())) {
//            return false;
//        }
//        if (super.priceOne.get() != other.priceOne.get()) {
//            return false;
//        }
//        if (super.priceSum.get() != other.priceSum.get()) {
//            return false;
//        }
//        if ((super.buildingPart.get() == null) ? (other.buildingPart.getQuantity() != null) : !super.buildingPart.get().equals(other.buildingPart.get())) {
//            return false;
//        }
//    return true;
//    }

    /**
     * Extractor to observe changes in "Property" fields.
     *
     * @return extractor
     */
    public static Callback<Item, Observable[]> extractor() {
        return (Item p) -> new Observable[]{p.quantityProperty(), p.priceOneProperty()};
    }


}
