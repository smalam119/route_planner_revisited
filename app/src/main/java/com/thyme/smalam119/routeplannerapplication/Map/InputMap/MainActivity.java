package com.thyme.smalam119.routeplannerapplication.Map.InputMap;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.CustomView.LocationInfoCard;
import com.thyme.smalam119.routeplannerapplication.LocationList.LocationListActivity;
import com.thyme.smalam119.routeplannerapplication.Map.NotificationMap.NotificationMapActivity;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.Profile.ProfileActivity;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.LocationDetailSharedPrefUtils;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapInteractionCallBack {

    private RPAOnInputMapReadyCallback mOnMapReadyCallback;
    private FloatingActionButton mProfileActionButton;
    private FloatingActionButton mNotificationActionButton;
    private LocationInfoCard mLocationInfoCard;
    private FloatingActionMenu mFloatingActionMenu;
    private TextView mNotificationCountTV;
    private ImageView mNotificationMarkerImage;
    private Button mNotificationButton;
    private int notificationCount = 0;
    private ArrayList<LocationDetail> locationDetails;
    private LocationDetail mGlobalLocationDetail;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOnMapReadyCallback = new RPAOnInputMapReadyCallback(this);
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(this);
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
        if(mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref() == null) {
            locationDetails = new ArrayList<>();
        } else {
            locationDetails = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.location_notification_label);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);

        mFloatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_menu);

        mProfileActionButton = (FloatingActionButton) findViewById(R.id.profile_action_button);
        mProfileActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        mNotificationActionButton = (FloatingActionButton) findViewById(R.id.notification_action_button);
        mNotificationActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotificationMapActivity.class));
            }
        });

        mLocationInfoCard = (LocationInfoCard) findViewById(R.id.location_info);
        mLocationInfoCard.setVisibility(View.GONE);
        mNotificationCountTV = (TextView) findViewById(R.id.notification_count);
        mNotificationMarkerImage = (ImageView) findViewById(R.id.marker_image);
        mNotificationButton = (Button) findViewById(R.id.notification_Button) ;
        mNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
                mLocationDetailSharedPrefUtils.setLocationDataToSharedPref(locationDetails);
                startActivity(intent);
                finish();
            }
        });

        mLocationInfoCard.getSelectButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNotificationMarkerImage.setImageResource(R.drawable.marker);
                LocationDetail locationDetail = mLocationInfoCard.getLocationData();
                locationDetails.add(locationDetail);
                mLocationDetailSharedPrefUtils.setLocationDataToSharedPref(locationDetails);
                notificationCount = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref().size();
                mNotificationCountTV.setText(notificationCount + "");
            }
        });

        if(mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref() == null) {
            mNotificationCountTV.setText(0 + "");
        } else {
            mNotificationMarkerImage.setImageResource(R.drawable.marker);
            notificationCount = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref().size();
            mNotificationCountTV.setText(notificationCount + "");
        }

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
        mLocationInfoCard.setLat(locationDetail.getLat());
        mLocationInfoCard.setLng(locationDetail.getLng());
        mLocationInfoCard.setIdentifierColor(locationDetail.getIdentifierColor());
    }

    @Override
    public void onMapLongClick(LocationDetail locationDetail) {
        bindLocationDataToView(locationDetail);
        showLocationInfoCard();
        mGlobalLocationDetail = locationDetail;
        Log.d("lld",mGlobalLocationDetail.getAddressLine());

    }

    @Override
    public void onMapClick() {
       hideLocationInfoCard();
    }
}
