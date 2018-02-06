package com.cattracker.joe.cattracker;

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

class MockUserCommentRepository implements IUserCommentRepository {

    private static MockUserCommentRepository _instance;

    public static MockUserCommentRepository getInstance()
    {
        if (_instance == null)
            _instance = new MockUserCommentRepository();

        return _instance;
    }

    private MockUserCommentRepository() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            cat1List.add(new UserComment(3.5f, "Hello World1", Uri.parse("jsd94"), sdf.parse("2014/10/29 18:10:45")));

            cat2List.add(new UserComment(4f, "Hello World2", Uri.parse("jsd94"), sdf.parse("2014/10/29 18:10:45")));

            cat3List.add(new UserComment(5f, "Hello World3", Uri.parse("jsd94"), sdf.parse("2014/10/29 18:10:45")));
        }
        catch (ParseException e) {
        }
    }

    private static List<UserComment> cat1List = new ArrayList<>();

    private static List<UserComment> cat2List = new ArrayList<>();

    private static List<UserComment> cat3List = new ArrayList<>();

    private static Map<Uri, List<UserComment>> newCatDict = new HashMap<>();

    @Override
    public Iterable<UserComment> getUserComments(final Uri catId) {
        switch (catId.toString())
        {
            case MockCatProfileFetcher.CATPROFILE1:
                return new Iterable<UserComment>() {
                    @Override
                    public Iterator<UserComment> iterator() {
                        return new CommentIterator(cat1List);
                    }
                };
            case MockCatProfileFetcher.CATPROFILE2:
                return new Iterable<UserComment>() {
                    @Override
                    public Iterator<UserComment> iterator() {
                        return new CommentIterator(cat2List);
                    }
                };
            case MockCatProfileFetcher.CATPROFILE3:
                return new Iterable<UserComment>() {
                    @Override
                    public Iterator<UserComment> iterator() {
                        return new CommentIterator(cat3List);
                    }
                };
            default:
                return new Iterable<UserComment>() {
                    @Override
                    public Iterator<UserComment> iterator() {
                        if (! newCatDict.containsKey(catId))
                            return new CommentIterator(new ArrayList<UserComment>());

                        return new CommentIterator(newCatDict.get(catId));
                    }
                };
        }
    }

    @Override
    public boolean addComment(Uri catId, UserComment comment)
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
                    newCatDict.put(catId, new ArrayList<UserComment>());

                newCatDict.get(catId).add(comment);
        }

        return true;
    }

    private class CommentIterator implements Iterator<UserComment>
    {
        List<UserComment> list;
        int position;

        public CommentIterator(List<UserComment> l)
        {
            list = l;
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return list.size() > position;
        }

        @Override
        public UserComment next() {
            return list.get(position++);
        }
    }
}
