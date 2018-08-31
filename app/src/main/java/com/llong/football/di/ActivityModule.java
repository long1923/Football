package com.llong.football.di;

import android.app.Activity;

import com.llong.football.activity.BaseActivity;
import com.llong.football.activity.MainActivity;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by cui-hl on 2018/08/31.
 */
@Module(subcomponents = ActivitySubcomponent.class)
public abstract class ActivityModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bind(ActivitySubcomponent.Builder builder);
}
