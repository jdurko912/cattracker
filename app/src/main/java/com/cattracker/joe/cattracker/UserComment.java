package com.cattracker.joe.cattracker;

import android.net.Uri;

import java.util.Date;

/**
 * Created by JOSEPH on 8/20/2017.
 */

class UserComment {
    public float starRating;
    public String comment;
    public Uri userId;
    public Date dateSubmitted;

    public UserComment() {}

    public UserComment(float sr, String c, Uri uid, Date d)
    {
        starRating = sr;
        comment = c;
        userId = uid;
        dateSubmitted = d;
    }
}
