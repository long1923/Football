package com.llong.football.api;

import com.llong.football.db.BaseResponse;

import java.io.IOException;

import rx.Observer;

/**
 * API请求结果回调接口。
 * 需要保存数据库的API请求在此仅回调API请求的成功与否。T类型为String，成功走onSuccess(ResponseListener.SUCCESS),失败走onFail()，自定义异常信息。
 * 无需保存数据库的API请求在此可回调API返还数据。T类型为具体结构体。
 * Created by cui-hl on 2018/09/10.
 */

public abstract class ResponseObserver <T> implements Observer<T> {

    public abstract void onSuccess(T data) throws IOException;

    public abstract void onFail(Exception e);

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T object) {

        try {
            if(object instanceof BaseResponse){
                if(((BaseResponse) object).r_code==0){
                    onSuccess(object);
                }else{
                    Exception exception=new Exception(((BaseResponse) object).r_info);
                    onFail(exception);
                }
            }else{
                onSuccess(object);
            }
        } catch (IOException e) {
            //数据处理过程中发生异常。
            onFail(e);
        }
    }
}
