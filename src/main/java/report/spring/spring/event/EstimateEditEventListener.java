package report.spring.spring.event;

import org.greenrobot.eventbus.Subscribe;
import java.util.function.Consumer;


public class EstimateEditEventListener implements NodeEventListener<EstimateEditEvent> {

    private Consumer<EstimateEditEvent> eventConsumer;

    @Subscribe
    public void actOnEvent(EstimateEditEvent event) {
        eventConsumer.accept(event);
    }

    public Consumer<EstimateEditEvent> getEventConsumer() {
        return eventConsumer;
    }

    public void setEventConsumer(Consumer<EstimateEditEvent> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }
}
