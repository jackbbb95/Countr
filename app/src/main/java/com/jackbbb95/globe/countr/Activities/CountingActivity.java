package com.jackbbb95.globe.countr.Activities;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.Fragments.CountrListFragment;
import com.jackbbb95.globe.countr.R;

public class CountingActivity extends AppCompatActivity {

    private Countr curCountr;
    private int curPos;
    private boolean switchFab = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting);
        getCountr(); //gets the relevant countr through the intent
        Toolbar toolbar = (Toolbar) findViewById(R.id.counting_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(curCountr.getName());

        final Matrix matrix = new Matrix();
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.counting_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, curCountr.getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fab.setScaleType(ImageView.ScaleType.MATRIX);
                matrix.postRotate((float) 90,42,42);
                fab.setImageMatrix(matrix);
                switchFab = !switchFab;

            }
        });


        giveResult();
    }


    public boolean getSwitchFab(){return switchFab;}

    public void giveResult(){
        Intent activityIntent = new Intent(this,MainCountrActivity.class);
        activityIntent.putExtra("NewCountr", curCountr);
        activityIntent.putExtra("Position", curPos);
        setResult(RESULT_OK, activityIntent);

        Intent fragIntent = new Intent(this, CountrListFragment.class);
        fragIntent.putExtra("NewCountr", curCountr);
        fragIntent.putExtra("Position", curPos);
        setResult(RESULT_OK,fragIntent);
    }

    /*
    Retrieves the relevant countr from the create dialog
     */
    public Countr getCountr(){
        Intent intent = getIntent();
        curCountr = (Countr) intent.getSerializableExtra("Countr");
        curPos = intent.getIntExtra("Position", -1);
        return curCountr;
    }

    public void updateCountr(Countr countr){
        curCountr = countr;
    }
}
