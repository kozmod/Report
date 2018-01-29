package report.entities.items.counterparties.commonReq;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import report.entities.items.Clone;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CountAgentCommonReq implements Observable,Clone {
    private long id;
    private BooleanProperty isIP;
    private ObjectProperty<BigInteger> OGRN;
    private ObjectProperty<LocalDate> dateOGRN;
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
    public CountAgentCommonReq(
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
        this.isIP = new SimpleBooleanProperty(this,"sql",isIP);
        this.OGRN = new SimpleObjectProperty<>(this,"sql",OGRN);
        this.dateOGRN = new  SimpleObjectProperty<>(this,"sql",LocalDate.ofEpochDay(dateOGRN));
        this.inn = new SimpleObjectProperty<>(this,"sql",inn);
        this.kpp =  new SimpleObjectProperty<>(this,"sql",kpp);
        this.addressLow = new SimpleStringProperty(this,"sql",addressLow);
        this.addressFact = new SimpleStringProperty(this,"sql",addressFact);
        this.addressPost = new SimpleStringProperty(this,"sql",addressPost);

    }
    /***************************************************************************
     *                                                                         *
     * Clone Constructors                                                      *
     *                                                                         *
     **************************************************************************/
    public CountAgentCommonReq(CountAgentCommonReq source) {
        this.id = source.id;
        this.isIP = new SimpleBooleanProperty(source.isIP.getValue());
        this.OGRN = new SimpleObjectProperty<>(source.OGRN.getValue());
        this.dateOGRN = new  SimpleObjectProperty<>(source.dateOGRN.getValue());
        this.inn = new SimpleObjectProperty<>(source.inn.getValue());
        this.kpp =  new SimpleObjectProperty<>(source.kpp.getValue());
        this.addressLow = new SimpleStringProperty(source.addressLow.getValue());
        this.addressFact = new SimpleStringProperty(source.addressFact.getValue());
        this.addressPost = new SimpleStringProperty(source.addressPost.getValue());
    }
    /***************************************************************************
     *                                                                         *
     * Override - Observable                                                   *
     *                                                                         *
     **************************************************************************/
    @Override
    public void addListener(InvalidationListener listener) {
        this.isIP.addListener(listener);
        this.OGRN.addListener(listener);
        this.dateOGRN.addListener(listener);
        this.inn.addListener(listener);
        this.kpp.addListener(listener);
        this.addressLow.addListener(listener);
        this.addressFact.addListener(listener);
        this.addressPost.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        this.isIP.removeListener(listener);
        this.OGRN.removeListener(listener);
        this.dateOGRN.removeListener(listener);
        this.inn.removeListener(listener);
        this.kpp.removeListener(listener);
        this.addressLow.removeListener(listener);
        this.addressFact.removeListener(listener);
        this.addressPost.removeListener(listener);
    }

    /***************************************************************************
     *                                                                         *
     * Getters\Setters                                                         *
     *                                                                         *
     **************************************************************************/

    @Override
    public CountAgentCommonReq getClone() {
        return null;
    }

    public long getId() {
        return 0;
    }

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

    public LocalDate getDateOGRN() {
        return dateOGRN.get();
    }

    public ObjectProperty<LocalDate> dateOGRNProperty() {
        return dateOGRN;
    }

    public void setDateOGRN(LocalDate dateOGRN) {
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
     * @return Callback<VariableTIV, Observable[]>
     */
    public static Callback<CountAgentCommonReq, Observable[]> extractor() {
        return (CountAgentCommonReq p) -> new Observable[]{
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
