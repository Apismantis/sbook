package com.example.sherman.sbook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.BookRecyclerViewAdapter;
import com.example.sherman.sbook.adapters.CategoryRecyclerViewAdapter;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.models.Book;
import com.example.sherman.sbook.models.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Category> listViewItems;
    private Context context;

    public CategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TAG", "onDataChange: "+ "name12");

        View view = inflater.inflate(R.layout.home_fragment, container, false);
        context = view.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        getListItemData();
        return view;
    }

    private List<Category> getListItemData() {
        Log.d("TAG", "onDataChange: "+ "name");
         listViewItems = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(Database.CATEGORIES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot category : dataSnapshot.getChildren()) {
                    String id = category.getKey();
                    Log.d("ABC", id);

                    listViewItems.add(new Category(category.child("name").getValue().toString(), null));
                    Log.d("TAG", "onDataChange: "+category.child("name"));

                }
               recyclerView.setAdapter(new CategoryRecyclerViewAdapter(context, listViewItems));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return listViewItems;
    }
}
