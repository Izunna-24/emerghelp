package com.emerghelp.emerghelp.security.utils;

public class EmailUtils {

    public static String getEmailMessage(String name, String host) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. \n\n" +
                getVerificationUrl(host) + "\n\nThe support Team";
    }

    public static String getVerificationUrl(String host) {
        return host;
    }
}