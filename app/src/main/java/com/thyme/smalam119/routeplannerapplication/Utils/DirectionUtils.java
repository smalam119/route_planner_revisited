package com.thyme.smalam119.routeplannerapplication.Utils;

import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sayedalam on 12/18/17.
 */

public class DirectionUtils {

    public static String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + ","
                + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + parameters;

        return url;
    }

    /** A method to download json data from url */
    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            inputStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inputStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Error downloading url", e.toString());
        } finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public ArrayList<String> latlngArrayToStringArray(ArrayList<LatLng> latLngList){

        ArrayList<String> stringLatLngs = new ArrayList();
        String sLatLng = null;
        for(int x = 0 ; x < latLngList.size(); x++){
            LatLng latLng = latLngList.get(x);
            sLatLng = String.valueOf(latLng);
            stringLatLngs.add(sLatLng);
        }
        return stringLatLngs ;



    }

    public String splitter(ArrayList<LatLng> sLatLng, int index, String flag){

        String latLngStringArray = latlngArrayToStringArray(sLatLng).get(index);
        String[] s = latLngStringArray.split(",");
        String[] s2= s[0].split(" ");
        String s3 = s2[1].replaceAll("[<>\\(\\),-]", "").replaceAll("\\s+","");
        String s4 = s[1].replaceAll("[<>\\(\\),-]", "").replaceAll("\\s+","");

        if(flag.equals("lat"))
            return s3;
        if(flag.equals("lng"))
            return s4;
        return "no flag";

    }
}
