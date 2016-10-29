package com.example.sherman.sbook.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.NotificationAdapter;
import com.example.sherman.sbook.constants.Constants;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.models.Book;
import com.example.sherman.sbook.models.Notification;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.sherman.sbook.R.drawable.notification;

/**
 * Created by kenp on 30/10/2016.
 */

public class NotificationFragment extends Fragment {


    private RecyclerView rvNotifications;
    private NotificationAdapter adapter;

    private List<Book> notificationBooks = new ArrayList<Book>();

    private DatabaseReference notificationRef = FirebaseDatabase.getInstance().getReference().child(Database.USERS);
    private DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child(Database.BOOKS);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvNotifications = (RecyclerView) view.findViewById(R.id.rvNotification);
        adapter = new NotificationAdapter(getActivity(), notificationBooks);
        rvNotifications.setAdapter(adapter);
        rvNotifications.setLayoutManager(new LinearLayoutManager(getActivity()));


        notificationRef.child(getUserId()).child("notifications").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String bookId = dataSnapshot.getKey().toString();
                bookRef.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        notificationBooks.add(0, dataSnapshot.getValue(Book.class));
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

    // Get user id
    public String getUserId() {
        SharedPreferences sp = getActivity().getSharedPreferences(Constants.RefName, Context.MODE_PRIVATE);
        return sp.getString(Constants.UserID, "");
    }
}
