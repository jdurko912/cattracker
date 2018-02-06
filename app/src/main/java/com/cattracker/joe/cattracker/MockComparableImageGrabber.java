package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JOSEPH on 8/16/2017.
 */

class MockComparableImageGrabber implements IGrabComparableImages {

    @Override
    public List<Pair<Bitmap, Uri>> getFiveRelevantImages(Activity activity) {
        int[] drawableArray = { R.drawable.exp_cat1, R.drawable.exp_cat2, R.drawable.exp_cat3 };
        List<Pair<Bitmap, Uri>> result = new ArrayList<>();

        for (int drawable : drawableArray)
        {
            Bitmap icon = BitmapFactory.decodeResource(activity.getResources(),
                    drawable);

            Uri.Builder builder = new Uri.Builder();
            switch (drawable){
                case R.drawable.exp_cat1:
                    builder.path(MockCatProfileFetcher.CATPROFILE1);
                    break;
                case R.drawable.exp_cat2:
                    builder.path(MockCatProfileFetcher.CATPROFILE2);
                    break;
                case R.drawable.exp_cat3:
                    builder.path(MockCatProfileFetcher.CATPROFILE3);
                    break;
            }

            result.add(new Pair<>(icon, builder.build()));
        }

        return result;
    }
}
