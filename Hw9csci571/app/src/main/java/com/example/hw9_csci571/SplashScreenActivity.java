package com.example.hw9_csci571;


import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {
    private final int WAIT_TIME = 1200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Simulating a long running task
                /* Create an Intent that will start the ProfileData-Activity. */

                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, WAIT_TIME);

        //finish();
    }
}
