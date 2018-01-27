package report.models_view.nodes.propertySheet_wrappers;

import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import org.controlsfx.control.PropertySheet;
import report.entities.CommonDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models_view.nodes.table_wrappers.AbstractTableWrapper;
import report.models_view.nodes.table_wrappers.BindBase;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PropertySheetWrapper implements BindBase{
    private PropertySheet sheet;
    private Map<String, LinkedList<ObservableList>> daoMap;


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public PropertySheetWrapper() {
        this.daoMap = new HashMap<>();

    }

    public PropertySheetWrapper(PropertySheet sheet, CommonDAO ... daos) {
        this();
        this.sheet = sheet;
    }

    /***************************************************************************
     *                                                                         *
     * Base                                                                    *
     *                                                                         *
     **************************************************************************/
    @Override
    public void toBase() {


    }

    @Override
    public void setFromBase() {



    }
    /***************************************************************************
     *                                                                         *
     * Memento                                                                    *
     *                                                                         *
     **************************************************************************/
    public void undoChangeItems() {

    }

    public  void saveChanges(){

    }

    public ContextMenu getContextMenu() {
        return null;
    }



    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    public void setItems(List<ObjectPSI> items){
        Map<String, LinkedList<ObservableList>> daoMap1 = items
                .stream()
                .collect(Collectors.groupingBy(
                        ObjectPSI::getSqlTableName,
                        Collector.of(
                                LinkedList::new,
                                LinkedList::add,
                                (l1, l2) -> { l1.addAll(l2); return l1;})
                ));
        sheet.getItems().setAll(items);
    }


//    public ObservableList<? extends  ObjectPSI<T>> items(){
//        return (ObservableList<? extends ObjectPSI<T>>) sheet.getItems();
//    }

}
