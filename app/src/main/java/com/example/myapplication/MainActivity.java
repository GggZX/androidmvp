package com.example.myapplication;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myapplication.base.baseview.BaseActivity;
import com.example.myapplication.base.baseview.BasePresenter;
import com.example.myapplication.base.baseview.BaseView;


public class MainActivity extends BaseActivity {

    RadioGroup radioGroup;
    FrameLayout frameLayout;
    RadioButton rbHome,rbMine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initMyData() {

    }

    @Override
    public BasePresenter getPresenter(BaseView view) {
        return null;
    }

    @Override
    public void initMyListener() {

    }


    @Override
    public int getlayoutid() {
        return R.layout.activity_main;
    }

    @Override
    public void initview() {
        frameLayout=findViewById(R.id.frame);
        rbHome=findViewById(R.id.button_home);
        rbMine=findViewById(R.id.button_my);
    }

}
