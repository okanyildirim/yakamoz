package com.hof.yakamozauth.help.service.model;


public class Utility {

    public static void notNullAndLessThan(String fieldName, String string, Integer min, Integer max) {
        if (string != null && !string.isEmpty()) {
            lessThan(fieldName, string.length(), min, max);
        } else {
            throw new IllegalArgumentException(fieldName + " cannot be empty!");
        }
    }

    public static void lessThan(String fieldName, Integer length, Integer min, Integer max) {
        if (length < min) {
            throw new IllegalArgumentException(fieldName + " size cannot lower than "+ min +" characters!");
        }

        if (length > max) {
            throw new IllegalArgumentException(fieldName + " size cannot higher than "+ max +" characters!");
        }
    }
}
