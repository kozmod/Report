package report.entities.items.counterparties.AgentTVI;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;
import report.entities.items.Clone;
import report.entities.items.propertySheet__TEST.ObjectPSI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountAgentTVI implements Clone {
    private boolean newAdded = false;
    private int idName;
    private StringProperty name;
    private int idForm;
    private StringProperty form;
    private int idType;
    private StringProperty type;

    private Map<String,Integer> linkedNames;
    private List<ObjectPSI> reqList;


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

    public Map<String, Integer> getLinkedNames() {
        return linkedNames;
    }

    public void setLinkedNames(Map<String, Integer> linkedNames) {
        this.linkedNames = linkedNames;
    }

    public List<ObjectPSI> getReqList() {
        return reqList;
    }

    public void setReqList(List<ObjectPSI> reqList) {
        this.reqList = reqList;
    }

    public CountAgentTVI  setNewStatus(boolean value){
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
                "id-name [" + this.getIdName() +"],"+
                "name ["    + this.getName()   +"],"+
                "id-type [" + this.getIdForm() +"],"+
                "type ["    + this.getForm()   +"],"+
                "id-form [" + this.getIdType()   +"],"+
                "form ["    + this.getType()   +"]";
    }

    /***************************************************************************
     *                                                                         *
     * Extractor                                                               *
     *                                                                         *
     **************************************************************************/
    public static Callback<CountAgentTVI, Observable[]> extractor() {
        return (CountAgentTVI p) -> new Observable[]{p.formProperty(), p.nameProperty(),p.typeProperty()};
    }

}
