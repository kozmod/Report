
package report.models.coefficient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import report.controllers.showEstLayoutController.Est;

import report.view_models.data_models.DecimalFormatter;
import report.entities.items.variable.ItemPropertiesFAO;
import report.models.sql.SQLconnector;
import report.usage_strings.FileFields;
import report.usage_strings.SQL;


public class CoefficientQuery {
    /**
     * Getter to list of variable -> Coefficient variable.
     * @return f (Coefficient)
     */
    public Coefficient getCoefficientClass() {
        Coefficient coefficient = new Coefficient();
        
        String siteNumber = Est.Common.getSiteSecondValue(SQL.Site.SITE_NUMBER);
        String contractor = Est.Common.getSiteSecondValue(SQL.Site.CONTRACTOR);
        
        Properties variableProperties = new ItemPropertiesFAO().getProperties();
        Double pse  = DecimalFormatter.stringToDouble(variableProperties.get(FileFields.FormulaVar.PER_SALE_EXPENSES));
        Double iTax = DecimalFormatter.stringToDouble(variableProperties.get(FileFields.FormulaVar.INCOM_TAX));

          try(Connection connection = SQLconnector.getInstance();
              PreparedStatement pstmt = connection.prepareStatement( "execute Coeff_TEST ?,? ");) {
                    pstmt.setString   (1, siteNumber);
                    pstmt.setString   (2, contractor);
                    
                    ResultSet rs = pstmt.executeQuery();
               while(rs.next()){
                    coefficient.setOSR            (rs.getDouble(SQL.Formula.OSR));
                    coefficient.setQuantity       (rs.getInt(SQL.Formula.QUANTITY));
                    coefficient.setSaleHouseSum   (rs.getDouble(SQL.Formula.SALE_HOUSE_SUM_ALL));
                    coefficient.setSiteExpenses   (rs.getDouble(SQL.Formula.SITE_EXPESES));
                    coefficient.setSmetCost       (Est.Common.getSiteSecondValue(SQL.Site.SMET_COST));
                    coefficient.setSmetCostSum    (rs.getDouble(SQL.Formula.SMET_COST_SUM_ALL));
                    coefficient.setPerSaleExpenses(pse);
                    coefficient.setIncomeTax      (iTax);    
               }
           } catch (SQLException ex) {
               Logger.getLogger(CoefficientQuery.class.getName()).log(Level.SEVERE, null, ex);
           }

        return  coefficient;
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
