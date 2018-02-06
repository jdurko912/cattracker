package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.net.Uri;

/**
 * Created by JOSEPH on 8/26/2017.
 */

class MockCatProfileWriter implements IWriteCatProfiles {

    ICatPictureRepository catSnapRepo;

    @Override
    public Uri addNewCat(Activity activity) {
        if (catSnapRepo == null)
            catSnapRepo = MockCatPictureRepository.getInstance(activity);

        return catSnapRepo.getNextCatId();
    }

    @Override
    public boolean addCatSnap(Activity activity, Uri cat, CatSnap snap) {
        if (catSnapRepo == null)
            catSnapRepo = MockCatPictureRepository.getInstance(activity);

        catSnapRepo.addPicture(cat, snap);
        return true;
    }

    @Override
    public boolean addCatName(Uri cat, String name) {
        return false;
    }

    @Override
    public boolean addCatUserComment(Uri cat, UserComment comment) {
        return false;
    }
}
