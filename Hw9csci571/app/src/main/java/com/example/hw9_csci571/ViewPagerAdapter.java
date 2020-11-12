package com.example.hw9_csci571;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class ViewPagerAdapter extends FragmentPagerAdapter {

    private String title[] = {"TODAY", "WEEKLY", "PHOTOS"};

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return TabFragment.getInstance();
        }
        else if(position == 1){
            return weeklyFragment.getInstance();
        }
        else if(position==2){
            //return TabFragment.getInstance();
            // ================ temporary disbale ===================================
            return ItemFragment.newInstance(7);
        }
        return TabFragment.getInstance();
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