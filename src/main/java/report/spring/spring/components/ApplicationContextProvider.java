package report.spring.spring.components;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext CONTEXT;

    private ApplicationContextProvider() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CONTEXT = applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) CONTEXT.getBean(beanName);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return CONTEXT.getBean(beanClass);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return CONTEXT.getBean(beanName, requiredType);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName, Object... args) throws BeansException {
        return (T) CONTEXT.getBean(beanName, args);
    }
}