package report.models.mementos;
import report.entities.items.TableClone;

public class ReverseTableMemento<E extends TableClone> implements Memento<E>  {
    private E reverseClone;

    public ReverseTableMemento(E treeParent) {
        this.reverseClone = this.getClone(treeParent);
    }

    @Override
    public E getSavedState() {
        return (E) reverseClone.getClone();
    }

    private E getClone(E items){
        return (E) items.getClone();
    }
}
