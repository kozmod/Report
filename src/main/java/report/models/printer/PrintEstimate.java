
package report.models.printer;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import report.entities.abstraction.dao.CommonDao;
import report.entities.items.AbstractEstimateTVI;
import report.usage_strings.SQL;
import report.layout.controllers.estimate.EstimateController_old.Est;

import report.entities.items.site.PreviewTIV;


public class PrintEstimate extends AbstractPrinterXML {

    private Document doc;
    private ObservableList<AbstractEstimateTVI> obsKS;
    private ObservableList<PreviewTIV> obsPreTab;
//    private ContractorTIV contractorObject;

    //Constructor =====================================================================================================================    
    public PrintEstimate(CommonDao dao) {
        this.obsKS = FXCollections.observableArrayList((Collection<? extends AbstractEstimateTVI>) dao.getData());


        doc = buildDocument("\\lib\\XML_Models\\Смета.xml");
        addJMrows();
        saveDocument("\\lib\\XML_Models\\Смета-" + Est.Common.getSiteSecondValue(SQL.Common.SITE_NUMBER) + "_" + Est.Common.getSiteSecondValue(SQL.Common.CONTRACTOR) + ".xml");
    }


    public PrintEstimate(List list, Path path) {
        this.obsKS = FXCollections.observableArrayList(list);


        doc = buildDocument("\\lib\\XML_Models\\Смета.xml");
        addJMrows();
        saveDocument(path.toString());
    }


    //Methots ==========================================================================================================================
    //Add Name of OBJECT
    private void setObjectName() {

        String text = "Объект: ДКП 'Мечта пятницы', ж/дом '',  уч. № ";

        StringBuilder objString = new StringBuilder(text);
        objString.insert(36, obsKS.get(0).getTypeHome());
        objString.append(obsKS.get(0).getSiteNumber());

        getTargetElement("Home").setTextContent(objString.toString());

    }


    private void setNumber_Date() {

        getTargetElement("number-date").setTextContent("№ " + obsPreTab.get(0).getSecondValue());

    }

    private void setCont_NContract() {

        getTargetElement("contractor").setTextContent("№ " + obsPreTab.get(0).getSecondValue());
        getTargetElement("Ncontract").setTextContent("№ " + obsPreTab.get(0).getSecondValue());

    }


    //Add Job - Material rows
    private void addJMrows() {

        int rowsQuantity = 1;
        String buildingPart = null;

        for (AbstractEstimateTVI abstractEstimateTVI : obsKS) {


            Element targetRow = getTargetElement("SumRow");

            //CHECK -> Binded Job
            if (!abstractEstimateTVI.getBuildingPart().equals(buildingPart)) {
                buildingPart = abstractEstimateTVI.getBuildingPart();

                Element row = doc.createElement("Row");
//                row.setAttribute("ss:StyleID", "s46");

                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s139")
                        .setCellValue("Number", Integer.toString(rowsQuantity))
                        .build());
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s109")
                        .setCellValue("String", buildingPart)
                        .build());
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s141")
                        .setCellValue("String", "")
                        .build());
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s142")
                        .setCellValue("String", "")
                        .build());
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s142")
                        .setCellValue("String", "")
                        .build());
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s142")
                        .setCellValue("String", "")
                        .build());
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s142")
                        .setCellValue("String", "")
                        .build());
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s142")
                        .setCellValue("String", "")
                        .build());
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s141")
                        .setCellValue("String", "")
                        .build());

                targetRow.getParentNode().insertBefore(row, targetRow);
                rowsQuantity++;
            }
            ;


            Element row = doc.createElement("Row");
//            row.setAttribute("ss:StyleID", "s143");

            //Posittion
            row.appendChild(new CellBuilder(doc)
                    .setCellStyle("s139")
                    .setCellValue("Number", Integer.toString(rowsQuantity))
                    .build());
            //JM name
            row.appendChild(new CellBuilder(doc)
                    .setCellStyle("s147")
                    .setCellValue("String", abstractEstimateTVI.getJM_name())
                    .build());
            //Unit
            row.appendChild(new CellBuilder(doc)
                    .setCellStyle("s141")
                    .setCellValue("String", abstractEstimateTVI.getUnit())
                    .build());
            //Value
            row.appendChild(new CellBuilder(doc)
                    .setCellStyle("s148")
                    .setCellValue("Number", Double.toString(abstractEstimateTVI.getQuantity()))
                    .build());
            if (abstractEstimateTVI.getBindJob().endsWith("-")) {
                //Price one - JOB
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s148")
                        .setCellValue("Number", Double.toString(abstractEstimateTVI.getPriceOne()))
                        .build());
                //Price Sum - JOB
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s143")
                        .setCellValue("Number", "")
                        .setMultFormula("-1", "-2")
                        .build());
                //Price one - Material
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s148")
                        .setCellValue("Number", "")
                        .build());
                //Price one - Material
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s143")
                        .setCellValue("Number", "")
                        .setMultFormula("-1", "-4")
                        .build());

            } else {
                //Price one - JOB
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s148")
                        .setCellValue("Number", "")
                        .build());
                //Price Sum - JOB
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s143")
                        .setCellValue("Number", "")
                        .setMultFormula("-1", "-2")
                        .build());
                //Price one - Material
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s148")
                        .setCellValue("Number", Double.toString(abstractEstimateTVI.getPriceOne()))
                        .build());
                //Price one - Material
                row.appendChild(new CellBuilder(doc)
                        .setCellStyle("s143")
                        .setCellValue("Number", "")
                        .setMultFormula("-1", "-4")
                        .build());
            }
            row.appendChild(new CellBuilder(doc)
                    .setCellStyle("s143")
                    .setCellValue("Number", "")
                    .setSumFormula("-1", "-3")
                    .build());

            targetRow.getParentNode().insertBefore(row, targetRow);
            rowsQuantity++;
//                
        }

        super.getTargetElement("SumAmountValue")
                .setAttribute("ss:Formula", "=SUM(R[-" + Integer.toString(rowsQuantity - 1) + "]C:R[-1]C)");
        super.getTargetElement("HeadSumAmountValue")
                .setAttribute("ss:Formula", "=R[" + Integer.toString(rowsQuantity + 7) + "]C[2]");


    }

//Builder ==========================================================================================================================  
//    public static class Builder{
//        private ObservableList<AbstractEstimateTVI> obsKS;
//        private ObservableList<PreviewTableItem>  obsPreTab;
//        private String ksNumber, ksDate;
//
//        public Builder setObsKS(ObservableList<AbstractEstimateTVI> obsKS) {
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
