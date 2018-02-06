package com.cattracker.joe.cattracker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by JOSEPH on 8/24/2017.
 */

public class CommentPostView extends GridLayout {
    TextView userName;
    TextView comment;
    RatingBar starRating;

    public CommentPostView(Context context)
    {
        super(context);

        init();
    }

    public CommentPostView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public String getUserName()
    {
        return userName.getText().toString();
    }

    public void setUserName(String u, OnClickListener listener)
    {
        userName.setText(u);
        userName.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        userName.setOnClickListener(listener);

        invalidate();
        requestLayout();
    }

    public void setComment(String c)
    {
        comment.setText(c);
        invalidate();
        requestLayout();
    }

    public void setStarRating(float f)
    {
        starRating.setRating(f);
        invalidate();
        requestLayout();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.comment, this);

        userName = findViewById(R.id.commentUser);
        comment = findViewById(R.id.commentMessage);
        starRating = findViewById(R.id.commentRating);
    }
}
