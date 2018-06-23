package report.spring.spring.components;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public final class ApplicationContextProvider implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private ApplicationContextProvider() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Object... args) throws BeansException {
        return (T) applicationContext.getBean(beanName, args);
    }

    public <T> T getBean(Class<T> beanClass) {
        return beanClass.cast(applicationContext.getBean(beanClass));
    }

    public <T> T getBean(String beanName, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(beanName, requiredType);
    }

//    @SuppressWarnings("unchecked")
//    public <V extends Node,C extends Initializable> ViewFx<V,C> getVeiwFxBean(Class<V> viewClass, Class<C> controllerClass){
//        String[] beanNamesForType = applicationContext.getBeanNamesForType(
//                ResolvableType.forClassWithGenerics(ViewFx.class,viewClass,controllerClass)
//        );
//        return (ViewFx<V, C>) applicationContext.getBean(beanNamesForType[0]);
//
//    }
}