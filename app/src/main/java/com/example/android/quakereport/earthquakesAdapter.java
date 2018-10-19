package com.example.android.quakereport;

import android.content.Context;
import java.text.DecimalFormat;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class earthquakesAdapter extends ArrayAdapter<EarthQuake> {

    public earthquakesAdapter(@NonNull Context context, List<EarthQuake> earthQuakes) {
        super(context, 0, earthQuakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        EarthQuake earthQuake = getItem(position);

        TextView magnitudeTV = (TextView) listView.findViewById(R.id.mag);
        DecimalFormat formatter = new DecimalFormat("0.0");
        String mag = formatter.format(earthQuake.getMag());
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTV.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthQuake.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        magnitudeTV.setText(mag);

        String place = earthQuake.getPlace();

        TextView offsetTV = (TextView) listView.findViewById(R.id.offset);
        //offsetTV.setText(earthQuake.getPlace());

        TextView primaryLocationTV = (TextView) listView.findViewById(R.id.primarylocation);
        //primaryLocationTV.setText(earthQuake.getPlace());

        splitLocation(place,offsetTV,primaryLocationTV);

        TextView dateTV = (TextView) listView.findViewById(R.id.date);
        dateTV.setText(earthQuake.getDate());

        TextView timeTV = (TextView) listView.findViewById(R.id.time);
        timeTV.setText(earthQuake.getTime());

        return listView;
    }

    private void splitLocation(String location, TextView offsetTV, TextView primaryLocationTV) {
        String offset;
        String primaryLocation;
        if(location.contains("of")){
            {offset= location.substring(0,location.indexOf("of")+2);
                primaryLocation = location.substring(location.indexOf("of")+2,
                        location.length()-1); }
        }
        else{
            offset= "Near the";
            primaryLocation = location;
        }
        offsetTV.setText(offset);
        primaryLocationTV.setText(primaryLocation);
    }
    private int getMagnitudeColor(double magnitude){
        int mag = (int) magnitude;
        int color = 0;
        switch (mag){
            case 10:
                color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;
            case 9:
                color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                break;
            case 8:
                color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                break;
            case 7:
                color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                break;
            case 6:
                color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                break;
            case 5:
                color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                break;
            case 4:
                color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                break;
            case 3:
                color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                break;
            case 2:
                color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                break;
            case 1:
            case 0:
                color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                break;
            default:
                color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                break;

        }
        return color;

    }
}
