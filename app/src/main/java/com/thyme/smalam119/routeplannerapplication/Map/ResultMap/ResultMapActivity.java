package com.thyme.smalam119.routeplannerapplication.Map.ResultMap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.LocationList.OptimizationType;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.ResultLocationList.ResultLocationListActivity;
import com.thyme.smalam119.routeplannerapplication.Utils.HandyFunctions;

public class ResultMapActivity extends AppCompatActivity {

    private RpaOnResultMapReadyCallBack mRpaOnResultMapReadyCallBack;
    private Button mTestButton;
    public Button mNextButton;
    private RadioGroup mRadioGroup;
    private RadioButton mByDistanceRadioButton;
    private RadioButton mByDurationRadioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRpaOnResultMapReadyCallBack = new RpaOnResultMapReadyCallBack(this);
        setContentView(R.layout.activity_result_map);
        prepareView();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_result);
        mapFragment.getMapAsync(mRpaOnResultMapReadyCallBack);
    }

    private void prepareView() {
        mTestButton = (Button) findViewById(R.id.opt_dis_button);
        mTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                if(selectedId == mByDistanceRadioButton.getId()) {
                    mRpaOnResultMapReadyCallBack.getOptimizeRoute(OptimizationType.BY_DISTANCE);
                } else if(selectedId == mByDurationRadioButton.getId()) {
                    mRpaOnResultMapReadyCallBack.getOptimizeRoute(OptimizationType.BY_DURATION);
                }
            }
        });
        mRadioGroup = (RadioGroup) findViewById(R.id.type_of_opt_radio_group);
        mByDistanceRadioButton = (RadioButton) findViewById(R.id.by_distance);
        mByDurationRadioButton = (RadioButton) findViewById(R.id.by_duration);
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setVisibility(View.GONE);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoResultLocationListActivity();
            }
        });
    }

    private void gotoResultLocationListActivity() {
        Intent i = new Intent(this, ResultLocationListActivity.class);
        i.putExtra("resultLocationList", mRpaOnResultMapReadyCallBack.optimizedLocationList);
        i.putExtra("totalDistance", HandyFunctions.convertMeterToKiloMeter(mRpaOnResultMapReadyCallBack.totalDistance));
        i.putExtra("totalDuration", HandyFunctions.convertMinuteToHour(mRpaOnResultMapReadyCallBack.totalDuration));
        startActivity(i);
    }

}
