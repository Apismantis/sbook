package com.example.sherman.sbook.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sherman.sbook.BookItem;
import com.example.sherman.sbook.adapters.BookRecyclerViewAdapter;
import com.example.sherman.sbook.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        List<BookItem> gaggeredList = getListItemData();

        BookRecyclerViewAdapter rcAdapter = new BookRecyclerViewAdapter(MainActivity.this, gaggeredList);
        recyclerView.setAdapter(rcAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private List<BookItem> getListItemData(){
        List<BookItem> listViewItems = new ArrayList<BookItem>();
        listViewItems.add(new BookItem("Alkane", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Ethane", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Alkyne", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Benzene", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Amide", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Amino Acid", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Phenol", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Carbonxylic", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Nitril", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Ether", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Ester", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Alcohol", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Alkane", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Ethane", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Alkyne", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Benzene", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Amide", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Amino Acid", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Phenol", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Carbonxylic", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Nitril", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Ether", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Ester", R.mipmap.ic_launcher));
        listViewItems.add(new BookItem("Alcohol", R.mipmap.ic_launcher));

        return listViewItems;
    }
}
