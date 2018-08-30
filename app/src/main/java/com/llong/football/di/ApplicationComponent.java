package com.llong.football.di;

import com.llong.football.FootballApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by cui-hl on 2018/08/30.
 */

@Singleton
@Component(modules = {HttpModule.class})
public interface ApplicationComponent {

    void inject(FootballApplication application);
}
