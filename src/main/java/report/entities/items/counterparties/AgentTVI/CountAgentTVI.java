package report.entities.items.counterparties.AgentTVI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import report.entities.items.Clone;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.view.LinkedNamePair;

public class CountAgentTVI implements Clone {
    private boolean newAdded = false;
    private int idName;
    private StringProperty name;
    private int idForm;
    private StringProperty form;
    private int idType;
    private StringProperty type;
    private int idCountConst;

    private ObservableList<LinkedNamePair> linkedNames;
    private ObservableList<ObjectPSI> requisites;


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
            String type,
            int idCountConst
    ) {
        this.idName = idName;
        this.idForm = idForm;
        this.idType = idType;
        this.idCountConst = idCountConst;
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
        this.idCountConst = source.idCountConst;
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

    public void setIdCountConst(int idCountConst) {
        this.idCountConst = idCountConst;
    }

    public int getIdCountConst() {
        return idCountConst;
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

    public ObservableList<LinkedNamePair> getLinkedNames() {
        return linkedNames;
    }

    public void setLinkedNames(ObservableList<LinkedNamePair> linkedNames) {
        this.linkedNames = linkedNames;
    }

    public ObservableList<ObjectPSI> getRequisites() {
        return requisites;
    }

    public void setRequisites(ObservableList<ObjectPSI> requisites) {
        this.requisites = requisites;
    }

    public CountAgentTVI setNewStatus(boolean value) {
        this.newAdded = value;
        return this;
    }

    /***************************************************************************
     *                                                                         *
     * Equals / HashCode / toString                                            *
     *                                                                         *
     **************************************************************************/
    @Override
    public String toString() {
        return "CountAgentTVI: " +
                "id-name [" + getIdName() + "]," +
                "name [" + getName() + "]," +
                "id-type [" + getIdForm() + "]," +
                "type [" + getForm() + "]," +
                "id-form [" + getIdType() + "]," +
                "form [" + getType() + "]" +
                "id-count-const [" + getIdCountConst() + "]";
    }

    /***************************************************************************
     *                                                                         *
     * Extractor                                                               *
     *                                                                         *
     **************************************************************************/
    public static Callback<CountAgentTVI, Observable[]> extractor() {
        return (CountAgentTVI p) -> new Observable[]{p.formProperty(), p.nameProperty(), p.typeProperty()};
    }

}
