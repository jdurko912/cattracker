package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;

import java.util.List;

/**
 * Created by JOSEPH on 8/16/2017.
 */

interface IGrabComparableImages {
    List<Pair<Bitmap, Uri>> getFiveRelevantImages(Activity activity);
}
