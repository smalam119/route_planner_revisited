package com.thyme.smalam119.routeplannerapplication.LocationList;

/**
 * Created by sayedalam on 12/7/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.LocationDetailSharedPrefUtils;
import java.util.ArrayList;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListViewHolder> {
    private Context mContext;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;
    ArrayList<LocationDetail> locationDetails;

    public LocationListAdapter(Context context, ArrayList<LocationDetail> locationDetails) {
        this.mContext = context;
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(context);
        this.locationDetails = locationDetails;
    }

    @Override
    public LocationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_cell, parent, false);

        return new LocationListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LocationListViewHolder holder, final int position) {
        LocationDetail locationDetail = locationDetails.get(position);

        holder.locationTitleTV.setText(locationDetail.getLocationTitle());
        holder.locationTitleTV.setTextColor(locationDetail.getIdentifierColor());
        holder.addressLineTV.setText(locationDetail.getAddressLine());
        holder.latlngTV.setText(locationDetail.getLat() + ", " + locationDetail.getLng());
        holder.crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("cross_button",position + " clicked");
                locationDetails.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,locationDetails.size());
                mLocationDetailSharedPrefUtils.setLocationDataToSharedPref(locationDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationDetails.size();
    }
}
