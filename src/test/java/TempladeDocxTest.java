import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import report.services.ReplaceFieldTask;
import report.services.TemplateDocx;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class TempladeDocxTest {

    @Test
    void doxServiceTest() {

        Map<String,String> replacementMap = new HashMap<>();
        replacementMap.put("t1","ЗАМЕНА_1");
        replacementMap.put("t2","ЗАМЕНА_2");
        replacementMap.put("t3","ЗАМЕНА_3");


        TemplateDocx docx = TemplateDocx.newBuilder()
                .setTemplateFile("D:\\IdeaProjects\\Report\\lib\\docs_templates\\test.docx")
                .setOutputFile("D:\\IdeaProjects\\Report\\lib\\docs_templates\\out.docx")
                .build();
        docx.process(new ReplaceFieldTask(replacementMap));
        docx.save();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Docx-replacer from stackoverflow")
    public void test() throws Exception {
        XWPFDocument document = new XWPFDocument(new FileInputStream("D:\\IdeaProjects\\Report\\lib\\docs_templates\\test.docx"));

        WordReplaceTextInFormFields replacer = new WordReplaceTextInFormFields();
        replacer.replaceFormFieldText(document, "t1", "Моя Компания");

        document.write(new FileOutputStream("D:\\IdeaProjects\\Report\\lib\\docs_templates\\out.docx"));
        document.close();

    }

    public class WordReplaceTextInFormFields {

        public void replaceFormFieldText(XWPFDocument document, String ffname, String text) {
            boolean foundformfield = false;
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    System.out.println(run.getCTR().toString());
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
                    if (foundformfield && run.getCTR().getTList().size() > 0) {
                        CTText[] textXmlArray = run.getCTR().getTArray();
//                        run.getCTR().getTList().get(0).setStringValue(text);
                        CTText textXml = textXmlArray[0];
                        textXml.setStringValue(text);
//                        System.out.println(run.getCTR());
                    }
                }
            }
        }
    }
}
