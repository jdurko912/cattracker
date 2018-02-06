package com.cattracker.joe.cattracker;

import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;

import java.util.Date;

/**
 * Created by JOSEPH on 8/22/2017.
 */

public class CatSnap {
    public Bitmap image;
    public Location location;
    public Uri cat;
    public Location humaneSociety;
    public Date dateTaken;

    public CatSnap()
    {}

    public CatSnap(Bitmap i, Location l, Uri c, Location h, Date d)
    {
        image = i;
        location = l;
        cat = c;
        humaneSociety = h;
        dateTaken = d;
    }
}
