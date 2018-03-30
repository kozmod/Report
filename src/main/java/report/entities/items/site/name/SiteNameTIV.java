package report.entities.items.site.name;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import report.entities.items.Clone;

public class SiteNameTIV implements Clone<SiteNameTIV> {

    private final StringProperty siteName;

    public SiteNameTIV(String siteName) {

        this.siteName = new SimpleStringProperty(siteName);
    }

    public SiteNameTIV(SiteNameTIV siteNameTIV) {
        this.siteName = new SimpleStringProperty(siteNameTIV.getSiteName());
    }

    public String getSiteName() {
        return siteName.get();
    }

    public StringProperty siteNameProperty() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName.set(siteName);
    }

    @Override
    public SiteNameTIV getClone() {
        return new SiteNameTIV(this);
    }
}
