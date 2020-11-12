package com.example.hw9_csci571;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoadingScreenActivity extends AppCompatActivity {

    private final int WAIT_TIME = 1600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        findViewById(R.id.progressbar_view).setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Simulating a long running task
                /* Create an Intent that will start the ProfileData-Activity. */

                String dest = getIntent().getStringExtra("DEST");
                //if(dest == "SearchCityActivity"){
                Intent mainIntent = new Intent(LoadingScreenActivity.this,SearchCityActivity.class);
                String query = getIntent().getStringExtra("QUERY");
                mainIntent.putExtra("QUERY", query);
                LoadingScreenActivity.this.startActivity(mainIntent);
                LoadingScreenActivity.this.finish();
                //  }
//                else if (dest == "DetailActivity"){
//                    Log.i("loading",dest);
//                    Intent intent = new Intent(LoadingScreenActivity.this,DetailActivity.class);
//                    String passData = getIntent().getStringExtra("SEND_DATA");
//                    String address= getIntent().getStringExtra("address");
//                    String twitterTemp = getIntent().getStringExtra("twitter");
//                    intent.putExtra("SEND_DATA", passData);
//                    intent.putExtra("address", address);
//                    intent.putExtra("twitter",twitterTemp);
//                    LoadingScreenActivity.this.startActivity(intent);
//                    LoadingScreenActivity.this.finish();
//                }
            }
        }, WAIT_TIME);
    }
}
