package report.entities.items.counterparties;

import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;


public class CountBankReq implements TableClone<CountBankReq> {
    private long id;
    private StringProperty accNumber;
    private StringProperty bankName;
    private LongProperty   bic;
    private StringProperty corAcc;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public CountBankReq(long id, String accNumber, String bankName, long bic, String corAcc) {
        this.id = id;
        this.accNumber = new SimpleStringProperty(accNumber);
        this.bankName = new SimpleStringProperty(bankName);
        this.bic = new SimpleLongProperty(bic);
        this.corAcc = new SimpleStringProperty(corAcc);
    }
    /***************************************************************************
     *                                                                         *
     * Clone Constructors                                                      *
     *                                                                         *
     **************************************************************************/
    public CountBankReq(CountBankReq source) {
        this.id = id;
        this.accNumber = new SimpleStringProperty(source.accNumber.getValue());
        this.bankName = new SimpleStringProperty(source.bankName.getValue());
        this.bic = new SimpleLongProperty(source.bic.getValue());
        this.corAcc = new SimpleStringProperty(source.corAcc.getValue());
    }
    /***************************************************************************
     *                                                                         *
     * Getters\Setters                                                         *
     *                                                                         *
     **************************************************************************/
    @Override
    public CountBankReq getClone() {
        return new CountBankReq(this);
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    public String getAccNumber() {
        return accNumber.get();
    }

    public StringProperty accNumberProperty() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber.set(accNumber);
    }

    public String getBankName() {
        return bankName.get();
    }

    public StringProperty bankNameProperty() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName.set(bankName);
    }

    public long getBic() {
        return bic.get();
    }

    public LongProperty bicProperty() {
        return bic;
    }

    public void setBic(long bic) {
        this.bic.set(bic);
    }

    public String getCorAcc() {
        return corAcc.get();
    }

    public StringProperty corAccProperty() {
        return corAcc;
    }

    public void setCorAcc(String corAcc) {
        this.corAcc.set(corAcc);
    }

    /**
     *Extractor to observe changes in "Property" fields.
     *
     * @return Callback<TableItemCountName, Observable[]>
     */
    public static Callback<CountBankReq, Observable[]> extractor() {
        return (CountBankReq p) -> new Observable[]{
                p.accNumberProperty(),
                p.bankNameProperty(),
                p.bicProperty(),
                p.corAccProperty()};
    }
}
