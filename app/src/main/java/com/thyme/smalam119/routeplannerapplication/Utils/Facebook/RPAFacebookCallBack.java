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
import com.thyme.smalam119.routeplannerapplication.MainActivity;
import com.thyme.smalam119.routeplannerapplication.Utils.SharedPrefUtils;

/**
 * Created by smalam119 on 11/24/17.
 */

public class RPAFacebookCallBack implements FacebookCallback<LoginResult> {

    Activity activity;
    SharedPrefUtils sharedPrefUtils;

    public RPAFacebookCallBack(Activity activity) {
        this.activity = activity;
        sharedPrefUtils = new SharedPrefUtils(activity);
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.d("login success",loginResult.toString());
        AccessToken accessToken = loginResult.getAccessToken();
        sharedPrefUtils.saveAccessToken(accessToken.getToken());
        makeGraphRequest(accessToken);
        activity.startActivity(new Intent(activity, MainActivity.class));
    }

    @Override
    public void onCancel() {
        Log.d("login Canceled","");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("login failed",error.toString());
        sharedPrefUtils.clearToken();
    }

    private void makeGraphRequest(AccessToken accessToken) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken,new RPAGraphRequest(activity));
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

}

