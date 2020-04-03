package com.skapps.android.fightcovid;

import android.app.Activity;
import android.app.Application;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.skapps.android.fightcovid.ViewModels.JsonViewModel;
import com.skapps.android.fightcovid.ViewModels.StateStatusVM;

import java.sql.Wrapper;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.Context.MODE_PRIVATE;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.skapps.android.fightcovid.LoginActivity.SHARED_PREFS1;
import static com.skapps.android.fightcovid.QueryUtils.getTimeString;

/**
 * Created by Irshad Kasana on 4/2/2020.
 */
public class MyWork extends Worker {

    JsonViewModel jsonViewModel;


   /// private static final String TAG = SyncDataWorker.class.getSimpleName();

    public MyWork(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);

        Application mApplication;
    }
    @NonNull
    @Override
    public Result doWork() {

     //   Context applicationContext = getApplicationContext();
        //simulate slow work
        // TODO
        //  WorkerUtils.makeStatusNotification("Fetching Data", applicationContext);
        ///   Log.i(TAG, "Fetching Data from Remote host");
        // TODO
        //  WorkerUtils.sleep();

        try {
            //create a call to network

            // calling the ViewModel

            // TODO
            //  jsonViewModel = ViewModelProviders.of(this, new  JsonViewModel(getActivity().getApplication(), "my awesome param")).get( JsonViewModel.class);

            new AsyncTask<String, String, String>() {
                /**
                 * Before starting background do some work.
                 * */
                @Override
                protected void onPreExecute() {
                }

                @Override
                protected String doInBackground(String... params) {
                    // TODO fetch url data do bg process.
                    return null;
                }

                /**
                 * Update list ui after process finished.
                 */
                //execute it in main threead
                //TODO

                protected void onPostExecute(String result) {
                    // NO NEED to use activity.runOnUiThread(), code execute here under UI thread.

                    // Updating parsed JSON data into ListView
                  // TODO PR1
                    // remove the commit from next two lines and see the error
                  //  StateStatusVM stateBarModel = new ViewModelProvider(this).get(StateStatusVM.class);
                  //  stateBarModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
                   //     @Override
                     //   public void onChanged(List<Integer> integers) {
                            //  lastUpdatedTv.setText(getTimeString(Integer.toString(integers.get(6)), Integer.toString(integers.get(7))));
                        }
                  //  });
                    // not include
                   // final List data = new Gson().fromJson(result);
                    // updating listview
                   // ((ListActivity) activity).updateUI(data);
                } ;
        }


                    //TODO
//            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty() && response.body().size() > 0) {
////
////                String data = WorkerUtils.toJson(response.body());
////                Log.i(TAG, "Json String from network " + data);
////
////
            // TODO
            // WorkerUtils.makeStatusNotification(applicationContext.getString(R.string.new_data_available), applicationContext);
//             if{
//                return Result.success();}
//           if {
//                return Result.retry();
//            }


        catch (Throwable e) {
            e.printStackTrace();
            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
            Log.e(TAG, "Error fetching data", e);
            return Result.failure();
        }  return null;
    }


    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "OnStopped called for this worker");
    }


//    private static Activity getActivity(Wrapper wrapper) {
//        Activity activity;
//        if (wrapper.getContext() instanceof android.app.Fragment) {
//            activity = ((android.app.Fragment) wrapper.getContext()).getActivity();
//        } else if (wrapper.getContext() instanceof android.support.v4.app.Fragment) {
//            activity = ((android.support.v4.app.Fragment) wrapper.getContext()).getActivity();
//        } else {
//            activity = (Activity) wrapper.getContext();
//        }
//
//        return activity;
//    }

}
