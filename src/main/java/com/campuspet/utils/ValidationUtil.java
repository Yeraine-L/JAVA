package com.campuspet.utils;

public final class ValidationUtil {
    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidPassword(String value) {
        return !isBlank(value) && value.trim().length() >= 6;
    }

    public static boolean isValidPhone(String value) {
        return value != null && value.matches("\\d{11}");
    }
}
