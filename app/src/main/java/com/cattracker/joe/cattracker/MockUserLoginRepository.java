package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JOSEPH on 8/26/2017.
 */

class MockUserLoginRepository implements IUserLoginRepository {

    private static MockUserLoginRepository instance;

    public static MockUserLoginRepository getInstance(Activity context)
    {
        if (instance == null)
        {
            instance = new MockUserLoginRepository(context);
        }

        return instance;
    }

    private Map<Uri, CatLover> catLoverRepo;

    private static final String USER1 = "jsd94";
    private static final String USER2 = "guest";

    public MockUserLoginRepository(Activity context)
    {
        catLoverRepo = new HashMap<>();

        Uri.Builder builder = new Uri.Builder();
        builder.path(USER1);

        CatLover catLover = new CatLover(builder.build(), "ilovecats");
        catLover.emailAddress = "jsd94@drexel.edu";
        catLover.firstName = "Joe";
        catLover.lastName = "Durko";
        catLover.profilePicture = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.defaultuser1);
        catLover.picturesTaken = 300;
        catLover.streetAddress = "777 Pittsburgh Street\nPittsburgh, PA 15207";
        catLover.phoneNumber = "555-555-5555";

        catLoverRepo.put(catLover.screenName, catLover);


        builder.path(USER2);

        CatLover catLover2 = new CatLover(builder.build(), "guest");
        catLover2.emailAddress = "guest@drexel.edu";
        catLover2.firstName = "Guest";
        catLover2.picturesTaken = 9;
        catLover2.profilePicture = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.guest);

        catLoverRepo.put(catLover2.screenName, catLover2);
    }

    @Override
    public boolean checkUserCredentials(Uri username, String password) {
        if (! catLoverRepo.containsKey(username))
            return false;

        CatLover catLover = catLoverRepo.get(username);
        if (! password.equals(catLover.password))
            return false;

        return true;
    }

    @Override
    public CatLover getCatLover(Uri userName) {
        if (! catLoverRepo.containsKey(userName))
            return null;

        return catLoverRepo.get(userName);
    }

    @Override
    public boolean addUser(Uri userName, String password) {
        if (catLoverRepo.containsKey(userName))
            return false;

        catLoverRepo.put(userName, new CatLover(userName, password));

        return true;
    }

    @Override
    public boolean updateUserProfile(CatLover catLover) {
        if (! catLoverRepo.containsKey(catLover.screenName))
            return false;

        catLoverRepo.put(catLover.screenName, catLover);
        return true;
    }
}
