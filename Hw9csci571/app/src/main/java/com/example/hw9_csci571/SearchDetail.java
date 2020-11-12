package com.example.hw9_csci571;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String receiveData;
    private String twitterTemp;

    private String mycity;
    private String address;
    private int[] tabIcons = {
            R.drawable.calendar_today,
            R.drawable.trending_up,
            R.drawable.google_photos
    };

    private RequestQueue mQueue;
    private ArrayList<String> picURLArray = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mQueue = Volley.newRequestQueue(this);

        receiveData = getIntent().getStringExtra("SEND_DATA");
        //receiveData = new JSONObject(data);
        address = getIntent().getStringExtra("address");
        twitterTemp = getIntent().getStringExtra("twitter");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(address);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        CityAdapter adapter = new CityAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        String url = "https://www.googleapis.com/customsearch/v1?q="+address+"&cx=017754693043744206472:d7ujejxjqlh&num=8&searchType=image&key=AIzaSyAnxouolpmitthOjXW2Pcz_Ta5Z5Xwqhk4";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray picArray = response.getJSONArray("items");
                            for (int i=0;i<picArray.length();++i){
                                JSONObject temp = (JSONObject)picArray.get(i);
                                picURLArray.add(temp.getString("link"));
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        // ================ temporary disbale ===================================
        mQueue.add(request);

    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tweet_menu,menu);
        MenuItem twitterItem = menu.findItem(R.id.action_twitter);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_twitter:
                String url = "https://twitter.com/intent/tweet?text="+"Check Out "+address+"’s Weather! It is "+twitterTemp+"°F .&hashtags=CSCI571WeatherSearch";
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(openURL);
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case android.R.id.home:

                Intent intent = new Intent(SearchDetail.this, SearchCityActivity.class);

                intent.putExtra("QUERY", address);
                startActivity(intent);
                finish();
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public String getMyData() {
        return receiveData;
    }


    public ArrayList<String> getMyURL(){
        return picURLArray;
    }
}
