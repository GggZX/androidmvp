package com.example.myapplication.common;

import com.example.myapplication.base.common.http.HttpHelper;
import com.example.myapplication.base.common.http.MyDisposableManager;
import com.example.myapplication.base.common.http.api.RetrofitService;
import com.example.myapplication.base.common.http.callback.CallBack;
import com.example.myapplication.base.common.http.exception.ExceptionHandle;
import com.example.myapplication.base.common.http.exception.ResponeThrowable;
import com.example.myapplication.utils.CommonUtil;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CommomModel {


    public void getcheckcode( final CallBack<SplashBean> callBack) {
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),"{'version':'38'}");
        MyDisposableManager.getInstance().addDisposable("BannerBean",
                HttpHelper.INSTANCE
                        .getService(RetrofitService.class)
                        .getcheckcode(requestBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<SplashBean>() {
                            @Override
                            public void accept(SplashBean homeMenuBean) throws Exception {
                                if (homeMenuBean != null ) {
                                    callBack.onSuccess(homeMenuBean);
                                } else {
                                    callBack.onFail(CommonUtil.getString(homeMenuBean.toString()));

                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                ResponeThrowable responeThrowable = ExceptionHandle.handleException(throwable);
                                callBack.onFail(responeThrowable.message);
                            }
                        }));
    }

}
