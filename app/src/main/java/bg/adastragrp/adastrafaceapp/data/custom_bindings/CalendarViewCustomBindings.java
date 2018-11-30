package bg.adastragrp.adastrafaceapp.data.custom_bindings;

import android.databinding.BindingAdapter;
import android.widget.CalendarView;

public class CalendarViewCustomBindings {

    @BindingAdapter("android:onDateChanged")
    public static void setListeners(CalendarView calendarView,
                                    final CalendarView.OnDateChangeListener onDateChangeListener) {

        calendarView.setOnDateChangeListener(onDateChangeListener);
    }

    @BindingAdapter("app:date")
    public static void setDate(CalendarView calendarView, final Long newDate) {
        if (newDate != null && calendarView.getDate() != newDate) {
            calendarView.setDate(newDate);
        }
    }
}