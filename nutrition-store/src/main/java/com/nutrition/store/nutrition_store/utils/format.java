package com.nutrition.store.nutrition_store.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class format {
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,##0.00");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String formatCurrency(Double amount) {
        return amount != null ? "$" + CURRENCY_FORMAT.format(amount) : "$0.00";
    }

    public static String formatDate(Date date) {
        return date != null ? DATE_FORMAT.format(date) : "";
    }

    public static String formatDateTime(Date date) {
        return date != null ? DATETIME_FORMAT.format(date) : "";
    }

    public static String truncateString(String text, int maxLength) {
        if (text == null || text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + "...";
    }
}
