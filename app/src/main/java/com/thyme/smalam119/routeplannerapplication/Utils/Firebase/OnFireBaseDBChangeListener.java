package com.thyme.smalam119.routeplannerapplication.Utils.Firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by smalam119 on 12/11/17.
 */

public interface OnFireBaseDBChangeListener {

    public void onDataChanged(DataSnapshot dataSnapshot);
    public void onCancel(DatabaseError error);
}
