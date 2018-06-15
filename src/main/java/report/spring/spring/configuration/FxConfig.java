package report.spring.spring.configuration;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import report.spring.views.ControlFx;
import report.spring.views.ViewFx;

import java.io.IOException;

@SuppressWarnings("unchecked")
public interface FxConfig {

    default <V extends ViewFx<? extends Node, ? extends Initializable>> V loadView(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(url));
        Parent node = loader.load();
        return (V) new ViewFx(node, loader.getController());
    }

    default <V extends ViewFx<? extends Node, ? extends Initializable>> V loadView(String url,Initializable controller) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(url));
        loader.setController(controller);
        return (V) new ViewFx(loader.load(), controller);
    }

    default <V extends ControlFx<?, ? extends Initializable>> V loadElement(String url) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(url));
        return (V) new ControlFx(loader.load(), loader.getController());
    }

    default <V extends ControlFx<?, ? extends Initializable>> V loadElement(String url, Initializable controller) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(url));
        loader.setController(controller);
        return (V) new ControlFx(loader.load(), controller);
    }
}
