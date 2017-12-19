package com.thyme.smalam119.routeplannerapplication.Model.User;

import com.google.firebase.database.IgnoreExtraProperties;
import java.util.ArrayList;

/**
 * Created by smalam119 on 12/11/17.
 */

@IgnoreExtraProperties
public class SingleRoute {

    private float totalDuration;
    private double totalDistance;
    private ArrayList<SinglePath> pathList;

    public SingleRoute() {
    }

    public SingleRoute(double totalDistance, float totalDuration, ArrayList<SinglePath> pathList) {
        this.totalDuration = totalDuration;
        this.pathList = pathList;
        this.totalDistance = totalDistance;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public float getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(float totalDuration) {
        this.totalDuration = totalDuration;
    }

    public ArrayList<SinglePath> getPathList() {
        return pathList;
    }

    public void setPathList(ArrayList<SinglePath> pathList) {
        this.pathList = pathList;
    }
}
