package com.thyme.smalam119.routeplannerapplication.Utils;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by smalam119 on 11/26/17.
 */

public class BasicProgressBar {

    private ProgressDialog progress;
    private Activity mActivity;

    public BasicProgressBar(Activity activity, String message) {
        this.mActivity = activity;
        progress=new ProgressDialog(activity);
        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);

    }

    public void show() {
        progress.show();
    }

    public void hide() {
        progress.hide();
    }
}
