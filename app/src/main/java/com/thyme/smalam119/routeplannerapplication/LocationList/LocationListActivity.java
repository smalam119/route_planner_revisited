package com.thyme.smalam119.routeplannerapplication.LocationList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.thyme.smalam119.routeplannerapplication.Login.LoginActivity;
import com.thyme.smalam119.routeplannerapplication.Map.ResultMap.ResultMapActivity;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.LocationDetailSharedPrefUtils;
import java.util.ArrayList;

public class LocationListActivity extends AppCompatActivity {

    private RecyclerView mLocationRecyclerView;
    private LocationListAdapter locationListAdapter;
    private Button mOptimizeButton;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;
    private ArrayList<LocationDetail> mLocationDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        prepareView();
    }

    private void prepareView() {
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(getApplicationContext());
        mLocationDetails = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref();
        mLocationRecyclerView = findViewById(R.id.location_recycler_view);
        mOptimizeButton = findViewById(R.id.optimize_button);
        mOptimizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LocationListActivity.this, ResultMapActivity.class);
                startActivity(i);
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mLocationRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        locationListAdapter = new LocationListAdapter(this,mLocationDetails);
        mLocationRecyclerView.setLayoutManager(llm);
        mLocationRecyclerView.setAdapter(locationListAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
    }

}
