package report.entities.items.propertySheet__TEST;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import org.controlsfx.control.PropertySheet;
import java.util.Optional;

public class ObjectPSI<T> implements  PropertySheet.Item{
    private final ObjectProperty<T> sheetObject;
    private final String category;
    private final String description;
    private String sqlName;
    private String sqlTableName;

    /***************************************************************************
     *                                                                         *
     * CONSTRUCTORS                                                            *
     *                                                                         *
     **************************************************************************/
    public ObjectPSI(T value) {
        this("",value);
    }

    public ObjectPSI(String name,T value) {
       this(name,"-",value);
    }
    public ObjectPSI(String name,String category,T value) {
        this(name,category,"",value);
    }
    public ObjectPSI(String name,String category,String description,T value) {
        this.sheetObject = new SimpleObjectProperty<>(this, name,value);
        this.category = category;
        this.description = description;
    }
    /***************************************************************************
     *                                                                         *
     * Getters/Setters                                                         *
     *                                                                         *
     **************************************************************************/
    public ObjectPSI<T> setSqlName(String sqlName){
        this.sqlName = sqlName;
        return this;

    }
    public String getSqlName(){
        return this.sqlName;
    }
    public ObjectPSI<T> setSqlTableName(String sqlTableNameName){
        this.sqlTableName = sqlName;
        return this;

    }
    public String getSqlTableName(){
        return this.sqlTableName;
    }
    /***************************************************************************
     *                                                                         *
     * Override                                                                *
     *                                                                         *
     **************************************************************************/
    @Override
    public Class<?> getType() {
        return sheetObject.getValue().getClass();
    }

    @Override
    public String getCategory() {
        return this.category;
    }

    @Override
    public String getName() {
        return sheetObject.getName();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public T getValue() {
        return sheetObject.getValue();
    }

    @Override
    public void setValue(Object o) {
        sheetObject.setValue((T) o);

    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.of(sheetObject);
    }

    @Override
    public String toString() {
        return "ObjectPSI [" +
                "name = "       + this.getName()   +
                "value = "      + this.getValue()  +
                "SQL name = "   + this.getSqlName()+
                "type ="        + this.getType()   +
                "category = "   + this.getCategory();
    }
}
