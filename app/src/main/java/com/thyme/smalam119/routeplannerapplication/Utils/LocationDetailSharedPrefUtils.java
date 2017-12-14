package com.thyme.smalam119.routeplannerapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by sayedalam on 12/14/17.
 */

public class LocationDetailSharedPrefUtils {
    private Context mContext;
    private Gson mGson;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String LOCATION_DETAIL_LIST_KEY = "loc_det";

    public LocationDetailSharedPrefUtils(Context mContext) {
        mGson = new Gson();
        this.mContext = mContext;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mEditor = mSharedPreferences.edit();
    }

    public void setLocationDataToSharedPref(ArrayList<LocationDetail> locationDetails) {
        String id = LOCATION_DETAIL_LIST_KEY ;
        String locationDataJson = mGson.toJson(locationDetails);
        mEditor.putString(id,locationDataJson);
        mEditor.commit();
    }

    public ArrayList<LocationDetail> getLocationDataFromSharedPref() {
        Type type = new TypeToken<ArrayList<LocationDetail>>(){}.getType();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String locationDataJson = prefs.getString(LOCATION_DETAIL_LIST_KEY, null);
        ArrayList<LocationDetail> locationDetails  = mGson.fromJson(locationDataJson, type);
        return locationDetails;
    }
}
