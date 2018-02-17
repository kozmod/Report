package report.entities.items.counterparties;

import javafx.collections.FXCollections;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RexExBodyDAO {
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



    public List<ObjectPSI> getBank(int countId){
        List<ObjectPSI> list = FXCollections.observableArrayList(ObjectPSI.extractor());
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setInt(1,countId);
            if(pstmt.execute()){
                try(ResultSet rs = pstmt.getResultSet()){
                    if (rs.next()){
                        list.add(
                                new ObjectPSI<>("ИО Наименование",
                                        CATEGORY,
                                        "Наименование типа Исполнительного органа",
                                        rs.getString(EX_BODY),
                                        EX_BODY,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("ИО Фамилия",
                                        CATEGORY,
                                        "Фамилия Исполнительного органа",
                                        rs.getString(EX_BODY_NAME),
                                        EX_BODY_NAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("ИО Имя",
                                        CATEGORY,
                                        "Имя Исполнительного органа",
                                        rs.getString(EX_BODY_SURNAME),
                                        EX_BODY_SURNAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("ИО Отчество",
                                        CATEGORY,
                                        "Отчество Исполнительного органа",
                                        rs.getString(EX_BODY_F_NAME),
                                        EX_BODY_F_NAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("ИО Основания",
                                        CATEGORY,
                                        "Основания для заключения договора",
                                        rs.getString(ACTION_BASIS),
                                        ACTION_BASIS,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Главный Бухгалтер:Фамилия",
                                        CATEGORY,
                                        "Главный Бухгалтер:Фамилия",
                                        rs.getString(BOOKKEEPER_NAME),
                                        BOOKKEEPER_NAME,
                                        SQL_TABLE

                                )
                        );
                        list.add(
                                new ObjectPSI<>("Главный Бухгалтер:Имя",
                                        CATEGORY,
                                        "Главный Бухгалтер:Имя",
                                        rs.getString(BOOKKEEPER_SURNAME),
                                        BOOKKEEPER_SURNAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Главный Бухгалтер:Отчество",
                                        CATEGORY,
                                        "Главный Бухгалтер:Отчество",
                                        rs.getString(BOOKKEEPER_F_NAME),
                                        BOOKKEEPER_F_NAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Паспорт: Серия",
                                        CATEGORY,
                                        "Паспорт: Серия",
                                        rs.getString(ID_SERIES),
                                        ID_SERIES,
                                        SQL_TABLE
                                )
                        );

                        list.add(
                                new ObjectPSI<>("Паспорт: Номер",
                                        CATEGORY,
                                        "Паспорт: Номер",
                                        rs.getString(ID_NUMBER),
                                        ID_NUMBER,
                                        SQL_TABLE
                                )
                        );

                        list.add(
                                new ObjectPSI<>("Паспорт:Кем выдан",
                                        CATEGORY,
                                        "Паспорт:Кем выдан",
                                        rs.getString(ID_TEXT),
                                        ID_TEXT,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Паспорт:Дата выдачи",
                                        CATEGORY,
                                        "Паспорт:Дата выдачи",
                                        LocalDate.ofEpochDay(rs.getInt(ID_DATE)),
                                        ID_DATE,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Паспорт:Код падразделения",
                                        CATEGORY,
                                        "Паспорт:Код падразделени",
                                        rs.getString(ID_CODE),
                                        ID_CODE,
                                        SQL_TABLE
                                )
                        );

                    }else{
                        list.add(
                                new ObjectPSI<>("ИО Наименование",
                                        CATEGORY,
                                        "Наименование типа Исполнительного органа",
                                        "",
                                        EX_BODY,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("ИО Фамилия",
                                        CATEGORY,
                                        "Фамилия Исполнительного органа",
                                        "",
                                        EX_BODY_NAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("ИО Имя",
                                        CATEGORY,
                                        "Имя Исполнительного органа",
                                        "",
                                        EX_BODY_SURNAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("ИО Отчество",
                                        CATEGORY,
                                        "Отчество Исполнительного органа",
                                        "",
                                        EX_BODY_F_NAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("ИО Основания",
                                        CATEGORY,
                                        "Основания для заключения договора",
                                        "",
                                        ACTION_BASIS,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Главный Бухгалтер:Фамилия",
                                        CATEGORY,
                                        "Главный Бухгалтер:Фамилия",
                                        "",
                                        BOOKKEEPER_NAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Главный Бухгалтер:Имя",
                                        CATEGORY,
                                        "Главный Бухгалтер:Имя",
                                        "",
                                        BOOKKEEPER_SURNAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Главный Бухгалтер:Отчество",
                                        CATEGORY,
                                        "Главный Бухгалтер:Отчество",
                                        "",
                                        BOOKKEEPER_F_NAME,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Паспорт: Серия",
                                        CATEGORY,
                                        "Паспорт: Серия",
                                        "",
                                        ID_SERIES,
                                        SQL_TABLE
                                )
                        );

                        list.add(
                                new ObjectPSI<>("Паспорт: Номер",
                                        CATEGORY,
                                        "Паспорт: Номер",
                                        "",
                                        ID_NUMBER,
                                        SQL_TABLE
                                )
                        );

                        list.add(
                                new ObjectPSI<>("Паспорт:Кем выдан",
                                        CATEGORY,
                                        "Паспорт:Кем выдан",
                                        "",
                                        ID_TEXT,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Паспорт:Дата выдачи",
                                        CATEGORY,
                                        "Паспорт:Дата выдачи",
                                        LocalDate.ofEpochDay(LocalDate.now().toEpochDay()),
                                        ID_DATE,
                                        SQL_TABLE
                                )
                        );
                        list.add(
                                new ObjectPSI<>("Паспорт:Код падразделения",
                                        CATEGORY,
                                        "Паспорт:Код падразделени",
                                        "",
                                        ID_CODE,
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

    public Map<String, ObjectPSI> getEmptyItems(){
        Map<String, ObjectPSI> map = new HashMap<>();
        map.put(EX_BODY,
                new ObjectPSI<>("ИО Наименование",
                        CATEGORY,
                        "Наименование типа Исполнительного органа",
                        "",
                        EX_BODY,
                        SQL_TABLE
                )
        );
        map.put(EX_BODY_NAME,
                new ObjectPSI<>("ИО Фамилия",
                        CATEGORY,
                        "Фамилия Исполнительного органа",
                        "",
                        EX_BODY_NAME,
                        SQL_TABLE
                )
        );
        map.put(EX_BODY_SURNAME,
                new ObjectPSI<>("ИО Имя",
                        CATEGORY,
                        "Имя Исполнительного органа",
                        "",
                        EX_BODY_SURNAME,
                        SQL_TABLE
                )
        );
        map.put(EX_BODY_F_NAME,
                new ObjectPSI<>("ИО Отчество",
                        CATEGORY,
                        "Отчество Исполнительного органа",
                        "",
                        EX_BODY_F_NAME,
                        SQL_TABLE
                )
        );
        map.put(ACTION_BASIS,
                new ObjectPSI<>("ИО Основания",
                        CATEGORY,
                        "Основания для заключения договора",
                        "",
                        ACTION_BASIS,
                        SQL_TABLE
                )
        );
        map.put(BOOKKEEPER_NAME,
                new ObjectPSI<>("Главный Бухгалтер:Фамилия",
                        CATEGORY,
                        "Главный Бухгалтер:Фамилия",
                        "",
                        BOOKKEEPER_NAME,
                        SQL_TABLE
                )
        );
        map.put(BOOKKEEPER_SURNAME,
                new ObjectPSI<>("Главный Бухгалтер:Имя",
                        CATEGORY,
                        "Главный Бухгалтер:Имя",
                        "",
                        BOOKKEEPER_SURNAME,
                        SQL_TABLE
                )
        );
        map.put(BOOKKEEPER_F_NAME,
                new ObjectPSI<>("Главный Бухгалтер:Отчество",
                        CATEGORY,
                        "Главный Бухгалтер:Отчество",
                        "",
                        BOOKKEEPER_F_NAME,
                        SQL_TABLE
                )
        );
        map.put(ID_SERIES,
                new ObjectPSI<>("Паспорт: Серия",
                        CATEGORY,
                        "Паспорт: Серия",
                        "",
                        ID_SERIES,
                        SQL_TABLE
                )
        );

        map.put(ID_NUMBER,
                new ObjectPSI<>("Паспорт: Номер",
                        CATEGORY,
                        "Паспорт: Номер",
                        "",
                        ID_NUMBER,
                        SQL_TABLE
                )
        );

        map.put(ID_TEXT,
                new ObjectPSI<>("Паспорт:Кем выдан",
                        CATEGORY,
                        "Паспорт:Кем выдан",
                        "",
                        ID_TEXT,
                        SQL_TABLE
                )
        );
        map.put(ID_DATE,
                new ObjectPSI<>("Паспорт:Дата выдачи",
                        CATEGORY,
                        "Паспорт:Дата выдачи",
                        LocalDate.ofEpochDay(LocalDate.now().toEpochDay()),
                        ID_DATE,
                        SQL_TABLE
                )
        );
        map.put(ID_CODE,
                new ObjectPSI<>("Паспорт:Код падразделения",
                        CATEGORY,
                        "Паспорт:Код падразделени",
                        "",
                        ID_CODE,
                        SQL_TABLE
                )
        );
        return map;
    }


}
