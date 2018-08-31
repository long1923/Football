package com.llong.football.di;

import com.llong.football.activity.BaseActivity;
import com.llong.football.activity.MainActivity;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by cui-hl on 2018/08/31.
 */
@Subcomponent
public interface ActivitySubcomponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {

    }
}
