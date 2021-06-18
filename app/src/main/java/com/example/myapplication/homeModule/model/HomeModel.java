package com.example.myapplication.homeModule.model;

import com.example.myapplication.MyApplication;
import com.example.myapplication.base.common.http.BannerBean;
import com.example.myapplication.base.common.http.HttpHelper;
import com.example.myapplication.base.common.http.MyDisposableManager;
import com.example.myapplication.base.common.http.api.RetrofitService;
import com.example.myapplication.base.common.http.callback.CallBack;
import com.example.myapplication.base.common.http.exception.ExceptionHandle;
import com.example.myapplication.base.common.http.exception.ResponeThrowable;
import com.example.myapplication.logingmodule.response.LoginResponse;
import com.example.myapplication.utils.SPUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomeModel {

    public void getallbanner( final CallBack<BannerBean> callBack) {
        MyDisposableManager.getInstance().addDisposable("getallbanner",
                HttpHelper.INSTANCE
                        .getService(RetrofitService.class)
                        .getallbanner()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<BannerBean>() {
                            @Override
                            public void accept(BannerBean bannerBean) throws Exception {
//                                SPUtils.putString(MyApplication.getApplication().getApplicationContext(),"token",loginResponse.getToken());
                                callBack.onSuccess(bannerBean);
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
