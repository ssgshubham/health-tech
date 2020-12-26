package com.vaibhav.fitnessapp.ui.previous_orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PreviousOrdersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PreviousOrdersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}