import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import report.entities.items.counterparties.*;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.converters.dateStringConverters.LocalDayStringConverter;
import report.models.services.TemplateFactory;

import java.io.*;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    public void tmplateFactoryDocxTest() throws SQLException {
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
        TemplateFactory.writeDocxTemplate(file, list);
    }

    @Test
//    @Disabled
    @SuppressWarnings("unchecked")
    @DisplayName("Template Factory Test - Doc")
    public void tmplateFactoryTest() throws SQLException {
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
    @DisplayName("Template Factory Test - Doc")
    public void copyTest() throws SQLException {
        File file = new File("xxxx-2.doc");

    }

}
