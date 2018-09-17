package com.llong.football.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.llong.football.activity.MainActivity;
import com.llong.football.api.ApiRepository;
import com.llong.football.api.ResponseObserver;
import com.llong.football.db.bean.Subject;
import com.llong.football.db.repository.DBRepository;
import com.llong.football.di.DataException;
import com.llong.football.viewmodel.item.RecommendViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

/**
 * Created by cui-hl on 2018/09/12.
 */
public class MainViewModel extends BaseViewModel {

    @Inject
    ApiRepository apiRepository;

    @Inject
    DBRepository dbRepository;

    private APIResponseObserver apiResponseObserver;

    private DBAction dbAction;

    private MutableLiveData<List<RecommendViewModel>> dataLiveData = new MutableLiveData<>();


    @Inject
    public MainViewModel() {
        apiResponseObserver=new APIResponseObserver();
        dbAction=new DBAction();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        apiResponseObserver=null;
        dbAction=null;
    }

    public MutableLiveData<List<RecommendViewModel>> getDataLiveData() {
        return dataLiveData;
    }

    /**
     * 加载数据
     */
    public void initData(){
        //如果画面数据使用缓存，API请求回来的数据不直接响应在画面中。
        //API请求回来的数据先保存到数据库中，然后画面得到API请求成功的回调后，查询数据库做画面内容展示。
//        getList();
        requestData();
    }

    /**
     * 画面数据加载。
     */
    private void getList(){
        dbRepository.getSubjectList(dbAction);
    }

    /**
     * API请求
     */
    private void requestData(){
        apiRepository.login(apiResponseObserver, "");
    }

    /**
     * 定义数据库查询Action。
     * 使用内部类的原因：实例化对象在activity销毁时清除内存引用，避免内存泄漏。
     */
    class DBAction implements Action1<List<Subject>> {
        @Override
        public void call(List<Subject> subjects) {
            if(subjects==null || subjects.size()<=0){

            }else{
                List<RecommendViewModel> list=new ArrayList<>();
                for(Subject subject:subjects){
                    RecommendViewModel viewModel=new RecommendViewModel();
                    viewModel.setSubject(subject);
                    list.add(viewModel);
                }
                dataLiveData.setValue(list);
            }
        }
    }

    /**
     * 定义API请求Observer。
     * 使用内部类的原因：实例化对象在activity销毁时清除内存引用，避免内存泄漏。
     */
    class APIResponseObserver extends ResponseObserver<String> {
        @Override
        public void onSuccess(String data) {
            //API请求成功回调，访问数据库展示画面内容。
            getList();
        }

        @Override
        public void onFail(DataException e) {
            //API请求异常
            errorLiveData.setValue(e);
        }
    }
}
