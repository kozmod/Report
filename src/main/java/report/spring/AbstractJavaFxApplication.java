package report.spring;

import javafx.application.Application;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class AbstractJavaFxApplication extends Application {

    private static String[] savedArgs;
    protected AnnotationConfigApplicationContext context;

    @Override
    public void init() {
        context =  new AnnotationConfigApplicationContext(configurationsArray());
        context.getAutowireCapableBeanFactory().autowireBean(this);

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    protected static void launchApp(Class<? extends AbstractJavaFxApplication> clazz, String[] args) {
        AbstractJavaFxApplication.savedArgs = args;
        Application.launch(clazz, savedArgs);
    }

    protected abstract Class<?>[] configurationsArray();

}
