
package report.models_view.nodes.table_wrappers;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import report.entities.CommonDAO;
import report.entities.items.Clone;
import report.models.mementos.TableMemento;

import java.util.*;

public class TableWrapper<E extends Clone> extends AbstractTableWrapper<ObservableList<E>> {

    protected final TableView<E> tableView;

     /***************************************************************************
     *                                                                         *
     * CONSTRUCTORS                                                            *
     *                                                                         *
     **************************************************************************/
    public TableWrapper (TableView<E> table,CommonDAO<ObservableList<E>> commonDao) {
        this("TEST TITLE",table, commonDao);
    }

    public TableWrapper(String title, TableView<E> table, CommonDAO<ObservableList<E>> commonDao) {
        super(title);
        this.DAO = commonDao;
        tableView = table;

    }
    /***************************************************************************
     *                                                                         *
     * MEMENTO                                                                 *
     *                                                                         *
     **************************************************************************/
    /**
     * Save Items of TableView.
     */
    @Override
    public void saveTableItems() {
        super.memento = new TableMemento(tableView.getItems());
    }
    /**
     * Undo changes of TableView Items.
     */
    @Override
    public void undoChangeItems(){
        tableView.getItems().setAll( memento.getSavedState());
        memento.clearChanges();
    }
    /***************************************************************************
     *                                                                         *
     * SQL                                                                     *
     *                                                                         *
     **************************************************************************/
    @Override
    public void toBase(){
        Collection<E> deleteCollection = this.memento.toDelete();
        Collection<E> insertCollection = this.memento.toInsert();
        if(!deleteCollection.isEmpty())
            this.DAO.delete(this.memento.toDelete());
        if(!insertCollection.isEmpty())
            this.DAO.insert(this.memento.toInsert());
//       this.DAO.dellAndInsert(this.memento);

    }
    /**
     * Contain :
     * <br>
     * 1. setItems() from <b>SQL</b> if <u>CommonDAO</u> != <b>NULL</b>
     * <br>
     * 2. saveTableItems() - save table items to TableMemento.
     * <br>
     */
    @Override
    public void setFromBase(){
        tableView.setItems(DAO.getData());
        this.saveTableItems();
//        treeTableView.refresh();
    }

    /***************************************************************************
     *                                                                         *
     * Getters/Setters                                                         *
     *                                                                         *
     **************************************************************************/

//    @Override
//    public void setDAO(CommonDAO dao) {
//        this.DAO = dao;
//
//    }

    @Override
    public ContextMenu getContextMenu(){ return tableView.getContextMenu();}
    @Override
    public void setContextMenu(ContextMenu contextMenu){ tableView.setContextMenu(contextMenu);}
    @Override
    public ObservableList<E> getItems(){ return tableView.getItems();}

    /**
     * ONLY TableWrapper.
     * @return tableView TableView<E>
     */
    public  TableView<E> tableView(){ return tableView; }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    /**
     * Contain :
     * <br>
     * 1. setItems()
     * <br>
     * 2. saveTableItems() - save table items to TableMemento.
     * <br>
     * @param items - Observable List of table item (inherited TableItems)
     */
    @Override
    public void setTableData(ObservableList<E> items){
        tableView.setItems(items);
        this.saveTableItems();
    }
    /**
     * Update TableView Items, use <b>method</b>:
     * <br><b>this.</b>getItems().setAll(newItems);
     */
    public void updateTableFromBASE(List newItems){tableView.getItems().setAll(newItems);}



    /**
     * Add new column into current table and return one.
     * @param <K>
     * @param fieldName entity field name.
     * @param name column name.
     * @return TableColumn
     */
    public  <K> TableColumn<E,K> addColumn(String name, String fieldName){
        TableColumn<E,K> column = new TableColumn<>(name);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        tableView.getColumns().add(column);
        return column;
    }
    /**
     * Add new column use CALLBACK into current table and return one.
     * @param <K>
     * @param callback callback -> lambda.
     * @param name column name.
     * @return TableColumn
     */
    public  <K> TableColumn<E,K> addColumn(String name, Callback<TableColumn.CellDataFeatures<E,K>, ObservableValue<K>> callback){
        TableColumn<E,K> column = new TableColumn<>(name);
//        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        column.setCellValueFactory(callback);
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


    /***************************************************************************
     *                                                                         *
     * TABLE VIEW METHODS                                                      *
     *                                                                         *
     **************************************************************************/
    @Override
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
