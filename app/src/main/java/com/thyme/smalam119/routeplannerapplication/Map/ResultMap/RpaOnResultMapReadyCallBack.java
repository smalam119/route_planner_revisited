package com.thyme.smalam119.routeplannerapplication.Map.ResultMap;

import android.app.Activity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.thyme.smalam119.routeplannerapplication.Utils.Cons;
import com.thyme.smalam119.routeplannerapplication.Utils.LocationDetailSharedPrefUtils;

/**
 * Created by sayedalam on 12/17/17.
 */

public class RpaOnResultMapReadyCallBack implements OnMapReadyCallback {

    private Activity mActivity;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;

    public RpaOnResultMapReadyCallBack(Activity activity) {
        this.mActivity = activity;
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(activity);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        prepareMap(googleMap);
    }

    private void prepareMap(GoogleMap googleMap) {
        googleMap.setMinZoomPreference(13.0f);
        googleMap.setMaxZoomPreference(16.0f);
        googleMap.setLatLngBoundsForCameraTarget(Cons.DHAKA_BOUND);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Cons.DHAKA_LATLNG));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Cons.DHAKA_LATLNG, 14.0f));
    }
}
