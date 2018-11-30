package bg.adastragrp.adastrafaceapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bg.adastragrp.adastrafaceapp.constants.Constants;

public class ConverterUtils {

    public static String getUserJoinedDate(Long date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.ENGLISH);
        return dateFormatter.format(new Date(date));
    }
}
