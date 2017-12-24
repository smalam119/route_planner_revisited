package com.thyme.smalam119.routeplannerapplication.Utils.Facebook;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by smalam119 on 11/24/17.
 */

public class FBSharedPrefUtils {

    private Activity mActivity;

    public FBSharedPrefUtils(Activity activity) {
        this.mActivity = activity;
    }

    public void saveAccessToken(String token) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("fb_access_token",token);
        editor.commit();
    }

    public String getToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        return prefs.getString("fb_access_token", null);
    }

    public void clearToken() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply(); // This line is IMPORTANT !!!
    }

    public void saveFacebookUserInfo(String first_name,String last_name, String email, String gender, String profileURL){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("fb_first_name", first_name);
        editor.putString("fb_last_name", last_name);
        editor.putString("fb_email", email);
        editor.putString("fb_gender", gender);
        editor.putString("fb_profileURL", profileURL);
        editor.apply(); // This line is IMPORTANT !!!
    }

    public void getFacebookUserInfo(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
    }
}
