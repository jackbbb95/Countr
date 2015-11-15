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

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CountrListFragment extends Fragment {

    //the ArrayAdapter
    private static ArrayList<Countr> countrArrayList;//testing data
    private static CustomListViewAdapter mCountrAdapter;
    private static TextView createText;

    public CountrListFragment() {
    }

    public ArrayList<Countr> getCountrArrayList(){return countrArrayList;}
    public CustomListViewAdapter getmCountrAdapter(){return mCountrAdapter;}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.countr_list_fragment, container, false);
        this.countrArrayList = new ArrayList<Countr>();
        //Countr exampleCountr = new Countr("This Won't Appear",0,1,0); //use to fill first position in countrArrayList
        //this.countrArrayList.add(exampleCountr);

        mCountrAdapter = new CustomListViewAdapter(
                //current context
                getActivity(),
                //ID of the list item layout
                R.layout.list_item_countr,
                //countr object array
                countrArrayList);

        ListView countrListView = (ListView) rootView.findViewById(R.id.listview_countr);
        countrListView.setAdapter(mCountrAdapter);


        createText = (TextView) rootView.findViewById(R.id.create_counter_textview);

        return rootView;
    }

    public void noCreateCountrText(){
        if(countrArrayList.size() > 0) {
            createText.setVisibility(View.GONE);
        }
    }



}
