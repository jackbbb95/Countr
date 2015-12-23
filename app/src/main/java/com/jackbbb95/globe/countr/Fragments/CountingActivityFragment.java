package com.jackbbb95.globe.countr.Fragments;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jackbbb95.globe.countr.Activities.CountingActivity;
import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.R;

//note that the actual ounting is handled in the activity that this fragment belongs to
public class CountingActivityFragment extends Fragment {

    private TextView countrCurrentNumber;
    private Button countButton;
    private TextView intervalPop;
    private Countr theCountr;

    public TextView getCountrCurrentNumber(){return countrCurrentNumber;}
    public TextView getIntervalPop(){return intervalPop;}
    public Button getCountButton(){return countButton;}

    //default constructor
    public CountingActivityFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_counting, container, false);
        //get the current countr that will be counting
        theCountr = ((CountingActivity)getActivity()).getCountr();
        //configure the textview that shows the actual current number of the countr
        countrCurrentNumber = (TextView) rootView.findViewById(R.id.current_number_tv);
        Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto.light.ttf");
        countrCurrentNumber.setTypeface(robotoLight);
        countrCurrentNumber.setText(String.valueOf(theCountr.getCurrentNumber()));
        //configure the popups that show the interval of the countr as you count
        intervalPop = (TextView) rootView.findViewById(R.id.interval_pop);
        intervalPop.setTypeface(robotoLight);
        //For the button background which adds to the countr
        countButton = (Button) rootView.findViewById(R.id.count_button);

        return rootView;
    }

}
