package com.epam.havryliuk.restaurant.model.util.tags;

public class DataParser {

    public String getItem(String data, String key) {
        data = data.substring(data.indexOf("{")+1, data.indexOf("}"));
        String[] values = data.split(",(?=(?:[^\']*\'[^\']*\')*[^\']*$)", -1);
        String value = getRow(values, key);
        if (value.contains("\'")){
            return getStringValue(value);
        } else {
            return getNumberValue(value);
        }
    }

    private String getNumberValue(String value) {
        return value.substring(value.indexOf("=") + 1);
    }

    private String getStringValue(String value) {
        int fistQuote = value.indexOf("'");
        int lastQuote = value.lastIndexOf("'");
        return value.substring(fistQuote + 1, lastQuote);
    }

    private String getRow(String[] values, String key) {
        for (String value : values) {
            if (value.contains(key)) {
                return value;
            }
        }
        throw new IllegalArgumentException("There is no " + key + " string in data.");
    }
}
