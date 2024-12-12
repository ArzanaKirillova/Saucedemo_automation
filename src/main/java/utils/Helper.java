package com.saucedemo.utils;

public class Helper {

    public static double convertStringWithDollarToDouble(String amount) {
        return Double.parseDouble(amount.replace("$", ""));
    }
}