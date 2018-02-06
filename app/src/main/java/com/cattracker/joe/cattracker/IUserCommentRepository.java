package com.cattracker.joe.cattracker;

import android.net.Uri;

/**
 * Created by JOSEPH on 8/22/2017.
 */

interface IUserCommentRepository {
    boolean addComment(Uri catId, UserComment comment);
    Iterable<UserComment> getUserComments(Uri catId);
}
