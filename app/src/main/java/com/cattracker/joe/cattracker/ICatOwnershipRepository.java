package com.cattracker.joe.cattracker;

import android.net.Uri;

/**
 * Created by JOSEPH on 8/26/2017.
 */

interface ICatOwnershipRepository {
    CatLover getCatOwner(Uri catUri);
    boolean setCatOwner(Uri catUri, CatLover owner);
}
