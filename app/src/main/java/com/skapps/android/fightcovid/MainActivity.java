package com.skapps.android.fightcovid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import static com.skapps.android.fightcovid.LoginActivity.DISTRICT;
import static com.skapps.android.fightcovid.LoginActivity.SHARED_PREFS;
import static com.skapps.android.fightcovid.LoginActivity.STATE;

public class MainActivity extends AppCompatActivity {
    private String txt;
    private String dis;
    private ListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // calling the method to retrieve the shared preference
        loadData();

        ViewPager2 viewPager2 = findViewById(R.id.viewPager);
        viewPager2.setAdapter(new PagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                if(position == 1){
                    tab.setText("India");
                }else{
                    tab.setText("Your State");
                }
            }
        });
        tabLayoutMediator.attach();


    }
    public void loadData(){
        //reading the sharedpreferenced data
        SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        dis=sharedPref.getString(DISTRICT,"nn");
        txt=sharedPref.getString(STATE,"nn");
        Toast.makeText(getApplicationContext(),txt+dis,Toast.LENGTH_SHORT).show();
    }
}
