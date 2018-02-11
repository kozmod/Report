package report.entities.items.propertySheet__TEST;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import org.controlsfx.control.PropertySheet;
import report.entities.items.Clone;
import report.entities.items.Item;

import java.util.Optional;

/**
 * Universal object to PropertySheet(<b>controlsfx</b>).
 * @param <T>
 */
public class ObjectPSI<T> implements  Clone,PropertySheet.Item{
    private final ObjectProperty<T> sheetObject;
    private final String category;
    private final String description;
    private final String sqlName;
    private final String sqlTableName;
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
        this(name,category,"",value,"","");
    }
    public ObjectPSI(String name,String category,String description,T value,String sqlName, String sqlTableName) {
        this.sheetObject = new SimpleObjectProperty<>(this, this.chaheNames(name),value);
        this.category = category;
        this.description = description;
        this.sqlName = sqlName;
        this.sqlTableName = sqlTableName;
    }

    /***************************************************************************
     *                                                                         *
     * Getters/Setters                                                         *
     *                                                                         *
     **************************************************************************/

    //TODO: think about abstraction and SQL-interface
    public  String getSqlName(){
        return this.sqlName;
    }

    public String getSqlTableName(){
        return this.sqlTableName;
    }
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    private String chaheNames(String name){
        return name.replace(' ','\n');
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
    public <E> E getClone() {
        return (E) new  ObjectPSI(this.getName(),
                this.getCategory(),
                this.getDescription(),
                this.getValue(),
                this.getSqlName(),
                this.getSqlTableName()
        );
    }
    public long getId() {
        return 0;
    }

    public void setId(long id) {

    }
    /***************************************************************************
     *                                                                         *
     * Equals/HashCode/toString                                                *
     *                                                                         *
     **************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectPSI<?> objectPSI = (ObjectPSI<?>) o;
        if (sheetObject != null
                ? !sheetObject.getValue().equals(objectPSI.sheetObject.getValue())
                : objectPSI.sheetObject != null)
            return false;
        if (category != null
                ? !category.equals(objectPSI.category)
                : objectPSI.category != null)
            return false;
        if (description != null
                ? !description.equals(objectPSI.description)
                : objectPSI.description != null)
            return false;
        if (sqlName != null
                ? !sqlName.equals(objectPSI.sqlName)
                : objectPSI.sqlName != null)
            return false;
        return sqlTableName != null
                ? sqlTableName.equals(objectPSI.sqlTableName)
                : objectPSI.sqlTableName == null;
    }
    @Override
    public int hashCode() {
        int result = sheetObject.getValue() != null ? sheetObject.getValue().hashCode() : 0;
        result = 4 * result + (category != null ? category.hashCode() : 0);
        result = 4 * result + (description != null ? description.hashCode() : 0);
        result = 4 * result + (sqlName != null ? sqlName.hashCode() : 0);
        result = 4 * result + (sqlTableName != null ? sqlTableName.hashCode() : 0);
        return result;
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
    /***************************************************************************
     *                                                                         *
     * Extractor                                                               *
     *                                                                         *
     **************************************************************************/
    public static Callback<ObjectPSI, Observable[]> extractor() {
        return (ObjectPSI p) -> new Observable[]{p.sheetObject};
    }
}
