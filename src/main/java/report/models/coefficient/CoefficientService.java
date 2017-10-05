
package report.models.coefficient;


import report.layoutControllers.LogController;

import java.math.BigDecimal;

public class CoefficientService {

    private static CoefficientService coefficient = null;

    private Integer quantity;
    private Double profit;
    private Double coefficientValue;
    private Double siteExpenses,
                   OSR,
                   smetCost,
                   smetCostSum,
                   saleHouseSum,
                   perSaleExpenses,
                   incomeTax;

/*!******************************************************************************************************************
*                                                                                                         CONSTRUCTOR
/********************************************************************************************************************/
    public CoefficientService(){}

    public CoefficientService(Integer quantity,
                              Double siteExpenses,
                              Double OSR,
                              Double smetCost,
                              Double smetCostSum,
                              Double saleHouseSum,
                              Double perSaleExpenses,
                              Double incomeTax) {
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
    private Double computeCoefficient(){
//        profit = (perSaleExpenses*saleHouseSum) - OSR - smetCostSum;
        profit = this.getProfit();
        double nonRoundCoefficient = (siteExpenses
                - OSR/quantity.doubleValue()
                - (profit/quantity.doubleValue())*incomeTax) ///  Налоги<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                /smetCost;

        //Round COEFFICIENT to #.00
        Double COEFFICIENT = Math.floor(nonRoundCoefficient * 100) / 100;
//        Double COEFFICIENT = nonRoundCoefficient;
        if(COEFFICIENT ==  0
                | COEFFICIENT ==  Double.NEGATIVE_INFINITY
                | COEFFICIENT ==  Double.POSITIVE_INFINITY
                | COEFFICIENT ==  Double.NaN
                )
            return 1d;
        else {
//            LogController.appendLogViewText(
//                    "siteExpenses = " + siteExpenses + " || " +
//                            "OSR = " + new BigDecimal(OSR).toPlainString() + " || " +
//                            "quantity = " +  new BigDecimal(quantity).toPlainString()  + " || " +
//                            "incomeTax = " +  new BigDecimal(incomeTax).toPlainString()  + " || " +
//                            "smetCost = " +  new BigDecimal(smetCost).toPlainString()  + " || " +
//                            "profit =" +  new BigDecimal(profit).toPlainString() + " || " +
//                            "perSaleExpenses =" +  new BigDecimal(perSaleExpenses).toPlainString() + " || " +
//                            "" );
//            LogController.appendLogViewText(
//                    "siteExpenses = " + siteExpenses + " || " +
//                            "OSR = " + new BigDecimal(OSR).toPlainString() + " || " +
//                            "OSR/quantity.doubleValue() = " +  OSR/quantity.doubleValue()  + " || " +
//                            "(profit/quantity.doubleValue())*incomeTax) = " +  (profit/quantity.doubleValue())*incomeTax  + " || " +
//                            "profit =" +  new BigDecimal(profit).toPlainString() + " || " +
//                            "COEFFICIENT =" + nonRoundCoefficient+ " || " +
//
//                            "" );
            return COEFFICIENT;
        }
    }


/*!******************************************************************************************************************
*                                                                                                             Builder
/********************************************************************************************************************/

    public static Builder newCoeffBuilder(){
        return new CoefficientService().new Builder();
    }


    public class Builder{

        public Builder setSiteExpenses(Double siteExpenses){
            CoefficientService.this.setSiteExpenses(siteExpenses);
            return  this;
        }

        public Builder setOSR(Double OSR) {
            CoefficientService.this.OSR = OSR;
            return  this;
        }
        public Builder setSmetCost(Double smetCost) {
            CoefficientService.this.smetCost = smetCost;
            return  this;
        }
        public Builder setSmetCostSum(Double smetCostSum) {
            CoefficientService.this.smetCostSum = smetCostSum;
            return  this;
        }
        public Builder setSaleHouseSum(Double saleHouseSum) {
            CoefficientService.this.saleHouseSum = saleHouseSum;
            return  this;
        }
        public Builder setQuantity(Integer quantity) {
            CoefficientService.this.quantity = quantity;
            return  this;
        }
        public Builder setPerSaleExpenses(Double perSaleExpenses) {
            CoefficientService.this.perSaleExpenses = perSaleExpenses;
            return  this;
        }
        public Builder setIncomeTax(Double incomeTax) {
            CoefficientService.this.incomeTax = incomeTax;
            return  this;
        }

        public CoefficientService build() {
            CoefficientService.this.coefficientValue = CoefficientService.this.computeCoefficient();
            return  CoefficientService.this;
        }



    }

/*!******************************************************************************************************************
*                                                                                                       Getter/Setter
/********************************************************************************************************************/
    public void setSiteExpenses(Double siteExpenses) { this.siteExpenses = siteExpenses;}
    public void setOSR(Double OSR) { this.OSR = OSR; }
    public void setSmetCost(Double smetCost) {this.smetCost = smetCost;}
    public void setSmetCostSum(Double smetCostSum) {  this.smetCostSum = smetCostSum; }
    public void setSaleHouseSum(Double saleHouseSum) { this.saleHouseSum = saleHouseSum; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setPerSaleExpenses(Double perSaleExpenses) { this.perSaleExpenses = perSaleExpenses; }
    public void setIncomeTax(Double incomeTax) {  this.incomeTax = incomeTax;}


    public  Double getValue() {
        return  this.computeCoefficient();
    }

    private Double getProfit(){
//        return (profit =((perSaleExpenses*saleHouseSum) - OSR - smetCostSum));
        return (profit =((perSaleExpenses*saleHouseSum) - OSR ));
    }

    private Double getAllTaxes(){
        this.getProfit();
        return (profit/quantity.doubleValue())*incomeTax;
    }


/*!******************************************************************************************************************
*                                                                                                      Static Methods
/********************************************************************************************************************/

    public static CoefficientService createCoefficient(){
        return (coefficient = new CoefficientQuery().getCoefficientClass());
    }

    public static Double getCurrentValue(){
        return  coefficient.getValue();
    }

    public static Double getCurrentTaxes(){
        return  coefficient.getAllTaxes();
    }


}
