package com.cattracker.joe.cattracker;

/**
 * Created by JOSEPH on 8/23/2017.
 */

class CatName {
    String name;
    int numberOfVotes;
    boolean voted;

    public CatName(String n, int num, boolean v)
    {
        name = n;
        numberOfVotes = num;
        voted = v;
    }
}
