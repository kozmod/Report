package report.entities.items.discount_coef;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TreeItem;
import report.entities.items.Clone;
import report.entities.items.DItem;

public class DiscountCoef implements Clone {


    interface SQL {
        String RATE_OF_RETURN = "RateOfReturn";
        String KD = "KD";
        String KD_PER_MONTH = "KDperMonth";
        String MARKET_RISKS = "MarketRisks";
        String SPECIFIC_RISKS = "SpecificRisk";
    }

    public static final String RATE_OF_RETURN = "Ставка доходности по ОФЗ РФ 26214 (с погашением в мае 2020 г.)";
    public static final String KD = "Итого ставка доходности на собственный капитал (КД), %";
    public static final String KD_PER_MONTH = "Месячный коэффициент дисконтирования (КД/12)";
    public static final String MARKET_RISKS = "Рыночные риски";
    public static final String SPECIFIC_RISKS = "Специфические риски";
    public static final String ROOT_ELEMENT = "ROOT";


    private Long id;
    private final DoubleProperty rateOfReturn;
    private final DoubleProperty kd;
    private final DoubleProperty kdPerMonth;
    private final SpecificRisk specificRisk;
    private final MarketRisk marketRisk;

    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/

    public DiscountCoef(long id, double rateOfReturn, SpecificRisk specificRisk, MarketRisk marketRisk) {
        this.id = id;
        this.rateOfReturn = new SimpleDoubleProperty(rateOfReturn);
        this.specificRisk = specificRisk;
        this.marketRisk = marketRisk;
        this.kd = new SimpleDoubleProperty();
        this.kdPerMonth = new SimpleDoubleProperty();


    }

    /**
     * Clone CONSTRUCTOR implementation
     */
    public DiscountCoef(DiscountCoef difCoef) {
        this.id = difCoef.id;
        this.rateOfReturn = new SimpleDoubleProperty(difCoef.rateOfReturn.getValue());
        this.specificRisk = new SpecificRisk(difCoef.specificRisk);
        this.marketRisk = new MarketRisk(difCoef.marketRisk);
        this.kd = new SimpleDoubleProperty();
        this.kdPerMonth = new SimpleDoubleProperty();


    }

    /***************************************************************************
     *                                                                         *
     * GETTER                                                                  *
     *                                                                         *
     **************************************************************************/
//    @Override
    public long getId() {
        return id;
    }

    //    @Override
    public void setId(long id) {
        this.id = id;
    }

    public DoubleProperty kdProperty() {
        return kd;
    }

    public DoubleProperty kdPerMonthProperty() {
        return kdPerMonth;
    }

    public SpecificRisk specificRisk() {
        return specificRisk;
    }

    public MarketRisk marketRisk() {
        return marketRisk;
    }

    public double getRateOfReturn() {
        return rateOfReturn.get();
    }

    public DoubleProperty rateOfReturnProperty() {
        return rateOfReturn;
    }

    @Override
    public DiscountCoef getClone() {
        return new DiscountCoef(this);
    }


    /***************************************************************************
     *                                                                         *
     * Method                                                                  *
     *                                                                         *
     **************************************************************************/

    private void computeKD() {
        double var = this.rateOfReturn.get()
                + this.specificRisk.valueProperty().get()
                + this.marketRisk.valueProperty().get();
        this.kd.setValue(var);
        this.kdPerMonth.setValue(var / 12);
    }


    /**
     * TreeItem from Object
     *
     * @return
     */
    public TreeItem<DItem> tree() {
        final TreeItem<DItem> specificRoot = new TreeItem<>(
                new DItem(id, "", SPECIFIC_RISKS, specificRisk.valueProperty())
        );
        specificRoot.getChildren().addAll(
                new TreeItem<>(
                        new DItem(id,
                                "",
                                SpecificRisk.KEY_CHAR,
                                specificRisk.keyCharProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(
                                id,
                                "",
                                SpecificRisk.ENTERPRISE_SIZE,
                                specificRisk.entSizeProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                SpecificRisk.FIN_STRUCT,
                                specificRisk.finStructProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                SpecificRisk.CLIENT_DIV,
                                specificRisk.clientDivProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                SpecificRisk.OTHER_RISKS,
                                specificRisk.otherRisksProperty()
                        )
                )
        );

        final TreeItem<DItem> marketRoot = new TreeItem<>(
                new DItem(id, "", MARKET_RISKS, marketRisk.valueProperty())
        );
        marketRoot.getChildren().addAll(
                new TreeItem<>(
                        new DItem(id,
                                "",
                                MarketRisk.SOCIAL_RISKS,
                                marketRisk.socialProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(
                                id,
                                "",
                                MarketRisk.INTERNAL_RISKS,
                                marketRisk.internalProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                MarketRisk.EXTERNAL_RISKS,
                                marketRisk.externalProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                MarketRisk.CYCLICAL_CHAR,
                                marketRisk.cyclicalCharProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                MarketRisk.DEV_STAGE,
                                marketRisk.devStageProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                MarketRisk.COMPETITION,
                                marketRisk.competitionProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                MarketRisk.REGULATION,
                                marketRisk.regulationProperty()
                        )
                ),
                new TreeItem<>(
                        new DItem(id,
                                "",
                                MarketRisk.MARKET_BARRIERS,
                                marketRisk.marketBarriersProperty()
                        )
                )
        );

        final TreeItem<DItem> RORLeaf = new TreeItem<>(new DItem(id, "", RATE_OF_RETURN, this.rateOfReturn));
        final TreeItem<DItem> KD = new TreeItem<>(new DItem(id, "", this.KD, this.kd));
        final TreeItem<DItem> KDperMonth = new TreeItem<>(new DItem(id, "", this.KD, this.kdPerMonth));
        final TreeItem<DItem> root = new TreeItem<>(new DItem(id, "", ROOT_ELEMENT, 0d));
        root.getChildren().addAll(RORLeaf, specificRoot, marketRoot, KD, KDperMonth);

        root.getValue().secondValueProperty().addListener((ChangeListener) (observable, oldValue, newValue) -> {
            this.specificRisk.compute();
            this.marketRisk.compute();
            this.computeKD();
        });
        this.specificRisk.compute();
        this.marketRisk.compute();
        this.computeKD();
        return root;
    }

    /***************************************************************************
     *                                                                         *
     * Override                                                                *
     *                                                                         *
     **************************************************************************/
    @Override
    public String toString() {
        return "DiscountCoef{" +
                "id=" + id +
                ", rateOfReturn=" + rateOfReturn +
                ", kd=" + kd +
                ", kdPerMonth=" + kdPerMonth +
                ", specificRisk=" + specificRisk +
                ", marketRisk=" + marketRisk +
                '}';
    }
}
