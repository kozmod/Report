package report.models.view.wrappers.table;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import report.entities.abstraction.dao.CommonDao;
import report.models.mementos.Memento;
import report.models.view.wrappers.ContainMemento;

import java.util.Optional;

public abstract class AbstractTableWrapper<E> implements ContainMemento {

    Memento<E> memento;
    final String title;
    CommonDao<E> DAO;

    /**
     * Abstract Constructor.
     *
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
    public String getTitle() {
        return title;
    }

    public Memento<E> getMemento() {
        return this.memento;
    }



    /***************************************************************************
     *                                                                         *
     * Abstract Methods                                                        *
     *                                                                         *
     **************************************************************************/
//    /**
//     * Save Items to MEMENTO.
//     */
//    @Override
//    public abstract void saveMemento() ;
//    /**
//     * UNDO changes from MEMENTO.
//     */
//    @Override
//    public abstract void undoChangeItems();
//    /**
//     * Get DAO.
//     */
    public CommonDao<E> getDAO() {
        if (this.DAO == null)
            throw new NullPointerException(TableWrapper.class.getCanonicalName());
        return DAO;
    }

    public void setDAO(final CommonDao<E> dao) {
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
     *
     * @param items E
     */
    public abstract void setTableData(E items);

}
