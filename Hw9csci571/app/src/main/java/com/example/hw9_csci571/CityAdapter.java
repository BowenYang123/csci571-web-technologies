package com.example.hw9_csci571;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CityAdapter extends FragmentPagerAdapter {
    private String title[] = {"TODAY", "WEEKLY", "PHOTOS"};

    public CityAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return CityTagFragment.getInstance();
        }
        else if(position == 1){
            return  weeklyFragment2.getInstance();
        }
        else if(position==2){
            return ItemFragment2.newInstance(7);
        }
        return CityTagFragment.getInstance();
    }

    @Override
    public int getCount() {
        return title.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
