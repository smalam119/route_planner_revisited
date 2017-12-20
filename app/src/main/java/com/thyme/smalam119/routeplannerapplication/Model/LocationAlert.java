package com.thyme.smalam119.routeplannerapplication.Model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by smalam119 on 12/20/17.
 */

@IgnoreExtraProperties
public class LocationAlert {
    private double mLat, mLng;
    private String title, message;

    public LocationAlert() {

    }

    public LocationAlert(double mLat, double mLng, String title, String message) {
        this.mLat = mLat;
        this.mLng = mLng;
        this.title = title;
        this.message = message;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public double getmLng() {
        return mLng;
    }

    public void setmLng(double mLng) {
        this.mLng = mLng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
