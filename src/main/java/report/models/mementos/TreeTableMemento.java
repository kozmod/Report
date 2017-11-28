package report.models.mementos;
import report.entities.items.TableClone;

public class TreeTableMemento<E extends TableClone> implements Memento<E>  {
    private E treeRootParentClone;

    public TreeTableMemento(E treeParent) {
        this.treeRootParentClone = treeParent;
    }

    @Override
    public E getSavedState() {
        return (E) treeRootParentClone.getClone();
    }

    private E getClone(E items){
        return (E) items.getClone();
    }
}
