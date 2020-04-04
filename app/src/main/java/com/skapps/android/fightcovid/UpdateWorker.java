package com.skapps.android.fightcovid;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by Syed Umair on 03/04/2020.
 */
public class UpdateWorker extends Worker {

    private static final int UPDATE_NOTIFICATION_ID_STATE = 3432;
    private static final int UPDATE_NOTIFICATION_ID_COUNTRY = 3522;

    private int currentIntValue;

    public UpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {


        try {

            //for State
            List<Integer> newList = QueryUtils.fetchStateBarData("https://api.covid19india.org/data.json");
            currentIntValue = newList.get(0);
            int previousValue = getApplicationContext().getSharedPreferences(QueryUtils.CONFIRMED_UPDATE_WORK_KEY, Context.MODE_PRIVATE)
                    .getInt(QueryUtils.CONFIRMED_INT_STATE_KEY, currentIntValue);

            if(previousValue < currentIntValue){
                String state = getApplicationContext().getSharedPreferences(LaunchActivity.USER_CHOICE_PREF, Context.MODE_PRIVATE).getString(LaunchActivity.PREF_SELECTED_STATE_KEY, "Maharashtra");
                int more = currentIntValue - previousValue;
                NotificationUtils.notifyUserOfUpdate(getApplicationContext(), more + " more cases",
                        more + " more cases in " + state, UPDATE_NOTIFICATION_ID_STATE);

                //updating previous value
                getApplicationContext().getSharedPreferences(QueryUtils.CONFIRMED_UPDATE_WORK_KEY, Context.MODE_PRIVATE)
                        .edit().putInt(QueryUtils.CONFIRMED_INT_STATE_KEY, currentIntValue).apply();
            }



            //for country
            List<Integer> newListCountry = QueryUtils.fetchCountryBarData("https://api.covid19india.org/data.json");
            currentIntValue = newListCountry.get(0);
            int previousValueCountry = getApplicationContext().getSharedPreferences(QueryUtils.CONFIRMED_UPDATE_WORK_KEY, Context.MODE_PRIVATE)
                    .getInt(QueryUtils.CONFIRMED_INT_COUNTRY_KEY, currentIntValue);

            if(previousValueCountry < currentIntValue){
                int more = currentIntValue - previousValueCountry;
                NotificationUtils.notifyUserOfUpdate(getApplicationContext(), more + " more cases",
                        more + " more cases in India", UPDATE_NOTIFICATION_ID_COUNTRY);

                //updating previous value
                getApplicationContext().getSharedPreferences(QueryUtils.CONFIRMED_UPDATE_WORK_KEY, Context.MODE_PRIVATE)
                        .edit().putInt(QueryUtils.CONFIRMED_INT_COUNTRY_KEY, currentIntValue).apply();
            }

        } catch (Exception e) { // if no internet connection
            return Result.failure();
        }


        return Result.success();

    }

}
