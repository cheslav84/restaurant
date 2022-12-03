package com.havryliuk.restaurant.utils;

import com.havryliuk.restaurant.Constants;
import com.havryliuk.restaurant.exceptions.PropertyInitializationException;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class PropertiesLoader {
    static Logger log = Logger.getLogger(PropertiesLoader.class.getName());

       public static Properties getProperties (String path) throws PropertyInitializationException {
           Properties properties = new Properties();
           try (InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(path)) {
               properties.load(is);
           } catch (IOException e) {
               log.error("Error loading query properties from file " + path, e);
               throw new PropertyInitializationException();
           }
           return properties;
       }

}
