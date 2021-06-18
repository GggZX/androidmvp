package com.example.myapplication.logingmodule.presenter;

import android.content.Context;

import com.example.myapplication.base.baseview.BasePresenter;
import com.example.myapplication.base.common.http.callback.CallBack;
import com.example.myapplication.logingmodule.model.LoginModel;
import com.example.myapplication.logingmodule.response.LoginResponse;
import com.example.myapplication.logingmodule.view.ILoginView;

import java.util.HashMap;

public class LoginPresenter implements BasePresenter {


    LoginModel loginModel;
    ILoginView iLoginView;
    Context context;

    public LoginPresenter(Context context, ILoginView iLoginView){
        this.context=context;
        this.iLoginView=iLoginView;
        loginModel=new LoginModel();
    }


    public void login(HashMap<String, Object> h){

        loginModel.requestLogin(h, new CallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                iLoginView.onSuccess(loginResponse);
            }

            @Override
            public void onFail(String msg) {
                iLoginView.onFail(msg);
            }
        });
    }

    @Override
    public void cancle() {

    }
}
