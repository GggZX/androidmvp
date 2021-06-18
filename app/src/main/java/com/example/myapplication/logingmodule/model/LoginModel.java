package com.example.myapplication.logingmodule.model;

import com.example.myapplication.MyApplication;
import com.example.myapplication.base.baseview.BaseModel;
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

public class LoginModel extends BaseModel {


    public void requestLogin(HashMap<String, Object> hashMap, final CallBack<LoginResponse> callBack) {
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),"{\n" +
                "    \"username\":\"12345\",\n" +
                "    \"password\":\"123\"\n" +
                "    }");
        MyDisposableManager.getInstance().addDisposable("loginbyPsw",
                HttpHelper.INSTANCE
                        .getService(RetrofitService.class)
                        .loginbyPsw(requestBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<LoginResponse>() {
                            @Override
                            public void accept(LoginResponse loginResponse) throws Exception {
                                SPUtils.putString(MyApplication.getApplication().getApplicationContext(),"token",loginResponse.getToken());
//                                SharedPreferencesUtil.getInstance().saveString("token",loginResponse.getToken());
                                callBack.onSuccess(loginResponse);
                            }
                            /*    if (loginResponse.getCode()!=200){
                                    callBack.onFail(CommonUtil.getString(loginResponse.getError_description()));
                                }
                                else
                                {
                                    if (loginResponse != null ) {
                                        LoginManager.getInstance().saveLoginEnity(loginResponse);
                                        callBack.onSuccess(loginResponse);
                                        Hawk.put(LoginManager.LOGIN_INFO, loginResponse);
                                    } else {
                                        callBack.onFail(CommonUtil.getString(loginResponse.getReturn_msg()));

                                    }
                                }
                            }*/
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                ResponeThrowable responeThrowable = ExceptionHandle.handleException(throwable);
                                callBack.onFail(responeThrowable.message);
                            }
                        }));
    }

}
