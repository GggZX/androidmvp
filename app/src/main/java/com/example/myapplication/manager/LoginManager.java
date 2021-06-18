package com.example.myapplication.manager;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.example.myapplication.base.BaseEnity;
import com.example.myapplication.logingmodule.response.LoginResponse;
import com.example.myapplication.utils.CommonUtil;
import com.example.myapplication.utils.SPUtils;


public class LoginManager extends BaseEnity {


    public Context mContext;
    private DataStore dataStore;
    public static final String LOGIN_INFO = "login_info";

    public static LoginManager getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        @SuppressLint("StaticFieldLeak")
        private static LoginManager instance = new LoginManager();
    }

    public void init(Application application) {
        if (application == null) throw new IllegalArgumentException("context not is null!!!");
        this.mContext = application.getApplicationContext();
        dataStore = new DataStore(mContext);
    }

    public void saveLoginEnity(LoginResponse response) {
        dataStore.put(LOGIN_INFO, response);
        SPUtils.putString(mContext,"loginstatus",response.getData().getAccessToken());
    }

    public LoginResponse getLoginEnity() {
        return dataStore.get(LOGIN_INFO, null);
    }


    public String getAccessToken(){
        LoginResponse loginResponse=getLoginEnity();
        return loginResponse==null?"": CommonUtil.getString(loginResponse.getData().getAccessToken());

    }


    public String getTokenType(){
        LoginResponse loginResponse=getLoginEnity();
        return loginResponse==null?"":CommonUtil.getString(loginResponse.getData().getTokenType());

    }


    public String getRealUserName(){
        LoginResponse l = getLoginEnity();
        return l==null?"": CommonUtil.getString(l.getData().getReal_name());
    }

    public String getUserName(){
        LoginResponse l = getLoginEnity();
        if (null!=l){
            return l.getData().getUser_name();
        }
        else return " ";
    }



}
