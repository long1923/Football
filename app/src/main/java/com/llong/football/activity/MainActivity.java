package com.llong.football.activity;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.llong.football.R;
import com.llong.football.databinding.ActivityMainBinding;
import com.llong.football.di.DataException;
import com.llong.football.fragment.MessageDialogFragment;
import com.llong.football.widget.RecyclerMultipleAdapter;
import com.llong.football.viewmodel.MainViewModel;
import com.llong.football.viewmodel.item.RecommendViewModel;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends BaseActivity{

    @Inject
    MainViewModel viewModel;

    ActivityMainBinding binding;

    private RecyclerMultipleAdapter<RecommendViewModel> adapter;

    public final ObservableField<String> name = new ObservableField<>();

    private MainActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(this);

        adapter=new RecyclerMultipleAdapter(RecyclerMultipleAdapter.RecommendLayoutId);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        viewModel.getDataLiveData().observe(this, new Observer<List<RecommendViewModel>>() {
            @Override
            public void onChanged(@Nullable List<RecommendViewModel> list) {
                adapter.setData(list);
                closeLoading();
            }
        });

        viewModel.getErrorLiveData().observe(this, new Observer<DataException>() {
            @Override
            public void onChanged(@Nullable DataException e) {
                closeLoading();
                new MessageDialogFragment.Builder(activity)
                        .message("请求失败")
                        .positiveLabel("OK")
                        .requestCode(200)
                        .cancelable(false)
                        .buildAndShow();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity=null;
    }

    public void sendData(View view){
        showLoading();
        //加载数据
        viewModel.initData();
    }

}
