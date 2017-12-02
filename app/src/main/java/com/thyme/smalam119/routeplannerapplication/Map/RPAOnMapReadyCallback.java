package com.thyme.smalam119.routeplannerapplication.Map;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by smalam119 on 11/28/17.
 */

public class RPAOnMapReadyCallback implements OnMapReadyCallback {

    public OnMapInteractionCallBack onMapInteractionCallBack;
    private Activity mActivity;
    private LocationDetail locationDetail;

    public RPAOnMapReadyCallback(Activity activity) {
        this.mActivity = activity;
        locationDetail = new LocationDetail();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng dhaka = new LatLng(23.8103, 90.4125);
        googleMap.addMarker(new MarkerOptions().position(dhaka)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 14.0f));
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                getLocationDetail(latLng.latitude,latLng.longitude,mActivity);
            }
        });

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                onMapInteractionCallBack.onMapClick();
            }
        });
    }

    private void getLocationDetail(final double latitude, final double longitude, final Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                locationDetail.setAddressLine(address.getAddressLine(0));
                locationDetail.setLocationTitle(address.getSubLocality());
                locationDetail.setLat(String.valueOf(address.getLatitude()));
                locationDetail.setLng(String.valueOf(address.getLongitude()));
                locationDetail.setDistance("1.5 MILES");
                onMapInteractionCallBack.onMapLongClick(locationDetail);
            }

        } catch(IOException e) {

        }
    }
}
