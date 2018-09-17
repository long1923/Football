package com.llong.football.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.llong.football.di.DataException;

/**
 * Created by cui-hl on 2018/09/17.
 */

public class BaseViewModel extends ViewModel {

    protected MutableLiveData<DataException> errorLiveData = new MutableLiveData<>();

    public MutableLiveData<DataException> getErrorLiveData() {
        return errorLiveData;
    }
}
