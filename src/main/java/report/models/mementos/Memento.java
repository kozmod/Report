package report.models.mementos;



public interface Memento<E> {
    E getSavedState();

    E toInsert();

    E toDelete();

    void clear();
}

