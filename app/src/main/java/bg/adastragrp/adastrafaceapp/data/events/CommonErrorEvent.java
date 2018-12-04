package bg.adastragrp.adastrafaceapp.data.events;

import android.content.res.Resources;

import bg.adastragrp.adastrafaceapp.constants.enums.ErrorEvent;

/**
 * Contains information about error happened throughout the app
 */
public class CommonErrorEvent implements ErrorEvent {

    private String message;
    private Integer messageResourceId;

    public CommonErrorEvent(String message) {
        this.message = message;
    }

    public CommonErrorEvent(int messageResourceId) {
        this.messageResourceId = messageResourceId;
    }

    @Override
    public String getErrorMessage(Resources resources) {
        if (this.message != null) {
            return this.message;
        }
        if (messageResourceId != null) {
            return resources.getString(messageResourceId);
        }

        return null;
    }
}
