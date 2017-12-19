package com.thyme.smalam119.routeplannerapplication.ResultLocationList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.thyme.smalam119.routeplannerapplication.LocationList.VerticalSpaceItemDecoration;
import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;
import com.thyme.smalam119.routeplannerapplication.Model.User.SinglePath;
import com.thyme.smalam119.routeplannerapplication.Model.User.SingleRoute;
import com.thyme.smalam119.routeplannerapplication.Model.User.User;
import com.thyme.smalam119.routeplannerapplication.R;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseAuthUtils;
import com.thyme.smalam119.routeplannerapplication.Utils.Firebase.FireBaseDBUtils;

import java.util.ArrayList;

public class ResultLocationListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TextView mTotalDistanceTV, mTotalDurationTV;
    private Button mSaveResultButton;
    private ResultLocationListAdapter mResultLocationListAdapter;
    private ArrayList<LocationDetail> locationDetails;
    private double mTotalDistance;
    private float mTotalDuration;
    private FireBaseDBUtils mFireBaseDBUtils;
    private FireBaseAuthUtils mFireBaseAuthUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_location_list);
        prepareView();
    }

    private void prepareView() {
        locationDetails = (ArrayList<LocationDetail>)getIntent().getSerializableExtra("resultLocationList");
        mTotalDistance = getIntent().getDoubleExtra("totalDistance",0.0);
        mTotalDuration = getIntent().getFloatExtra("totalDuration",0.0f);

        mFireBaseAuthUtils = new FireBaseAuthUtils(this);
        mFireBaseDBUtils = new FireBaseDBUtils("rpa-data");

        mRecyclerView = (RecyclerView) findViewById(R.id.result_location_recycler_view);
        mTotalDistanceTV = (TextView) findViewById(R.id.total_distance);
        mTotalDurationTV = (TextView) findViewById(R.id.total_duration);
        mSaveResultButton = (Button) findViewById(R.id.save_result_button);
        mSaveResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<SinglePath> singlePaths = new ArrayList<>();
                ArrayList<SingleRoute> singleRoutes = new ArrayList<>();
                for(LocationDetail locationDetail: locationDetails) {
                    SinglePath singlePath = new SinglePath(Double.valueOf(locationDetail.getLat()),
                            Double.valueOf(locationDetail.getLng()),1);
                    singlePaths.add(singlePath);

                }

                SingleRoute singleRoute = new SingleRoute(mTotalDistance,mTotalDuration,singlePaths);
                singleRoutes.add(singleRoute);

                User user = new User(singleRoutes);
                mFireBaseDBUtils.writeData("users",mFireBaseAuthUtils.getCurrentUser().getUid(),user);

            }
        });

        mTotalDistanceTV.setText("Total Distance: " + mTotalDistance + " " + "km");
        mTotalDurationTV.setText("Total Duration: " + mTotalDuration + " " + "hr");

        mResultLocationListAdapter = new ResultLocationListAdapter(this,locationDetails);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(mResultLocationListAdapter);
    }
}
