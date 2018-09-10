package com.llong.football.db.repository.Dao;

import com.llong.football.db.bean.PaperDetail;
import com.llong.football.db.bean.Subject;

import org.litepal.LitePal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 封装操作Subject对象数据库的Dao。
 * Created by cui-hl on 2018/09/10.
 */
@Singleton
public class SubjectDao {

    /**
     * 使用Dagger2进行注入
     */
    @Inject
    public SubjectDao() {
    }

    /**
     * 将Subject对象集合插入数据库
     * @param list
     */
    public void addSubjectList(List<Subject> list){
        for(Subject subject:list){
            addSubject(subject);
        }
    }

    /**
     * 保存Subject对象。
     * 保存前先根据唯一内容在数据库中做查询。
     * 如果存在相同内容，先delete删除在插入。(避免内容重复)
     * @param data
     */
    public void addSubject(Subject data){
        //根据唯一内容做查询
        List<Subject> list= LitePal.where("SubjectName = ?", data.SubjectName).find(Subject.class);
        for(Subject subject:list){
            List<PaperDetail> detailList= LitePal.where("SubjectName = ?", data.SubjectName).find(PaperDetail.class);
            for(PaperDetail detail:detailList){
                detail.delete();
            }
            //存在相同内容，删除数据库中的数据。
            subject.delete();
        }
        for(PaperDetail detail:data.paperDetail){
            detail.SubjectName=data.SubjectName;
        }
        LitePal.saveAll(data.paperDetail);
        //将数据插入数据库中。
        data.save();
    }

    /**
     * 获取数据库中所有的Subject对象。
     * @return
     */
    public List<Subject> getSubjectList(){
        List<Subject> list=LitePal.findAll(Subject.class);
        for(Subject subject:list){
            //关联的子表做查询。
            subject.paperDetail=LitePal.where("SubjectName = ?", subject.SubjectName).find(PaperDetail.class);
        }
        return list;
    }
}
