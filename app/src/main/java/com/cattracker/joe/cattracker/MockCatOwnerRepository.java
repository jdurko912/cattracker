package com.cattracker.joe.cattracker;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JOSEPH on 8/26/2017.
 */

class MockCatOwnerRepository implements ICatOwnershipRepository {

    private static MockCatOwnerRepository instance;

    public static MockCatOwnerRepository getInstance()
    {
        if (instance == null)
            instance = new MockCatOwnerRepository();

        return instance;
    }

    private Map<Uri, CatLover> ownerRepo;

    public MockCatOwnerRepository()
    {
        ownerRepo = new HashMap<>();
    }

    @Override
    public CatLover getCatOwner(Uri catUri) {
        if (! ownerRepo.containsKey(catUri))
            return null;

        return ownerRepo.get(catUri);
    }

    @Override
    public boolean setCatOwner(Uri catUri, CatLover owner) {
        if (ownerRepo.containsKey(catUri))
            return false;

        ownerRepo.put(catUri, owner);

        return true;
    }
}
