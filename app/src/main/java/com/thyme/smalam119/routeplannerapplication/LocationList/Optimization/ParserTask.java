package com.thyme.smalam119.routeplannerapplication.LocationList.Optimization;

import android.graphics.Color;
import android.os.AsyncTask;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.thyme.smalam119.routeplannerapplication.LocationList.LocationListActivity;
import com.thyme.smalam119.routeplannerapplication.Utils.JsonParserForDirection;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sayedalam on 12/18/17.
 */

class ParserTask extends
        AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    private LocationListActivity mActivity;

    public ParserTask(LocationListActivity mActivity) {
        this.mActivity = mActivity;
    }

    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(
            String... jsonData) {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            JsonParserForDirection parser = new JsonParserForDirection();

            // Starts parsing data
            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
        String distance = "";
        String duration = "";

        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                if (j == 0) { // Get distance from the list
                    distance = point.get("distance");
                    continue;
                } else if (j == 1) { // Get duration from the list
                    duration = point.get("duration");
                    continue;
                }

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(2);
            lineOptions.color(Color.GREEN);
        }

        mActivity.distanceList.add(distance);
        mActivity.durationList.add(duration);
        mActivity.lineOptionsList.add(lineOptions);

        // Log.d("DISTANCE", distance);
        // Log.d("DURATION", duration);
        // Drawing polyline in the Google Map for the i-th route
        //map.addPolyline(lineOptions);
    }
}
