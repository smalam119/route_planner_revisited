package com.thyme.smalam119.routeplannerapplication.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;

/**
 * Created by smalam119 on 12/2/17.
 */

public class LocationInfoCard extends LinearLayout {

    private String mLocationTitle;
    private String mAddressLine;
    private String mLat;
    private String mLng;
    private String mDistance;
    private String mOpenTime;

    private View mRootView;
    private TextView mLocationTitleTV;
    private TextView mAddressLineTV;
    private TextView mLatLngTV;
    private TextView mDistanceTV;
    private TextView mOpenTimeTV;
    private Button mSelectButton;

    public LocationInfoCard(Context context) {
        super(context);
        prepareView(context);
    }

    public LocationInfoCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        prepareView(context);
    }

    public String getmLocationTitle() {
        return mLocationTitle;
    }

    public void setLocationTitle(String mLocationTitle) {
        this.mLocationTitle = mLocationTitle;
        mLocationTitleTV.setText(mLocationTitle);
    }

    public String getAddressLine() {
        return mAddressLine;
    }

    public void setAddressLine(String mAddressLine) {
        this.mAddressLine = mAddressLine;
        mAddressLineTV.setText(mAddressLine);
    }

    public String getLng() {
        return mLng;
    }

    public void setLng(String mLng) {
        this.mLng = mLng;

    }

    public void setLat(String lat) {
        this.mLat = lat;
    }

    public String getLat() {
        return mLat;
    }

    public void setLatlng(String mLatlng) {
        mLatLngTV.setText(mLatlng);
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String mDistance) {
        this.mDistance = mDistance;
        mDistanceTV.setText(mDistance);
    }

    public String getOpenTime() {
        return mOpenTime;
    }

    public void setOpenTime(String mOpenTime) {
        this.mOpenTime = mOpenTime;
        mOpenTimeTV.setText(mOpenTime);
    }

    public Button getSelectButton() {
        return mSelectButton;
    }

    public LocationDetail getLocationData() {
        LocationDetail locationDetail = new LocationDetail();
        locationDetail.setLocationTitle(getmLocationTitle());
        locationDetail.setAddressLine(getAddressLine());
        locationDetail.setLat(getLat());
        locationDetail.setLng(getLng());
        locationDetail.setDistance("");
        return locationDetail;
    }

    private void prepareView(Context context) {

        mRootView = inflate(context, R.layout.location_info_card_view, this);
        mLocationTitleTV = (TextView) mRootView.findViewById(R.id.location_title);
        mAddressLineTV = (TextView) mRootView.findViewById(R.id.address_line);
        mLatLngTV = (TextView) mRootView.findViewById(R.id.lat_lng);
        mDistanceTV = (TextView) mRootView.findViewById(R.id.distance);
        mOpenTimeTV = (TextView) mRootView.findViewById(R.id.open_time);
        mSelectButton = (Button) mRootView.findViewById(R.id.select_button);
        mSelectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
