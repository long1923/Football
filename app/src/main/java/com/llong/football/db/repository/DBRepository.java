package com.llong.football.db.repository;

import com.llong.football.db.SubjectResponse;
import com.llong.football.db.bean.Subject;
import com.llong.football.db.repository.Dao.SubjectDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 处理数据库操作的工具类
 * Created by cui-hl on 2018/09/10.
 */
@Singleton
public class DBRepository {

    @Inject
    SubjectDao subjectDao;

    @Inject
    public DBRepository() {
    }


    /**
     * 将Subject对象集合保存到数据库中
     * 数据库插入操作需要在ApiRepository中进行，避免在画面级别进行数据库的插入操作。
     * @param response
     * @throws Throwable
     */
    public void saveSubject(SubjectResponse response) throws Throwable{
        if(response!=null && response.paper!=null){
            subjectDao.addSubjectList(response.paper);
        }
    }

    /**
     * 访问Subject对象的数据库，获取Subject对象的集合。
     * @param action    Rx中的action，用于画面数据的回传。
     */
    public void getSubjectList(Action1<List<Subject>> action){
        if(action==null){
            return;
        }
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
