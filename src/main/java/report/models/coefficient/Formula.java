package report.models.coefficient;

import report.entities.items.counterparties.AgentTVI.CountAgentTVI;

public class Formula {

    private final Integer quantity;
    private final Double siteExpenses,
            OSR,
            smetCost,
            smetCostSum,
            saleHouseSum,
            perSaleExpenses,
            incomeTax;

    /*!******************************************************************************************************************
    *                                                                                                         CONSTRUCTOR
    /********************************************************************************************************************/
    private Formula() {
        this.quantity = 0;
        this.siteExpenses = 0d;
        this.OSR = 0d;
        this.smetCost = 0d;
        this.smetCostSum = 0d;
        this.saleHouseSum = 0d;
        this.perSaleExpenses = 0d;
        this.incomeTax = 0d;
    }

    public Formula(
            Integer quantity,
            Double siteExpenses,
            Double OSR,
            Double smetCost,
            Double smetCostSum,
            Double saleHouseSum,
            Double perSaleExpenses,
            Double incomeTax
    ) {
        this.quantity = quantity;
        this.siteExpenses = siteExpenses;
        this.OSR = OSR;
        this.smetCost = smetCost;
        this.smetCostSum = smetCostSum;
        this.saleHouseSum = saleHouseSum;
        this.perSaleExpenses = perSaleExpenses;
        this.incomeTax = incomeTax;

    }


    /*!******************************************************************************************************************
    *                                                                                                            Methods
    /********************************************************************************************************************/


    /**
     * Double NonRoundCoefficient = (siteExpenses- OSR/QUANTITY- (PROFIT/quantity.doubleValue())*incomeTax)/smetCost;
     * <br> </br>
     * <br>PROFIT =((perSaleExpenses*saleHouseSum) - OSR)</br>
     *
     * @return Double Coefficient Value
     */
    public Double computeCoefficient() {

        double nonRoundCoefficient = (siteExpenses - (OSR / quantity.doubleValue()) - this.allTaxes()) / smetCost;
        //Round COEFFICIENT to #.00
        Double COEFFICIENT = Math.floor(nonRoundCoefficient * 100) / 100;
        if (COEFFICIENT == 0
                | COEFFICIENT == Double.NEGATIVE_INFINITY
                | COEFFICIENT == Double.POSITIVE_INFINITY
                | COEFFICIENT == Double.NaN
                )
            COEFFICIENT = 1.0;
//        }
        return COEFFICIENT;
    }

    /**
     * profit =((perSaleExpenses*saleHouseSum) - OSR );
     *
     * @return
     */
    public Double profit() {
        return (perSaleExpenses * saleHouseSum) - OSR;
    }

    /**
     * (this.profit()/quantity.doubleValue())*incomeTax
     * )*incomeTax;
     *
     * @return
     */
    public Double allTaxes() {
        return (this.profit() / quantity.doubleValue()) * incomeTax;
    }

    /**
     * (this.profit()/quantity.doubleValue()
     * )*incomeTax;
     *
     * @return
     */
    public Integer quantity() {
        return quantity.intValue();
    }

    /*!******************************************************************************************************************
    *                                                                                                      Static Methods
    /********************************************************************************************************************/


    public static Formula formulaFromBase() {
        return new FormulaQuery().getFormula();
    }
    public static Formula formulaFromBase(final String siteNumber,final CountAgentTVI contractor) {
        return new FormulaQuery().getFormula(siteNumber,contractor);
    }

    public static Integer quantityFromBase() {
        return new FormulaQuery().getSiteQuantity();
    }


}
