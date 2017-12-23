package com.thyme.smalam119.routeplannerapplication.Utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by smalam119 on 12/3/17.
 */

public class Alerts {

    public static void simpleAlert(final Activity activity, String title, String buttonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HandyFunctions.gotoSystemSettings(activity);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });
        builder.show();
    }

    public static void simpleAlertWithMessage(final Activity activity, String title, String message, String buttonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    public static void showSimpleWarning(final Activity activity, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
