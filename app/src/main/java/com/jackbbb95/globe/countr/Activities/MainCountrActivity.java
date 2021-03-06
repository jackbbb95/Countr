package com.jackbbb95.globe.countr.Activities;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.Fragments.CountrListFragment;
import com.jackbbb95.globe.countr.Fragments.CreateCountrDialogFrag;
import com.jackbbb95.globe.countr.Handlers.DBHelper;
import com.jackbbb95.globe.countr.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainCountrActivity extends AppCompatActivity implements CreateCountrDialogFrag.CreateCountrDialogListener {

    private CountrListFragment countrListFragment = new CountrListFragment(); //used to create an instance of the list of Countrs
    private ArrayList<Countr> countrArrayList = new ArrayList<>();
    private DBHelper myDB;
    private Gson gson;
    private FloatingActionButton addCountr;

    public FloatingActionButton getAddCountr(){return addCountr;}

    public ArrayList<Countr> getCountrArrayList(){return countrArrayList;}
    public CountrListFragment getCountrListFragment(){return countrListFragment;}

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
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        readArrayList();
        PreferenceManager.setDefaultValues(this,R.xml.pref_general,false);
        if(getCountrListFragment().getmCountrAdapter().getCount() == 0)
            countrListFragment.getCreateText().setVisibility(View.VISIBLE);


        //creates a fab that will lead to the create countr dialog
        addCountr = (FloatingActionButton) findViewById(R.id.addCountr);

        //on click, shows the CreateCountrDialog
        addCountr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateCountrDialog();
            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();
        saveArrayList(); //save the array in case of device rotation
    }

    @Override
    protected void onPause(){
        super.onPause();
        saveArrayList(); //save the array
    }

    @Override
    protected void onStop(){
        super.onStop();
        saveArrayList(); //save the array
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_countr_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
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

     */
    public void onFinishCreateCountrDialog(String name){
        Toast.makeText(MainCountrActivity.this, "Countr '" + name + "' created", Toast.LENGTH_SHORT).show();
    }

    /*
    Through CreateCountrDialogListener, takes newly created countr and craetes Snackbar. Adds the countr to the countrArrayList
//    that the listView reads through the adapter. Adapter is refreshed to display in the listView on the homescreen
    Also hides the "Create a New Countr" message
    Opens the Activity where the user counts
    @param newCountr is passed in through the listener when the 'create' button is clicked, with the set parameters
     */
    @Override
    public void onFinishCreateCountr(Countr newCountr) {
        countrListFragment.getmCountrAdapter().add(newCountr);
        countrListFragment.getmCountrAdapter().notifyDataSetChanged();
        countrListFragment.getCreateText().setVisibility(View.GONE);
        onFinishCreateCountrDialog(newCountr.getName());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            int curPos = data.getIntExtra("Position",-1);
            Countr newCountr = (Countr)data.getSerializableExtra("NewCountr");
            countrArrayList.set(curPos,newCountr);
            updateList();
        }
        super.onActivityResult(resultCode, requestCode, data);
    }


    public void updateList(){
        countrListFragment.getmCountrAdapter().notifyDataSetChanged();
    }


    /*
    Method used to save the arraylist on exit of the app
     */
    public void saveArrayList(){
        this.deleteDatabase("CountrsDB"); //clears current database
        ArrayList<Countr> tempArrayList = new ArrayList<Countr>();
        for(int i = 0; i < countrListFragment.getmCountrAdapter().getCount(); i++) {
            tempArrayList.add(countrListFragment.getmCountrAdapter().getItem(i));
        }

        gson = new Gson();
        Type type = new TypeToken<ArrayList<Countr>>() {}.getType();
        String inputString = gson.toJson(tempArrayList, type);
        myDB = new DBHelper(this);
        SQLiteDatabase db = myDB.getWritableDatabase();
        myDB.insertCountr(inputString, db); //rewrites the current Countrs into the database
        myDB.close();
    }

    /*
    Reads in the saved Countrs on creation of the activity
     */
    public void readArrayList(){
        gson = new Gson();
        myDB = new DBHelper(this);
        SQLiteDatabase db = myDB.getReadableDatabase();
        Type type = new TypeToken<ArrayList<Countr>>() {}.getType();
        String returnString = myDB.getCountr(db);
        ArrayList<Countr> tempArray;
        try {
            if (returnString != null) {
                tempArray = gson.fromJson(returnString, type);
                countrListFragment.getmCountrAdapter().addAll(tempArray); //add all to the Listview adapter
                countrListFragment.getmCountrAdapter().notifyDataSetChanged();
                countrListFragment.getCreateText().setVisibility(View.GONE); //Create Countr message disappear
            }
        }catch(Exception e) {
            Log.d("ReadArray", e.getMessage());
        }

        myDB.close();
    }

}

