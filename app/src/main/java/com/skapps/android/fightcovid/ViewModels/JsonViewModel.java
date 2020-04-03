package com.skapps.android.fightcovid.ViewModels;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.skapps.android.fightcovid.LaunchActivity;
import com.skapps.android.fightcovid.Location;
import com.skapps.android.fightcovid.QueryUtils;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Syed Umair on 31/03/2020.
 */
public class JsonViewModel extends AndroidViewModel {
//    private final JsonLiveData data;
    private MutableLiveData<List<Location>> loc = new MutableLiveData<>();


    public JsonViewModel(Application application) {

        super(application);
        loadData();

    }

    public LiveData<List<Location>> getData() {
        return loc;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        new AsyncTask<Void,Void,List<Location>>() {

            @Override
            protected List<Location> doInBackground(Void... voids) {
                SharedPreferences pref = getApplication().getSharedPreferences(LaunchActivity.USER_CHOICE_PREF, MODE_PRIVATE);
                return QueryUtils.fetchCovidStateData("https://api.covid19india.org/state_district_wise.json", pref.getString(LaunchActivity.PREF_SELECTED_STATE_KEY, "State"));
            }

            @Override
            protected void onPostExecute(List<Location> locations) {
                loc.setValue(locations);
            }
        }.execute();

    }

}

