package com.epam.havryliuk.restaurant.model.util;

import com.epam.havryliuk.restaurant.model.exceptions.PropertyInitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads properties from file to Properties class.
 */
public class PropertiesLoader {
    private static final Logger LOG = LogManager.getLogger(PropertiesLoader.class);

    /**
     * Method creates the instance of Properties and loads to it the properties from file.
     *
     * @param propertiesFileName String representing a propertiesFileName to properties file
     * @return Properties that loaded from corresponding file.
     * @throws PropertyInitializationException if the properties could not be loaded from file.
     */
    public static synchronized Properties getProperties(String propertiesFileName) throws PropertyInitializationException {
        Properties properties = new Properties();
        try (InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (is == null) {
                LOG.error("The properties file propertiesFileName haven't been found: " + propertiesFileName);
                throw new IOException("The properties file propertiesFileName haven't been found: "
                        + propertiesFileName);
            }
            properties.load(is);
        } catch (IOException e) {
            LOG.error("Error loading query properties from file " + propertiesFileName, e);
            throw new PropertyInitializationException("Error loading query properties from file "
                    + propertiesFileName);
        }
        return properties;
    }
}