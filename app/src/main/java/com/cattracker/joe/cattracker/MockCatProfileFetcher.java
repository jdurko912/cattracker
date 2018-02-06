package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by JOSEPH on 8/17/2017.
 */

class MockCatProfileFetcher implements IFetchCatProfiles {

    public static final String CATPROFILE1 = "CATPROFILE1";
    public static final String CATPROFILE2 = "CATPROFILE2";
    public static final String CATPROFILE3 = "CATPROFILE3";
    public static final String NEWCATPROFILE = "NEWCAT";

    IUserCommentRepository commentRepo = MockUserCommentRepository.getInstance();
    ICatNameRepository nameRepo = MockCatNameRepository.getInstance();

    Iterable<UserComment> currentPictureIteratable;
    Iterable<CatName> currentNameIteratable;

    @Override
    public String getTopCatName(Uri catUri)
    {
        CatName topCatName = null;

        for (CatName catName : nameRepo.getNames(catUri))
        {
            if (topCatName == null)
            {
                topCatName = catName;
                continue;
            }

            if (catName.numberOfVotes > topCatName.numberOfVotes)
                topCatName = catName;
        }

        if (topCatName == null)
            return "Furry Friend";

        return topCatName.name;
    }

    @Override
    public Bitmap getCatProfilePicture(Activity context, Uri catUri) {
        ICatPictureRepository repo = MockCatPictureRepository.getInstance(context);
        Bitmap profile = repo.getPictures(catUri).iterator().next().image;

        return profile;
    }

    @Override
    public List<UserComment> getNextFiveUserComments(Uri catUri) {
        if (currentPictureIteratable == null)
            currentPictureIteratable = commentRepo.getUserComments(catUri);

        int count = 0;
        List<UserComment> result = new ArrayList<>();
        for (UserComment comment : currentPictureIteratable)
        {
            result.add(comment);

            if (++count == 5)
                return result;
        }

        return result;
    }

    @Override
    public float getOverallCatRating(Uri catUri) {

        float totalStars = 0;
        int numberOfComments = 0;
        for (UserComment comment : commentRepo.getUserComments(catUri)) {
            totalStars += comment.starRating;
            numberOfComments++;
        }

        if (numberOfComments == 0)
            return 0;

        float average = totalStars/numberOfComments;
        return Math.round(average * 2) / 2.0f;
    }

    @Override
    public List<CatSnap> getCatPictures(Activity context, Uri catUri) {
        ICatPictureRepository pictureRepo = MockCatPictureRepository.getInstance(context);
        Iterable<CatSnap> pictures = pictureRepo.getPictures(catUri);

        List<CatSnap> result = new ArrayList<>();
        for (CatSnap picture : pictures)
        {
            result.add(picture);
        }

        return result;
    }

    @Override
    public List<CatName> getNextFiveCatNames(Uri catUri) {
        if (currentNameIteratable == null)
            currentNameIteratable = nameRepo.getNames(catUri);

        int count = 0;
        List<CatName> result = new ArrayList<>();
        for (CatName name : currentNameIteratable)
        {
            result.add(name);

            if (++count == 5)
                return result;
        }

        return result;

    }
}
