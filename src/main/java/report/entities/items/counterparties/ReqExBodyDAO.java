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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReqExBodyDAO  extends AbstractReqDAO{
    //common data
    private static final String SQL       = "SELECT * FROM [dbo].[Count_Req_ExBody]  WHERE  id_Count = ? AND dell =0";
    private static final String SQL_TABLE = "[dbo].[Count_Req_ExBody]";
    private static final String CATEGORY  = "Исполнительный Орган";
    //columns
    private static String EX_BODY             = "ExBody";
    private static String EX_BODY_NAME        = "ExBody_Name";
    private static String EX_BODY_SURNAME     = "ExBody_Surname";
    private static String EX_BODY_F_NAME      = "ExBody_FName";
    private static String ACTION_BASIS        = "ActionBasis";
    private static String BOOKKEEPER_NAME     = "Bookkeeper_Name";
    private static String BOOKKEEPER_SURNAME  = "Bookkeeper_Surname";
    private static String BOOKKEEPER_F_NAME   = "Bookkeeper_FName";
    private static String ID_SERIES           = "ID_Series";
    private static String ID_NUMBER           = "ID_Number";
    private static String ID_DATE             = "ID_Date";
    private static String ID_TEXT             = "ID_Text";
    private static String ID_CODE             = "ID_Code";

    @Override
    public String getSqlTableName() {
        return ReqExBodyDAO.SQL_TABLE;
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
                    if(rs.next()){
                        ReqDaoUtils.setID(rs.getLong("id_Count"),map);
                        map.get(EX_BODY).setValue(rs.getString(EX_BODY));
                        map.get(EX_BODY_NAME).setValue(rs.getString(EX_BODY_NAME));
                        map.get(EX_BODY_SURNAME).setValue(rs.getString(EX_BODY_SURNAME));
                        map.get(EX_BODY_F_NAME).setValue(rs.getString(EX_BODY_F_NAME));
                        map.get(ACTION_BASIS).setValue(rs.getString(ACTION_BASIS));
                        map.get(BOOKKEEPER_NAME).setValue(rs.getString(BOOKKEEPER_NAME));
                        map.get(BOOKKEEPER_SURNAME).setValue(rs.getString(BOOKKEEPER_SURNAME));
                        map.get(BOOKKEEPER_F_NAME).setValue(rs.getString(BOOKKEEPER_F_NAME));
                        map.get(ID_SERIES).setValue(rs.getInt(ID_SERIES));
                        map.get(ID_NUMBER).setValue(rs.getString(ID_NUMBER));
                        map.get(ID_DATE).setValue(LocalDate.ofEpochDay(rs.getInt(ID_DATE)));
                        map.get(ID_TEXT).setValue(rs.getString(ID_TEXT));
                        map.get(ID_CODE).setValue(rs.getString(ID_CODE));
                    }

                }
            }
            list.addAll(map.values());
        } catch (SQLException ex) {
            Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Map<String, ObjectPSI> getEmptyItems(){
        return ReqDaoUtils
                .getEmptyItems(
                        new ObjectPSI<>("ИО Наименование",
                                CATEGORY,
                                "Наименование типа Исполнительного органа",
                                "",
                                EX_BODY,
                                SQL_TABLE,
                                ".+"
                        ),
                        new ObjectPSI<>("ИО Фамилия",
                                CATEGORY,
                                "Фамилия Исполнительного органа",
                                "",
                                EX_BODY_NAME,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("ИО Имя",
                                CATEGORY,
                                "Имя Исполнительного органа",
                                "",
                                EX_BODY_SURNAME,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("ИО Отчество",
                                CATEGORY,
                                "Отчество Исполнительного органа",
                                "",
                                EX_BODY_F_NAME,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("ИО Основания",
                                CATEGORY,
                                "Основания для заключения договора",
                                "",
                                ACTION_BASIS,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("Главный Бухгалтер:Фамилия",
                                CATEGORY,
                                "Главный Бухгалтер:Фамилия",
                                "",
                                BOOKKEEPER_NAME,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("Главный Бухгалтер:Имя",
                                CATEGORY,
                                "Главный Бухгалтер:Имя",
                                "",
                                BOOKKEEPER_SURNAME,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("Главный Бухгалтер:Отчество",
                                CATEGORY,
                                "Главный Бухгалтер:Отчество",
                                "",
                                BOOKKEEPER_F_NAME,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("Паспорт: Серия",
                                CATEGORY,
                                "Паспорт: Серия",
                                "",
                                ID_SERIES,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("Паспорт: Номер",
                                CATEGORY,
                                "Паспорт: Номер",
                                "",
                                ID_NUMBER,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("Паспорт:Кем выдан",
                                CATEGORY,
                                "Паспорт:Кем выдан",
                                "",
                                ID_TEXT,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("Паспорт:Дата выдачи",
                                CATEGORY,
                                "Паспорт:Дата выдачи",
                                LocalDate.ofEpochDay(LocalDate.now().toEpochDay()),
                                ID_DATE,
                                SQL_TABLE
                        ),
                        new ObjectPSI<>("Паспорт:Код падразделения",
                                CATEGORY,
                                "Паспорт:Код падразделени (6 символов)",
                                "",
                                ID_CODE,
                                SQL_TABLE
                        )

                );
    }



}
