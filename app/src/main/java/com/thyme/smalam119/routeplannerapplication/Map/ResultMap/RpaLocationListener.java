package com.thyme.smalam119.routeplannerapplication.Map.ResultMap;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by smalam119 on 12/20/17.
 */

public class RpaLocationListener implements LocationListener {
    private LocationManager mLocationManager;
    private Context mContext;
    private String mProvider;

    public RpaLocationListener(Context mContext) {
        this.mContext = mContext;
        prepareView();
    }

    private void prepareView() {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(mProvider);
        if (location != null) {
            onLocationChanged(location);
        } else {

        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
