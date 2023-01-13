package com.epam.havryliuk.restaurant.model.util;

import com.epam.havryliuk.restaurant.model.exceptions.PropertyInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static final Logger log = LogManager.getLogger(PropertiesLoader.class);

       public static Properties getProperties (String path) throws PropertyInitializationException {
           Properties properties = new Properties();
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