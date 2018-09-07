package com.llong.football.api;

import com.llong.football.bean.BaseResponse;
import com.llong.football.bean.SubjectResponse;
import com.llong.football.http.HttpService;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;
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

    public void login(final ResponseListener<SubjectResponse> listener, String username){

        httpService.login(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<BaseResponse<SubjectResponse>>() {

                    @Override
                    public void onSuccess(BaseResponse<SubjectResponse> data) throws IOException {
                        listener.onSuccess(data.r_data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        listener.onFail(e);
                    }
                });
    }

    abstract class ResponseObserver<T> implements Observer<T> {

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
                onFail(e);
            }
        }
    }
}
