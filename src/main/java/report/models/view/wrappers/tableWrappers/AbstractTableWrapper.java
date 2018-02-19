package report.models.view.wrappers.tableWrappers;

import javafx.scene.control.ContextMenu;
import report.entities.abstraction.CommonDAO;
import report.models.mementos.Memento;
import report.models.view.wrappers.BindBase;
import report.models.view.wrappers.Reverting;

public abstract class AbstractTableWrapper<E> implements Reverting {

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

    /**
     * Abstract Constructor.
     * @param title
     */
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
    public void setDAO(final CommonDAO<E> dao) {
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
