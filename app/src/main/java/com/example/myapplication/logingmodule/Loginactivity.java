package com.example.myapplication.logingmodule;

import android.content.Intent;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.Config;
import com.example.myapplication.R;
import com.example.myapplication.RequestConstants;
import com.example.myapplication.base.baseview.BaseActivity;
import com.example.myapplication.base.baseview.BaseView;
import com.example.myapplication.common.WebActivity;
import com.example.myapplication.homeModule.HomeActivity;
import com.example.myapplication.logingmodule.presenter.LoginPresenter;
import com.example.myapplication.logingmodule.response.LoginResponse;
import com.example.myapplication.logingmodule.view.ILoginView;
import com.example.myapplication.utils.AntiHijackingUtil;
import com.example.myapplication.utils.CommonUtil;
import com.example.myapplication.utils.MD5Utils;
import com.example.myapplication.utils.SPUtils;
import com.example.myapplication.view.ChangeNetworkDialog;
import com.example.myapplication.view.Loading_view;

import java.util.HashMap;

import butterknife.BindView;

public class Loginactivity  extends BaseActivity<LoginPresenter> implements ILoginView {
    @BindView(R.id.text_login)
    TextView text_login;
    @BindView(R.id.edaccount)
    EditText edAccount;
    @BindView(R.id.edpsw)
    EditText edPsw;
    @BindView(R.id.tvAgree)
    TextView tvAgree;
    @BindView(R.id.simple)
    ImageView simple;


    Loading_view loading_view;
    boolean edacHasStr,edPSWhasStr;

    @Override
    public void initMyData() {
        if (getIntent().hasExtra("clearId")&&getIntent().getStringExtra("clearId").equals("clearId")){
            SPUtils.logout(this);
        }
        loading_view=new Loading_view(this,R.style.CustomProgressDialog);
//
        edAccount.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        if (Config.isDebug){
            edAccount.setText("admin");
            edPsw.setText("admin");
        }
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Config.isDebug){
                    ChangeNetworkDialog dialog = ChangeNetworkDialog.newInstance();
                    dialog.show(getFragmentManager(), "show");
                }
            }
        });
//
//        if (!CommonUtil.isEmpty(new PatternHelper().getFromStorage()))
//            PatternLockSettingActivity.startCheckLockFromLogin(Loginactivity.this);
//                startActivity(new Intent(Loginactivity.this, PatternLockSettingActivity.class));
    }

    @Override
    public LoginPresenter getPresenter(BaseView view) {
        return new LoginPresenter(this,this);
    }

    @Override
    public void initMyListener() {
        text_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startHome();
                if (!checkPSWAndAccount())return;
                String psw= MD5Utils.digest(edPsw.getText().toString());
                String psw2=edPsw.getText().toString();
                String acount=edAccount.getText().toString();
                HashMap<String, Object> hashMap=new HashMap<>();
//                hashMap.put("grant_type","password");
                hashMap.put("password",psw2);
                hashMap.put("username",acount);
                mPresenter.login(hashMap);
                showLoading();

            }
        });
        edAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s))
                edacHasStr=false;
            else edacHasStr=true;
            if (edacHasStr&&edPSWhasStr){
            text_login.setBackground(getResources().getDrawable(R.mipmap.background_5));
            text_login.setClickable(true);
            }else {
                text_login.setBackground(getResources().getDrawable(R.mipmap.background_4));
                text_login.setClickable(false);
            }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s))
                    edPSWhasStr=false;
                else edPSWhasStr=true;
                if (edacHasStr&&edPSWhasStr){
                    text_login.setBackground(getResources().getDrawable(R.mipmap.background_5));
                    text_login.setClickable(true);
                }else {
                    text_login.setBackground(getResources().getDrawable(R.mipmap.background_4));
                    text_login.setClickable(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        String conent = " <a href='https://www.qq.com' style ='color:#ff0000;'>《隐私权保护声明》</a>";
        String conent = "注册即同意<a href='"+ RequestConstants.USER_AGREEMENT+"'>《用户协议》</a> 及 <a style='color:#000000;'" +
                "href='"+ RequestConstants.PRIVACY_POLICY+"'>《隐私权保护声明》</a>";
        tvAgree.setMovementMethod(LinkMovementMethod.getInstance());//使超链接可点击
        tvAgree.setText(CommonUtil.setTextLink(this, conent, new CommonUtil.OnClickGotoUrlListener() {
            @Override
            public void gotoUrl(String url) {
                if(RequestConstants.PRIVACY_POLICY.equals(url)){
                    WebActivity.go2Url(Loginactivity.this,url, "隐私权保护声明");
                }else{
                    WebActivity.go2Url(Loginactivity.this,url, "舱用户协议");
                }
            }
        }));
    }
    String account,psw;
    private boolean checkPSWAndAccount(){
        account=edAccount.getText().toString().replaceAll(" ","");
        psw=edPsw.getText().toString();
        if (account==null||account.equals("")){
            toastShort("请输入账号");
            return false;
        }
        if (psw==null||psw.equals("")){
            toastShort("请输入密码");
            return false;
        }
    /*    if (!CheckInputUtil.isPwd(psw)){
            toastShort("密码不符合校验规则");
            return false;
        }*/


        else return true;
    }


    private void startHome(){

        Intent intent=new Intent(Loginactivity.this, HomeActivity.class);
        intent.putExtra("from","login");
        startActivity(intent);
        finish();
    }
    @Override
    public int getlayoutid() {
        return R.layout.activity_login2;
    }

    @Override
    public void initview() {
        text_login=findViewById(R.id.text_login);

        showWaterMask=false;
    }

    @Override
    public void onSuccess(LoginResponse loginResponse) {
        hideLoading();
        toastShort("success");
        if(loginResponse.getToken()!=null){
            startHome();
        }else {
            toastShort("账号或密码错误");
        }

//        if (CommonUtil.isEmpty(new PatternHelper().getFromStorage()))
//            PatternLockSettingActivity.startSettingLockFromLogin(Loginactivity.this);
//    toastShort("loginsuccess username="+loginResponse.getData().getUser_name()+"  rolename="+loginResponse.getData().getRole_name());
    }

    @Override
    protected void onStop() {
        super.onStop();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 白名单
                boolean safe = AntiHijackingUtil.checkActivity(getApplicationContext());
                // 系统桌面
                boolean isHome = AntiHijackingUtil.isHome(getApplicationContext());
                // 锁屏操作
                boolean isReflectScreen = AntiHijackingUtil.isReflectScreen(getApplicationContext());
                // 判断程序是否当前显示
                if (!safe && !isHome && !isReflectScreen) {
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "已切换至后台运行",
                            Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        }).start();

    }

    @Override
    public void onFail(String msg) {
        hideLoading();
//        startHome();
        toastShort(msg);
//        startActivity(new Intent(Loginactivity.this, HomeActivity.class));
    }

    public void showLoading(){
        if (loading_view==null)
            loading_view=new Loading_view(this,R.style.CustomProgressDialog);
        if (!loading_view.isShowing()){
            loading_view.show();
        }
    }

    public void hideLoading(){
        if (loading_view==null)return;
        if (!loading_view.isShowing())return;
        loading_view.dismiss();
    }
}
