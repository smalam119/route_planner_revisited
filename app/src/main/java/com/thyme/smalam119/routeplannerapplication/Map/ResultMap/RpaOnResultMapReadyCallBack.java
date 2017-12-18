package com.thyme.smalam119.routeplannerapplication.Map.ResultMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
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
    private Activity mActivity;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;
    private ArrayList<LocationDetail> mLocationDetails;
    private ApiInterface apiService;
    private TSPEngine mTspEng;
    private int[][] mInputMatrixForTSP;
    private int numberOfLocations;
    private ArrayList<String> mDistanceList;
    private ArrayList<String> mDurationList;
    private ArrayList<List<LatLng>> mdecodedPolyList;
    private GoogleMap mGoogleMap;


    public RpaOnResultMapReadyCallBack(Activity activity) {
        this.mActivity = activity;
        apiService = RetroFitClient.getClient().create(ApiInterface.class);
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(activity);
        mLocationDetails = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref();
        mTspEng = new TSPEngine();
        numberOfLocations = mLocationDetails.size();
        mInputMatrixForTSP = new int[numberOfLocations][numberOfLocations];
        mDistanceList = new ArrayList<>();
        mDurationList = new ArrayList<>();
        mdecodedPolyList = new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        prepareMap(googleMap);
        for (int i = 0; i < numberOfLocations; i++) {
            LatLng origin = mLocationDetails.get(i).getLatLng();

            for (int j = 0; j < numberOfLocations; j++) {
                LatLng dest = mLocationDetails.get(j).getLatLng();
                makeGetDirectionNetworkCall(googleMap,origin,dest);
            }
        }
    }

    private void prepareMap(GoogleMap googleMap) {
        googleMap.setMinZoomPreference(13.0f);
        googleMap.setMaxZoomPreference(16.0f);
        googleMap.setLatLngBoundsForCameraTarget(Cons.DHAKA_BOUND);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(Cons.DHAKA_LATLNG));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Cons.DHAKA_LATLNG, 14.0f));
    }

    public void optimizeRoute(OptimizationType optimizationType) {
                int row = -1;

        switch (optimizationType) {
            case BY_DISTANCE:
                for (int i = 0; i < mDistanceList.size(); i++) {
                    int column = i % numberOfLocations;
                    if (column == 0)
                        row++;
                    mInputMatrixForTSP[row][column] = HandyFunctions.convertDistanceToMeter(mDistanceList
                            .get(i));
                }
                    break;
            case BY_DURATION:
                for (int i = 0; i < mDurationList.size(); i++) {
                    int column = i % numberOfLocations;
                    if (column == 0)
                        row++;
                    mInputMatrixForTSP[row][column] = HandyFunctions.convertHourToMinute(mDurationList.get(i));
                }

        }

        final ArrayList<Integer> pointOrder = mTspEng.computeTSP(mInputMatrixForTSP,
                numberOfLocations);
        Log.d("point_order", pointOrder.size() + "");


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < pointOrder.size() - 2; i++) {
                    int index = pointOrder.get(i+1) * numberOfLocations
                            + pointOrder.get(i+1);
                    try{
                        mGoogleMap.addPolyline(new PolylineOptions()
                                .addAll(mdecodedPolyList.get(index))
                                .width(20)
                                .color(Color.RED)
                                .geodesic(true));
                    }catch(Exception ex){
                        Log.d("EX", "index: "+index+" Ex: "+ex.toString());
                        Log.d("decoded_poly_list", mdecodedPolyList.size() + "");
                    }
                }
            }
        });
    }

    private void makeGetDirectionNetworkCall(final GoogleMap googleMap, LatLng origin, LatLng dest) {
        Call<Example> call = apiService.getDistanceDuration("metric", origin.latitude + "," + origin.longitude,dest.latitude + "," + dest.longitude, "driving");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = JsonParserForDirection.decodePoly(encodedString);
//                        googleMap.addPolyline(new PolylineOptions()
//                                .addAll(list)
//                                .width(20)
//                                .color(Color.RED)
//                                .geodesic(true)
//                        );
                        mDistanceList.add(distance);
                        mDurationList.add(time);
                        mdecodedPolyList.add(list);
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
}
