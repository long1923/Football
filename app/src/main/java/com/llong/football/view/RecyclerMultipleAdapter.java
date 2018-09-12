package com.llong.football.view;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.llong.football.BR;
import com.llong.football.R;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView Base
 * @author liang-ll
 * @version 1.0 2018/01/10
 */
public class RecyclerMultipleAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final static int RecommendLayoutId= R.layout.item_recommend;

    //底がloading
    public final static int footerLayoutId= R.layout.layout_loading;

    private List<T> mData;

    private boolean isHasFooter;

    private ItemClickListener onItemClick;

    private ItemLongClickListener onItemLongClick;

    /**
     * 複数のレイアウトが設定されている場合、viewTypeグループ
     */
    private List<Integer> viewTypeList;

    private boolean isNoItemClick;

    /**
     * 単一のレイアウトはviewTypeIdを設定することです
     */
    private int viewType;

    public RecyclerMultipleAdapter(int viewType) {
        this.viewType=viewType;
        this.mData = new ArrayList<T>();
    }

    public RecyclerMultipleAdapter(){

    }

    /**
     * 底loadingかどうか
     * @param hasFooter
     */
    public void setHasFooter(boolean hasFooter) {
        isHasFooter = hasFooter;
        notifyDataSetChanged();
    }

    public boolean getHasFooter(){
        return isHasFooter;
    }


    public RecyclerMultipleAdapter(List<Integer> viewTypeList) {
        this.viewTypeList=viewTypeList;
        this.mData = new ArrayList<T>();
    }

    public void setViewTypeList(List<Integer> viewTypeList) {
        this.viewTypeList = viewTypeList;
    }

    public void setData(List<T> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void setData(List<T> mData, List<Integer> viewTypeList) {
        this.mData = mData;
        this.viewTypeList = viewTypeList;
        notifyDataSetChanged();
    }

    public void addData(T data, Integer viewType){
        mData.add(data);
        viewTypeList.add(viewType);
        notifyDataSetChanged();
    }

    public void addDataAll(List<T> mData, List<Integer> viewTypeList){
        this.mData.addAll(mData);
        this.viewTypeList.addAll(viewTypeList);
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mData;
    }

    /**
     * アイテムをクリックできないように設定する
     * @param noItemClick   true:クリック不可  false:デフォルト，クリック可能
     */
    public void setNoItemClick(boolean noItemClick) {
        isNoItemClick = noItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                viewType, parent,false);

        if(viewType==footerLayoutId){
            RecyclerFooterViewHolder holder = new RecyclerFooterViewHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }else{
            RecyclerViewHolder holder = new RecyclerViewHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerFooterViewHolder) {

        }else{
            RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            bindData(recyclerViewHolder,getVariableId(),mData.get(position));
            if(!isNoItemClick){
                recyclerViewHolder.itemView.setOnClickListener(getClickListener(recyclerViewHolder.itemView,getRealPosition(holder)));
                recyclerViewHolder.itemView.setOnLongClickListener(getLongClickListener(recyclerViewHolder.itemView,getRealPosition(holder)));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isHasFooter){
            if( position >= mData.size()){
                return footerLayoutId;
            }else{
                if(viewTypeList==null){
                    return viewType;
                }else{
                    return viewTypeList.get(position);
                }
            }
        }else{
            if(viewTypeList==null){
                return viewType;
            }else{
                return viewTypeList.get(position);
            }
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return position;
    }

    /**
     * bind  reference
     * @return BR id
     */
    public int getVariableId(){
        return BR.vm;
    }


    /**
     * all data
     */
    @Override
    public int getItemCount() {
        if(mData==null){
            mData=new ArrayList<>();
        }
        if(isHasFooter){
            return mData.size()==0 ? mData.size() : mData.size()+1;
        }else{
            return mData.size();
        }
    }

    /**
     * data
     * @param pos position
     * @return T content
     */
    public T getItemData(int pos){
        return  mData.get(pos);
    }


    private View.OnClickListener getClickListener(final View view, final int pos){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( null != onItemClick) {
                    onItemClick.onItemClick(view, pos);
                }
            }
        };
    }

    private View.OnLongClickListener getLongClickListener(final View view, final int pos){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if( null != onItemLongClick) {
                    onItemLongClick.onItemLongClick(view, pos);
                }
                return true;
            }
        };
    }

    public void setItemLongClickListener(ItemLongClickListener listener) {
        this.onItemLongClick = listener;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.onItemClick = listener;
    }


    private void bindData(RecyclerViewHolder holder,int variableId ,T item){
        holder.getBinding().setVariable(variableId, item);
        holder.getBinding().executePendingBindings();
    }


    public interface ItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }


    public interface ItemClickListener {
        public void onItemClick(View view, int position);
    }
}

