package com.skapps.android.fightcovid;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.skapps.android.fightcovid.ViewModels.CountryStatusVM;
import com.skapps.android.fightcovid.ViewModels.JsonViewModelCountry;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import static com.skapps.android.fightcovid.QueryUtils.getTimeString;


/**
 * A simple {@link Fragment} subclass.
 */
/**
 * Created by Syed Umair on 31/03/2020.
 */
public class CountryFragment extends Fragment {

    private ListViewAdapter mAdapter;
    private ListView listView;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        listView = view.findViewById(R.id.list_country);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView state=  view.findViewById(R.id.primary_location);
                String locat = state.getText().toString();
                Intent intent = new Intent(getActivity(), StateDetails.class);
                intent.putExtra("STATE_NAME", locat);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(isConnected(getContext())) {
            //Filling status bar of state fragment with data
            final TextView confirmedTV = getView().findViewById(R.id.confirmed_count_country);
            final TextView confirmedDeltaTV = getView().findViewById(R.id.confirmed_count_country_delta);
            final TextView recoveredTV = getView().findViewById(R.id.recovered_count_country);
            final TextView recoveredDeltaTV = getView().findViewById(R.id.recovered_count_country_delta);
            final TextView deceasedTV = getView().findViewById(R.id.deceased_count_country);
            final TextView deceasedDeltaTV = getView().findViewById(R.id.deceased_count_country_delta);
            final TextView lastUpdatedTv = getView().findViewById(R.id.updated_on_tv_country);

            CountryStatusVM stateBarModel = new ViewModelProvider(this).get(CountryStatusVM.class);
            stateBarModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
                @Override
                public void onChanged(List<Integer> integers) {

                    if (!integers.isEmpty()) {
                        confirmedTV.setText(Integer.toString(integers.get(0)));
                        if (integers.get(1) == 0) {
                            confirmedDeltaTV.setVisibility(View.GONE);
                        } else {
                            confirmedDeltaTV.setVisibility(View.VISIBLE);
                            confirmedDeltaTV.setText("+" + integers.get(1));
                        }

                        recoveredTV.setText(Integer.toString(integers.get(2)));
                        if (integers.get(3) == 0) {
                            recoveredDeltaTV.setVisibility(View.GONE);
                        } else {
                            recoveredDeltaTV.setVisibility(View.VISIBLE);
                            recoveredDeltaTV.setText("+" + integers.get(3));
                        }

                        deceasedTV.setText(Integer.toString(integers.get(4)));
                        if (integers.get(5) == 0) {
                            deceasedDeltaTV.setVisibility(View.GONE);
                        } else {
                            recoveredDeltaTV.setVisibility(View.VISIBLE);
                            deceasedDeltaTV.setText("+" + integers.get(5));
                        }
                        lastUpdatedTv.setText(getTimeString(Integer.toString(integers.get(6)), Integer.toString(integers.get(7))));
                    }

                }
            });


            //Filling List with data


            JsonViewModelCountry model = new ViewModelProvider(this).get(JsonViewModelCountry.class);
            model.getData().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
                @Override
                public void onChanged(List<Location> locations) {
                    if (!locations.isEmpty()) {
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

                }
            });
        }
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {
            return false;
        }
    }
}
