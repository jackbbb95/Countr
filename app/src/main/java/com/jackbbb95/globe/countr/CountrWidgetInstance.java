package com.jackbbb95.globe.countr;


import android.content.Context;
import android.content.SharedPreferences;

public class CountrWidgetInstance {
    private CharSequence widgetText;
    private long widgetNum;
    private long interval;
    private int id ;
    private static final String PREFS_NAME = "Countr";

    public CountrWidgetInstance(CharSequence name, long num, long interval, int id){
        this.widgetText = name;
        this.widgetNum = num;
        this.interval = interval;
        this.id = id;
    }
    public CountrWidgetInstance(){}

    public CharSequence getWidgetText(){return widgetText;}
    public long getWidgetNum(){
        return widgetNum;
    }
    public long getInterval(){
        return interval;
    }
    public int getId(){return id;}

    public void setWidgetText(String i){widgetText = i;}
    public void setWidgetNum(long i){widgetNum = i;}
    public void setInterval(long i){interval = i;}
    public void setId(int i, Context context){
        SharedPreferences.Editor prefsEdit = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        if(this.id != i){
            prefsEdit.putInt("CountrID",i);
            prefsEdit.commit();
            this.id = i;
        } //TODO Remember you changed this shit
    }
}
