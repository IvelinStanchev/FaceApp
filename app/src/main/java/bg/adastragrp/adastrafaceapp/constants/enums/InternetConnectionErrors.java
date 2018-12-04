package bg.adastragrp.adastrafaceapp.constants.enums;

import android.content.res.Resources;

import bg.adastragrp.adastrafaceapp.R;

/**
 * Contains enums for each error connected with Internet disruptions
 */
public enum InternetConnectionErrors implements ErrorEvent {

    UNKNOWN(R.string.unknownError),
    NO_INTERNET_CONNECTION(R.string.noInternetConnection);

    private int errorString;

    InternetConnectionErrors(int errorString) {
        this.errorString = errorString;
    }

    @Override
    public String getErrorMessage(Resources resources) {
        return resources.getString(errorString);
    }
}
