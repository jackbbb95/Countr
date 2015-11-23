package com.jackbbb95.globe.countr.Fragments;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jackbbb95.globe.countr.Activities.CountingActivity;
import com.jackbbb95.globe.countr.Activities.MainCountrActivity;
import com.jackbbb95.globe.countr.Countr;
import com.jackbbb95.globe.countr.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CountingActivityFragment extends Fragment {

    private TextView countrCurrentNumber;
    private Button add;
    private int num;
    private int interval;
    public CountingActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_counting, container, false);

        interval = ((CountingActivity) getActivity()).getCountr().getCountBy();


        //For the Current Count Number
        countrCurrentNumber = (TextView) rootView.findViewById(R.id.current_number_tv);
        num = ((CountingActivity) getActivity()).getCountr().getCurrentNumber();
        countrCurrentNumber.setText(String.valueOf(num));

        //For the button background which adds to the countr
        add = (Button) rootView.findViewById(R.id.add_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Countr tempCountr = ((CountingActivity) getActivity()).getCountr();
                tempCountr.setCurrentNumber(tempCountr.getCurrentNumber() + interval);
                countrCurrentNumber.setText(String.valueOf(tempCountr.getCurrentNumber()));
                ((CountingActivity) getActivity()).updateCountr(tempCountr);

            }
        });

        return rootView;
    }

}
