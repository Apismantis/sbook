package com.example.sherman.sbook.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sherman.sbook.Book;
import com.example.sherman.sbook.adapters.BookRecyclerViewAdapter;
import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.PagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//
//        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
//        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
//
//        List<Book> gaggeredList = getListItemData();
//
//        BookRecyclerViewAdapter rcAdapter = new BookRecyclerViewAdapter(MainActivity.this, gaggeredList);
//        recyclerView.setAdapter(rcAdapter);
        initImageLoader();

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
//        Intent intent = new Intent(this, BookDetailActivity.class);
//        intent.putExtra(Constants.bookId, "hp04J1ddxsgpfVscwqpzM2Kz48r2");
//        startActivity(intent);
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

    private List<Book> getListItemData(){
        List<Book> listViewItems = new ArrayList<Book>();
        listViewItems.add(new Book("Alkane", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Ethane", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Alkyne", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Benzene", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Amide", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Amino Acid", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Phenol", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Carbonxylic", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Nitril", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Ether", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Ester", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Alcohol", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Alkane", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Ethane", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Alkyne", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Benzene", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Amide", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Amino Acid", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Phenol", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Carbonxylic", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Nitril", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Ether", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Ester", R.mipmap.ic_launcher));
        listViewItems.add(new Book("Alcohol", R.mipmap.ic_launcher));
        return listViewItems;
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .build();
        ImageLoader.getInstance().init(config);
    }
}
