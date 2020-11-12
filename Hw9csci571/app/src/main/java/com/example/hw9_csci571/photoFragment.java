package com.example.hw9_csci571;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class photoFragment extends Fragment {


    private String mycity;
    private RequestQueue mQueue;
    private ArrayList<String> picURLArray ;

    public static Fragment getInstance() {
        Bundle bundle = new Bundle();
        //bundle.putInt("pos", position);
        photoFragment photoFragment = new photoFragment();
        photoFragment.setArguments(bundle);
        return photoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DetailActivity activity = (DetailActivity) getActivity();
        picURLArray = (ArrayList<String> )activity.getMyURL().clone();
        Log.i("Frag",picURLArray.toString());
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        textView = (TextView) view.findViewById(R.id.textView);
//        Log.i("city",mycity);


    }
}