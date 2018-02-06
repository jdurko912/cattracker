package com.cattracker.joe.cattracker;

import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by JOSEPH on 8/23/2017.
 */

class MockCatNameRepository implements ICatNameRepository {
    private static MockCatNameRepository _instance;

    public static MockCatNameRepository getInstance()
    {
        if (_instance == null)
            _instance = new MockCatNameRepository();

        return _instance;
    }

    public MockCatNameRepository()
    {
        cat1List.add(new CatName("Boo-Boo", 4000, false));

        cat2List.add(new CatName("Leo", 4000, false));

        cat3List.add(new CatName("Cheddar", 4000, false));
    }

    private static List<CatName> cat1List = new ArrayList<>();

    private static List<CatName> cat2List = new ArrayList<>();

    private static List<CatName> cat3List = new ArrayList<>();

    private static Map<Uri, List<CatName>> newCatDict = new HashMap<>();

    @Override
    public Iterable<CatName> getNames(final Uri catId) {
        switch (catId.toString())
        {
            case MockCatProfileFetcher.CATPROFILE1:
                return new Iterable<CatName>() {
                    @Override
                    public Iterator<CatName> iterator() {
                        return new CatNameIterator(cat1List);
                    }
                };
            case MockCatProfileFetcher.CATPROFILE2:
                return new Iterable<CatName>() {
                    @Override
                    public Iterator<CatName> iterator() {
                        return new CatNameIterator(cat2List);
                    }
                };
            case MockCatProfileFetcher.CATPROFILE3:
                return new Iterable<CatName>() {
                    @Override
                    public Iterator<CatName> iterator() {
                        return new CatNameIterator(cat3List);
                    }
                };
            default:
                return new Iterable<CatName>() {
                    @Override
                    public Iterator<CatName> iterator() {
                        if (! newCatDict.containsKey(catId))
                            return new CatNameIterator(new ArrayList<CatName>());

                        return new CatNameIterator(newCatDict.get(catId));
                    }
                };
        }
    }

    @Override
    public boolean addVote(Uri catId, String name) {
        List<CatName> thisCatsNames;

        switch (catId.toString())
        {
            case MockCatProfileFetcher.CATPROFILE1:
                thisCatsNames = cat1List;
                break;
            case MockCatProfileFetcher.CATPROFILE2:
                thisCatsNames = cat2List;
                break;
            case MockCatProfileFetcher.CATPROFILE3:
                thisCatsNames = cat3List;
                break;
            default:
                if (! newCatDict.containsKey(catId))
                    return false;

                thisCatsNames = newCatDict.get(catId);
        }

        for (CatName catName : thisCatsNames)
        {
            if (catName.name == name)
            {
                catName.numberOfVotes++;
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean addName(Uri catId, CatName comment)
    {
        switch (catId.toString())
        {
            case MockCatProfileFetcher.CATPROFILE1:
                cat1List.add(comment);
                break;
            case MockCatProfileFetcher.CATPROFILE2:
                cat2List.add(comment);
                break;
            case MockCatProfileFetcher.CATPROFILE3:
                cat3List.add(comment);
                break;
            default:
                if (! newCatDict.containsKey(catId))
                    newCatDict.put(catId, new ArrayList<CatName>());

                newCatDict.get(catId).add(comment);
        }

        return true;
    }

    private class CatNameIterator implements Iterator<CatName>
    {
        List<CatName> list;
        int position;

        public CatNameIterator(List<CatName> l)
        {
            list = l;
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return list.size() > position;
        }

        @Override
        public CatName next() {
            return list.get(position++);
        }
    }
}
