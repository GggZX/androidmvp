package com.example.myapplication.base.common.http.exception;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by liukang on 2018/6/13.
 * 异常信息
 */

public class ExceptionHandle {

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUNT = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable handleException(Throwable e) {
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUNT:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "服务异常，请重试 " + httpException.code();
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析异常";
            return ex;
        } else if (e instanceof ConnectException || e instanceof UnknownHostException || e instanceof NoRouteToHostException) {
            ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR);
            ex.message = "网络连接不可用，请稍后重试";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证异常";
            return ex;
        } else if (e instanceof ConnectTimeoutException || e instanceof java.net.SocketTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络连接超时";
            return ex;
        } else {
            ex = new ResponeThrowable(e, ERROR.UNKNOWN);
            Log.e("ERROR",ex.getMessage());
            ex.message = "未知错误";
            return ex;
        }
    }

    class ERROR {
        /**
         * 未知错误
         */
        public static final String UNKNOWN = "1000";
        /**
         * 解析错误
         */
        public static final String PARSE_ERROR = "1001";
        /**
         * 网络错误
         */
        public static final String NETWORD_ERROR = "1002";
        /**
         * 协议出错
         */
        public static final String HTTP_ERROR = "1003";

        /**
         * 证书出错
         */
        public static final String SSL_ERROR = "1005";

        /**
         * 连接超时
         */
        public static final String TIMEOUT_ERROR = "1006";

    }
}
