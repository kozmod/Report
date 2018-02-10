
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.JVM)
public class DaoTests {


    @Test
    @DisplayName("Общие параметры")
//    @Disabled
    public void firstTest(){

//        final String sqlQuery = "execute [dbo].[INSERT_DIC_COUNT_NAME] @Name = ?, @New_Name= ? ";
//
//            try (Connection connection = SQLconnector.getInstance();
//                 CallableStatement cstmt = connection.prepareCall(sqlQuery)) {
//                cstmt.setString(1, "-1");
//                cstmt.setString(2, "AAAAAdAAA");
////            execute  [dbo].[INSERT_DIC_COUNT_NAME] @Name = '-1', @New_Name= 'ТЕСТ_2'
//                cstmt.execute();
//                System.out.println(cstmt.getResultSet());
//                System.out.println(cstmt.getMoreResults());
//                System.out.println(cstmt.getResultSet());
//                if(cstmt.getMoreResults()){
//                    try (ResultSet rs = cstmt.getResultSet()) {
//                        while (rs.next()) {
//                            System.out.println("id of new  value " + rs.getInt("ID"));
//                        }
//                    }
//                }
//
//            } catch (SQLException ex) {
//                Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }

//        final String sqlAddCount = "execute [dbo].[INSERT_DIC_COUNT_NAME] @Name = ?, @New_Name= ? ";
//        final String sqlAddFRK = "INSERT INTO [dbo].[FRK_ACCOUNT_COUNT_TYPE_FORM] ([ID_IDEAL_CORR],[ID_FORM],[ID_COUNT],[ID_TYPE]) VALUES(?,?,?,?) ";
//            int newElementId = -1;
//            try (Connection connection = SQLconnector.getInstance();
//                 CallableStatement cstmt = connection.prepareCall(sqlAddCount)) {
//                cstmt.setString(1, Integer.toString(newElementId));
//                cstmt.setString(2, "adsadada");
//                cstmt.execute();
//                System.out.println(cstmt.getResultSet());
//                System.out.println(cstmt.getMoreResults());
//                System.out.println(cstmt.getResultSet());
//                if(cstmt.getMoreResults()){
//                    try (ResultSet rs = cstmt.getResultSet()) {
//                        System.out.println("AAAAAAAAAa");
//                        while (rs.next()) {
//                            System.out.println("DDDDDDDDDDDDDDDDDDDDD");
//                            newElementId = rs.getInt("ID");
//                            if(newElementId != -1){
//                                System.out.println(" ++++++++ "+ newElementId);
//                            }
//                        }
//                    }
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(ExpensesDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }

    }
}
