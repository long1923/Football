package com.llong.football.http;


import com.llong.football.db.BaseResponse;
import com.llong.football.db.SubjectResponse;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by cui-hl on 2018/08/30.
 */

public interface HttpService {

    String REQUEST_HEADER = "Content-Type: application/json; charset=utf-8";

    @Headers({REQUEST_HEADER})
    @POST("members/initial/get2")
    Observable<BaseResponse<SubjectResponse>> login(@Body String username);
}
