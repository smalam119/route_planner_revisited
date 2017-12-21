package com.thyme.smalam119.routeplannerapplication.Profile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thyme.smalam119.routeplannerapplication.Model.User.SingleRoute;
import com.thyme.smalam119.routeplannerapplication.R;
import java.util.ArrayList;

/**
 * Created by smalam119 on 12/21/17.
 */

public class PreviousRouteListAdapter extends RecyclerView.Adapter<PreviuosRouteViewHolder> {
    private Context context;
    private ArrayList<SingleRoute> singleRoutes;

    public PreviousRouteListAdapter(Context context, ArrayList<SingleRoute> singleRoutes) {
        this.context =context;
        this.singleRoutes = singleRoutes;
    }

    @Override
    public PreviuosRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.previous_routes_cell, parent, false);

        return new PreviuosRouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PreviuosRouteViewHolder holder, int position) {

        SingleRoute singleRoute = singleRoutes.get(position);

        holder.mRouteListTitleTV.setText(singleRoute.getPathList().get(0).getLocationTitle() + " " + "to" + " "+
                singleRoute.getPathList().get(singleRoute.getPathList().size() - 1).getLocationTitle());
        holder.mRouteDistanceTV.setText(singleRoute.getTotalDistance() + "km");
    }

    @Override
    public int getItemCount() {
        return singleRoutes.size();
    }
}
