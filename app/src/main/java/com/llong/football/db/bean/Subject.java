package com.llong.football.db.bean;

import com.llong.football.db.SubjectResponse;

import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * Created by cui-hl on 2018/09/10.
 */

public class Subject extends LitePalSupport {

    public String SubjectName;

    public String SubjectType;

    public String Subject;

    public List<PaperDetail> paperDetail;

    @Override
    public String toString() {
        return "SubjectResponse{" +
                "SubjectName='" + SubjectName + '\'' +
                ", SubjectType='" + SubjectType + '\'' +
                ", SubjectResponse='" + Subject + '\'' +
                ", paperDetail=" + paperDetail +
                '}';
    }
}
