package com.skapps.android.fightcovid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class LaunchActivity extends AppCompatActivity {

    private Spinner mSpinnerState;
    private Button button;

    public static final String PREF_SELECTED_STATE_KEY = "selected_state_key";
    public static final String USER_CHOICE_PREF = "MAIN_PREF";
    private String mSelectedState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //binding spinners to view
        mSpinnerState = findViewById(R.id.spinner1);
        button = findViewById(R.id.Next_button);

        //initialize the array adapters
        ArrayAdapter<CharSequence> statesAdapter = ArrayAdapter.createFromResource(this,
                R.array.states, android.R.layout.simple_spinner_item);

        statesAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        mSpinnerState.setAdapter(statesAdapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedState = mSpinnerState.getSelectedItem().toString();
                SharedPreferences userPref = getSharedPreferences(USER_CHOICE_PREF, MODE_PRIVATE);
                userPref.edit().putString(PREF_SELECTED_STATE_KEY, mSelectedState).apply();

                Intent myIntent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
    }


}
