package com.jackbbb95.globe.countr;

public  class Countr {
    private String name;
    private int startNumber;
    private int totalCounted;
    private int countBy;
    private int currentNumber = startNumber+totalCounted;

    public Countr(String name, int startNumber, int countBy, int currentNumber) {
        this.name = name;
        this.startNumber = startNumber;
        this.countBy = countBy;
        this.currentNumber = currentNumber;
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
