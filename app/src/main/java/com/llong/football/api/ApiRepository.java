package com.llong.football.api;

import com.llong.football.bean.User;
import com.llong.football.http.HttpService;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cui-hl on 2018/08/30.
 */

@Singleton
public class ApiRepository {

    @Inject
    HttpService httpService;

    @Inject
    public ApiRepository() {
    }

    public void login(Observer observer, String username){

        httpService.login(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
