package com.example.myapplication.utils;

/**
 * @ Logout.java
 * 作用 日志输出接口
 * 注意事项
 */

import android.util.Log;

import com.example.myapplication.Config;


/**
 * @Description:打印工具类
 */
public class Logout {

    /**
     * log  info
     *
     * @param tag
     * @param msg
     */

    private static String TAG = "CWT";

    public static void i(String tag, Object msg) {
        if (Config.isDebug)
            log("i",tag,msg);

    }

    public static void i(Object msg) {
        if (Config.isDebug)
            log("i",TAG,msg);
    }

    /**
     * log  debug
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, Object msg) {
        if (Config.isDebug)
            log("d",tag,msg);
    }

    public static void d(Object msg) {
        if (Config.isDebug)
            log("d",TAG,msg);
    }

    /**
     * log  error
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, Object msg) {
        if (Config.isDebug)
            log("e",tag,msg);
    }

    public static void e(Object msg) {
        if (Config.isDebug)
            log("e",TAG,msg);
    }

    /**
     * log  v
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, Object msg) {
        if (Config.isDebug)
            log("v",tag,msg);
    }

    public static void v(Object msg) {
        if (Config.isDebug) {
            log("v",TAG,msg);
        }
    }

    /**
     * print msg
     *
     * @param msg
     */
    public static void printf(Object msg) {
        if (Config.isDebug) {
            Log.i(TAG, msg + "");
        }
    }

    private static void log(Object... content) {
        String tag = "WSGW";
        String flag = "e";
        String message = "";
        if (content == null || content.length == 0 || content[2]==null||content.length > 3) {
            return;
        }
        if (content.length == 3) {
            if (content[0] instanceof String) {//flag
                flag = (String) content[0];
            }
            if (content[1] instanceof String &&content[1]!=null) {//tag
                tag = (String) content[1];
            }
            if (content[2] instanceof String) {//message
                message = (String) content[2];
            } else {
                message = content[2].toString();
            }

        } else if (content.length == 2) {
            if (content[0] instanceof String) {//flag
                flag = (String) content[0];
            }
            if (content[1] instanceof String) {//message
                message = (String) content[1];
            } else {
                message = content[1].toString();
            }
        }

        //log截取
        if (message.length() > 2000) {
            for (int i = 0; i < message.length(); i += 2000) {
                if (i + 2000 < message.length()) {
                    logOut(flag,tag,message.substring(i, i + 2000));
                } else {
                    logOut(flag,tag,message.substring(i, message.length()));
                }
            }
        } else {
            logOut(flag,tag,message);
        }
    }

    private static void logOut(String flag, String tag, String message) {
        if ("v".equals(flag)) {
            Log.v(tag, "msg: " + message);
        } else if ("d".equals(flag)) {
            Log.d(tag, "msg: " + message);
        } else if ("i".equals(flag)) {
            Log.i(tag, "msg: " + message);
        } else if ("e".equals(flag)) {
            Log.e(tag, "msg: " + message);
        } else {
            Log.e(tag, "msg: " + message);
        }


    }


}
