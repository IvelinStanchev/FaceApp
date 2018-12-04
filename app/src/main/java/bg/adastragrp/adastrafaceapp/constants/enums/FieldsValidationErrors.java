package bg.adastragrp.adastrafaceapp.constants.enums;

import android.content.res.Resources;

import bg.adastragrp.adastrafaceapp.R;
import bg.adastragrp.adastrafaceapp.constants.Constants;

/**
 * Contains enums for each error connected with fields validation
 */
public enum FieldsValidationErrors implements ErrorEvent {

    EMAIL_LENGTH(R.string.emailLengthShouldBeBetween, Constants.EMAIL_MIN_LENGTH, Constants.EMAIL_MAX_LENGTH),
    EMAIL_NOT_VALID(R.string.emailNotValid),
    PASSWORD_LENGTH(R.string.passwordLengthShouldBeBetween, Constants.PASSWORD_MIN_LENGTH, Constants.PASSWORD_MAX_LENGTH),
    PASSWORDS_DO_NOT_MATCH(R.string.passwordsDoNotMatch),
    NAME_LENGTH(R.string.nameLengthShouldBeBetween, Constants.NAME_MIN_LENGTH, Constants.NAME_MAX_LENGTH),
    INVALID_ROOM_FORMAT(R.string.invalidRoomFormat),
    INVALID_DATE(R.string.youHaveToSelectStartingDate);

    private int errorString;
    private Object[] params;

    FieldsValidationErrors(int errorString) {
        this.errorString = errorString;
    }

    FieldsValidationErrors(int errorString, Object... params) {
        this.errorString = errorString;
        this.params = params;
    }

    @Override
    public String getErrorMessage(Resources resources) {
        return resources.getString(errorString, params);
    }
}
