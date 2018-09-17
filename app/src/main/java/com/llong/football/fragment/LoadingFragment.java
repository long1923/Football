package com.llong.football.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llong.football.R;
import com.llong.football.databinding.FragmentLoadingBinding;

/**
 * loading加载框
 * Created by cui-hl on 2018/09/17.
 */
public class LoadingFragment extends DialogFragment {

    private final String tag="Loading";

    FragmentLoadingBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_loading, container, false);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //点击空白区域不消失
        this.setCancelable(false);

        return binding.getRoot();
    }


    public void show(FragmentManager manager){
        super.show(manager, tag);
    }

}
