package com.example.hw9_csci571;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class ScrollViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<>();
    Context context;


    public ScrollViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public int getPosition(String query){
        return mFragmentTitleList.indexOf(query);
    }
    @Override
    public int getItemPosition(Object object) {
        return ScrollViewPagerAdapter.POSITION_NONE;
    }

    public void removeTabPage(int pos) {
        if (!mFragmentTitleList.isEmpty() && pos<mFragmentTitleList.size()) {
            mFragmentList.remove(pos);
            mFragmentTitleList.remove(pos);
            notifyDataSetChanged();
        }
    }

    public CharSequence getTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

}