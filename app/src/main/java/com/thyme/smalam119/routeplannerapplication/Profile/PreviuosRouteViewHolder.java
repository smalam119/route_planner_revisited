package com.thyme.smalam119.routeplannerapplication.Profile;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thyme.smalam119.routeplannerapplication.R;

/**
 * Created by smalam119 on 12/21/17.
 */

public class PreviuosRouteViewHolder extends RecyclerView.ViewHolder {
    public TextView mRouteListTitleTV, mRouteDistanceTV;
    public CardView cardView;

    public PreviuosRouteViewHolder(View itemView) {
        super(itemView);
        prepareView(itemView);
    }

    public void prepareView(View itemView) {
        mRouteListTitleTV = (TextView)  itemView.findViewById(R.id.title_route_list);
        mRouteDistanceTV = (TextView)  itemView.findViewById(R.id.total_route_distance);
        cardView = (CardView) itemView.findViewById(R.id.card_view_holder);
    }


}
