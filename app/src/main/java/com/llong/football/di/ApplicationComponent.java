package com.llong.football.di;

import android.app.Application;

import com.llong.football.FootballApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by cui-hl on 2018/08/30.
 */

@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        HttpModule.class})
public interface ApplicationComponent extends AndroidInjector<FootballApplication>{

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<FootballApplication> {
    }

}
