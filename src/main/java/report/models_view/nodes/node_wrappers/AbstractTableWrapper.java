package report.models_view.nodes.node_wrappers;

import javafx.scene.control.ContextMenu;
import report.entities.CommonDAO;
import report.models.mementos.Memento;

public abstract class AbstractTableWrapper<E> {

    protected final String title;
    protected Memento<E> memento;
    protected final CommonDAO<E,AbstractTableWrapper> commonDao;


    /*!******************************************************************************************************************
    *                                                                                                       CONSTRUCTORS
    ********************************************************************************************************************/

    public AbstractTableWrapper (CommonDAO<E,AbstractTableWrapper> commonDao) {
        this("TEST TITLE", commonDao);
    }

    public AbstractTableWrapper(String title, CommonDAO<E,AbstractTableWrapper> commonDao) {
        this.title = title;
        this.commonDao = commonDao;
    }

    /*!******************************************************************************************************************
    *                                                                                                      Getter/Setter
    ********************************************************************************************************************/
    public String getTitle()   {return title;}

    public CommonDAO getDAO() {
        if(this.commonDao == null)
            throw  new NullPointerException(TableWrapper.class.getCanonicalName());
        return commonDao;
    }




    /*!******************************************************************************************************************
    *                                                                                                   Abstract MEMENTO
    ********************************************************************************************************************/
    public abstract ContextMenu getContextMenu();
    public abstract void setContextMenu(ContextMenu contextMenu);
    public abstract E getItems();
    public abstract void refresh();


    //Table DATA
    public abstract void setTableData(E items);
    public abstract void setTableDataFromBASE();

    //Memento
    public abstract void saveTableItems() ;
    public abstract void undoChangeItems();
    public abstract Memento<E> getMemento();


}
