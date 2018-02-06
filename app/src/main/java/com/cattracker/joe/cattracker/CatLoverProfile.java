package com.cattracker.joe.cattracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CatLoverProfile extends AppCompatActivity {

    CatLover catLover;
    IUserLoginRepository userRepo;
    SessionManager session;

    // View Id's
    private static final int PASSWORD_ID = 0;
    private static final int FIRSTNAME_ID = 1;
    private static final int LASTNAME_ID = 2;
    private static final int EMAIL_ID = 3;
    private static final int ADDRESS_ID = 4;
    private static final int PHONE_ID = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_lover_profile);

        session = new SessionManager(this);

        Bundle extras = getIntent().getExtras();
        Uri profileUri =  extras.getParcelable("profile");

        userRepo = MockUserLoginRepository.getInstance(this);
        catLover = userRepo.getCatLover(profileUri);

        // Set Profile picture
        ImageView profilePic = (ImageView) findViewById(R.id.lover_profile);
        profilePic.setImageBitmap(catLover.profilePicture);

        // Set name
        TextView name = (TextView) findViewById(R.id.lover_name);
        name.setText(catLover.screenName.toString());

        // Set rating
        String ratingString = fillOutStars(catLover.getRating());

        // Set information
        TableLayout information = (TableLayout) findViewById(R.id.lover_information);
        TextView rating = (TextView) findViewById(R.id.lover_rating_name);
        rating.setText(ratingString);

        boolean thisUser = catLover.screenName.equals(session.getUserDetails().screenName);
        if (thisUser)
        {
            TableRow passwordRow = new TableRow(this);
            TextView pwLabel = new TextView(this);
            pwLabel.setText("Password:  ");

            EditText password = new EditText(this);
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            password.setText(catLover.password);
            password.setId(PASSWORD_ID);
            passwordRow.addView(pwLabel);
            passwordRow.addView(password);
            information.addView(passwordRow);
        }

        final TextView firstName = addInformationRow(information, thisUser, "First Name:  ", catLover.firstName, FIRSTNAME_ID);
        final TextView lastName = addInformationRow(information, thisUser, "Last Name:  ", catLover.lastName, LASTNAME_ID);
        final TextView email = addInformationRow(information, thisUser, "Email:  ", catLover.emailAddress, EMAIL_ID);
        final TextView address = addInformationRow(information, thisUser, "Address:  ", catLover.streetAddress, ADDRESS_ID);
        final TextView phone = addInformationRow(information, thisUser, "Phone Number:  ", catLover.phoneNumber, PHONE_ID);

        if (thisUser)
        {
            Button updateButn = new Button(this);
            updateButn.setText("Update Information");
            updateButn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CatLover newCatLover = new CatLover(catLover.screenName, catLover.password);

                    TextView pwView = (TextView) findViewById(PASSWORD_ID);
                    newCatLover.profilePicture = catLover.profilePicture;
                    newCatLover.password = pwView.getText().toString();
                    newCatLover.firstName = firstName.getText().toString().isEmpty() ? null : firstName.getText().toString();
                    newCatLover.lastName = lastName.getText().toString().isEmpty() ? null : lastName.getText().toString();
                    newCatLover.emailAddress = email.getText().toString().isEmpty() ? null : email.getText().toString();
                    newCatLover.streetAddress = address.getText().toString().isEmpty() ? null : address.getText().toString();
                    newCatLover.phoneNumber = phone.getText().toString().isEmpty() ? null : phone.getText().toString();

                    newCatLover.picturesTaken = catLover.picturesTaken;

                    userRepo.updateUserProfile(newCatLover);

                    finish();
                }
            });

            LinearLayout buttonView = (LinearLayout) findViewById(R.id.confirm_button_location);
            buttonView.addView(updateButn);
        }
    }

    private TextView addInformationRow(TableLayout information, boolean thisUser, String label, String value, int id)
    {
        if (value != null)
        {
            TableRow newRow = new TableRow(this);
            TextView labelView = new TextView(this);
            labelView.setText(label);

            TextView field;
            if (thisUser)
            {
                field = new EditText(this);
            }
            else
            {
                field = new TextView(this);
            }
            field.setText(value);
            field.setId(id);
            newRow.addView(labelView);
            newRow.addView(field);
            information.addView(newRow);

            return field;
        }
        else if (thisUser)
        {
            TableRow newRow = new TableRow(this);
            TextView labelView = new TextView(this);
            labelView.setText(label);

            TextView field = new EditText(this);

            field.setId(id);
            newRow.addView(labelView);
            newRow.addView(field);
            information.addView(newRow);

            return field;
        }

        return null;
    }

    private String fillOutStars(float rating)
    {
        Bitmap emptyStar = BitmapFactory.decodeResource(getResources(),
                R.drawable.empty_star);
        Bitmap star = BitmapFactory.decodeResource(getResources(),
                R.drawable.fullstar);
        Bitmap halfStar = BitmapFactory.decodeResource(getResources(),
                R.drawable.halfstar);

        String ratingString = "Cat-o-strophic";
        ImageView firstStar = (ImageView) findViewById(R.id.star1);
        if (rating >= 1.0)
        {
            firstStar.setImageBitmap(star);
        }
        else if(rating >= 0.5)
        {
            firstStar.setImageBitmap(halfStar);
        }
        else
        {
            firstStar.setImageBitmap(emptyStar);
        }

        ImageView secondStar = (ImageView) findViewById(R.id.star2);
        if (rating >= 2.0)
        {
            secondStar.setImageBitmap(star);
            ratingString = "Cat-prentice";
        }
        else if(rating >= 1.5)
        {
            secondStar.setImageBitmap(halfStar);
        }
        else
        {
            secondStar.setImageBitmap(emptyStar);
        }

        ImageView thirdStar = (ImageView) findViewById(R.id.star3);
        if (rating >= 3.0)
        {
            thirdStar.setImageBitmap(star);
            ratingString = "Cat-tastic";
        }
        else if(rating >= 2.5)
        {
            thirdStar.setImageBitmap(halfStar);
        }
        else
        {
            thirdStar.setImageBitmap(emptyStar);
        }


        ImageView fourthStar = (ImageView) findViewById(R.id.star4);
        if (rating >= 4.0)
        {
            fourthStar.setImageBitmap(star);
            ratingString = "Every day is Caturday";
        }
        else if(rating >= 3.5)
        {
            fourthStar.setImageBitmap(halfStar);
        }
        else
        {
            fourthStar.setImageBitmap(emptyStar);
        }


        ImageView fifthStar = (ImageView) findViewById(R.id.star5);
        if (rating >= 5.0)
        {
            fifthStar.setImageBitmap(star);
            ratingString = "Cat Master";
        }
        else if(rating >= 4.5)
        {
            fifthStar.setImageBitmap(halfStar);
        }
        else
        {
            fifthStar.setImageBitmap(emptyStar);
        }

        return ratingString;
    }
}
