package com.thyme.smalam119.routeplannerapplication.Map.ResultMap;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.thyme.smalam119.routeplannerapplication.LocationList.OptimizationType;
import com.thyme.smalam119.routeplannerapplication.Model.Direction.Example;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.NetworkCalls.ApiInterface;
import com.thyme.smalam119.routeplannerapplication.NetworkCalls.RetroFitClient;
import com.thyme.smalam119.routeplannerapplication.Utils.Cons;
import com.thyme.smalam119.routeplannerapplication.Utils.HandyFunctions;
import com.thyme.smalam119.routeplannerapplication.Utils.JsonParserForDirection;
import com.thyme.smalam119.routeplannerapplication.Utils.LocationDetailSharedPrefUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sayedalam on 12/17/17.
 */

public class RpaOnResultMapReadyCallBack implements OnMapReadyCallback {
    private ResultMapActivity mActivity;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;
    private ArrayList<LocationDetail> mLocationDetails;
    private GoogleMap mGoogleMap;
    private int numberOfLocations;
    private ArrayList<LocationDetail> optimizedLocationListDistance;
    private ArrayList<LocationDetail> optimizedLocationListDuration;
    private ApiInterface apiService;


    public RpaOnResultMapReadyCallBack(ResultMapActivity activity) {
        this.mActivity = activity;
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(activity);
        mLocationDetails = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref();
        numberOfLocations = mLocationDetails.size();
        optimizedLocationListDistance = (ArrayList<LocationDetail> )mActivity.getIntent().getSerializableExtra("optimizedLocationListDistance");
        optimizedLocationListDuration = (ArrayList<LocationDetail>) mActivity.getIntent().getSerializableExtra("optimizedLocationListDuration");
        apiService = RetroFitClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        prepareMap();
        drawRoute(OptimizationType.BY_DISTANCE);
    }

    private void prepareMap() {
        mGoogleMap.setMinZoomPreference(13.0f);
        mGoogleMap.setMaxZoomPreference(16.0f);
        mGoogleMap.setLatLngBoundsForCameraTarget(Cons.DHAKA_BOUND);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Cons.DHAKA_LATLNG));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Cons.DHAKA_LATLNG, 14.0f));
    }

    public void drawRoute(OptimizationType optimizationType) {

        switch (optimizationType) {

            case BY_DISTANCE:
                mGoogleMap.clear();
                for(int i = 0; i < optimizedLocationListDistance.size() - 1; i++) {
                    drawRoute(optimizedLocationListDistance.get(i),optimizedLocationListDistance.get(i+1),i + 1);
                }
                break;

            case BY_DURATION:
                mGoogleMap.clear();
                for(int i = 0; i < optimizedLocationListDuration.size() - 1; i++) {
                    drawRoute(optimizedLocationListDuration.get(i),optimizedLocationListDuration.get(i+1),i + 1);
                }
                break;
        }


    }

    public void drawRoute(final LocationDetail origin, final LocationDetail dest, final int locationIndex) {
        final ProgressDialog mProgressDialog = ProgressDialog.show(mActivity,"Please Wait",
                "drawing route....");
        Call<Example> call = apiService.getDistanceDuration("metric", origin.getLat() + "," + origin.getLng(),dest.getLat() + "," + dest.getLng(), "driving");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    Log.d("onResponse", "drawing route....");
                    mProgressDialog.hide();
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = JsonParserForDirection.decodePoly(encodedString);
                        mGoogleMap.addMarker(new MarkerOptions().position(origin.getLatLng())
                                .title("Marker")
                                .icon(BitmapDescriptorFactory.fromBitmap(HandyFunctions.getMarkerIcon(mActivity,locationIndex + "",origin.getIdentifierColor()))));

                        mGoogleMap.addMarker(new MarkerOptions().position(dest.getLatLng())
                                .title("Marker")
                                .icon(BitmapDescriptorFactory.fromBitmap(HandyFunctions.getMarkerIcon(mActivity,locationIndex + 1 + "",dest.getIdentifierColor()))));

                        mGoogleMap.addPolyline(new PolylineOptions()
                                .addAll(list)
                                .width(20)
                                .color(origin.getIdentifierColor())
                                .geodesic(true)
                        );
                    }
                    mActivity.mNextButton.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.d("onResponse", "drawing failed");
                    mProgressDialog.hide();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                mProgressDialog.hide();
                Log.d("onFailure", "drawing failed");
            }
        });
    }

}
