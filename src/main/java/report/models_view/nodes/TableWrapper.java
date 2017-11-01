
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
import report.entities.items.TableClone;
import report.models_view.data_utils.Memento;

import java.util.List;
import java.util.Objects;

public class TableWrapper<E extends TableClone>  {



    private String title;
    private Memento<E> memento;
    protected final TableView<E> tableView;
    private final  ItemDAO<E,TableWrapper> dao;



/*!******************************************************************************************************************
*                                                                                                       MEMENTO
********************************************************************************************************************/
    //Memento - create
    /**
     * Save Items of TableView.
     */
    public void saveTableItems() {
        memento = new Memento<>(tableView.getItems());
    }

    //Memento - undo
    /**
     * Undo changes of TableView Items.
     */
    public void undoChangeItems(){
        tableView.getItems().setAll( memento.getSavedState());

    }

    public Memento getMemento(){
        return memento;
    }

/*!******************************************************************************************************************
*                                                                                                       CONSTRUCTORS
********************************************************************************************************************/

    public TableWrapper (TableView<E> table,ItemDAO<E,TableWrapper> dao) {

        this("TEST TITLE",table, dao);
    }

    public TableWrapper(String title, TableView<E> table, ItemDAO<E,TableWrapper> dao) {
        tableView = table;
        this.title = title;
        this.dao = dao;
    }

/*!******************************************************************************************************************
*                                                                                                      Getter/Setter
********************************************************************************************************************/

    public String getTitle()   {return title;}
//    public  void setTitle(String title) {this.title = title;}

//   public <E>CeartakerUID getCRUD(){return  ceartaker;}

    public ItemDAO getDAO() {
        if(this.dao == null)
            throw  new NullPointerException(TableWrapper.class.getCanonicalName());
        return dao;
    }

    public  TableView<E> tableView(){ return tableView; }

    ///
    public ObservableList<E> getItems(){ return tableView.getItems();}


    public ContextMenu getContextMenu(){ return tableView.getContextMenu();}
    public void setContextMenu(ContextMenu contextMenu){ tableView.setContextMenu(contextMenu);}

//    public void setColumnResizePolicy(Callback<ResizeFeatures, Boolean> callback ){ tableView.setColumnResizePolicy(callback);}

//    public ObjectProperty<ContextMenu> contextMenuProperty(){return tableView.contextMenuProperty();}

//    public BooleanProperty editableProperty(){return tableView.editableProperty();}

//    public void setItems(ObservableList<E> value){tableView.setItems(value);}

    //    public void setDAO(ItemDAO<E,TableWrapper> dao) {this.dao = dao;}


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
    public void setTableData(ObservableList<E> items){
        tableView.setItems(items);
        saveTableItems();
    }
    /**
     * Update TableView Items, use <b>method</b>:
     * <br><b>this.</b>getItems().setAll(newItems);
     */
    public void updateTableFromBASE(List newItems){tableView.getItems().setAll(newItems);}
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
//        tableView.refresh();
    }



    /**
     * Add new column into current table and return one.
     * @param <K>
     * @param fieldName entity field name.
     * @param name column name.
     * @return TableColumn
     */
    public  <K> TableColumn addColumn(String name, String fieldName){
        TableColumn<E,K> column = new TableColumn<>(name);
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
        TableColumn<E,K> column = new TableColumn<>(name);
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
    public  <K> TableColumn<E,K> addColumn (TableColumn<E,Objects> parentCol, String name, String fieldName){
        TableColumn<E,K> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));

        parentCol.getColumns().add(column);
        return column;
    }



    /*!******************************************************************************************************************
    *                                                                                                  TABLE VIEW METHODS
    ********************************************************************************************************************/
    public void refresh(){tableView.refresh();}

    public TableViewSelectionModel<E> getSelectionModel(){
        return tableView.getSelectionModel();
    }
    public void setEditable(boolean value){
        tableView.setEditable(value);
    }
    public void setDisable(boolean value){
        tableView.setDisable(value);
    }


}
