package com.thyme.smalam119.routeplannerapplication.Utils.Facebook;

import android.app.Activity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.thyme.smalam119.routeplannerapplication.Utils.SharedPrefUtils;

/**
 * Created by smalam119 on 11/24/17.
 */

public class RPAFacebookAccessTokenTracker extends AccessTokenTracker {

    private Activity mActivity;
    SharedPrefUtils sharedPrefUtils;

    public RPAFacebookAccessTokenTracker(Activity activity) {
        this.mActivity = activity;
        sharedPrefUtils = new SharedPrefUtils(activity);
    }

    @Override
    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
        if (currentAccessToken == null){
            //User logged out
            sharedPrefUtils.clearToken();
            LoginManager.getInstance().logOut();
        }
    }
}
