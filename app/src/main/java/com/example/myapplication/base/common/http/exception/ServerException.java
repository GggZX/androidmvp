package com.example.myapplication.base.common.http.exception;

/**
 * <pre>
 *     author : wuxiaoqi
 *     e-mail : 1321972760@qq.com
 *     time   : 2018/06/19
 *     desc   : 服务异常
 *     version: 1.0
 * </pre>
 */
public class ServerException extends RuntimeException {

    public String code;
    public String message;

    public ServerException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
