package report.entities;


import report.models_view.nodes.node_wrappers.AbstractTableWrapper;

public interface CommonDAO<C,T extends AbstractTableWrapper> extends TableDataBaseName{
    C getList();
    void delete(C entry);
    void insert(C  entry);

    default void  dellAndInsert(T object) {
        this.delete((C)object.getMemento().getSavedState());
        this.insert((C)object.getItems());


    }

}
