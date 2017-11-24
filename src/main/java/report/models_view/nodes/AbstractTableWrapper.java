package report.models_view.nodes;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import report.entities.ItemDAO;
import report.entities.items.TableClone;
import report.models_view.data_utils.Memento;

public abstract class AbstractTableWrapper<E extends TableClone> {

    private final String title;
    private Memento<E> memento;
    private final  ItemDAO<E,TableWrapper> dao;

    /*!******************************************************************************************************************
    *                                                                                                       CONSTRUCTORS
    ********************************************************************************************************************/

    public AbstractTableWrapper (ItemDAO<E,TableWrapper> dao) {
        this("TEST TITLE", dao);
    }

    public AbstractTableWrapper(String title, ItemDAO<E,TableWrapper> dao) {
        this.title = title;
        this.dao = dao;
    }

    /*!******************************************************************************************************************
    *                                                                                                      Getter/Setter
    ********************************************************************************************************************/
    public String getTitle()   {return title;}

    public ItemDAO getDAO() {
        if(this.dao == null)
            throw  new NullPointerException(TableWrapper.class.getCanonicalName());
        return dao;
    }




    public abstract ContextMenu getContextMenu();
    public abstract void setContextMenu(ContextMenu contextMenu);

}
