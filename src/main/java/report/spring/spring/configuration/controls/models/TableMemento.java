
package report.spring.spring.configuration.controls.models;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import report.entities.items.Clone;
import report.models.mementos.Memento;

import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TableMemento<E extends Clone<E>> implements Memento<Collection<E>> {

    private final Logger logger;

    private final Set<E> deleteSet;
    private final Set<E> insertSet;

    private ObservableList<E> tableMemento;

    @Autowired
    public TableMemento(Logger logger) {
        this.logger = logger;
        this.deleteSet = Collections.newSetFromMap(new IdentityHashMap<>());
        this.insertSet = Collections.newSetFromMap(new IdentityHashMap<>());
    }

    //todo add interface !!!
    public void initState(ObservableList<E> tableItems){
        this.tableMemento = getNewObs(tableItems);
        addListener(tableItems);
        clear();
    }

    @Override
    public ObservableList<E> getSavedState() {
        return this.getNewObs(tableMemento);
    }

    @Override
    public Collection<E> toInsert() {
        return insertSet;
    }

    @Override
    public Collection<E> toDelete() {
        return deleteSet;
    }

    @Override
    public void clear() {
        deleteSet.clear();
        insertSet.clear();
    }

    private ObservableList<E> getNewObs(ObservableList<E> items) {
        ObservableList<E> newObsList = FXCollections.observableArrayList();
        items.forEach((E obsItem) -> newObsList.add(obsItem.getClone()));

        return newObsList;
    }

    private void addListener(ObservableList<E> items) {
        items.addListener((ListChangeListener<E>) observable -> {
            if (observable.next()) {
                if (observable.wasAdded()) {
                    insertSet.addAll(observable.getAddedSubList());
//                    logger.info("\32[31m + Added \33[0m");
                } else if (observable.wasUpdated()) {
                    E item = items.get(observable.getFrom());
                    if (!insertSet.contains(item)) {
                        deleteSet.add(item);
                        insertSet.add(item);
//                        logger.info("\32[31m + up \33[0m");
                    }
                } else if (observable.wasRemoved()) {
                    List<? extends E> itemList = observable.getRemoved();
                    insertSet.removeAll(itemList);
                    deleteSet.addAll(itemList);
//                    logger.info("\32[31m + rem \33[0m");
                }
            }
        });
    }
}
