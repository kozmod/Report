package report.models.view.wrappers.propertySheetWrappers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.validation.ValidationSupport;
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

public class PropertySheetWrapper {
    private PropertySheet sheet;
    private  ObservableList<ObjectPSI> items;
    private ValidationSupport  validationSupport;
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
//    public void toBase() {
//        System.out.println(this.mementoMap.values());
//
//    }
//    public void setFromBase() {
//        //TODO: write DAO and add this one here
//        List<ObjectPSI> list = new ArrayList<>();
//        list.addAll(new ReqCommonDAO().getBank(55));
//        list.addAll(new ReqBankDAO().getBank(55));
//        list.addAll(new RexExBodyDAO().getBank(55));
//
//
//        this.setItems(list);
//    }

    public void setFromBase(int value) {
        items = FXCollections.observableArrayList(ObjectPSI.extractor());
        items.addAll(new ReqCommonDAO().getBank(value));
        items.addAll(new ReqBankDAO().getBank(value));
        items.addAll(new RexExBodyDAO().getBank(value));
        this.setItems(items);

    }
    /***************************************************************************
     *                                                                         *
     * Memento                                                                 *
     *                                                                         *
     **************************************************************************/
//    public void undoChangeItems() {
//        this.mementoMap.values().forEach(SheetMemento::clearChanges);
//    }
//    public  void saveChanges(){
//        this.itemMap.keySet()
//                .forEach(key -> mementoMap.put(key,new SheetMemento(itemMap.get(key))));
//    }
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    public void setItems(List<ObjectPSI> items){
//        this.itemMap = items
//                .stream()
//                .collect(Collectors.groupingBy(
//                        ObjectPSI::getSqlName,
//                        Collectors.mapping(
//                                i ->i,
//                                Collectors.toList()
//                        )
//                )
//        );
//        this.mementoMap = new HashMap<>();
//        this.saveChanges();
        sheet.getItems().setAll(items);
    }

    public PropertySheet getSheet() {
        return sheet;
    }

    public  ObservableList<ObjectPSI> getObservableItems(){
        return this.items;
    }


    public void setValidationSupport(ValidationSupport vs){
        this.validationSupport = vs;
    }

    public ValidationSupport getValidationSupport() {
        return validationSupport;
    }
}
