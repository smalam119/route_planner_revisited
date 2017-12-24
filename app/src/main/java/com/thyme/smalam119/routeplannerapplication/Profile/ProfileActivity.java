package com.thyme.smalam119.routeplannerapplication.Profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.thyme.smalam119.routeplannerapplication.LocationList.VerticalSpaceItemDecoration;
import com.thyme.smalam119.routeplannerapplication.Map.InputMap.MainActivity;
import com.thyme.smalam119.routeplannerapplication.Map.NotificationMap.NotificationMapActivity;
import com.thyme.smalam119.routeplannerapplication.Model.User.SingleRoute;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseDBUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.OnFireBaseDBChangeListener;
import com.thyme.smalam119.routeplannerapplication.Utils.LoginRegistrationManager.LoginManager;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements OnFireBaseDBChangeListener {
    private RecyclerView recyclerView;
    private Button optButton, sendNotiButton;
    private TextView userEmailTV;
    private PreviousRouteListAdapter previousRouteListAdapter;
    private FireBaseDBUtils mFireBaseDBUtils;
    private LoginManager mLoginManager;
    public ArrayList<SingleRoute> previousRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        prepareView();
    }

    private void prepareView() {

        mLoginManager = new LoginManager(this);
        mFireBaseDBUtils = new FireBaseDBUtils("rpa-data");
        mFireBaseDBUtils.onFirebaseDBChangeListener = this;
        mFireBaseDBUtils.readData("users",mLoginManager.getAuthKey(),"routeList");

        recyclerView = (RecyclerView) findViewById(R.id.saved_route_recycle_view);
        optButton = (Button) findViewById(R.id.get_opt_button);
        optButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
        sendNotiButton = (Button) findViewById(R.id.send_not_button);
        sendNotiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, NotificationMapActivity.class));
            }
        });
        userEmailTV = (TextView) findViewById(R.id.user_email);
        userEmailTV.setText(mLoginManager.getUserMail());

        previousRoutes = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        recyclerView.setLayoutManager(llm);
    }

    private void setUserName(String username) {
        mFireBaseDBUtils.writeData("users",mLoginManager.getAuthKey(),"username",username);
    }

    @Override
    public void onDataChanged(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            SingleRoute singleRoute = snapshot.getValue(SingleRoute.class);
            previousRoutes.add(singleRoute);
        }

        previousRouteListAdapter = new PreviousRouteListAdapter(this,previousRoutes);
        recyclerView.setAdapter(previousRouteListAdapter);
    }

    @Override
    public void onCancel(DatabaseError error) {

    }
}
