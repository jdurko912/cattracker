package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.net.Uri;

/**
 * Created by JOSEPH on 8/26/2017.
 */

interface IWriteCatProfiles {
    Uri addNewCat(Activity activity);
    boolean addCatSnap(Activity context, Uri cat, CatSnap snap);
    boolean addCatName(Uri cat, String name);
    boolean addCatUserComment(Uri cat, UserComment comment);
}
