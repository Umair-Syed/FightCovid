package com.skapps.android.fightcovid;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

//         List<Location> list = new ArrayList<>();
//        list.add(new Location(17, "Srinagar"));
//        list.add(new Location(9, "Jammu"));
//        list.add(new Location(8, "Rajourui"));
//        list.add(new Location(17, "katwa"));

//        new AsyncTask<String, Void, List<Location>>(){
//
//            @Override
//            protected List<Location> doInBackground(String... strings) {
//
//               return QueryUtils.fetchCovidData("https://api.covid19india.org/state_district_wise.json");
//            }
//
//            @Override
//            protected void onPostExecute(List<Location> locations) {
//                Log.d("StateFrag", locations.get(0).getmLocation() + " " + locations.get(0).getmCount() );
//            }
//        }.execute();

//        Collections.sort(list, new Comparator<Location>() {
//            @Override
//            public int compare(Location o1, Location o2) {
//                return Integer.compare(o1.getmCount(), o2.getmCount());
//            }
//        });

//        final ListView listView = getView().findViewById(R.id.list_state);
//        Log.d("statefragement", "before view model instance");
//
//
//
//        Log.d("statefragement", "afgter view model instance");
//        model.getData().observe(this, new Observer<List<Location>>() {
//            @Override
//            public void onChanged(List<Location> locations) {
//                mAdapter = new ListViewAdapter(getContext(), locations);
//                listView.setAdapter(mAdapter);
//            }
//        });

        final ListView listView = getView().findViewById(R.id.list_state);
        Log.d("statefragement", "before view model instance");

        JsonViewModel model = new ViewModelProvider(this).get(JsonViewModel.class);
        Log.d("statefragement", "afgter view model instance");
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
