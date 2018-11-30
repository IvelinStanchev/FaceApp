package bg.adastragrp.adastrafaceapp.data.custom_bindings;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;

import bg.adastragrp.adastrafaceapp.constants.enums.ErrorEvent;

public class EditTextCustomBindings {

    @BindingAdapter("app:errorText")
    public static void setErrorText(TextInputLayout view, ErrorEvent errorEvent) {
        if (errorEvent != null) {
            view.setErrorEnabled(true);
            view.getParent().requestChildFocus(view, view);
            view.setError(errorEvent.getErrorMessage(view.getContext().getResources()));
        } else {
            view.setErrorEnabled(false);
            view.setError(null);
        }
    }
}
