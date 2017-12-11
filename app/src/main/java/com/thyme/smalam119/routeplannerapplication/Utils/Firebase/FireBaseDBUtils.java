package com.thyme.smalam119.routeplannerapplication.Utils.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by smalam119 on 12/11/17.
 */

public class FireBaseDBUtils {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public OnFireBaseDBChangeListener onFirebaseDBChangeListener;

    public FireBaseDBUtils(String reference) {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference(reference);
    }

    public void writeData(String root,String userId,Object object) {
        mFirebaseDatabase.child(root).child(userId).setValue(object);
    }

    public void readData(String root,String userId) {
        mFirebaseDatabase.child(root).child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onFirebaseDBChangeListener.onDataChanged(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                onFirebaseDBChangeListener.onCancel(error);
            }
        });
    }
}
