package com.llong.football.db.bean;

/**
 * @author cui-hl
 * @version 1.0 2018/8/22
 */
public class OptionInfo {

    public String id;
    public String name;

    public OptionInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public OptionInfo(String name) {
        this.name = name;
    }
}
