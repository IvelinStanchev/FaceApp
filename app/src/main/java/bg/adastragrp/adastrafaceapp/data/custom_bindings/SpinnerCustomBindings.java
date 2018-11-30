package bg.adastragrp.adastrafaceapp.data.custom_bindings;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SpinnerCustomBindings {

    @InverseBindingAdapter(attribute = "app:selectedItem")
    public static String getSelectedItem(AppCompatSpinner spinner) {
        Object selectedItem = spinner.getSelectedItem();
        if (selectedItem instanceof String) {
            return (String) selectedItem;
        }

        return null;
    }

    @BindingAdapter(value = { "android:onItemSelected", "app:selectedItemAttrChanged" }, requireAll = false)
    public static void setListeners(AppCompatSpinner spinner,
                                    final Spinner.OnItemSelectedListener onSelectedItemListener,
                                    final InverseBindingListener inverseBindingListener) {

        Spinner.OnItemSelectedListener newListener;

        if (inverseBindingListener == null) {
            newListener = onSelectedItemListener;
        } else {
            newListener = new Spinner.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (onSelectedItemListener != null) {
                        onSelectedItemListener.onItemSelected(parent, view, position, id);
                    }
                    inverseBindingListener.onChange();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            };
        }

        spinner.setOnItemSelectedListener(newListener);
    }

    @BindingAdapter("app:selectedItem")
    public static void setSelectedItem(Spinner spinner, String newItem) {
        Object previouslySelectedItem = spinner.getSelectedItem();
        if (previouslySelectedItem instanceof String) {
            if (newItem != null) {
                SpinnerAdapter spinnerAdapter = spinner.getAdapter();

                if (spinnerAdapter != null) {
                    for (int i = 0, itemCount = spinnerAdapter.getCount(); i < itemCount; i++) {
                        Object spinnerItem = spinnerAdapter.getItem(i);
                        if (spinnerItem instanceof String) {

                            if (spinnerItem.equals(newItem) &&
                                    !spinnerItem.equals(previouslySelectedItem)) {
                                spinner.setSelection(i);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
