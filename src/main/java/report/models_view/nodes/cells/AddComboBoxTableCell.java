package report.models_view.nodes.cells;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import report.entities.items.ID;

import java.util.NoSuchElementException;
import java.util.Set;

public class AddComboBoxTableCell<S extends ID,T > extends TableCell<S,T> implements CommittableRow {

   private int CHANGED = 0;
   private Set<? super CommittableRow> setOfNewCells;

   private final ObservableList<T> items;
   private ComboBox<T> comboBox;
   private ObjectProperty<StringConverter<T>> converter;
   private BooleanProperty comboBoxEditable;

   /***************************************************************************
    *                                                                         *
    * Constructors                                                            *
    *                                                                         *
    **************************************************************************/


   public AddComboBoxTableCell(T ... comboVars ) {
      this((StringConverter<T>) new DefaultStringConverter(),
              null,
              FXCollections.observableArrayList(comboVars));
   }

   public AddComboBoxTableCell(Set<? super CommittableRow> set,T ... comboVars ) {
      this((StringConverter<T>) new DefaultStringConverter(),
              set,
              FXCollections.observableArrayList(comboVars));
   }
   public AddComboBoxTableCell(StringConverter<T> converter, T ... comboVars  ) {
      this(converter,
              null,
              FXCollections.observableArrayList(comboVars));
   }

   public AddComboBoxTableCell(StringConverter<T> converter,Set<? super CommittableRow> set, T ... comboVars  ) {
      this(converter,set, FXCollections.observableArrayList(comboVars));
   }

   public AddComboBoxTableCell(StringConverter<T> var1, Set<? super CommittableRow> set,ObservableList<T> var2) {
      this.converter = new SimpleObjectProperty(this, "converter");
      this.comboBoxEditable = new SimpleBooleanProperty(this, "comboBoxEditable");
      this.getStyleClass().add("combo-box-table-cell");
      this.items = var2;
      this.converter.set(var1);
      this.setOfNewCells = set;
   }
   /***************************************************************************
    *                                                                         *
    * Getters/Setters                                                         *
    *                                                                         *
    **************************************************************************/


   /***************************************************************************
    *                                                                         *
    *  @Override                                                              *
    *                                                                         *
    **************************************************************************/

   @Override
   public void startEdit() {
      if (this.isEditable() && this.getTableView().isEditable() && this.getTableColumn().isEditable()) {
         if (this.comboBox == null) {
            this.comboBox = this.createComboBox(this, this.items, this.converter);
            this.comboBox.editableProperty().bind(this.comboBoxEditable);
         }

         this.comboBox.getSelectionModel().select(this.getItem());
         super.startEdit();
         this.setText((String)null);
         this.setGraphic(this.comboBox);
      }
   }

   @Override
   public void updateItem(T item, boolean empty) {
      super.updateItem(item, empty);
      if (this.isEmpty()) {
         this.setText(null);
         this.setGraphic(null);
      } else {
         final long personID = this.getTableView()
                 .getItems()
                 .get(this.getIndex())
                 .getID();
         if(this.comboBox == null) {
            this.comboBox = createComboBox(this,this.items,this.converter);
         }

//         System.out.println("-> " +
//                 " personID  - " +(personID  == -1)+
//                 " setOfNewCells   -" +(setOfNewCells == null)+
//                 " CHANGED  - "+ (CHANGED == 0));

         if(setOfNewCells != null & personID  == -1 &  CHANGED == 0) {
            System.out.println("index cell " + getIndex());
            setGraphic(comboBox);
            comboBox.setValue(item);
            this.getTableView().setEditable(false);
            this.setText(null);
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
      if (!isEditing() & !item.equals(getItem())) {
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
      this.getTableView().setEditable(true);
   }

   @Override
   public void cancelEdit() {
      super.cancelEdit();
      this.setText(this.converter.get().toString(this.getItem()));
      this.setGraphic((Node)null);
   }



   @SuppressWarnings("Duplicates")
   private <T> ComboBox<T> createComboBox(final Cell<T> cell,
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

   /***************************************************************************
    *                                                                         *
    *  Methods                                                                *
    *                                                                         *
    **************************************************************************/
   @Override
   public void commitData() {
      if( CHANGED== 0) CHANGED++;
      this.setText(this.comboBox.getValue().toString());
      this.commitEdit(this.converter.get().fromString(this.comboBox.getValue().toString()));
      System.out.println("box commited " +  this.getIndex() + " CHANGED = "+ CHANGED);
   }


   private void addToNewCell(){
      if(setOfNewCells != null){
         setOfNewCells.add(this);
      }else{
         throw new NoSuchElementException();
      }

   }

}
