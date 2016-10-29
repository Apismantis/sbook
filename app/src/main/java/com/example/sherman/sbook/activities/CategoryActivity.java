package com.example.sherman.sbook.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.BookRecyclerViewAdapter;
import com.example.sherman.sbook.adapters.RecycleViewDecoration;
import com.example.sherman.sbook.constants.Constants;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherman on 10/30/2016.
 */

public class CategoryActivity extends AppCompatActivity {
    private Context mContext;
    private View rootLayout;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private RecyclerView recyclerView;
    private BookRecyclerViewAdapter rcAdapter;
    public ProgressDialog progressDialog;
    // Categories Ref
    DatabaseReference CategoriesRef = FirebaseDatabase
            .getInstance()
            .getReference()
            .child(Database.CATEGORIES);

    // Book Ref
    DatabaseReference BookRef = FirebaseDatabase
            .getInstance()
            .getReference()
            .child(Database.BOOKS); // books

    String userId;
    List<Book> Books = new ArrayList<>();
    List<String> BookLoaded = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        // Init recyclerview
        initRecyclerView();

        // Get user ID
        userId = getUserId();
        if (userId.equals(""))
            return;

        // User Ref
        DatabaseReference UserRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Database.USERS)
                .child(userId)
                .child("interesting");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[] categories = getCategories(dataSnapshot.getValue().toString());
                for (int i = 0; i < categories.length; i++) {
//                    getBooks(categories[i]);

                    if (i == categories.length - 1) {
//                        loadOtherBooks();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public String[] getCategories(String intersting) {
        Log.d("TAG", "Categories: " + intersting);
        return intersting.split(",");
    }
    private void initRecyclerView() {
        recyclerView = (RecyclerView) rootLayout.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        rcAdapter = new BookRecyclerViewAdapter(mContext, Books);

        int spacingInPixels = 4;
        if (Integer.valueOf(android.os.Build.VERSION.SDK) >= 21)
            spacingInPixels = 16;

        recyclerView.addItemDecoration(new RecycleViewDecoration(spacingInPixels));
        recyclerView.setAdapter(rcAdapter);
    }
    // Get user id
    public String getUserId() {
        SharedPreferences sp = mContext.getSharedPreferences(Constants.RefName, Context.MODE_PRIVATE);
        return sp.getString(Constants.UserID, "");
    }
}
