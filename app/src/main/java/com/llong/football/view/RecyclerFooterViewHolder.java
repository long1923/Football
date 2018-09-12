package com.llong.football.view;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerView Footer Holder
 * @author liang-ll
 * @version 1.0 2018/01/10
 */
public class RecyclerFooterViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public RecyclerFooterViewHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }


}

