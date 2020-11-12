package com.example.hw9_csci571;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LoadingScreenActivity2 extends AppCompatActivity {

    private final int WAIT_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);
        findViewById(R.id.progressbar_view).setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Simulating a long running task
                /* Create an Intent that will start the ProfileData-Activity. */
                    Intent intent = new Intent(LoadingScreenActivity2.this,DetailActivity.class);
                    String passData = getIntent().getStringExtra("SEND_DATA");
                    String address= getIntent().getStringExtra("address");
                    String twitterTemp = getIntent().getStringExtra("twitter");
                    intent.putExtra("SEND_DATA", passData);
                    intent.putExtra("address", address);
                    intent.putExtra("twitter",twitterTemp);
                    intent.putExtra("fromFrag","true");
                    LoadingScreenActivity2.this.startActivity(intent);
                    LoadingScreenActivity2.this.finish();
            }
        }, WAIT_TIME);
    }
}
