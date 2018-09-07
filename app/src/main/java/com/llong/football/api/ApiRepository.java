package com.llong.football.api;

import com.llong.football.http.HttpService;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
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

    public void login(final ResponseListener listener, String username){

        httpService.login(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<ResponseBody>() {

                    @Override
                    public void onSuccess(ResponseBody data) throws IOException {
                        String value=data.string();
                        listener.onSuccess(value);
                    }

                    @Override
                    public void onFail(Exception e) {

                    }
                });
    }

    abstract class ResponseObserver<T> implements Observer {

        public abstract void onSuccess(T data) throws IOException;

        public abstract void onFail(Exception e);

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Object object) {

            try {
                onSuccess((T) object);
            } catch (IOException e) {
                onFail(e);
            }
        }
    }
}
