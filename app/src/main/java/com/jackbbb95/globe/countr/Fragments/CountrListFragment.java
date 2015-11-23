package com.jackbbb95.globe.countr.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jackbbb95.globe.countr.Activities.CountingActivity;
import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.Handlers.CountrAdapter;
import com.jackbbb95.globe.countr.Activities.MainCountrActivity;
import com.jackbbb95.globe.countr.R;

import java.util.ArrayList;

/**
 * A ListView fragment that displays the users created Countrs
 */
public class CountrListFragment extends Fragment {

    //the ArrayAdapter
    private ArrayList<Countr> countrArrayList; //the ArrayList in which each Countr item to be displayed, is placed
    private static CountrAdapter mCountrAdapter; //the adapter that the ArrayList is set to
    private static TextView createText; //the TextView that prompts the user to create a new Countr
    private int curCountrPos = -1;
    private String CUR_POS = "Current Countr Position";
    private boolean beforeCount = true;

    public CountrListFragment() {} //default constructor

    //public ArrayList<Countr> getCountrArrayList(){return countrArrayList;} //returns the ArrayList
    public CountrAdapter getmCountrAdapter(){return mCountrAdapter;} //returns the Adapter for the ArrayList
    public TextView getCreateText(){return createText;}
    public ArrayList<Countr> getCountrArrayList(){return countrArrayList;}
    public int getCurCountrPos(){return curCountrPos;}

    /*
    Creates and inflates the ListView that contains the Countrs with an adapter providing the layout. Also shows the
    "Create a New Countr" message
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.countr_list_fragment, container, false); //sets the CountrList to inflate

        countrArrayList = ((MainCountrActivity)getActivity()).getCountrArrayList(); //creates the ArrayList for the Countrs

        mCountrAdapter = new CountrAdapter( //creates the adapter
                //current context
                getActivity(),
                //ID of the list item layout
                R.layout.list_item_countr,
                //countr object array
                countrArrayList);

        ListView countrListView = (ListView) rootView.findViewById(R.id.listview_countr); //sets the layout of the ListView to be listview_countr
        countrListView.setAdapter(mCountrAdapter); //sets the adapter to the ListView
        createText = (TextView) rootView.findViewById(R.id.create_counter_textview); //shows the "Create a New Countr" message for when there is no Countr

        countrListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curCountrPos = position;
                beforeCount = false;
                Countr countr = (Countr) parent.getAdapter().getItem(position);
                Intent intent = new Intent(getActivity(),CountingActivity.class);
                intent.putExtra("Countr",countr);
                intent.putExtra("Position",position);
                startActivityForResult(intent, 1);

            }
        });

        return rootView; //inflates CountrList
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Countr newCountr = (Countr) data.getSerializableExtra("NewCountr");
            int curPos = data.getIntExtra("Position",-1);
            ((MainCountrActivity) getActivity()).getSaveArray().set(curPos, newCountr);
            mCountrAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt(CUR_POS,curCountrPos);
        savedInstanceState.putBoolean("Before Count",beforeCount);
        super.onSaveInstanceState(savedInstanceState);
    }
}
