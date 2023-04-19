package com.example.utils;

import com.example.config.AppConfig;

public class StringUtils {

    public static String capitalize(String str) {
        return str
                .toLowerCase()
                .substring(0, 1)
                .toUpperCase()
                .concat(str.substring(1));
    }

    public static String composeTransportString(String leftValue, String rightValue) {
        var sb = new StringBuilder();
        return sb
                .append(leftValue)
                .append(AppConfig.DELIMITER)
                .append(rightValue)
                .toString();
    }

    public static String[] decomposeTransportString(String transportString) {
        var components = transportString.trim().split(AppConfig.DELIMITER);
        return new String[]{ components[0], components[1] };
    }
    
}
