package com.thyme.smalam119.routeplannerapplication.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.thyme.smalam119.routeplannerapplication.Model.User.SinglePath;
import com.thyme.smalam119.routeplannerapplication.Model.User.SingleRoute;
import com.thyme.smalam119.routeplannerapplication.Model.User.User;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseAuthUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseDBUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.OnFireBaseDBChangeListener;
import java.util.ArrayList;

public class FireBaseDBTestActivity extends AppCompatActivity implements OnFireBaseDBChangeListener {
    private FireBaseDBUtils mFireBaseDBUtils;
    private FireBaseAuthUtils mFireBaseAuthUtils;
    private Button createButton, updateButton, deleteButton, readButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base_dbtest);
        prepareUtilsAndViews();
    }


    private void prepareUtilsAndViews() {
        mFireBaseAuthUtils = new FireBaseAuthUtils(this);
        mFireBaseDBUtils = new FireBaseDBUtils("rpa-data");
        mFireBaseDBUtils.onFirebaseDBChangeListener = this;
        createButton = (Button) findViewById(R.id.create_button);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireBaseDBUtils.writeData("users","12345",prepareUserData());
            }
        });
        updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireBaseDBUtils.writeData("users","12345",prepareUserData());
            }
        });
        deleteButton = (Button) findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireBaseDBUtils.writeData("users","12345",null);
            }
        });

        readButton = (Button) findViewById(R.id.read_button);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFireBaseDBUtils.readData("users","12345");
            }
        });
        mFireBaseDBUtils.writeData("users", mFireBaseAuthUtils.getCurrentUser().getUid(),prepareUserData());
    }

    private User prepareUserData() {
        SinglePath singlePath = new SinglePath("Test",23.8103,90.4125,1);
        ArrayList<SinglePath> singlePaths = new ArrayList<>();
        singlePaths.add(singlePath);
        SingleRoute singleRoute = new SingleRoute(20.0,60,singlePaths);
        ArrayList<SingleRoute> singleRoutes = new ArrayList<>();
        singleRoutes.add(singleRoute);
        User user = new User(singleRoutes);
        return user;
    }

    @Override
    public void onDataChanged(DataSnapshot dataSnapshot) {
        try {
            User user = dataSnapshot.getValue(User.class);
        } catch (NullPointerException e) {
        }
    }

    @Override
    public void onCancel(DatabaseError error) {
        Log.w("fbdb", "Failed to read value.", error.toException());
    }
}
