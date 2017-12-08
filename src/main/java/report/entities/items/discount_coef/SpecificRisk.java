package report.entities.items.discount_coef;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.math.BigDecimal;

/**
 * <b>Описание :</b>
 * <br>Ключевая фигура: качество и глубина управления, в %
 * <br>Размер предприятия, в %
 * <br>Финансовая стр-ра: источники финансирования компании, в %
 * <br>Диверсификация клиентуры, в %
 * <br>Прочие риски, связанные с деятельностью компании, в %
 */
public class SpecificRisk {
    interface SQL{
        String KEY_CHAR   = "KeyChar";
        String ENTERPRISE_SIZE = "EntSize";
        String FIN_STRUCT = "FinStruct";
        String CLIENT_DIV  = "ClientDiv";
        String OTHER_RISKS  = "OtherRisks";

    }



    static final String KEY_CHAR   = "Ключевая фигура: качество и глубина управления, в %";
    static final String ENTERPRISE_SIZE = "Размер предприятия, в %";
    static final String FIN_STRUCT = "Финансовая стр-ра: источники финансирования компании, в %";
    static final String CLIENT_DIV  = "Диверсификация клиентуры, в %";
    static final String OTHER_RISKS  = "Прочие риски, связанные с деятельностью компании, в %";


    private long id;
    private final DoubleProperty keyChar;
    private final DoubleProperty entSize;
    private final DoubleProperty finStruct;
    private final DoubleProperty clientDiv;
    private final DoubleProperty otherRisks;
    //compute
    private final DoubleProperty value;
    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/

    public SpecificRisk(long id,double keyChar, double entSize, double finStruct, double clientDiv, double otherRisks) {
        this.id = id;
        this.keyChar    = new SimpleDoubleProperty(keyChar);
        this.entSize    = new SimpleDoubleProperty(entSize);
        this.finStruct  = new SimpleDoubleProperty(finStruct);
        this.clientDiv  = new SimpleDoubleProperty(clientDiv);
        this.otherRisks = new SimpleDoubleProperty(otherRisks);
        this.value = new SimpleDoubleProperty();
    }
    public SpecificRisk(SpecificRisk sr) {
        this.id = sr.id;
        this.keyChar    = new SimpleDoubleProperty(sr.keyChar.getValue());
        this.entSize    = new SimpleDoubleProperty(sr.entSize.getValue());
        this.finStruct  = new SimpleDoubleProperty(sr.finStruct.getValue());
        this.clientDiv  = new SimpleDoubleProperty(sr.clientDiv.getValue());
        this.otherRisks = new SimpleDoubleProperty(sr.otherRisks.getValue());
        this.value = new SimpleDoubleProperty();
    }
    /***************************************************************************
     *                                                                         *
     * Methods                                                                  *
     *                                                                         *
     **************************************************************************/
    public void compute(){
        BigDecimal bdValue = new BigDecimal(keyChar.get())
                .add(new BigDecimal(entSize.get()))
                .add(new BigDecimal(finStruct.get()))
                .add(new BigDecimal(clientDiv.get()))
                .add(new BigDecimal(otherRisks.get()));
//        double compute = (keyChar.get()100 + entSize.get()/100 +finStruct.get()/100 +clientDiv.get()/100 +otherRisks.get()/100)
//        return bdValue.doubleValue();
        value.set(bdValue.doubleValue());
    }

    /***************************************************************************
     *                                                                         *
     * GETTER                                                                  *
     *                                                                         *
     **************************************************************************/
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public double getKeyChar() { return keyChar.get(); }
    public DoubleProperty keyCharProperty() {  return keyChar;}
    public void setKeyChar(double keyChar) { this.keyChar.set(keyChar); }

    public double getEntSize() {return entSize.get(); }
    public DoubleProperty entSizeProperty() { return entSize;}
    public void setEntSize(double entSize) { this.entSize.set(entSize);}

    public double getFinStruct() { return finStruct.get();}
    public DoubleProperty finStructProperty() { return finStruct; }
    public void setFinStruct(double finStruct) {this.finStruct.set(finStruct); }

    public double getClientDiv() { return clientDiv.get();}
    public DoubleProperty clientDivProperty() {return clientDiv; }
    public void setClientDiv(double clientDiv) {this.clientDiv.set(clientDiv); }

    public double getOtherRisks() {return otherRisks.get();}
    public DoubleProperty otherRisksProperty() {return otherRisks; }
    public void setOtherRisks(double otherRisks) { this.otherRisks.set(otherRisks); }


    public DoubleProperty valueProperty() {
        return value;
    }
}
