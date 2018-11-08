package com.tinh.dev.applove.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tinh.dev.applove.DatePicker_Fragment;
import com.tinh.dev.applove.Diary_Fragment;
import com.tinh.dev.applove.HomeFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:

                return new HomeFragment();
            case 0:

                return new Diary_Fragment();

                case 2:

                return new DatePicker_Fragment();

                default:
                    return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


}
