package com.thyme.smalam119.routeplannerapplication.Utils.Facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginResult;
import com.thyme.smalam119.routeplannerapplication.Map.MainActivity;

/**
 * Created by smalam119 on 11/24/17.
 */

public class RPAFacebookCallBack implements FacebookCallback<LoginResult> {

    private Activity mActivity;
    FBSharedPrefUtils FBSharedPrefUtils;

    public RPAFacebookCallBack(Activity activity) {
        this.mActivity = activity;
        FBSharedPrefUtils = new FBSharedPrefUtils(activity);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.d("login success",loginResult.toString());
        AccessToken accessToken = loginResult.getAccessToken();
        FBSharedPrefUtils.saveAccessToken(accessToken.getToken());
        makeGraphRequest(accessToken);
        mActivity.startActivity(new Intent(mActivity, MainActivity.class));
        mActivity.finish();
    }

    @Override
    public void onCancel() {
        Log.d("login Canceled","");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("login failed",error.toString());
        FBSharedPrefUtils.clearToken();
    }

    private void makeGraphRequest(AccessToken accessToken) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken,new RPAGraphRequest(mActivity));
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

}

