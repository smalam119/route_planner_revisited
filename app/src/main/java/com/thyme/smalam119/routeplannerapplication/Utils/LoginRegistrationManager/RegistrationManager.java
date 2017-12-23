package com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager;

import android.app.Activity;

import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseAuthUtils;

/**
 * Created by smalam119 on 12/23/17.
 */

public class RegistrationManager {
    private FireBaseAuthUtils mFireBaseAuthUtils;
    public OnRegistrationListener onRegistrationListener;
    public Activity activity;

    public RegistrationManager(Activity activity) {
        this.activity = activity;
        mFireBaseAuthUtils = new FireBaseAuthUtils(this,activity);
    }

    public void register(String email, String password) {
        mFireBaseAuthUtils.createUser(email,password);
    }
}
