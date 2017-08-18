
package report.models.printer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class CellBuilder {
    private final static String STYLE_ID = "ss:StyleID";
    private final static String TYPE     = "ss:Type";
    private final static String FORMULA  = "ss:Formula";
    private final static String CELL     = "Cell";
    private final static String DATA     = "Data";



    private Document doc;
    private Element  cell,data, namedCell;
    
    
    public  CellBuilder(Document doc){
        this.doc = doc;
        this.data = doc.createElement(DATA);
        this.cell = doc.createElement(CELL);
        
        
    }
    

      
    public CellBuilder setCellStyle(String cellStyle) {
       this.cell.setAttribute(STYLE_ID, cellStyle);

     return this;
    }
        
    public CellBuilder setCellValue(String type, String value) {
        this.data.setAttribute(TYPE, type);
        this.data.setTextContent(value);
//            CellXML.this.cellType = cellType;
//            CellXML.this.cellValue = cellValue;

    return this;
    }
    
     public CellBuilder setMultFormula(String firstValue, String secondValue) {
         //"ss:Formula", "RC[-1]*RC[-2]"
         StringBuilder formulaString = new StringBuilder()
                 .append("RC[")
                 .append(firstValue)
                 .append("]*RC[")
                 .append(secondValue)
                 .append("]");
        this.cell.setAttribute(FORMULA, formulaString.toString());
//        this.cell.setAttribute("ss:Formula", "RC["+ firstValue +"]*RC["+ secondValue +"]");

    return this;
    }

     public CellBuilder setSumFormula(String firstValue, String secondValue) {
         //"ss:Formula", "RC[-3]+RC[-1]"
         StringBuilder formulaString = new StringBuilder()
                 .append("RC[")
                 .append(firstValue)
                 .append("]+RC[")
                 .append(secondValue)
                 .append("]");
        this.cell.setAttribute(FORMULA, formulaString.toString());
//        this.cell.setAttribute("ss:Formula", "RC["+ firstValue +"]+RC["+ secondValue +"]");

    return this;
    }
            
    public Element build() {
         this.cell.appendChild(data);
            return this.cell;
    }
    

    
}
