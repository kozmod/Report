package report.models.view.wrappers.toString;

public final class SiteToStringWrapper implements ToStringWrapper<Integer> {
    private final int integer;

    public SiteToStringWrapper(int integer) {
        this.integer = integer;
    }

    @Override
    public Integer getEntity() {
        return integer;
    }

    @Override
    public String toString() {
        return String.valueOf(integer);
    }
}
