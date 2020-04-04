package com.skapps.android.fightcovid;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Created by Syed Umair on 31/03/2020.
 */
public class ListViewAdapter extends ArrayAdapter<Location> {

    public ListViewAdapter(Context context, List<Location> locations) {
        super(context, 0 , locations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Location currentLocation  = getItem(position);
        TextView countView = listItemView.findViewById(R.id.count);
        countView.setText(Integer.toString(currentLocation.getmCount()));


        GradientDrawable magnitudeCircle = (GradientDrawable) countView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentLocation.getmCount());
        magnitudeCircle.setColor(magnitudeColor);

        TextView locationView = listItemView.findViewById(R.id.primary_location);
        locationView.setText(currentLocation.getmLocation());

        return listItemView;
    }

    private int getMagnitudeColor(int count) {
        int magnitudeColorResourceId;

        if(count < 5){
            magnitudeColorResourceId = R.color.magnitude1;
        }else if(count < 10){
            magnitudeColorResourceId = R.color.magnitude2;
        }else if(count < 30){
            magnitudeColorResourceId = R.color.magnitude3;
        }else if(count < 60){
            magnitudeColorResourceId = R.color.magnitude4;
        }else if(count < 120){
            magnitudeColorResourceId = R.color.magnitude5;
        }else if(count < 240){
            magnitudeColorResourceId = R.color.magnitude6;
        }else if(count < 480){
            magnitudeColorResourceId = R.color.magnitude7;
        }else if(count < 900){
            magnitudeColorResourceId = R.color.magnitude8;
        }else if(count < 2000){
            magnitudeColorResourceId = R.color.magnitude9;
        }else{
            magnitudeColorResourceId = R.color.magnitude10plus;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
