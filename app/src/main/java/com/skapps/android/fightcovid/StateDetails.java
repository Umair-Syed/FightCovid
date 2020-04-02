package com.skapps.android.fightcovid;

import android.os.Bundle;
import android.widget.ListView;

import com.skapps.android.fightcovid.ViewModels.SelectedStateVM;
import com.skapps.android.fightcovid.ViewModels.SelectedStateVMFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class StateDetails extends AppCompatActivity {
    private String stateName;
    private ListViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_details);

        if(getIntent().hasExtra("STATE_NAME")){
            stateName = getIntent().getStringExtra("STATE_NAME");
            setTitle(stateName);
        }

        final ListView listView = findViewById(R.id.list_state_selected);
        listView.setEmptyView(findViewById(R.id.empty_view));

        SelectedStateVMFactory factory = new SelectedStateVMFactory(stateName);
        SelectedStateVM stateVM = new ViewModelProvider(this, factory).get(SelectedStateVM.class);
        stateVM.getData().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(List<Location> locations) {
                Collections.sort(locations, new Comparator<Location>() {
                    @Override
                    public int compare(Location o1, Location o2) {
                        return Integer.compare(o1.getmCount(), o2.getmCount());
                    }
                });
                Collections.reverse(locations);

                mAdapter = new ListViewAdapter(StateDetails.this, locations);
                listView.setAdapter(mAdapter);
            }
        });

    }
}
