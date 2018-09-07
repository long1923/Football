package com.llong.football.di;

import com.llong.football.FootballApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by cui-hl on 2018/08/30.
 */

@Singleton
@Component(modules = {HttpModule.class, ActivityModule.class, BuildersModule.class, AndroidSupportInjectionModule.class})
public interface ApplicationComponent extends AndroidInjector<FootballApplication>{

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<FootballApplication> {}
}
