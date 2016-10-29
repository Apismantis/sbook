package com.example.sherman.sbook.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.constants.Constants;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.models.Book;
import com.example.sherman.sbook.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String bookId;
    private Book book;
    private User owner;

    private ImageView ivCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvPublisher;

    private ImageView ivAvatar;
    private TextView tvFullName;
    private TextView tvInteresting;

    private Button btnCall;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference bookRef;
    DatabaseReference ownerRef;

    ImageLoader imageLoader;
    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        showLoadingDialog();

        imageLoader = ImageLoader.getInstance();

        setupViews();

        Intent intent = getIntent();
        bookId = intent.getStringExtra(Constants.bookId);
        getDataFromFirebase();
    }

    private void showLoadingDialog() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("Loading book data");
        loadingDialog.setMessage("Please wait! Just a moment...");
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    private void setupViews() {
        ivCover = (ImageView) findViewById(R.id.ivBookCover);
        tvTitle = (TextView) findViewById(R.id.tvBookTitle);
        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvPublisher = (TextView) findViewById(R.id.tvPublisher);

        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);
        tvFullName = (TextView) findViewById(R.id.tvUserFullname);
        tvInteresting = (TextView) findViewById(R.id.tvUserInteresting);

        btnCall = (Button) findViewById(R.id.btnCall);
    }

    private void getDataFromFirebase() {
        bookRef = database.getReference(Database.BOOKS + bookId);
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                book = dataSnapshot.getValue(Book.class);

                if (book != null) {
                    getUserInfo(book.getOwner());
                    updateBookInfoUI();
                } else {
                    Toast.makeText(BookDetailActivity.this, "Cannot get book info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateBookInfoUI() {

        getSupportActionBar().setTitle(book.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvPublisher.setText(book.getPublisher());

        imageLoader.displayImage(book.getCoverUrl(), ivCover);
    }

    private void getUserInfo(String userId) {
        ownerRef = database.getReference(Database.USERS + userId);
        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                owner = dataSnapshot.getValue(User.class);

                if (owner != null) {
                    updateUserInfoUI();
                } else {
                    Toast.makeText(BookDetailActivity.this, "Cannot get user info", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUserInfoUI() {
        tvFullName.setText(owner.getFullName());
        tvInteresting.setText(owner.getInteresting());
        imageLoader.displayImage(owner.getAvatarUrl(), ivAvatar);
        btnCall.setOnClickListener(this);

        if (loadingDialog.isShowing()) {
            loadingDialog.hide();
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCall:
                callOwner();
                break;
        }
    }

    private void callOwner() {
        if (owner != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + owner.getPhoneNumber()));


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                return;
            }

            startActivity(intent);
        }
    }
}
