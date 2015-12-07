package com.jackbbb95.globe.countr.Fragments;

import android.app.Activity;
import android.content.Context;
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
    private int num;
    private int interval;
    private int displayWidth;
    private int displayHeight;
    private TextView intervalPop;
    private CountingActivity countAct;



    public CountingActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_counting, container, false);

        interval = ((CountingActivity) getActivity()).getCountr().getCountBy();


        //For the Current Count Number
        countrCurrentNumber = (TextView) rootView.findViewById(R.id.current_number_tv);
        num = ((CountingActivity) getActivity()).getCountr().getCurrentNumber();
        countrCurrentNumber.setText(String.valueOf(num));

        //setup interval count popups
        intervalPop = (TextView) rootView.findViewById(R.id.interval_pop);
        final Random r = new Random();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        displayWidth = metrics.widthPixels;
        displayHeight = metrics.heightPixels;

        //setup fade animation for interval popup
        final AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(700);
        fadeOut.setFillAfter(true);

        //For the button background which adds to the countr
        countButton = (Button) rootView.findViewById(R.id.count_button);
        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //if the fab is in adding position, count up by interval(with popup)
                if (((CountingActivity) getActivity()).getSwitchFab() == false) {
                    countUp();

                } else { //if the fab is in the subtracting position, count down by interval(with popup)
                    countDown();
                }

            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof CountingActivity) countAct = (CountingActivity) context;

    }

    public void countUp() {
        final Random r = new Random();
        //setup fade animation for interval popup
        final AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(700);
        fadeOut.setFillAfter(true);

        Countr tempCountr = countAct.getCountr();
//TODO Still working through hadware key usage
        num = tempCountr.getCurrentNumber();
        tempCountr.setCurrentNumber(tempCountr.getCurrentNumber() + interval);
        countrCurrentNumber.setText(String.valueOf(tempCountr.getCurrentNumber()));
        ((CountingActivity) getActivity()).updateCountr(tempCountr);
            //For the interval Popup
        intervalPop.setText("+" + interval);
        float randWidth = 150 + r.nextFloat() * (displayWidth - (float) (displayWidth / 4));
        float randHeight = 200 + r.nextFloat() * (displayHeight - (float) (displayHeight / 3));
        while (randWidth > displayWidth / 2.9 && randWidth < displayWidth / 1.3
                && randHeight > displayHeight / 2.7 && randHeight < displayHeight / 1.5) {
            randHeight = 200 + r.nextFloat() * (displayHeight - (float) (displayHeight / 3));
            randWidth = 150 + r.nextFloat() * (displayWidth - (float) (displayWidth / 4));
        }
        intervalPop.setY(randHeight);
        intervalPop.setX(randWidth);
        intervalPop.startAnimation(fadeOut);
    }


    public void countDown() {
        final Random r = new Random();
        //setup fade animation for interval popup
        final AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(700);
        fadeOut.setFillAfter(true);

        Countr tempCountr = ((CountingActivity) getActivity()).getCountr();
        num = tempCountr.getCurrentNumber();
        tempCountr.setCurrentNumber(tempCountr.getCurrentNumber() - interval);
        countrCurrentNumber.setText((String.valueOf(tempCountr.getCurrentNumber())));
        ((CountingActivity) getActivity()).updateCountr(tempCountr);

        //For the interval Popup
        intervalPop.setText("-" + interval);
        float randWidth = 300 + r.nextFloat() * (displayWidth - 500);
        float randHeight = 200 + r.nextFloat() * (displayHeight - 800);
        while (randWidth > displayWidth / 2.9 && randWidth < displayWidth / 1.3
                && randHeight > displayHeight / 2.7 && randHeight < displayHeight / 1.5) {
            randHeight = 200 + r.nextFloat() * (displayHeight - (float) (displayHeight / 3));
            randWidth = 150 + r.nextFloat() * (displayWidth - (float) (displayWidth / 4));
        }
        intervalPop.setY(randHeight);
        intervalPop.setX(randWidth);
        intervalPop.startAnimation(fadeOut);
    }



//TODO FIGURE OUT THE VOLUME KEYS

}
