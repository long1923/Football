package com.llong.football.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.llong.football.R;
import com.llong.football.api.ApiRepository;
import com.llong.football.api.DataObserver;
import com.llong.football.databinding.ActivityMainBinding;

import javax.inject.Inject;

import rx.Observer;


public class MainActivity extends AppCompatActivity {


    @Inject
    ApiRepository apiRepository;

    public String value="000";
    public final ObservableField<String> name = new ObservableField<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(this);


        DataObserver<String> observer=new DataObserver<String>() {
            @Override
            public void onObserver(String data) {
                name.set(data);
            }
        };
        apiRepository.login(observer, "");

    }

}
