package com.hof.cms.common;
// import org.apache.commons.validator.routines.EmailValidator;

public class Utility {

    public static void notNullAndLessThan(String string,String fieldName, int min, int max){

        if (string !=null && !string.isEmpty()){
            lessThan(fieldName,string.length(),min,max);
        }
        else{
            throw new IllegalArgumentException(fieldName + " cannot be empty!");
        }

    }

    private static void lessThan(String fieldName, int length, int min, int max) {

        if (length<min){
            throw new IllegalArgumentException(fieldName + "size cannat be smaller than " + min + " characters!");
        }
        if (length>max){
            throw new IllegalArgumentException(fieldName + "size cannat be bigger than " + max + " characters!");
        }
    }

    /*public static void validateMail(String email){
        if (!EmailValidator.getInstance().isValid(email)){
            throw new IllegalArgumentException("Email is invalid!");
        }
    }*/
}
