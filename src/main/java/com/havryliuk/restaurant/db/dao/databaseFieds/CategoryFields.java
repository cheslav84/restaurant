package com.havryliuk.restaurant.db.dao.databaseFieds;

import com.havryliuk.restaurant.Constants;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CategoryFields {
    private static final Properties properties = new Properties();

    public static String CATEGORY_ID;
    public static String CATEGORY_NAME;

    static {
        loadProperties();
        initialiseVariable();
    }

    private static void loadProperties() {
        try (FileReader fileReader = new FileReader(Constants.DB_FIELDS_SETTING_FILE)) {
            properties.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace(); //todo log
        }
    }

    private static void initialiseVariable() {
        CATEGORY_ID = (String) properties.get("category.id");
        CATEGORY_NAME = (String) properties.get("category.name");
    }


}
