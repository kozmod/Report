package report.models_view.nodes.table_wrappers;

import com.sun.istack.internal.NotNull;
import javafx.scene.control.ContextMenu;
import report.entities.CommonDAO;
import report.models.mementos.Memento;

public abstract class AbstractTableWrapper<E> implements BindBase {

    Memento<E> memento;
    final String title;
    CommonDAO<E> DAO;

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
    public CommonDAO<E> getDAO() {
        if(this.DAO == null)
            throw  new NullPointerException(TableWrapper.class.getCanonicalName());
        return DAO;
    }
    public void setDAO(@NotNull final CommonDAO<E> dao) {
        this.DAO = dao;
    }
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

}
