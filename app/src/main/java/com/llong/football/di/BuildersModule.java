package com.llong.football.di;

import com.llong.football.activity.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by cui-hl on 2018/09/07.
 */

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = ActivityModule.class)
    abstract MainActivity mainActivityInjector();

}
