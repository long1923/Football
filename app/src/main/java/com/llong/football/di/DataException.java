package com.llong.football.di;

/**
 * Created by cui-hl on 2018/09/17.
 */

public class DataException extends Exception {

    /** 类别 */
    public final Type type;

    /** 类别 */
    public enum Type {

        /** request参数异常 */
        REQUEST_PARAM_ERROR,

        /** 网络异常 */
        NETWORK_ERROR,

        /** 数据处理异常 */
        DATA_PROCESS_ERROR,

        /** response参数异常 */
        RESPONSE_PARAM_ERROR,

        /** 数据库存储异常 */
        DB_STORAGE_ERROR,
    }

    public DataException(Type type){
        super();
        this.type = type;
    }

    public DataException(Type type, Throwable cause){
        super(cause);
        this.type = type;
    }

    public DataException(Type type, String massage){
        super(massage);
        this.type = type;
    }
}
