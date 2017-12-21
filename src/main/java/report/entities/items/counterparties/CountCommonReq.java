package report.entities.items.counterparties;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;
import report.entities.items.TableClone;

import java.math.BigInteger;

public class CountCommonReq  implements TableClone<CountCommonReq> {
    private long id;
    private BooleanProperty isIP;
    private ObjectProperty<BigInteger> OGRN;
    private IntegerProperty dateOGRN;
    private ObjectProperty<BigInteger> inn;
    private ObjectProperty<BigInteger> kpp;
    private StringProperty addressLow;
    private StringProperty addressFact;
    private StringProperty addressPost;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public CountCommonReq(
            long id,
            boolean isIP,
            BigInteger  OGRN,
            int dateOGRN,
            BigInteger inn,
            BigInteger kpp,
            String addressLow,
            String addressFact,
            String addressPost) {
        this.id = id;
        this.isIP = new SimpleBooleanProperty(isIP);
        this.OGRN = new SimpleObjectProperty<>(OGRN);
        this.dateOGRN = new  SimpleIntegerProperty(dateOGRN);
        this.inn = new SimpleObjectProperty<>(inn);
        this.kpp =  new SimpleObjectProperty<>(kpp);
        this.addressLow = new SimpleStringProperty(addressLow);
        this.addressFact = new SimpleStringProperty(addressFact);
        this.addressPost = new SimpleStringProperty(addressPost);
    }
    /***************************************************************************
     *                                                                         *
     * Clone Constructors                                                      *
     *                                                                         *
     **************************************************************************/
    public CountCommonReq(CountCommonReq source) {
        this.id = source.id;
        this.isIP = new SimpleBooleanProperty(source.isIP.getValue());
        this.OGRN = new SimpleObjectProperty<>(source.OGRN.getValue());
        this.dateOGRN = new  SimpleIntegerProperty(source.dateOGRN.getValue());
        this.inn = new SimpleObjectProperty<>(source.inn.getValue());
        this.kpp =  new SimpleObjectProperty<>(source.kpp.getValue());
        this.addressLow = new SimpleStringProperty(source.addressLow.getValue());
        this.addressFact = new SimpleStringProperty(source.addressFact.getValue());
        this.addressPost = new SimpleStringProperty(source.addressPost.getValue());
    }

    /***************************************************************************
     *                                                                         *
     * Getters\Setters                                                         *
     *                                                                         *
     **************************************************************************/

    @Override
    public CountCommonReq getClone() {
        return null;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    public boolean isIsIP() {
        return isIP.get();
    }

    public BooleanProperty isIPProperty() {
        return isIP;
    }

    public void setIsIP(boolean isIP) {
        this.isIP.set(isIP);
    }

    public BigInteger getOGRN() {
        return OGRN.get();
    }

    public ObjectProperty<BigInteger> OGRNProperty() {
        return OGRN;
    }

    public void setOGRN(BigInteger OGRN) {
        this.OGRN.set(OGRN);
    }

    public int getDateOGRN() {
        return dateOGRN.get();
    }

    public IntegerProperty dateOGRNProperty() {
        return dateOGRN;
    }

    public void setDateOGRN(int dateOGRN) {
        this.dateOGRN.set(dateOGRN);
    }

    public BigInteger getInn() {
        return inn.get();
    }

    public ObjectProperty<BigInteger> innProperty() {
        return inn;
    }

    public void setInn(BigInteger inn) {
        this.inn.set(inn);
    }

    public BigInteger getKpp() {
        return kpp.get();
    }

    public ObjectProperty<BigInteger> kppProperty() {
        return kpp;
    }

    public void setKpp(BigInteger kpp) {
        this.kpp.set(kpp);
    }

    public String getAddressLow() {
        return addressLow.get();
    }

    public StringProperty addressLowProperty() {
        return addressLow;
    }

    public void setAddressLow(String addressLow) {
        this.addressLow.set(addressLow);
    }

    public String getAddressFact() {
        return addressFact.get();
    }

    public StringProperty addressFactProperty() {
        return addressFact;
    }

    public void setAddressFact(String addressFact) {
        this.addressFact.set(addressFact);
    }

    public String getAddressPost() {
        return addressPost.get();
    }

    public StringProperty addressPostProperty() {
        return addressPost;
    }

    public void setAddressPost(String addressPost) {
        this.addressPost.set(addressPost);
    }
    /**
     *Extractor to observe changes in "Property" fields.
     *
     * @return Callback<TableItemVariable, Observable[]>
     */
    public static Callback<CountCommonReq, Observable[]> extractor() {
        return (CountCommonReq p) -> new Observable[]{
                p.isIPProperty(),
                p.dateOGRNProperty(),
                p.innProperty(),
                p.kppProperty(),
                p.addressLowProperty(),
                p.addressFactProperty(),
                p.addressPostProperty()
        };


    }
}
