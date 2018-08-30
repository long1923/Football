package com.llong.football.di;

import com.google.gson.Gson;
import com.llong.football.http.HttpService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cui-hl on 2018/08/30.
 */
@Module
public class HttpModule {

    @Singleton
    @Provides
    public OkHttpClient providesOkHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)      //设置连接超时
                .build();
    }

    @Singleton
    @Provides
    public Retrofit providesRetrofit(Gson gson, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl("https://mytgw01apjpp-pre-cecandroid.azurewebsites.net/fch/android/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Singleton
    @Provides
    public HttpService providesHttpService(Retrofit retrofit){
        return retrofit.create(HttpService.class);
    }
}
