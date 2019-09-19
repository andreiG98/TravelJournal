package com.example.traveljournal.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.traveljournal.R;
import com.example.traveljournal.models.RoomDB;
import com.example.traveljournal.models.TripModel;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<TripModel> mTripData;
    private LayoutInflater mInflater;
    int mExpandedPosition = -1;
    View recyclerView;
    RoomDB dbInstance;

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        private TextView periodView;
        private TextView destinationView;
        private ImageView tripImage;
        private LinearLayout mTripDetails;
        private TextView mTripType;
        private TextView mTripPrice;
        private TextView mTripRating;

        public TripViewHolder(View v) {
            super(v);
            periodView = v.findViewById(R.id.list_period_trip);
            destinationView = v.findViewById(R.id.list_destination_trip);
            tripImage = v.findViewById(R.id.list_trip_icon);
            mTripDetails = v.findViewById(R.id.trip_details_expand);
            mTripType = v.findViewById(R.id.trip_type_text);
            mTripPrice = v.findViewById(R.id.trip_price_text);
            mTripRating = v.findViewById(R.id.trip_rating_text);
        }
    }

    public TripAdapter(Context context, List<TripModel> data) {
        mInflater = LayoutInflater.from(context);
        this.mTripData = data;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        recyclerView = mInflater.inflate(R.layout.trip_list_row, parent, false);
        return new TripViewHolder(recyclerView);
    }

    private String getPeriodTime(Date startDate, Date endDate) {
        if (startDate.getMonth() >= 0 && endDate.getMonth() <= 2 && startDate.getMonth() <= endDate.getMonth())
            return "Winter " + endDate.getYear();

        if (startDate.getMonth() >= 3 && endDate.getMonth() <= 5 && startDate.getMonth() <= endDate.getMonth())
            return "Spring " + endDate.getYear();

        if (startDate.getMonth() >= 6 && endDate.getMonth() <= 8 && startDate.getMonth() <= endDate.getMonth())
            return "Summer " + endDate.getYear();

        if (startDate.getMonth() >= 9 && endDate.getMonth() <= 11 && startDate.getMonth() <= endDate.getMonth())
            return "Fall " + endDate.getYear();

        return String.valueOf(endDate.getYear());
    }

    @Override
    public void onBindViewHolder(@NonNull final TripViewHolder tripViewHolder, final int position) {
        final TripModel currentTrip = mTripData.get(position);

        String imageUrl = currentTrip.getTripIconURL();
        Picasso
                .get()
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(tripViewHolder.tripImage);

        String periodTime = getPeriodTime(currentTrip.getStartDate(), currentTrip.getEndDate());
        if (periodTime != null)
            tripViewHolder.periodView.setText(periodTime);
        tripViewHolder.destinationView.setText(currentTrip.getDestination());
        tripViewHolder.mTripType.setText("Trip type: " + currentTrip.getTripType());
        tripViewHolder.mTripPrice.setText("Price: " + String.valueOf(currentTrip.getTripPrice()) + " euro");
        tripViewHolder.mTripRating.setText("Rating: " + String.valueOf(currentTrip.getTripRating()));

        final boolean isExpanded = position == mExpandedPosition;
        tripViewHolder.mTripDetails.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        tripViewHolder.itemView.setActivated(isExpanded);
        tripViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1 : position;
                notifyDataSetChanged();
            }
        });
        tripViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                new AlertDialog.Builder(v.getContext(), R.style.deleteDialog)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mTripData.remove(position);
                                dbInstance = RoomDB.getInstance(v.getContext());
                                dbInstance.deleteTrip(currentTrip);
                                notifyDataSetChanged();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTripData.size();
    }
}
