package com.llong.football.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.llong.football.fragment.LoadingFragment;
import com.llong.football.fragment.MessageDialogFragment;

import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;


/**
 * Created by cui-hl on 2018/08/31.
 */

public abstract class BaseActivity extends DaggerAppCompatActivity implements MessageDialogFragment.Callback{

    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        loadingFragment=new LoadingFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loadingFragment!=null){
            loadingFragment.dismiss();
            loadingFragment=null;
        }
    }


    public void showLoading(){
        loadingFragment.show(getSupportFragmentManager());
    }

    public void closeLoading(){
        loadingFragment.dismiss();
    }

    @Override
    public void onSuccess(int requestCode, int resultCode) {

    }

    @Override
    public void onCancel(int requestCode) {

    }


}
