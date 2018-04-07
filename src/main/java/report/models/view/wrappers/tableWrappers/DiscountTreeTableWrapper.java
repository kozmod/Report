package report.models.view.wrappers.tableWrappers;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeTableView;
import report.entities.abstraction.CommonDAO;
import report.entities.items.DItem;
import report.entities.items.discount_coef.DiscountCoef;
import report.models.mementos.EntityMemento;
import report.models.mementos.Memento;
import report.models.view.customNodes.ContextMenuOptional;

public class DiscountTreeTableWrapper extends AbstractTableWrapper<DiscountCoef> {

    private final TreeTableView<DItem> treeTableView;
    private DiscountCoef discountCoef;
    private CommonDAO<DiscountCoef> commonDAO;

    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/
    public DiscountTreeTableWrapper(String title, TreeTableView treeView, CommonDAO<DiscountCoef> commonDao) {
        this(title, treeView, commonDao.getData(), commonDao);
    }

    public DiscountTreeTableWrapper(String title, TreeTableView treeView, DiscountCoef discountCoef, CommonDAO<DiscountCoef> commonDao) {
        super(title);
        this.commonDAO = commonDao;
        this.discountCoef = discountCoef;
        treeTableView = treeView;
        treeTableView.setRoot(discountCoef.tree());

    }

    /***************************************************************************
     *                                                                         *
     * MEMENTO                                                                 *
     *                                                                         *
     **************************************************************************/
    @Override
    public void saveMemento() {
        super.memento = new EntityMemento<>(discountCoef);
        ContextMenuOptional.setTableItemContextMenuListener(this);
    }

    @Override
    public void undoChangeItems() {
        this.discountCoef = this.memento.getSavedState();
        treeTableView.setRoot(discountCoef.tree());
        ContextMenuOptional.setTableItemContextMenuListener(this);
    }

    @Override
    public Memento<DiscountCoef> getMemento() {
        return super.memento;
    }

    public TreeTableView<DItem> tableView() {
        return treeTableView;
    }

    public void selectionModel() {
        treeTableView.getSelectionModel();
    }


    /***************************************************************************
     *                                                                         *
     * Getter/Setter                                                           *
     *                                                                         *
     **************************************************************************/
    @Override
    public ContextMenu getContextMenu() {
        return treeTableView.getContextMenu();
    }

    @Override
    public void setContextMenu(ContextMenu contextMenu) {
        treeTableView.setContextMenu(contextMenu);
//        treeTableView.getRoo ContextMenuOptional.setTableItemContextMenuListener(this);t()
//                .getQuantity()
//                .secondValueProperty()
//                .addListener((ChangeListener) (observable, oldValue, newValue) ->{
//                    contextMenu.setImpl_showRelativeToWindow(false);
//                    treeTableView.refresh();
//        });
    }

    @Override
    public void setTableData(DiscountCoef items) {
        this.discountCoef = discountCoef;
        treeTableView.setRoot(discountCoef.tree());
        this.saveMemento();
    }

    public void setFromBase() {
        this.discountCoef = commonDAO.getData();
        treeTableView.setRoot(discountCoef.tree());
        this.saveMemento();
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
    public DiscountCoef getItems() {
        return discountCoef;
    }

    @Override
    public void refresh() {
        treeTableView.refresh();
    }


    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/


}
