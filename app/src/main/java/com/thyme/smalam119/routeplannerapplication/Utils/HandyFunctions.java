package com.thyme.smalam119.routeplannerapplication.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Random;

/**
 * Created by smalam119 on 12/3/17.
 */

public class HandyFunctions {

    public static boolean isConnectedWithNetwork(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnected();
        return isConnected;
    }

    public static String getFirstCharacter(String s) {
        if(s == null) {
            return "U";
        }
        return s.substring(0,1);
    }

    public static void gotoSystemSettings(Activity activity) {
        Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(dialogIntent);
    }

    public static int getRandomColor() {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }
}
