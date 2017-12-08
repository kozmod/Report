package report.models_view.nodes.node_wrappers;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeTableView;
import report.entities.CommonDAO;
import report.entities.items.discount_coef.DiscountCoef;
import report.entities.items.discount_coef.TableDItem;
import report.models.mementos.Memento;
import report.models.mementos.TreeTableMemento;
import report.models_view.nodes.ContextMenuOptional;

public class DiscountTreeTableWrapper extends AbstractTableWrapper<DiscountCoef> {

    private final TreeTableView<TableDItem> treeTableView;
    private DiscountCoef discountCoef;

    /***************************************************************************
     *                                                                         *
     * Constructor                                                             *
     *                                                                         *
     **************************************************************************/
    public DiscountTreeTableWrapper(String title, TreeTableView treeView, CommonDAO<DiscountCoef, AbstractTableWrapper> commonDao) {
        this(title,treeView,commonDao.getData(),commonDao);
    }
    public DiscountTreeTableWrapper(String title, TreeTableView treeView,DiscountCoef discountCoef,  CommonDAO<DiscountCoef, AbstractTableWrapper> commonDao) {
        super(title, commonDao);
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
    public void saveTableItems() {super.memento = new TreeTableMemento<>(discountCoef);
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
//                .getValue()
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
        this.discountCoef = commonDao.getData();
        treeTableView.setRoot(discountCoef.tree());
        this.saveTableItems();
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
