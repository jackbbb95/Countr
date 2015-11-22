package com.jackbbb95.globe.countr;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public  class Countr implements Serializable{
    private String name; //holds the name of the countr
    private int startNumber; //holds the set number that the countr starts at
    private int totalCounted; //keeps count of the total number that has been counted
    private int countBy; //the interval of each count
    private int currentNumber = startNumber+totalCounted; //the current number that the counter displays


    public Countr(String name, int startNumber, int countBy, int currentNumber, int totalCounted) {
        this.name = name;
        this.startNumber = startNumber;
        this.countBy = countBy;
        this.currentNumber = currentNumber;
        this.totalCounted = totalCounted;

    }

    public String getName(){return this.name;}
    public int getStartNumber(){return this.startNumber;}
    public int getCountBy(){return this.countBy;}
    public int getCurrentNumber(){return this.currentNumber;}

    public void setName(String newName){this.name = newName;}
    public void setStartNumber(int newStartNumber){this.startNumber = newStartNumber;}
    public void setCountBy(int newCountBy){this.countBy = newCountBy;}
    public void setCurrentNumber(int newCurrentNumber){this.currentNumber = newCurrentNumber;}



}
