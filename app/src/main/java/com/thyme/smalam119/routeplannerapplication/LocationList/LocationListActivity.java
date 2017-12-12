package com.thyme.smalam119.routeplannerapplication.LocationList;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;

import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.Cons;

import java.util.ArrayList;
import java.util.List;

public class LocationListActivity extends AppCompatActivity {

    private RecyclerView mLocationRecyclerView;
    private LocationListAdapter locationListAdapter;
    private List<LocationDetail> locationDetails;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);
        intent = getIntent();
        locationDetails = (ArrayList<LocationDetail>) intent.getSerializableExtra(Cons.KEY_EXTRA_LOCATION_ARRAY_LIST);
        prepareView();
    }

    private void prepareView() {
        mLocationRecyclerView = findViewById(R.id.location_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mLocationRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        locationListAdapter = new LocationListAdapter(this,locationDetails);
        mLocationRecyclerView.setLayoutManager(llm);
        mLocationRecyclerView.setAdapter(locationListAdapter);
    }

}
