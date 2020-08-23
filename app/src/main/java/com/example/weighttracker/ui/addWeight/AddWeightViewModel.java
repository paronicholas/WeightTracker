package com.example.weighttracker.ui.addWeight;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddWeightViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddWeightViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Add weight: ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}