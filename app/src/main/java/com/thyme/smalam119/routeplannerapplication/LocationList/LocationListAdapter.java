package com.thyme.smalam119.routeplannerapplication.LocationList;

/**
 * Created by sayedalam on 12/7/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;
import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListViewHolder> {
    private Context mContext;
    private List<LocationDetail> mLocationDetailList;

    public LocationListAdapter(Context context, List<LocationDetail> list) {
        this.mContext = context;
        this.mLocationDetailList = list;
    }

    @Override
    public LocationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_cell, parent, false);

        return new LocationListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LocationListViewHolder holder, int position) {
        LocationDetail locationDetail = mLocationDetailList.get(position);

        holder.locationTitleTV.setText(locationDetail.getLocationTitle());
        holder.addressLineTV.setText(locationDetail.getAddressLine());
        holder.latlngTV.setText(locationDetail.getLat() + ", " + locationDetail.getLng());
    }

    @Override
    public int getItemCount() {
        return mLocationDetailList.size();
    }
}
