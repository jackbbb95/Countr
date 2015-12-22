package com.jackbbb95.globe.countr.Handlers;

import android.app.Application;
import android.content.Context;

//class used in case there needs to be reference to the activity
public class MyApplication extends Application{
    private static Context context;
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

}
