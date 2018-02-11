package report.entities.items.counterparties;

import report.entities.items.expenses.ExpensesDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.beck.sql.SQLconnector;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReqCommonDAO {
    //common data
    private static final String SQL       = "SELECT * FROM [dbo].[Count_Req_Common] WHERE  id_Count = ? AND dell = 0";
    private static final String SQL_TABLE = "[dbo].[Count_Req_Common]";
    private static final String CATEGORY  = "Общие реквизиты";
    //columns
    private static String OGRN       = "OGRN";
    private static String DATE_OGRN  = "DateOGRN";
    private static String INN        = "INN";
    private static String KPP        = "KPP";
    private static String ADRESS_L   = "Adress_L";
    private static String ADRESS_F   = "Adress_F";
    private static String ADRESS_P   = "Adress_P";

    public List<ObjectPSI> getBank(int countId){
        List<ObjectPSI> list = new ArrayList<>();
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1,countId);
            if(pstmt.execute()){
                try(ResultSet rs = pstmt.getResultSet()){
                    if (rs.next()){
                        list.add(new ObjectPSI<>("ОГРН",
                                        CATEGORY,
                                        "ОГРН",
                                        new BigInteger(rs.getString(OGRN)),
                                        OGRN,
                                        SQL_TABLE
                                )
                        );
                        list.add(new ObjectPSI<>("Дата присвоения ОГРН",
                                        CATEGORY,
                                        "Дата присвоения ОГРН",
                                        LocalDate.ofEpochDay(rs.getInt(DATE_OGRN)),
                                        DATE_OGRN,
                                        SQL_TABLE
                                )
                        );
                        list.add(new ObjectPSI<>("ИНН",
                                        CATEGORY,
                                        "ИНН",
                                        new BigInteger(rs.getString(INN)),
                                        INN,
                                        SQL_TABLE
                                )
                        );
                        list.add(new ObjectPSI<>("КПП",
                                        CATEGORY,
                                        "КПП",
                                        new BigInteger(rs.getString(KPP)),
                                        KPP,
                                        SQL_TABLE
                                )
                        );
                        list.add(new ObjectPSI<>("Юридический Адрес",
                                        CATEGORY,
                                        "Юридический Адрес",
                                        rs.getString(ADRESS_L),
                                        ADRESS_L,
                                        SQL_TABLE
                                )
                        );
                        list.add(new ObjectPSI<>("Фактический Адрес",
                                        CATEGORY,
                                        "Фактический Адрес",
                                        rs.getString(ADRESS_F),
                                        ADRESS_F,
                                        SQL_TABLE
                                )
                        );
                        list.add(new ObjectPSI<>("Почтовый Адрес",
                                        CATEGORY,
                                        "Почтовый Адрес",
                                        rs.getString(ADRESS_P),
                                        ADRESS_P,
                                        SQL_TABLE
                                )
                        );
                    }else{

                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
