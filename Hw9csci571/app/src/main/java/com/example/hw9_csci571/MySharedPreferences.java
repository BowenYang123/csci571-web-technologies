//package com.example.hw9_csci571;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class MySharedPreferences {
//
//    //创建一个SharedPreferences    类似于创建一个数据库，库名为 data
//    public static SharedPreferences share(Context context){
//        SharedPreferences sharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE);
//        return sharedPreferences;
//    }
//
//
//    public static Object getName(Context context,String flag){
//        return share(context).getString(flag,null);
//    }
//
//    public static boolean setName(String name,String flag, Context context){
//        SharedPreferences.Editor e = share(context).edit();
//        e.putString(flag,name);
//        Boolean bool = e.commit();
//        return bool;
//    }
//
//    //string for search
//    public static String getPswd(Context context){
//        return share(context).getString("localString",null);
//    }
//    //这里使用的是 apply() 方法保存，将不会有返回值
//    public static void setPswd(String pswd, Context context){
//        SharedPreferences.Editor e = share(context).edit();
//        e.putString("localString",pswd);
//        e.commit();
//    }
//
//    public static void clear(Context context){
//        SharedPreferences.Editor e = share(context).edit();
//        e.clear();
//        e.commit();
//    }
//}