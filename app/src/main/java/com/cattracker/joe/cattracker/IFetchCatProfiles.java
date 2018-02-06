package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;

import java.util.List;

/**
 * Created by JOSEPH on 8/20/2017.
 */

interface IFetchCatProfiles {
    String getTopCatName(Uri catUri);
    Bitmap getCatProfilePicture(Activity context, Uri catUri);
    List<UserComment> getNextFiveUserComments(Uri catUri);
    float getOverallCatRating(Uri catUri);
    List<CatSnap> getCatPictures(Activity context, Uri catUri);
    List<CatName> getNextFiveCatNames(Uri catUri);
}
