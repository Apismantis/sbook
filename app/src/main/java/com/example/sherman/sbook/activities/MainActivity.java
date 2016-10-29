package com.example.sherman.sbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.PagerAdapter;
import com.example.sherman.sbook.fragments.HomeFragment;
import com.example.sherman.sbook.fragments.SearchFragment;
import com.example.sherman.sbook.services.NotifyService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnMenu, btnSearch, btnCloseSearch;
    private TextView tvAcitivityTitle;
    public AutoCompleteTextView atcBookTitle;
    private RelativeLayout homeFragment;
    private TabLayout tabLayout;
    private FloatingActionButton fab;

    // Book title
    public String[] bookTitle = {
            "Mộng Xưa Thành Cũ",
            "Trên Đường Băng",
            "Năng Lực Lãnh Đạo",
            "Thám Tử Lừng Danh Conan",
            "Đời Thay Đổi Khi Chúng Ta Đổi Thay",
            "Đắc Nhân Tâm",
            "Cho Tôi Xin Một Vé Đi Tuổi Thơ"};

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
                btnSearch.setVisibility(RelativeLayout.GONE);
                tvAcitivityTitle.setVisibility(RelativeLayout.GONE);
                btnMenu.setVisibility(RelativeLayout.GONE);
                btnCloseSearch.setVisibility(View.VISIBLE);
                atcBookTitle.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                homeFragment.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.GONE);

                SearchFragment list_fragment = new SearchFragment();
                fragmentTransaction.replace(R.id.homeFragment, list_fragment, list_fragment.toString() + "");
                fragmentTransaction.commit();
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

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                homeFragment.setVisibility(View.GONE);
                tabLayout.setVisibility(View.VISIBLE);

                SearchFragment list_fragment = new SearchFragment();
                fragmentTransaction.replace(R.id.homeFragment, list_fragment, list_fragment.toString() + "");
                fragmentTransaction.commit();
            }
        });

        initImageLoader();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);

        fab = (FloatingActionButton)findViewById(R.id.fabAddNewBook);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
        startService(new Intent(this, NotifyService.class));
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "ONE");
        adapter.addFragment(new HomeFragment(), "TWO");
        adapter.addFragment(new HomeFragment(), "THREE");
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
        atcBookTitle.setDropDownBackgroundResource(R.drawable.dropdown_back);

        // Home fragment
        homeFragment = (RelativeLayout) findViewById(R.id.homeFragment);

        // Init tablayout
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);
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
