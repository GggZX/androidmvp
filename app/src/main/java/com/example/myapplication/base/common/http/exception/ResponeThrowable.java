package com.example.myapplication.base.common.http.exception;

/**
 * Created by wuxiaoqi on 2018/6/13.
 * 请求异常
 */

public class ResponeThrowable extends Exception {
    public String code;
    public String message;

    public ResponeThrowable(Throwable throwable, String code) {
        super(throwable);
        this.code = code;
    }
}
