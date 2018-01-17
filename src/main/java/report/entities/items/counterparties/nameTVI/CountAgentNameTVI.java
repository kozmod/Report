package report.entities.items.counterparties.nameTVI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.CloneInterface;

public class CountAgentNameTVI implements CloneInterface<CountAgentNameTVI> {
    private long id;
    private StringProperty linkedName;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public CountAgentNameTVI(long id, String linkedName) {
        this.id = id;
        this.linkedName = new SimpleStringProperty(linkedName);
    }

    /***************************************************************************
     *                                                                         *
     * Clone Constructor                                                      *
     *                                                                         *
     **************************************************************************/
    public CountAgentNameTVI(CountAgentNameTVI source) {
        this.id = source.id;
        this.linkedName = new SimpleStringProperty(source.linkedName.getValue());
    }
    /***************************************************************************
     *                                                                         *
     * Getters\Setters                                                         *
     *                                                                         *
     **************************************************************************/
    @Override
    public CountAgentNameTVI getClone() {
        return new CountAgentNameTVI(this);
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
     * @return Callback<CountAgentNameTVI, Observable[]>
     */
    public static Callback<CountAgentNameTVI, Observable[]> extractor() {
        return (CountAgentNameTVI p) -> new Observable[]{p.linkedNameProperty()};
    }
}
