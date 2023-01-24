package com.epam.havryliuk.restaurant.model.util.annotations;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Predicate;


public class ApplicationServiceContext {
    private static final Predicate<Field> HAS_ANNOTATION = field -> field.getDeclaredAnnotation(Autowired.class) != null;
    private static final Logger LOG = LogManager.getLogger(ApplicationServiceContext.class);

    public <T> T getInstance(Class<T> clazz) {
        T object;
        try {
            Constructor<?> constructor = clazz.getConstructor();
            object = (T) constructor.newInstance();
        } catch (InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException |
                 NoSuchMethodException e) {
            String errorMessage = "Class doesn't have a constructor.";
            LOG.error(errorMessage);
            throw new IllegalStateException(errorMessage, e);
        }
        final Field[] declaredFields = clazz.getDeclaredFields();
        injectAnnotatedFields(object, declaredFields);
        return object;
    }

    private <T> void injectAnnotatedFields(T object, Field[] declaredFields) {
        Arrays.stream(declaredFields)
                .filter(HAS_ANNOTATION).toList()
                .forEach(field -> {
                    field.setAccessible(true);
                    Class<?> clazz = field.getType();
                    try {
                        Object annotatedObject = clazz.getConstructor().newInstance();
                        field.set(object, annotatedObject);
                        injectAnnotatedFields(object, clazz.getDeclaredFields());
                    } catch (InstantiationException |
                             IllegalAccessException |
                             InvocationTargetException |
                             NoSuchMethodException e) {
                        String errorMessage = "Field doesn't have a constructor.";
                        LOG.error(errorMessage);
                        throw new IllegalStateException(errorMessage, e);
                    }
                });
    }
}
