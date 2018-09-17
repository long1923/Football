package com.llong.football.widget;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView Holder
 * @author liang-ll
 * @version 1.0 2018/01/10
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }


}

