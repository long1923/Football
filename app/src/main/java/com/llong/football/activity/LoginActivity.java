package com.llong.football.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.llong.football.R;
import com.llong.football.api.ApiRepository;
import com.llong.football.api.ResponseListener;
import com.llong.football.bean.SubjectResponse;
import com.llong.football.databinding.ActivityLoginBinding;

import javax.inject.Inject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity{

    @Inject
    ApiRepository apiRepository;

    ActivityLoginBinding binding;

    public final ObservableField<String> name = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(this);

        apiRepository.login(new ResponseListener<SubjectResponse>() {
            @Override
            public void onSuccess(SubjectResponse data) {
                name.set(data.toString());
            }

            @Override
            public void onFail(Exception e) {

            }
        }, "");
    }

}

