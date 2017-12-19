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
import com.thyme.smalam119.routeplannerapplication.Utils.TSPEngine.TSPEngine;
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
    private ApiInterface apiService;
    private TSPEngine mTspEng;
    private int[][] mInputMatrixForTSP;
    private int numberOfLocations;
    private ArrayList<String> mDistanceList;
    private ArrayList<String> mDurationList;
    private GoogleMap mGoogleMap;
    public ArrayList<LocationDetail> optimizedLocationList;
    public int totalDistance = 0;
    public int totalDuration = 0;


    public RpaOnResultMapReadyCallBack(ResultMapActivity activity) {
        this.mActivity = activity;
        apiService = RetroFitClient.getClient().create(ApiInterface.class);
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(activity);
        mLocationDetails = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref();
        optimizedLocationList = new ArrayList<>();
        mTspEng = new TSPEngine();
        numberOfLocations = mLocationDetails.size();
        mInputMatrixForTSP = new int[numberOfLocations][numberOfLocations];
        mDistanceList = new ArrayList<>();
        mDurationList = new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        prepareMap();
        prepareDistanceDurationList();
    }

    private void prepareMap() {
        mGoogleMap.setMinZoomPreference(13.0f);
        mGoogleMap.setMaxZoomPreference(16.0f);
        mGoogleMap.setLatLngBoundsForCameraTarget(Cons.DHAKA_BOUND);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Cons.DHAKA_LATLNG));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Cons.DHAKA_LATLNG, 14.0f));
    }

    public void getOptimizeRoute(OptimizationType optimizationType) {
                int row = -1;

        switch (optimizationType) {
            case BY_DISTANCE:
                for (int i = 0; i < mDistanceList.size(); i++) {
                    int column = i % numberOfLocations;
                    if (column == 0)
                        row++;
                    mInputMatrixForTSP[row][column] = HandyFunctions.convertDistanceToMeter(mDistanceList
                            .get(i));
                    totalDistance = totalDistance + HandyFunctions.convertDistanceToMeter(mDistanceList
                            .get(i));
                    totalDuration = totalDuration + HandyFunctions.convertHourToMinute(mDurationList.get(i));

                    Log.d("total distance", totalDistance + "");
                }
                    break;
            case BY_DURATION:
                for (int i = 0; i < mDurationList.size(); i++) {
                    int column = i % numberOfLocations;
                    if (column == 0)
                        row++;
                    mInputMatrixForTSP[row][column] = HandyFunctions.convertHourToMinute(mDurationList.get(i));
                    totalDistance = totalDistance + HandyFunctions.convertDistanceToMeter(mDistanceList
                            .get(i));
                    totalDuration = totalDuration + HandyFunctions.convertHourToMinute(mDurationList.get(i));

                    Log.d("total distance", totalDistance + "");
                }

        }

        final ArrayList<Integer> pointOrder = mTspEng.computeTSP(mInputMatrixForTSP,
                numberOfLocations);

        for(int i = 0; i < pointOrder.size() - 1; i++){
            Log.d("point_order", pointOrder.get(i) + "");
            optimizedLocationList.add(mLocationDetails.get(pointOrder.get(i)));
        }

        for(int i = 0; i < optimizedLocationList.size() - 1; i++) {
            drawRoute(optimizedLocationList.get(i),optimizedLocationList.get(i+1),i + 1);
        }

    }

    private void getDistanceAndDuration(LatLng origin, LatLng dest) {
        final ProgressDialog mProgressDialog = ProgressDialog.show(mActivity,"Please Wait",
                "collecting data...");
        Call<Example> call = apiService.getDistanceDuration("metric", origin.latitude + "," + origin.longitude,dest.latitude + "," + dest.longitude, "driving");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    Log.d("onResponse", "collecting data...");
                    mProgressDialog.hide();
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        mDistanceList.add(distance);
                        mDurationList.add(time);
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "failed collecting data");
                    mProgressDialog.hide();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("onFailure", "failed collecting data");
                mProgressDialog.hide();
            }
        });
    }

    private void prepareDistanceDurationList() {
        for (int i = 0; i < numberOfLocations; i++) {
            LatLng origin = mLocationDetails.get(i).getLatLng();

            for (int j = 0; j < numberOfLocations; j++) {
                LatLng dest = mLocationDetails.get(j).getLatLng();
                getDistanceAndDuration(origin,dest);
            }
        }
    }

    private void drawRoute(final LocationDetail origin, final LocationDetail dest, final int locationIndex) {
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
