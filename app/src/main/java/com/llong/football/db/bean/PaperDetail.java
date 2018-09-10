package com.llong.football.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by cui-hl on 2018/09/10.
 */

public class PaperDetail extends LitePalSupport {

    /**
     * 与Subject关联字段，SubjectName
     */
    public String SubjectName;

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
