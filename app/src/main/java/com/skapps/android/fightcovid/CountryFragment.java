package com.skapps.android.fightcovid;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
/**
 * Created by Syed Umair on 31/03/2020.
 */
public class CountryFragment extends Fragment {

    private ListViewAdapter mAdapter;

    public CountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Location> list = new ArrayList<>();
        list.add(new Location(17, "Kerela"));
        list.add(new Location(9, "Maharastra"));
        list.add(new Location(8, "Rajasthan"));
        list.add(new Location(17, "Srinagar"));

        Collections.sort(list, new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                return Integer.compare(o1.getmCount(), o2.getmCount());
            }
        });


        ListView listView = getView().findViewById(R.id.list_country);
        mAdapter = new ListViewAdapter(getContext(), list);

        listView.setAdapter(mAdapter);
    }
}
