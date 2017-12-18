package com.thyme.smalam119.routeplannerapplication.Utils.Facebook;

import android.app.Activity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;

/**
 * Created by smalam119 on 11/24/17.
 */

public class RPAFacebookAccessTokenTracker extends AccessTokenTracker {

    private Activity mActivity;
    FBSharedPrefUtils FBSharedPrefUtils;

    public RPAFacebookAccessTokenTracker(Activity activity) {
        this.mActivity = activity;
        FBSharedPrefUtils = new FBSharedPrefUtils(activity);
    }

    @Override
    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
        if (currentAccessToken == null){
            //User logged out
            FBSharedPrefUtils.clearToken();
            LoginManager.getInstance().logOut();
        }
    }
}
