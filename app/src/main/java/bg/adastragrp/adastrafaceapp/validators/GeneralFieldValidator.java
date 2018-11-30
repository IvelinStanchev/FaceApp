package bg.adastragrp.adastrafaceapp.validators;

import android.text.TextUtils;

public class GeneralFieldValidator {

    public static boolean validateLength(String value, int minLength, int maxLength) {
        if (TextUtils.isEmpty(value) ||
                value.length() < minLength ||
                value.length() > maxLength) {
            return false;
        }

        return true;
    }

    public static boolean containsDigitsOnly(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }

        value = value.replace(".", "").replace(",", "");

        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
