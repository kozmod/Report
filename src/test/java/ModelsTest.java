import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import report.entities.items.counterparties.*;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.converters.dateStringConverters.LocalDayStringConverter;
import report.services.TemplateFactory;
import java.io.*;
import java.math.BigInteger;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
@FixMethodOrder(MethodSorters.JVM)
public class ModelsTest {
    @Test
    @Disabled
    @DisplayName("XWPFDocument apache poi-ooxml")
    public void XWPFDocumentTest() {
        try {
            File file = new File("C:\\Users\\xxx\\Desktop\\Я чччч и здлрого.docx");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            paragraphs.stream().forEach(para -> {
                para.getRuns().stream().forEach(run -> {
                    String text = run.getText(0);
                    if (text != null && text.contains("чччч")) {
                        text = text.replace("чччч", " MAXMAXMAXMAX ");
                        run.setText(text, 0);
                    }
                });
            });
            paragraphs.stream().forEach(p -> System.out.println(p.getText()));
            fis.close();
            document.write(new FileOutputStream(new File("C:\\Users\\xxx\\Desktop\\Я чччч и здлрогоWWWWWWWW.docx")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Disabled
    @DisplayName("BigInteger test")
    public void sizeVariableTest() {
        BigInteger big = new BigInteger("9999999999999");
        System.out.println(big);
    }

    @Test
//    @Disabled
    @DisplayName("ReqDaoUtils sql string build")
    public void reqDaoUtilsTest() throws SQLException {
        List<ObjectPSI> list = new ReqCommonDAO().getByID(55);
        String str = ReqDaoUtils.buildSqlString("id_Count", list);
//        String str2 = ReqDaoUtils.buildSqlString(list);
        System.out.println(str + "\n"
//                + str2
        );
    }

    @Test
//    @Disabled
    @DisplayName("LocalDateConverter")
    public void localDateConverterTest() throws SQLException {
        System.out.println(
                new LocalDayStringConverter().fromString("2012-01-01").toString()
        );
    }

    @Test
//    @Disabled
    @SuppressWarnings("unchecked")
    @DisplayName("Template Factory Test - Docx")
    public void templateFactoryDocxTest() throws SQLException {
        File file = new File("xxxx.docx");
        List<ObjectPSI> list = new ArrayList<>();
        list.add(new ObjectPSI("Наименование банка",
                "",
                "Организация",
                "CHANGED",
                "Contractor_Name",
                "",
                ".{1,50}"
        ));
        list.add(new ObjectPSI("Наименование банка",
                "",
                "Организация",
                "ОГРН",
                "OGRN",
                "",
                ".{1,50}"
        ));
        list.add(new ObjectPSI("Наименование банка",
                "",
                "Организация",
                "XXXXXXXXXXXXXXXXXXXXXX",
                "ExBody_Surname_roditelnij",
                "",
                ".{1,50}"
        ));

        list.add(new ObjectPSI("Наименование банка",
                "",
                "Организация",
                "ASADADADDAD",
                "Appraisal_term",
                "",
                ".{1,50}"
        ));
        TemplateFactory.writeDocxTemplate(file, list);
    }

    @Test
//    @Disabled
    @SuppressWarnings("unchecked")
    @DisplayName("Template Factory Test - Doc")
    public void templateFactoryTest() throws SQLException {
        File file = new File("xxxx-2.doc");

        List<ObjectPSI> list = new ArrayList<>();
        list.add(new ObjectPSI("Наименование банка",
                "",
                "Организация",
                "CHANGED",
                "Contractor_Name",
                "",
                ".{1,50}"
        ));
//        list.add(new ObjectPSI("Наименование банка",
//                "",
//                "Организация",
//                "ОГРН",
//                "OGRN",
//                "",
//                ".{1,50}"
//        ));

        TemplateFactory.writeDocTemplate(file, list);
    }


    @Test
//    @Disabled
    @SuppressWarnings("unchecked")
    @DisplayName("----")
    public void test() throws Exception {
        XWPFDocument document = new XWPFDocument(new FileInputStream("D:\\IdeaProjects\\Report\\lib\\docs_templates\\test.docx"));

        WordReplaceTextInFormFields replaser = new WordReplaceTextInFormFields();
        replaser.replaceFormFieldText(document, "t", "Моя Компания");

        document.write(new FileOutputStream("D:\\IdeaProjects\\Report\\lib\\docs_templates\\out.docx"));
        document.close();

    }

    public class WordReplaceTextInFormFields {

        public void replaceFormFieldText(XWPFDocument document, String ffname, String text) {
            boolean foundformfield = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
//                    System.out.println(run.getCTR().toString());
//                    System.out.println(Arrays.toString(run.getCTR().getTArray()));
                    XmlCursor cursor = run.getCTR().newCursor();
                    cursor.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//w:fldChar/@w:fldCharType");
                    while (cursor.hasNextSelection()) {
                        cursor.toNextSelection();
                        XmlObject obj = cursor.getObject();
                        if ("begin".equals(((SimpleValue) obj).getStringValue())) {
                            cursor.toParent();
                            obj = cursor.getObject();
                            obj = obj.selectPath("declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//w:ffData/w:name/@w:val")[0];
                            if (ffname.equals(((SimpleValue) obj).getStringValue())) {
                                foundformfield = true;
                            } else {
                                foundformfield = false;
                            }
                        } else if ("end".equals(((SimpleValue) obj).getStringValue())) {
                            if (foundformfield) return;
                            foundformfield = false;
                        }
                    }
                    if (foundformfield && run.getCTR().getTArray().length > 0) {
                        CTText[] textXmlArray = run.getCTR().getTArray();
                        run.getCTR().getTList().get(0).setStringValue(text);
                        CTText textXml = textXmlArray[0];
                        textXml.setStringValue(text);
                        System.out.println(run.getCTR());
                    }
                }
            }
        }
    }
}
