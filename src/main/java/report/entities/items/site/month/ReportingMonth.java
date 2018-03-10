package report.entities.items.site.month;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import report.entities.items.Clone;

import java.util.Date;

//TODO: refactor to NORMAL
public class ReportingMonth implements Clone<ReportingMonth> {
    private ObjectProperty<Date> month;
    private StringProperty countFinish;
    private StringProperty countParty;
    private StringProperty priceSum;
    private StringProperty sCred;
    private StringProperty operProfit;
    private StringProperty addCost;
    private StringProperty allTaxes;
    private StringProperty sumOsr;

    public ReportingMonth(Date month, String countFinish, String countParty, String priceSum, String sCred, String operProfit, String addCost, String allTaxes, String sumOsr) {
        this.month = new SimpleObjectProperty<>(month);
        this.countFinish = new SimpleStringProperty(countFinish);
        this.countParty = new SimpleStringProperty(countParty);
        this.priceSum = new SimpleStringProperty(priceSum);
        this.sCred = new SimpleStringProperty(sCred);
        this.operProfit = new SimpleStringProperty(operProfit);
        this.addCost = new SimpleStringProperty(addCost);
        this.allTaxes = new SimpleStringProperty(allTaxes);
        this.sumOsr = new SimpleStringProperty(sumOsr);
    }
    public ReportingMonth(ReportingMonth reportingMonth) {
        this.month = new SimpleObjectProperty<>(reportingMonth.getMonth());
        this.countFinish = new SimpleStringProperty(reportingMonth.getCountFinish());
        this.countParty = new SimpleStringProperty(reportingMonth.getCountParty());
        this.priceSum = new SimpleStringProperty(reportingMonth.getPriceSum());
        this.sCred = new SimpleStringProperty(reportingMonth.getsCred());
        this.operProfit = new SimpleStringProperty(reportingMonth.getOperProfit());
        this.addCost = new SimpleStringProperty(reportingMonth.getAddCost());
        this.allTaxes = new SimpleStringProperty(reportingMonth.getAllTaxes());
        this.sumOsr = new SimpleStringProperty(reportingMonth.getSumOsr());
    }
    
    @Override
    public ReportingMonth getClone() {
        return new ReportingMonth(this);
    }

    public Date getMonth() {
        return month.get();
    }

    public ObjectProperty<Date> monthProperty() {
        return month;
    }

    public void setMonth(Date month) {
        this.month.set(month);
    }

    public String getCountFinish() {
        return countFinish.get();
    }

    public StringProperty countFinishProperty() {
        return countFinish;
    }

    public void setCountFinish(String countFinish) {
        this.countFinish.set(countFinish);
    }

    public String getCountParty() {
        return countParty.get();
    }

    public StringProperty countPartyProperty() {
        return countParty;
    }

    public void setCountParty(String countParty) {
        this.countParty.set(countParty);
    }

    public String getPriceSum() {
        return priceSum.get();
    }

    public StringProperty priceSumProperty() {
        return priceSum;
    }

    public void setPriceSum(String priceSum) {
        this.priceSum.set(priceSum);
    }

    public String getsCred() {
        return sCred.get();
    }

    public StringProperty sCredProperty() {
        return sCred;
    }

    public void setsCred(String sCred) {
        this.sCred.set(sCred);
    }

    public String getOperProfit() {
        return operProfit.get();
    }

    public StringProperty operProfitProperty() {
        return operProfit;
    }

    public void setOperProfit(String operProfit) {
        this.operProfit.set(operProfit);
    }

    public String getAddCost() {
        return addCost.get();
    }

    public StringProperty addCostProperty() {
        return addCost;
    }

    public void setAddCost(String addCost) {
        this.addCost.set(addCost);
    }

    public String getAllTaxes() {
        return allTaxes.get();
    }

    public StringProperty allTaxesProperty() {
        return allTaxes;
    }

    public void setAllTaxes(String allTaxes) {
        this.allTaxes.set(allTaxes);
    }

    public String getSumOsr() {
        return sumOsr.get();
    }

    public StringProperty sumOsrProperty() {
        return sumOsr;
    }

    public void setSumOsr(String sumOsr) {
        this.sumOsr.set(sumOsr);
    }


}
