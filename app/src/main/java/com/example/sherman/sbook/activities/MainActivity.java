package com.example.sherman.sbook.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.PagerAdapter;
import com.example.sherman.sbook.fragments.BookCategoryFragment;
import com.example.sherman.sbook.services.NotifyService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnMenu, btnSearch;
    private TextView tvAcitivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init view
        initView();
        initImageLoader();

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons(tabLayout);

        startService(new Intent(this, NotifyService.class));
    }

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BookCategoryFragment(), "ONE");
        adapter.addFragment(new BookCategoryFragment(), "TWO");
        adapter.addFragment(new BookCategoryFragment(), "THREE");
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

        tvAcitivityTitle = (TextView) findViewById(R.id.tvActivityTitle);
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

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
    }
}
