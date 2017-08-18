
package report.models.printer;

import java.time.LocalDate;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import report.usege_strings.SQL;
import report.controllers.showEstLayoutController.Est;
import report.view_models.data_models.DecimalFormatter;
import report.entities.items.site.TableItemPreview;
import report.entities.items.TableItem;



public class PrintKS extends AbstractPrinterXML{
    
    
    private Document doc;
    private ObservableList<TableItem> obsKS; 
//    private ObservableList<PreviewTableItem>  obsPreTab;
    private String ksNumber, ksDate;
    
    

//Constructor =====================================================================================================================    
    private PrintKS(
            ObservableList<TableItem> obsKS, 
//                    ObservableList<PreviewTableItem> obsPreTab, 
                    String ksNumber, 
                    String ksDate) {
        this.obsKS = obsKS;
//        this.obsPreTab = obsPreTab;
        this.ksNumber = ksNumber;
        this.ksDate = ksDate;
        
        doc = buildDocument("\\libS\\XML_Models\\KS-2.xml");
        setObjectName();
        setDates();
        setNumber();
        addJMrows();
        saveDocument("\\libS\\XML_Models\\КС-2 № " + ksNumber + ".xml");
    }
//    public  PrintKS(String ksNamber) {
//        this.obsKS = (ObservableList<TableItem>) Est.KS.getTabMap().get(ksNamber);
//
//        this.ksNumber = ksNamber;
//        this.ksDate = obsKS.get(2).toString();
//        
//        doc = buildDocument("\\libS\\XML_Models\\KS-2.xml");
//        setObjectName();
//        setDates();
//        setNumber();
//        addJMrows();
//        saveDocument("\\libS\\XML_Models\\КС-2 № " + ksNumber + ".xml");
//    }

    
    
//Methots ==========================================================================================================================      
    //Add Name of OBJECT
    private void setObjectName(){
        
        String text = "Объект: ДКП 'Мечта пятницы', ж/дом '',  уч. № ";
        
        StringBuilder objString = new StringBuilder(text);
        objString.insert(36, obsKS.get(0).getTypeHome());
        objString.append( obsKS.get(0).getSiteNumber());
        
        getTargetElement("Home").setTextContent(objString.toString());
        
    }

    //Add Dates of KS
    private void  setDates(){
        
        super.getTargetElement("createData").setTextContent(ksDate);
        getTargetElement("periodFrom").setTextContent(
                LocalDate.ofEpochDay((long) Est.KS.getSecondValue(SQL.Site.DATE_CONTRACT)).toString()
        );
        getTargetElement("periodTo").setTextContent(
            LocalDate.ofEpochDay((long) Est.KS.getSecondValue(SQL.Site.FINISH_BUILDING)).toString()
        );   
    }
       
    private void  setNumber(){
        //get Date contract
        LocalDate dateContract = LocalDate.ofEpochDay((long) Est.KS.getSecondValue(SQL.Site.DATE_CONTRACT));
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
        
        int i = 1;
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
                                 .setCellValue("Number", Integer.toString(i))
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
                i++;
            };
            
            //Set rows
            Element row = doc.createElement("Row");
            row.setAttribute("ss:StyleID", "s143");
            
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s77")
                                 .setCellValue("Number", Integer.toString(i))
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
                                 .setCellValue("Number",DecimalFormatter.toString(item.getPrice_one()) )
                                 .build());
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s174")
                                 .setCellValue("Number", "" )
                                 .setMultFormula("-1","-2")
                                 .build());
            
            targetRow.getParentNode().insertBefore(row, targetRow);
            i++;             
        }
        
        //set FORMULA to ROW "Итого:"
        String rowsCounter = Integer.toString(i - 1);
        getTargetElement("SumEmountValue").setAttribute("ss:Formula", "=SUM(R[-"+ rowsCounter +"]C:R[-1]C)");
        getTargetElement("SumEmountCost").setAttribute("ss:Formula", "=SUM(R[-"+ rowsCounter +"]C:R[-1]C)");
        

      
    }
    
//Builder ==========================================================================================================================  
    public static class Builder{
        private ObservableList<TableItem> obsKS; 
        private ObservableList<TableItemPreview>  obsPreTab;
        private String ksNumber, ksDate;

        public Builder setObsKS(ObservableList<TableItem> obsKS) {
            this.obsKS = obsKS;
        return this;
        }

//        public Builder setObsPreTab(ObservableList<PreviewTableItem> obsPreTab) {
//            this.obsPreTab = obsPreTab;
//        return this;
//        }

        public Builder setKSnumber(String ksNumber) {
            this.ksNumber = ksNumber;
        return this;
        }

        public Builder setKSDate(String ksDate) {
            this.ksDate = ksDate;
        return this;    
        }
        
        public void build() {
            new PrintKS( obsKS, ksNumber, ksDate);
        }
    
    
    }
}
