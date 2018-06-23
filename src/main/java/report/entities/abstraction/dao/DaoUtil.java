package report.entities.abstraction.dao;

import report.entities.items.expenses.ExpensesDao;
import report.models.sql.SqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public interface DaoUtil<K, V> {

    default Map<K, V> getPairValue(String firstColumn, String secondColumn, String sql) {
        Map<K, V> map = new HashMap<>();
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (pstmt.execute()) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    while (rs.next()) {
                        map.put(
                                (K) rs.getObject(firstColumn),
                                (V) rs.getObject(secondColumn)

                        );
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }
}
