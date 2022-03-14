package Registration.Helpers;

import io.cucumber.junit.Cucumber;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class CustomRunner<cucumber> extends Runner {

    private Class<cucumber> classValue;
    private Cucumber cucumber;

    public CustomRunner(Class<cucumber> classValue) throws Exception {
        this.classValue = classValue;
        cucumber = new Cucumber(classValue);
    }

    @Override
    public Description getDescription() {
        return cucumber.getDescription();
    }

    private void runAnnotatedMethods(Class<?> annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methods = this.classValue.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(null);
                    break;
                }
            }
        }
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            runAnnotatedMethods(BeforeSuite.class);
            cucumber = new Cucumber(classValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cucumber.run(notifier);
    }

  
}