package com.cattracker.joe.cattracker;

import android.location.Location;

/**
 * Created by JOSEPH on 8/16/2017.
 */

class MockLocationGrabber implements ILocationGrabber {
    @Override
    public void initializeLocation() {

    }

    @Override
    public String getLocalCoordinateString(Location location) {
        return "1122 Boogie Woogie Avenue\n" +
                "San Francisco, CA 90210";
    }

    @Override
    public String getShelterLocationString(Location location) {
        return "250 Florida St\n" +
                "San Francisco, CA 94103";
    }

    @Override
    public Location getLocalCoordinates() {
        Location loc = new Location("");

        loc.setLatitude(37.866603d);
        loc.setLongitude(-121.411579d);

        return loc;
    }

    @Override
    public Location getShelterLocation() {
        Location loc = new Location("");

        loc.setLatitude(37.766603d);
        loc.setLongitude(-122.411579d);

        return loc;
    }
}
