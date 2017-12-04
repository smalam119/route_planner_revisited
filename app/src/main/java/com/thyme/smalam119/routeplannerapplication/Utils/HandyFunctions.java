package com.thyme.smalam119.routeplannerapplication.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
