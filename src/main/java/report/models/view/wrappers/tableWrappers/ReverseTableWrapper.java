package report.models.view.wrappers.tableWrappers;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableView;
import report.entities.abstraction.CommonDAO;
import report.entities.abstraction.Reverse;
import report.entities.items.Clone;
import report.entities.items.DItem;
import report.models.mementos.EntityMemento;
import report.models.mementos.Memento;
import report.models.view.customNodes.ContextMenuOptional;

public class ReverseTableWrapper<E extends Reverse & Clone> extends AbstractTableWrapper<E> {

    private final TableView<DItem> tableView;
    private E reverseObj;
    private CommonDAO<E> commonDAO;

    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/
    public ReverseTableWrapper(String title, TableView<DItem> table, CommonDAO<E> commonDao) {
        this(title, table, null, commonDao);
    }

    public ReverseTableWrapper(String title, TableView<DItem> table, E reverseObj, CommonDAO<E> commonDao) {
        super(title);
        this.commonDAO = commonDao;
        this.reverseObj = reverseObj;
        this.tableView = table;

    }

    /***************************************************************************
     *                                                                         *
     * MEMENTO                                                                 *
     *                                                                         *
     **************************************************************************/
    @Override
    public void saveMemento() {
        super.memento = new EntityMemento<>(reverseObj);
    }

    @Override
    public void undoChangeItems() {
        this.reverseObj = this.memento.getSavedState();
        tableView.setItems(reverseObj.reverse());
        ContextMenuOptional.setTableItemContextMenuListener(this, this.tableView.getItems());
    }

    @Override
    public Memento<E> getMemento() {
        return super.memento;
    }

    /***************************************************************************
     *                                                                         *
     * Getter/Setter                                                           *
     *                                                                         *
     **************************************************************************/
    @Override
    public ContextMenu getContextMenu() {
        return tableView.getContextMenu();
    }

    @Override
    public void setContextMenu(ContextMenu contextMenu) {
        tableView.setContextMenu(contextMenu);
    }

    @Override
    public void setTableData(E items) {
        this.reverseObj = items;
        tableView.setItems(reverseObj.reverse());
        this.saveMemento();
        ContextMenuOptional.setTableItemContextMenuListener(this, this.tableView.getItems());
    }

    public void setFromBase() {
        this.setTableData(commonDAO.getData());

    }

    @Override
    public CommonDAO getDAO() {
        return this.commonDAO;
    }

    @Override
    public void toBase() {
        this.commonDAO.dellAndInsert(this.memento);
    }

    @Override
    public E getItems() {
        return reverseObj;
    }

    @Override
    public void refresh() {
        tableView.refresh();
    }

    public TableView<DItem> tableView() {
        return this.tableView;
    }


}