package com.thyme.smalam119.routeplannerapplication.Map.InputMap;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.SupportMapFragment;
import com.thyme.smalam119.routeplannerapplication.CustomView.LocationInfoCard;
import com.thyme.smalam119.routeplannerapplication.LocationList.LocationListActivity;
import com.thyme.smalam119.routeplannerapplication.Login.LoginActivity;
import com.thyme.smalam119.routeplannerapplication.Map.NotificationMap.NotificationMapActivity;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.Profile.ProfileActivity;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.Alerts;
import com.thyme.smalam119.routeplannerapplication.Utils.LocationDetailSharedPrefUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager.LoginManager;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapInteractionCallBack {

    //view
    private FloatingActionButton mProfileActionButton;
    private FloatingActionButton mNotificationActionButton;
    private FloatingActionButton mGetCurrentLocationButton;
    private FloatingActionButton mLogoutActionButton;
    private LocationInfoCard mLocationInfoCard;
    private FloatingActionMenu mFloatingActionMenu;
    private TextView mNotificationCountTV;
    private Button mNumberOfLocationAddedButton;
    private SupportMapFragment mMapFragment;

    //interface
    private RPAOnInputMapReadyCallback mOnMapReadyCallback;

    //others
    private int notificationCount = 0;
    public ArrayList<LocationDetail> locationDetails;
    private LocationDetail mGlobalLocationDetail;
    private LocationDetailSharedPrefUtils mLocationDetailSharedPrefUtils;

    //managers
    private LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareUtils();
        prepareView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void prepareView() {
        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(mOnMapReadyCallback);

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

        mGetCurrentLocationButton = (FloatingActionButton) findViewById(R.id.current_location_action_button);
        mGetCurrentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnMapReadyCallback.markCurrentLocation();
            }
        });

        mLogoutActionButton = (FloatingActionButton) findViewById(R.id.log_out);
        mLogoutActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationDetailSharedPrefUtils.removeAll();
                mLoginManager.logout();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();

            }
        });

        mLocationInfoCard = (LocationInfoCard) findViewById(R.id.location_info);
        mLocationInfoCard.setVisibility(View.GONE);
        mNotificationCountTV = (TextView) findViewById(R.id.notification_count);
        mNumberOfLocationAddedButton = (Button) findViewById(R.id.marker_image);
        mNumberOfLocationAddedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationCount > 0) {
                    Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
                    mLocationDetailSharedPrefUtils.setLocationDataToSharedPref(locationDetails);
                    startActivity(intent);
                    finish();
                } else {
                    Alerts.showSimpleWarning(MainActivity.this,"warning","No location is added yet");
                }
            }
        });

        mLocationInfoCard.getSelectButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNumberOfLocationAddedButton.setBackgroundResource(R.drawable.marker);
                LocationDetail locationDetail = mLocationInfoCard.getLocationData();
                locationDetails.add(locationDetail);
                notificationCount = locationDetails.size();
                mNotificationCountTV.setText(notificationCount + "");
            }
        });

        if(locationDetails.size() == 0) {
            mNumberOfLocationAddedButton.setBackgroundResource(R.drawable.marker_white);
            mNotificationCountTV.setText(0 + "");
        } else {
            mNumberOfLocationAddedButton.setBackgroundResource(R.drawable.marker);
            notificationCount = locationDetails.size();
            mNotificationCountTV.setText(notificationCount + "");
        }

    }

    private void prepareUtils() {
        mOnMapReadyCallback = new RPAOnInputMapReadyCallback(this);
        mOnMapReadyCallback.onMapInteractionCallBack = this;
        mLocationDetailSharedPrefUtils = new LocationDetailSharedPrefUtils(this);
        mLoginManager = new LoginManager(this);

        if(mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref() == null) {
            locationDetails = new ArrayList<>();
        } else {
            locationDetails = mLocationDetailSharedPrefUtils.getLocationDataFromSharedPref();
        }
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
