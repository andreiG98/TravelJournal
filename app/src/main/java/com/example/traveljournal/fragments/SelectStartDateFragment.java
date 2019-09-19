package com.example.traveljournal.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.traveljournal.R;

import java.util.Calendar;

public class SelectStartDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    Button mStartDateBtn;
    private final static String APP_KEY = "android_travel_journal_key";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, yy, mm, dd);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        saveStartDateSharedPref(this.getActivity(), "year", year);
        saveStartDateSharedPref(this.getActivity(), "month", month);
        saveStartDateSharedPref(this.getActivity(), "day", dayOfMonth + 1);
        populateSetDate(year, month + 1, dayOfMonth);
    }

    public static void saveStartDateSharedPref (Context ctx, String key, int value) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(SelectStartDateFragment.APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void populateSetDate(int year, int month, int day) {
        mStartDateBtn = getActivity().findViewById(R.id.start_date_btn);
        mStartDateBtn.setText(day+"/"+month+"/"+year);
    }
}
