package com.example.myapplication.base.common.http.callback;

public interface CallBack<T> {

    void onSuccess(T t);

    void onFail(String msg);
}
