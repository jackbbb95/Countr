package com.jackbbb95.globe.countr.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
import com.jackbbb95.globe.countr.Activities.CountingActivity;
import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.R;




/**
 * A placeholder fragment containing a simple view.
 */
public class CountingActivityFragment extends Fragment {

    private TextView countrCurrentNumber;
    private Button countButton;
    private TextView intervalPop;
    private Countr theCountr;


    public TextView getCountrCurrentNumber(){return countrCurrentNumber;}
    public TextView getIntervalPop(){return intervalPop;}
    public Button getCountButton(){return countButton;}

    public CountingActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_counting, container, false);
        theCountr = ((CountingActivity)getActivity()).getCountr();
        //For the Current Count Number
        countrCurrentNumber = (TextView) rootView.findViewById(R.id.current_number_tv);
        Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto.light.ttf");
        countrCurrentNumber.setTypeface(robotoLight);
        countrCurrentNumber.setText(String.valueOf(theCountr.getCurrentNumber()));
        intervalPop = (TextView) rootView.findViewById(R.id.interval_pop);
        intervalPop.setTypeface(robotoLight);
        //For the button background which adds to the countr
        countButton = (Button) rootView.findViewById(R.id.count_button);

        return rootView;
    }

}
