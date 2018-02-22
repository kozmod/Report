package report.entities.items.counterparties;

import javafx.collections.FXCollections;
import report.entities.items.expenses.ExpensesDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.beck.sql.SQLconnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReqCommonDAO extends AbstractReqDAO{
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
    //name


    @Override
    public String getSqlTableName() {
        return ReqCommonDAO.SQL_TABLE;
    }

    @Override
    public <HEIR extends List<ObjectPSI>> HEIR getData() {
        return null;
    }

    @Override
    public List<ObjectPSI> getByID(int countId){
        List<ObjectPSI> list = FXCollections.observableArrayList(ObjectPSI.extractor());
        Map<String, ObjectPSI> map = this.getEmptyItems();
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1,countId);
            if(pstmt.execute()){
                try(ResultSet rs = pstmt.getResultSet()){
                    if (rs.next()){
                        ReqDaoUtils.setID(rs.getLong("id"),map);
                        map.get(OGRN).setValue(rs.getString(OGRN));
                        map.get(DATE_OGRN).setValue(LocalDate.ofEpochDay(rs.getInt(DATE_OGRN)));
                        map.get(INN).setValue(rs.getInt(INN));
                        map.get(KPP).setValue(rs.getInt(KPP));
                        map.get(ADRESS_L).setValue(rs.getString(ADRESS_L));
                        map.get(ADRESS_F).setValue(rs.getString(ADRESS_F));
                        map.get(ADRESS_P).setValue(rs.getString(ADRESS_P));
                    }

                }
            }
            list.addAll(map.values());
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    public Map<String, ObjectPSI> getEmptyItems() {
        return ReqDaoUtils
                .getEmptyItems(
                        new ObjectPSI<>("ОГРН",
                                CATEGORY,
                                "ОГРН",
                                "0",
                                OGRN,
                                SQL_TABLE,
                                "\\d{13}"
                        ),
                        new ObjectPSI<>("Дата присвоения ОГРН",
                                CATEGORY,
                                "Дата присвоения ОГРН",
                                LocalDate.ofEpochDay(LocalDate.now().toEpochDay()),
                                DATE_OGRN,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("ИНН",
                                CATEGORY,
                                "ИНН",
                                "0",
                                INN,
                                SQL_TABLE,
                                "\\d{9}"
                        ),
                        new ObjectPSI<>("КПП",
                                CATEGORY,
                                "КПП",
                                "0",
                                KPP,
                                SQL_TABLE,
                                "\\d{9}"
                        ),
                        new ObjectPSI<>("Юридический Адрес",
                                CATEGORY,
                                "Юридический Адрес",
                                "",
                                ADRESS_L,
                                SQL_TABLE,
                                ".{1,100}"
                        ),
                        new ObjectPSI<>("Фактический Адрес",
                                CATEGORY,
                                "Фактический Адрес",
                                "",
                                ADRESS_F,
                                SQL_TABLE,
                                ".{1,100}"
                        ),
                        new ObjectPSI<>("Почтовый Адрес",
                                CATEGORY,
                                "Почтовый Адрес",
                                "",
                                ADRESS_P,
                                SQL_TABLE,
                                ".{1,100}"
                        )
                );
    }

}
