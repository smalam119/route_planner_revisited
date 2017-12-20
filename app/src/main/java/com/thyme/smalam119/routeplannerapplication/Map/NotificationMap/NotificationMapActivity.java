package com.thyme.smalam119.routeplannerapplication.Map.NotificationMap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.R;

public class NotificationMapActivity extends AppCompatActivity {

    private RpaOnNotificationMapReadyCallBack mRpaOnNotificationMapReadyCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_map);
        prepareView();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(mRpaOnNotificationMapReadyCallBack);
    }

    private void prepareView() {
        mRpaOnNotificationMapReadyCallBack = new RpaOnNotificationMapReadyCallBack(this);
    }

}
