package com.llong.football;

import android.app.Activity;
import android.app.Application;

import com.llong.football.di.ApplicationComponent;
import com.llong.football.di.DaggerApplicationComponent;

import org.litepal.LitePal;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by cui-hl on 2018/08/30.
 */

public class FootballApplication extends DaggerApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }

}
