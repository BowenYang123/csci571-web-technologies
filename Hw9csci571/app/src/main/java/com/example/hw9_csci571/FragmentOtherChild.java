package com.example.hw9_csci571;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentOtherChild extends Fragment {
    String childname;
    TextView textViewChildName;


    private String passData;

    private Double lat,lng;
    private String twitterTemp;

    private CardView clickCard;
    private TextView TempShow;
    private TextView SummaryShow;
    private TextView AddShow;
    private ImageView WeatherImg;

    private TextView HumidiyShow;
    private TextView WindSpeedShow;
    private TextView VisibilityShow;
    private TextView PressureShow;

    private TextView date0;
    private TextView date1;
    private TextView date2;
    private TextView date3;
    private TextView date4;
    private TextView date5;
    private TextView date6;
    private TextView date7;

    private ImageView img0;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private ImageView img6;
    private ImageView img7;

    private TextView TempLow0;
    private TextView TempLow1;
    private TextView TempLow2;
    private TextView TempLow3;
    private TextView TempLow4;
    private TextView TempLow5;
    private TextView TempLow6;
    private TextView TempLow7;

    private TextView TempHigh0;
    private TextView TempHigh1;
    private TextView TempHigh2;
    private TextView TempHigh3;
    private TextView TempHigh4;
    private TextView TempHigh5;
    private TextView TempHigh6;
    private TextView TempHigh7;

    private String address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        Bundle bundle = getArguments();

        childname = bundle.getString("data");
        address = bundle.getString("address");


        clickCard = view.findViewById(R.id.card1);
        clickCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("SEND_DATA", passData);
                intent.putExtra("address", address);
                intent.putExtra("twitter",twitterTemp);
                startActivity(intent);
            }
        });

        TempShow = view.findViewById(R.id.temperature);
        SummaryShow = view.findViewById(R.id.summary);
        AddShow = view.findViewById(R.id.address);
        WeatherImg = view.findViewById(R.id.card1Img);

        HumidiyShow = view.findViewById(R.id.humidity_input);
        WindSpeedShow = view.findViewById((R.id.windspeed_input));
        VisibilityShow = view.findViewById(R.id.visibility_input);
        PressureShow = view.findViewById(R.id.pressure_input);

        date0 = view.findViewById(R.id.date0);
        date1 = view.findViewById(R.id.date1);
        date2 = view.findViewById(R.id.date2);
        date3 = view.findViewById(R.id.date3);
        date4 = view.findViewById(R.id.date4);
        date5 = view.findViewById(R.id.date5);
        date6 = view.findViewById(R.id.date6);
        date7 = view.findViewById(R.id.date7);

        img0 = view.findViewById(R.id.img0);
        img1 = view.findViewById(R.id.img1);
        img2 = view.findViewById(R.id.img2);
        img3 = view.findViewById(R.id.img3);
        img4 = view.findViewById(R.id.img4);
        img5 = view.findViewById(R.id.img5);
        img6 = view.findViewById(R.id.img6);
        img7 = view.findViewById(R.id.img7);

        TempLow0 = view.findViewById(R.id.tempLow0);
        TempLow1 = view.findViewById(R.id.tempLow1);
        TempLow2 = view.findViewById(R.id.tempLow2);
        TempLow3 = view.findViewById(R.id.tempLow3);
        TempLow4 = view.findViewById(R.id.tempLow4);
        TempLow5 = view.findViewById(R.id.tempLow5);
        TempLow6 = view.findViewById(R.id.tempLow6);
        TempLow7 = view.findViewById(R.id.tempLow7);

        TempHigh0 = view.findViewById(R.id.tempHigh0);
        TempHigh1 = view.findViewById(R.id.tempHigh1);
        TempHigh2 = view.findViewById(R.id.tempHigh2);
        TempHigh3 = view.findViewById(R.id.tempHigh3);
        TempHigh4 = view.findViewById(R.id.tempHigh4);
        TempHigh5 = view.findViewById(R.id.tempHigh5);
        TempHigh6 = view.findViewById(R.id.tempHigh6);
        TempHigh7 = view.findViewById(R.id.tempHigh7);


        jsonParse(childname);
        setEvents();
        return view;
    }


    private void setEvents() {

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
    private void jsonParse(String inputdata){
        passData = inputdata;
        JSONObject jsonObject = null;
        JSONObject dailyObject =null;
        try {
            //for card1
            JSONObject response = new JSONObject(inputdata);
            jsonObject = response.getJSONObject("currently");

            String icon  = jsonObject.get("icon").toString();
            Float temp = Float.parseFloat(jsonObject.get("temperature").toString());
            String Temperature  = ""+ Math.round(temp) +'\u00B0'+"F";
            twitterTemp = ""+ Math.round(temp);
            String Summary = jsonObject.get("summary").toString();
            TempShow.setText(Temperature);
            SummaryShow.setText(Summary);
            AddShow.setText(address);

            switch (icon){
                case "clear-day":
                    WeatherImg.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    WeatherImg.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    WeatherImg.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    WeatherImg.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    WeatherImg.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    WeatherImg.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    WeatherImg.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    WeatherImg.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    WeatherImg.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    WeatherImg.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }
            // for card1
            String humidity  = jsonObject.get("humidity").toString();
            double humi = Double.valueOf(humidity);
            humi = humi*100;
            String humi_str  = ""+ Math.round(humi) +" %";
            HumidiyShow.setText(humi_str);

            DecimalFormat df = new DecimalFormat("#.00");
            String windspeed  = jsonObject.get("windSpeed").toString();
            double ws = Double.valueOf(windspeed);
            String ws_str = df.format(ws)+" mph";
            WindSpeedShow.setText(ws_str);

            String visibility  = jsonObject.get("visibility").toString();
            double vs = Double.valueOf(visibility);
            String vs_str = df.format(vs)+" km";
            VisibilityShow.setText(vs_str);

            String pressure = jsonObject.get("pressure").toString();
            double pres = Double.valueOf(pressure);
            String pres_str = df.format(pres)+" mb";
            PressureShow.setText(pres_str);

            //for card3
            dailyObject = response.getJSONObject("daily");
            JSONArray dailyArray = dailyObject.getJSONArray("data");

            //day0
            JSONObject daily0 = dailyArray.getJSONObject(0);
            Integer d0 = Integer.parseInt(daily0.get("time").toString());

            SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
            date0.setText(sdf.format(TimestampToDate(d0)));
            String icon0 = daily0.getString("icon");

            switch (icon0){
                case "clear-day":
                    img0.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    img0.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    img0.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    img0.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    img0.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    img0.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    img0.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    img0.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    img0.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    img0.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }


            String tmpL0 = daily0.getString("temperatureLow");
            Float tmp0 = Float.parseFloat(tmpL0);
            tmpL0 = ""+ Math.round(tmp0 );
            TempLow0.setText(tmpL0);

            String tmpH0 = daily0.getString("temperatureHigh");
            tmp0 = Float.parseFloat(tmpH0);
            tmpH0 = ""+ Math.round(tmp0 );
            TempHigh0.setText(tmpH0);

            //day1
            JSONObject daily1 = dailyArray.getJSONObject(1);
            Integer d1 = Integer.parseInt(daily1.get("time").toString());

            date1.setText(sdf.format(TimestampToDate(d1)));
            String icon1 = daily1.getString("icon");

            switch (icon1){
                case "clear-day":
                    img1.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    img1.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    img1.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    img1.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    img1.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    img1.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    img1.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    img1.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    img1.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    img1.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }


            String tmpL1 = daily1.getString("temperatureLow");
            Float tmp1 = Float.parseFloat(tmpL1);
            tmpL1 = ""+ Math.round(tmp1);
            TempLow1.setText(tmpL1);

            String tmpH1 = daily1.getString("temperatureHigh");
            tmp1 = Float.parseFloat(tmpH1);
            tmpH1 = ""+ Math.round(tmp1);
            TempHigh1.setText(tmpH1);

            //day2
            JSONObject daily2 = dailyArray.getJSONObject(2);
            Integer d2 = Integer.parseInt(daily2.get("time").toString());

            date2.setText(sdf.format(TimestampToDate(d2)));
            String icon2 = daily2.getString("icon");

            switch (icon2){
                case "clear-day":
                    img2.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    img2.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    img2.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    img2.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    img2.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    img2.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    img2.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    img2.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    img2.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    img2.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }


            String tmpL2 = daily2.getString("temperatureLow");
            Float tmp2 = Float.parseFloat(tmpL2);
            tmpL2 = ""+ Math.round(tmp2);
            TempLow2.setText(tmpL2);

            String tmpH2 = daily2.getString("temperatureHigh");
            tmp2 = Float.parseFloat(tmpH2);
            tmpH2 = ""+ Math.round(tmp2);
            TempHigh2.setText(tmpH2);

            //day3
            JSONObject daily3 = dailyArray.getJSONObject(3);
            Integer d3 = Integer.parseInt(daily3.get("time").toString());

            date3.setText(sdf.format(TimestampToDate(d3)));
            String icon3 = daily3.getString("icon");

            switch (icon3){
                case "clear-day":
                    img3.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    img3.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    img3.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    img3.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    img3.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    img3.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    img3.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    img3.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    img3.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    img3.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }


            String tmpL3 = daily3.getString("temperatureLow");
            Float tmp3 = Float.parseFloat(tmpL3);
            tmpL3 = ""+ Math.round(tmp3);
            TempLow3.setText(tmpL3);

            String tmpH3 = daily3.getString("temperatureHigh");
            tmp3 = Float.parseFloat(tmpH3);
            tmpH3 = ""+ Math.round(tmp3);
            TempHigh3.setText(tmpH3);

            //day4
            JSONObject daily4 = dailyArray.getJSONObject(4);
            Integer d4 = Integer.parseInt(daily4.get("time").toString());

            date4.setText(sdf.format(TimestampToDate(d4)));
            String icon4 = daily4.getString("icon");

            switch (icon4){
                case "clear-day":
                    img4.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    img4.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    img4.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    img4.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    img4.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    img4.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    img4.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    img4.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    img4.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    img4.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }


            String tmpL4 = daily4.getString("temperatureLow");
            Float tmp4 = Float.parseFloat(tmpL4);
            tmpL4 = ""+ Math.round(tmp4);
            TempLow4.setText(tmpL4);

            String tmpH4 = daily4.getString("temperatureHigh");
            tmp4 = Float.parseFloat(tmpH4);
            tmpH4 = ""+ Math.round(tmp4);
            TempHigh4.setText(tmpH4);

            //day5
            JSONObject daily5 = dailyArray.getJSONObject(5);
            Integer d5 = Integer.parseInt(daily5.get("time").toString());

            date5.setText(sdf.format(TimestampToDate(d5)));
            String icon5 = daily5.getString("icon");

            switch (icon5){
                case "clear-day":
                    img5.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    img5.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    img5.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    img5.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    img5.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    img5.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    img5.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    img5.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    img5.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    img5.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }


            String tmpL5 = daily5.getString("temperatureLow");
            Float tmp5 = Float.parseFloat(tmpL5);
            tmpL5 = ""+ Math.round(tmp5);
            TempLow5.setText(tmpL5);

            String tmpH5 = daily5.getString("temperatureHigh");
            tmp5 = Float.parseFloat(tmpH5);
            tmpH5 = ""+ Math.round(tmp5);
            TempHigh5.setText(tmpH5);

            //day6
            JSONObject daily6 = dailyArray.getJSONObject(6);
            Integer d6 = Integer.parseInt(daily6.get("time").toString());

            date6.setText(sdf.format(TimestampToDate(d6)));
            String icon6 = daily6.getString("icon");

            switch (icon6){
                case "clear-day":
                    img6.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    img6.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    img6.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    img6.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    img6.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    img6.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    img6.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    img6.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    img6.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    img6.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }


            String tmpL6 = daily6.getString("temperatureLow");
            Float tmp6 = Float.parseFloat(tmpL6);
            tmpL6 = ""+ Math.round(tmp6);
            TempLow6.setText(tmpL6);

            String tmpH6 = daily6.getString("temperatureHigh");
            tmp6 = Float.parseFloat(tmpH6);
            tmpH6 = ""+ Math.round(tmp6);
            TempHigh6.setText(tmpH6);

            //day7
            JSONObject daily7 = dailyArray.getJSONObject(7);
            Integer d7 = Integer.parseInt(daily7.get("time").toString());

            date7.setText(sdf.format(TimestampToDate(d7)));
            String icon7 = daily7.getString("icon");

            switch (icon7){
                case "clear-day":
                    img7.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    img7.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    img7.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    img7.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    img7.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    img7.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    img7.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    img7.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    img7.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    img7.setImageResource(R.drawable.weather_night_partly_cloudy);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + icon);
            }


            String tmpL7 = daily7.getString("temperatureLow");
            Float tmp7 = Float.parseFloat(tmpL7);
            tmpL7 = ""+ Math.round(tmp7);
            TempLow7.setText(tmpL7);

            String tmpH7 = daily7.getString("temperatureHigh");
            tmp7 = Float.parseFloat(tmpH7);
            tmpH7 = ""+ Math.round(tmp7);
            TempHigh7.setText(tmpH7);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}