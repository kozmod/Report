
package report.models.printer;

import java.nio.file.Path;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import report.entities.items.KS.TableItemKS;
import report.entities.items.contractor.TableItemContractor;
import report.models_view.data_utils.decimalFormatters.DoubleDFormatter;
import report.usage_strings.SQL;
import report.layoutControllers.EstimateController.Est;
import report.entities.items.TableItem;



public class PrintKS extends AbstractPrinterXML{
    
    
    private Document doc;
    private ObservableList<TableItem> obsKS;
//    private ObservableList<PreviewTableItem>  obsPreTab;
    private String ksNumber, ksDate;
    private TableItemContractor contractorObject;
    

//Constructor =====================================================================================================================    
//    private PrintKS(
//            ObservableList<TableItemKS> obsKS,
////                    ObservableList<PreviewTableItem> obsPreTab,
//                    String ksNumber,
//                    String ksDate) {
//        this.obsKS = obsKS;
////        this.obsPreTab = obsPreTab;
//        this.ksNumber = ksNumber;
//        this.ksDate = ksDate;
//
//        doc = buildDocument("\\lib\\XML_Models\\KS-2.xml");
//        setObjectName();
//        setDates();
//        setNumber();
//        addJMrows();
//
//        saveDocument(System.getProperty("user.dir") + "\\lib\\XML_Models\\КС-2 № " + ksNumber + ".xml");
//    }

    public PrintKS(ObservableList<TableItem> obsKS,TableItemContractor contractor, Path path) {
        this.obsKS    = obsKS;
        this.ksNumber = Integer.toString(((TableItemKS)obsKS.get(0)).getKSNumber());
        this.ksDate   = LocalDate.ofEpochDay(((TableItemKS)obsKS.get(0)).getKSDate()).toString();

        this.contractorObject = contractor;

        doc = buildDocument("\\lib\\XML_Models\\KS-2.xml");
        setObjectName();
        setDates();
        setNumber();
        addJMrows();
        setAdress();
        saveDocument(path.toString());
    }

    public PrintKS(ObservableList<TableItem> obsKS, Path path) {
        this.obsKS    = obsKS;
        this.ksNumber = Integer.toString(((TableItemKS)obsKS.get(0)).getKSNumber());
        this.ksDate   = LocalDate.ofEpochDay(((TableItemKS)obsKS.get(0)).getKSDate()).toString();



        doc = buildDocument("\\lib\\XML_Models\\KS-2.xml");
        setObjectName();
        setDates();
        setNumber();
        addJMrows();
        saveDocument(path.toString());
    }


//    public  PrintKS(String ksNamber) {
//        this.obsKS = (ObservableList<TableItem>) Est.KS.getTabMap().saveEst(ksNamber);
//
//        this.ksNumber = ksNamber;
//        this.ksDate = obsKS.saveEst(2).formatNumber();
//        
//        doc = buildDocument("\\libS\\XML_Models\\KS-2.xml");
//        setObjectName();
//        setDates();
//        setNumber();
//        addJMrows();
//        saveDocument("\\libS\\XML_Models\\КС-2 № " + ksNumber + ".xml");
//    }

    
    
//Methods ==========================================================================================================================
    //Add Name of OBJECT
    private void setObjectName(){

        String text  = new StringBuilder("Объект: ДКП 'Мечта пятницы', ж/дом '',  уч. № ")
                .insert(36, obsKS.get(0).getTypeHome())
                .append( obsKS.get(0).getSiteNumber())
                .toString();
        
        getTargetElement("Home").setTextContent(text);
        
    }
    private void setAdress(){
        String text = new StringBuilder("Объект: ")
                .append( "ООО «")
                .append(contractorObject.getContractor())
                .append( "», ")
                .append(contractorObject.getAdress())
                .toString();

        getTargetElement("Contractor").setTextContent(text);

    }

    //Add Dates of KS
    private void  setDates(){
        
        super.getTargetElement("createData").setTextContent(ksDate);
        getTargetElement("periodFrom").setTextContent(
                LocalDate.ofEpochDay((long) Est.KS.getSiteSecondValue(SQL.Site.DATE_CONTRACT)).toString()
        );
        getTargetElement("periodTo").setTextContent(
            LocalDate.ofEpochDay((long) Est.KS.getSiteSecondValue(SQL.Site.FINISH_BUILDING)).toString()
        );   
    }
       
    private void  setNumber(){
        //saveEst Date contract
        LocalDate dateContract = LocalDate.ofEpochDay((long) Est.KS.getSiteSecondValue(SQL.Site.DATE_CONTRACT));
        String day   = String.format("%02d", dateContract.getDayOfMonth());
        String month = String.format("%02d", dateContract.getMonthValue());
        String year  = Integer.toString(dateContract.getYear());
        
        //set number of KS
        getTargetElement("number").setTextContent("№ " + ksNumber);
        
        //set ->  day / month / year
        getTargetElement("day").setTextContent(day);
        getTargetElement("month").setTextContent(month);  
        getTargetElement("year").setTextContent(year);   
    }
       
    
    //Add Job - Material rows
    private void addJMrows(){
        
        int rowsQuantity = 1;
        String buildingPart = null;
        
        for(TableItem item : obsKS){
            
            
            Element targetRow = getTargetElement("SumRow");

            //CHECK -> Binded Job
            if(!item.getBildingPart().equals(buildingPart)){
                buildingPart = item.getBildingPart();
                 
                Element row = doc.createElement("Row");
                row.setAttribute("ss:StyleID", "s143");
                
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("Number", Integer.toString(rowsQuantity))
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s150")
                                 .setCellValue("String", buildingPart)
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("String", "" )
                                 .build());
                
                targetRow.getParentNode().insertBefore(row, targetRow); 
                rowsQuantity++;
            };
            
            //Set rows
            Element row = doc.createElement("Row");
            row.setAttribute("ss:StyleID", "s143");
            
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("Number", Integer.toString(rowsQuantity))
                                 .build());
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("Number", "" )
                                 .build());
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s172")
                                 .setCellValue("String", item.getJM_name())
                                 .build());
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s67")
                                 .setCellValue("String", "договорная" )
                                 .build());
            row.appendChild( new CellBuilder(doc)
                                 .setCellStyle("s67")
                                 .setCellValue("String", item.getUnit() )
                                 .build());
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s173")
                                 .setCellValue("Number", Double.toString(item.getValue()) )
                                 .build());
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s174")
                                 .setCellValue("Number",new DoubleDFormatter().toString(item.getPrice_one())
                                         .replace(" ","") )
                                 .build());
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s174")
                                 .setCellValue("Number", "" )
                                 .setMultFormula("-1","-2")
                                 .build());
            
            targetRow.getParentNode().insertBefore(row, targetRow);
            rowsQuantity++;
        }
        
        //set FORMULA to ROW "Итого:"
        String rowsCounter = Integer.toString(rowsQuantity - 1);
        super.getTargetElement("SumAmountValue")
                .setAttribute("ss:Formula", "=SUM(R[-"+ rowsCounter +"]C:R[-1]C)");
        super.getTargetElement("SumAmountCost")
                .setAttribute("ss:Formula", "=SUM(R[-"+ rowsCounter +"]C:R[-1]C)");
        

      
    }
    
//Builder ==========================================================================================================================  
//    public static class Builder{
//        private ObservableList<TableItem> obsKS;
//        private ObservableList<TableItemPreview>  obsPreTab;
//        private String ksNumber, ksDate;
//
//        public Builder setObsKS(ObservableList<TableItem> obsKS) {
//            this.obsKS = obsKS;
//        return this;
//        }
//
////        public Builder setObsPreTab(ObservableList<PreviewTableItem> obsPreTab) {
////            this.obsPreTab = obsPreTab;
////        return this;
////        }
//
//        public Builder setKSnumber(String ksNumber) {
//            this.ksNumber = ksNumber;
//        return this;
//        }
//
//        public Builder setKSDate(String ksDate) {
//            this.ksDate = ksDate;
//        return this;
//        }
//
//        public void build() {
//            new PrintKS( obsKS, ksNumber, ksDate);
//        }
//
//
//    }
}
