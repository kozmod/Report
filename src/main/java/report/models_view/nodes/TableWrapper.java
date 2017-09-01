
package report.models_view.nodes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import report.entities.ItemDAO;
import report.models_view.data_utils.Memento;

import java.util.List;
import java.util.Objects;

public class TableWrapper<S>  {


    protected TableView<S> tableView;
    private String title;
    private Memento<S> memento;
    private ItemDAO<S,TableWrapper> dao;



/*!******************************************************************************************************************
*                                                                                                       MEMENTO
********************************************************************************************************************/
    //Memento - create
    /**
     * Save Items of TableView.
     */
    public void saveTableItems() {
        memento = new Memento(tableView.getItems());
    }

    //Memento - undo
    /**
     * Undo changes of TableView Items.
     */
    public void undoChangeItems(){
        tableView.getItems().setAll(memento.getSavedState());

    }

    public Memento getMemento(){
        return memento;
    }

/*!******************************************************************************************************************
*                                                                                                       CONSTRUCTORS
********************************************************************************************************************/

    public TableWrapper() {
        tableView = new TableView<>();
    }
    public TableWrapper(TableView<S> tableView){
        this.tableView = tableView;
    }
    public TableWrapper(String title){
        this();
        this.title = title;
    }
    public TableWrapper(String title, ObservableList<S> items) {
        tableView = new TableView<>(items);
        this.title = title;
    }
    public TableWrapper(String title, ObservableList<S> items, ItemDAO<S,TableWrapper> dao) {
        this(title,items);
        this.dao = dao;
    }

/*!******************************************************************************************************************
*                                                                                                      Getter/Setter
********************************************************************************************************************/

    public String getTitle()   {return title;}
    public  void setTitle(String title) {this.title = title;}

//   public <S>CeartakerUID getCRUD(){return  ceartaker;}

    public ItemDAO getDAO() {return dao;}
    public void setDAO(ItemDAO<S,TableWrapper> dao) {this.dao = dao;}

    public  TableView<S> getTableView(){ return tableView; }

    ///
    public ObservableList<S> getItems(){ return tableView.getItems();}
    public void setItems(ObservableList<S> value){tableView.setItems(value);}

    public ContextMenu getContextMenu(){ return tableView.getContextMenu();}
    public void setContextMenu(ContextMenu contextMenu){ tableView.setContextMenu(contextMenu);}

    public void setColumnResizePolicy(Callback<ResizeFeatures, Boolean> callback ){ tableView.setColumnResizePolicy(callback);}

    public ObjectProperty<ContextMenu> contextMenuProperty(){return tableView.contextMenuProperty();}

    public BooleanProperty editableProperty(){return tableView.editableProperty();}


/*!******************************************************************************************************************
*                                                                                                             METHODS
********************************************************************************************************************/
    /**
     * Contain :
     * <br>
     * 1. setItems()
     * <br>
     * 2. saveTableItems() - save table items to Memento.
     * <br>
     * @param items - Observable List of table item (inherited TableItems)
     */
    public void setTableData(ObservableList<S> items){
        tableView.setItems(items);
        saveTableItems();
    }
    /**
     * Contain :
     * <br>
     * 1. setItems() from <b>SQL</b> if <u>DAO</u> != <b>NULL</b>
     * <br>
     * 2. saveTableItems() - save table items to Memento.
     * <br>
     */
    public void setTableDataFromBASE(){
        tableView.setItems(dao.getList());
        saveTableItems();
    }



    /**
     * Add new column into current table and return one.
     * @param <K>
     * @param fieldName entity field name.
     * @param name column name.
     * @return TableColumn
     */
    public  <K> TableColumn addColumn(String name, String fieldName){
        TableColumn<S,K> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));


        tableView.getColumns().add(column);
        return column;
    }



    /**
     * Add new column into current table and return one.
     * @param position position to add (min: 0).
     * @param fieldName  entity field name.
     * @param name column name.
     * @return TableColumn
     */
    public <K> TableColumn addColumn(int position, String name, String fieldName){
        TableColumn<S,K> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        tableView.getColumns().add(position,column);
        return column;
    }

    /**
     * Add new column into another column and return one.
     * @param fieldName  entity field name.
     * @param name column name.
     * @param parentCol parent-column name.
     * @return TableColumn
     */
    public  <K> TableColumn<S,K> addColumn (TableColumn<S,Objects> parentCol, String name, String fieldName){
        TableColumn<S,K> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));

        parentCol.getColumns().add(column);
        return column;
    }
    /**
     * Update TableView Items, use <b>method</b>:
     * <br><b>this.</b>getItems().setAll(newItems);
     */
    public void updateTableFromBASE(List newItems){tableView.getItems().setAll(newItems);}


    /*!******************************************************************************************************************
    *                                                                                                  TABLE VIEW METHODS
    ********************************************************************************************************************/
    public void refresh(){tableView.refresh();}

    public TableViewSelectionModel<S> getSelectionModel(){
        return tableView.getSelectionModel();
    }
    public void setEditable(boolean value){
        tableView.setEditable(value);
    }
    public void setDisable(boolean value){
        tableView.setDisable(value);
    }


}
