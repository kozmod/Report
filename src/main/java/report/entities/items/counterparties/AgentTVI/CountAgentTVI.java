package report.entities.items.counterparties.AgentTVI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.Clone;

public class CountAgentTVI implements Clone {
    private int idName;
    private StringProperty name;
    private int idForm;
    private StringProperty form;
    private int idType;
    private StringProperty type;

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public CountAgentTVI(
            int idName,
            String name,
            int idForm,
            String form,
            int idType,
            String type
    ) {
        this.idName = idName;
        this.idForm = idForm;
        this.idType = idType;
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
        this.idName = source.idName;
        this.idForm = source.idForm;
        this.idType = source.idType;
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

    public long getId() {
        //TODO - thinking about ID-interface
        return 0;
    }

    public void setId(long id) {
        //TODO - thinking about ID-interface
    }

    public int getIdName() {
        return idName;
    }

    public void setIdName(int idName) {
        this.idName = idName;
    }

    public int getIdForm() {
        return idForm;
    }

    public void setIdForm(int idForm) {
        this.idForm = idForm;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
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
