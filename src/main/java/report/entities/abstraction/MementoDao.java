package report.entities.abstraction;

import report.models.mementos.Memento;

public interface MementoDao<E> extends CommonDao<E> {
    /**
     * Delete & Insert data to SQL in direct sequence
     *
     * @return ObservableList
     */
    default void dellAndInsert(Memento<E> memento) {
        this.delete(memento.toDelete());
        this.insert(memento.toInsert());
    }
}
