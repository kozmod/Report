package report.entities.items.counterparties;

import javafx.collections.ObservableList;
import report.entities.items.expenses.ExpensesDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.beck.sql.SQLconnector;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReqBankDAO {
    //common data
    private static final String SQL      = "SELECT * FROM [dbo].[Count_Req_Bank]  WHERE  id_Count = ? AND dell =0";
    private static final String SQL_TABLE= "[dbo].[Count_Req_Bank]";
    private static final String CATEGORY = "Банковские реквизиты";
    //columns
    private static String BANK_NAME  = "BankName";
    private static String BIC        = "BIC";
    private static String ACC_NUMBER = "AccNumber";
    private static String COR_ACC    = "CorAcc";


    public List<ObjectPSI> getBank(int countId){
        List<ObjectPSI> list = new ArrayList<>();
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1,countId);
            if(pstmt.execute()){
                try(ResultSet rs = pstmt.getResultSet()){
                    while (rs.next()){
                        list.add(
                                new ObjectPSI<>("Наименование банка",
                                        CATEGORY,
                                        "Организация",
                                        rs.getString(BANK_NAME),
                                        BANK_NAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("БИК",
                                        CATEGORY,
                                        "БИК банка",
                                        new BigInteger(rs.getString(BIC)),
                                        BIC,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Номер Счета",
                                        CATEGORY,
                                        "Номер Счета",
                                        new BigInteger(rs.getString(ACC_NUMBER)),
                                        ACC_NUMBER,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Креспондентский счет",
                                        CATEGORY,
                                        "Креспондентский счет",
                                        new BigInteger(rs.getString(COR_ACC)),
                                        COR_ACC,
                                        SQL_TABLE
                                )
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
