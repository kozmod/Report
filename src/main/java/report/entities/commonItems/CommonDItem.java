package report.entities.commonItems;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;
import report.entities.items.Clone;
import report.entities.items.DItem;

import java.util.Objects;

public class CommonDItem<F, S> implements Clone {

    private Long id;
    private final String sqlColumn;
    private final ObjectProperty<F> firstValue;
    private final ObjectProperty<S> secondValue;

    /***************************************************************************
     *                                                                         *
     * CONSTRUCTOR                                                             *
     *                                                                         *
     **************************************************************************/
    public CommonDItem(long id, String sqlColumnName, F fValue, S sValue) {
        this.id = id;
        this.sqlColumn = sqlColumnName;
        this.firstValue = new SimpleObjectProperty<>(fValue);
        this.secondValue = new SimpleObjectProperty<>(sValue);
    }

    /***************************************************************************
     *                                                                         *
     * Clone CONSTRUCTOR                                                       *
     *                                                                         *
     **************************************************************************/
    @Override
    public CommonDItem<F, S> getClone() {
        CommonDItem<F, S> clone = new CommonDItem<>(
                this.id,
                this.sqlColumn,
                this.getFirstValue(),
                this.getSecondValue()
        );
        return clone;
    }

    /***************************************************************************
     *                                                                         *
     * Getter / Setter                                                         *
     *                                                                         *
     **************************************************************************/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSqlColumn() {
        return sqlColumn;
    }

    public F getFirstValue() {
        return firstValue.get();
    }

    public ObjectProperty<F> firstValueProperty() {
        return firstValue;
    }

    public void setFirstValue(F firstValue) {
        this.firstValue.set(firstValue);
    }

    public S getSecondValue() {
        return secondValue.get();
    }

    public ObjectProperty<S> secondValueProperty() {
        return secondValue;
    }

    public void setSecondValue(S secondValue) {
        this.secondValue.set(secondValue);
    }

    /***************************************************************************
     *                                                                         *
     * hashCode / equals / toString                                            *
     *                                                                         *
     **************************************************************************/
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 3 * hash + Objects.hashCode(this.id) >>> 3;
        hash = 3 * hash + Objects.hashCode(this.sqlColumn) >>> 3;
        hash = 3 * hash + Objects.hashCode(this.firstValue.getValue()) >>> 3;
        hash = 3 * hash + Objects.hashCode(this.secondValue.getValue()) >>> 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommonDItem other = (CommonDItem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.sqlColumn, other.sqlColumn)) {
            return false;
        }
        if ((this.firstValue.get() == null) ? (other.firstValue.getValue() != null) : !this.firstValue.get().equals(other.firstValue.get())) {
            return false;
        }
        if ((this.secondValue.get() == null) ? (other.secondValue.getValue() != null) : this.secondValue.get() != other.secondValue.get()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ID=[" + getId() + "];" +
                "FV=[" + getFirstValue() + "];" +
                "SV=[" + getSecondValue() + "];" +
                "hashCode=[" + this.hashCode() + "]";
    }
    /***************************************************************************
     *                                                                         *
     * Extractor                                                               *
     *                                                                         *
     **************************************************************************/
    /**
     * Extractor to observe changes in "Property" fields.
     *
     * @return Callback<VariableTIV               ,                               Observable               [               ]>
     */
    public static <F, S> Callback<CommonDItem<F, S>, Observable[]> extractor() {
        return (CommonDItem<F, S> p) -> new Observable[]{p.firstValueProperty(), p.secondValueProperty()};
    }

}
