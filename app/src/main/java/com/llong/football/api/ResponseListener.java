package com.llong.football.api;

import android.util.Log;

import com.llong.football.bean.BaseData;

import rx.Observer;

/**
 * Created by cui-hl on 2018/08/30.
 */

public abstract class ResponseListener<T>{

    public abstract void onSuccess(T data);

    public abstract void onFail(Exception e);

}
