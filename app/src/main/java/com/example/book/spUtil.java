package com.example.book;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class spUtil {

    //avoid contructor
    private spUtil(){}
    public static final String PREF_NAME = "BooksPreferences";
    public static final String POSITION= "position";
    public static final String QUERY = "query";



    //initialization
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    //read the string from the preferences
    public static String getPreferenceString(Context context, String key) {
        return getPrefs(context).getString(key, "");
    }

    //read the int from the preference
    public static int getPreferenceInt(Context context, String key) {
        return getPrefs(context).getInt(key, 0);
    }

    //use to write a string
    public static void setPreferenceString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    //use to write an integer
    public static void setPreferenceInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    //save only five query,keep track of the u
    public static ArrayList<String> getQueryList(Context context) {
        ArrayList<String> queryList = new ArrayList<String>();
        for (int i=1; i<=5; i++) {
            String query = getPrefs(context).getString(QUERY + String.valueOf(i), "");
            if (!query.isEmpty()) {
                query = query.replace(",", " ");
                queryList.add(query.trim());
            }
        }
        return queryList;
    }




}
