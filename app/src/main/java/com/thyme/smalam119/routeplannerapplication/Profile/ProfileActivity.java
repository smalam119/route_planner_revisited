package com.thyme.smalam119.routeplannerapplication.Profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.thyme.smalam119.routeplannerapplication.LocationList.VerticalSpaceItemDecoration;
import com.thyme.smalam119.routeplannerapplication.Model.User.SingleRoute;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseAuthUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseDBUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.OnFireBaseDBChangeListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements OnFireBaseDBChangeListener {
    private RecyclerView recyclerView;
    private Button optButton, sendNotiButton;
    private TextView userNameTV, userEmailTV;
    private PreviousRouteListAdapter previousRouteListAdapter;
    private FireBaseDBUtils mFireBaseDBUtils;
    private FireBaseAuthUtils mFireBaseAuthUtils;
    public ArrayList<SingleRoute> previousRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        prepareView();
    }

    private void prepareView() {

        mFireBaseAuthUtils = new FireBaseAuthUtils(this);
        mFireBaseDBUtils = new FireBaseDBUtils("rpa-data");
        mFireBaseDBUtils.onFirebaseDBChangeListener = this;
        mFireBaseDBUtils.readData("users",mFireBaseAuthUtils.getCurrentUser().getUid(),"routeList");

        recyclerView = (RecyclerView) findViewById(R.id.saved_route_recycle_view);
        optButton = (Button) findViewById(R.id.get_opt_button);
        sendNotiButton = (Button) findViewById(R.id.send_not_button);
        userNameTV = (TextView) findViewById(R.id.user_name);
        userEmailTV = (TextView) findViewById(R.id.user_email);

        previousRoutes = new ArrayList<>();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        recyclerView.setLayoutManager(llm);
    }

    @Override
    public void onDataChanged(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            SingleRoute singleRoute = snapshot.getValue(SingleRoute.class);
            previousRoutes.add(singleRoute);
        }

        previousRouteListAdapter = new PreviousRouteListAdapter(this,previousRoutes);
        recyclerView.setAdapter(previousRouteListAdapter);
        Log.d("onDataChanged",previousRoutes.size() + "");
    }

    @Override
    public void onCancel(DatabaseError error) {

    }
}
