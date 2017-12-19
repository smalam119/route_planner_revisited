package com.thyme.smalam119.routeplannerapplication.ResultLocationList;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thyme.smalam119.routeplannerapplication.R;

/**
 * Created by smalam119 on 12/19/17.
 */

public class ResultLocationListViewHolder extends RecyclerView.ViewHolder {
    public TextView sequenceNumber, locationName;
    public CardView cardView;

    public ResultLocationListViewHolder(View itemView) {
        super(itemView);
        sequenceNumber = (TextView) itemView.findViewById(R.id.sequence_number_text_view);
        locationName = (TextView) itemView.findViewById(R.id.location_name_text_view);
        cardView = (CardView) itemView.findViewById(R.id.card_view_holder);
    }
}
