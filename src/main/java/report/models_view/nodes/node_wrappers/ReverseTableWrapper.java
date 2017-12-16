package report.models_view.nodes.node_wrappers;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableView;
import report.entities.CommonDAO;
import report.entities.Reverse;
import report.entities.items.TableClone;
import report.entities.items.TableDItem;
import report.models.mementos.Memento;
import report.models.mementos.ReverseTableMemento;
import report.models_view.nodes.ContextMenuOptional;

public class ReverseTableWrapper<E extends Reverse & TableClone> extends AbstractTableWrapper<E> {

        private final TableView<TableDItem> tableView;
        private E reverseObj;

        /***************************************************************************
         *                                                                         *
         * Constructor                                                             *
         *                                                                         *
         **************************************************************************/
        public ReverseTableWrapper(String title, TableView<TableDItem> table, CommonDAO<E> commonDao) {
            this(title,table,null,commonDao);
        }
        public ReverseTableWrapper(String title, TableView<TableDItem> table , E reverseObj, CommonDAO<E> commonDao) {
            super(title, commonDao);
            this.reverseObj = reverseObj;
            this.tableView = table;

        }
        /***************************************************************************
         *                                                                         *
         * MEMENTO                                                                 *
         *                                                                         *
         **************************************************************************/
        @Override
        public void saveTableItems() {
            super.memento = new ReverseTableMemento<>(reverseObj);
        }

        @Override
        public void undoChangeItems() {
            this.reverseObj = this.memento.getSavedState();
            tableView.setItems(reverseObj.reverse());
            ContextMenuOptional.setTableItemContextMenuListener(this,this.tableView.getItems());
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
            this.saveTableItems();
            ContextMenuOptional.setTableItemContextMenuListener(this,this.tableView.getItems());
        }

        @Override
        public void setDataFromBASE() {
            this.setTableData( commonDao.getData());

        }

        @Override
        public E getItems() {
            return reverseObj;
        }

        @Override
        public void refresh() {
            tableView.refresh();
        }

        public  TableView<TableDItem> tableView(){
            return this.tableView;
        }

        /***************************************************************************
         *                                                                         *
         * Methods                                                                 *
         *                                                                         *
         **************************************************************************/


    }