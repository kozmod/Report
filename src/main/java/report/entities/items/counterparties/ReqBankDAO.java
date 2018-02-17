package report.entities.items.counterparties;

import javafx.collections.FXCollections;
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
        List<ObjectPSI> list =  FXCollections.observableArrayList(ObjectPSI.extractor());
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1,countId);
            if(pstmt.execute()){
                try(ResultSet rs = pstmt.getResultSet()){
                    if(rs.next()){
                        list.add(
                                new ObjectPSI<>("Наименование банка",
                                        CATEGORY,
                                        "Организация",
                                        rs.getString(BANK_NAME),
                                        BANK_NAME,
                                        SQL_TABLE,
                                        ".{1,50}"
                                )
                        );
                        list.add(
                                new ObjectPSI<>("БИК",
                                        CATEGORY,
                                       "БИК банка (8 восем символов)",
                                        rs.getString(BIC),
                                        BIC,
                                        SQL_TABLE,
                                        "\\d{9}"

                                )
                        );
                        list.add(
                                new ObjectPSI<>("Номер Счета",
                                        CATEGORY,
                                        "Номер Счета",
                                        rs.getString(ACC_NUMBER),
                                        ACC_NUMBER,
                                        SQL_TABLE,
                                        "\\d{20}"
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Креспондентский счет",
                                        CATEGORY,
                                        "Креспондентский счет",
                                        rs.getString(COR_ACC),
                                        COR_ACC,
                                        SQL_TABLE,
                                        "\\d{20}"
                                )
                        );
                        //TODO: empty value
                    }else{
                        list.add(
                                new ObjectPSI<>("Наименование банка",
                                        CATEGORY,
                                        "Организация",
                                        "",
                                        BANK_NAME,
                                        SQL_TABLE,
                                        ".{1,50}"
                                )
                        );
                        list.add(
                                new ObjectPSI<>("БИК",
                                        CATEGORY,
                                        "БИК банка",
                                        new BigInteger("0"),
                                        BIC,
                                        SQL_TABLE,
                                        "\\d{9}"
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Номер Счета",
                                        CATEGORY,
                                        "Номер Счета",
                                        new BigInteger("0"),
                                        ACC_NUMBER,
                                        SQL_TABLE,
                                        "\\d{20}"
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Креспондентский счет",
                                        CATEGORY,
                                        "Креспондентский счет",
                                        new BigInteger("0"),
                                        COR_ACC,
                                        SQL_TABLE,
                                        "\\d{20}"
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
