package com.skapps.android.fightcovid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static androidx.constraintlayout.widget.Constraints.TAG;

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

        Context applicationContext = getApplicationContext();
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
            //  jsonViewModel = ViewModelProviders.of(this, new  JsonViewModel(this.getApplication(), "my awesome param")).get( JsonViewModel.class);



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


        } catch (Throwable e) {
            e.printStackTrace();
            // Technically WorkManager will return Result.failure()
            // but it's best to be explicit about it.
            // Thus if there were errors, we're return FAILURE
            Log.e(TAG, "Error fetching data", e);
            return Result.failure();
        }
    }


    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "OnStopped called for this worker");
    }
}
