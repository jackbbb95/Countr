package com.jackbbb95.globe.countr.Handlers;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jackbbb95.globe.countr.R;

/**
 * Created by Bogle on 12/11/2015.
 */
public class MyApplication extends Application{
    private static Context context;
    private static int themeId;
    public void onCreate() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = prefs.getString("theme","none");
        if(theme.equals("1")){
            setTheme(R.style.AppTheme_Dark_NoActionBar);
        }
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

}
