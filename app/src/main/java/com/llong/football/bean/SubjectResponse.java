package com.llong.football.bean;

import java.util.List;

/**
 * Created by cui-hl on 2018/08/30.
 */

public class SubjectResponse {

    public List<Subject> paper;

    @Override
    public String toString() {
        return "SubjectResponse{" +
                "paper=" + paper +
                '}';
    }

    public static class Subject{

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


    public static class PaperDetail{

        public String orderNumber;

        public String describe;

        @Override
        public String toString() {
            return "PaperDetail{" +
                    "orderNumber='" + orderNumber + '\'' +
                    ", describe='" + describe + '\'' +
                    '}';
        }
    }
}
