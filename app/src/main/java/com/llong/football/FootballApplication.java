package com.llong.football;

import android.app.Application;

import com.llong.football.di.ApplicationComponent;
import com.llong.football.di.DaggerApplicationComponent;

/**
 * Created by cui-hl on 2018/08/30.
 */

public class FootballApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationComponent.builder().build().inject(this);
    }
}
