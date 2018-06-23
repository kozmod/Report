package report.entities.items.counterparties;

import javafx.collections.FXCollections;
import report.entities.items.expenses.ExpensesDao;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.sql.SqlConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReqBankDao extends AbstractReqDao {

    //common data
    private static final String SQL = "SELECT * FROM [dbo].[Count_Req_Bank]  WHERE  id_Count = ? AND dell =0";
    private static final String SQL_TABLE = "[dbo].[Count_Req_Bank]";
    private static final String CATEGORY = "Банковские реквизиты";
    //columns
    private static String BANK_NAME = "BankName";
    private static String BIC = "BIC";
    private static String ACC_NUMBER = "AccNumber";
    private static String COR_ACC = "CorAcc";

    @Override
    public String getSqlTableName() {
        return ReqBankDao.SQL_TABLE;
    }

    @Override
    public <HEIR extends List<ObjectPSI>> HEIR getData() {
        return null;
    }

    public List<ObjectPSI> getByID(int countId) {
        List<ObjectPSI> list = FXCollections.observableArrayList(ObjectPSI.extractor());
        Map<String, ObjectPSI> map = this.getEmtyItems();
        try (Connection connection = SqlConnector.getInstance();
             PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1, countId);
            if (pstmt.execute()) {
                try (ResultSet rs = pstmt.getResultSet()) {
                    if (rs.next()) {
                        ReqDaoUtils.setID(rs.getLong("id_Count"), map);
                        map.get(BANK_NAME).setValue(rs.getString(BANK_NAME));
                        map.get(BIC).setValue(rs.getInt(BIC));
                        map.get(ACC_NUMBER).setValue(rs.getString(ACC_NUMBER));
                        map.get(COR_ACC).setValue(rs.getString(COR_ACC));
                    }
                }
            }
            list.addAll(map.values());
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Map<String, ObjectPSI> getEmtyItems() {
        return ReqDaoUtils
                .getEmptyItems(
                        new ObjectPSI<>("Наименование банка",
                                CATEGORY,
                                "Организация",
                                "",
                                BANK_NAME,
                                SQL_TABLE,
                                ".{1,50}"
                        ),
                        new ObjectPSI<>("БИК",
                                CATEGORY,
                                "БИК банка",
                                0,
                                BIC,
                                SQL_TABLE,
                                "\\d{9}"
                        ),
                        new ObjectPSI<>("Номер Счета",
                                CATEGORY,
                                "Номер Счета",
                                "",
                                ACC_NUMBER,
                                SQL_TABLE,
                                "\\d{20}"
                        ),
                        new ObjectPSI<>("Креспондентский счет",
                                CATEGORY,
                                "Креспондентский счет",
                                "",
                                COR_ACC,
                                SQL_TABLE,
                                "\\d{20}"
                        )
                );
    }

}
