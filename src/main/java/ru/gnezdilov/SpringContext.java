package ru.gnezdilov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.gnezdilov.dao.exception.NotFoundException;

public class SpringContext {
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        if (context == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new NotFoundException(e);
            }
            context = new AnnotationConfigApplicationContext(MainConfiguration.class);
        }
        return context;
    }
}
