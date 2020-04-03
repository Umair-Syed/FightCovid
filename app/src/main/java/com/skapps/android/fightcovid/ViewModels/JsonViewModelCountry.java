package com.skapps.android.fightcovid.ViewModels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.skapps.android.fightcovid.Location;
import com.skapps.android.fightcovid.QueryUtils;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by Syed Umair on 02/04/2020.
 */
public class JsonViewModelCountry extends AndroidViewModel {

    private MutableLiveData<List<Location>> locationsStates = new MutableLiveData<>();

    public JsonViewModelCountry(Application application) {

        super(application);
        loadData();

    }

    public LiveData<List<Location>> getData() {
        return locationsStates;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        new AsyncTask<Void,Void,List<Location>>() {

            @Override
            protected List<Location> doInBackground(Void... voids) {
                return QueryUtils.fetchCovidCountryData("https://api.covid19india.org/data.json");
            }

            @Override
            protected void onPostExecute(List<Location> locations) {
                Log.d("jsonviewmodel", locations.get(0).getmLocation() + " " + locations.get(0).getmCount() );
                locationsStates.setValue(locations);
            }
        }.execute();

    }
}
