package com.skapps.android.fightcovid.ViewModels;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.skapps.android.fightcovid.Location;
import com.skapps.android.fightcovid.QueryUtils;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Syed Umair on 02/04/2020.
 */
public class SelectedStateVM extends ViewModel {
    private MutableLiveData<List<Location>> loc = new MutableLiveData<>();

    public SelectedStateVM(String selectedState) {

        loadData(selectedState);

    }

    public LiveData<List<Location>> getData() {
        return loc;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData(final String sState) {
        new AsyncTask<Void,Void,List<Location>>() {

            @Override
            protected List<Location> doInBackground(Void... voids) {
                return QueryUtils.fetchCovidStateData("https://api.covid19india.org/state_district_wise.json", sState);  //TODO provice cstate from preferences
            }

            @Override
            protected void onPostExecute(List<Location> locations) {
                loc.setValue(locations);
            }
        }.execute();

    }
}
