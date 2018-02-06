package com.cattracker.joe.cattracker;

import android.net.Uri;

/**
 * Created by JOSEPH on 8/22/2017.
 */

interface ICatPictureRepository {
    boolean addPicture(Uri catId, CatSnap pic);
    Iterable<CatSnap> getPictures(Uri catId);
    Uri getNextCatId();
}
