package com.cattracker.joe.cattracker;

import android.net.Uri;

/**
 * Created by JOSEPH on 8/23/2017.
 */

interface ICatNameRepository {
    boolean addName(Uri catId, CatName name);
    Iterable<CatName> getNames(Uri catId);
    boolean addVote(Uri catId, String name);
}
