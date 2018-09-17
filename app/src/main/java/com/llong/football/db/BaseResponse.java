package com.llong.football.db;

import com.llong.football.di.DataException;

/**
 * Created by cui-hl on 2018/08/30.
 */

public class BaseResponse<T>{

    public DataException exception;

    public int r_code;

    public String r_info;

    public T r_data;

}
