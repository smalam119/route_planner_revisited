package com.thyme.smalam119.routeplannerapplication.Model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by smalam119 on 12/2/17.
 */

public class LocationDetail implements Serializable {
    private String locationTitle;
    private String addressLine;
    private String lat;
    private String lng;
    private String distance;
    private int identifierColor;

    public String getLocationTitle() {
        return locationTitle;
    }

    public void setLocationTitle(String locationTitle) {
        this.locationTitle = locationTitle;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getIdentifierColor() {
        return identifierColor;
    }

    public void setIdentifierColor(int identifierColor) {
        this.identifierColor = identifierColor;
    }

    public LatLng getLatLng() {
        return new LatLng(Double.parseDouble(getLat()),Double.parseDouble(getLng()));
    }

    public LocationDetail() {

    }

    public LocationDetail(String locationTitle, String addressLine, String lat, String lng, String distance, int identifierColor) {
        this.locationTitle = locationTitle;
        this.addressLine = addressLine;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.identifierColor = identifierColor;
    }
}
