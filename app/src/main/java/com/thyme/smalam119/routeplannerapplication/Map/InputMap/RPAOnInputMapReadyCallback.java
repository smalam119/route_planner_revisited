package com.thyme.smalam119.routeplannerapplication.Map.InputMap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.Utils.Cons;
import com.thyme.smalam119.routeplannerapplication.Utils.HandyFunctions;
import com.thyme.smalam119.routeplannerapplication.Utils.LocationDetailSharedPrefUtils;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by smalam119 on 11/28/17.
 */

public class RPAOnInputMapReadyCallback implements OnMapReadyCallback {

    public OnMapInteractionCallBack onMapInteractionCallBack;
    private Activity mActivity;
    private LocationDetail locationDetail;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;
    private Vibrator mVibrate;
    private LocationManager mLocationManager;
    private String mProvider;
    private GoogleMap mGoogleMap;

    public RPAOnInputMapReadyCallback(Activity activity) {
        this.mActivity = activity;
        locationDetail = new LocationDetail();
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(activity);
        mVibrate = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void markCurrentLocation() {
        mLocationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        mProvider = mLocationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(mProvider);

        if(location != null) {
            getLocationDetail(location.getLatitude(),location.getLongitude(),mActivity);
            String firstCharacterOfLocationName = HandyFunctions.getFirstCharacter(locationDetail.getAddressLine());
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude()))
                    .title("Marker")
                    .icon(BitmapDescriptorFactory.fromBitmap(HandyFunctions.
                            getMarkerIcon(mActivity,firstCharacterOfLocationName,locationDetail.getIdentifierColor()))));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 14.0f));
        } else {

            getLocationDetail(23.7265631,90.3886909,mActivity);
            String firstCharacterOfLocationName = HandyFunctions.getFirstCharacter(locationDetail.getAddressLine());
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(23.7265631,90.3886909))
                    .title("Marker")
                    .icon(BitmapDescriptorFactory.fromBitmap(HandyFunctions.
                            getMarkerIcon(mActivity,firstCharacterOfLocationName,locationDetail.getIdentifierColor()))));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Cons.BUET_LATLNG));
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Cons.BUET_LATLNG, 14.0f));
        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d("map","map is ready");
        mGoogleMap = googleMap;
        setupMap(googleMap);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mVibrate.vibrate(100);
                setupMap(googleMap);
                getLocationDetail(latLng.latitude,latLng.longitude,mActivity);
                String firstCharacterOfLocationName = HandyFunctions.getFirstCharacter(locationDetail.getAddressLine());
                googleMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Marker in Dhaka")
                        .icon(BitmapDescriptorFactory.fromBitmap(HandyFunctions.getMarkerIcon(mActivity,firstCharacterOfLocationName,locationDetail.getIdentifierColor()))));
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
                LocationDetail locationDetail = prepareLocationDetailModel(address);
                onMapInteractionCallBack.onMapLongClick(locationDetail);
            }

        } catch(IOException e) {

        }
    }

    private LocationDetail prepareLocationDetailModel(Address address) {
        locationDetail.setAddressLine(address.getAddressLine(0));
        String subLocality = address.getSubLocality();
        if(subLocality == null) {
            locationDetail.setLocationTitle("Unknown");
        } else {
            locationDetail.setLocationTitle(address.getSubLocality());
        }
        locationDetail.setLat(String.valueOf(address.getLatitude()));
        locationDetail.setLng(String.valueOf(address.getLongitude()));
        locationDetail.setDistance("1.5 MILES");
        locationDetail.setIdentifierColor(HandyFunctions.getRandomColor());
        return locationDetail;
    }

    private void setupMap(GoogleMap googleMap) {
        googleMap.clear();
        googleMap.setMinZoomPreference(13.0f);
        googleMap.setMaxZoomPreference(16.0f);
        googleMap.setLatLngBoundsForCameraTarget(Cons.DHAKA_BOUND);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Cons.DHAKA_LATLNG));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Cons.DHAKA_LATLNG, 14.0f));

        if(mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref() == null) {

        } else {
            for(LocationDetail locationDetail : mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref()) {
                String firstCharacterOfLocationName = HandyFunctions.getFirstCharacter(locationDetail.getAddressLine());
                LatLng latLngSel = new LatLng(Double.valueOf(locationDetail.getLat()),Double.valueOf(locationDetail.getLng()));
                googleMap.addMarker(new MarkerOptions().position(latLngSel)
                        .title("Marker")
                        .icon(BitmapDescriptorFactory.fromBitmap(HandyFunctions.getMarkerIcon(mActivity,firstCharacterOfLocationName,locationDetail.getIdentifierColor()))));
            }
        }
    }
}
