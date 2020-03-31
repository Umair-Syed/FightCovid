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
        countView.setText(currentLocation.getmCount());

        GradientDrawable magnitudeCircle = (GradientDrawable) countView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentLocation.getmCount());
        magnitudeCircle.setColor(magnitudeColor);

        TextView locationView = listItemView.findViewById(R.id.primary_location);
        locationView.setText(currentLocation.getmLocation());

        return listItemView;
    }

    private int getMagnitudeColor(int count) {
        int magnitudeColorResourceId;
        switch (count) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
