package report.models.view.wrappers.toString;

public final class SiteToStringWrapper implements ToStringWrapper<String> {
    private final String siteNumber;

    public SiteToStringWrapper(String siteNumber) {
        this.siteNumber = siteNumber;
    }

    @Override
    public String getEntity() {
        return siteNumber;
    }

    @Override
    public String toString() {
        return siteNumber;
    }
}
