package com.epam.havryliuk.restaurant.model.util.annotations;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ApplicationServiceContext {

    private static final Predicate<Field> HAS_ANNOTATION = field -> field.getDeclaredAnnotation(Autowired.class) != null;

    private static final Logger LOG = LogManager.getLogger(ApplicationServiceContext.class);

    public <T> T getInstance(Class<T> clazz) {
        T object = null;
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
                .filter(HAS_ANNOTATION)
                .collect(Collectors.toList())
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



//
//
//    private static boolean hasAutowiredField(Field[] declaredFields) {
//        return Arrays.stream(declaredFields)
//                .anyMatch(HAS_ANNOTATION);
//    }
//
//    private static boolean hasAutowired(final Class<?> aClass) {
//        final Field[] declaredFields = aClass.getDeclaredFields();
//        return Arrays.stream(declaredFields)
//                .anyMatch(HAS_ANNOTATION);
//    }
//
//    public static void initContext(Class<?> clazz) {
//        Reflections reflections = new Reflections("com.epam.havryliuk.restaurant.model.service");
//        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Component.class);
//        if (classSet.isEmpty()) {
//            LOG.debug("Annotation processor hasn't found any Component class.");
//        }
//        classSet.stream()
//                .filter(ApplicationServiceContext::hasAutowired)
//                .forEach(ApplicationServiceContext::buildBeans);
//    }
//
//
//    private static void buildBeans(final Class<?> aClass) {
//        final Constructor<?>[] constructors = aClass.getConstructors();
//        if (constructors.length == 0) {
//            throw new IllegalStateException();
//        }
//        Constructor<?> constructor = Arrays.stream(constructors)
//                .filter(c -> c.getParameterCount() == 0)
//                .findAny()
//                .orElseThrow(IllegalStateException::new);
//
//
//        try {
//            final Object object = constructor.newInstance();//Service object
//
//            Arrays.stream(aClass.getDeclaredFields())
//                    .filter(HAS_ANNOTATION)
//                    .collect(Collectors.toList())
//                    .forEach(field -> {
//                        field.setAccessible(true);
//                        try {
//
//                            Object o = field.get(object);//
//
//
//                            System.out.println(field);
//
//                            final Class<?> clazz = field.getType();
//
//                            final Constructor<?>[] daoConstructors = clazz.getConstructors();
//                            if (daoConstructors.length == 0) {
//                                throw new IllegalStateException();
//                            }
//                            Constructor<?> daoConstructor = Arrays.stream(daoConstructors)
//                                    .filter(c -> c.getParameterCount() == 0)
//                                    .findAny()
//                                    .orElseThrow(IllegalStateException::new);
//
//                            final Object dao = daoConstructor.newInstance();//Service object
//
//                            field.getDeclaredAnnotation(Autowired.class);
//
////                            System.out.println(field.getType());
////                            System.out.println(o.hashCode());
//
//                        } catch (IllegalAccessException e) {
//                            throw new RuntimeException(e);
//                        } catch (InvocationTargetException e) {
//                            throw new RuntimeException(e);
//                        } catch (InstantiationException e) {
//                            throw new RuntimeException(e);
//                        }
//                    });
//
//
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//




}
