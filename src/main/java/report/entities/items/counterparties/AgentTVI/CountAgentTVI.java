package report.entities.items.counterparties.AgentTVI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.CloneInterface;

public class CountAgentTVI implements CloneInterface<CountAgentTVI> {
    private long id;
    private StringProperty name;
    private StringProperty form;
    private StringProperty type;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public CountAgentTVI(
            long id,
            String name,
            String form,
            String type
    ) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.form = new SimpleStringProperty(form);
        this.type = new SimpleStringProperty(type);
    }
    /***************************************************************************
     *                                                                         *
     * Clone Constructors                                                      *
     *                                                                         *
     **************************************************************************/
    public CountAgentTVI(CountAgentTVI source) {
        this.id = source.id;
        this.name = new SimpleStringProperty(source.name.getValue());
        this.form = new SimpleStringProperty(source.form.getValue());
        this.type = new SimpleStringProperty(source.type.getValue());
    }

    /***************************************************************************
     *                                                                         *
     * Getters\Setters                                                         *
     *                                                                         *
     **************************************************************************/
    @Override
    public CountAgentTVI getClone() {
        return new CountAgentTVI(this);
    }
    @Override
    public long getId() {
        return id;
    }
    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getForm() {
        return form.get();
    }

    public StringProperty formProperty() {
        return form;
    }

    public void setForm(String form) {
        this.form.set(form);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    /**
     *Extractor to observe changes in "Property" fields.
     *
     * @return Callback<CountAgentTVI, Observable[]>
     */
    public static Callback<CountAgentTVI, Observable[]> extractor() {
        return (CountAgentTVI p) -> new Observable[]{p.formProperty(), p.nameProperty(),p.typeProperty()};
    }

}
