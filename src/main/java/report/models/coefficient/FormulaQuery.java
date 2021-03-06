
package report.models.coefficient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import report.entities.items.counterparties.AgentTVI.CountAgentTVI;
import report.entities.items.variable.VariableTIV_new;
import report.layout.controllers.estimate.EstimateController_old;
import report.layout.controllers.estimate.EstimateController_old.Est;
import report.models.sql.SqlConnector;
import report.usage_strings.SQL;


public class FormulaQuery {
    /**
     * Getter to list of variable -> Formula variable.
     *
     * @return f (Formula)
     */
    public Formula getFormula(final String siteNumber,final  String contractor) {
        Formula formula = null;

        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement("execute Coeff_TEST_2 ?,? ");) {
            pstmt.setString(1, siteNumber);
            pstmt.setString(2, contractor);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                formula = new Formula(
                        rs.getInt(SQL.Formula.QUANTITY),
                        rs.getDouble(SQL.Formula.SITE_EXPESES),
                        rs.getDouble(SQL.Formula.OSR),
                        Est.Common.getSiteSecondValue(SQL.Site.SMET_COST),
                        rs.getDouble(SQL.Formula.SMET_COST_SUM_ALL),
                        rs.getDouble(SQL.Formula.SALE_COSTSUM_FROM_FINPLAN),
//                           pse,
//                           iTax
                        rs.getDouble(VariableTIV_new.SQL.SALE_EXP),
                        rs.getDouble(VariableTIV_new.SQL.INCOME_TAX)
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormulaQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

        return formula;
    }

    public Formula getFormula() {
        return getFormula(
                Est.Common.getSiteSecondValue(SQL.Site.SITE_NUMBER),
                Est.Common.getSiteSecondValue(SQL.Site.CONTRACTOR).toString()
        );
    }

    public Formula getFormula(final String siteNumber,final  CountAgentTVI contractor) {
        return getFormula(siteNumber, contractor.getName());
    }
    /**
     * Getter to Site Quantity.
     *
     * @return q (Number)
     */
    public Integer getSiteQuantity() {
        int q = 0;
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection
                     .prepareStatement("SELECT sum(F.[Quantity]) FROM [dbo].[FinPlan] F WHERE F.[dell] = 0; ");) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                q = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormulaQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return q;
    }

    public void applyCoefficient(String siteNumber, double coefficient) {

        CountAgentTVI contractor = EstimateController_old.Est.Common.getCountAgentTVI();
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt
                     = connection.prepareStatement("execute ComputeK ?,?,? ");) {
            pstmt.setString(1, siteNumber);
            pstmt.setInt(2, contractor.getIdCountConst());
            pstmt.setDouble(3, coefficient);
            pstmt.execute();

        } catch (SQLException ex) {
            Logger.getLogger(FormulaQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
