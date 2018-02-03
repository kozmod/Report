package report.entities.items.variable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.abstraction.Reverse;
import report.entities.items.Clone;
import report.entities.items.DItem;

public class VariableTIV_new implements Clone, Reverse {

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
    public VariableTIV_new(long id, double incomeTax, double saleExp) {
        this.id = id;
        this.incomeTax = new SimpleDoubleProperty(incomeTax);
        this.saleExp  = new SimpleDoubleProperty(saleExp);
    }
    /**
     * Clone CONSTRUCTOR implementation
     */
    public VariableTIV_new(VariableTIV_new variable) {
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
    public ObservableList<DItem> reverse(){
        ObservableList<DItem>  reverseList = FXCollections.observableArrayList(DItem.extractor());
        reverseList.add(new DItem(
                        this.id,
                        SQL.SQL_TABLE,
                        SQL.INCOME_TAX,
                        this.incomeTax.getValue()
                )
        );
        reverseList.add(new DItem(
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


    public long getId() {
        return id;
    }

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
    public VariableTIV_new getClone() {
        return new VariableTIV_new(this);
    }



}
