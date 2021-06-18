package com.example.myapplication.utils;

import android.text.TextUtils;

public class FUtil {
    public static String FormatWithoutUnit(String s){
        if (s==null||s.equals(""))
            return "--";
        else return s;
    }
    public static String Format(String s, String unit){
        if (s==null||s.equals(""))
            return "--";
        else return s+unit;
    }

    public static String FormatSringAdd(String s, String s2){
        if (TextUtils.isEmpty(s)&& TextUtils.isEmpty(s2)){
            return "--";
        }
        if (TextUtils.isEmpty(s)&&!TextUtils.isEmpty(s2)){
            return "--"+s2;
        }
        if (!TextUtils.isEmpty(s)&& TextUtils.isEmpty(s2)){
            return s;
        }
        else return s+s2;
    }
}
