package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuake>> {
     private String mUrl;

     public EarthquakeLoader(Context context,String url){
         super(context);
         mUrl = url;
     }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<EarthQuake> loadInBackground() {
        String JSON = "";
        URL url  = QueryUtils.createUrl(mUrl);
        try {
            JSON = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<EarthQuake> earthQuakes = new ArrayList<>();
        earthQuakes = QueryUtils.extractEarthquakes(JSON);
        return earthQuakes;
    }

}
