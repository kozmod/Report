package report.models.mementos;


import report.entities.items.Clone;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import java.util.ArrayList;
import java.util.List;

/**
 * Memento to PropertySheet.
 */
public class SheetMemento implements Memento<List<ObjectPSI>> {
    private List<ObjectPSI> listClone;
    private List<ObjectPSI> listMain;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public SheetMemento(final List<ObjectPSI>  items) {
        this.listClone = this.getNewObs(items);
        this.listMain  = items;
    }
    /***************************************************************************
     *                                                                         *
     * Override                                                                *
     *                                                                         *
     **************************************************************************/
    @Override
    public List<ObjectPSI> getSavedState() {
        return listClone;
    }

    @Override
    public List<ObjectPSI> toInsert() {
        return listMain;
    }

    @Override
    public List<ObjectPSI> toDelete() {
        return listClone;
    }

    @Override
    public void clearChanges() {
        this.listClone = this.getNewObs(listMain);
    }
    /***************************************************************************
     *                                                                         *
     * Private Methods                                                         *
     *                                                                         *
     **************************************************************************/
    private List<ObjectPSI> getNewObs(List<ObjectPSI> items){
        List<ObjectPSI> newObsList = new ArrayList<>(items.size());
        items.forEach((Clone obsItem) -> newObsList.add(obsItem.getClone()));
        return newObsList;
    }
}
