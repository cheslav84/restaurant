package com.epam.havryliuk.restaurant.model.util.annotations;

import com.epam.havryliuk.restaurant.model.service.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * ApplicationServiceContext instantiates Service classes and the fields of that class
 * which annotated with certain annotation.
 */
public class ApplicationProcessor {
    private static final Logger LOG = LogManager.getLogger(ApplicationProcessor.class);
    /**
     * Predicate that is checking whether the field is marked by @Autowired annotation.
     */
    private static final Predicate<Field> HAS_ANNOTATION =
            field -> field.getDeclaredAnnotation(Autowired.class) != null;

    /**
     * Method receives as parameter a Service class, creates an instance of that class,
     * then gets declared fields of created class and instantiates that fields of class which
     * marked by certain annotation.
     *
     * @param clazz Service class that should be instantiated as well as the fields of that class
     *              which is marked by certain annotation.
     * @param <T>   any class that implements Service interface.
     * @return the instance of the Service class.
     * @throws IllegalStateException if some Exception occurs during instantiation Service class.
     */
    @SuppressWarnings("unchecked")
    public static synchronized  <T extends Service> T getInstance(Class<? extends Service> clazz) {
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

    /**
     * Method checks if any fields of object is annotated by certain annotation,
     * sets the proper accessibility of that fields, gets the instances of the fields
     * and sets the instance to every field that has annotation. In case of that instantiated
     * objects has its own fields with the proper annotation, the instantiation procedure
     * will be repeated with recursion.
     *
     * @param object         fields of which should be instantiated.
     * @param declaredFields an Array of declared fields of that object.
     */
    private static synchronized <T> void injectAnnotatedFields(T object, Field[] declaredFields) {
        List<Field> list = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            if (HAS_ANNOTATION.test(declaredField)) {
                list.add(declaredField);
            }
        }
        for (Field field : list) {
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
        }
//        Arrays.stream(declaredFields)
//                .filter(HAS_ANNOTATION).toList()
//                .forEach(field -> {
//                    field.setAccessible(true);
//                    Class<?> clazz = field.getType();
//                    try {
//                        Object annotatedObject = clazz.getConstructor().newInstance();
//                        field.set(object, annotatedObject);
//                        injectAnnotatedFields(object, clazz.getDeclaredFields());
//                        LOG.debug("{} initialised.", annotatedObject.getClass());//todo
//                    } catch (InstantiationException |
//                             IllegalAccessException |
//                             InvocationTargetException |
//                             NoSuchMethodException e) {
//                        String errorMessage = "Field doesn't have a constructor.";
//                        LOG.error(errorMessage);
//                        throw new IllegalStateException(errorMessage, e);
//                    }
//                });
    }
}
