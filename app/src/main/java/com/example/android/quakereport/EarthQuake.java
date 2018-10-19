package com.example.android.quakereport;

import java.net.URL;

public class EarthQuake {
    /*member variables*/
    private String mPlace;
    private double mMag;
    //private  long mDate;
    private String mDate;
    private String mTime;
    private String mEarthquakeWebsite;

    public EarthQuake(String place,double mag,String date, String time, String earthquakeWebsite){
        mPlace = place;
        mMag = mag;
        mDate = date;
        mTime = time;
        mEarthquakeWebsite = earthquakeWebsite;
    }

    public String getPlace() {
        return mPlace;
    }

    public double getMag() {
        return mMag;
    }

    public String getDate() {
        return mDate;
    }

    public String getTime() {
        return mTime;
    }
    public String getEarthquakeWebsite() {
        return mEarthquakeWebsite;
    }
}
