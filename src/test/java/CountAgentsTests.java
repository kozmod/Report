import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import report.entities.items.osr.OSR_DAO;
import report.models.beck.sql.SQLconnector;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@FixMethodOrder(MethodSorters.JVM)
public class CountAgentsTests {
    @Test
    @DisplayName("Counter Agents")
    public void addCountAgentsTestTest(){
        String sql ="";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt  = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            //set false SQL Autocommit
            connection.setAutoCommit(false);

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(OSR_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
