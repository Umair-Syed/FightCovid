package com.skapps.android.fightcovid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.skapps.android.fightcovid.ViewModels.JsonViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import static com.skapps.android.fightcovid.LoginActivity.DISTRICT;
import static com.skapps.android.fightcovid.LoginActivity.SHARED_PREFS2;
import static com.skapps.android.fightcovid.LoginActivity.SHARED_PREFS1;
import static com.skapps.android.fightcovid.LoginActivity.STATE;

public class MainActivity extends AppCompatActivity {
    private final String SYNC_DATA_WORK_NAME="SYNC_DATA_WORK_NAME";
    private final String TAG_SYNC_DATA="TAG_SYNC_DATA";
    private String txt;
    private String dis;
    private ListViewAdapter mAdapter;
    public WorkManager mWorkManager;

   private JsonViewModel jsonViewModel;
   private ProgressBar mProgressBar;

    // initialization of saved data by work manager after getting it from live data
    private LiveData<List<WorkInfo>> mSavedWorkInfo;
   //  mSavedWorkInfo = mWorkManager.getWorkInfosByTagLiveData(TAG_SYNC_DATA);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // calling the method to retrieve the shared preference
        loadData();
        fetchData();

        // get VM
        jsonViewModel = new ViewModelProvider(this).get(JsonViewModel.class);

        // Create the observer which updates the UI.
        final Observer<Location> nameObserver = new Observer<Location>() {
            @Override
            public void onChanged(@Nullable final Location newName) {
                // Update the UI, in this case, a TextView.
               // nameTextView.setText(newName);
            }


        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
       // jsonViewModel.getCurrentName().observe(this, nameObserver);
    };


        // Show work info, goes inside onCreate()
        //TODO
//        jsonViewModel.getOutputWorkInfo().observe(this, listOfWorkInfo -> {
//
//            // If there are no matching work info, do nothing
//            if (listOfWorkInfo == null || listOfWorkInfo.isEmpty()) {
//                return;
//            }
//
//            // We only care about the first output status.
//            // Every continuation has only one worker tagged TAG_SYNC_DATA
//            WorkInfo workInfo = listOfWorkInfo.get(0);
//            Log.i(TAG, "WorkState: " + workInfo.getState());
//            if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
//                showWorkFinished();
//
//                //observe Room db
//                jsonViewModel.getBooks().observe(this, books -> {
//                    mBookAdapter = new BookAdapter(MainActivity.this, books);
//                    recyclerView.setAdapter(mBookAdapter);
//
//                });
//
//
//            } else {
//                showWorkInProgress();
//            }
//        });

    }

    private void showWorkInProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showWorkFinished() {
        mProgressBar.setVisibility(View.GONE);
    }



    public void loadData(){
        //reading the sharedpreferenced data
        SharedPreferences sharedPref1 = getSharedPreferences(SHARED_PREFS1,MODE_PRIVATE);
        dis=sharedPref1.getString(DISTRICT,"nn");
        SharedPreferences sharedPref2 = getSharedPreferences(SHARED_PREFS2,MODE_PRIVATE);
        txt=sharedPref2.getString(STATE,"nn");
        Toast.makeText(getApplicationContext(),txt+dis,Toast.LENGTH_SHORT).show();

        //Refresh data regularly by WorkerManager
        //fetching new update data



        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new PagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if(position == 1){
                    tab.setText("India");
                }else{
                    tab.setText(txt);
                }
            }
        });
        tabLayoutMediator.attach();
    }

    // defining fetch update
    public void fetchData() {
        // Create Network constraint
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        //Periodic request
        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(MyWork.class, 15, TimeUnit.MINUTES)
                        .addTag(TAG_SYNC_DATA)
                        .setConstraints(constraints)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        //ensure of unique work
        mWorkManager.enqueueUniquePeriodicWork(
                SYNC_DATA_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );

    }

    public LiveData<List<WorkInfo>> getOutputWorkInfo() {
        return mSavedWorkInfo;
    }
}
