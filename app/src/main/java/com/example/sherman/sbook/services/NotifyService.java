package com.example.sherman.sbook.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.sherman.sbook.R;
import com.example.sherman.sbook.activities.BookDetailActivity;
import com.example.sherman.sbook.constants.Constants;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.models.Book;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import static com.example.sherman.sbook.R.drawable.book;
import static com.example.sherman.sbook.R.drawable.notification;

/**
 * Created by kenp on 29/10/2016.
 */

public class NotifyService extends IntentService {
    private static final String TAG = "NotifyService";

    private static int notificationId = 0;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference categoryRef = database.getReference(Database.CATEGORIES + "nt0WxiJNyRQ9r1OXPNIE7wNr4p12/books");
    private DatabaseReference bookRef = database.getReference().child(Database.BOOKS);
    private DatabaseReference notificationRef = database.getReference().child(Database.USERS);
    private DatabaseReference interestingRef = database.getReference().child(Database.USERS);

    ArrayList<String> currentInterestingBook = new ArrayList<>();
    private String userId;


    public NotifyService() {
        super("Notify new book service");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        getUserId();


        interestingRef.child(userId).child("interests").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                currentInterestingBook.add(dataSnapshot.getValue().toString());
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

    @Override
    protected void onHandleIntent(Intent intent) {

        bookRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Book newBook = dataSnapshot.getValue(Book.class);

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
            arr[i] = fromVietnameseToUnicode(arr[i]);
        }

        return new String(arr);
    }

    private char fromVietnameseToUnicode(char c) {
        switch (c) {
            case 'á':
            case 'à':
            case 'ả':
            case 'ã':
            case 'ạ':

            case 'ă':
            case 'ắ':
            case 'ằ':
            case 'ẳ':
            case 'ẵ':
            case 'ặ':

            case 'â':
            case 'ấ':
            case 'ầ':
            case 'ẩ':
            case 'ẫ':
            case 'ậ':
                return 'a';

            case 'é':
            case 'è':
            case 'ẻ':
            case 'ẽ':
            case 'ẹ':

            case 'ê':
            case 'ế':
            case 'ề':
            case 'ể':
            case 'ễ':
            case 'ệ':
                return 'e';

            case 'đ':
                return 'd';

            case 'í':
            case 'ì':
            case 'ĩ':
            case 'ỉ':
            case 'ị':
                return 'i';

            case 'ó':
            case 'ò':
            case 'õ':
            case 'ỏ':
            case 'ọ':

            case 'ô':
            case 'ố':
            case 'ồ':
            case 'ổ':
            case 'ỗ':
            case 'ộ':

            case 'ơ':
            case 'ớ':
            case 'ờ':
            case 'ở':
            case 'ỡ':
            case 'ợ':
                return 'o';

            case 'ư':
            case 'ứ':
            case 'ừ':
            case 'ử':
            case 'ữ':
            case 'ự':

            case 'ú':
            case 'ù':
            case 'ủ':
            case 'ũ':
            case 'ụ':
                return 'u';

            default:
                return c;
        }
    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Get user id
    public String getUserId() {
        SharedPreferences sp = getSharedPreferences(Constants.RefName, Context.MODE_PRIVATE);
        userId = sp.getString(Constants.UserID, "");
        return userId;
    }
}
