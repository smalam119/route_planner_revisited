package com.thyme.smalam119.routeplannerapplication.LocationList.Optimization;

import android.os.AsyncTask;
import android.util.Log;

import com.thyme.smalam119.routeplannerapplication.LocationList.LocationListActivity;
import com.thyme.smalam119.routeplannerapplication.Utils.DirectionUtils;

/**
 * Created by sayedalam on 12/18/17.
 */

public class DownloadTask extends AsyncTask<String, Void, String> {

    private LocationListActivity mActivity;

    public DownloadTask(LocationListActivity mActivity) {
        this.mActivity = mActivity;
    }

    // Downloading data in non-ui thread
    @Override
    protected String doInBackground(String... url) {

        // For storing data from web service
        String data = "";

        try {
            // Fetching the data from web service
            data = DirectionUtils.downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }

    // Executes in UI thread, after the execution of
    // doInBackground()
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        ParserTask parserTask = new ParserTask(mActivity);

        // Invokes the thread for parsing the JSON data
        parserTask.execute(result);

    }
}
