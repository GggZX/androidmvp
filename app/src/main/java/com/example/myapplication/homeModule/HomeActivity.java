package com.example.myapplication.homeModule;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.base.baseview.BaseActivity;
import com.example.myapplication.base.baseview.BasePresenter;
import com.example.myapplication.base.baseview.BaseView;
import com.example.myapplication.homeModule.fragment.HomeFragment;
import com.example.myapplication.homeModule.fragment.MineFragment;
import com.example.myapplication.view.MyTitleBar;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.button_home)
    RadioButton rhome;

    MyTitleBar myTitleBar;

    @Override
    public void initMyData() {
        myTitleBar=initTitleBar("三周年快乐");
        myTitleBar.setBackgroundColor1(getResources().getColor(R.color.transparent));
        myTitleBar.setTitleColor("#000000");
        myTitleBar.setLeftIcon(R.mipmap.back_white);
    }

    @Override
    public BasePresenter getPresenter(BaseView view) {
        return null;
    }

    HomeFragment homeFragment;
    MineFragment mineFragment;

    @Override
    public void initMyListener() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (checkedId){
                    case R.id.button_home:
                        if (homeFragment==null){
                            homeFragment=new HomeFragment();
                            fragmentTransaction.add(R.id.frame,homeFragment);
                        }
                        else {
                            fragmentTransaction.show(homeFragment);
                        }
                        if (mineFragment!=null)
                            fragmentTransaction.hide(mineFragment);
                        break;
                    case R.id.button_my:
                        if (mineFragment==null){
                            mineFragment=new MineFragment();
                            fragmentTransaction.add(R.id.frame,mineFragment);
                        }
                        else {
                            fragmentTransaction.show(mineFragment);
                        }
                        if (homeFragment!=null)
                            fragmentTransaction.hide(homeFragment);
                        break;
                }
                fragmentTransaction.commit();
            }
        });
        rhome.setChecked(true);
    }

    @Override
    public int getlayoutid() {
        return R.layout.activity_main;
    }

    @Override
    public void initview() {

    }
}
