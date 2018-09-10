package com.llong.football.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

import com.llong.football.R;
import com.llong.football.api.ApiRepository;
import com.llong.football.api.ResponseListener;
import com.llong.football.db.bean.Subject;
import com.llong.football.db.repository.DBRepository;
import com.llong.football.db.SubjectResponse;
import com.llong.football.databinding.ActivityMainBinding;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;


public class MainActivity extends BaseActivity {


    @Inject
    ApiRepository apiRepository;

    @Inject
    DBRepository dbRepository;

    public final ObservableField<String> name = new ObservableField<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(this);


        apiRepository.login(new ResponseListener<String>() {
            @Override
            public void onSuccess(String data) {
                getList();
            }

            @Override
            public void onFail(Exception e) {
                String value=e.getMessage();
                name.set(value);
            }
        }, "");

    }

    private void getList(){
        dbRepository.getSubjectList(new Action1<List<Subject>>() {
            @Override
            public void call(List<Subject> subjects) {
                String value="";
                for(Subject subject:subjects){
                    value=value+subject.toString();
                }
                name.set(value);
            }
        });
    }

    public void openLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

}
