package com.example.sherman.sbook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.CategoryRecyclerViewAdapter;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.models.Category;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Category> listViewItems;
    private Context context;
    private CategoryRecyclerViewAdapter adapter;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        context = view.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        listViewItems = new ArrayList<>();

        adapter = new CategoryRecyclerViewAdapter(context, listViewItems);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData() {
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Database.CATEGORIES);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String id = dataSnapshot.getKey();
                Log.d("ABC", id);
                List<String> listBook = new ArrayList<>();

                for (DataSnapshot book : dataSnapshot.child("books").getChildren()) {
                    listBook.add(book.getValue().toString());
                }
                listViewItems.add(new Category(id, listBook, dataSnapshot.child("coverUrl").getValue().toString(), dataSnapshot.child("description").getValue().toString(), dataSnapshot.child("name").getValue().toString()));
                adapter.notifyDataSetChanged();
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
}
