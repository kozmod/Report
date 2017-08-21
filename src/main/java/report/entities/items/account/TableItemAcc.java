
package report.entities.items.account;



import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//Account Table Item to ObsList
    public class TableItemAcc {                                             
 
        private final IntegerProperty date;                          
        private final IntegerProperty num;                          
        private final StringProperty  ITN_Client;                          
        private final StringProperty  name_Client;                        
        private final StringProperty  accNum_Client;                            
        private final StringProperty  BIC_Cor;                            
        private final StringProperty  accNum_Cor;                            
        private final StringProperty  name_Cor;                            
        private final IntegerProperty VO;    
        private final StringProperty  description;
        private final DoubleProperty   deb;
        private final DoubleProperty   cred;

        private final DoubleProperty   outgoingRest;


        
        public TableItemAcc(int    date,
                            int    num, 
                            String ITN_Client, 
                            String name_Client, 
                            String accNum_Client, 
                            String BIC_Cor, 
                            String accNum_Cor, 
                            String name_Cor, 
                            int    VO,
                            String description,
                            double  deb,
                            double  cred,
                            double outgoingRest
        ) {
            this.date          = new SimpleIntegerProperty(date);
            this.num           = new SimpleIntegerProperty(num);
            this.ITN_Client    = new SimpleStringProperty(ITN_Client);
            this.name_Client   = new SimpleStringProperty(name_Client);
            this.accNum_Client = new SimpleStringProperty(accNum_Client);
            this.BIC_Cor       = new SimpleStringProperty(BIC_Cor);
            this.accNum_Cor    = new SimpleStringProperty(accNum_Cor);
            this.name_Cor      = new SimpleStringProperty(name_Cor);
            this.VO            = new SimpleIntegerProperty(VO);
            this.description   = new SimpleStringProperty(description);
            this.deb           = new SimpleDoubleProperty(deb);
            this.cred          = new SimpleDoubleProperty(cred);

            this.outgoingRest  = new SimpleDoubleProperty(outgoingRest);
        }

        public int  getDate() {return date.get();}
        public void setDate(int value_inp) {date.set(value_inp);}
        
        public int  getNum(){return num.get();}
        public void setNum(int value_inp){num.set(value_inp);}
      
        public String getITN_Client() {return ITN_Client.get();}
        public void   setITN_Client(String value_inp) {ITN_Client.set(value_inp);}
        
        public String getName_Client() {return name_Client.get();} 
        public void   setName_Client(String value_inp) {name_Client.set(value_inp);}
        
        public String getAccNum_Client() {return accNum_Client.get();} 
        public void   setAccNum_Client(String value_inp) {accNum_Client.set(value_inp);}
        
        public String getBIC_Cor() {return BIC_Cor.get();} 
        public void   setBIC_Cor(String value_inp) {BIC_Cor.set(value_inp);}
        
        public String getAccNum_Cor() {return accNum_Cor.get();} 
        public void   setAccNum_Cor(String value_inp) {accNum_Cor.set(value_inp);}
        
        public String getName_Cor() {return name_Cor.get();} 
        public void   setName_Cor(String value_inp) {name_Cor.set(value_inp);}
        
        public String getDescription() {return description.get();} 
        public void   setDescription(String value_inp) {description.set(value_inp);}
        
        public int  getVO(){return VO.get();}        
        public void setVO(int value_inp){VO.set(value_inp);}

        public Double getDeb() {return deb.get();} 
        public void  setDeb (float value_inp) {deb.set(value_inp);}
        
        public Double getCred() {return cred.get();} 
        public void  setCred(float value_inp) {cred.set(value_inp);}

        public Double         getOutgoingRest() {return outgoingRest.get();}
//        public DoubleProperty outgoingRestProperty() {return outgoingRest;}
        public void           setOutgoingRest(double outgoingRest) {this.outgoingRest.set(outgoingRest);}

    @Override
        public int hashCode() {
            int hash = 7;
            hash = 7 * hash + (this.date          != null ? this.date.intValue()                    : 0);
            hash = 7 * hash + (this.num           != null ? this.num.intValue()                     : 0);
            hash = 7 * hash + (this.VO            != null ? this.VO.intValue()                      : 0);
            hash =     hash + (this.deb           != null ? this.deb.getValue().hashCode()          : 0);
            hash =     hash + (this.cred          != null ? this.cred.getValue().hashCode()         : 0);
            hash =     hash + (this.outgoingRest  != null ? this.outgoingRest.getValue().hashCode() : 0);

            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            
            if (obj == null) {
                return false;
            }
            if (!TableItemAcc.class.isAssignableFrom(obj.getClass())) {
                return false;
            }
            final TableItemAcc other = (TableItemAcc) obj;
            if (this.date.get() != other.date.get()) {
                return false;
            }
            if (this.num.get() != other.num.get()) {
                return false;
            }
            if ((this.ITN_Client.get() == null) ? (other.ITN_Client.get() != null) : !this.ITN_Client.get().equals(other.ITN_Client.get())) {
                return false;
            }
            if ((this.name_Client.get() == null) ? (other.name_Client.get() != null) : !this.name_Client.get().equals(other.name_Client.get())) {
                return false;
            }
            if ((this.accNum_Client.get() == null) ? (other.accNum_Client.get() != null) : !this.accNum_Client.get().equals(other.accNum_Client.get())) {
                return false;
            }
            if ((this.BIC_Cor.get() == null) ? (other.BIC_Cor.get() != null) : !this.BIC_Cor.get().equals(other.BIC_Cor.get())) {
                return false;
            }
            if ((this.accNum_Cor.get() == null) ? (other.accNum_Cor.get() != null) : !this.accNum_Cor.get().equals(other.accNum_Cor.get())) {
                return false;
            }
            if ((this.name_Cor.get() == null) ? (other.name_Cor.get() != null) : !this.name_Cor.get().equals(other.name_Cor.get())) {
                return false;
            }
            if (this.VO.get() != other.VO.get()) {
                return false;
            }
            if ((this.description.get() == null) ? (other.description.get() != null) : !this.description.get().equals(other.description.get())) {
                return false;
            }
            if (this.deb.get() != other.deb.get()) {
                return false;
            }
            if (this.cred.get() != other.cred.get()) {
                return false;
            }
            if (this.outgoingRest.get() != other.outgoingRest.get()) {
                return false;
            }
            
        return true;
        }
    } 
    