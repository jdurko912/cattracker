package com.cattracker.joe.cattracker;

import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by JOSEPH on 8/26/2017.
 */

class CatLover {
    public Bitmap profilePicture;
    public Uri screenName;
    public String password;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public String streetAddress;
    public String phoneNumber;
    public int picturesTaken;

    public CatLover(Uri sn, String pw)
    {
        screenName = sn;
        password = pw;
    }

    public float getRating()
    {
        return picturesTaken / 10.0f;
    }
}
