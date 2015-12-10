package com.jackbbb95.globe.countr.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.Fragments.CountingActivityFragment;
import com.jackbbb95.globe.countr.Fragments.CountrListFragment;
import com.jackbbb95.globe.countr.R;

import java.util.List;


public class CountingActivity extends AppCompatActivity {

    private static Countr curCountr;
    private int curPos;
    private boolean switchFab = false;
    private int useHardwareButtons;
    private FloatingActionButton fab;

    public boolean getSwitchFab() {
        return switchFab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        SharedPreferences countrPrefs = getSharedPreferences("countrPrefs", Activity.MODE_PRIVATE);

        useHardwareButtons = countrPrefs.getInt("useHB",0); //import settings prefs

        getCountr(); //gets the relevant countr through the intent
        Toolbar toolbar = (Toolbar) findViewById(R.id.counting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(curCountr.getName());
        final Context context = this;

        fab = (FloatingActionButton) findViewById(R.id.counting_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchFab) {
                    Snackbar.make(view, "Counting Up", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(R.drawable.ic_countr_plus);
                }
                if (!switchFab) {
                    Snackbar.make(view, "Counting Down", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(R.drawable.ic_countr_minus);
                }

                switchFab = !switchFab;

            }
        });

        final TextView curNum = getVisibleFragment().getCountrCurrentNumber();
        final TextView pops = getVisibleFragment().getIntervalPop();
        getVisibleFragment().getCountButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getSwitchFab()) {
                    curCountr.count(true, curNum, pops, context);
                } else { //if the fab is in the subtracting position, count down by interval(with popup)
                    curCountr.count(false, curNum, pops, context);
                }
            }
        });



        giveResult();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }




    public void giveResult() {
        Intent activityIntent = new Intent(this, MainCountrActivity.class);
        activityIntent.putExtra("NewCountr", curCountr);
        activityIntent.putExtra("Position", curPos);
        setResult(RESULT_OK, activityIntent);

        Intent fragIntent = new Intent(this, CountrListFragment.class);
        fragIntent.putExtra("NewCountr", curCountr);
        fragIntent.putExtra("Position", curPos);
        setResult(RESULT_OK, fragIntent);
    }

    /*
    Retrieves the relevant countr from the create dialog
     */
    public Countr getCountr() {
        Intent intent = getIntent();
        curCountr = (Countr) intent.getSerializableExtra("Countr");
        curPos = intent.getIntExtra("Position", -1);
        return curCountr;
    }

    public static void updateCountr(Countr countr) {
        curCountr = countr;
    }


    /*
    Change count with volume keys
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if(useHardwareButtons == 2 || useHardwareButtons == 0){ //if first use or if setting for hardware buttons is true
            int keyCode = event.getKeyCode();
            int action = event.getAction();
            TextView curNum = getVisibleFragment().getCountrCurrentNumber();
            TextView pops = getVisibleFragment().getIntervalPop();
            switch(keyCode){
                case KeyEvent.KEYCODE_VOLUME_UP:{
                    if(action == KeyEvent.ACTION_DOWN){
                        curCountr.count(true, curNum, pops, this);
                        fab.setImageResource(R.drawable.ic_countr_plus);
                        switchFab = false;
                    }
                    return true;
                }
                case KeyEvent.KEYCODE_VOLUME_DOWN:{
                    if(action == KeyEvent.ACTION_DOWN){
                        curCountr.count(false, curNum, pops, this);
                        fab.setImageResource(R.drawable.ic_countr_minus);
                        switchFab = true;
                    }
                    return true;
                }
                default: return super.dispatchKeyEvent(event);
            }
        }

        return super.dispatchKeyEvent(event);
    }

    //Get the active fragment
    public CountingActivityFragment getVisibleFragment(){
        FragmentManager fragmentManager = CountingActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for(Fragment fragment : fragments){
            if(fragment != null && fragment.getUserVisibleHint())
                return (CountingActivityFragment)fragment;
        }
        return null;
    }

}

