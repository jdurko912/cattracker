package com.cattracker.joe.cattracker;

import android.net.Uri;

/**
 * Created by JOSEPH on 8/26/2017.
 */

public interface IUserLoginRepository {
    boolean checkUserCredentials(Uri username, String password);
    CatLover getCatLover(Uri userName);
    boolean addUser(Uri userName, String password);
    boolean updateUserProfile(CatLover catLover);
}
