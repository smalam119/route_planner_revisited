package com.thyme.smalam119.routeplannerapplication.ResultLocationList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;
import java.util.ArrayList;

/**
 * Created by smalam119 on 12/19/17.
 */

public class ResultLocationListAdapter extends RecyclerView.Adapter<ResultLocationListViewHolder> {
    private Context mContext;
    private ArrayList<LocationDetail> locationDetails;

    public ResultLocationListAdapter(Context mContext, ArrayList<LocationDetail> locationDetails) {
        this.mContext = mContext;
        this.locationDetails = locationDetails;
    }

    @Override
    public ResultLocationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_location_cell, parent, false);

        return new ResultLocationListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResultLocationListViewHolder holder, int position) {
        LocationDetail locationDetail = locationDetails.get(position);

        holder.sequenceNumber.setText(position + 1 + "");
        holder.sequenceNumber.setTextColor(locationDetail.getIdentifierColor());
        holder.locationName.setText(locationDetail.getLocationTitle());
        holder.locationName.setTextColor(locationDetail.getIdentifierColor());
    }

    @Override
    public int getItemCount() {
        return locationDetails.size();
    }
}
