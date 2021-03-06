package com.jackbbb95.globe.countr;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.jackbbb95.globe.countr.Activities.CountingActivity;

import java.io.Serializable;
import java.util.Random;

public  class Countr implements Serializable{
    private String name; //holds the name of the countr
    private long startNumber; //holds the set number that the countr starts at
    private long totalCounted; //keeps count of the total number that has been counted
    private long countBy; //the interval of each count
    private long currentNumber = startNumber+totalCounted; //the current number that the counter displays

    public Countr(String name, int startNumber, int countBy, int currentNumber, int totalCounted) {
        this.name = name;
        this.startNumber = startNumber;
        this.countBy = countBy;
        this.currentNumber = currentNumber;
        this.totalCounted = totalCounted;
    }


    public String getName(){return this.name;}
    public long getStartNumber(){return this.startNumber;}
    public long getCountBy(){return this.countBy;}
    public long getCurrentNumber(){return this.currentNumber;}

    public void setName(String newName){this.name = newName;}
    public void setStartNumber(int newStartNumber){this.startNumber = newStartNumber;}
    public void setCountBy(int newCountBy){this.countBy = newCountBy;}
    public void setCurrentNumber(int newCurrentNumber){this.currentNumber = newCurrentNumber;}

    //the method that will change the current number and will show the interval textviews
    public void count(boolean addOrSubtract,TextView curNum,TextView pops,Context context){
        final Random r = new Random();
        //setup fade animation for interval popup
        final AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(700);
        fadeOut.setFillAfter(true);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int displayWidth = metrics.widthPixels;
        int displayHeight = metrics.heightPixels;


        if(addOrSubtract){
            currentNumber = getCurrentNumber() + getCountBy();
            curNum.setText(String.valueOf(currentNumber));
            CountingActivity.updateCountr(this);
            //For the interval Popup
            pops.setText(String.format("+%d", countBy));
        }
        if(!addOrSubtract){
            currentNumber = getCurrentNumber() - getCountBy();
            curNum.setText(String.valueOf(currentNumber));
            CountingActivity.updateCountr(this);
            //For the interval Popup
            pops.setText(String.format("-%d", countBy));
        }

        //the algorithm for the random locations that the interval will pop up on each count
        float randWidth = 150 + r.nextFloat() * (displayWidth - (float) (displayWidth / 4));
        float randHeight = 200 + r.nextFloat() * (displayHeight - (float) (displayHeight / 3));
        while (randWidth > displayWidth / 2.9 && randWidth < displayWidth / 1.3
                && randHeight > displayHeight / 2.7 && randHeight < displayHeight / 1.5) {
            randHeight = 200 + r.nextFloat() * (displayHeight - (float) (displayHeight / 3));
            randWidth = 150 + r.nextFloat() * (displayWidth - (float) (displayWidth / 4));
        }
        pops.setY(randHeight);
        pops.setX(randWidth);
        pops.startAnimation(fadeOut);
    }

}
