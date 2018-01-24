package report.entities.items.counterparties;

import com.sun.javafx.binding.BidirectionalBinding;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.util.Pair;
import javafx.util.StringConverter;
import report.entities.items.Clone;
import report.models.mementos.EntityMemento;
import report.models.numberStringConverters.numberStringConverters.BigIntegerStringConverter;

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

    public <T> void bindBidirectional(Property<String> viewProperty, Property<T> otherProperty, StringConverter<T> converter){
        Pair<Property, Property>  pair = new Pair<>(viewProperty,otherProperty);
        Class<?> clazz = otherProperty.getBean().getClass();
        propertyMap.put(clazz,pair);
        Bindings.bindBidirectional(viewProperty,  otherProperty,converter);

    }
    public <T> void bindBidirectional(Property<T> viewProperty, Property<T> otherProperty){
        Pair<Property, Property>  pair = new Pair<>(viewProperty,otherProperty);
        Class<?> clazz = otherProperty.getBean().getClass();
        propertyMap.put(clazz,pair);
        BidirectionalBinding.bind(viewProperty, otherProperty);

    }



}
