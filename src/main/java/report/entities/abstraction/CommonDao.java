package report.entities.abstraction;

import report.models.mementos.Memento;

public interface CommonDao<E> {
    <HEIR extends E> HEIR getData();

    void delete(E entry);

    void insert(E entry);
}
