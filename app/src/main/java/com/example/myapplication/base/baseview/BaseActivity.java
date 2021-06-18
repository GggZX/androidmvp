package com.example.myapplication.base.baseview;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.manager.LoginManager;
import com.example.myapplication.utils.Logout;
import com.example.myapplication.utils.PermissionHelper;
import com.example.myapplication.utils.StateUtils;
import com.example.myapplication.utils.SystemUtil;
import com.example.myapplication.view.MyTitleBar;
import com.example.myapplication.view.WaterMarkBg;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseActivity<P extends BasePresenter>  extends AppCompatActivity implements BaseView, CustomAdapt {

    public BaseActivity mActivity;
    Unbinder bind;
    public PermissionHelper permissionHelper;
    public P mPresenter;
//    Loading_view loading_view;
  public   boolean showWaterMask=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            initTextSize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        mActivity=this;
        permissionHelper = new PermissionHelper(this);

        //初始化状态栏
        initScreenState();
        //防止软键盘遮挡
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(getlayoutid());

        bind = ButterKnife.bind(this);
        mPresenter = getPresenter(this);
//        loading_view=new Loading_view(this,R.style.CustomProgressDialog);
//        WaterMarkUtil.showWatermarkView(this);

        initview();
        Logout.d("------SystemUtil-----------"+ SystemUtil.getDeviceBrand()+"  "+SystemUtil.getSystemModel());
        if (showWaterMask)
            initBG();
        initMyData();
        initMyListener();

    }

    private void initBG(){
        final ViewGroup rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        View framView = null;
        boolean ismate20=false;
        try {
            ismate20=SystemUtil.getSystemModel().equals("HMA-AL00");
        }catch (Exception e){
            ismate20=false;
        }
            framView = LayoutInflater.from(this).inflate(R.layout.layout_watermark, null);
            //可对水印布局进行初始化操作
            ImageView imageView=framView.findViewById(R.id.img_appbg);

            String wg="水印-";
            String wg2="三周年快乐";
            List<String> labels = new ArrayList<>();
            labels.add(wg2+ LoginManager.getInstance().getRealUserName());

            WaterMarkBg drawable=new WaterMarkBg(this,labels,45,18,ismate20);
            imageView.setImageDrawable(drawable);
            rootView.addView(framView);


    }

 /*   public void showLoading(){
        if (loading_view==null)
            loading_view=new Loading_view(this);
        if (!loading_view.isShowing()){
            loading_view.show();
        }
    }

    public void hideLoading(){
        if (loading_view==null)return;
        if (!loading_view.isShowing())return;
        loading_view.dismiss();
    }*/
    /**
     * 规定按照宽度适配
     * @return
     */
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    /**
     * 设置适配的宽度或者高度
     * @return
     */
    @Override
    public float getSizeInDp() {
        return 667;
    }

    public abstract void initMyData();

    public abstract P getPresenter(BaseView view);
    /**
     * 初始化状态栏
     */
    public void initScreenState() {
//        StateUtils.StatusBarLightMode(this,3);
        StateUtils.StatusBarLightMode(this);
        //设置状态栏沉浸颜色
//        StateUtils.setStatusBarColor(this, R.color.primgreen);
    }

    public abstract void initMyListener();

    public abstract int getlayoutid();

    public abstract void initview();

    public void initTextSize() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }


    /**
     * 设置头标题
     *
     * @param title
     * @return MyTitleBar
     */
    public MyTitleBar initTitleBar(String title) {
        MyTitleBar  titleBar = findViewById(R.id.titleBar);
        titleBar.setTitle(title);
        titleBar.setLeftImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInput();
                finish();
            }
        });
//        titleBar.setTitleColor("#222A37");
        titleBar.setTitleSize(18);

        return titleBar;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        if (mPresenter != null) {
            mPresenter.cancle();
            mPresenter = null;
        }
    }

    //     点击输入框以外的地方隐藏软键盘
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //   LoginManager.getInstance().resetAutoLogoutTime();
//     点击输入框以外的地方隐藏软键盘
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }
    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null) {
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        //设置为竖屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
//		MPTracker.onActivityResume(this);
    }


    public void toastShort(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void toastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
