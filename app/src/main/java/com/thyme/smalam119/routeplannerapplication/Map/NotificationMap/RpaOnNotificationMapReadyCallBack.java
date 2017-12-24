package com.thyme.smalam119.routeplannerapplication.Map.NotificationMap;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.thyme.smalam119.routeplannerapplication.Model.LocationAlert;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.Cons;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseAuthUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseDBUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.OnFireBaseDBChangeListener;
import com.thyme.smalam119.routeplannerapplication.Utils.HandyFunctions;
import java.util.ArrayList;

/**
 * Created by smalam119 on 12/20/17.
 */

public class RpaOnNotificationMapReadyCallBack implements OnMapReadyCallback, OnFireBaseDBChangeListener {
    private Activity mActivity;
    private Vibrator mVibrate;
    private FireBaseDBUtils mFireBaseDBUtils;
    private FireBaseAuthUtils mFireBaseAuthUtils;
    private ArrayList<LocationAlert> locationAlerts;
    private GoogleMap mGoogleMap;

    public RpaOnNotificationMapReadyCallBack(Activity activity) {
        this.mActivity = activity;
        mVibrate = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        mFireBaseAuthUtils = new FireBaseAuthUtils(mActivity);
        mFireBaseDBUtils = new FireBaseDBUtils("rpa-data");
        mFireBaseDBUtils.onFirebaseDBChangeListener = this;
        locationAlerts = new ArrayList<>();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                showInputDialog(latLng);
            }
        });
        prepareMap();
        mFireBaseDBUtils.readData("rpa-alerts");
    }

    private void showInputDialog(final LatLng latLng) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(mActivity);
        View mView = layoutInflaterAndroid.inflate(R.layout.input_dialog, null);
        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mActivity);
        alertDialogBuilderUserInput.setView(mView);
        final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();
        final EditText notificationTitle = (EditText) mView.findViewById(R.id.notification_title);
        final EditText notificationMessage = (EditText) mView.findViewById(R.id.notification_message);
        Button postButton = (Button) mView.findViewById(R.id.notification_post_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = notificationTitle.getText().toString();
                String message = notificationMessage.getText().toString();
                LocationAlert locationAlert = new LocationAlert(latLng.latitude,latLng.longitude,title,message);
                mFireBaseDBUtils.writeData("rpa-alerts",mFireBaseAuthUtils.getCurrentUser().getUid() + "_" + HandyFunctions.generateRandomString(),locationAlert);
                alertDialogAndroid.dismiss();
            }
        });

    }

    private void prepareMap() {
        mGoogleMap.setMinZoomPreference(13.0f);
        mGoogleMap.setMaxZoomPreference(16.0f);
        mGoogleMap.setLatLngBoundsForCameraTarget(Cons.DHAKA_BOUND);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(Cons.DHAKA_LATLNG));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Cons.DHAKA_LATLNG, 14.0f));
    }

    @Override
    public void onDataChanged(DataSnapshot dataSnapshot) {

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            LocationAlert locationAlert = snapshot.getValue(LocationAlert.class);
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(locationAlert.getmLat(), locationAlert.getmLng()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.road_block))
                    .title(locationAlert.getMessage()));
        }

    }

    @Override
    public void onCancel(DatabaseError error) {

    }
}
