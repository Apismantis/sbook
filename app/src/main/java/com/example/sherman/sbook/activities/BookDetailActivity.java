package com.example.sherman.sbook.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    private RelativeLayout btnCall;

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
        loadingDialog.setMessage(getString(R.string.please_wait));
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

        btnCall = (RelativeLayout) findViewById(R.id.rlContactOwner);
    }

    private void getDataFromFirebase() {
        bookRef = database.getReference().child(Database.BOOKS).child(bookId);
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                book = dataSnapshot.getValue(Book.class);

                if (book != null) {
                    getUserInfo(book.getOwner());
                    updateBookInfoUI();
                } else {
                    if (loadingDialog.isShowing()) {
                        loadingDialog.hide();
                    }

                    Toast.makeText(BookDetailActivity.this, R.string.error_when_load_book_data, Toast.LENGTH_SHORT).show();
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
                    if (loadingDialog.isShowing()) {
                        loadingDialog.hide();
                    }

                    Toast.makeText(BookDetailActivity.this, R.string.error_when_load_user_data, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            case R.id.rlContactOwner:
                callOwner();
                break;
        }
    }

    private void callOwner() {
        if (owner != null) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + owner.getPhoneNumber()));

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            return;

            startActivity(intent);
        }
    }
}
