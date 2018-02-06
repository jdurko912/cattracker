package com.cattracker.joe.cattracker;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final int _cameraRequest = 1;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    SessionManager session;
    Button mapButton;
    Button cameraButton;
    ImageButton logoutButton;
    ImageButton settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        session = new SessionManager(this);

        /**
         * Call this function whenever you want to check user login
         * This will redirect user to LoginActivity is he is not
         * logged in
         * */
        session.checkLogin();

        settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleEditSettings();
            }
        });

        mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleMapRequest();
            }
        });

        cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        logoutButton = (ImageButton) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            handleCameraRequest();
        } else {
            Log.e("onActivityResult",
                    String.format("Request failed - resultCode: %d, requestCode:%d", resultCode, requestCode));
        }
    }

    protected void handleEditSettings()
    {
        Uri profile = session.getUserDetails().screenName;

        Intent intent = new Intent(this, CatLoverProfile.class);

        intent.putExtra("profile", profile);

        startActivity(intent);
    }

    protected void handleMapRequest()
    {
        Intent intent = new Intent(this, MapActivity.class);

        startActivity(intent);
    }

    protected void handleCameraRequest(){
        File imageFile = getImageFile();
        String imageFileName = imageFile.getAbsolutePath();

        Intent intent = new Intent(this, ImageCompare.class);

        intent.putExtra("picture", imageFileName);

        startActivity(intent);
    }

    private void takePicture() {
        verifyStoragePermissions(this);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File imageFile = getImageFile();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));


        startActivityForResult(intent, _cameraRequest);
    }

    private File getImageFile(){
        File targetDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        assureThatDirectoryExist(targetDir);
        File imageFile = new File(targetDir, "tempPic.jpg");

        return imageFile;
    }

    // Emulators don't always have the standard folder created, so create if necessary
    private void assureThatDirectoryExist(File directory){
        if(!directory.exists())
            directory.mkdirs();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
