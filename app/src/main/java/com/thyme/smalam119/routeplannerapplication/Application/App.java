package com.thyme.smalam119.routeplannerapplication.Application;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;

/**
 * Created by smalam119 on 11/25/17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
