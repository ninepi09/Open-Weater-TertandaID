package com.tertandaid.openweather;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class Wheather24Adapter extends
        RecyclerView.Adapter<Wheather24Adapter.ViewHolder> {

    // Store a member variable for the contacts
    private List<Weather2> mWeather2s;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // Pass in the contact array into the constructor
    public Wheather24Adapter(List<Weather2> weather2s) {
        mWeather2s = weather2s;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tanngalItem, suhuItem;
        ImageView AwanIV;
        public TextView messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tanngalItem = (TextView) itemView.findViewById(R.id.jamItemTV);
            suhuItem = (TextView) itemView.findViewById(R.id.suhuItemTV);
            AwanIV =  itemView.findViewById(R.id.iconHourlyIM);
        }
    }
}
