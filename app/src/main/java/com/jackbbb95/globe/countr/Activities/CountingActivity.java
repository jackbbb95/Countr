package com.jackbbb95.globe.countr.Activities;


import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.Fragments.CountingActivityFragment;
import com.jackbbb95.globe.countr.Fragments.CountrListFragment;
import com.jackbbb95.globe.countr.R;

public class CountingActivity extends AppCompatActivity {

    private Countr curCountr;
    private int curPos;
    private boolean switchFab = false;
    private CountingActivityFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        getCountr(); //gets the relevant countr through the intent
        Toolbar toolbar = (Toolbar) findViewById(R.id.counting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(curCountr.getName());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.counting_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!switchFab) {
                    Snackbar.make(view, "Counting Down", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(android.R.drawable.arrow_down_float);
                }
                if (switchFab) {
                    Snackbar.make(view, "Counting Up", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(android.R.drawable.arrow_up_float);
                }

                switchFab = !switchFab;

            }
        });


        giveResult();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        //SaveArray();
    }


    public boolean getSwitchFab() {
        return switchFab;
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

    public void updateCountr(Countr countr) {
        curCountr = countr;
    }


    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            frag = new CountingActivityFragment();
            frag.countUp();
            return true;
        } //TODO FIX VOLUME KEY IMPLEMENTATION
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            frag = new CountingActivityFragment();
            frag.countDown();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }



}

