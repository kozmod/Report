package report.entities.items.counterparties;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.TableClone;

public class TableItemCountName implements TableClone<TableItemCountName> {
    private long id;
    private StringProperty linkedName;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public TableItemCountName(long id, String linkedName) {
        this.id = id;
        this.linkedName = new SimpleStringProperty(linkedName);
    }

    /***************************************************************************
     *                                                                         *
     * Clone Constructor                                                      *
     *                                                                         *
     **************************************************************************/
    public TableItemCountName(TableItemCountName source) {
        this.id = source.id;
        this.linkedName = new SimpleStringProperty(source.linkedName.getValue());
    }
    /***************************************************************************
     *                                                                         *
     * Getters\Setters                                                         *
     *                                                                         *
     **************************************************************************/
    @Override
    public TableItemCountName getClone() {
        return new TableItemCountName(this);
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public void setId(long id) {

    }

    public String getLinkedName() {
        return linkedName.get();
    }

    public StringProperty linkedNameProperty() {
        return linkedName;
    }

    public void setLinkedName(String linkedName) {
        this.linkedName.set(linkedName);
    }
    /**
     *Extractor to observe changes in "Property" fields.
     *
     * @return Callback<TableItemCountName, Observable[]>
     */
    public static Callback<TableItemCountName, Observable[]> extractor() {
        return (TableItemCountName p) -> new Observable[]{p.linkedNameProperty()};
    }
}
