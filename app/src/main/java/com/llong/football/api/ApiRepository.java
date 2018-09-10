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
 * API请求工具类.
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

    /**
     * API请求处理。
     *
     * @param observer API请求结果的回调。
     * @param username
     */
    public void login(ResponseObserver observer, String username) {
        if (observer == null) {
            return;
        }
        httpService.login(username)
                .map(new Func1<BaseResponse<SubjectResponse>, BaseResponse<SubjectResponse>>() {
                    @Override
                    public BaseResponse<SubjectResponse> call(BaseResponse<SubjectResponse> data) {
                        //执行数据库插入操作(保存数据)。
                        try {
                            dbRepository.saveSubject(data.r_data);
                        } catch (Throwable e) {
                            //数据保存失败，本次API请求失败告终。
                            data = new BaseResponse<>();
                            data.r_code = -1;
                            data.r_info = "Subject save failure";
                        }
                        return data;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
