package com.jackbbb95.globe.countr;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CountrListFragment extends Fragment {

    //the ArrayAdapter
    private CustomListViewAdapter mCountrAdapter;

    public CountrListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.countr_list_fragment, container, false);


        ArrayList<Countr> countrArrayList = new ArrayList<Countr>();//testing data

        for(int j=0; j<12; j++){
            countrArrayList.add(j, new Countr("Counter Number: " + (j + 1), 0, 1,0));
        }
        //String[] stringy = new String[countrArrayList.size()-1];

        //for(int j=0; j<countrArrayList.size()-1; j++){
            //stringy[j] = countrArrayList.get(j).getName();



        CustomListViewAdapter mCountrAdapter = new CustomListViewAdapter(
                //current context
                getActivity(),
                //ID of the list item layout
                R.layout.list_item_countr,
                //countr object array
                countrArrayList);

        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_countr);
        listView.setAdapter(mCountrAdapter);


        return rootView;
    }

}
