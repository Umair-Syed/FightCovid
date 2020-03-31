


package com.skapps.android.fightcovid;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

//import com.skapps.android.fightcovid.MainActivity;
//import com.skapps.android.fightcovid.R;

import androidx.appcompat.app.AppCompatActivity;
public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //variable to store state value
    private String state="";
    //variable to store district value
    private String district="";
    // two spinner objects for state and district
    private Spinner spinnerState;
    private Spinner spinnerDistrict;
    private Button button;
    //constants
    public static final String SHARED_PREFS="SHARED_PREF";
    public static final String DISTRICT="districtsaved";
    public static final String STATE="statesaved";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //binding spinners to view
        spinnerState = (Spinner)findViewById(R.id.spinner1);
        spinnerDistrict = (Spinner)findViewById(R.id.spinner2);
        button=(Button)findViewById(R.id.Next_button);
        //initialize the array adapters
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.ds, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.st, android.R.layout.simple_spinner_item);

        //setting the drop down view for adapters
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting adapter
        spinnerState.setAdapter(adapter1);
        spinnerDistrict.setAdapter(adapter2);

        //listener to item selected
        spinnerState.setOnItemSelectedListener(this);
        spinnerDistrict.setOnItemSelectedListener(this);


        //Next button clicked
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
                Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        // variable to store the value corresponded to whether selection of sate and district is none or not
        int checkState =spinnerState.getSelectedItemPosition();
        int checkDistrict =spinnerState.getSelectedItemPosition();
        if(checkDistrict !=0){
            state = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(),state+"selected",Toast.LENGTH_SHORT).show();
        }
        if(checkDistrict!=0){
            district = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(),district+"selected",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
    // method for future defaults shared preferences
    public  Context getContext(){
        Context mContext = LoginActivity.this;
        return mContext;
    }
    // initialization and editing of shared preferences and then saving it
    public void savedata(){
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(DISTRICT, district);
        editor.putString(STATE, state);
        editor.commit();
    }
}