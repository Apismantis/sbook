package com.example.sherman.sbook.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.CategoryRecyclerViewAdapter;
import com.example.sherman.sbook.adapters.NotificationRecyclerViewAdapter;
import com.example.sherman.sbook.models.Category;
import com.example.sherman.sbook.models.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {
    public NotificationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Context context = view.getContext();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        StaggeredGridLayoutManager gaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<Notification> listCategory = getListItemData();
        recyclerView.setAdapter(new NotificationRecyclerViewAdapter(context, listCategory));
        return view;
    }

    private List<Notification> getListItemData(){
        List<Notification> listViewItems = new ArrayList<>();
        listViewItems.add(new Notification("Ester", null));
        listViewItems.add(new Notification("Alcohol", null));
        listViewItems.add(new Notification("Alkane", null));
        listViewItems.add(new Notification("Ethane", null));

        return listViewItems;
    }

}
