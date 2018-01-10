
package report.models.sql.sqlQuery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import report.entities.items.account.AccountDAO;
import report.entities.items.account.AccountTVI;
import report.models.DiffList;
import report.models.sql.SQLconnector;


public class InsertFileXLSQuery {
    
    
    public InsertFileXLSQuery(){}
    
    
    public  void insertRowsFromXls_Site_Numeric(String FilePath){
         
               FileInputStream ExcelFile = null;
               Sheet sheet = null;                                              //Sheet
               Row row = null;

               Connection connection = SQLconnector.getInstance();
                
        try{
               switch (FilenameUtils.getExtension(FilePath)){                    // chooose file tipe
                    case "xls": 
                        PreparedStatement pstmt_xls 
                                = connection.prepareStatement("INSERT into dbo.[Estimate]  ("
                                            + "[SiteNumber]"
                                            + ",[TypeHome]"
                                            + ",[Contractor]"
                                            + ",[JM_name]"
                                            + ",[JobsOrMaterials]"
                                            + ",[BindedJob]"                    //BindenJob
                                            + ",[Value]"
                                            + ",[Unit]"
                                            + ",[Price_one]"
                                            //+ ",[Price_sum]" 
                                            + ",[BuildingPart]"
                                            + ",[TableType]"+ ")"
                               + "values (?,?,?,?,?,?,?,?,?,?,?)");
                        
                        ExcelFile = new FileInputStream(new File(FilePath));
                        HSSFWorkbook wbXls = new HSSFWorkbook(ExcelFile);
                        sheet = wbXls.getSheetAt(0);
                    
                        //CellReference ref = new CellReference("D2"); // type Home 1
                        
                            
                            for(int i = 3, c = 0, v =0; i<9; i++, c +=5 , v++){
                            
                                row = sheet.getRow(1);
                                Cell cellTyprHome = row.getCell(i);
                                String bildingPart =null;
                                String bindedJob  = null;
                                
                                
                                for(int j = 6 ; i<sheet.getLastRowNum(); j++){
                                    row = sheet.getRow(j);
                                   
                                        if(row.getCell(1).getStringCellValue().equals("ФУНДАМЕНТ")){
                                            j++;
                                            bildingPart = "ФУНДАМЕНТ";
                                        }
                                        if(row.getCell(1).getStringCellValue().equals("СТЕНЫ, ПЕРЕКРЫТИЯ")){
                                            j++;
                                            bildingPart = "СТЕНЫ, ПЕРЕКРЫТИЯ";
                                        }
                                        if(row.getCell(1).getStringCellValue().equals("КРОВЛЯ")){
                                            j++;
                                            bildingPart = "КРОВЛЯ";
                                        }
                                        if(row.getCell(1).getStringCellValue().equals("ПРОЕМЫ")){
                                            j++;
                                            bildingPart = "ПРОЕМЫ";
                                        }
                                        if(row.getCell(1).getStringCellValue().equals("ОТДЕЛОЧНЫЕ РАБОТЫ")){
                                            j++;
                                            bildingPart = "ОТДЕЛОЧНЫЕ РАБОТЫ";
                                        }
                                        if(row.getCell(1).getStringCellValue().equals("ИТОГО ПЗ:")){
                                        break;
                                        }
                                        
                                   
                                        
                                    row = sheet.getRow(j);
                                            pstmt_xls.setString(1,"0");                                                                 //SiteNumber
                                            pstmt_xls.setString(2,cellTyprHome.toString());                                             //TypeHome
                                            pstmt_xls.setString(3,"0");                                                                 //Contractor
                                            pstmt_xls.setString(4,row.getCell(1).toString());                                           //JM_Name
                                            pstmt_xls.setDouble(7,(Math.round(row.getCell(3 + v).getNumericCellValue()*100.00)/100.00));  //Value 
                                            pstmt_xls.setString(8,row.getCell(2).toString());                                           //Unit
                                    
                                    
                                        if(row.getCell(10 + c).getNumericCellValue() != 0.00 && row.getCell(11 + c).getNumericCellValue() != 0.00){
                                            bindedJob = row.getCell(1).toString();
                                            
                                            pstmt_xls.setString(5,"Работа");                                                               //Job or Mat
                                            pstmt_xls.setString(6, "-");                                                                //Binden Job
                                            pstmt_xls.setDouble(9, (Math.round(row.getCell(10 + c).getNumericCellValue()*100.00)/100.00));
                                           // pstmt_xls.setDouble(9, (Math.round(row.getCell(11 + c).getNumericCellValue()*100.0)/100.0));  
                                        }else{
                                            
                                            pstmt_xls.setString(5,"Материал");
                                            pstmt_xls.setString(6,bindedJob);
                                            pstmt_xls.setDouble(9, (Math.round(row.getCell(12 + c).getNumericCellValue()*100.00)/100.00));
                                           // pstmt_xls.setDouble(9, (Math.round(row.getCell(13 + c).getNumericCellValue()*100.0)/100.0));  

                                        }
                                           
                                    
                                            pstmt_xls.setString(10,bildingPart );
                                            pstmt_xls.setInt(11, 0);                                //TableWrapper Type 0-Madel
                                            pstmt_xls.execute();
                                            
                                            
                                }
                            }    
                    break;
                    case "xlsx": 
                        PreparedStatement  pstmt_xlsx
                            = connection.prepareStatement("INSERT into dbo.[Site]  ("
                                            + "[SiteNumber]"
                                            + ",[TypeHome]"
                                            + ",[NContract]"
                                            + ",[DateContract]"
                                            + ",[SmetCost]"
                                            + ",[SumCost]"
                                            + ",[FinishBuilding]"
                                            + ",[Contractor]"
                                            + ",[CostHouse]"
                                            + ",[CostSite]"
                                            + ",[SaleClients]"
                                            + ",[DebtClients]"
                                            + ",[StatusPayment]"
                                            + ",[StatusJobs]"
                                            + ",[QueueBuilding]"
                                                +",[SaleHouse]"
                                                +",[SiteTypeID]"
                                    
                                                
//                                                + ",[incom]"
//                                                + ",[overheads]"
//                                                + ",[taxes]"
                                            +")"
                               + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ExcelFile = new FileInputStream(new File(FilePath));
                        System.out.println(ExcelFile.toString());
                        XSSFWorkbook  wbXlsX = new XSSFWorkbook(ExcelFile);
                        sheet = wbXlsX.getSheetAt(0);
                  
                        Iterator rows = sheet.rowIterator();
                        row = (XSSFRow) rows.next();
//                        Iterator cells = row.cellIterator();
                        // goto second row
                            while(rows.hasNext()){
                                row = (XSSFRow) rows.next();
                                Cell firstCell = row.getCell(0);
                                if(firstCell !=null){
                                    
//                                     System.out.println((int)(Math.round(row.getCell(3).getNumericCellValue()*100)/100));
                                       pstmt_xlsx.setString(1, firstCell.toString());
                                       pstmt_xlsx.setString(2, row.getCell(1).toString());
                                       pstmt_xlsx.setString(3, row.getCell(2).toString());
//                                       pstmt_xlsx.setInt(4, (int)(Math.round(row.getCell(3).getNumericCellValue()*100)/100));

                                       pstmt_xlsx.setInt   (4, dateParser(row.getCell(3).toString()));

                                       //SmetCost
                                       pstmt_xlsx.setDouble(5, (Math.round(row.getCell(4).getNumericCellValue()*100.00)/100.00));
                                       pstmt_xlsx.setDouble(6, (Math.round(row.getCell(5).getNumericCellValue()*100.00)/100.00));
                                       pstmt_xlsx.setInt   (7, dateParser(row.getCell(6).toString()));
                                       pstmt_xlsx.setString(8, row.getCell(7).toString());
                                       
                                       //CostHouse == SmetCost (cell #4)
                                       pstmt_xlsx.setDouble(9, (Math.round(row.getCell(4).getNumericCellValue()*100.00)/100.00));
                                       pstmt_xlsx.setDouble(10, (Math.round(row.getCell(9).getNumericCellValue()*100.00)/100.00));
                                       pstmt_xlsx.setDouble(11, (Math.round(row.getCell(10).getNumericCellValue()*100.00)/100.00));
                                       pstmt_xlsx.setDouble(12, (Math.round(row.getCell(11).getNumericCellValue()*100.00)/100.00));
                                       pstmt_xlsx.setString(13, row.getCell(12).toString());
                                       pstmt_xlsx.setString(14, row.getCell(13).toString());
                                       pstmt_xlsx.setString(15, row.getCell(14).toString());
                                      
                                       
                                       //SaleHouse (cell #8)
                                       pstmt_xlsx.setDouble(16, (Math.round(row.getCell(8).getNumericCellValue()*100.00)/100.00));
                                       pstmt_xlsx.setDouble(17, 1);
                                       
                                       
                                       //incom - overheads -taxes
//                                       pstmt_xlsx.setObject(17, (float) 8.7);
//                                       pstmt_xlsx.setObject(18, (float) 2.5);
//                                       pstmt_xlsx.setObject(19, (float) 1.5);
                                       //execute
//                                       pstmt_xlsx.execute();  
                                        pstmt_xlsx.addBatch();
                                }     
                            }
                            pstmt_xlsx.executeBatch();
                    break;
                 
                    default: 
                    break;
                    } 
             
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
//    public static void insertRowsFromXls_Account(String FilePath) {
//
//        
//        try {
//            FileInputStream ExcelFile = null;
//            Sheet sheet = null;                                              //Sheet
//            Row row = null;
//            PreparedStatement  pstmt_acc
//                    = connection.prepareStatement("INSERT into dbo.[Account] ("
//                            + "[Date]"
//                            + ",[Num]"
//                            + ",[ITN_CLient]"
//                            + ",[Name_Client]"
//                            + ",[AccNum_Client]"
//                            + ",[BIC_Cor]"
//                            + ",[AccNum_Cor]"
//                            + ",[Name_Cor]"
//                            + ",[VO]"
//                            + ",[Description]"
//                            + ",[Deb]"
//                            + ",[Cred]"
//                            + ")"
//                            + "values (?,?,?,?,?,?,?,?,?,?,?,?)");
////            HSSFWorkbook wbXls = new HSSFWorkbook(ExcelFile);
////                ExcelFile = new FileInputStream(new File(FilePath));
////                XSSFWorkbook  wbXlsX = new XSSFWorkbook(ExcelFile);
////                sheet = wbXlsX.getSheetAt(0);
//
//            Workbook workbook = WorkbookFactory.create(new File(FilePath));
//            sheet = workbook.getSheetAt(0);
//       
//            Iterator rows = sheet.rowIterator();
////            row = (XSSFRow)  sheet.getRow(44);
//                
//            
//
////            System.out.println(row.getCell(0).formatNumber());
////            System.out.println(dateParser(row.getCell(0).formatNumber()));
////            System.out.println(Long.parseLong(row.getCell(2).getStringCellValue()));
//////            
//             while(rows.hasNext()){
//                 row =  (Row) rows.next();
//                 if(row.getCell(0).getStringCellValue().length() == 10){
////                 System.out.println(dateParser(row.getCell(0).formatNumber()));
//                 pstmt_acc.setInt    (1, dateParser(row.getCell(0).formatNumber()));
//                 pstmt_acc.setInt    (2, Integer.parseInt(row.getCell(1).getStringCellValue()));
//                 pstmt_acc.setString (3, row.getCell(2).getStringCellValue());
//                 pstmt_acc.setString (4, row.getCell(3).getStringCellValue());
//                 pstmt_acc.setString (5, row.getCell(4).getStringCellValue());
//                 pstmt_acc.setString (6, row.getCell(5).getStringCellValue());
//                 pstmt_acc.setString (7, row.getCell(6).getStringCellValue());
//                 pstmt_acc.setString (8, row.getCell(7).getStringCellValue());
//                 pstmt_acc.setInt    (9, Integer.parseInt(row.getCell(8).getStringCellValue()));
//                 pstmt_acc.setString (10, row.getCell(9).getStringCellValue());
//                 
//                    String debString = row.getCell(10).formatNumber();
//                    debString = debString.replace(String.valueOf((char) 160),"");
//                 pstmt_acc.setDouble  (11, (Math.round(Double.parseDouble(debString)*100.00)/100.00));
//                 
//                    String credString = row.getCell(11).getStringCellValue();
//                    credString = credString.replace(String.valueOf((char) 160),"");
//                 pstmt_acc.setDouble  (12, (Math.round(Double.parseDouble(credString)*100.00)/100.00));
//                 
//                 pstmt_acc.execute(); 
//                 
//                }else{
//                     
//                 }
//             }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (EncryptedDocumentException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidFormatException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }
//  
//    }
    
    public  void insertRowsFromXls_Account(String FilePath) {
        
        ObservableList<AccountTVI> AccObs = this.compareAccount(FilePath);
        
        String psmtmtString = "INSERT into dbo.[Account] ("
                            + "[Date]"
                            + ",[Num]"
                            + ",[ITN_CLient]"
                            + ",[Name_Client]"
                            + ",[AccNum_Client]"
                            + ",[BIC_Cor]"
                            + ",[AccNum_Cor]"
                            + ",[Name_Cor]"
                            + ",[VO]"
                            + ",[Description]"
                            + ",[Deb]"
                            + ",[Cred]"
                            + ",[OutgoingRest]"
                            + ")"
                            + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try(Connection connection = SQLconnector.getInstance();
            PreparedStatement  pstmt_acc = connection.prepareStatement(psmtmtString);){  
                for(AccountTVI row : AccObs){
                    pstmt_acc.setInt    (1, row.getDate());
                    pstmt_acc.setInt    (2, row.getNum());
                    pstmt_acc.setString (3, row.getITN_Client());
                    pstmt_acc.setString (4, row.getName_Client());
                    pstmt_acc.setString (5, row.getAccNum_Client());
                    pstmt_acc.setString (6, row.getBIC_Cor());
                    pstmt_acc.setString (7, row.getAccNum_Cor());
                    pstmt_acc.setString (8, row.getName_Cor());
                    pstmt_acc.setInt    (9, row.getVO());
                    pstmt_acc.setString (10, row.getDescription());
                    pstmt_acc.setDouble (11, row.getDeb());
                    pstmt_acc.setDouble (12,row.getCred() );
                    pstmt_acc.setDouble (13,row.getOutgoingRest());

                    pstmt_acc.addBatch();
                }
            pstmt_acc.executeBatch();
        } catch (SQLException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
    

    //DO NOT Insert - only compare Accounts 
    private ObservableList<AccountTVI> compareAccount(String FilePath) {
        Sheet sheet = null;                                              
        Row row = null;
        double incomingRest;

        
        ObservableList<AccountTVI> existAccObs = new AccountDAO().getList(0,0);
        ObservableList<AccountTVI> xlsAccObs = FXCollections.observableArrayList();


        try {
            File file = new File(FilePath);

            Workbook workbook = WorkbookFactory.create(file);
            sheet = workbook.getSheetAt(0);

            //grt Incoming Rest
            Cell cell = sheet.getRow(2).getCell(10);
            incomingRest = Double.parseDouble(cell
                    .getStringCellValue()
                    .replaceAll("RUR","")
                    .replaceAll(String.valueOf((char) 160),"")
            );


            Iterator rows = sheet.rowIterator();
            while(rows.hasNext()){
                row =  (Row) rows.next();
                if(row.getCell(0).toString().length() == 10){
                    String debString = row.getCell(10).toString();
//                    debString = debString.replace(String.valueOf((char) 160),"");
                    double d = Double.parseDouble(row.getCell(10).toString().replace(String.valueOf((char) 160),""));
                    String credString = row.getCell(11).getStringCellValue();
//                    credString = credString.replace(String.valueOf((char) 160),"");
                    double c = Double.parseDouble(row.getCell(11).toString().replace(String.valueOf((char) 160),""));

                    xlsAccObs.add(new AccountTVI(dateParser(row.getCell(0).getStringCellValue()),
                            Integer.parseInt(row.getCell(1).toString()),
                            row.getCell(2).toString(),
                            row.getCell(3).toString(),
                            row.getCell(4).toString(),
                            row.getCell(5).toString(),
                            row.getCell(6).toString(),
                            row.getCell(7).toString(),
                            Integer.parseInt(row.getCell(8).toString()),
                            row.getCell(9).toString(),
                            d,
                            c,
                            ( incomingRest = (incomingRest*100 + c*100 - d*100)/100 )
                    ));
//                     System.out.println(xlsAccObs.saveEst(i).getDate());
//                     i++;
                }
            }
            
            
//            intersection = FXCollections.observableArrayList (existAccObs);
//            intersection.retainAll(xlsAccObs);
//            
//            result = FXCollections.observableArrayList (existAccObs);
//            result.addAll(xlsAccObs);
//            result.removeAll(intersection);
//        for (AccTableItem x  : result){
//                 System.err.println(LocalDate.ofEpochDay( x.getDate()) + "  "+  x.getName_Client());
//
//         }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EncryptedDocumentException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(InsertFileXLSQuery.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.out.println("dif " + new DiffList(existAccObs, xlsAccObs).newElements().size());
//        System.out.println("existAccObs "+ existAccObs.size());
//        System.out.println("xlsAccObs "+ xlsAccObs.size());

        return FXCollections.observableArrayList(new DiffList(existAccObs, xlsAccObs).newElements());
    }

  

    
    
//    public static void insertRowsFromXls_Site_String(String FilePath){
//        
//         try {
//               FileInputStream ExcelFile = null;
//               Sheet sheet = null;                                              //Sheet
//              
//               
//                connection = SQLconnector.getInstance();
//                PreparedStatement  pstmt
//                       = connection.prepareStatement("INSERT into dbo.[Site]  ("
//                                            + "[SiteNumber]"
//                                            + ",[TypeHome]"
//                                            + ",[NContract]"
//                                            + ",[DateContract]"
//                                            + ",[SmetCost]"
//                                            + ",[SumCost]"
//                                            + ",[FinishBilding]"
//                                            + ",[Contractor]"
//                                            + ",[CostHouse]"
//                                            + ",[CostSite]"
//                                            + ",[SaleClients]"
//                                            + ",[DebtClients]"
//                                            + ",[StatusPeiment]"
//                                            + ",[StatusJobs]"
//                                            + ",[QueueBilding]" + ")"
//                               + "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//    
//               switch (FilenameUtils.getExtension(FilePath)){                    // chooose file tipe
//                    case "xls": 
//                        ExcelFile = new FileInputStream(new File(FilePath));
//                        Workbook wbXls = new HSSFWorkbook(ExcelFile);
//                        sheet = wbXls.getSheetAt(0);
//                       
//                    break;
//                    case "xlsx": 
//                        ExcelFile = new FileInputStream(new File(FilePath));
//                        XSSFWorkbook  wbXlsX = new XSSFWorkbook(ExcelFile);
//                        sheet = wbXlsX.getSheetAt(0);
//                  
//                        
//                        Iterator rows = sheet.rowIterator();
//                        Row row = (XSSFRow) rows.next();                        // goto second row
//                        //row = (XSSFRow) rows.next();
//                        //Iterator cells =row.cellIterator();
//                            while(rows.hasNext()){
//                                int cellNumb = 1;
//                                row = (XSSFRow) rows.next();
//                                Iterator cells = row.cellIterator();
//                                Cell firstCell = row.getCell(0);                //check => First Cell != NUll-Cells
//                                    if(firstCell !=null){
//                                        while (cells.hasNext()){
//                                            XSSFCell cell = (XSSFCell) cells.next();
//                                            //sqlLoader.getPstmt().setString(cellNumb, cell.formatNumber());
//                                            pstmt.setString(cellNumb, cell.formatNumber());
//                                            cellNumb++;  
//                                        }
//                                        pstmt.execute();
//                                        
//                                    }
//                            }
//                    break;
//                   
//                   default: break;
//                    } 
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(commonSQL_INSERT.class.getName()).log(Level.SEVERE, null, ex);
//        }  
//    }

    
    
    
    
    
//date parcer to epoch (from 1970)s ==============================================================================================================    
    private static int dateParser(String data){
        int epochDay;
        DateTimeFormatter dtf = null;
        if(data.length() != 0){
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            if(data.length() == 10){
                dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            }else {
                dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            }    
            LocalDate ld = LocalDate.parse(data, dtf);
            epochDay =(int) ld.toEpochDay();
        }else{
            epochDay = 0;
        }
        return epochDay;
    }
}
