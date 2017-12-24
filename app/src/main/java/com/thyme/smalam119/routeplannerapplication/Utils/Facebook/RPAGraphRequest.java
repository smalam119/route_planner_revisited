package com.thyme.smalam119.routeplannerapplication.Utils.Facebook;

import android.app.Activity;
import android.os.Bundle;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by smalam119 on 11/24/17.
 */

class RPAGraphRequest implements GraphRequest.GraphJSONObjectCallback {

    Activity activity;
    FBSharedPrefUtils FBSharedPrefUtils;

    public RPAGraphRequest(Activity activity) {
        this.activity = activity;
        FBSharedPrefUtils = new FBSharedPrefUtils(activity);
    }

    @Override
    public void onCompleted(JSONObject jsonObject, GraphResponse response) {
        Bundle facebookData = getFacebookData(jsonObject);
    }

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));


            FBSharedPrefUtils.saveFacebookUserInfo(object.getString("first_name"),
                    object.getString("last_name"),object.getString("email"),
                    object.getString("gender"), profile_pic.toString());

        } catch (Exception e) {
        }

        return bundle;
    }
}
