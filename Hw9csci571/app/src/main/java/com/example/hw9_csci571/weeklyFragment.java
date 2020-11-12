package com.example.hw9_csci571;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.Arrays;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;

public class weeklyFragment extends Fragment {

    //int position;
    private JSONObject mydata;


    private TextView SummaryShow;
    private ImageView WeatherImg;

    public static Fragment getInstance() {
        Bundle bundle = new Bundle();
        weeklyFragment weekFragment = new weeklyFragment();
        weekFragment .setArguments(bundle);
        return  weekFragment ;
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
        return inflater.inflate(R.layout.weekly_tab, container, false);
    }

    public String find_nearest(float target, ArrayList<Integer>list){
        float min = Math.abs(target-list.get(0));
        for (int i =0;i<list.size();++i){
            if(min>Math.abs(target-list.get(i))){
                min = Math.abs(target-list.get(i));
            }
            else{
                return Integer.toString(list.get(i));
            }
        }
        return Float.toString(min);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SummaryShow = view.findViewById(R.id.summary);
        WeatherImg = view.findViewById(R.id.card1Img);

        try{

            JSONObject jsonObject = mydata.getJSONObject("daily");

            String Summary =  jsonObject.get("summary").toString();
            SummaryShow.setText(Summary);

            String icon = jsonObject.get("icon").toString();

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
            LineChart chart = (LineChart) view.findViewById(R.id.chart);
            ArrayList<Float> myTempLow = new ArrayList();
            ArrayList<Float> myTempHigh = new ArrayList();
            JSONArray dailydata = jsonObject.getJSONArray("data");
            Log.i("dailydata",dailydata.toString());
            for (int i=0;i<dailydata.length();++i){
                JSONObject temp = (JSONObject)dailydata.get(i);
                myTempLow.add(Float.parseFloat(temp.getString("temperatureLow")));
                myTempHigh.add(Float.parseFloat(temp.getString("temperatureHigh")));
            }

            List<Entry>  entries1 = new ArrayList<Entry>();
            List<Entry> entries2 = new ArrayList<Entry>();
            for (int i=0;i<myTempLow.size();++i){

                // turn your data into Entry objects
                entries1.add(new Entry(i, myTempLow.get(i)));
                entries2.add(new Entry(i, myTempHigh.get(i)));
            }


//            Log.i("line_chart1",entries1.toString());
//            Log.i("line_chart2",entries2.toString());
            LineDataSet dataSet = new LineDataSet(entries1, "Minimum Temperature");
            LineDataSet dataSet2 = new LineDataSet(entries2, "Maximum Temperature");

            dataSet.setColor(Color.rgb(187, 134, 252));
            dataSet2.setColor(Color.rgb(250, 171, 26));

            LegendEntry legendEntryA = new LegendEntry();
            legendEntryA.label = "Minimum Temperature";
            legendEntryA.formColor = Color.rgb(187, 134, 252);

            LegendEntry legendEntryB = new LegendEntry();
            legendEntryB.label = "Maximum Temperature";
            legendEntryB.formColor = Color.rgb(250, 171, 26);


            Legend legend = chart.getLegend();
            legend.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used
            legend.setPosition(LegendPosition.BELOW_CHART_LEFT);
            legend.setTextSize(16f);
            legend.setTextColor(Color.WHITE);
            legend.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
            legend.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

            legend.setCustom(Arrays.asList(legendEntryA, legendEntryB));
            legend.setYOffset(18);
            chart.setExtraTopOffset(10);
            final ArrayList<String> xLabel = new ArrayList<>();
            xLabel.add("0");
            xLabel.add("1");
            xLabel.add("2");
            xLabel.add("3");
            xLabel.add("4");
            xLabel.add("5");
            xLabel.add("6");
            xLabel.add("7");



//
//
//            XAxis xAxis = chart.getXAxis();
//            xAxis.setDrawGridLines(false);
//            xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabel));

            XAxis xAxis = chart.getXAxis();
            xAxis.setTextColor(Color.parseColor("#333333"));
            xAxis.setTextSize(11f);
            xAxis.setAxisMinimum(0f);
            xAxis.setDrawAxisLine(true);//是否绘制轴线
            xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
            xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
            xAxis.setPosition(XAxis.XAxisPosition.TOP);//设置x轴的显示位置
            xAxis.setGranularity(1f);//禁止放大后x轴标签重绘
            xAxis.setEnabled(true);//显示x轴

            xAxis.setTextColor(Color.WHITE);//x轴文字颜色
            xAxis.setTextSize(12f);

            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if (value < xLabel.size()){
                        return xLabel.get((int) value);
                    }else {
                        return "";
                    }
                }
            });


//            YAxis yAxis = chart.getAxisLeft();
//            YAxis rightYAxis = chart.getAxisRight();
//            yAxis.setTextColor(Color.WHITE);//x轴文字颜色
//            yAxis.setTextSize(12f);
////            yAxis.setLabelCount(7, false);
////            yAxis.setTextColor(Color.BLACK);
////            yAxis.setTextSize(7f);
////            yAxis.setDrawGridLines(false);
////            yAxis.setDrawLabels(false);
////            yAxis.setAxisMaximum(70f);
//            yAxis.setValueFormatter(new IAxisValueFormatter() {
//                @Override
//                public String getFormattedValue(float value, AxisBase axis) {
//                    Log.i("valueO",Float.toString(value));
//                    if(value<highest &&value>lowest) {
//                        Log.i("value",find_nearest(value,YLabel));
//                        return find_nearest(value,YLabel);
//                    }else{
//                        return "";
//                    }
//                }
//            });


            YAxis yAxis = chart.getAxisLeft();
            yAxis.setTextColor(Color.parseColor("#333333"));
            YAxis rightYAxis = chart.getAxisRight();
            rightYAxis.setTextColor(Color.WHITE);
            yAxis.setTextColor(Color.WHITE);//x轴文字颜色
            rightYAxis.setTextSize(11f);
            yAxis.setTextSize(11f);
            rightYAxis.setEnabled(true);
            yAxis.setEnabled(true);

            yAxis.setTextSize(11f);
            yAxis.setDrawAxisLine(true);//是否绘制轴线
            yAxis.setDrawGridLines(false);
            rightYAxis.setDrawGridLines(false);
            yAxis.setDrawLabels(true);
            yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            yAxis.setGranularity(1f);
            yAxis.setEnabled(true);

            final float lowest = Collections.min(myTempLow);
            final float highest = Collections.max(myTempHigh);

            chart.getAxisLeft().setAxisMinimum(lowest-10);
            chart.getAxisLeft().setAxisMaximum(highest+10);
            chart.getAxisRight().setAxisMaximum(highest+10);
            chart.getAxisRight().setAxisMinimum(lowest-10);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

            dataSets.add(dataSet);
            dataSets.add(dataSet2);

            LineData data = new LineData (dataSets);
            data.setDrawValues(false);
            chart.setData(data);
            chart.invalidate(); // refresh
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


    }
}