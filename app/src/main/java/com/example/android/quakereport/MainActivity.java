package com.example.android.quakereport;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final String USGS_REQUSRT_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    ListView earthquakeListView;
    earthquakesAdapter earthquakesAdapter;
    List<EarthQuake> earthquakes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a fake list of earthquake locations.
        /*earthquakes.add(new EarthQuake("San Francisco",7.2,"Feb 2,2016"));
        earthquakes.add(new EarthQuake("London",6.1,"July 20,2015"));
        earthquakes.add(new EarthQuake("Tokyo",3.9,"Nov 10,2014"));
        earthquakes.add(new EarthQuake("Mexico City",5.4,"May 3,2014"));
        earthquakes.add(new EarthQuake("Moscow",2.8,"Jan 31,2013"));
        earthquakes.add(new EarthQuake("Rio de Janeiro",4.9,"April 19,2012"));
        earthquakes.add(new EarthQuake("Paris",1.6,"October30,2011"));*/
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakes = new ArrayList<>();
        // Create a new {@link ArrayAdapter} of earthquakes
         earthquakesAdapter = new earthquakesAdapter(getApplicationContext(),  earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthquakesAdapter);
        //new EarthquakeAsyncTask().execute(USGS_REQUSRT_URL);

        getSupportLoaderManager().initLoader(0,null,this);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthQuake selectedEarthQuake = (EarthQuake) adapterView.getItemAtPosition(i);
                openWebPage(selectedEarthQuake.getEarthquakeWebsite());
            }
        });
    }
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void UpdateUi(List<EarthQuake> earthQuakeList){
        earthquakes = earthQuakeList;
        earthquakesAdapter = new earthquakesAdapter(getApplicationContext(),  earthquakes);
        earthquakeListView.setAdapter(earthquakesAdapter);
        }
    public void resetUI(List<EarthQuake> earthQuakeList){
        earthquakes = new ArrayList<>();
        earthquakesAdapter = new earthquakesAdapter(getApplicationContext(),  earthquakes);
        earthquakeListView.setAdapter(earthquakesAdapter);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakeLoader(MainActivity.this,USGS_REQUSRT_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthQuake>> loader, List<EarthQuake> earthquakes) {
        UpdateUi(earthquakes);
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        resetUI(earthquakes);
    }

    /*private class EarthquakeAsyncTask extends AsyncTask <String,Void,List<EarthQuake>>{
        @Override
        protected List<EarthQuake> doInBackground(String... urls) {
            String JSON = "";
            URL url  = QueryUtils.createUrl(urls[0]);
            try {
                JSON = QueryUtils.makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<EarthQuake> earthQuakes = new ArrayList<>();
            earthQuakes = QueryUtils.extractEarthquakes(JSON);
            return earthQuakes;
            }

        @Override
        protected void onPostExecute(List<EarthQuake> earthQuakes) {
            UpdateUi(earthQuakes);
        }
    }*/
}
