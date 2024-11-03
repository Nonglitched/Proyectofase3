package com.nutrition.store.nutrition_store.utils;

public interface utils {
    String CURRENCY_SYMBOL = "$";
    String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    String DATETIME_FORMAT_PATTERN = "dd/MM/yyyy HH:mm:ss";
    
    int MAX_PRODUCT_NAME_LENGTH = 100;
    int MAX_DESCRIPTION_LENGTH = 500;
    int MIN_PASSWORD_LENGTH = 8;
    
    double MIN_PRODUCT_PRICE = 0.01;
    int MAX_DISCOUNT_PERCENTAGE = 100;
    
    String ROLE_USER = "ROLE_USER";
    String ROLE_ADMIN = "ROLE_ADMIN";
    
    String DEFAULT_TIMEZONE = "America/Argentina/Buenos_Aires";
    String DEFAULT_LOCALE = "es_AR";
}