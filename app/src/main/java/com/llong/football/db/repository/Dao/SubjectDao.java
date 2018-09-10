package com.llong.football.db.repository.Dao;

import com.llong.football.db.bean.PaperDetail;
import com.llong.football.db.bean.Subject;

import org.litepal.LitePal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by cui-hl on 2018/09/10.
 */
@Singleton
public class SubjectDao {

    @Inject
    public SubjectDao() {
    }

    public void addSubjectList(List<Subject> list){
        for(Subject subject:list){
            addSubject(subject);
        }
    }

    public void addSubject(Subject data){
        List<Subject> list= LitePal.where("SubjectName = ?", data.SubjectName).find(Subject.class);
        for(Subject subject:list){
            List<PaperDetail> detailList= LitePal.where("SubjectName = ?", data.SubjectName).find(PaperDetail.class);
            for(PaperDetail detail:detailList){
                detail.delete();
            }
            subject.delete();
        }
        for(PaperDetail detail:data.paperDetail){
            detail.SubjectName=data.SubjectName;
        }
        LitePal.saveAll(data.paperDetail);
        data.save();
    }

    public List<Subject> getSubjectList(){
        List<Subject> list=LitePal.findAll(Subject.class);
        for(Subject subject:list){
            subject.paperDetail=LitePal.where("SubjectName = ?", subject.SubjectName).find(PaperDetail.class);
        }
        return list;
    }
}
