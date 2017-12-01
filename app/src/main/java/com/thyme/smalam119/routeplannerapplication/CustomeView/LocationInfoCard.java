package com.thyme.smalam119.routeplannerapplication.CustomeView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thyme.smalam119.routeplannerapplication.R;

/**
 * Created by smalam119 on 12/2/17.
 */

public class LocationInfoCard extends CardView {

    View rootView;
    TextView locationTitleTV;
    TextView addressLineTV;
    TextView latLngTV;
    TextView distanceTV;
    TextView openTimeTV;
    Button selectButton;

    public LocationInfoCard(Context context) {
        super(context);
        prepareView(context);
    }

    public LocationInfoCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        prepareView(context);
    }

    private void prepareView(Context context) {

        rootView = inflate(context, R.layout.location_info_card_view, this);
        locationTitleTV = (TextView) rootView.findViewById(R.id.location_title);
        addressLineTV = (TextView) rootView.findViewById(R.id.address_line);
        latLngTV = (TextView) rootView.findViewById(R.id.lat_lng);
        distanceTV = (TextView) rootView.findViewById(R.id.distance);
        openTimeTV = (TextView) rootView.findViewById(R.id.open_time);
        selectButton = (Button) rootView.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
