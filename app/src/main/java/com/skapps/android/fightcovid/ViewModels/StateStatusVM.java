package com.skapps.android.fightcovid.ViewModels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static com.skapps.android.fightcovid.QueryUtils.fetchStateBarData;

/**
 * Created by Syed Umair on 02/04/2020.
 */
public class StateStatusVM extends AndroidViewModel {

    private MutableLiveData<List<Integer>> barData = new MutableLiveData<>();

    public StateStatusVM(Application application) {

        super(application);
        loadData();

    }

    public LiveData<List<Integer>> getData() {
        return barData;
    }

    @SuppressLint("StaticFieldLeak")
    private void loadData() {
        new AsyncTask<Void,Void,List<Integer>>() {

            @Override
            protected List<Integer> doInBackground(Void... voids) {
                return fetchStateBarData("https://api.covid19india.org/data.json");
            }

            @Override
            protected void onPostExecute(List<Integer> locations) {

                barData.setValue(locations);
            }
        }.execute();

    }
}
