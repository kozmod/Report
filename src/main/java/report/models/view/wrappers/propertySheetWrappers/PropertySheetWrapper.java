package report.models.view.wrappers.propertySheetWrappers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.validation.ValidationSupport;
import report.entities.items.counterparties.AbstractReqDAO;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.mementos.ChangedMemento;
import report.models.view.customNodes.ContextMenuOptional;
import report.models.view.wrappers.Reverting;

import java.util.*;
import java.util.stream.Stream;

public class PropertySheetWrapper implements Reverting {
    private PropertySheet sheet;
    private ObservableList<ObjectPSI> items;
    private AbstractReqDAO[] daos;
    private Map<String,ChangedMemento> mementos;
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
    public PropertySheetWrapper(PropertySheet sheet,AbstractReqDAO ... daos) {
        this.daos  = daos;
        this.sheet = sheet;
    }
    /***************************************************************************
     *                                                                         *
     * Base                                                                    *
     *                                                                         *
     **************************************************************************/

    public void setFromBase(int value) {
        items = FXCollections.observableArrayList(ObjectPSI.extractor());
        mementos = new HashMap<>(daos.length,2);
        Stream.of(daos)
                .forEach(dao -> {
                    List<ObjectPSI>  list= dao.getByID(value);
                    items.addAll(list);
                    mementos.put(dao.sqlTableName(),new ChangedMemento(list));
                });
        this.setItems(items);


    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    public void setItems(List<ObjectPSI> items){
        sheet.getItems().setAll(items);
        this.items.addListener((ListChangeListener<ObjectPSI>) item -> {
            if(item.next() && item.wasUpdated()) {
                ((ContextMenuOptional) sheet.getContextMenu()).setDisable_SaveUndoPrint_groupe(false);
                mementos.get(items.get(item.getFrom()).getSqlTableName()).setChanged(true);
            }

        });


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
    /***************************************************************************
     *                                                                         *
     * Override                                                                *
     *                                                                         *
     **************************************************************************/
    @Override
    public void saveMemento() {

    }

    @Override
    public void undoChangeItems() {

    }

    @Override
    public void toBase() {
        Stream.of(daos).forEach( dao ->{
            ChangedMemento memento = mementos.get(dao.sqlTableName());
            if(memento.isChanged()){
                dao.insert(memento.toInsert());
            }
        });

    }


}
