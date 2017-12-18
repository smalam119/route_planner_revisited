package com.thyme.smalam119.routeplannerapplication.Map.InputMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;
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

    public RPAOnInputMapReadyCallback(Activity activity) {
        this.mActivity = activity;
        locationDetail = new LocationDetail();
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(activity);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.d("map","map is ready");
        setupMap(googleMap);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                setupMap(googleMap);
                getLocationDetail(latLng.latitude,latLng.longitude,mActivity);
                String firstCharacterOfLocationName = HandyFunctions.getFirstCharacter(locationDetail.getAddressLine());
                googleMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Marker in Dhaka")
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerIcon(firstCharacterOfLocationName,locationDetail.getIdentifierColor()))));
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

    private Bitmap getMarkerIcon(String alphabet, int color) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(100, 100, conf);

        Paint paintCircle = new Paint();
        paintCircle.setColor(color);
        paintCircle.setStyle(Paint.Style.FILL);

        Paint paintText = new Paint();
        paintText.setColor(mActivity.getResources().getColor(R.color.black));
        paintText.setStyle(Paint.Style.FILL_AND_STROKE);
        paintText.setTextSize(30);

        Canvas canvas = new Canvas(bmp);
        canvas.drawCircle(50,50,25,paintCircle);
        canvas.drawText(alphabet,40,60,paintText);

        return bmp;
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
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerIcon(firstCharacterOfLocationName,locationDetail.getIdentifierColor()))));
            }
        }
    }
}
