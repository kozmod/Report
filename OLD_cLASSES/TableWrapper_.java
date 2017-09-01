package report.models_view.nodes;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import report.entities.ItemDAO;
import report.models_view.data_utils.Memento;

import java.util.List;


public class TableWrapper_<S> {

    private String       title;
    private Memento<S>   memento;
    private ItemDAO      dao;
    public  TableView<S> table;



/*!******************************************************************************************************************
*                                                                                                             MEMENTO
********************************************************************************************************************/
    //Memento - create
    /**
     * Save Items of TableView.
     */
    public void saveTableItems() {
        memento = new Memento(table.getItems());
    }

    //Memento - undo
    /**
     * Undo changes of TableView Items.
     */
    public void undoChageItems(){
        table.getItems().setAll(memento.getSavedState());

    }

    public Memento getMemento(){
        return memento;
    }


/*!******************************************************************************************************************
*                                                                                                       CONSTRUCTORS
********************************************************************************************************************/

    public TableWrapper_() {
        this.table = new TableView<>();
    }

    public TableWrapper_(String title){
        this.table = new TableView<>() ;
        this.title = title;
    }

    public TableWrapper_(String title,ObservableList<S> items) {
        this.table = new TableView<>(items) ;
        this.title = title;
    }
    public TableWrapper_(String title,ObservableList<S> items,ItemDAO dao) {
        this.table = new TableView<>(items);
        this.title = title;
        this.dao = dao;
    }

/*!******************************************************************************************************************
*                                                                                                      Getter/Setter
********************************************************************************************************************/

    public String getTitle()   {return title;}

    public ItemDAO getDAO() {return dao;}

    public  void setTitle(String title) {this.title = title;}

    public void setDAO(ItemDAO dao) {this.dao = dao;}

    public TableView<S> getTable() {return table;}

    public void setTable(TableView<S> table) { this.table = table;}

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
        table.setItems(items);
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
        table.setItems(dao.getList());
        saveTableItems();
    }

    /**
     * get tableItems
     * @return
     */
    public ObservableList<S> getItems() {
        return table.getItems();
    }


    /**
     * Add new column into current table and return one.
     * @param <K>
     * @param fieldName entity field name.
     * @param name column name.
     * @return TableColumn
     */
    public  <K> TableColumn addColumn(String name, String fieldName){
        TableColumn<S,K> column = new TableColumn(name);
        column.setCellValueFactory(new PropertyValueFactory(fieldName));


        table.getColumns().add(column);
        return column;
    }
    public TableColumn addColumnTEST(String name, String fieldName){
        TableColumn column = new TableColumn(name);
        column.setCellValueFactory(new PropertyValueFactory(fieldName));


        table.getColumns().add(column);
        return column;
    }


    /**
     * Add new column into current table and return one.
     * @param position position to add (min: 0).
     * @param fieldName  entity field name.
     * @param name column name.
     * @return TableColumn
     */
    public TableColumn addColumn(int position, String name, String fieldName){
        TableColumn column = new TableColumn(name);
        column.setCellValueFactory(new PropertyValueFactory(fieldName));
        table.getColumns().add(position,column);
        return column;
    }

    /**
     * Update TableView Items, use <b>method</b>:
     * <br><b>this.</b>getItems().setAll(newItems);
     * @param newItems
     */
    public void updateTableFromBASE(List newItems){table.getItems().setAll(newItems);}


}
