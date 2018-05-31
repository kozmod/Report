package report.entities.items.site;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;
import report.entities.items.Clone;

import java.util.Date;

public class SiteEntity implements Clone {

    private Long id;
    private final String siteNumber;
    private final StringProperty typeHome;
    private final StringProperty numberOfContract;
    private final IntegerProperty dateOfContract;
    private final IntegerProperty countAgentId;
    private final StringProperty countAgentName;
    private final IntegerProperty siteTypeId;
    private final StringProperty queueBuildings;
    private final FloatProperty smetCost;
    private final FloatProperty sumCost;
    private final FloatProperty costHouse;
    private final FloatProperty costSite;
    private final FloatProperty saleClient;
    private final FloatProperty debtClient;
    private final FloatProperty saleHouse;
    private final DoubleProperty coefficient;
    private final StringProperty statusPayment;
    private final StringProperty statusJobs;
    private final IntegerProperty finishBuilding;

    public SiteEntity(
            long id,
            String siteNumber,
            String typeHome,
            String numberOfContract,
            int dateOfContract,
            int countAgentId,
            String countAgentName,
            int siteTypeId,
            String queueBuildings,
            float smetCost,
            float sumCost,
            float costHouse,
            float costSite,
            float saleClient,
            float debtClient,
            float saleHouse,
            double coefficient,
            String statusPayment,
            String statusJobs,
            int finishBuilding) {
        this.id = id;
        this.siteNumber = siteNumber;
        this.typeHome = new SimpleStringProperty(typeHome);
        this.numberOfContract = new SimpleStringProperty(numberOfContract);
        this.dateOfContract = new SimpleIntegerProperty(dateOfContract);
        this.countAgentId = new SimpleIntegerProperty(countAgentId);
        this.countAgentName = new SimpleStringProperty(countAgentName);
        this.siteTypeId = new SimpleIntegerProperty(siteTypeId);
        this.queueBuildings = new SimpleStringProperty(queueBuildings);
        this.smetCost = new SimpleFloatProperty(smetCost);
        this.sumCost = new SimpleFloatProperty(sumCost);
        this.costHouse = new SimpleFloatProperty(costHouse);
        this.costSite = new SimpleFloatProperty(costSite);
        this.saleClient = new SimpleFloatProperty(saleClient);
        this.debtClient = new SimpleFloatProperty(debtClient);
        this.saleHouse = new SimpleFloatProperty(saleHouse);
        this.coefficient = new SimpleDoubleProperty(coefficient);
        this.statusPayment = new SimpleStringProperty(statusPayment);
        this.statusJobs = new SimpleStringProperty(statusJobs);
        this.finishBuilding = new SimpleIntegerProperty(finishBuilding);
    }

    /***
     * Clone CONSTRUCTOR implementation
     * @return SiteEntity
     */
    @Override
    public SiteEntity getClone() {
        SiteEntity clone = new SiteEntity(
                this.id,
                this.siteNumber,
                this.typeHome.getValueSafe(),
                this.numberOfContract.getValueSafe(),
                this.dateOfContract.getValue(),
                this.countAgentId.getValue(),
                this.countAgentName.getValueSafe(),
                this.siteTypeId.getValue(),
                this.queueBuildings.getValueSafe(),
                this.smetCost.getValue(),
                this.sumCost.getValue(),
                this.costHouse.getValue(),
                this.costSite.getValue(),
                this.saleClient.getValue(),
                this.debtClient.getValue(),
                this.saleHouse.getValue(),
                this.coefficient.getValue(),
                this.statusPayment.getValueSafe(),
                this.statusJobs.getValueSafe(),
                this.finishBuilding.getValue()
        );
        return clone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteNumber() {
        return siteNumber;
    }

    public String getTypeHome() {
        return typeHome.get();
    }

    public StringProperty typeHomeProperty() {
        return typeHome;
    }

    public void setTypeHome(String typeHome) {
        this.typeHome.set(typeHome);
    }

    public String getNumberOfContract() {
        return numberOfContract.get();
    }

    public StringProperty numberOfContractProperty() {
        return numberOfContract;
    }

    public void setNumberOfContract(String numberOfContract) {
        this.numberOfContract.set(numberOfContract);
    }

    public int getDateOfContract() {
        return dateOfContract.get();
    }

    public IntegerProperty dateOfContractProperty() {
        return dateOfContract;
    }

    public void setDateOfContract(int dateOfContract) {
        this.dateOfContract.set(dateOfContract);
    }

    public int getCountAgentId() {
        return countAgentId.get();
    }

    public IntegerProperty countAgentIdProperty() {
        return countAgentId;
    }

    public void setCountAgentId(int countAgentId) {
        this.countAgentId.set(countAgentId);
    }

    public int getSiteTypeId() {
        return siteTypeId.get();
    }

    public IntegerProperty siteTypeIdProperty() {
        return siteTypeId;
    }

    public void setSiteTypeId(int siteTypeId) {
        this.siteTypeId.set(siteTypeId);
    }

    public String getQueueBuildings() {
        return queueBuildings.get();
    }

    public StringProperty queueBuildingsProperty() {
        return queueBuildings;
    }

    public void setQueueBuildings(String queueBuildings) {
        this.queueBuildings.set(queueBuildings);
    }

    public float getSmetCost() {
        return smetCost.get();
    }

    public FloatProperty smetCostProperty() {
        return smetCost;
    }

    public void setSmetCost(float smetCost) {
        this.smetCost.set(smetCost);
    }

    public float getSumCost() {
        return sumCost.get();
    }

    public FloatProperty sumCostProperty() {
        return sumCost;
    }

    public void setSumCost(float sumCost) {
        this.sumCost.set(sumCost);
    }

    public float getCostHouse() {
        return costHouse.get();
    }

    public FloatProperty costHouseProperty() {
        return costHouse;
    }

    public void setCostHouse(float costHouse) {
        this.costHouse.set(costHouse);
    }

    public float getCostSite() {
        return costSite.get();
    }

    public FloatProperty costSiteProperty() {
        return costSite;
    }

    public void setCostSite(float costSite) {
        this.costSite.set(costSite);
    }

    public float getSaleClient() {
        return saleClient.get();
    }

    public FloatProperty saleClientProperty() {
        return saleClient;
    }

    public void setSaleClient(float saleClient) {
        this.saleClient.set(saleClient);
    }

    public float getDebtClient() {
        return debtClient.get();
    }

    public FloatProperty debtClientProperty() {
        return debtClient;
    }

    public void setDebtClient(float debtClient) {
        this.debtClient.set(debtClient);
    }

    public float getSaleHouse() {
        return saleHouse.get();
    }

    public FloatProperty saleHouseProperty() {
        return saleHouse;
    }

    public void setSaleHouse(float saleHouse) {
        this.saleHouse.set(saleHouse);
    }

    public double getCoefficient() {
        return coefficient.get();
    }

    public DoubleProperty coefficientProperty() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient.set(coefficient);
    }

    public String getStatusPayment() {
        return statusPayment.get();
    }

    public StringProperty statusPaymentProperty() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment.set(statusPayment);
    }

    public String getStatusJobs() {
        return statusJobs.get();
    }

    public StringProperty statusJobsProperty() {
        return statusJobs;
    }

    public void setStatusJobs(String statusJobs) {
        this.statusJobs.set(statusJobs);
    }

    public int getFinishBuilding() {
        return finishBuilding.get();
    }

    public IntegerProperty finishBuildingProperty() {
        return finishBuilding;
    }

    public void setFinishBuilding(int finishBuilding) {
        this.finishBuilding.set(finishBuilding);
    }

    public String getCountAgentName() {
        return countAgentName.get();
    }

    public StringProperty countAgentNameProperty() {
        return countAgentName;
    }

    public void setCountAgentName(String countAgentName) {
        this.countAgentName.set(countAgentName);
    }

    /**
     * Extractor to observe changes in "Property" fields.
     *
     * @return Callback<VariableTIV                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Observable                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               [                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ]>
     */
    public static Callback<SiteEntity, Observable[]> extractor() {
        return (SiteEntity p) -> new Observable[]{
                p.typeHome,
                p.numberOfContract,
                p.dateOfContract,
                p.countAgentId,
                p.countAgentName,
                p.siteTypeId,
                p.queueBuildings,
                p.smetCost,
                p.sumCost,
                p.costHouse,
                p.costSite,
                p.saleClient,
                p.debtClient,
                p.saleHouse,
                p.coefficient,
                p.statusPayment,
                p.statusJobs,
                p.finishBuilding
        };
    }

    @Override
    public String toString() {
        return "SiteEntity = {" +
                "id = " + this.id +
                " ,siteNumber = " + this.siteNumber +
                " ,typeHome = " + this.typeHome.getValueSafe() +
                " ,numberOfContract = " + this.numberOfContract.getValueSafe() +
                " ,dateOfContract = " + this.dateOfContract.getValue() +
                " ,countAgentId = " + this.countAgentId.getValue() +
                " ,countAgentName = " + this.countAgentName.getValueSafe() +
                " ,siteTypeId = " + this.siteTypeId.getValue() +
                " ,queueBuildings = " + this.queueBuildings.getValueSafe() +
                " ,smetCost = " + this.smetCost.getValue() +
                " ,sumCost = " + this.sumCost.getValue() +
                " ,costHouse = " + this.costHouse.getValue() +
                " ,costSite = " + this.costSite.getValue() +
                " ,saleClient = " + this.saleClient.getValue() +
                " ,debtClient = " + this.debtClient.getValue() +
                " ,saleHouse = " + this.saleHouse.getValue() +
                " ,coefficient = " + this.coefficient.getValue() +
                " ,statusPayment = " + this.statusPayment.getValueSafe() +
                " ,statusJobs = " + this.statusJobs.getValueSafe() +
                " ,finishBuilding = " + this.finishBuilding.getValue() +
                "}";
    }
}