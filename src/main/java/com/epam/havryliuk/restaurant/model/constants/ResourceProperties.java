package com.epam.havryliuk.restaurant.model.constants;

/**
 * Interface contains the list of property files that is need
 * to be loaded to application.
 */
public interface ResourceProperties {
    /**
     * Contains the names of database fields;
     */
    String DB_FIELDS_SETTING_FILE = "database_fields.properties";

    /**
     * Contains the database queries ;
     */
    String DB_QUERIES_FILE = "database_queries.properties";

    /**
     * Contains the database context properties to initialise DataSource;
     */
    String DB_CONTEXT = "db_context.properties";

    /**
     * Contains application pages paths for forwarding to that pages
     * and some commands list that is used in application for redirecting;
     */
    String PAGES_PATH_FILE = "application_path.properties";

}
