package com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager;

import android.app.Activity;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseAuthUtils;

/**
 * Created by smalam119 on 12/23/17.
 */

public class LoginManager {
    private FireBaseAuthUtils mFireBaseAuthUtils;
    public OnLoginListener onLoginListener;
    public Activity activity;

    public LoginManager(Activity activity) {
        this.activity = activity;
        mFireBaseAuthUtils = new FireBaseAuthUtils(this,activity);
    }

    public void login(String email, String password) {
        mFireBaseAuthUtils.signInUser(email,password);
    }

    public void logout() {
        removeAuthKey();
    }

    public String getAuthKey() {
        if(mFireBaseAuthUtils.getCurrentUser() == null) {
            return null;
        } else {
            return mFireBaseAuthUtils.getCurrentUser().getUid();
        }
    }

    public String getUserMail() {
        if(mFireBaseAuthUtils.getCurrentUser() == null) {
            return null;
        } else {
            return mFireBaseAuthUtils.getCurrentUser().getEmail();
        }
    }

    public void removeAuthKey() {
        if(mFireBaseAuthUtils.getCurrentUser() != null) {
            mFireBaseAuthUtils.logOut();
        }
    }

}
