
package report.models.coefficient;


public class Coefficient {

    private Integer quantity;
    private Double COEFFICIENT;
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
    
    public Coefficient(){    }

    
/*!******************************************************************************************************************
*                                                                                                            Methods
/********************************************************************************************************************/     
    private Double computeCoefficient(){
        double profit = (perSaleExpenses*saleHouseSum) - OSR - smetCostSum;
        double nonRoundCoefficient = (siteExpenses 
                                        - OSR/quantity.doubleValue()
                                        - (profit/quantity.doubleValue())*incomeTax)
                                        /smetCost;
        //Roun COEFFICIENT to #.00
        COEFFICIENT = Math.floor(nonRoundCoefficient * 100) / 100;
        return COEFFICIENT;
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
        

    public  Double getCoefficientValue() {
        return  this.computeCoefficient();
    }
    
    
    
    
}
