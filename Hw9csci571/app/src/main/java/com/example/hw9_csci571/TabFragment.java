package com.example.hw9_csci571;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class TabFragment extends Fragment {

    //int position;
    private TextView textView;

    private JSONObject mydata;

    private TextView WindSpeedShow;
    private TextView PressureShow;
    private TextView PrecipitationShow;

    private TextView TempShow;
    private ImageView currently;
    private TextView SummaryShow;
    private TextView HumidiyShow;

    private TextView VisibilityShow;
    private TextView CloudcoverShow;
    private TextView ozoneShow;

    public static Fragment getInstance() {
        Bundle bundle = new Bundle();
        //bundle.putInt("pos", position);
        TabFragment tabFragment = new TabFragment();
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //position = getArguments().getInt("pos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DetailActivity activity = (DetailActivity) getActivity();
        String myDataFromActivity = activity.getMyData();
        try {
            mydata = new JSONObject(myDataFromActivity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        WindSpeedShow = view.findViewById((R.id.windspeed_input));
        PressureShow = view.findViewById(R.id.pressure_input);
        PrecipitationShow = view.findViewById(R.id.precipIntensity_input);

        TempShow = view.findViewById(R.id.temperature_input);
        currently = view.findViewById(R.id.currently);
        SummaryShow = view.findViewById(R.id.summary);
        HumidiyShow = view.findViewById(R.id.humidity_input);

        VisibilityShow = view.findViewById(R.id.visibility_input);
        CloudcoverShow = view.findViewById(R.id.cloudCover_input);
        ozoneShow = view.findViewById(R.id.ozone_input);


        try {
            JSONObject jsonObject = mydata.getJSONObject("currently");

            String icon = jsonObject.get("icon").toString();

            Float temp = Float.parseFloat(jsonObject.get("temperature").toString());
            String Temperature  = ""+ Math.round(temp) +'\u00B0'+"F";
            String Summary = jsonObject.get("summary").toString();
            TempShow.setText(Temperature);
            SummaryShow.setText(Summary);
//            String addr = mycity+", "+myregion+", "+mycountry;
//            AddShow.setText(addr);

            switch (icon){
                case "clear-day":
                    currently.setImageResource(R.drawable.weather_sunny);
                    break;
                case "clear-night":
                    currently.setImageResource(R.drawable.weather_night);
                    break;
                case "rain":
                    currently.setImageResource(R.drawable.weather_rainy);
                    break;
                case "snow":
                    currently.setImageResource(R.drawable.weather_snowy);
                    break;
                case "sleet":
                    currently.setImageResource(R.drawable.weather_snowy_rainy);
                    break;
                case "wind":
                    currently.setImageResource(R.drawable.weather_windy_variant );
                    break;
                case "fog":
                    currently.setImageResource(R.drawable.weather_fog );
                    break;
                case "cloudy":
                    currently.setImageResource(R.drawable.weather_cloudy);
                    break;
                case "partly-cloudy-day":
                    currently.setImageResource(R.drawable.weather_partly_cloudy);
                    break;
                case "partly-cloudy-night":
                    currently.setImageResource(R.drawable.weather_night_partly_cloudy);
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
            if(ws<1){
                ws_str  = "0"+ ws_str ;
            }
            WindSpeedShow.setText(ws_str);

            String visibility  = jsonObject.get("visibility").toString();
            double vs = Double.valueOf(visibility);
            String vs_str = df.format(vs)+" km";
            VisibilityShow.setText(vs_str);

            String pressure = jsonObject.get("pressure").toString();
            double pres = Double.valueOf(pressure);
            String pres_str = df.format(pres)+" mb";
            PressureShow.setText(pres_str);

            String Precipitation = jsonObject.get("precipIntensity").toString();
            double prec = Double.valueOf(Precipitation);
            String prec_str = df.format(prec)+" mmph";
            if(prec<1){
                prec_str = "0"+prec_str;
            }
            PrecipitationShow.setText(prec_str);

            String cloudCover = jsonObject.get("cloudCover").toString();
            double cc = Double.valueOf(cloudCover);
            cc = cc*100;
            String cc_str  = ""+ Math.round(cc) +" %";
            CloudcoverShow.setText(cc_str);

            String ozone = jsonObject.get("ozone").toString();
            double oz = Double.valueOf(ozone);
            String oz_str = df.format(oz)+" DU";
            ozoneShow.setText(oz_str);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}