package com.example.hw9_csci571;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;



import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.CardView;
//import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.widget.SearchView.OnQueryTextListener;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Location location;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST =9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL= 5000;
    //LIST FOR PERMISSONS
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissonRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private static final int ALL_PERMISSIONS_RESULT=1011;

    FragmentParent fragmentParent;
    private String passData;
    private String address;

    private RequestQueue mQueue;
    private RequestQueue autoQueue;
    private Double lat,lng;
    private String mycity,mycountry,myregion;
    private String twitterTemp;

    private ArrayAdapter<String> autoText;

    private static final String TAG = "MainActivity";
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;

    private ArrayList<String> autoitems;

    private AutoSuggestAdapter autoSuggestAdapter;
    LinearLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("WeatherApp");
        getIDs();

        progress = findViewById(R.id.progressbar_view);
        mQueue = Volley.newRequestQueue(this);
        autoQueue =  Volley.newRequestQueue(this);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (permissionsToRequest.size() >0 ){
                requestPermissions(permissionsToRequest.
                        toArray(new String[permissionsToRequest.size()]),ALL_PERMISSIONS_RESULT);

            }
        }
        //new Task().execute();
        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();

//        if(getFragmentManager().getBackStackEntryCount() > 0){
//            getFragmentManager().popBackStackImmediate();
//        }
//        else{
//            super.onBackPressed();
//        }



    }



    private void update_fav(){
        SharedPreferences userSettings= getSharedPreferences("weatherdata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userSettings.edit();
        Map<String,String> key_Value=(Map<String, String>)userSettings.getAll(); //获取所有保存在对应标识下的数据，并以Map形式返回

        for(Map.Entry<String, String> entry : key_Value.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            Log.i("aaaaa",mapKey+":"+mapValue);
            fragmentParent.addPage(mapValue,mapKey);
        }
        progress.setVisibility(View.GONE);
        //fragmentParent.setNowItem();
    }


    private void getIDs() {
        fragmentParent = (FragmentParent) this.getSupportFragmentManager().findFragmentById(R.id.fragmentParent);
//        autoCompleteTextView = findViewById(R.id.auto);
    }


    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions){
        ArrayList<String> result = new ArrayList<>();

        for (String perm: wantedPermissions){
            if(!hasPermission(perm)){
                result.add(perm);
            }
        }

        return result;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) searchItem.getActionView();
        //final SearchView.SearchAutoComplete tmp = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search);
        final SearchView.SearchAutoComplete tmp2 = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        tmp2.setCursorVisible(false);

        tmp2.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                searchView.setQuery(autoSuggestAdapter.getItem(position),false);
            }
        });
        ArrayList<String> def = new ArrayList<String>(5);

        autoSuggestAdapter = new AutoSuggestAdapter(this,
                R.layout.auto_back_ground);

        //tmp2.setAdapter(dataAdapter);
        tmp2.setAdapter(autoSuggestAdapter);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(searchView.getQuery())) {
                        makeApiCall(searchView.getQuery().toString());
                    }
                }
                return false;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(MainActivity.this, SearchCityActivity.class);
                intent.putExtra("QUERY", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>=1){
                    handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                    handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                            AUTO_COMPLETE_DELAY);
                }

                return true;
            }


        });


        return true;
    }

    private void makeApiCall(String text) {
        ApiCall.make(this, text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //parsing logic, please change it as per your requirement
                List<String> stringList = new ArrayList<>();
                    try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray array = responseObject.getJSONArray("predictions");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        stringList.add(row.getString("description"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    private boolean hasPermission(String permission){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(googleApiClient!=null){
            googleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!checkPlayServices()){
            //locationTv.setText("you need to install Google Play Services");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (googleApiClient != null && googleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
            googleApiClient.disconnect();
        }
    }

    private boolean checkPlayServices(){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode =  apiAvailability.isGooglePlayServicesAvailable(this);

        if(resultCode!=ConnectionResult.SUCCESS){
            if(apiAvailability.isUserResolvableError((resultCode))){
                apiAvailability.getErrorDialog(this,resultCode,PLAY_SERVICES_RESOLUTION_REQUEST);
            }
            else{
                finish();
            }
            return false;
        }
        return true;
    }
    private void get_city(){
        String url="http://ip-api.com/json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mycountry = response.get("countryCode").toString();
                            mycity = response.get("city").toString();
                            myregion = response.get("region").toString();
                            address = mycity+", "+myregion+", "+mycountry;
                            jsonParse();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }

    public static Date TimestampToDate(Integer time){
        long temp = (long)time*1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
            //System.out.println(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }



    private void jsonParse(){
        String url="https://api.darksky.net/forecast/c89b39d0d8a515e8a73f2851d5987436/" + lat.toString()+','+lng.toString();
        //Log.i(TAG,url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        passData = response.toString();
//                        SharedPreferences userSettings = getSharedPreferences("weatherdata",SearchCityActivity.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = userSettings.edit();
//                        editor.putString(address,passData);
//                        editor.commit();
                        fragmentParent.addFirstPage(passData,address);
                        update_fav();

                        //MySharedPreferences.setName(passData,address, MainActivity.this);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        mQueue.add(request);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return ;
        }

        // permission ok
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if(location !=null){
            lat = location.getLatitude();
            lng = location.getLongitude();

            get_city();
        }
        startLocationUpdates();
    }

    private void startLocationUpdates(){
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest, this);
    }
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if(location !=null){
            //locationTv.setText("Latitude:" + location.getLatitude() + "\nLongitude:"+location.getLongitude());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case ALL_PERMISSIONS_RESULT:
                for (String perm:permissionsToRequest){
                    if (!hasPermission(perm)){
                        permissonRejected.add(perm);
                    }
                }

                if (permissonRejected.size()>0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (shouldShowRequestPermissionRationale(permissonRejected.get(0))){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setMessage("These permissions are mandatory to get your location")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                                requestPermissions(permissonRejected.toArray(new String[permissonRejected.size()]),
                                                        ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }).setNegativeButton("Cancel",null).create().show();
                            return;
                        }
                    }
                }
                else{
                    if(googleApiClient != null){
                        googleApiClient.connect();
                    }
                }
                break;
        }
    }
}
