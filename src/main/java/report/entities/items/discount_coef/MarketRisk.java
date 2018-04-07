package report.entities.items.discount_coef;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.math.BigDecimal;

public class MarketRisk {
    interface SQL {
        /**
         * Общеэкономические факторы
         */
        String SOCIAL_RISKS = "SocialRisks";
        String INTERNAL_RISKS = "InternalRisks";
        String EXTERNAL_RISKS = "ExternalRisks";
        /**
         * Отраслевые факторы
         */
        String CYCLICAL_CHAR = "CyclicalChar";
        String DEV_STAGE = "DevStage";
        String COMPETITION = "Competition";
        String REGULATION = "Regulation";
        String MARKET_BARRIERS = "MarketBarriers";
    }

    private long id;
    /**
     * Общеэкономические факторы
     */
    public static final String SOCIAL_RISKS = "Cоциально-политический риск";
    public static final String INTERNAL_RISKS = "Внутриэкономический риск";
    public static final String EXTERNAL_RISKS = "Bнешнеэкономический риск";
    /**
     * Отраслевые факторы
     */
    public static final String CYCLICAL_CHAR = "Циклический характер";
    public static final String DEV_STAGE = "Стадия развития";
    public static final String COMPETITION = "Конкуренция";
    public static final String REGULATION = "Регулирование";
    public static final String MARKET_BARRIERS = "Препятствия к вхождению в рынок";

    private final DoubleProperty social;
    private final DoubleProperty internal;
    private final DoubleProperty external;

    private final DoubleProperty cyclicalChar;
    private final DoubleProperty devStage;
    private final DoubleProperty competition;
    private final DoubleProperty regulation;
    private final DoubleProperty marketBarriers;
    //compute
    private final DoubleProperty value;

    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/
    public MarketRisk(long id,
                      double social,
                      double internal,
                      double external,
                      double cyclicalChar,
                      double devStage,
                      double competition,
                      double regulation,
                      double marketBarriers) {
        this.id = id;
        this.social = new SimpleDoubleProperty(social);
        this.internal = new SimpleDoubleProperty(internal);
        this.external = new SimpleDoubleProperty(external);
        this.cyclicalChar = new SimpleDoubleProperty(cyclicalChar);
        this.devStage = new SimpleDoubleProperty(devStage);
        this.competition = new SimpleDoubleProperty(competition);
        this.regulation = new SimpleDoubleProperty(regulation);
        this.marketBarriers = new SimpleDoubleProperty(marketBarriers);
        this.value = new SimpleDoubleProperty();
    }

    public MarketRisk(MarketRisk mr) {
        this.id = mr.id;
        this.social = new SimpleDoubleProperty(mr.social.getValue());
        this.internal = new SimpleDoubleProperty(mr.internal.getValue());
        this.external = new SimpleDoubleProperty(mr.external.getValue());
        this.cyclicalChar = new SimpleDoubleProperty(mr.cyclicalChar.getValue());
        this.devStage = new SimpleDoubleProperty(mr.devStage.getValue());
        this.competition = new SimpleDoubleProperty(mr.competition.getValue());
        this.regulation = new SimpleDoubleProperty(mr.regulation.getValue());
        this.marketBarriers = new SimpleDoubleProperty(mr.marketBarriers.getValue());
        this.value = new SimpleDoubleProperty();
    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                  *
     *                                                                         *
     **************************************************************************/
    public void compute() {
        BigDecimal bdValue = new BigDecimal(social.get())
                .add(new BigDecimal(internal.get()))
                .add(new BigDecimal(external.get()))
                .add(new BigDecimal(cyclicalChar.get()))
                .add(new BigDecimal(devStage.get()))
                .add(new BigDecimal(competition.get()))
                .add(new BigDecimal(regulation.get()))
                .add(new BigDecimal(marketBarriers.get()));
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

    public double getSocial() {
        return social.get();
    }

    public DoubleProperty socialProperty() {
        return social;
    }

    public void setSocial(double social) {
        this.social.set(social);
    }

    public double getInternal() {
        return internal.get();
    }

    public DoubleProperty internalProperty() {
        return internal;
    }

    public void setInternal(double internal) {
        this.internal.set(internal);
    }

    public double getExternal() {
        return external.get();
    }

    public DoubleProperty externalProperty() {
        return external;
    }

    public void setExternal(double external) {
        this.external.set(external);
    }

    public double getCyclicalChar() {
        return cyclicalChar.get();
    }

    public DoubleProperty cyclicalCharProperty() {
        return cyclicalChar;
    }

    public void setCyclicalChar(double cyclicalChar) {
        this.cyclicalChar.set(cyclicalChar);
    }

    public double getDevStage() {
        return devStage.get();
    }

    public DoubleProperty devStageProperty() {
        return devStage;
    }

    public void setDevStage(double devStage) {
        this.devStage.set(devStage);
    }

    public double getCompetition() {
        return competition.get();
    }

    public DoubleProperty competitionProperty() {
        return competition;
    }

    public void setCompetition(double competition) {
        this.competition.set(competition);
    }

    public double getRegulation() {
        return regulation.get();
    }

    public DoubleProperty regulationProperty() {
        return regulation;
    }

    public void setRegulation(double regulation) {
        this.regulation.set(regulation);
    }

    public double getMarketBarriers() {
        return marketBarriers.get();
    }

    public DoubleProperty marketBarriersProperty() {
        return marketBarriers;
    }

    public void setMarketBarriers(double marketBarriers) {
        this.marketBarriers.set(marketBarriers);
    }


    public DoubleProperty valueProperty() {
        return value;
    }
}
