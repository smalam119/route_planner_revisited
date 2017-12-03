package com.thyme.smalam119.routeplannerapplication.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.CustomeView.LocationInfoCard;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.R;

public class MainActivity extends AppCompatActivity implements OnMapInteractionCallBack {

    private RPAOnMapReadyCallback mOnMapReadyCallback;
    private FloatingActionButton mProfileActionButton;
    private FloatingActionButton mNotificationActionButton;
    private LocationInfoCard mLocationInfoCard;
    private FloatingActionMenu mFloatingActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOnMapReadyCallback = new RPAOnMapReadyCallback(this);
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

        mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_menu);

        mProfileActionButton = (FloatingActionButton) findViewById(R.id.profile_action_button);
        mProfileActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mNotificationActionButton = (FloatingActionButton) findViewById(R.id.notification_action_button);
        mNotificationActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mLocationInfoCard = (LocationInfoCard) findViewById(R.id.location_info);
        mLocationInfoCard.setVisibility(View.GONE);

        mOnMapReadyCallback.onMapInteractionCallBack = this;

    }

    private void showLocationInfoCard() {
        mLocationInfoCard.setVisibility(View.VISIBLE);
        mFloatingActionMenu.setVisibility(View.GONE);
    }

    private void hideLocationInfoCard() {
        mLocationInfoCard.setVisibility(View.GONE);
        mFloatingActionMenu.setVisibility(View.VISIBLE);
    }

    private void bindLocationDataToView(LocationDetail locationDetail) {
        mLocationInfoCard.setLocationTitle(locationDetail.getLocationTitle());
        mLocationInfoCard.setAddressLine(locationDetail.getAddressLine());
        mLocationInfoCard.setLatlng(locationDetail.getLat() + " , " + locationDetail.getLng());
        mLocationInfoCard.setDistance(locationDetail.getDistance() + " " + "AWAY");
        mLocationInfoCard.setOpenTime("11 AM to 10:20 PM");
    }

    @Override
    public void onMapLongClick(LocationDetail locationDetail) {
        bindLocationDataToView(locationDetail);
        showLocationInfoCard();
    }

    @Override
    public void onMapClick() {
       hideLocationInfoCard();
    }
}
