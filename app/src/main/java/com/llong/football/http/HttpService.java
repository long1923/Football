package com.llong.football.http;


import com.llong.football.bean.User;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by cui-hl on 2018/08/30.
 */

public interface HttpService {

    String REQUEST_HEADER = "Content-Type: application/json; charset=utf-8";

    @Headers({REQUEST_HEADER})
    @POST("members/initial/get")
    Observable<ResponseBody> login(@Body String username);
}
