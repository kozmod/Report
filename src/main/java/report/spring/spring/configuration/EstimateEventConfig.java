package report.spring.spring.configuration;

import org.greenrobot.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import report.spring.spring.event.EstimateEditEventListener;

@Configuration
public class EstimateEventConfig {

    @Bean
    public EventBus eventBus(){
        return new EventBus();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @Autowired
    public EstimateEditEventListener estimateEditEventListener(EventBus eventBus){
        EstimateEditEventListener editEvent = new EstimateEditEventListener();
        eventBus.register(editEvent);
        return editEvent;
    }
}
