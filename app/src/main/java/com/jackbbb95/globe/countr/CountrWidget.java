package com.jackbbb95.globe.countr;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link CountrWidgetConfigureActivity CountrWidgetConfigureActivity}
 */
public class CountrWidget extends AppWidgetProvider {

    private static final String PLUS_CLICKED = "plusButtonClicked";
    private static final String MINUS_CLICKED = "minusButtonClicked";
    private static CountrWidgetInstance countr;
    private static ArrayList<CountrWidgetInstance> list = new ArrayList<>();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of the
        /*
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            CountrWidgetInstance c = new CountrWidgetInstance();
            c.setId(appWidgetIds[i], context);
            list.add(c);
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
        */
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            CountrWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        countr = new CountrWidgetInstance();
        countr.setWidgetText(CountrWidgetConfigureActivity.loadCountrName(context, appWidgetId));
        countr.setWidgetNum(CountrWidgetConfigureActivity.loadCountrNum(context, appWidgetId));
        countr.setInterval(CountrWidgetConfigureActivity.loadCountrInterval(context, appWidgetId));
        countr.setId(appWidgetId,context);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.countr_widget);
        views.setTextViewText(R.id.appwidget_text, countr.getWidgetText());
        views.setTextViewText(R.id.current_number_widget_tv, String.valueOf(countr.getWidgetNum()));

        Intent intentPlus = new Intent(context,CountrWidget.class);
        intentPlus.setAction(PLUS_CLICKED);
        intentPlus.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        Intent intentMinus = new Intent(context,CountrWidget.class);
        intentMinus.setAction(MINUS_CLICKED);
        intentMinus.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        views.setOnClickPendingIntent(R.id.widget_plus_button, PendingIntent.getBroadcast(context, appWidgetId, intentPlus, 0));
        views.setOnClickPendingIntent(R.id.widget_minus_button, PendingIntent.getBroadcast(context, appWidgetId, intentMinus, 0));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        int widgetId = 0;
        if(extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }


        if (PLUS_CLICKED.equals(intent.getAction())) {
            CountrWidgetInstance curCountr = new CountrWidgetInstance();
            for(CountrWidgetInstance c : list){
                if(c.getId() == widgetId){
                    curCountr = c;
                }
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.countr_widget);

            curCountr.setWidgetNum(curCountr.getWidgetNum() + curCountr.getInterval());
            //CountrWidgetConfigureActivity.savePrefs(context, widgetId, curCountr.getWidgetText().toString(), curCountr.getWidgetNum(), curCountr.getInterval());

            remoteViews.setTextViewText(R.id.current_number_widget_tv,String.valueOf(curCountr.getWidgetNum()));

            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
//TODO Keep working through having different data sets for each widget
        else if (MINUS_CLICKED.equals(intent.getAction())) {
            CountrWidgetInstance curCountr = new CountrWidgetInstance();
            for(CountrWidgetInstance c : list){
                if(c.getId() == widgetId){
                    curCountr = c;
                }
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.countr_widget);

            curCountr.setWidgetNum(curCountr.getWidgetNum() - curCountr.getInterval());
            //CountrWidgetConfigureActivity.savePrefs(context, widgetId, curCountr.getWidgetText().toString(), curCountr.getWidgetNum(), curCountr.getInterval());

            remoteViews.setTextViewText(R.id.current_number_widget_tv,String.valueOf(curCountr.getWidgetNum()));

            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }else{
            super.onReceive(context, intent);
        }

    }

    public static void addToList(CountrWidgetInstance c){
        list.add(c);
    }

}

