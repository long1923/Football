package com.llong.football;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.llong.football.di.ApplicationComponent;
import com.llong.football.di.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by cui-hl on 2018/08/30.
 */

public class FootballApplication extends DaggerApplication implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentSupportInjector;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentSupportInjector;
    }
}
