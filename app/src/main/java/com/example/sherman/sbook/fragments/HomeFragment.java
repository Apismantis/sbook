package com.example.sherman.sbook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.BookRecyclerViewAdapter;
import com.example.sherman.sbook.models.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherman on 10/29/2016.
 */

public class HomeFragment extends Fragment {
    private Context mContext;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mContext = getActivity();
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<Book> gaggeredList = getListItemData();

        BookRecyclerViewAdapter rcAdapter = new BookRecyclerViewAdapter(mContext, gaggeredList);
        recyclerView.setAdapter(rcAdapter);

        return v;
    }
    private List<Book> getListItemData(){
        List<Book> listViewItems = new ArrayList<Book>();
        listViewItems.add(new Book("Alkane", "nguyễn tuấn"));
        listViewItems.add(new Book("Ethane", "nguyễn tuấn"));
        listViewItems.add(new Book("Alkyne", "nguyễn tuấn"));
        listViewItems.add(new Book("Benzene", "nguyễn tuấn"));
        listViewItems.add(new Book("Amide", "nguyễn tuấn"));
        listViewItems.add(new Book("Amino Acid", "nguyễn tuấn"));
        listViewItems.add(new Book("Phenol", "nguyễn tuấn"));
        listViewItems.add(new Book("Carbonxylic", "nguyễn tuấn"));
        listViewItems.add(new Book("Nitril", "nguyễn tuấn"));
        listViewItems.add(new Book("Ether", "nguyễn tuấn"));
        listViewItems.add(new Book("Ester", "nguyễn tuấn"));
        listViewItems.add(new Book("Alcohol", "nguyễn tuấn"));
        listViewItems.add(new Book("Alkane", "nguyễn tuấn"));
        listViewItems.add(new Book("Ethane", "nguyễn tuấn"));

        return listViewItems;
    }
}
