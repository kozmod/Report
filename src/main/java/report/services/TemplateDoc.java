//package report.services;
//
//import org.apache.poi.hwpf.HWPFDocument;
//import org.apache.poi.hwpf.usermodel.CharacterRun;
//import org.apache.poi.hwpf.usermodel.Paragraph;
//import org.apache.poi.hwpf.usermodel.Range;
//import org.apache.poi.hwpf.usermodel.Section;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.function.UnaryOperator;
//
//public class TemplateDoc {
//    private final File templateFile;
//    private HWPFDocument document;
//
//    public TemplateDoc(File templateFile) {
//        this.templateFile = templateFile;
//    }
//
//    public TemplateDoc(String templateFilePath) {
//        this.templateFile = new File(templateFilePath);
//    }
//
//    private HWPFDocument load() {
//        try {
//            FileInputStream fis = new FileInputStream(templateFile);
//            document = new HWPFDocument(fis);
//            if (document == null) throw new IOException();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return document;
//    }
//
//    public void process(final UnaryOperator<String> operator) {
//        HWPFDocument document = load();
//        Range range = document.getRange();
//        for (int i = 0; i < range.numSections(); i++) {
//            Section section = range.getSection(i);
//            for (int j = 0; j < section.numParagraphs(); j++) {
//                Paragraph paragraph = section.getParagraph(j);
//                for (int k = 0; k < paragraph.numCharacterRuns(); k++) {
//                    CharacterRun run = paragraph.getCharacterRun(k);
//                    String text = run.text();
//                    run.replaceText(operator.apply(text), false);
//                }
//
//            }
//        }
//    }
//
//    public void save(File saveFile) {
//        try (FileOutputStream fos = new FileOutputStream(saveFile)) {
//            document.write(fos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
