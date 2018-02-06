package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by JOSEPH on 8/22/2017.
 */

class MockCatPictureRepository implements ICatPictureRepository {

    private static MockCatPictureRepository _instance;

    public static MockCatPictureRepository getInstance(Activity context)
    {
        if (_instance == null)
            _instance = new MockCatPictureRepository(context);

        return _instance;
    }

    private static List<CatSnap> cat1List = new ArrayList<>();

    private static List<CatSnap> cat2List = new ArrayList<>();

    private static List<CatSnap> cat3List = new ArrayList<>();

    private static Map<Uri, List<CatSnap>> newCatDict = new HashMap<>();

    private MockCatPictureRepository(Activity context)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try
        {
            Location humaneSoceityLocation = new Location("");
            humaneSoceityLocation.setLatitude(40.4558d);
            humaneSoceityLocation.setLongitude(-79.9039d);

            Bitmap cat1Image = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.exp_cat1);

            Location cat1Location = new Location("");
            cat1Location.setLatitude(40.4406d);
            cat1Location.setLongitude(-79.9959d);
            cat1List.add(new CatSnap(cat1Image, cat1Location, Uri.parse(MockCatProfileFetcher.CATPROFILE1), humaneSoceityLocation, sdf.parse("2017/8/19 15:00:00")));

            Bitmap cat2Image = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.exp_cat2);

            Location cat2Location = new Location("");
            cat2Location.setLatitude(40.427442d);
            cat2Location.setLongitude(-79.946855d);
            cat2List.add(new CatSnap(cat2Image, cat2Location, Uri.parse(MockCatProfileFetcher.CATPROFILE2), humaneSoceityLocation, sdf.parse("2017/8/20 11:00:00")));

            Bitmap cat3Image = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.exp_cat3);

            Location cat3Location = new Location("");
            cat3Location.setLatitude(40.430399d);
            cat3Location.setLongitude(-79.945139d);
            cat3List.add(new CatSnap(cat3Image, cat3Location, Uri.parse(MockCatProfileFetcher.CATPROFILE3), humaneSoceityLocation, sdf.parse("2017/8/22 18:00:00")));
        }
        catch (ParseException e)
        {
        }
    }

    @Override
    public boolean addPicture(Uri catId, CatSnap pic) {
        switch (catId.toString())
        {
            case MockCatProfileFetcher.CATPROFILE1:
                cat1List.add(pic);
                break;
            case MockCatProfileFetcher.CATPROFILE2:
                cat2List.add(pic);
                break;
            case MockCatProfileFetcher.CATPROFILE3:
                cat3List.add(pic);
                break;
            default:
                if (! newCatDict.containsKey(catId))
                    newCatDict.put(catId, new ArrayList<CatSnap>());

                newCatDict.get(catId).add(pic);
        }

        return true;
    }

    @Override
    public Iterable<CatSnap> getPictures(final Uri catId) {
        switch (catId.toString())
        {
            case MockCatProfileFetcher.CATPROFILE1:
                return new Iterable<CatSnap>() {
                    @Override
                    public Iterator<CatSnap> iterator() {
                        return new CatSnapIterator(cat1List);
                    }
                };
            case MockCatProfileFetcher.CATPROFILE2:
                return new Iterable<CatSnap>() {
                    @Override
                    public Iterator<CatSnap> iterator() {
                        return new CatSnapIterator(cat2List);
                    }
                };
            case MockCatProfileFetcher.CATPROFILE3:
                return new Iterable<CatSnap>() {
                    @Override
                    public Iterator<CatSnap> iterator() {
                        return new CatSnapIterator(cat3List);
                    }
                };
            default:
                return new Iterable<CatSnap>() {
                    @Override
                    public Iterator<CatSnap> iterator() {
                        return new CatSnapIterator(newCatDict.get(catId));
                    }
                };
        }
    }

    @Override
    public Uri getNextCatId() {
        // TODO:  make this more efficient
        for ( int i = 4; i < Integer.MAX_VALUE; i++)
        {
            Uri.Builder builder = new Uri.Builder();
            builder.path("CATPROFILE" + String.valueOf(i));
            Uri newUri = builder.build();

            boolean alreadyExists = false;
            for ( Uri catUri : newCatDict.keySet())
            {
                if (catUri == newUri)
                {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists)
                return newUri;
        }

        return null;
    }

    private class CatSnapIterator implements Iterator<CatSnap>
    {
        List<CatSnap> list;
        int position;

        public CatSnapIterator(List<CatSnap> l)
        {
            list = l;
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return list.size() > position;
        }

        @Override
        public CatSnap next() {
            return list.get(position++);
        }
    }
}
