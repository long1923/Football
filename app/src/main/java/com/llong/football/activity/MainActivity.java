package com.llong.football.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.llong.football.R;
import com.llong.football.databinding.ActivityMainBinding;
import com.llong.football.view.RecyclerMultipleAdapter;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(this);

        adapter=new RecyclerMultipleAdapter(RecyclerMultipleAdapter.RecommendLayoutId);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        //加载数据
        viewModel.initData();

        viewModel.getDataLiveData().observe(this, new Observer<List<RecommendViewModel>>() {
            @Override
            public void onChanged(@Nullable List<RecommendViewModel> list) {
                adapter.setData(list);
            }
        });
    }




    public void openLogin(View view){

    }



}
