package com.thyme.smalam119.routeplannerapplication.Map.ResultMap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.LocationList.OptimizationType;
import com.thyme.smalam119.routeplannerapplication.R;

public class ResultMapActivity extends AppCompatActivity {

    private RpaOnResultMapReadyCallBack mRpaOnResultMapReadyCallBack;
    private Button mTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRpaOnResultMapReadyCallBack = new RpaOnResultMapReadyCallBack(this);
        setContentView(R.layout.activity_result_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_result);
        mapFragment.getMapAsync(mRpaOnResultMapReadyCallBack);
        mTestButton = (Button) findViewById(R.id.test_button);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRpaOnResultMapReadyCallBack.optimizeRoute(OptimizationType.BY_DISTANCE);
            }
        });
    }

}
