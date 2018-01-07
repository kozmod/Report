package report.models_view.nodes.node_wrappers;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.TableSelectionModel;
import report.entities.CommonDAO;
import report.models.mementos.Memento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractTableWrapper<E> {

    Memento<E> memento;
    final String title;
//    final CommonDAO<E> commonDao;
    /***************************************************************************
     *                                                                         *
     * CONSTRUCTORS                                                            *
     *                                                                         *
     **************************************************************************/

//    public AbstractTableWrapper (CommonDAO<E> commonDao) {
//        this("TEST TITLE", commonDao);
//    }

//    public AbstractTableWrapper(String title, CommonDAO<E> commonDao) {
//        this.title = title;
//        this.commonDao = commonDao;
//    }
    public AbstractTableWrapper(String title) {
        this.title = title;
    }

    /***************************************************************************
     *                                                                         *
     * Getter/Setter                                                           *
     *                                                                         *
     **************************************************************************/
    public String getTitle()   {return title;}
    public Memento<E> getMemento(){return this.memento;};
    /***************************************************************************
     *                                                                         *
     * Abstract Methods                                                        *
     *                                                                         *
     **************************************************************************/
    /**
     * Save Items to MEMENTO.
     */
    public abstract void saveTableItems() ;
    /**
     * UNDO changes from MEMENTO.
     */
    public abstract void undoChangeItems();
    /**
     * Get DAO.
     */
    public abstract CommonDAO getDAO() ;
//        if(this.commonDao == null)
//            throw  new NullPointerException(TableWrapper.class.getCanonicalName());
//        return commonDao;
//    }

    /**
     * Send data to SQL.
     */
    public abstract void saveSQL();

    /**
     * Get ContextMenu of TableView.
     */
    public abstract ContextMenu getContextMenu();
    /**
     * Set ContextMenu to TableView.
     */
    public abstract void setContextMenu(ContextMenu contextMenu);
    /**
     * Get TableView's Items.
     */
    public abstract E getItems();
    /**
     * Refresh TableView.
     */
    public abstract void refresh();

    /**
     * Set TableData and SAVE one to MEMENTO.
     * @param items E
     */
    public abstract void setTableData(E items);
    /**
     * Set TableData from BASE (use table SQL-name) and SAVE one to MEMENTO.
     */
    public abstract void setDataFromBASE();





}
