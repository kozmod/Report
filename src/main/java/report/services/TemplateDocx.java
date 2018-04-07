package report.services;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class TemplateDocx {

    private File templateFile;
    private File outputFile;
    private XWPFDocument document;

    private TemplateDocx() {
    }

    public void process(final Consumer<XWPFRun> operator) {
        XWPFDocument document = this.load();
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        paragraphs.forEach(para -> {
            para.getRuns().forEach(run -> {
                operator.accept(run);
            });
        });
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

    public void save() {
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            document.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Builder newBuilder() {
        return new TemplateDocx().new Builder();
    }


    public class Builder {
        private Builder(){}

        public Builder setTemplateFile(Path templatePath){
            TemplateDocx.this.templateFile = templatePath.toFile();
            return this;
        }
        public Builder setTemplateFile(String templatePath){
            TemplateDocx.this.templateFile = new File(templatePath);
            return this;
        }

        public Builder setOutputFile(Path outputPath){
            TemplateDocx.this.outputFile = outputPath.toFile();
            return this;
        }
        public Builder setOutputFile(String outputPath){
            TemplateDocx.this.outputFile = new File(outputPath);
            return this;
        }
        public TemplateDocx build() {
            return TemplateDocx.this;
        }

    }

}
