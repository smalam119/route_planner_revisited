package com.thyme.smalam119.routeplannerapplication.Map.InputMap;

import com.thyme.smalam119.routeplannerapplication.Model.LocationDetail;

/**
 * Created by smalam119 on 12/2/17.
 */

public interface OnMapInteractionCallBack {

    void onMapLongClick(LocationDetail locationDetail);
    void onMapClick();
}
