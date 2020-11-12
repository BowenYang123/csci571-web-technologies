package com.example.hw9_csci571;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class BackLoadingActivity extends AppCompatActivity {

    private final int WAIT_TIME = 800;
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
                Intent intent = new Intent(BackLoadingActivity.this,MainActivity.class);
                intent.putExtra("tag",getIntent().getStringExtra("tag"));
                BackLoadingActivity.this.startActivity(intent);
                BackLoadingActivity.this.finish();
            }
        }, WAIT_TIME);
    }
}
