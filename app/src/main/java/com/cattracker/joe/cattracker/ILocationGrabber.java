package com.cattracker.joe.cattracker;

import android.location.Location;

/**
 * Created by JOSEPH on 8/16/2017.
 */

interface ILocationGrabber {
    void initializeLocation();
    Location getLocalCoordinates();
    Location getShelterLocation();
    String getLocalCoordinateString(Location location);
    String getShelterLocationString(Location location);
}
