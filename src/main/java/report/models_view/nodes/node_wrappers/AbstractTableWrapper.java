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
    final CommonDAO<E> commonDao;

    /*!******************************************************************************************************************
    *                                                                                                       CONSTRUCTORS
    ********************************************************************************************************************/

    public AbstractTableWrapper (CommonDAO<E> commonDao) {
        this("TEST TITLE", commonDao);
    }

    public AbstractTableWrapper(String title, CommonDAO<E> commonDao) {
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
    public abstract void setDataFromBASE();

    //Memento
    public abstract void saveTableItems() ;
    public abstract void undoChangeItems();
    public abstract Memento<E> getMemento();



}
