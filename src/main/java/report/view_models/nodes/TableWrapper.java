
package report.view_models.nodes;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import report.entities.items.TableClone;
import report.view_models.data_models.Memento;
import report.entities.ItemDAO;

public class TableWrapper<S> extends TableView<S>{
    
   private String title;
   private Memento<S > memento;
   private ItemDAO dao;
    
 
//   private CeartakerUID<TableClone> ceartaker = new CeartakerUID<TableClone>();

/*!******************************************************************************************************************
*                                                                                                       MEMENTO
********************************************************************************************************************/
    //Memento - create
            /**
            * Save Items of TableView.
            */
        public void saveTableItems() {
            memento = new Memento(this.getItems());  
        }
        
        //Memento - undo
            /**
            * Undo changes of TableView Items.
            */
        public void undoChageItems(){
            this.getItems().setAll(memento.getSavedState());
            
        }
        
        public Memento getMemento(){
            return memento;
        }
        
/*!******************************************************************************************************************
*                                                                                                       CONSTRUCTORS
********************************************************************************************************************/

    public TableWrapper()                        { super(); }
   public TableWrapper(String title)            { super(); this.title = title; }
   public TableWrapper(String title,
                       ObservableList<S> items) { super(items); this.title = title; }
   public TableWrapper(String title,
                       ObservableList<S> items,
                       ItemDAO dao)             { super(items); this.title = title;this.dao = dao; }
    
/*!******************************************************************************************************************
*                                                                                                      Getter/Setter
********************************************************************************************************************/
        
   public String getTitle()   {return title;}
//   public <S>CeartakerUID getCRUD(){return  ceartaker;}

    public ItemDAO getDAO() {return dao;}

    public  void setTitle(String title) {this.title = title;}

    public void setDAO(ItemDAO dao) {this.dao = dao;}
    
/*!******************************************************************************************************************
*                                                                                                             METHODS
********************************************************************************************************************/
    /**
     * Contain :
     * <br>
     * 1. setItems()
     * <br>
     * 2. saveTableItems() - save table items to Memento.
     * <br>
     * @param items - Observable List of table item (inherited TableItems)
     */
    public void setTableData(ObservableList<S> items){
        super.setItems(items);
        saveTableItems();
    }
    /**
     * Contain :
     * <br>
     * 1. setItems() from <b>SQL</b> if <u>DAO</u> != <b>NULL</b>
     * <br>
     * 2. saveTableItems() - save table items to Memento.
     * <br>
     */
    public void setTableDataFromBASE(){
        super.setItems(dao.getList());
        saveTableItems();
    }
    
    
    
    /** 
     * Add new column into current table and return one.
     * @param <K>
     * @param fieldName entity field name.
     * @param name column name.
     * @return TableColumn 
     */    
    public  <K> TableColumn addColumn(String name, String fieldName){
        TableColumn<S,K> column = new TableColumn(name);
                    column.setCellValueFactory(new PropertyValueFactory(fieldName));
        
                   
        this.getColumns().add(column);
        return column; 
    }
    public TableColumn addColumnTEST(String name, String fieldName){
        TableColumn column = new TableColumn(name);
                    column.setCellValueFactory(new PropertyValueFactory(fieldName));
        
                   
        this.getColumns().add(column);
        return column; 
    }

    
    /** 
     * Add new column into current table and return one.
     * @param position position to add (min: 0).
     * @param fieldName  entity field name.
     * @param name column name.
     * @return TableColumn 
     */  
    public TableColumn addColumn(int position, String name, String fieldName){
        TableColumn column = new TableColumn(name);
                    column.setCellValueFactory(new PropertyValueFactory(fieldName));
        this.getColumns().add(position,column);
        return column;
    }
    
    /**
     * Update TableView Items, use <b>method</b>:
     * <br><b>this.</b>getItems().setAll(newItems);
     * @param newItems 
     */
    public void updateTableFromBASE(List newItems){this.getItems().setAll(newItems);}
    
    
}
