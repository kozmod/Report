package report.layout.controllers.planning.temp;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import report.entities.items.DItem;
import report.entities.items.discount_coef.DiscountCoef;


public class EditingTreeTableCell extends TreeTableCell<DItem, Double> {
    private static byte TYPE = 0;
    private TextField textField;
    private ComboBox comboBox;


    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/
    public EditingTreeTableCell(StringConverter<Double> sc) {
        setConverter(sc);
    }

    /***************************************************************************
     *                                                                         *
     * Properties                                                              *
     *                                                                         *
     **************************************************************************/

    // --- converter
    private ObjectProperty<StringConverter<Double>> converter =
            new SimpleObjectProperty<StringConverter<Double>>(this, "converter");

    /**
     * The {@link StringConverter} property.
     */
    public final ObjectProperty<StringConverter<Double>> converterProperty() {
        return converter;
    }

    /**
     * Sets the {@link StringConverter} to be used in this cell.
     */
    public final void setConverter(StringConverter<Double> value) {
        converterProperty().set(value);
    }

    /**
     * Returns the {@link StringConverter} used in this cell.
     */
    public final StringConverter<Double> getConverter() {
        return converterProperty().get();
    }

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**
     * {@inheritDoc}
     */
    @Override
    public void startEdit() {
        if (!isEditable()
                || !this.getTreeTableView().isEditable()
                || !this.getTableColumn().isEditable()) {
            return;
        }
        String itemString = this.getTreeTableView().getTreeItem(this.getIndex()).getValue().getFirstValue();
        if (itemString.equals(DiscountCoef.RATE_OF_RETURN)) {
            EditingTreeTableCell.TYPE = 0;
            super.startEdit();
            if (isEditing()) {
                if (textField == null) {
                    textField = Util.createTextField(this, getConverter());
                }
                Util.startEdit(this, getConverter(), null, null, textField);
            }
        } else if (itemString.equals(DiscountCoef.MARKET_RISKS)
                || itemString.equals(DiscountCoef.SPECIFIC_RISKS)) {

        } else if (itemString.equals(DiscountCoef.KD)) {
//            this.setStyle("-fx-text-fill: red; -fx-font-weight:bold;");
        } else if (itemString.equals(DiscountCoef.KD_PER_MONTH)) {
        } else {
            EditingTreeTableCell.TYPE = 1;
            if (comboBox == null) {
                ObservableList<Double> items = FXCollections.observableArrayList(0.5d, 1d, 1.5d, 2.0d, 2.5d, 3.0d);
                comboBox = Util.createComboBox(this, items, converterProperty());
                comboBox.editableProperty().bind(new SimpleBooleanProperty(this, "comboBoxEditable"));
            }
            comboBox.getSelectionModel().select(getItem());
            super.startEdit();
            setText(null);
            setGraphic(comboBox);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getConverter().toString(getItem()));
        setGraphic(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (EditingTreeTableCell.TYPE == 0)
            Util.updateTextFieldItem(this, getConverter(), null, null, textField);
        if (EditingTreeTableCell.TYPE == 1)
            Util.updateComboItem(this, getConverter(), null, null, comboBox);
    }

    /***************************************************************************
     *                                                                         *
     * UTIL CLASS                                                              *
     *                                                                         *
     **************************************************************************/
    private static final class Util extends Cell<Double> {
        static <T> void startEdit(final Cell<T> cell,
                                  final StringConverter<T> converter,
                                  final HBox hbox,
                                  final Node graphic,
                                  final TextField textField) {
            if (textField != null) {
                textField.setText(getItemText(cell, converter));
            }
            cell.setText(null);

            if (graphic != null) {
                hbox.getChildren().setAll(graphic, textField);
                cell.setGraphic(hbox);
            } else {
                cell.setGraphic(textField);
            }

            textField.selectAll();

            // requesting focus so that key input can immediately go into the
            // TextField (see RT-28132)
            textField.requestFocus();
        }

        private static <T> String getItemText(Cell<T> cell, StringConverter<T> converter) {
            return converter == null ?
                    cell.getItem() == null ? "" : cell.getItem().toString() :
                    converter.toString(cell.getItem());
        }

        static <T> TextField createTextField(final Cell<T> cell, final StringConverter<T> converter) {
            final TextField textField = new TextField(getItemText(cell, converter));

            // Use onAction here rather than onKeyReleased (with check for Enter),
            // as otherwise we encounter RT-34685
            textField.setOnAction(event -> {
                if (converter == null) {
                    throw new IllegalStateException(
                            "Attempting to convert text input into Object, but provided "
                                    + "StringConverter is null. Be sure to set a StringConverter "
                                    + "in your cell factory.");
                }
                cell.commitEdit(converter.fromString(textField.getText()));
                event.consume();
            });
            textField.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ESCAPE) {
                    cell.cancelEdit();
                    t.consume();
                }
            });
            return textField;
        }

        static <T> ComboBox<T> createComboBox(final Cell<T> cell,
                                              final ObservableList<T> items,
                                              final ObjectProperty<StringConverter<T>> converter) {
            ComboBox<T> comboBox = new ComboBox<T>(items);
            comboBox.converterProperty().bind(converter);
            comboBox.setMaxWidth(Double.MAX_VALUE);
            comboBox.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
                if (cell.isEditing()) {
                    cell.commitEdit(newValue);
                }
            });
            return comboBox;
        }


        static void updateComboItem(final Cell<Double> cell,
                                    final StringConverter<Double> converter,
                                    final HBox hbox,
                                    final Node graphic,
                                    final ComboBox<Double> comboBox) {
            if (cell.isEmpty()) {
                cell.setText(null);
                cell.setGraphic(null);
            } else {
                if (cell.isEditing()) {
                    if (comboBox != null) {
                        comboBox.getSelectionModel().select(cell.getItem());
                    }
                    cell.setText(null);

                    if (graphic != null) {
                        hbox.getChildren().setAll(graphic, comboBox);
                        cell.setGraphic(hbox);
                    } else {
                        cell.setGraphic(comboBox);
                    }
                } else {
                    cell.setText(getItemText(cell, converter));
                    cell.setGraphic(graphic);
                }
            }
        }

        static <T> void updateTextFieldItem(final Cell<T> cell,
                                            final StringConverter<T> converter,
                                            final HBox hbox,
                                            final Node graphic,
                                            final TextField textField) {
            if (cell.isEmpty()) {
                cell.setText(null);
                cell.setGraphic(null);
            } else {
                if (cell.isEditing()) {
                    if (textField != null) {
                        textField.setText(getItemText(cell, converter));
                    }
                    cell.setText(null);

                    if (graphic != null) {
                        hbox.getChildren().setAll(graphic, textField);
                        cell.setGraphic(hbox);
                    } else {
                        cell.setGraphic(textField);
                    }
                } else {
                    cell.setText(getItemText(cell, converter));
                    cell.setGraphic(graphic);
                }
            }
        }


    }
}
