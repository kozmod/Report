
package report.models.printer;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public abstract class AbstractPrinterXML {

    private Document doc;

    protected Element getTargetElement(String targetID) {

        Element targetCell = null;

        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile("//*[@search_id='" + targetID + "']");
            NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            targetCell = (Element) ((NodeList) expr.evaluate(doc, XPathConstants.NODESET)).item(0);

        } catch (XPathExpressionException ex) {
            Logger.getLogger(AbstractPrinterXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return targetCell;
    }

    protected Document buildDocument(String modelPath) {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new File(System.getProperty("user.dir") + modelPath));

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(AbstractPrinterXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(AbstractPrinterXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AbstractPrinterXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }

    protected void saveDocument(String savePath) {

        try {
            DOMSource source = new DOMSource(doc);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(savePath);
            transformer.transform(source, result);

        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(AbstractPrinterXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(AbstractPrinterXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
