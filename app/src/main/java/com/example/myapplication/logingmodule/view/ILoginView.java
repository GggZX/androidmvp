package com.example.myapplication.logingmodule.view;


import com.example.myapplication.logingmodule.response.LoginResponse;

public interface ILoginView {

    void onSuccess(LoginResponse loginResponse);

    void onFail(String msg);
}
