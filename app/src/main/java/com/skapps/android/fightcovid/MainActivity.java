package com.skapps.android.fightcovid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity{

    public static String mSelectedState;
    public static String FIRST_RUN_PREF_KEY = "isFirstRun";
    public static String FROM_MENU_PREF_KEY = "fromMenu";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean isFirstRun = getSharedPreferences("FIRST_RUN_PREF", MODE_PRIVATE)
                .getBoolean(FIRST_RUN_PREF_KEY, true);

        if (isFirstRun) {
            //show start activity
            startActivity(new Intent(this, LaunchActivity.class));
        }

        getSharedPreferences("FIRST_RUN_PREF", MODE_PRIVATE).edit()
                .putBoolean(FIRST_RUN_PREF_KEY, false).apply();

        //if Internet is connected then setup viewPager2 to load fragments otherwise ask for Internet
        if(isConnected(this)){
            setupviewPager2();
        }else {
            showDialog();
        }

        //For background service for notifications
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(UpdateWorker.class,
                20, TimeUnit.MINUTES).setConstraints(constraints).addTag("Periodic")
                .setInitialDelay(5, TimeUnit.MINUTES).build();

        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Check if user triggered a refresh:
            case R.id.menu_refresh:
                setupviewPager2();
                return true;
            case R.id.change_state:
                getSharedPreferences("FIRST_RUN_PREF", MODE_PRIVATE).edit()
                        .putBoolean(FROM_MENU_PREF_KEY, true).apply();
                startActivity(new Intent(this, LaunchActivity.class));
                return true;
            case R.id.about_us:
                startActivity(new Intent(this, AboutUs.class));
                return true;
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.skapps.android.fightcovid";
                String shareSub = "Use this app to get latest updates on Covid19 in India";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Share using"));
                return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("No internet connection. Make sure that Wi-Fi or mobile data is turned on.")
                .setCancelable(false)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(!isConnected(MainActivity.this)){
                            showDialog();
                        }else{
                            setupviewPager2();
                        }
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setupviewPager2(){
        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new PagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if (position == 1) {
                    tab.setText("India");
                } else {
                    SharedPreferences pref = getSharedPreferences(LaunchActivity.USER_CHOICE_PREF, MODE_PRIVATE);
                    mSelectedState = pref.getString(LaunchActivity.PREF_SELECTED_STATE_KEY, "State");
                    tab.setText(mSelectedState);
                }
            }
        });
        tabLayoutMediator.attach();

    }


}
