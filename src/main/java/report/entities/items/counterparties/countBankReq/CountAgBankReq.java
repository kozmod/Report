package report.entities.items.counterparties.countBankReq;

import javafx.beans.Observable;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;


public class CountAgBankReq implements TableClone<CountAgBankReq> {
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
    public CountAgBankReq(long id, String accNumber, String bankName, long bic, String corAcc) {
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
    public CountAgBankReq(CountAgBankReq source) {
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
    public CountAgBankReq getClone() {
        return new CountAgBankReq(this);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) { this.id = id;

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
    /***************************************************************************
     *                                                                         *
     * Equals\HashCode                                                         *
     *                                                                         *
     **************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountAgBankReq that = (CountAgBankReq) o;

//        if (id != that.id) return false;
        if (accNumber != null ? !accNumber.getValue().equals(that.accNumber.getValue()) : that.accNumber.getValue() != null) return false;
        if (bankName != null ? !bankName.getValue().equals(that.bankName.getValue()) : that.bankName.getValue() != null) return false;
        if (bic != null ? !bic.getValue().equals(that.bic.getValue()) : that.bic.getValue() != null) return false;
        return corAcc != null ? corAcc.equals(that.corAcc.getValue()) : that.corAcc.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = 11;
        result = 31 * result + (accNumber != null ? accNumber.getValue().hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.getValue().hashCode() : 0);
        result = 31 * result + (bic != null ? bic.getValue().hashCode() : 0);
        result = 31 * result + (corAcc != null ? corAcc.getValue().hashCode() : 0);
        return result;
    }

    /**
     *Extractor to observe changes in "Property" fields.
     *
     * @return Callback<CountAgentNameTVI, Observable[]>
     */
    public static Callback<CountAgBankReq, Observable[]> extractor() {
        return (CountAgBankReq p) -> new Observable[]{
                p.accNumberProperty(),
                p.bankNameProperty(),
                p.bicProperty(),
                p.corAccProperty()};
    }
}
