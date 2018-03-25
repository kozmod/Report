package report.entities.contract;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import report.entities.items.Clone;

import java.io.File;

public class ContractTIV implements Clone {
    private final StringProperty contractName;
    private final LongProperty lastModified;

    public ContractTIV(File contract) {
        this.contractName = new SimpleStringProperty(contract.getName());
        this.lastModified = new SimpleLongProperty(contract.lastModified());
    }

    public String getContractName() {
        return contractName.get();
    }

    public StringProperty contractNameProperty() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName.set(contractName);
    }

    public long getLastModified() {
        return lastModified.get();
    }

    public LongProperty lastModifiedProperty() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified.set(lastModified);
    }

    @Override
    public Object getClone() {
        return null;
    }
}
