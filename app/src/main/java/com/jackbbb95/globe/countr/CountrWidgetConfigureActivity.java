package com.jackbbb95.globe.countr;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackbbb95.globe.countr.Activities.MainCountrActivity;
import com.jackbbb95.globe.countr.Handlers.CountrAdapter;
import com.jackbbb95.globe.countr.Handlers.DBHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * The configuration screen for the {@link CountrWidget CountrWidget} AppWidget.
 */
public class CountrWidgetConfigureActivity extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static final String PREFS_NAME = "Countr";
    private static final String PREF_CUR_NAME = "appwidget_";
    private static final String PREF_CUR_NUM = "countrNum";
    private static final String PREF_CUR_INTERVAL = "countrInterval";
    private ArrayAdapter<Countr> countrListAdapter;
    private String curCountrName;
    private long curCountrNum;
    private long curCountrInterval;


    public CountrWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.countr_widget_configure);

        final ArrayList<Countr> countrList = new MainCountrActivity().getCountrArrayList();
        countrListAdapter = new CountrAdapter( //creates the adapter
                //current context
                this,
                //ID of the list item layout
                R.layout.list_item_countr,
                //countr object array
                countrList);
        ListView mAppWidgetList = (ListView) findViewById(R.id.widget_list_view);
        mAppWidgetList.setAdapter(countrListAdapter);

        readArrayList(); //sets the adapter to hold all of the countrs the user has

        mAppWidgetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curCountrName = countrListAdapter.getItem(position).getName();
                curCountrNum = countrListAdapter.getItem(position).getCurrentNumber();
                curCountrInterval = countrListAdapter.getItem(position).getCountBy();
                //TODO THIS SHIT
            }
        });





        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        //mAppWidgetText.setText(loadCountrName(CountrWidgetConfigureActivity.this, mAppWidgetId));
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = CountrWidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
            String countrName = curCountrName;
            long countrCurNum = curCountrNum;
            long countrCurInterval = curCountrInterval; //TODO Send this shit through to the widget as well
            saveTitlePref(context, mAppWidgetId, countrName, countrCurNum, countrCurInterval);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            CountrWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String name, long current, long interval) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_CUR_NAME + appWidgetId, name);
        prefs.putLong(PREF_CUR_NUM + appWidgetId, current);
        prefs.putLong(PREF_CUR_INTERVAL + appWidgetId, interval);
        prefs.commit();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadCountrName(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_CUR_NAME + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.add_widget);
        }
    }

    static Long loadCountrNum(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        return prefs.getLong(PREF_CUR_NUM + appWidgetId,-1);
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_CUR_NAME + appWidgetId);
        prefs.commit();
    }

    //to ge the current array of countrs
    public void readArrayList(){
        Gson gson = new Gson();
        DBHelper myDB = new DBHelper(this);
        SQLiteDatabase db = myDB.getReadableDatabase();
        Type type = new TypeToken<ArrayList<Countr>>() {}.getType();
        String returnString = myDB.getCountr(db);
        ArrayList<Countr> tempArray;
        try {
            if (returnString != null) {
                tempArray = gson.fromJson(returnString, type);
                countrListAdapter.addAll(tempArray); //add all to the Listview adapter
                countrListAdapter.notifyDataSetChanged();
            }
        }catch(Exception e) {
            Log.d("ReadArray", e.getMessage());
        }

        myDB.close();
    }
}

