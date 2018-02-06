package com.cattracker.joe.cattracker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by JOSEPH on 8/24/2017.
 */

public class VoteRow extends TableRow {
    TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
    LayoutParams innerParams1 = new TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, Gravity.CENTER);
    LayoutParams innerParams2 = new TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, Gravity.CENTER);
    LayoutParams innerParams3 = new TableRow.LayoutParams(0, LayoutParams.MATCH_PARENT, Gravity.CENTER);

    TextView catName;
    TextView numberOfVotes;
    CheckBox voted;

    public VoteRow(Context context)
    {
        super(context);

        init();
    }

    public VoteRow(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public String getCatName()
    {
        return catName.getText().toString();
    }

    public void setUserName(String u)
    {
        catName.setText(u);
        invalidate();
        requestLayout();
    }

    public void setVoteNumber(int v)
    {
        numberOfVotes.setText(String.valueOf(v));
        invalidate();
        requestLayout();
    }

    public void setVoteStatus(boolean v, OnClickListener listener)
    {
        voted.setActivated(v);

        if (v)
        {
            voted.setChecked(true);
            voted.setEnabled(false);
        }
        else
            voted.setOnClickListener(listener);

        invalidate();
        requestLayout();
    }

    private void init() {
        //LayoutInflater inflater = LayoutInflater.from(getContext());
        //inflater.inflate(R.layout.vote, this);

        setBackground(getContext().getDrawable(R.drawable.table_last_row));
        setWeightSum(1f);
        setLayoutParams(rowParams);

        catName = makeCatNameView(); //findViewById(R.id.vote_name);
        numberOfVotes = makeNumVoteView(); //findViewById(R.id.vote_number);
        voted = makeVotedToggleView(); //findViewById(R.id.vote_checkbox);

        addView(catName);
        addView(numberOfVotes);
        addView(voted);
    }

    private TextView makeCatNameView()
    {
        TextView name = new TextView(getContext());
        innerParams1.weight = 0.4f;
        innerParams1.column = 0;
        name.setLayoutParams(innerParams1);
        name.setBackground(getContext().getDrawable(R.drawable.table_cell));
        return name;
    }

    private TextView makeNumVoteView()
    {
        TextView name = new TextView(getContext());
        innerParams2.weight = 0.4f;
        innerParams2.column = 1;
        name.setLayoutParams(innerParams2);
        name.setBackground(getContext().getDrawable(R.drawable.table_cell));
        return name;
    }

    private CheckBox makeVotedToggleView()
    {
        CheckBox name = new CheckBox(getContext());
        innerParams3.weight = 0.2f;
        innerParams3.column = 2;
        name.setLayoutParams(innerParams3);
        return name;
    }
}
