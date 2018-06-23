package report.spring.spring.configuration.controls.cells;

import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import report.entities.items.estimate.EstimateTVI;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class InKsColoredCell extends TableCell<EstimateTVI, Boolean> {

    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!isEmpty()) {
//                        this.setTextFill(Color.BLACK);
            if (item.equals(true)) this.setTextFill(Color.RED);
            if (item.equals(false)) this.setTextFill(Color.GREEN);
            setText(Boolean.toString(item));
        }
    }

}