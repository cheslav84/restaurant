package com.epam.havryliuk.restaurant.model.util.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(ComponentScans.class)
public @interface ComponentScan {
    public String value();
}