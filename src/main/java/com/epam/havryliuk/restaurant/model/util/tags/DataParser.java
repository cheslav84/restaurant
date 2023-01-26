package com.epam.havryliuk.restaurant.model.util.tags;

/**
 * Parses the String representation of Object, and returns the value
 * of the field of an Object by the name of that field.
 */
public class DataParser {

    /**
     * Regular expression to split String by commas, but not that commas that is
     * enclosed in quotation marks, as such commas can be inside whole sentence.
     */
    private static final String SPLIT_BY_COMMA = ",(?=(?:[^']*'[^']*')*[^']*$)";

    /**
     * Method receives String representation of object and String key by which value
     * should be extracted from the object. The values in object can be enclosed in quotation
     * marks - String ones, and cannot be enclosed in quotation marks - Numeric values.
     * That is different method implies to extract that object.
     * @param object String that represent some Object.
     * @param key by which value should be extracted, that is the name of an object field.
     * @return String value extracted from object, that is actually the value of an object field.
     */
    public String getItem(String object, String key) {
        object = object.substring(object.indexOf("{")+1, object.indexOf("}"));
        String[] fields = object.split(SPLIT_BY_COMMA, -1);
        String field = getRow(fields, key);
        if (field.contains("'")){
            return getStringValue(field);
        } else {
            return getNumberValue(field);
        }
    }

    /**
     * @param field of object represented by String, that contains the value.
     * @return value that is extracted from the field and that is not enclosed in
     * quotation marks.
     */
    private String getNumberValue(String field) {
        return field.substring(field.indexOf("=") + 1);
    }

    /**
     * @param field of object represented by String, that contains the value.
     * @return value that is extracted from the field and that is enclosed in
     * quotation marks.
     */
    private String getStringValue(String field) {
        int fistQuote = field.indexOf("'");
        int lastQuote = field.lastIndexOf("'");
        return field.substring(fistQuote + 1, lastQuote);
    }

    /**
     * Method searches a String in Strings array that contains the key.
     * @param fields array of Strings that represent object fields as data split by delimiters.
     * @param key by which value should be extracted.
     * @return String in with key and value is present.
     */
    private String getRow(String[] fields, String key) {
        for (String value : fields) {
            if (value.contains(key)) {
                return value;
            }
        }
        throw new IllegalArgumentException("There is no " + key + " string in data.");
    }
}
