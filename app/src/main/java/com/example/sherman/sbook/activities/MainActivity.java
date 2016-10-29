package com.example.sherman.sbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.PagerAdapter;
import com.example.sherman.sbook.fragments.CategoryFragment;
import com.example.sherman.sbook.fragments.HomeFragment;
import com.example.sherman.sbook.fragments.NotificationFragment;
import com.example.sherman.sbook.services.NotifyService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import static android.support.design.R.styleable.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnMenu, btnSearch, btnCloseSearch;
    private TextView tvAcitivityTitle;
    private AutoCompleteTextView atcBookTitle;
    private FloatingActionButton fab;

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

        initImageLoader();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);

<<<<<<< HEAD
        startService(new Intent(this, NotifyService.class));
=======
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
>>>>>>> 102de145be494f110c2678dd391069dbc662c3bb
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "ONE");
        adapter.addFragment(new CategoryFragment(), "TWO");
        adapter.addFragment(new NotificationFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons(TabLayout tabLayout) {
        tabLayout.getTabAt(0).setIcon(R.drawable.book_drawable);
        tabLayout.getTabAt(1).setIcon(R.drawable.category_drawable);
        tabLayout.getTabAt(2).setIcon(R.drawable.notification_drawable);
    }

    private void initView() {
        btnMenu = (ImageButton) findViewById(R.id.btnMenuDrawer);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnCloseSearch = (ImageButton) findViewById(R.id.btnCloseSearch);

        tvAcitivityTitle = (TextView) findViewById(R.id.tvActivityTitle);

        // Init atc
        atcBookTitle = (AutoCompleteTextView) findViewById(R.id.atcBookTitle);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, bookTitle);
        atcBookTitle.setThreshold(1);
        atcBookTitle.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
