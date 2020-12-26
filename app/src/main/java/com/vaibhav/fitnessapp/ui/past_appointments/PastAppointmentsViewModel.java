package com.vaibhav.fitnessapp.ui.past_appointments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PastAppointmentsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PastAppointmentsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}