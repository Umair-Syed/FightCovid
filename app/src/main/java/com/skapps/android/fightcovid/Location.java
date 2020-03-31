package com.skapps.android.fightcovid;

/**
 * Created by Syed Umair on 31/03/2020.
 */
public class Location {

    private int mCount;
    private String mLocation;

    public Location(int count, String location){
        mCount = count;
        mLocation = location;
    }

    public int getmCount() {
        return mCount;
    }

    public String getmLocation() {
        return mLocation;
    }
}
