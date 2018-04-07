package report.entities.abstraction;

import report.models.mementos.Memento;

public interface CommonDAO<E> {
    <HEIR extends E> HEIR getData();

    void delete(E entry);

    void insert(E entry);

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
