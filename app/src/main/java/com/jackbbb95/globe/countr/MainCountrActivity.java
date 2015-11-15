package com.jackbbb95.globe.countr;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainCountrActivity extends AppCompatActivity implements CreateCountrDialogFrag.CreateCountrDialogListener {

    private CountrListFragment countrListFragment = new CountrListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_countr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //creates a fab that will lead to the create dialog
        //TODO Make FAB open CreateCountrDialogFrag
        FloatingActionButton addCountr = (FloatingActionButton) findViewById(R.id.addCountr);
        addCountr.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showCreateCountrDialog();


                //list.getmCountrAdapter().notifyDataSetChanged();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_countr_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showCreateCountrDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CreateCountrDialogFrag createCountrDialog = new CreateCountrDialogFrag();
        createCountrDialog.show(fm, "fragment_create_countr");


    }

    public void onFinishCreateCountrDialog(String name){
        Snackbar.make(this.findViewById(R.id.listview_countr),"Countr '" + name + "' created", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public Countr onFinishCreateCountr(Countr newCountr) {

        onFinishCreateCountrDialog(newCountr.getName());

        countrListFragment.getmCountrAdapter().add(newCountr);
        countrListFragment.getmCountrAdapter().notifyDataSetChanged();
        countrListFragment.noCreateCountrText();

        return newCountr;

    }
}
