package report.entities.items.counterparties.executiveBody;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.CloneInterface;

public class CountAgentExBody implements CloneInterface<CountAgentExBody> {
    private long id;
    private StringProperty exBody;
    private StringProperty exBodyName;
    private StringProperty exBodySurname;
    private StringProperty exBodyFName;
    private StringProperty bcName;
    private StringProperty bcSurname;
    private StringProperty bcFName;
    private IntegerProperty idSeries;
    private IntegerProperty idNumber;
    private IntegerProperty idDate;
    private StringProperty idText;
    private IntegerProperty idCode;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public CountAgentExBody(
            long id,
            String exBody,
            String exBodyName,
            String exBodySurname,
            String exBodyFName,
            String bcName,
            String bcSurname,
            String bcFName,
            int idSeries,
            int idNumber,
            int idDate,
            String idText,
            int idCode
    ) {
        this.id = id;
        this.exBody = new SimpleStringProperty(exBody);
        this.exBodyName = new SimpleStringProperty(exBodyName);
        this.exBodySurname = new SimpleStringProperty(exBodySurname);
        this.exBodyFName = new SimpleStringProperty(exBodyFName);
        this.bcName = new SimpleStringProperty(bcName);
        this.bcSurname = new SimpleStringProperty(bcSurname);
        this.bcFName = new SimpleStringProperty(bcFName);
        this.idSeries = new SimpleIntegerProperty(idSeries);
        this.idNumber = new SimpleIntegerProperty(idNumber);
        this.idDate = new SimpleIntegerProperty(idDate);
        this.idText =  new SimpleStringProperty(idText);
        this.idCode = new SimpleIntegerProperty(idCode);
    }
    /***************************************************************************
     *                                                                         *
     * Clone Constructors                                                      *
     *                                                                         *
     **************************************************************************/
    public CountAgentExBody(CountAgentExBody source ) {
        this.id = id;
        this.exBody = new SimpleStringProperty(source.exBody.getValue());
        this.exBodyName = new SimpleStringProperty(source.exBodyName.getValue());
        this.exBodySurname = new SimpleStringProperty(source.exBodySurname.getValue());
        this.exBodyFName = new SimpleStringProperty(source.exBodyFName.getValue());
        this.bcName = new SimpleStringProperty(source.bcName.getValue());
        this.bcSurname = new SimpleStringProperty(source.bcSurname.getValue());
        this.bcFName = new SimpleStringProperty(source.bcFName.getValue());
        this.idSeries = new SimpleIntegerProperty(source.idSeries.getValue());
        this.idNumber = new SimpleIntegerProperty(source.idNumber.getValue());
        this.idDate = new SimpleIntegerProperty(source.idDate.getValue());
        this.idText =  new SimpleStringProperty(source.idText.getValue());
        this.idCode = new SimpleIntegerProperty(source.idCode.getValue());
    }

    /***************************************************************************
     *                                                                         *
     * Getters/Setters                                                         *
     *                                                                         *
     **************************************************************************/

    @Override
    public CountAgentExBody getClone() {
        return null;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    public String getExBody() {
        return exBody.get();
    }

    public StringProperty exBodyProperty() {
        return exBody;
    }

    public void setExBody(String exBody) {
        this.exBody.set(exBody);
    }

    public String getExBodyName() {
        return exBodyName.get();
    }

    public StringProperty exBodyNameProperty() {
        return exBodyName;
    }

    public void setExBodyName(String exBodyName) {
        this.exBodyName.set(exBodyName);
    }

    public String getExBodySurname() {
        return exBodySurname.get();
    }

    public StringProperty exBodySurnameProperty() {
        return exBodySurname;
    }

    public void setExBodySurname(String exBodySurname) {
        this.exBodySurname.set(exBodySurname);
    }

    public String getExBodyFName() {
        return exBodyFName.get();
    }

    public StringProperty exBodyFNameProperty() {
        return exBodyFName;
    }

    public void setExBodyFName(String exBodyFName) {
        this.exBodyFName.set(exBodyFName);
    }

    public String getBcName() {
        return bcName.get();
    }

    public StringProperty bcNameProperty() {
        return bcName;
    }

    public void setBcName(String bcName) {
        this.bcName.set(bcName);
    }

    public String getBcSurname() {
        return bcSurname.get();
    }

    public StringProperty bcSurnameProperty() {
        return bcSurname;
    }

    public void setBcSurname(String bcSurname) {
        this.bcSurname.set(bcSurname);
    }

    public String getBcFName() {
        return bcFName.get();
    }

    public StringProperty bcFNameProperty() {
        return bcFName;
    }

    public void setBcFName(String bcFName) {
        this.bcFName.set(bcFName);
    }

    public int getIdSeries() {
        return idSeries.get();
    }

    public IntegerProperty idSeriesProperty() {
        return idSeries;
    }

    public void setIdSeries(int idSeries) {
        this.idSeries.set(idSeries);
    }

    public int getIdNumber() {
        return idNumber.get();
    }

    public IntegerProperty idNumberProperty() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber.set(idNumber);
    }

    public int getIdDate() {
        return idDate.get();
    }

    public IntegerProperty idDateProperty() {
        return idDate;
    }

    public void setIdDate(int idDate) {
        this.idDate.set(idDate);
    }

    public String getIdText() {
        return idText.get();
    }

    public StringProperty idTextProperty() {
        return idText;
    }

    public void setIdText(String idText) {
        this.idText.set(idText);
    }

    public int getIdCode() {
        return idCode.get();
    }

    public IntegerProperty idCodeProperty() {
        return idCode;
    }

    public void setIdCode(int idCode) {
        this.idCode.set(idCode);
    }
    /**
     *Extractor to observe changes in "Property" fields.
     *
     * @return Callback<CountAgentTVI, Observable[]>
     */
    public static Callback<CountAgentExBody, Observable[]> extractor() {
        return (CountAgentExBody p) -> new Observable[]{
                p.exBodyProperty(),
                p.exBodyNameProperty(),
                p.exBodySurnameProperty(),
                p.exBodyFNameProperty(),
                p.bcNameProperty(),
                p.bcSurnameProperty(),
                p.bcFNameProperty(),
                p.idSeriesProperty(),
                p.idNumberProperty(),
                p.idDateProperty(),
                p.idTextProperty(),
                p.idCodeProperty()
        };
    }
}
