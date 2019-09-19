package com.example.traveljournal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.traveljournal.R;
import com.example.traveljournal.fragments.SelectEndDateFragment;
import com.example.traveljournal.fragments.SelectStartDateFragment;

public class ManageTripActivity extends AppCompatActivity {

    Button mSaveTrip;
    SeekBar mPriceBar;
    TextView mCurrentTripPrice;
    RadioGroup mRadioTripType;
    Button mStartDateBtn;
    Button mEndDateBtn;
    ImageView mTripIcon;

    public static int isInteger(String s) {
        int number;
        try {
            number = Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return -1;
        } catch(NullPointerException e) {
            return -1;
        }
        // only got here if we didn't return false
        return number;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_trip);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSaveTrip = findViewById(R.id.save_trip_btn);
        mPriceBar  =findViewById(R.id.price_bar);
        mCurrentTripPrice = findViewById(R.id.current_trip_price);
        mStartDateBtn = findViewById(R.id.start_date_btn);
        mEndDateBtn = findViewById(R.id.end_date_btn);
        mRadioTripType = findViewById(R.id.radio_group_trip_type);
        mTripIcon = findViewById(R.id.list_trip_icon);
        mRadioTripType.check(findViewById(R.id.city_break_btn).getId());

        mSaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                EditText mDestination = findViewById(R.id.destination_name_input);

                String stringToPassBack = mDestination.getText().toString();
                intent.putExtra("destination", stringToPassBack);

                String mImageUrl;
                switch (stringToPassBack.toLowerCase().replaceAll("\\s+", "")) {
                    case "sanfrancisco":
                        mImageUrl = "https://pixabay.com/get/52e2d2424954ac14f6da8c7dda79367c123bddec5b556c48702973dc9f44c05bba_640.jpg";
                        break;
                    case "newyork":
                        mImageUrl = "https://pixabay.com/get/51e4dd464357b108f5d0846096293579113cd7ed574c704c732773dc9e4bc458_640.jpg";
                        break;
                    case "paris":
                        mImageUrl = "https://pixabay.com/get/57e8d6454e53a914f6da8c7dda79367c123bddec5b556c48702973dc9f44c35ebc_640.jpg";
                        break;
                    case "bucharest":
                        mImageUrl = "https://pixabay.com/get/54e1d5414c52a914f6da8c7dda79367c123bddec5b556c48702973dc9f44c351ba_640.jpg";
                        break;
                    case "tokyo":
                        mImageUrl = "https://pixabay.com/get/52e1d1424f55a414f6da8c7dda79367c123bddec5b556c48702973dc9f44c259bf_640.jpg";
                        break;
                    case "london":
                        mImageUrl = "https://pixabay.com/get/53e3d5434f57b108f5d0846096293579113cd7ed574c704c732773dc9e4ac05c_640.jpg";
                        break;
                    case "rome":
                        mImageUrl = "https://pixabay.com/get/54e9dd474b50b108f5d0846096293579113cd7ed574c704c732773dc9e4ac258_640.jpg";
                        break;
                    default:
                        mImageUrl = "https://pixabay.com/get/57e2d3474a54ae14f6da8c7dda79367c123bddec5b556c48702973dc9f44cd5abc_640.jpg";
                        break;
                }
                intent.putExtra("imageURL", mImageUrl);

                EditText mTripName = findViewById(R.id.trip_name_input);
                stringToPassBack = mTripName.getText().toString();
                intent.putExtra("tripName", stringToPassBack);

                int selectedId = mRadioTripType.getCheckedRadioButtonId();
                RadioButton tripType = findViewById(selectedId);
                stringToPassBack = tripType.getText().toString();
                intent.putExtra("tripType", stringToPassBack);

                intent.putExtra("tripPrice", mPriceBar.getProgress());

                String startDate = mStartDateBtn.getText().toString();
                String[] splittedStartDate = startDate.split("/");
                int startDay = isInteger(splittedStartDate[0]);
                if (startDay != -1)
                    intent.putExtra("startDay", startDay);
                int startMonth = isInteger(splittedStartDate[1]);
                if (startMonth != -1)
                    intent.putExtra("startMonth", startMonth);
                int startYear = isInteger(splittedStartDate[2]);
                if (startYear != -1)
                    intent.putExtra("startYear", startYear);

                String endDate = mEndDateBtn.getText().toString();
                String[] splittedEndDate = endDate.split("/");
                int endDay = isInteger(splittedEndDate[0]);
                if (endDay != -1)
                    intent.putExtra("endDay", endDay);
                int endMonth = isInteger(splittedEndDate[1]);
                if (endMonth != -1)
                    intent.putExtra("endMonth", endMonth);
                int endYear = isInteger(splittedEndDate[2]);
                if (endYear != -1)
                    intent.putExtra("endYear", endYear);

                RatingBar tripRating = findViewById(R.id.rating_bar);
                intent.putExtra("tripRating", tripRating.getRating());

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mPriceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int val = (progress * (seekBar.getWidth() - 3 * seekBar.getThumbOffset())) / seekBar.getMax();
                mCurrentTripPrice.setText("" + progress);
                mCurrentTripPrice.setX(seekBar.getX() + val);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mStartDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectStartDateFragment();
                newFragment.show(getSupportFragmentManager(), "Start Date picker");
            }
        });

        mEndDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectEndDateFragment();
                newFragment.show(getSupportFragmentManager(), "End Date picker");
            }
        });
    }
}
