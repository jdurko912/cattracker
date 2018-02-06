package com.cattracker.joe.cattracker;

/**
 * Created by JOSEPH on 8/26/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

class SessionManager {
    IUserLoginRepository loginRepo;
    SharedPreferences pref;
    Editor editor;
    Activity _context;

    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "CatTrackerPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_NAME = "name";

    public SessionManager(Activity context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        loginRepo = MockUserLoginRepository.getInstance(context);
        editor = pref.edit();
    }

    public void createLoginSession(String name){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, name);

        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);
        }
    }

    public CatLover getUserDetails(){
        Uri.Builder builder = new Uri.Builder();
        builder.path(pref.getString(KEY_NAME, null));

        return loginRepo.getCatLover(builder.build());
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
