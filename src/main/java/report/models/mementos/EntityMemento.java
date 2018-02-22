package report.models.mementos;
import report.entities.items.Clone;

public class EntityMemento<E extends Clone<E>> implements Memento<E>  {
    private E entityClone;
    private E entityMain;

    public EntityMemento(E treeParent) {
        this.entityClone = treeParent.getClone();
        this.entityMain = treeParent;
    }

    @Override
    public E getSavedState() {
        return entityClone.getClone();
    }

    @Override
    public E toInsert() {
        return entityMain;
    }

    @Override
    public E toDelete() {
        return entityClone;
    }

    @Override
    public void clearChanges() {
        throw new UnsupportedOperationException("EntityMemento: unsupported operation -> .clearChanges(); !!!!");
    }

}
