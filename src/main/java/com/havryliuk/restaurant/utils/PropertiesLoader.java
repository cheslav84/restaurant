package com.havryliuk.restaurant.utils;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.db.connection.RestaurantConnectionPool;
import com.havryliuk.restaurant.exceptions.PropertyInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class PropertiesLoader {
    private static final Logger log = LogManager.getLogger(PropertiesLoader.class);// todo add logs for class

       public static Properties getProperties (String path) throws PropertyInitializationException {
           Properties properties = new Properties();
           System.out.println(path);
           try (InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(path)) {
               if (is == null) {
                   log.error("The properties file path haven't been found: " + path);
                   throw new IOException("The properties file path haven't been found: " + path);
               }
               properties.load(is);
           } catch (IOException e) {
               log.error("Error loading query properties from file " + path, e);
               throw new PropertyInitializationException("Error loading query properties from file " + path);
           }
           return properties;
       }

}