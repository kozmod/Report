package report.layout.controllers.estimate.new_estimate;

public enum EstimateTabsContent {
    BASE("BASE"),
    BASE_KS("BASE_KS"),
    CHANGED("CHANGED"),
    CHANGED_KS("CHANGED_KS"),
    ADDITIONAL("ADDITIONAL"),
    ADD_BASE("ADD_BASE"),
    ADD_CHANGED("ADD_CHANGED");

    EstimateTabsContent(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private String title;
}
