package com.nutrition.store.nutrition_store.utils;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

public class utilities {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$");
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static String generateUniqueCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public static double calculateDiscount(double price, int discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        return price * (1 - (discountPercentage / 100.0));
    }

    public static String maskCreditCard(String cardNumber) {
        if (isNullOrEmpty(cardNumber)) return "";
        if (cardNumber.length() < 4) return cardNumber;
        
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}