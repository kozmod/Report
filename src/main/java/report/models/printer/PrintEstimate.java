
package report.models.printer;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import report.usege_strings.SQL;
import report.controllers.showEstLayoutController.Est;

import report.entities.items.site.TableItemPreview;
import report.entities.items.TableItem;
import report.entities.ItemDAO;


public class PrintEstimate extends AbstractPrinterXML{
    
    private Document doc;
    private ObservableList<TableItem> obsKS; 
    private ObservableList<TableItemPreview>  obsPreTab;
    
    //Constructor =====================================================================================================================    
    public PrintEstimate(ItemDAO dao) {
//        this.obsKS = FXCollections.observableArrayList(commonSQL_SELECT.getObsEst_TEST_2(enumEst));
        this.obsKS = FXCollections.observableArrayList( dao.getList());
//        this.obsKS = commonSQL_SELECT.getEstObs(siteNumber, contName, "%", tableType);
//        this.obsKS = obsKS;
//        this.obsPreTab = obsPreTab;

        doc = buildDocument("\\lib\\XML_Models\\Смета.xml");
        addJMrows();
        saveDocument("\\lib\\XML_Models\\Смета-"+ Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER) +"_"+ Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR) +".xml");
    }

    public PrintEstimate(List list) {
//        this.obsKS = FXCollections.observableArrayList(commonSQL_SELECT.getObsEst_TEST_2(enumEst));
        this.obsKS = FXCollections.observableArrayList(list);
//        this.obsKS = commonSQL_SELECT.getEstObs(siteNumber, contName, "%", tableType);
//        this.obsKS = obsKS;
//        this.obsPreTab = obsPreTab;

        doc = buildDocument("\\lib\\XML_Models\\Смета.xml");
        addJMrows();
        saveDocument("\\lib\\XML_Models\\Смета-"+ Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER) +"_"+ Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR) +".xml");
    }

    public PrintEstimate(List list, Path path) {
//        this.obsKS = FXCollections.observableArrayList(commonSQL_SELECT.getObsEst_TEST_2(enumEst));
        this.obsKS = FXCollections.observableArrayList(list);
//        this.obsKS = commonSQL_SELECT.getEstObs(siteNumber, contName, "%", tableType);
//        this.obsKS = obsKS;
//        this.obsPreTab = obsPreTab;

        doc = buildDocument("\\lib\\XML_Models\\Смета.xml");
        addJMrows();
        saveDocument(path.toString());
    }

    
    
//Methots ==========================================================================================================================      
    //Add Name of OBJECT
    private void setObjectName(){
        
        String text = "Объект: ДКП 'Мечта пятницы', ж/дом '',  уч. № ";
        
        StringBuilder objString = new StringBuilder(text);
        objString.insert(36, obsKS.get(0).getTypeHome());
        objString.append( obsKS.get(0).getSiteNumber());
        
        getTargetElement("Home").setTextContent(objString.toString());
        
    }


       
    private void  setNumber_Date(){

        getTargetElement("number-date").setTextContent("№ " + obsPreTab.get(0).getSecondValue());
         
    }
    private void  setCont_NContract(){

        getTargetElement("contractor").setTextContent("№ " + obsPreTab.get(0).getSecondValue());
        getTargetElement("Ncontract").setTextContent("№ " + obsPreTab.get(0).getSecondValue());
         
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
//                row.setAttribute("ss:StyleID", "s46");
                
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s139")
                                 .setCellValue("Number", Integer.toString(i))
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s109")
                                 .setCellValue("String", buildingPart)
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s141")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s142")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s142")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s142")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s142")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s142")
                                 .setCellValue("String", "" )
                                 .build());
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s141")
                                 .setCellValue("String", "" )
                                 .build());
                
                targetRow.getParentNode().insertBefore(row, targetRow); 
                i++;
            };
            

            Element row = doc.createElement("Row");
//            row.setAttribute("ss:StyleID", "s143");
            
            //Posittion
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s139")
                                 .setCellValue("Number", Integer.toString(i))
                                 .build());
            //JM name
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s147")
                                 .setCellValue("String", item.getJM_name())
                                 .build());
            //Unit
            row.appendChild( new CellBuilder(doc)
                                 .setCellStyle("s141")
                                 .setCellValue("String", item.getUnit() )
                                 .build());
            //Value
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s148")
                                 .setCellValue("Number", Double.toString(item.getValue()) )
                                 .build());
            if(item.getBindedJob().endsWith("-")){
                //Price one - JOB
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s148")
                                 .setCellValue("Number", Double.toString(item.getPrice_one()) )
                                 .build());
                //Price Sum - JOB
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s143")
                                 .setCellValue("Number", "" )
                                 .setMultFormula("-1","-2")
                                 .build());
                //Price one - Material
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s148")
                                 .setCellValue("Number", "" )
                                 .build());
                //Price one - Material
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s143")
                                 .setCellValue("Number", "" )
                                 .setMultFormula("-1","-4")
                                 .build());
               
            }else{
                                //Price one - JOB
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s148")
                                 .setCellValue("Number", "")
                                 .build());
                //Price Sum - JOB
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s143")
                                 .setCellValue("Number", "" )
                                 .setMultFormula("-1","-2")
                                 .build());
                //Price one - Material
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s148")
                                 .setCellValue("Number",  Double.toString(item.getPrice_one()) )
                                 .build());
                //Price one - Material
                row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s143")
                                 .setCellValue("Number", "" )
                                 .setMultFormula("-1","-4")
                                 .build());   
            }
            row.appendChild(new CellBuilder(doc)
                                 .setCellStyle("s143")
                                 .setCellValue("Number", "" )
                                 .setSumFormula("-1","-3")
                                 .build());
               
            targetRow.getParentNode().insertBefore(row, targetRow);
            i++;
//                
        }
        

      
    }
    
//Builder ==========================================================================================================================  
//    public static class Builder{
//        private ObservableList<TableItem> obsKS; 
//        private ObservableList<PreviewTableItem>  obsPreTab;
//        private String ksNumber, ksDate;
//
//        public Builder setObsKS(ObservableList<TableItem> obsKS) {
//            this.obsKS = obsKS;
//        return this;
//        }
//
//        public Builder setObsPreTab(ObservableList<PreviewTableItem> obsPreTab) {
//            this.obsPreTab = obsPreTab;
//        return this;
//        }
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
//            new PrintKS( obsKS, obsPreTab, ksNumber, ksDate);
//        }
//    
//    
//    }
    
    
}
