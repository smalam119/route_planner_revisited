package com.thyme.smalam119.routeplannerapplication.Map.ResultMap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.LocationList.OptimizationType;
import com.thyme.smalam119.routeplannerapplication.R;

public class ResultMapActivity extends AppCompatActivity {
    private RpaOnResultMapReadyCallBack mRpaOnResultMapReadyCallBack;
    SupportMapFragment mMapFragment;
    private Button mOptimizeButton;
    public Button mNextButton;
    private RadioGroup mRadioGroup;
    private RadioButton mByDistanceRadioButton;
    private RadioButton mByDurationRadioButton;
    private boolean isTracking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_map);
        prepareUtils();
        prepareView();
    }

    private void prepareUtils() {
        mRpaOnResultMapReadyCallBack = new RpaOnResultMapReadyCallBack(this);
    }

    private void prepareView() {
        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_result);
        mMapFragment.getMapAsync(mRpaOnResultMapReadyCallBack);

        mOptimizeButton = (Button) findViewById(R.id.opt_button);
        mOptimizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = mRadioGroup.getCheckedRadioButtonId();
                if(selectedId == mByDistanceRadioButton.getId()) {
                    mRpaOnResultMapReadyCallBack.drawRoute(OptimizationType.BY_DISTANCE);
                } else if(selectedId == mByDurationRadioButton.getId()) {
                    mRpaOnResultMapReadyCallBack.drawRoute(OptimizationType.BY_DURATION);
                }
            }
        });

        mRadioGroup = (RadioGroup) findViewById(R.id.type_of_opt_radio_group);
        mByDistanceRadioButton = (RadioButton) findViewById(R.id.by_distance);
        mByDistanceRadioButton.setChecked(true);
        mByDurationRadioButton = (RadioButton) findViewById(R.id.by_duration);
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setVisibility(View.GONE);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTracking) {
                    mRpaOnResultMapReadyCallBack.rpaLocationListener.stopTracking();
                    mNextButton.setText("Start Tracking");
                    mNextButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    isTracking = false;
                } else {
                    mRpaOnResultMapReadyCallBack.rpaLocationListener.startTracking();
                    mNextButton.setText("Tracking......");
                    mNextButton.setBackgroundColor(getResources().getColor(R.color.green));
                    isTracking = true;
                }
            }
        });
    }

}
