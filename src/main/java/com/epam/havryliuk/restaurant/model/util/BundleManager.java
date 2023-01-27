package com.epam.havryliuk.restaurant.model.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum BundleManager {
    EN(ResourceBundle.getBundle("language_en", new Locale("en", "EN"))),
    UA(ResourceBundle.getBundle("language_ua", new Locale("uk", "UA")));
    private final ResourceBundle bundle;

    BundleManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
