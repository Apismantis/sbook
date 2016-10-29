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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.BookRecyclerViewAdapter;
import com.example.sherman.sbook.adapters.RecycleViewDecoration;
import com.example.sherman.sbook.constants.Constants;
import com.example.sherman.sbook.constants.Database;
import com.example.sherman.sbook.models.Book;
import com.example.sherman.sbook.models.Category;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherman on 10/30/2016.
 */

public class CategoryActivity extends AppCompatActivity {
    private static final String TAG = "DEBUG";
    private Context mContext;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private RecyclerView recyclerView;
    private BookRecyclerViewAdapter rcAdapter;

    // Book Ref
    DatabaseReference BookRef = FirebaseDatabase
            .getInstance()
            .getReference()
            .child(Database.BOOKS); // books

    String userId;
    List<Book> Books = new ArrayList<>();
    private ProgressDialog progressDialog;

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);
        setStatusBarTranslucent(true);
        Intent intent = getIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Category category = (Category) intent.getSerializableExtra("category");
        // Init recyclerview
        initRecyclerView();
        Picasso.with(mContext)
                .load(category.getBackgound())
                .into((ImageView) findViewById(R.id.background));
        TextView tvName = (TextView)findViewById(R.id.tvCategoryName);
        TextView tvDesr = (TextView)findViewById(R.id.tvDescription);
        tvName.setText(category.getName());
        tvDesr.setText(category.getDesr());
        getBooks(category.getId());
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
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

    public void getBooks(final String category) {
        DatabaseReference CategoriesRef = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Database.CATEGORIES)
                .child(category)
                .child(Database.BOOKS);

        CategoriesRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Hide dialog
                progressDialog.dismiss();

                // Duyet qua tat ca sach
                for (DataSnapshot book : dataSnapshot.getChildren()) {
                    String BookId = book.getValue().toString();
                    Log.d(TAG, "Category: " + category + ", BookId: " + BookId);

                    loadBookInfo(BookId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    // Load book
    private void loadBookInfo(String bookId) {

        BookRef.child(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                Books.add(book);
                rcAdapter.notifyDataSetChanged();

                Log.d(TAG, book.getTitle());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
