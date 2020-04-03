package com.skapps.android.fightcovid;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.skapps.android.fightcovid.ViewModels.JsonViewModel;
import com.skapps.android.fightcovid.ViewModels.StateStatusVM;

import java.nio.channels.Channel;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import static android.content.Context.MODE_PRIVATE;
import static com.skapps.android.fightcovid.LoginActivity.DISTRICT;
import static com.skapps.android.fightcovid.LoginActivity.SHARED_PREFS1;
import static com.skapps.android.fightcovid.QueryUtils.getTimeString;


/**
 * A simple {@link Fragment} subclass.
 */

/**
 * Created by Syed Umair on 31/03/2020.
 */
public class StateFragment extends Fragment {

    private ListViewAdapter mAdapter;
    //public Context mcontext;

    // constants for shared preference
    public final String SHARED_PREFS_CONFIRMED="SHARED_PREFS_CONFIRMED";
    public int confirmedCases;
    public final String CONFIRMED_CASES="CONFIRMED_CASES";
    //public String context = Context.NOTIFICATION_SERVICE;



    public StateFragment() {
        // Required empty public constructor
         }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Filling status bar of state fragment with data
        final TextView confirmedTV = getView().findViewById(R.id.confirmed_count_State);
        final TextView confirmedDeltaTV = getView().findViewById(R.id.confirmed_count_state_delta);
        final TextView recoveredTV = getView().findViewById(R.id.recovered_count_state);
        final TextView recoveredDeltaTV = getView().findViewById(R.id.recovered_count_state_delta);
        final TextView deceasedTV = getView().findViewById(R.id.deceased_count_state);
        final TextView deceasedDeltaTV = getView().findViewById(R.id.deceased_count_state_delta);
        final TextView lastUpdatedTv = getView().findViewById(R.id.updated_on_tv_state);

        StateStatusVM stateBarModel = new ViewModelProvider(this).get(StateStatusVM.class);
        stateBarModel.getData().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {

                // retrive old saved sharedprefernce
                int p;
                Context ctx = getActivity().getApplicationContext();
                SharedPreferences sharedPref1 = ctx.getSharedPreferences(SHARED_PREFS1, MODE_PRIVATE);
                p = sharedPref1.getInt(CONFIRMED_CASES, 0);

                int localVariableCC;
                confirmedTV.setText(Integer.toString(localVariableCC = integers.get(0)));
                if (localVariableCC == p) { } else {
                    // Notification zone
                    //Context ctx   = getActivity().getApplicationContext();
                    SharedPreferences sharedPrefConfirmedCases = ctx.getSharedPreferences(SHARED_PREFS_CONFIRMED, MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPrefConfirmedCases.edit();
                    editor2.putInt("CONFIRMED_CASES", localVariableCC);
                    editor2.commit();
                }
                    //call to Notification

                    // public void sendNotification(View view)
                   // {


                        //Create the intent that’ll fire when the user taps the notification//

                        // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.androidauthority.com/"));
                        // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                        //  mBuilder.setContentIntent(pendingIntent);


                        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

                            // Configure the notification channel.
                            notificationChannel.setDescription("Channel description");
                            notificationChannel.enableLights(true);
                            notificationChannel.setLightColor(Color.RED);
                            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                            notificationChannel.enableVibration(true);
                            notificationManager.createNotificationChannel(notificationChannel);
                        }

                        // Resources res = ctx.getResources();

                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_ID);

                        notificationBuilder.setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.drawable.color_red)
                                .setTicker("pending")
                                //     .setPriority(Notification.PRIORITY_MAX)
                                .setContentTitle("Default notification")
                                .setContentText(localVariableCC + " new cases in your state ")
                                .setContentInfo("Info");


//
//                        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
//                        Intent notificationIntent = new Intent(this, MainActivity.class);
//                        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
//                                PendingIntent.FLAG_UPDATE_CURRENT);
//                        builder.setContentIntent(contentIntent);
//
//                        mBuilder.setSmallIcon(R.drawable.notification_icon);
//                        mBuilder.setContentTitle("My notification");
//                        mBuilder.setContentText(localVariableCC+"new cases in your state ");
//
//                        NotificationManager mNotificationManager =
//
//                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                        mNotificationManager.notify(001, mBuilder.build());
//                    }

                        // }

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
//                Log.d("stateFrg", Integer.toString(integers.get(6)) + " " +  Integer.toString(7));
                        lastUpdatedTv.setText(getTimeString(Integer.toString(integers.get(6)), Integer.toString(integers.get(7))));

                    }
                });

                //Populating list view in state Fragment with data
                final ListView listView = getView().findViewById(R.id.list_state);

                JsonViewModel model = new ViewModelProvider(this).get(JsonViewModel.class);
                model.getData().observe(getViewLifecycleOwner(), new Observer<List<Location>>() {
                    @Override
                    public void onChanged(List<Location> locations) {

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
                });
            }

            // definition of SharedPreference
           // public void checkConfirmedCases() { }
        }