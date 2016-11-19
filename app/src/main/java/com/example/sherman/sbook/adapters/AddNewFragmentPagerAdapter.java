package com.example.sherman.sbook.adapters;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.example.sherman.sbook.R;
import com.example.sherman.sbook.fragments.AddSaleBookFragment;
import com.example.sherman.sbook.fragments.AddWantedBookFragment;

/**
 * Created by kenp on 12/11/2016.
 */
public class AddNewFragmentPagerAdapter extends PagerAdapter {

    private FragmentManager fragmentManager;
    private Context context;

    public static int PAGE_COUNT = 2;
    private String[] pageTitles = {"Thêm sách mới", "Thêm sách cần săn"};


    public AddNewFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }

    public AddNewFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragmentManager = fm;
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0)
            return new AddSaleBookFragment();
        return new AddWantedBookFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
