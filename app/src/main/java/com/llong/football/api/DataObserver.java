package com.llong.football.api;

import com.llong.football.bean.BaseData;

import rx.Observer;

/**
 * Created by cui-hl on 2018/08/30.
 */

public abstract class DataObserver<T> implements Observer {

    public abstract void onObserver(T data);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Object object) {
        if(object instanceof BaseData){

        }
        onObserver((T)object);
    }

}
