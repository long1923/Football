package com.llong.football.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.llong.football.R;
import com.llong.football.api.ApiRepository;
import com.llong.football.api.ResponseObserver;
import com.llong.football.db.bean.Subject;
import com.llong.football.db.repository.DBRepository;
import com.llong.football.databinding.ActivityMainBinding;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;


public class MainActivity extends BaseActivity{


    @Inject
    ApiRepository apiRepository;

    @Inject
    DBRepository dbRepository;

    public final ObservableField<String> name = new ObservableField<>();

    private APIResponseObserver apiResponseObserver;

    private DBAction dbAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(this);

        apiResponseObserver=new APIResponseObserver();
        dbAction=new DBAction();
        //如果画面数据使用缓存，API请求回来的数据不直接响应在画面中。
        //API请求回来的数据先保存到数据库中，然后画面得到API请求成功的回调后，查询数据库做画面内容展示。
        getList();
        requestData();
    }

    @Override
    protected void onClear() {
        dbAction=null;
        apiResponseObserver=null;
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

    public void openLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * 定义数据库查询Action。
     * 使用内部类的原因：实例化对象在activity销毁时清除内存引用，避免内存泄漏。
     */
    class DBAction implements Action1<List<Subject>>{
        @Override
        public void call(List<Subject> subjects) {
            if(subjects==null || subjects.size()<=0){
                name.set("加载中...");
            }else{
                String value="";
                for(Subject subject:subjects){
                    value=value+subject.toString();
                }
                name.set(value);
            }
        }
    }

    /**
     * 定义API请求Observer。
     * 使用内部类的原因：实例化对象在activity销毁时清除内存引用，避免内存泄漏。
     */
    class APIResponseObserver extends ResponseObserver<String>{
        @Override
        public void onSuccess(String data) {
            //API请求成功回调，访问数据库展示画面内容。
            getList();
        }

        @Override
        public void onFail(Exception e) {
            //API请求失败
            String value=e.getMessage();
            name.set(value);
        }
    }

}
