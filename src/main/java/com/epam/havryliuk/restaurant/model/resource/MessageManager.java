package com.epam.havryliuk.restaurant.model.resource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("messages_en", new Locale("en", "EN"))),
    UA(ResourceBundle.getBundle("messages_ua", new Locale("uk", "UA")));
    private ResourceBundle bundle;
    MessageManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }
    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
