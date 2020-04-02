package com.skapps.android.fightcovid;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * Created by Syed Umair on 31/03/2020.
 */
public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 1){
            return new CountryFragment();
        }else{
            return new StateFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
