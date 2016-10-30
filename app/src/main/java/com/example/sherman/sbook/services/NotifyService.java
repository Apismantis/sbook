package com.example.sherman.sbook.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.sherman.sbook.R;
import com.example.sherman.sbook.activities.BookDetailActivity;
import com.example.sherman.sbook.constants.Constants;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.helper.NormalizeTextHelper;
import com.example.sherman.sbook.models.Book;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import me.xdrop.fuzzywuzzy.FuzzySearch;

/**
 * Created by kenp on 29/10/2016.
 */

public class NotifyService extends IntentService {
    private static final String TAG = "NotifyService";

    private static int notificationId = 0;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference bookRef = database.getReference().child(Database.BOOKS);
    private DatabaseReference notificationRef = database.getReference().child(Database.USERS);
    private DatabaseReference interestingRef = database.getReference().child(Database.USERS);

    ArrayList<String> currentInterestingBook = new ArrayList<>();
    private String userId;

    private boolean hasInterest = false;
    private boolean requestSent = false;

    public NotifyService() {
        super("Notify new book service");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        getUserId();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        interestingRef.child(userId).child("interests").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                currentInterestingBook.add(dataSnapshot.getValue().toString());
                Log.d(TAG, "onChildAdded: INTEREST " + dataSnapshot.getValue().toString());
                hasInterest = true;
                if (hasInterest && !requestSent) {
                    getBookData();
                    requestSent = true;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getBookData() {
        bookRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Book newBook = dataSnapshot.getValue(Book.class);
                Log.d(TAG, "onChildAdded: " + "NEw BOOK");

                if (newBook != null) {
                    newBook.setId(dataSnapshot.getKey());
                    handleNewBook(newBook);
                } else Log.e(TAG, "onChildAdded: Cannot parse new book.");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void handleNewBook(final Book newBook) {

        if (isMatchInterest(newBook)) {
            notificationRef.child(userId)
                    .child("notifications").child(newBook.getId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Log.d(TAG, "onDataChange: " + dataSnapshot.toString());

                            if (dataSnapshot.getValue() == null) {
                                notificationRef.child(userId)
                                        .child("notifications").child(newBook.getId())
                                        .setValue(false);

                                sendNotification(newBook);

                                return;
                            }

                            if (!(boolean) dataSnapshot.getValue()) {
                                sendNotification(newBook);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
    }

    private void sendNotification(final Book newBook) {

        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra(Constants.bookId, newBook.getId());
        intent.putExtra("updateStatus", true);

        int requestID = (int) System.currentTimeMillis();
        int flags = PendingIntent.FLAG_CANCEL_CURRENT;

        final PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent, flags);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bookCover = null;
                try {
                    bookCover = Glide.with(getApplicationContext()).load(newBook.getCoverUrl()).asBitmap().into(100, 300).get();
                    String message = newBook.getTitle() + " " + getString(R.string.had_just_been_added);
                    Notification.Builder notificationBuilder = new Notification.Builder(getApplicationContext())
                            .setContentTitle(getString(R.string.app_name))
                            .setContentText(message)
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .setStyle(new Notification.BigTextStyle().bigText(message))
                            .setTicker(message);

                    if (bookCover != null) {
                        notificationBuilder.setLargeIcon(bookCover);
                    }

                    Notification notification = notificationBuilder.build();


                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                    notificationManager.notify(900, notification);
                    Log.d(TAG, "sendNotification: sent");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private boolean isMatchInterest(Book book) {

        String normalizedNewBookTitle = normalizeBookTitle(book.getTitle());

        for (int i = 0; i < currentInterestingBook.size(); i++) {
            String normalizedTitle = normalizeBookTitle(currentInterestingBook.get(i));
            Log.d(TAG, "isMatchInterest: " + normalizedNewBookTitle + " - " + normalizedTitle);
            if (compareTitle(normalizedNewBookTitle, normalizedTitle)) {
                return true;
            }
        }

        return false;
    }

    private boolean compareTitle(String normalizedNewBookTitle, String normalizedTitle) {
        if (FuzzySearch.ratio(normalizedNewBookTitle, normalizedTitle) >= 50)
            return true;
        return false;
    }

    private String normalizeBookTitle(String title) {
        String normalized = title.toLowerCase();
        normalized = convertToUnicode(normalized);
        return normalized;
    }

    private String convertToUnicode(String lowercaseString) {
        char arr[] = lowercaseString.toCharArray();

        for (int i = 0; i < lowercaseString.length(); i++) {
            arr[i] = NormalizeTextHelper.fromVietnameseToUnicode(arr[i]);
        }

        return new String(arr);
    }

    // Get user id
    public String getUserId() {
        SharedPreferences sp = getSharedPreferences(Constants.RefName, Context.MODE_PRIVATE);
        userId = sp.getString(Constants.UserID, "");
        return userId;
    }
}
