package com.cattracker.joe.cattracker;

import android.app.Activity;

/**
 * Created by JOSEPH on 8/17/2017.
 */

interface IProvideMap {
    Object getMap(Activity context);
    void populateMap(Activity context, Object map);
}
