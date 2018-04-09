package report.services;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class ReplaceFieldTask implements Consumer<XWPFRun> {
    private static final String FIRST_PATH = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//w:fldChar/@w:fldCharType";
    private static final String SECOND_PATH = "declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//w:ffData/w:name/@w:val";
    private static final String FLD_CHAR_TYPE_BEGIN = "begin";
    private static final String FLD_CHAR_TYPE_END = "end";


    private final Map<String, String> replacementMap;
    private String textToReplace = null;

    public ReplaceFieldTask(Map<String, String> replacementMap) {
        this.replacementMap = replacementMap;
    }

    @Override
    public void accept(XWPFRun xwpfRun) {
        XmlCursor cursor = xwpfRun.getCTR().newCursor();
        cursor.selectPath(FIRST_PATH);
        while (cursor.hasNextSelection()) {
            cursor.toNextSelection();
            XmlObject obj = cursor.getObject();
            if (FLD_CHAR_TYPE_BEGIN.equals(((SimpleValue) obj).getStringValue())) {
                cursor.toParent();
                obj = cursor.getObject();
                obj = obj.selectPath(SECOND_PATH)[0];

                textToReplace = replacementMap.get(((SimpleValue) obj).getStringValue());
            } else if (FLD_CHAR_TYPE_END.equals(((SimpleValue) obj).getStringValue())) {
                if (Objects.nonNull(textToReplace)) {
                    return;
                }
            }
        }
        List<CTText> ctTextList = xwpfRun.getCTR().getTList();
        if (Objects.nonNull(textToReplace) && !ctTextList.isEmpty()) {
            ctTextList.get(0).setStringValue(textToReplace);
            textToReplace = null;
        }
    }
}
