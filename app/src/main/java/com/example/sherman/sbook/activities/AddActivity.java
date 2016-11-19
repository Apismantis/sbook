package com.example.sherman.sbook.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.adapters.AddNewFragmentPagerAdapter;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ViewPager vpAddNew = (ViewPager) findViewById(R.id.vpAddNew);
        TabLayout tlAddNew = (TabLayout) findViewById(R.id.tlAddNew);

        vpAddNew.setAdapter(new AddNewFragmentPagerAdapter(getSupportFragmentManager(), AddActivity.this));
        tlAddNew.setupWithViewPager(vpAddNew);
    }
}
