package com.skapps.android.fightcovid;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.skapps.android.fightcovid.ViewModels.JsonViewModel;
import com.skapps.android.fightcovid.ViewModels.StateStatusVM;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final TextView confirmedTV = getView().findViewById(R.id.confirmed_count_state);
        final TextView confirmedDeltaTV = getView().findViewById(R.id.confirmed_count_state_delta);
        final TextView recoveredTV = getView().findViewById(R.id.recovered_count_state);
        final TextView recoveredDeltaTV = getView().findViewById(R.id.recovered_count_state_delta);
        final TextView deceasedTV = getView().findViewById(R.id.deceased_count_state);
        final TextView deceasedDeltaTV = getView().findViewById(R.id.deceased_count_state_delta);

        StateStatusVM stateBarModel = new ViewModelProvider(this).get(StateStatusVM.class);
        stateBarModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                confirmedTV.setText(Integer.toString(integers.get(0)));
                if(integers.get(1) == 0){
                    confirmedDeltaTV.setVisibility(View.GONE);
                }else {
                    confirmedDeltaTV.setVisibility(View.VISIBLE);
                    confirmedDeltaTV.setText("+"+integers.get(1));
                }

                recoveredTV.setText(Integer.toString(integers.get(2)));
                if(integers.get(3) == 0){
                    recoveredDeltaTV.setVisibility(View.GONE);
                }else{
                    recoveredDeltaTV.setVisibility(View.VISIBLE);
                    recoveredDeltaTV.setText("+"+integers.get(3));
                }

                deceasedTV.setText(Integer.toString(integers.get(4)));
                if(integers.get(5) == 0){
                    deceasedDeltaTV.setVisibility(View.GONE);
                }else{
                    recoveredDeltaTV.setVisibility(View.VISIBLE);
                    deceasedDeltaTV.setText("+"+integers.get(5));
                }

            }
        });


        final ListView listView = getView().findViewById(R.id.list_state);

        JsonViewModel model = new ViewModelProvider(this).get(JsonViewModel.class);
        model.getData().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {

                Collections.sort(locations, new Comparator<Location>() {
                    @Override
                    public int compare(Location o1, Location o2) {
                        return Integer.compare(o1.getmCount(), o2.getmCount());
                    }
                });
                Collections.reverse(locations);
                
                mAdapter = new ListViewAdapter(getContext(), locations);
                listView.setAdapter(mAdapter);
            }
        });
    }
}
