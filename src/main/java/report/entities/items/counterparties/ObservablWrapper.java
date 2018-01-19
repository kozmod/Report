package report.entities.items.counterparties;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.util.Pair;
import report.entities.items.Clone;
import report.models.mementos.EntityMemento;

import java.util.HashMap;
import java.util.Map;


public class ObservablWrapper<E extends Observable & Clone> {

private  final Map<Class<?>,Pair<E, EntityMemento<E>>> mementoMap;
private  final Map<Class<?>,Pair<Property, Property>> propertyMap;


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public ObservablWrapper( ) {
        this.mementoMap  = new HashMap<>();
        this.propertyMap = new HashMap<>();
    }

    public ObservablWrapper(Map<Class<?>, Pair<E, EntityMemento<E>>> mementoMap, Map<Class<?>, Pair<Property, Property>> propertyMap) {
        this.mementoMap = mementoMap;
        this.propertyMap = propertyMap;
    }
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    public void put(final E item) {
        Pair<E,EntityMemento<E>> pair = new Pair<>(item, new EntityMemento<>(item));
        mementoMap.put(item.getClass(),pair);
    }

    public void undoChanges(Class<E> key) {
       E main = mementoMap.get(key).getKey();
       E clone = mementoMap.get(key).getKey();

    }


}
