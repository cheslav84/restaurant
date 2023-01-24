package com.epam.havryliuk.restaurant.model.util.tags;

public class DataParser {

    public String getItem(String data, String key) {
//        String value;
        char openBracket = '{';
        char closeBracket = '}';
        data = data.substring(data.indexOf("{")+1, data.indexOf("}"));
        String[] values = data.split(",(?=(?:[^\']*\'[^\']*\')*[^\']*$)", -1);
        String value = getRow(values, key);
        if (value.contains("\'")){
            return getStringValue(value);
        } else {
            return getNumberValue(value);
        }


//        int fistIndex = getFistIndex(data, key);
//        int lastIndex = getLastIndex(data, fistIndex);
//        if (fistIndex != -1 && lastIndex != -1) {
//            return data.substring(fistIndex, lastIndex);
//        }
//        throw new IllegalArgumentException("Impossible te get value from data.");
    }

    private String getNumberValue(String value) {
        return value.substring(value.indexOf("=")+1);
    }

    private String getStringValue(String value) {
        int fistQuote = value.indexOf("'");
        int lastQuote = value.lastIndexOf("'");
        return value.substring(fistQuote+1, lastQuote);
    }

    private String getRow(String[] values, String key) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].contains(key)){
                return values[i];
            }
        }
        throw new IllegalArgumentException("There is no " + key + " string in data.");
    }

    private int getFistIndex(String data, String key) {
        return data.lastIndexOf(key) + key.length() + 2;
    }

    private int getLastIndex(String data, int fistIndex) {
        for (int i = fistIndex; i < data.length(); i++) {
            if (data.charAt(i) == ',') {
                return i-1;
            }
        }
        return -1;
    }
}
