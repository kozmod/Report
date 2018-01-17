package report.models.mementos;
import report.entities.items.CloneInterface;

public class ReverseTableMemento<E extends CloneInterface> implements Memento<E>  {
    private E reverseClone;
    private E reverseMain;

    public ReverseTableMemento(E treeParent) {
        this.reverseClone = this.getClone(treeParent);
        this.reverseMain  = treeParent;
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
        throw new UnsupportedOperationException("ReverseTableMemento: unsupported operation -> .clearChanges(); !!!!");
    }

    private E getClone(E items){
        return (E) items.getClone();
    }
}
