package com.cattracker.joe.cattracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by JOSEPH on 8/17/2017.
 */

class MockMapProvider implements IProvideMap {
    @Override
    public Object getMap(Activity context) {
        ImageView image = context.findViewById(R.id.fake_map);
        return image;
    }

    @Override
    public void populateMap(final Activity context, Object map) {
        ImageView image = (ImageView) map;

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CatProfile.class);

                Uri.Builder builder = new Uri.Builder();

                builder.path(MockCatProfileFetcher.CATPROFILE1);

                intent.putExtra("profile", builder.build());

                context.startActivity(intent);
            }
        });
    }
}
