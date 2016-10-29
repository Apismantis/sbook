package com.example.sherman.sbook.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.activities.MainActivity;
import com.example.sherman.sbook.adapters.BookRecyclerViewAdapter;
import com.example.sherman.sbook.adapters.RecycleViewDecoration;
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
import java.util.List;

import me.xdrop.fuzzywuzzy.FuzzySearch;

public class SearchFragment extends Fragment {
    private Context mContext;
    private View rootLayout;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    private RecyclerView recyclerView;
    private BookRecyclerViewAdapter rcAdapter;
    public ProgressDialog progressDialog;

    private AutoCompleteTextView atcBookTitle;

    private final String TAG = "HomeFragment";

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

    public SearchFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mContext = getActivity();
        rootLayout = inflater.inflate(R.layout.home_fragment, container, false);
        atcBookTitle = ((MainActivity) getActivity()).atcBookTitle;
        return rootLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Đang tải dữ liệu...");
        progressDialog.show();

        UserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Lay tat ca loai sach nguoi dung quan tam
                String[] categories = getCategories(dataSnapshot.getValue().toString());

                // Lay thon tin sach
                for (int i = 0; i < categories.length; i++) {
                    getBooks(categories[i]);

                    if (i == categories.length - 1) {
                        loadOtherBooks();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Set search listener
        setSearchListener();
    }

    private void setSearchListener() {
        // Add listener for atc
        atcBookTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String BookTitle = (String) adapterView.getItemAtPosition(i);
                Log.d(TAG, "Title: " + BookTitle);
                List<Book> SearchResult = new ArrayList<Book>();

                for (Book book : Books) {
                    String title = book.getTitle();
                    float ratio = FuzzySearch.ratio(
                            NormalizeTextHelper.normalizeBookTitle(BookTitle),
                            NormalizeTextHelper.normalizeBookTitle(title));

                    if (ratio == 100)
                        SearchResult.add(0, book);
                    else if (ratio > 50)
                        SearchResult.add(book);
                }

                Log.d(TAG, "Search result size: " + SearchResult.size());
                rcAdapter.changeData(SearchResult);
            }
        });

        atcBookTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    rcAdapter.changeData(Books);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void loadOtherBooks() {
        BookRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String bookId = dataSnapshot.getKey();
                Log.d(TAG, "Load other book: " + bookId);

                if (!BookLoaded.contains(bookId)) {
                    Book book = dataSnapshot.getValue(Book.class);
                    Books.add(book);
                    rcAdapter.notifyDataSetChanged();
                    Log.d(TAG, book.getCoverUrl());
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

    public void getBooks(final String category) {
        DatabaseReference CateRef = CategoriesRef
                .child(category)
                .child(Database.BOOKS);

        CateRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Hide dialog
                progressDialog.dismiss();

                // Duyet qua tat ca sach
                for (DataSnapshot book : dataSnapshot.getChildren()) {
                    String BookId = book.getValue().toString();
                    Log.d(TAG, "Category: " + category + ", BookId: " + BookId);

                    // Chi duyet sach chua duoc load
                    if (!BookLoaded.contains(BookId)) {
                        BookLoaded.add(BookId);
                        loadBookInfo(BookId);
                    }
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

    // Get Categories
    public String[] getCategories(String intersting) {
        Log.d(TAG, "Categories: " + intersting);
        return intersting.split(",");
    }

    // Init RecycleView
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
