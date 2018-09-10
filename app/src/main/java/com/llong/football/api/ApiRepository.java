package com.llong.football.api;

import android.util.Log;

import com.llong.football.db.BaseResponse;
import com.llong.football.db.repository.DBRepository;
import com.llong.football.db.SubjectResponse;
import com.llong.football.http.HttpService;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by cui-hl on 2018/08/30.
 */

@Singleton
public class ApiRepository {

    @Inject
    HttpService httpService;

    @Inject
    DBRepository dbRepository;

    @Inject
    public ApiRepository() {
    }

    public void login(final ResponseListener<String> listener, String username){

        httpService.login(username)
                .map(new Func1<BaseResponse<SubjectResponse>, BaseResponse<SubjectResponse> >() {
                    @Override
                    public BaseResponse<SubjectResponse> call(BaseResponse<SubjectResponse> data) {
                        Log.i("Thread:save", Thread.currentThread().getName());
                        try {
                            dbRepository.saveSubject(data.r_data);
                        } catch (Throwable e) {
                            data=new BaseResponse<>();
                            data.r_code=-1;
                            data.r_info="Subject save failure";
                        }
                        return data;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<BaseResponse<SubjectResponse>>() {
                    @Override
                    public void onSuccess(BaseResponse<SubjectResponse> data) throws IOException {
                        Log.i("Thread:onSuccess", Thread.currentThread().getName());
                        listener.onSuccess(ResponseListener.SUCCESS);
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
