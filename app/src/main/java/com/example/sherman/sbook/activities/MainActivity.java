package com.example.sherman.sbook.activities;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sherman.sbook.Book;
import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.PagerAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    private ImageButton btnMenu, btnSearch, btnCloseSearch;
    private TextView tvAcitivityTitle;
    private AutoCompleteTextView atcBookTitle;

    // Book title
    String[] bookTitle = {"Tony Buổi Sáng", "Yêu Em Từ Cái Nhìn Đầu Tiên", "7 Thói Quen Để Thành Đạt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init view
        initView();

        // Replace toolbar for search
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSearch.setVisibility(View.GONE);
                tvAcitivityTitle.setVisibility(View.GONE);
                btnMenu.setVisibility(View.GONE);
                btnCloseSearch.setVisibility(View.VISIBLE);
                atcBookTitle.setVisibility(View.VISIBLE);
            }
        });

        // Show home toolbar
        btnCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSearch.setVisibility(View.VISIBLE);
                tvAcitivityTitle.setVisibility(View.VISIBLE);
                btnMenu.setVisibility(View.VISIBLE);
                btnCloseSearch.setVisibility(View.GONE);
                atcBookTitle.setVisibility(View.GONE);
            }
        });

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

    private void initView() {
        btnMenu = (ImageButton) findViewById(R.id.btnMenuDrawer);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnCloseSearch = (ImageButton) findViewById(R.id.btnCloseSearch);

        tvAcitivityTitle = (TextView) findViewById(R.id.tvActivityTitle);

        // Init atc
        atcBookTitle = (AutoCompleteTextView) findViewById(R.id.atcBookTitle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>  (this, android.R.layout.simple_dropdown_item_1line, bookTitle);
        atcBookTitle.setThreshold(1);
        atcBookTitle.setAdapter(adapter);
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
