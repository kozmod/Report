package report.entities.items.variable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.Reverse;
import report.entities.items.TableClone;
import report.entities.items.TableDItem;

public class TableItemVariable_new implements TableClone<TableItemVariable_new>, Reverse {

    public interface SQL{
        String SQL_TABLE = "TBL_COMMAND_PROPERTY";
        String INCOME_TAX = "INCOMTAX";
        String SALE_EXP = "sale_Exp";
    }

    private Long id;
    private final DoubleProperty incomeTax;
    private final DoubleProperty saleExp;


    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    public TableItemVariable_new(long id, double incomeTax, double saleExp) {
        this.id = id;
        this.incomeTax = new SimpleDoubleProperty(incomeTax);
        this.saleExp  = new SimpleDoubleProperty(saleExp);
    }
    /**
     * Clone CONSTRUCTOR implementation
     */
    public TableItemVariable_new(TableItemVariable_new variable) {
        this.id = variable.id;
        this.incomeTax = new SimpleDoubleProperty(variable.incomeTax.getValue());
        this.saleExp  = new SimpleDoubleProperty(variable.saleExp.getValue());
    }

    /***************************************************************************
     *                                                                         *
     * Method                                                                  *
     *                                                                         *
     **************************************************************************/

    /**
     * ObservableList from Object
     * @return
     */
    @Override
    public ObservableList<TableDItem> reverse(){
        ObservableList<TableDItem>  reverseList = FXCollections.observableArrayList(TableDItem.extractor());
        reverseList.add(new TableDItem(
                        this.id,
                        SQL.SQL_TABLE,
                        SQL.INCOME_TAX,
                        this.incomeTax.getValue()
                )
        );
        reverseList.add(new TableDItem(
                        this.id,
                        SQL.SQL_TABLE,
                        SQL.SALE_EXP,
                        this.saleExp.getValue()
                )
        );
        return reverseList;
    }
    /***************************************************************************
     *                                                                         *
     * GETTER                                                                  *
     *                                                                         *
     **************************************************************************/

    @Override
    public long getId() {
        return id;
    }
    @Override
    public void setId(long id) {
        this.id = id;
    }

    public double getIncomeTax() {
        return incomeTax.get();
    }

    public DoubleProperty incomeTaxProperty() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax.set(incomeTax);
    }

    public double getSaleExp() {
        return saleExp.get();
    }

    public DoubleProperty saleExpProperty() {
        return saleExp;
    }

    public void setSaleExp(double saleExp) {
        this.saleExp.set(saleExp);
    }

    /***************************************************************************
     *                                                                         *
     * CLONE                                                                   *
     *                                                                         *
     **************************************************************************/
    @Override
    public TableItemVariable_new getClone() {
        return new TableItemVariable_new(this);
    }



}
