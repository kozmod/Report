package report.models.view.wrappers.propertySheet;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.validation.ValidationSupport;
import report.entities.items.counterparties.AbstractReqDao;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.mementos.ChangedMemento;
import report.models.view.customNodes.ContextMenuOptional;
import report.models.view.wrappers.Reverting;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertySheetWrapper implements Reverting {
    private PropertySheet sheet;
    private ObservableList<ObjectPSI> items;
    private Map<Integer, List<ObjectPSI>> cashedItemsMap;
    private ListChangeListener<ObjectPSI> listChangeListener;
    private AbstractReqDao[] daos;
    private ChangedMemento memento;
    private ValidationSupport validationSupport;
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

    public PropertySheetWrapper(PropertySheet sheet, AbstractReqDao... daos) {
        this.daos = daos;
        this.sheet = sheet;
        items = FXCollections.observableArrayList(ObjectPSI.extractor());
        this.cashedItemsMap = new HashMap<>();
    }

    /***************************************************************************
     *                                                                         *
     * Base                                                                    *
     *                                                                         *
     **************************************************************************/

    public void setFromBase(int value) {
//        List<ObjectPSI> list = cashedItemsMap.get(value);
//        if(list == null) {
//            list = Stream.of(daos)
//                    .flatMap(dao -> dao.getByID(value).stream())
//                    .collect(Collectors.toList());
        List<ObjectPSI> list2 = new ArrayList<>();
        Stream.of(daos)
                .forEach(dao -> {
                    list2.addAll(dao.getByID(value));

                });
//            cashedItemsMap.put(value,list);
//            this.setItems(list2);
//        }else {
        this.setItems(list2);
//        }

    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    public void setItems(List<ObjectPSI> items) {
        this.items.setAll(items);
        sheet.getItems().setAll(this.items);
        ContextMenuOptional contextMenuOptional = ((ContextMenuOptional) sheet.getContextMenu());
        if (contextMenuOptional != null
                && listChangeListener == null) {
            this.listChangeListener = item -> {
                if (item.next() && item.wasUpdated()) {
                    contextMenuOptional.setDisable_SaveUndoPrint_groupe(false);
                }
            };
            this.items.addListener(listChangeListener);
        }
        this.saveMemento();
    }

    public PropertySheet getSheet() {
        return sheet;
    }

    public ObservableList<ObjectPSI> getObservableItems() {
        return this.items;
    }


    public void setValidationSupport(ValidationSupport vs) {
        this.validationSupport = vs;
    }

    public ValidationSupport getValidationSupport() {
        return validationSupport;
    }

    /***************************************************************************
     *                                                                         *
     * Override                                                                *
     *                                                                         *
     **************************************************************************/
    @Override
    public void saveMemento() {
        memento = new ChangedMemento(this.items);
    }

    @Override
    public void undoChangeItems() {
        this.setItems(memento.getSavedState());
        this.saveMemento();

    }

    @Override
    public void toBase() {
        Map<String, List<ObjectPSI>> deleteMap = memento.toDelete().stream()
                .collect(Collectors.groupingBy(item -> item.getSqlTableName()));
        Map<String, List<ObjectPSI>> insertMap = memento.toInsert().stream()
                .collect(Collectors.groupingBy(item -> item.getSqlTableName()));
        Stream.of(daos).forEach(dao -> {
            dao.delete(deleteMap.get(dao.getSqlTableName()));
            dao.insert(insertMap.get(dao.getSqlTableName()));
        });
//        deleteMap.values().forEach(i -> i.stream().forEach(ii -> System.out.println(ii.getId())));
    }


}
