package com.llong.football.db.repository;

import com.llong.football.db.SubjectResponse;
import com.llong.football.db.bean.PaperDetail;
import com.llong.football.db.bean.Subject;
import com.llong.football.db.repository.Dao.SubjectDao;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by cui-hl on 2018/09/10.
 */

@Singleton
public class DBRepository {

    @Inject
    SubjectDao subjectDao;

    @Inject
    public DBRepository() {
    }


    public void saveSubject(SubjectResponse response) throws Throwable{
        if(response!=null && response.paper!=null){
            subjectDao.addSubjectList(response.paper);
        }
    }

    public void getSubjectList(Action1<List<Subject>> action){
        Observable.create(new Observable.OnSubscribe<List<Subject>>() {
                    @Override
                    public void call(Subscriber<? super List<Subject>> subscriber) {
                        List<Subject> list=subjectDao.getSubjectList();
                        subscriber.onNext(list) ;
                    }
                })
                .subscribeOn(Schedulers.io())     //定义事件产生线程：io线程
                .observeOn(AndroidSchedulers.mainThread())     //事件消费线程：主线程
                .subscribe(action) ;
    }




}
