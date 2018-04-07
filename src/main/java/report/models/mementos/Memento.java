package report.models.mementos;


import java.util.Collection;
import java.util.Set;

public interface Memento<E> {
    E getSavedState();

    E toInsert();

    E toDelete();

    void clearChanges();
}

