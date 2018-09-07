package com.llong.football.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.llong.football.R;
import com.llong.football.api.ApiRepository;
import com.llong.football.api.ResponseListener;
import com.llong.football.databinding.ActivityMainBinding;

import javax.inject.Inject;



public class MainActivity extends BaseActivity {


    @Inject
    ApiRepository apiRepository;

    public final ObservableField<String> name = new ObservableField<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(this);


        ResponseListener<String> observer=new ResponseListener<String>() {
            @Override
            public void onSuccess(String data) {
                name.set(data);
            }

            @Override
            public void onFail(Exception e) {

            }
        };
        apiRepository.login(observer, "");

    }

    public void openLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

}
