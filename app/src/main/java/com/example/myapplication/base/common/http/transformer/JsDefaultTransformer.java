package com.example.myapplication.base.common.http.transformer;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * JS交互相关模块使用
 * 因为不涉及到类型转换，故只作线程处理
 */
public class JsDefaultTransformer implements ObservableTransformer{
    @Override
    public ObservableSource apply(Observable upstream) {
      return   upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
