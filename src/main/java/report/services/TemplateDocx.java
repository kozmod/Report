package report.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.UnaryOperator;

public class TemplateDocx {

    private final File templateFile;
    private XWPFDocument document;

    public TemplateDocx(File templateFile) {
        this.templateFile = templateFile;
    }

    public TemplateDocx(String templateFilePath) {
        this.templateFile = new File(templateFilePath);
    }

    private XWPFDocument load() {
        try {
            FileInputStream fis = new FileInputStream(templateFile);
            document = new XWPFDocument(fis);
            if (document == null) throw new IOException();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public void process(final UnaryOperator<String> operator) {
        XWPFDocument document = this.load();
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        paragraphs.stream().forEach(para -> {
            para.getRuns().stream().forEach(run -> {
                String text = run.getText(0);
                System.out.println(text);
                if (text != null) {
                    run.setText(operator.apply(text), 0);
                }
            });
        });
    }

    public void save(File saveFile) {
        try (FileOutputStream fos = new FileOutputStream(saveFile)) {
            document.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
