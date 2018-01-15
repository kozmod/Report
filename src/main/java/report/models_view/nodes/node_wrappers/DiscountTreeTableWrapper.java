package report.models_view.nodes.node_wrappers;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeTableView;
import report.entities.CommonDAO;
import report.entities.items.discount_coef.DiscountCoef;
import report.entities.items.TableDItem;
import report.models.mementos.Memento;
import report.models.mementos.ReverseTableMemento;
import report.models_view.nodes.ContextMenuOptional;
import report.usage_strings.SQL;

public class DiscountTreeTableWrapper extends AbstractTableWrapper<DiscountCoef> {

    private final TreeTableView<TableDItem> treeTableView;
    private DiscountCoef discountCoef;
    private CommonDAO<DiscountCoef> commonDAO;

    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/
    public DiscountTreeTableWrapper(String title, TreeTableView treeView, CommonDAO<DiscountCoef> commonDao) {
        this(title,treeView,commonDao.getData(),commonDao);
    }
    public DiscountTreeTableWrapper(String title, TreeTableView treeView,DiscountCoef discountCoef,  CommonDAO<DiscountCoef> commonDao) {
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
    public void saveTableItems() {
        super.memento = new ReverseTableMemento<>(discountCoef);
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

    public  TreeTableView<TableDItem> tableView() {
        return  treeTableView;
    }

    public void selectionModel(){
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
        this.saveTableItems();
    }

    @Override
    public void setDataFromBASE() {
        this.discountCoef = commonDAO.getData();
        treeTableView.setRoot(discountCoef.tree());
        this.saveTableItems();
    }

    @Override
    public CommonDAO getDAO() {
        return this.commonDAO;
    }

    @Override
    public void saveSQL() {
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
