package com.thyme.smalam119.routeplannerapplication.Model.User;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by smalam119 on 12/11/17.
 */

@IgnoreExtraProperties
public class SinglePath {

    private String locationTitle;
    private double lat;
    private double lng;
    private int sequenceNumber;

    public SinglePath() {
    }

    public SinglePath(String locationList, double lat, double lng, int sequenceNumber) {
        this.locationTitle = locationList;
        this.lat = lat;
        this.lng = lng;
        this.sequenceNumber = sequenceNumber;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
