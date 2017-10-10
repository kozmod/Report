
package report.models.coefficient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import report.layoutControllers.estimate.EstimateController.Est;

import report.entities.items.variable.ItemPropertiesFAO;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.models.sql.SQLconnector;
import report.usage_strings.FileFields;
import report.usage_strings.SQL;


public class CoefficientQuery {
    /**
     * Getter to list of variable -> CoefficientService variable.
     * @return f (CoefficientService)
     */
    public CoefficientService getCoefficientClass() {
//        CoefficientService coefficient = new CoefficientService();
        CoefficientService coefficient = null;

        String siteNumber = Est.Common.getSiteSecondValue(SQL.Site.SITE_NUMBER);
        String contractor = Est.Common.getSiteSecondValue(SQL.Site.CONTRACTOR);

        Properties variableProperties = new ItemPropertiesFAO().getProperties();
        Double pse  = new DoubleStringConverter()
                .fromString(
                variableProperties.get(
                        FileFields.FormulaVar.PER_SALE_EXPENSES).toString()
        );
        Double iTax = new DoubleStringConverter().fromString(
                variableProperties.get(
                        FileFields.FormulaVar.INCOM_TAX).toString()
        );

          try(Connection connection = SQLconnector.getInstance();
              PreparedStatement pstmt = connection.prepareStatement( "execute Coeff_TEST ?,? ");) {
                    pstmt.setString   (1, siteNumber);
                    pstmt.setString   (2, contractor);

                    ResultSet rs = pstmt.executeQuery();
               while(rs.next()){
//                    coefficient.setOSR            (rs.getDouble(SQL.Formula.OSR));
//                    coefficient.setQuantity       (rs.getInt(SQL.Formula.QUANTITY));
//                    coefficient.setSaleHouseSum   (rs.getDouble(SQL.Formula.SALE_HOUSE_SUM_ALL));
//                    coefficient.setSiteExpenses   (rs.getDouble(SQL.Formula.SITE_EXPESES));
//                    coefficient.setSmetCost       (Est.Common.getSiteSecondValue(SQL.Site.SMET_COST));
//                    coefficient.setSmetCostSum    (rs.getDouble(SQL.Formula.SMET_COST_SUM_ALL));
//                    coefficient.setPerSaleExpenses(pse);
//                    coefficient.setIncomeTax      (iTax);
                   coefficient = CoefficientService.newCoeffBuilder()
                           .setOSR            (rs.getDouble(SQL.Formula.OSR))
                           .setQuantity       (rs.getInt(SQL.Formula.QUANTITY))
                           .setSaleHouseSum   (this.getSaleCostSumFromFinPlan())
//                           .setSaleHouseSum   (rs.getDouble(SQL.Formula.SALE_HOUSE_SUM_ALL))
                           .setSiteExpenses   (rs.getDouble(SQL.Formula.SITE_EXPESES))
                           .setSmetCost       (Est.Common.getSiteSecondValue(SQL.Site.SMET_COST))
                           .setSmetCostSum    (rs.getDouble(SQL.Formula.SMET_COST_SUM_ALL))
                           .setPerSaleExpenses(pse)
                           .setIncomeTax      (iTax)
                           .build();
               }
           } catch (SQLException ex) {
               Logger.getLogger(CoefficientQuery.class.getName()).log(Level.SEVERE, null, ex);
           }

        return  coefficient;
    }


    /**
     *
     * TEST TO ------> getSaleCostSumFromFinPlan()
     * @return
     */
    //TODO
    private static double getSaleCostSumFromFinPlan(){

        double saleHouseSumFromFinPlan = 0;
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement( "SELECT SUM(F.[SaleCost]*F.[Quantity]) FROM dbo.[FinPlan] F");) {

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                saleHouseSumFromFinPlan = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return saleHouseSumFromFinPlan;
    }



    /**
     * Getter to Site Quantity.
     * @return q (Number)
     */
    public Number getSiteQuantity() {
        int q = 0 ;
          try(Connection connection = SQLconnector.getInstance();
                PreparedStatement pstmt = connection
                        .prepareStatement( "SELECT sum(F.[Quantity]) FROM [dbo].[FinPlan] F WHERE F.[dell] = 0; ");) {
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()){
                    q = rs.getInt(1);
                }
           } catch (SQLException ex) {
               Logger.getLogger(CoefficientQuery.class.getName()).log(Level.SEVERE, null, ex);
           }
        return  q;
    }

    public  void applyCoefficient(String siteNumber, String contractor, double coefficient){

        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt
                    = connection.prepareStatement("execute ComputeK ?,?,? ");) {
                    pstmt.setString   (1, siteNumber);
                    pstmt.setString   (2, contractor);
                    pstmt.setDouble   (3, coefficient);
                    pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(CoefficientQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
