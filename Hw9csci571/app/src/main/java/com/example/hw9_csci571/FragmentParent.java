package com.example.hw9_csci571;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentParent extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ScrollViewPagerAdapter adapter;

    private FloatingActionButton fabBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        getIDs(view);
        setEvents();

        SharedPreferences userSettings = getActivity().getSharedPreferences("weatherdata",SearchCityActivity.MODE_PRIVATE);
        final SharedPreferences.Editor editor = userSettings.edit();

        fabBtn = view.findViewById(R.id.fab);
        fabBtn.hide();

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int page = viewPager.getCurrentItem();
                String address = adapter.getTitle(page).toString();
                    editor.remove(address);
                    editor.commit();

                if (tabLayout.getTabCount() >= 1 && page<tabLayout.getTabCount()) {
                    tabLayout.removeTabAt(page);
                    adapter.removeTabPage(page);
                }
                    //getActivity().getSupportFragmentManager().beginTransaction().remove(FragmentOtherChild.this).commit();

                    Toast.makeText(getActivity(), address + " was removed from favorites.", Toast.LENGTH_SHORT).show();

                }

        });
        return view;
    }

    private void getIDs(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.my_viewpager);

        adapter = new ScrollViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) view.findViewById(R.id.my_tab_layout);

    }


    public void setNowItem(){
        if(getActivity().getIntent().getStringExtra("tag")!=null){
            int now = adapter.getPosition(getActivity().getIntent().getStringExtra("tag"));
            viewPager.setCurrentItem(now);
        }
    }

    private void setEvents() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.i("position",Integer.toString(tab.getPosition()));
                if(tab.getPosition() == 0){
                    fabBtn.hide();
                }
                else{
                    fabBtn.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void not(){
        adapter.notifyDataSetChanged();
    }
    public void addFirstPage(String dataname,String address){
        Bundle bundle = new Bundle();
        bundle.putString("data", dataname);
        bundle.putString("address",address);
        FragmentChild fragmentChild = new FragmentChild();
        fragmentChild.setArguments(bundle);
        Log.i("data",dataname);
        Log.i("address",address);
        adapter.addFrag(fragmentChild, address);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0)
            tabLayout.setupWithViewPager(viewPager);
//        if (tabLayout.getTabCount()==1){
//            tabLayout.setVisibility(View.GONE);
//        }
        tabLayout.setVisibility(View.GONE);
        //viewPager.setCurrentItem(adapter.getCount() - 1);

    }
    public void addPage(String dataname,String address) {
        Bundle bundle = new Bundle();
        bundle.putString("data", dataname);
        bundle.putString("address", address);
        FragmentOtherChild fragmentChild = new FragmentOtherChild();
        fragmentChild.setArguments(bundle);
        adapter.addFrag(fragmentChild, address);
        adapter.notifyDataSetChanged();
        if (adapter.getCount() > 0)
            tabLayout.setupWithViewPager(viewPager);
        if (tabLayout.getTabCount()>0){
            tabLayout.setVisibility(View.VISIBLE);
        }
        //viewPager.setCurrentItem(adapter.getCount() - 1);
    }


}