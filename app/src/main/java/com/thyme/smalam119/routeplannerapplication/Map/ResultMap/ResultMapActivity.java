package com.thyme.smalam119.routeplannerapplication.Map.ResultMap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.R;

public class ResultMapActivity extends AppCompatActivity {

    private RpaOnResultMapReadyCallBack mRpaOnResultMapReadyCallBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_map);
        mRpaOnResultMapReadyCallBack = new RpaOnResultMapReadyCallBack(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_result);
        mapFragment.getMapAsync(mRpaOnResultMapReadyCallBack);
    }
}
