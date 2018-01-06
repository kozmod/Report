package report.models_view.nodes.cells;

import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import report.entities.items.ID;


import java.util.NoSuchElementException;
import java.util.Set;


public class AddTextFieldTableCell<S extends ID,T > extends TableCell<S,T> implements CommittableRow {
    private int CHANGED = 0;

    private Set<? super CommittableRow> setOfNewCells;
    private TextField textField ;
    private final StringConverter<T> converter;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    public AddTextFieldTableCell(StringConverter<T> converter) {
        super();
        this.converter = converter;
    }
//
    public AddTextFieldTableCell(StringConverter<T> converter,Set<? super CommittableRow> set) {
        super();
        this.converter = converter;
        this.setOfNewCells = set;

    }


    public AddTextFieldTableCell(Set<? super CommittableRow> set) {
        super();
        this.converter = (StringConverter<T>) new DefaultStringConverter();
        this.setOfNewCells = set;

    }

    /***************************************************************************
     *                                                                         *
     * Getters/Setters                                                         *
     *                                                                         *
     **************************************************************************/


    /***************************************************************************
     *                                                                         *
     *   Override                                                              *
     *                                                                         *
     **************************************************************************/

    @Override
    public void startEdit() {
        if (!isEditable()
                || !getTableView().isEditable()
                || !getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();
        if (isEditing()) {
            if (this.textField == null) {
                textField = this.createTextField(this, converter);
            }
            this.setText(null);
            this.setGraphic(textField);
            textField.selectAll();
//            this.getTableView().editableProperty().setValue(false);
         }
    }


    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
                    setText(null);
                    setGraphic(null);
        } else {
            final long personID = this.getTableView()
                    .getItems()
                    .get(this.getIndex())
                    .getId();
            if(setOfNewCells != null & personID  == -1 &  CHANGED == 0) {
                if(textField == null){
                    textField = createTextField(this,converter);
                }
                setGraphic(textField);
                textField.setText(item.toString());
                this.setText(null);
//                this.getTableView().setEditable(false);
                //Add cell into "NEED to COMMIT" cell set
                this.addToNewCell();
            }else{
                setGraphic(null);
                setText(item.toString());
                this.CHANGED++;
            }
        }
    }

    @Override
    public void commitEdit(T item) {
        // This block is necessary to support commitTextField on losing focus, because
        // the baked-in mechanism sets our editing state to false before we can
        // intercept the loss of focus. The default commitEdit(...) method
        // simply bails if we are not editing...
        if (!isEditing() && !item.equals(getItem())) {
            TableView<S> table = getTableView();
            if (table != null) {
                TableColumn<S, T> column = getTableColumn();
                TableColumn.CellEditEvent<S, T> event = new TableColumn.CellEditEvent<>(
                        table, new TablePosition<S,T>( table,getIndex(),column  ),
                        TableColumn.editCommitEvent(),
                        item
                );
                Event.fireEvent(column, event);
            }
        }
        super.commitEdit(item);
        this.setGraphic(null);
//        AddTextFieldTableCell.this.getTableView().setEditable(true);
        System.out.println("cell commited " +  this.getIndex());
    }

    /***************************************************************************
     *                                                                         *
     *  Methods                                                                *
     *                                                                         *
     **************************************************************************/

    @Override
    public void commitData(){
        System.out.println("text - "+  this.textField.getText());
        if( CHANGED == 0)
            CHANGED++;
        this.setText(this.textField.getText());
        this.commitEdit(converter.fromString(this.textField.getText()));

    }

    private void addToNewCell(){
        if(setOfNewCells != null){
            setOfNewCells.add(this);
        }else{
            throw new NoSuchElementException();
        }

    }

    /***************************************************************************
     *                                                                         *
     *  Static Methods                                                         *
     *                                                                         *
     **************************************************************************/
    @SuppressWarnings("Duplicates" )
    static <S extends ID,T> TextField createTextField(final AddTextFieldTableCell<S,T> cell, final StringConverter<T> converter) {
        final TextField textField = new TextField(getItemText(cell, converter));
        // Use onAction here rather than onKeyReleased (with check for Enter),
        // as otherwise we encounter RT-34685
//        if( cell.getTableView().getItems().get(cell.getIndex()).getId() != 0)
//        if( cell.changesCounter != 0)
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

    static <T> String getItemText(Cell<T> cell, StringConverter<T> converter) {
        return converter == null ?
                cell.getItem() == null ? "" : cell.getItem().toString() :
                converter.toString(cell.getItem());
    }



}
