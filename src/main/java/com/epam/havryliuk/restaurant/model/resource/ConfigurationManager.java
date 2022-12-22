package com.epam.havryliuk.restaurant.model.resource;

import java.util.ResourceBundle;

public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
    // класс извлекает информацию из файла application_path.properties
    private ConfigurationManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
