package com.thyme.smalam119.routeplannerapplication.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.CustomeView.LocationInfoCard;
import com.thyme.smalam119.routeplannerapplication.R;

public class MainActivity extends AppCompatActivity {

    private RPAOnMapReadyCallback mOnMapReadyCallback;
    private FloatingActionButton mProfileActionButton;
    private FloatingActionButton mNotificationActionButton;
    private LocationInfoCard locationInfoCard;
    private RelativeLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOnMapReadyCallback = new RPAOnMapReadyCallback();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(mOnMapReadyCallback);
        prepareView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void prepareView() {
        mProfileActionButton = (FloatingActionButton) findViewById(R.id.profile_action_button);
        mProfileActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick", "Profile Pressed");
            }
        });

        mNotificationActionButton = (FloatingActionButton) findViewById(R.id.notification_action_button);
        mNotificationActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("onClick", "Notification Pressed");
            }
        });

        locationInfoCard = (LocationInfoCard) findViewById(R.id.location_info);
        locationInfoCard.setLocationTitle("Houston, New York");
        locationInfoCard.setAddressLine("address xyz");
        locationInfoCard.setLatlng("16.10000, 90.1122");
        locationInfoCard.setmOpenTime("11 AM to 10:20 PM");

    }
}
