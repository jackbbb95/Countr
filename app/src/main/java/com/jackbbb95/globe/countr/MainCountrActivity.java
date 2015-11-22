package com.jackbbb95.globe.countr;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainCountrActivity extends AppCompatActivity implements CreateCountrDialogFrag.CreateCountrDialogListener {

    private CountrListFragment countrListFragment = new CountrListFragment(); //used to create an instance of the list of Countrs
    private ArrayList<Countr> countrArrayList = new ArrayList<>();
    private ArrayList<Countr> saveArray = new ArrayList<>();
    private DBHelper myDB;
    private Gson gson;


    public ArrayList<Countr> getCountrArrayList(){
        return countrArrayList;
    }

    /*
    Runs on the creation of the MainCountrActivity(the homescreen)
    Sets the layout, an actionbar, and a fab
    Layout includes content_main, which implements the countr_list_fragment layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_countr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ReadArrayList();

        //creates a fab that will lead to the create countr dialog
        FloatingActionButton addCountr = (FloatingActionButton) findViewById(R.id.addCountr);
        //on click, shows the CreateCountrDialog
        addCountr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateCountrDialog();
            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        SaveArrayList();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SaveArrayList();
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

    /*
    Shows the CreateCountrDialogFrag through the use of a fragment manager (allows activities to interact with fragments)
     */
    public void showCreateCountrDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CreateCountrDialogFrag createCountrDialog = new CreateCountrDialogFrag();
        createCountrDialog.show(fm, "fragment_create_countr");
    }

    /*
    Displays a Snackbar noting that the Countr has been created
    @param name is retrieved from the newly created countr to be displayed in the snackbar
    //TODO make this transition to a new activity in which the counting can actually be handled
     */
    public void onFinishCreateCountrDialog(String name){
        Snackbar.make(this.findViewById(R.id.listview_countr), "Countr '" + name + "' created", Snackbar.LENGTH_SHORT).show();
    }

    /*
    Through CreateCountrDialogListener, takes newly created countr and craetes Snackbar. Adds the countr to the countrArrayList
    that the listView reads through the adapter. Adapter is refreshed to display in the listView on the homescreen
    Also hides the "Create a New Countr" message
    @param newCountr is passed in through the listener when the 'create' button is clicked, with the set parameters
     */
    @Override
    public void onFinishCreateCountr(Countr newCountr) {
        onFinishCreateCountrDialog(newCountr.getName());
        saveArray.add(newCountr);
        countrListFragment.getmCountrAdapter().add(newCountr);
        countrListFragment.getmCountrAdapter().notifyDataSetChanged();
        countrListFragment.getCreateText().setVisibility(View.GONE);

    }

    /*
    Method used to save the arraylist on exit of the app
     */
    public void SaveArrayList(){
        this.deleteDatabase("CountrsDB"); //clears current database
        ArrayList<Countr> tempArrayList = new ArrayList<Countr>();
        for(Countr c : saveArray){
            tempArrayList.add(c);

        }
        gson = new Gson();
        Type type = new TypeToken<ArrayList<Countr>>() {}.getType();
        String inputString = gson.toJson(tempArrayList,type);
        myDB = new DBHelper(this);
        SQLiteDatabase db = myDB.getWritableDatabase();
        myDB.insertCountr(inputString,db); //rewrites the current Countrs into the database
        myDB.close();
    }

    /*
    Reads in the saved Countrs on creation of the activity
     */
    public void ReadArrayList(){
        gson = new Gson();
        myDB = new DBHelper(this);
        SQLiteDatabase db = myDB.getReadableDatabase();
        Type type = new TypeToken<ArrayList<Countr>>() {}.getType();
        String returnString = myDB.getCountr(db);
        ArrayList<Countr> tempArray;
        try {
            if (returnString != null) {
                tempArray = gson.fromJson(returnString, type);
                saveArray.addAll(tempArray); //loads the temporary array onto the current saveArray
                countrListFragment.getmCountrAdapter().addAll(saveArray /*tempArray*/); //add all to the Listview adapter
                countrListFragment.getmCountrAdapter().notifyDataSetChanged();
                countrListFragment.getCreateText().setVisibility(View.GONE); //Create Countr message disappear
            }
        }catch(Exception e) {
            Log.d("ReadArray", e.getMessage());
        }

        myDB.close();
    }



}
