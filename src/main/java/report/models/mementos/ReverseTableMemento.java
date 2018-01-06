package report.models.mementos;
import report.entities.items.TableClone;

import java.util.Collection;

public class ReverseTableMemento<E extends TableClone> implements Memento<E>  {
    private E reverseClone;
    private E reverseMain;

    public ReverseTableMemento(E treeParent) {
        this.reverseClone = this.getClone(treeParent);
    }

    @Override
    public E getSavedState() {
        return (E) reverseClone.getClone();
    }

    @Override
    public E toInsert() {
        return reverseMain;
    }

    @Override
    public E toDelete() {
        return reverseClone;
    }

    @Override
    public void clearChanges() {
        throw new UnsupportedOperationException("ReverseTableMemento - unsupport .clearChanges() !!!!");
    }

    private E getClone(E items){
        return (E) items.getClone();
    }
}
