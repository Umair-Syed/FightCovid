package com.skapps.android.fightcovid.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by Syed Umair on 02/04/2020.
 */
public class SelectedStateVMFactory extends ViewModelProvider.NewInstanceFactory {

    private final String selectedState;

    public SelectedStateVMFactory(String selectedState) {
        this.selectedState = selectedState;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SelectedStateVM(selectedState);
    }
}
