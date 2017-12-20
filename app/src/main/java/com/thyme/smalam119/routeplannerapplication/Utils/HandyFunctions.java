package com.thyme.smalam119.routeplannerapplication.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.thyme.smalam119.routeplannerapplication.R;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

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

    public static int convertDistanceToMeter(String distance) {

        if (distance.equals("")) {
            return 0;
        }

        String[] s = distance.split(" ");
        double result = 0.0;

        try {
            result = Double.parseDouble(s[0]);
        } catch (Exception ex) {
            Log.d("TAG_NUMBER", distance + " result" + result + " string: "
                    + s[0] + " ex: " + ex.toString());
        }

        if (s[1].equals("km"))
            result = result * 1000.0;

        return round(result);
    }

    public static int convertHourToMinute(String duration ) {

        if(duration.equals("")) {
            return 0;
        }

        String[] s = duration.split(" ");
        double result = 0.0;
        double adMin  = 0.0;

        try {
            result = Double.parseDouble(s[0]);
        } catch (Exception ex) {
            Log.d("TAG_NUMBER", duration + " result" + result + " string: "
                    + s[0] + " ex: " + ex.toString());
        }

        if (s[1].equals("h") && s[3].equals("min")){
            adMin = Double.parseDouble(s[2]);
            result = result * 60.0 + adMin;
        }

        else if (s[1].equals("h")){
            result = result * 60.0;
        }

        return round(result);

    }

    public static double convertMeterToKiloMeter(int meter) {
        double km = Double.valueOf(meter) * 0.001;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return Double.parseDouble(df.format(km));
    }

    public static float convertMinuteToHour(int minute) {
        float hr = Float.valueOf(minute) / 60;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return Float.parseFloat(df.format(hr));
    }

    public static int round(double d) {
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if (result < 0.5) {
            return d < 0 ? -i : i;
        } else {
            return d < 0 ? -(i + 1) : i + 1;
        }
    }

    public static Bitmap getMarkerIcon(Activity activity, String alphabet, int color) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(100, 100, conf);

        Paint paintCircle = new Paint();
        paintCircle.setColor(color);
        paintCircle.setStyle(Paint.Style.FILL);

        Paint paintText = new Paint();
        paintText.setColor(activity.getResources().getColor(R.color.black));
        paintText.setStyle(Paint.Style.FILL_AND_STROKE);
        paintText.setTextSize(30);

        Canvas canvas = new Canvas(bmp);
        canvas.drawCircle(50,50,25,paintCircle);
        canvas.drawText(alphabet,40,60,paintText);

        return bmp;
    }

    public static String generateRandomString() {
        String uuid = UUID.randomUUID().toString();
        return  uuid;
    }
}
