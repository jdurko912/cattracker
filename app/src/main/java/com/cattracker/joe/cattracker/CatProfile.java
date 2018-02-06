package com.cattracker.joe.cattracker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.List;

public class CatProfile extends AppCompatActivity {

    IFetchCatProfiles catProfilesFetcher;
    Uri profileUri;
    ILocationGrabber locationGrabber = new MockLocationGrabber();
    ICatOwnershipRepository ownerRepo;
    AlertDialog commentDialog;
    AlertDialog nameDialog;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_profile);

        Bundle extras = getIntent().getExtras();
        profileUri =  extras.getParcelable("profile");

        catProfilesFetcher = new MockCatProfileFetcher();
        ownerRepo = new MockCatOwnerRepository();

        session = new SessionManager(this);

        TextView ownership = (TextView) findViewById(R.id.cat_ownership);
        final CatLover owner = ownerRepo.getCatOwner(profileUri);
        if ( owner == null)
        {
            ownership.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CatLover newOwner = session.getUserDetails();
                    TextView textView = (TextView) view;
                    textView.setText("Owner:  " + newOwner.screenName.toString());
                    ownerRepo.setCatOwner(profileUri, newOwner);

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            goToUserProfile(newOwner.screenName);
                        }
                    });
                }
            });
        }
        else
        {
            ownership.setText(owner.screenName.toString());
            ownership.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToUserProfile(owner.screenName);
                }
            });
        }

        fillOutStars();

        setTopCatName();

        ImageView profilePicture = (ImageView) findViewById(R.id.cat_profile);
        profilePicture.setImageBitmap(catProfilesFetcher.getCatProfilePicture(this, profileUri));

        // For now, the map will just be a button to one picture of the cat
        ImageView catMapView = (ImageView) findViewById(R.id.fake_cat_map);
        catMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCatPictureFragment();
            }
        });

        populateCommentSection();

        Button addCommentButton = (Button) findViewById(R.id.add_new_comment_btn);
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddCommentFragment();
            }
        });

        populateNameSection();

        Button addNameButton = (Button) findViewById(R.id.add_name_button);
        addNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddNameFragment();
            }
        });
    }

    private void setTopCatName()
    {
        TextView catProfileName = (TextView) findViewById(R.id.cat_name);
        catProfileName.setText(catProfilesFetcher.getTopCatName(profileUri));
    }

    private void goToUserProfile(Uri screenName)
    {
        Intent intent = new Intent(this, CatLoverProfile.class);

        intent.putExtra("profile", screenName);

        startActivity(intent);
    }

    private void fillOutStars()
    {
        float f = catProfilesFetcher.getOverallCatRating(profileUri);

        Bitmap emptyStar = BitmapFactory.decodeResource(getResources(),
                R.drawable.empty_star);
        Bitmap star = BitmapFactory.decodeResource(getResources(),
                R.drawable.fullstar);
        Bitmap halfStar = BitmapFactory.decodeResource(getResources(),
                R.drawable.halfstar);

        ImageView firstStar = (ImageView) findViewById(R.id.star1);
        if (f >= 1.0)
        {
            firstStar.setImageBitmap(star);
        }
        else if(f >= 0.5)
        {
            firstStar.setImageBitmap(halfStar);
        }
        else
        {
            firstStar.setImageBitmap(emptyStar);
        }

        ImageView secondStar = (ImageView) findViewById(R.id.star2);
        if (f >= 2.0)
        {
            secondStar.setImageBitmap(star);
        }
        else if(f >= 1.5)
        {
            secondStar.setImageBitmap(halfStar);
        }
        else
        {
            secondStar.setImageBitmap(emptyStar);
        }

        ImageView thirdStar = (ImageView) findViewById(R.id.star3);
        if (f >= 3.0)
        {
            thirdStar.setImageBitmap(star);
        }
        else if(f >= 2.5)
        {
            thirdStar.setImageBitmap(halfStar);
        }
        else
        {
            thirdStar.setImageBitmap(emptyStar);
        }


        ImageView fourthStar = (ImageView) findViewById(R.id.star4);
        if (f >= 4.0)
        {
            fourthStar.setImageBitmap(star);
        }
        else if(f >= 3.5)
        {
            fourthStar.setImageBitmap(halfStar);
        }
        else
        {
            fourthStar.setImageBitmap(emptyStar);
        }


        ImageView fifthStar = (ImageView) findViewById(R.id.star5);
        if (f >= 5.0)
        {
            fifthStar.setImageBitmap(star);
        }
        else if(f >= 4.5)
        {
            fifthStar.setImageBitmap(halfStar);
        }
        else
        {
            fifthStar.setImageBitmap(emptyStar);
        }
    }

    private void populateCommentSection()
    {
        LinearLayout commentSection = (LinearLayout) findViewById(R.id.commentSection);
        commentSection.removeAllViews();
        List<UserComment> userComments = catProfilesFetcher.getNextFiveUserComments(profileUri);

        for (final UserComment comment : userComments)
        {
            CommentPostView commentView = new CommentPostView(commentSection.getContext());
            commentView.setUserName(comment.userId.toString(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToUserProfile(comment.userId);
                }
            });
            commentView.setComment(comment.comment);
            commentView.setStarRating(comment.starRating);

            commentSection.addView(commentView);
        }
    }

    private void populateNameSection()
    {
        TableLayout voteTable = (TableLayout) findViewById(R.id.name_vote_table);
        voteTable.removeViews(1, voteTable.getChildCount() - 1);

        List<CatName> catNames = catProfilesFetcher.getNextFiveCatNames(profileUri);
        final ICatNameRepository nameRepo = MockCatNameRepository.getInstance();

        for (final CatName catName : catNames)
        {
            final VoteRow row = new VoteRow(this);
            row.setUserName(catName.name);
            row.setVoteNumber(catName.numberOfVotes);
            // TODO:  Would probably be better to have this completely refresh after voting
            row.setVoteStatus(catName.voted, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameRepo.addVote(profileUri, row.getCatName());
                    row.setVoteNumber(catName.numberOfVotes);
                    row.setVoteStatus(true, null);
                    setTopCatName();
                }
            });

            row.setPadding(5, 5, 5, 5);
            row.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));

            voteTable.addView(row, new TableLayout.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT));
            voteTable.setColumnStretchable(1, true);
        }
    }

    private void createCatPictureFragment()
    {
        // For now, just get the latest picture
        List<CatSnap> catSnaps = catProfilesFetcher.getCatPictures(this, profileUri);
        CatSnap catSnap = catSnaps.get(catSnaps.size() - 1);

        // Create Fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.fragment_cat_picture, null);

        ImageView picture = view.findViewById(R.id.cat_picture);
        picture.setImageBitmap(catSnap.image);

        TextView catName = view.findViewById(R.id.cat_picture_name);
        catName.setText("Cat:  " + catProfilesFetcher.getTopCatName(profileUri));

        TextView location = view.findViewById(R.id.cat_picture_location);
        String coordinateString = locationGrabber.getLocalCoordinateString(catSnap.location);
        location.setText("Location:  " + coordinateString.substring(0, coordinateString.indexOf('\n')));

        TextView date = view.findViewById(R.id.cat_picture_date);
        date.setText("Taken:  " + catSnap.dateTaken.toString());

        TextView shelter = view.findViewById(R.id.cat_picture_shelter);
        String shelterCoordinates = locationGrabber.getShelterLocationString(catSnap.humaneSociety);
        shelter.setText("Nearest Shelter:  " + shelterCoordinates.substring(0, shelterCoordinates.indexOf('\n')));

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createAddCommentFragment()
    {
        // Create Fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View commentView = getLayoutInflater().inflate(R.layout.fragment_comment, null);

        Button confirmButton = commentView.findViewById(R.id.comment_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IUserCommentRepository repo = MockUserCommentRepository.getInstance();

                RatingBar rating = commentView.findViewById(R.id.comment_rating);
                TextView comment = commentView.findViewById(R.id.comment_text);

                //TODO:  When user accounts are in, DO NOT use profileUri for submitter
                boolean submitted = repo.addComment(profileUri, new UserComment(rating.getRating(), comment.getText().toString(), session.getUserDetails().screenName, Calendar.getInstance().getTime()));

                String toastText = submitted ? "Success" : "Error";
                Toast.makeText(CatProfile.this, toastText, Toast.LENGTH_SHORT).show();
                commentDialog.cancel();
                populateCommentSection();
                fillOutStars();
            }
        });

        builder.setView(commentView);

        commentDialog = builder.create();
        commentDialog.show();
    }

    private void createAddNameFragment()
    {
        // Create Fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View nameView = getLayoutInflater().inflate(R.layout.fragment_name, null);

        Button confirmButton = nameView.findViewById(R.id.name_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ICatNameRepository repo = MockCatNameRepository.getInstance();

                TextView comment = nameView.findViewById(R.id.new_name_text);

                boolean submitted = repo.addName(profileUri, new CatName(comment.getText().toString(), 1, true));

                String toastText = submitted ? "Success" : "Error";
                Toast.makeText(CatProfile.this, toastText, Toast.LENGTH_SHORT).show();
                nameDialog.cancel();
                populateNameSection();
                setTopCatName();
            }
        });

        builder.setView(nameView);

        nameDialog = builder.create();
        nameDialog.show();
    }
}
