package report.spring.views;

import javafx.fxml.Initializable;

public class ControlFx<N ,C extends Initializable>  {
    private final N view;
    private final C controller;

    public ControlFx(N view, C controller) {
        this.view = view;
        this.controller = controller;
    }

    public N getView() {
        return view;
    }

    public C getController() {
        return controller;
    }

}
