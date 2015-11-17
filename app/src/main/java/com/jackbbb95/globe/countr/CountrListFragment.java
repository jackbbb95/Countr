package com.jackbbb95.globe.countr;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A ListView fragment that displays the users created Countrs
 */
public class CountrListFragment extends Fragment {

    //the ArrayAdapter
    private ArrayList<Countr> countrArrayList; //the ArrayList in which each Countr item to be displayed, is placed
    private static CustomListViewAdapter mCountrAdapter; //the adapter that the ArrayList is set to
    private static TextView createText; //the TextView that prompts the user to create a new Countr

    public CountrListFragment() {} //default constructor

    //public ArrayList<Countr> getCountrArrayList(){return countrArrayList;} //returns the ArrayList
    public CustomListViewAdapter getmCountrAdapter(){return mCountrAdapter;} //returns the Adapter for the ArrayList
    public TextView getCreateText(){
        return createText;
    }


    /*
    Creates and inflates the ListView that contains the Countrs with an adapter providing the layout. Also shows the
    "Create a New Countr" message
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.countr_list_fragment, container, false); //sets the CountrList to inflate
        countrArrayList = ((MainCountrActivity)getActivity()).getCountrArrayList(); //creates the ArrayList for the Countrs

        mCountrAdapter = new CustomListViewAdapter( //creates the adapter
                //current context
                getActivity(),
                //ID of the list item layout
                R.layout.list_item_countr,
                //countr object array
                countrArrayList);

        ListView countrListView = (ListView) rootView.findViewById(R.id.listview_countr); //sets the layout of the ListView to be listview_countr
        countrListView.setAdapter(mCountrAdapter); //sets the adapter to the ListView
        createText = (TextView) rootView.findViewById(R.id.create_counter_textview); //shows the "Create a New Countr" message for when there is no Countr

        return rootView; //inflates CountrList
    }
}
