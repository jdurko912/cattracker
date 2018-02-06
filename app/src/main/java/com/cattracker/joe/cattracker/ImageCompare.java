package com.cattracker.joe.cattracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

public class ImageCompare extends AppCompatActivity {

    ILocationGrabber locationGrabber;
    IGrabComparableImages imageGrabber;
    SessionManager session;
    IUserLoginRepository userRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_compare);

        byte[] byteArray = grabImageFromIntent();

        locationGrabber = new MockLocationGrabber();
        imageGrabber = new MockComparableImageGrabber();
        userRepo = MockUserLoginRepository.getInstance(this);
        session = new SessionManager(this);

        Bitmap b = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        final Bitmap bmp = Bitmap.createScaledBitmap(b, 474, 421, false);
        ImageView takenPicture = (ImageView) findViewById(R.id.takenPicture);
        takenPicture.setImageBitmap(bmp);

        TextView gpsLocation = (TextView) findViewById(R.id.gpsLocationInfo);

        final Location local = locationGrabber.getLocalCoordinates();
        final Location shelter = locationGrabber.getShelterLocation();

        gpsLocation.setText("Location:  " + locationGrabber.getLocalCoordinateString(local));

        TextView shelterLocation = (TextView) findViewById(R.id.shelterLocationInfo);
        shelterLocation.setText("Nearest Shelter:  \n" + locationGrabber.getShelterLocationString(shelter));

        LinearLayout compareImageView = (LinearLayout) findViewById(R.id.compareImageLinearLayout);
        for(Pair<Bitmap, Uri> pair : imageGrabber.getFiveRelevantImages(this)) {
            Bitmap image = pair.first;
            Uri profile = pair.second;

            ImageView imageView = new ImageView(this);
            imageView.setImageBitmap(image);
            imageView.setMaxHeight(R.dimen.compare_image_height);
            imageView.setMaxWidth(R.dimen.compare_image_width);
            imageView.setTag(profile);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToCatProfile(view, bmp, local, shelter, Calendar.getInstance().getTime());
                }
            });

            compareImageView.addView(imageView);
        }

        Button cancelBtn = (Button) findViewById(R.id.imageCompareCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCamera();
            }
        });

        Button newCatBtn = (Button) findViewById(R.id.new_cat_button);
        newCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewCatProfile(bmp, local, shelter, Calendar.getInstance().getTime());
            }
        });
    }

    private void goToCamera()
    {
        finish();
    }

    private void goToNewCatProfile(Bitmap bmp, Location local, Location shelter, Date time)
    {
        CatLover userProfile = session.getUserDetails();
        userProfile.picturesTaken++;
        userRepo.updateUserProfile(userProfile);

        Intent intent = new Intent(this, CatProfile.class);

        Uri profile = MockCatPictureRepository.getInstance(this).getNextCatId();
        MockCatPictureRepository.getInstance(this).addPicture(profile, new CatSnap(bmp, local, profile, shelter, time));

        intent.putExtra("profile", profile);

        startActivity(intent);
    }

    private void goToCatProfile(View view, Bitmap bmp, Location local, Location shelter, Date time)
    {
        CatLover userProfile = session.getUserDetails();
        userProfile.picturesTaken++;
        userRepo.updateUserProfile(userProfile);

        Intent intent = new Intent(this, CatProfile.class);

        Uri profile = (Uri) view.getTag();

        MockCatPictureRepository.getInstance(this).addPicture(profile, new CatSnap(bmp, local, profile, shelter, time));

        intent.putExtra("profile", profile);

        startActivity(intent);
    }

    private byte[] grabImageFromIntent() {
        Bundle extras = getIntent().getExtras();
        String imageFileName = extras.getString("picture");

        Bitmap image = BitmapFactory.decodeFile(imageFileName);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }
}


