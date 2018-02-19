import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

@FixMethodOrder(MethodSorters.JVM)
public class ModelsTests {
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
//    @Disabled
    public void sizeVariableTest(){
        BigInteger big = new BigInteger("9999999999999");
        System.out.println(big);

    }
}
