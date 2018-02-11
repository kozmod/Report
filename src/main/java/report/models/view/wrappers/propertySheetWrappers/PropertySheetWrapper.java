package report.models.view.wrappers.propertySheetWrappers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.PropertySheet;
import report.entities.items.counterparties.ReqBankDAO;
import report.entities.items.counterparties.ReqCommonDAO;
import report.entities.items.counterparties.RexExBodyDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.mementos.SheetMemento;
import report.models.view.wrappers.tableWrappers.BindBase;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PropertySheetWrapper implements BindBase{
    private PropertySheet sheet;
    private Map<String, List<ObjectPSI>> itemMap;
    private Map<String, SheetMemento> mementoMap;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    /**
     * Private Constructor.
     */
    private PropertySheetWrapper() {
    }
    public PropertySheetWrapper(PropertySheet sheet) {
        this.sheet = sheet;
    }
    /***************************************************************************
     *                                                                         *
     * Base                                                                    *
     *                                                                         *
     **************************************************************************/
    @Override
    public void toBase() {
        System.out.println(this.mementoMap.values());

    }
    @Override
    public void setFromBase() {
        //TODO: write DAO and add this one here
        List<ObjectPSI> list = new ArrayList<>();
        list.addAll(new ReqCommonDAO().getBank(55));
        list.addAll(new ReqBankDAO().getBank(55));
        list.addAll(new RexExBodyDAO().getBank(55));


        this.setItems(list);
    }
    /***************************************************************************
     *                                                                         *
     * Memento                                                                 *
     *                                                                         *
     **************************************************************************/
    public void undoChangeItems() {
        this.mementoMap.values().forEach(SheetMemento::clearChanges);
    }
    public  void saveChanges(){
        this.itemMap.keySet()
                .forEach(key -> mementoMap.put(key,new SheetMemento(itemMap.get(key))));
    }
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    public void setItems(List<ObjectPSI> items){
        this.itemMap = items
                .stream()
                .collect(Collectors.groupingBy(
                        ObjectPSI::getSqlName,
                        Collectors.mapping(
                                i ->i,
                                Collectors.toList()
                        )
                )
        );
//        this.mementoMap = new HashMap<>();
//        this.saveChanges();
        sheet.getItems().setAll(items);
    }

    public PropertySheet getSheet() {
        return sheet;
    }
}
