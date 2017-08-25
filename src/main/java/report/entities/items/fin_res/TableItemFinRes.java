
package report.entities.items.fin_res;


import javafx.beans.property.*;

//Fin Result TableWrapper Item to ObsList
    public  class TableItemFinRes {                                             
 
        private final StringProperty  siteNumber;                          
        private final StringProperty  contractor; 
        private final StringProperty  NContract; 
        private final IntegerProperty dateContract;                          
        private final IntegerProperty finishBuilding;                          
        private final DoubleProperty  smetCost;
        private final DoubleProperty  costHouse;
        private final DoubleProperty  saleHouse;
        private final DoubleProperty  trueCost;
        private final DoubleProperty  profit;
        
        public TableItemFinRes(
                                String siteNumber, 
                                String contractor, 
                                String NContract,
                                int    dateContract,
                                int    finishBuilding,
                                double  smetCost,
                                double  costHouse,
                                double  saleHouse,
                                double  trueCost,
                                double  profit
                                ) {
            this.siteNumber     = new SimpleStringProperty(siteNumber);
            this.contractor     = new SimpleStringProperty(contractor);
            this.NContract      = new SimpleStringProperty(NContract);
            this.dateContract   = new SimpleIntegerProperty(dateContract);
            this.finishBuilding = new SimpleIntegerProperty(finishBuilding);
            this.smetCost       = new SimpleDoubleProperty(smetCost);
            this.costHouse      = new SimpleDoubleProperty(costHouse);
            this.saleHouse      = new SimpleDoubleProperty(saleHouse);
            this.trueCost       = new SimpleDoubleProperty(trueCost);
            this.profit         = new SimpleDoubleProperty(profit);
        }

        public String getSiteNumber() {return siteNumber.get();}
        public void   setSiteNumber(String value_inp) {siteNumber.set(value_inp);}
        
        public String getContractor() {return contractor.get();} 
        public void   setContractor(String value_inp) {contractor.set(value_inp);}
        
        public String getNContract() {return NContract.get();} 
        public void   setNContract(String value_inp) {NContract.set(value_inp);}
        
        public int  getDateContract() {return dateContract.get();}
        public void setDateContract(int value_inp) {dateContract.set(value_inp);}
        
        public int  getFinishBuilding(){return finishBuilding.get();}
        public void setFinishBuilding(int value_inp){finishBuilding.set(value_inp);}

        public Double getSmetCost() {return smetCost.get();}
        public void  setSmetCost(double value_inp) {smetCost.set(value_inp);}
        
        public Double getCostHouse() {return costHouse.get();}
        public void  setCostHouse(double value_inp) {costHouse.set(value_inp);}
        
        public Double getSaleHouse() {return saleHouse.get();}
        public void  setSaleHouse(double value_inp) {saleHouse.set(value_inp);}
        
        public Double getTrueCost() {return trueCost.get();}
        public void  setTrueCost(double value_inp) {trueCost.set(value_inp);}
        
        public Double getProfit() {return profit.get();}
        public void  setProfit(double value_inp) {profit.set(value_inp);}
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 77 * hash + (this.dateContract       != null ? this.dateContract.hashCode()   : 0);
            hash = 77 * hash + (this.finishBuilding     != null ? this.finishBuilding.hashCode() : 0);
            hash = 77 * hash + (this.smetCost           != null ? this.smetCost.hashCode()       : 0);
            hash = 77 * hash + (this.costHouse          != null ? this.costHouse.hashCode()      : 0);
            hash = 77 * hash + (this.saleHouse          != null ? this.saleHouse.hashCode()      : 0);
            hash = 77 * hash + (this.trueCost           != null ? this.trueCost.hashCode()       : 0);
            hash = 77 * hash + (this.profit             != null ? this.profit.hashCode()         : 0);
            
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            
            if (obj == null) {
                return false;
            }
            if (!TableItemFinRes.class.isAssignableFrom(obj.getClass())) {
                return false;
            }
            final TableItemFinRes other = (TableItemFinRes) obj;
            if (this.dateContract.get() != other.dateContract.get()) {
                return false;
            }
            if (this.finishBuilding.get() != other.finishBuilding.get()) {
                return false;
            }
            if ((this.siteNumber.get() == null) ? (other.siteNumber.get() != null) : !this.siteNumber.get().equals(other.siteNumber.get())) {
                return false;
            }
            if ((this.contractor.get() == null) ? (other.contractor.get() != null) : !this.contractor.get().equals(other.contractor.get())) {
                return false;
            }
            if ((this.NContract.get() == null) ? (other.NContract.get() != null) : !this.NContract.get().equals(other.NContract.get())) {
                return false;
            }
            if (this.smetCost.get() != other.smetCost.get()) {
                return false;
            }
            if (this.trueCost.get() != other.trueCost.get()) {
                return false;
            }
            if (this.costHouse.get() != other.costHouse.get()) {
                return false;
            }
            if (this.profit.get() != other.profit.get()) {
                return false;
            }
            
        return true;
        }
    } 