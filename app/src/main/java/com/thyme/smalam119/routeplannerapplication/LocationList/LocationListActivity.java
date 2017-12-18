package com.thyme.smalam119.routeplannerapplication.LocationList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.thyme.smalam119.routeplannerapplication.LocationList.Optimization.OptimizationType;
import com.thyme.smalam119.routeplannerapplication.Login.LoginActivity;
import com.thyme.smalam119.routeplannerapplication.LocationList.Optimization.DownloadTask;
import com.thyme.smalam119.routeplannerapplication.Map.ResultMap.ResultMapActivity;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.DirectionUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.HandyFunctions;
import com.thyme.smalam119.routeplannerapplication.Utils.LocationDetailSharedPrefUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.TSPEngine.TSPEngine;
import java.util.ArrayList;

public class LocationListActivity extends AppCompatActivity {

    private RecyclerView mLocationRecyclerView;
    private LocationListAdapter locationListAdapter;
    private Button mOptimizeButton;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;
    public ArrayList<LocationDetail> locationDetails;
    public ArrayList<String> distanceList;
    public ArrayList<String> durationList;
    public ArrayList<PolylineOptions> lineOptionsList;
    int numberOfLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        prepareView();
    }

    private void prepareView() {
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(getApplicationContext());
        locationDetails = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref();
        numberOfLocations = locationDetails.size();
        distanceList = new ArrayList<String>();
        durationList = new ArrayList<String>();
        lineOptionsList = new ArrayList<PolylineOptions>();
        mLocationRecyclerView = findViewById(R.id.location_recycler_view);
        mOptimizeButton = findViewById(R.id.optimize_button);
        mOptimizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationListActivity.this, ResultMapActivity.class));
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mLocationRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        locationListAdapter = new LocationListAdapter(this);
        mLocationRecyclerView.setLayoutManager(llm);
        mLocationRecyclerView.setAdapter(locationListAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void drawRoute(OptimizationType optimizationType) {
        TSPEngine tspEng = new TSPEngine();
        int[][] inputMatrixForTSP;
        inputMatrixForTSP = new int[numberOfLocations][numberOfLocations];

        // Checks, whether start and end locations are captured
        if (numberOfLocations >= 2) {

            for (int i = 0; i < numberOfLocations; i++) {
                LatLng origin = locationDetails.get(i).getLatLng();

                for (int j = 0; j < numberOfLocations; j++) {

                    LatLng dest = locationDetails.get(i).getLatLng();
                    // Getting URL to the Google Directions API
                    String url = DirectionUtils.getDirectionsUrl(origin, dest);
                    DownloadTask downloadTask = new DownloadTask(this);
                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            }
        }

        int row = -1;

        switch (optimizationType) {
            case BY_DISTANCE:
                for (int i = 0; i < distanceList.size(); i++) {
                    int column = i % numberOfLocations;
                    if (column == 0)
                        row++;
                    inputMatrixForTSP[row][column] = HandyFunctions.convertDistanceToMeter(distanceList
                            .get(i));
                }
                    break;
            case BY_DURATION:
                for (int i = 0; i < durationList.size(); i++) {
                    int column = i % numberOfLocations;
                    if (column == 0)
                        row++;
                    inputMatrixForTSP[row][column] = HandyFunctions.convertHourToMinute(durationList.get(i));
                }

        }

        final ArrayList<Integer> pointOrder = tspEng.computeTSP(inputMatrixForTSP,
                numberOfLocations);

//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < pointOrder.size() - 2; i++) {
//                    int index = pointOrder.get(i+1) * numberOfLocations
//                            + pointOrder.get(i + 1);
//                    try{
//                        map.addPolyline(lineOptionsList.get(index));
//                    }catch(Exception ex){
//                        Log.d("EX", "index: "+index+" Ex: "+ex.toString());
//                    }
//                }
//            }
//        });

    }
}
