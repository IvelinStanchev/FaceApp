package bg.adastragrp.adastrafaceapp.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class AppUtils {

    public static float convertPxToDp(float px) {
        return px / ((float) Resources.getSystem().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertDpToPx(float dp) {
        return dp * ((float) Resources.getSystem().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
