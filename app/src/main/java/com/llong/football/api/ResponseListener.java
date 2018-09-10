package com.llong.football.api;

/**
 * Created by cui-hl on 2018/08/30.
 */

public abstract class ResponseListener<T>{

    public static String SUCCESS="SUCCESS";

    public abstract void onSuccess(T data);

    public abstract void onFail(Exception e);

}
