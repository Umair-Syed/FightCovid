


package com.skapps.android.fightcovid;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.skapps.android.fightcovid.MainActivity;
import com.skapps.android.fightcovid.R;

import androidx.appcompat.app.AppCompatActivity;public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //variable to store state value
    private String state="";
    //variable to store  value
    private String district="";
    // two spinner objects for state and district
    private Spinner spinnerState;
    private Spinner spinnerDistrict;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       //binding spinners to view
        spinnerState = (Spinner)findViewById(R.id.spinner1);
        spinnerDistrict = (Spinner)findViewById(R.id.spinner2);

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

       // initialization and editing of shared preferences and then saving it
        SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.saved_state), state);
        editor.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        // variable to store the value corresponded to whether selection of sate and district is none or not
        int checkState =spinnerState.getSelectedItemPosition();
        int checkDistrict =spinnerState.getSelectedItemPosition();
     if(checkDistrict !=0){
        switch (position) {
            case 0:
                state = parent.getItemAtPosition(position).toString();
                break;
            case 1:

                break;
            case 2:

                break;

        }}
     if(checkDistrict!=0){ switch (position) {
         case 0:
             district = parent.getItemAtPosition(position).toString();
             break;
         case 1:

             break;
         case 2:

             break;

     }}
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
