package report.models.view.wrappers.controlsFxMod;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;
import report.entities.items.propertySheet__TEST.ObjectPSI;


public class ValidStringPropertyEditor<T extends TextInputControl> extends AbstractPropertyEditor<String, T> {
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public ValidStringPropertyEditor(ObjectPSI property, T control) {
        super(property, control);
    }

    public ValidStringPropertyEditor(ObjectPSI property, T control, boolean readonly) {
        super(property, control, readonly);
    }

    /***************************************************************************
     *                                                                         *
     * Override                                                                *
     *                                                                         *
     **************************************************************************/
    @Override
    protected ObservableValue<String> getObservableValue() {
        return this.getEditor().textProperty();
    }

    @Override
    public void setValue(String value) {
        this.getEditor().setText(value);
    }

    /***************************************************************************
     *                                                                         *
     * Factory Methods                                                         *
     *                                                                         *
     **************************************************************************/
    /**
     * Get ValidStringPropertyEditor with TextField.
     *
     * @param property ObjectPSI
     * @return AbstractPropertyEditor<String               ,               TextField>
     */
    public static AbstractPropertyEditor<String, TextField> textField(ObjectPSI property) {
        return new ValidStringPropertyEditor<>(property, new TextField());
    }

    /**
     * Get ValidStringPropertyEditor with TextArea.
     *
     * @param property ObjectPSI
     * @return AbstractPropertyEditor<String               ,               TextArea>
     */
    public static AbstractPropertyEditor<String, TextArea> textArea(ObjectPSI property) {
        return new ValidStringPropertyEditor<>(property, new TextArea());
    }
}
