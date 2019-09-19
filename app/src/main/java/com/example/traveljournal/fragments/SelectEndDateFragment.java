package com.example.traveljournal.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.traveljournal.R;

import java.util.Calendar;

public class SelectEndDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String APP_KEY = "android_travel_journal_key";
    Button mEndDateBtn;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = getValueFromSharedPref(this.getActivity(), "year");
        int mm = getValueFromSharedPref(this.getActivity(), "month");
        int dd = getValueFromSharedPref(this.getActivity(), "day");

        if (yy == 0 || mm == 0|| dd == 0) {
            yy = calendar.get(Calendar.YEAR);
            mm = calendar.get(Calendar.MONTH);
            dd = calendar.get(Calendar.DAY_OF_MONTH);
        }

        deleteAllValuesSharedPref(this.getActivity());

        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, yy, mm, dd);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        populateSetDate(year, month + 1, dayOfMonth);
    }

    public static int getValueFromSharedPref (Context ctx, String key) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(SelectEndDateFragment.APP_KEY, Context.MODE_PRIVATE);
        return sharedpreferences.getInt(key, 0);
    }

    public static void deleteAllValuesSharedPref (Context ctx) {
        SharedPreferences sharedpreferences = ctx.getSharedPreferences(SelectEndDateFragment.APP_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void populateSetDate(int year, int month, int day) {
        mEndDateBtn = getActivity().findViewById(R.id.end_date_btn);
        mEndDateBtn.setText(day+"/"+month+"/"+year);
    }
}