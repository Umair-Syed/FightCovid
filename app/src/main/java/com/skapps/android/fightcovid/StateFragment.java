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


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * Created by Syed Umair on 31/03/2020.
 */
public class StateFragment extends Fragment {

    private ListViewAdapter mAdapter;

    public StateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayList<Location> list = new ArrayList<>();
        list.add(new Location(17, "Srinagar"));
        list.add(new Location(9, "Jammu"));
        list.add(new Location(8, "Rajouri"));
        list.add(new Location(17, "Srinagar"));


        ListView listView = view.findViewById(R.id.list_state);
        mAdapter = new ListViewAdapter(getContext(), list);

        listView.setAdapter(mAdapter);
    }
}
