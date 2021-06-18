package com.example.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.myapplication.base.common.http.HttpHelper;
import com.example.myapplication.logingmodule.Loginactivity;
import com.example.myapplication.manager.LoginManager;
import com.example.myapplication.utils.Logout;
import com.example.myapplication.utils.SPUtils;
import com.orhanobut.hawk.Hawk;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;

public class MyApplication  extends Application {

  private static MyApplication myApplication = null;

  public static MyApplication getApplication() {
    return myApplication;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    myApplication=this;
    HttpHelper.INSTANCE.init(this);
    LoginManager.getInstance().init(this);
    initHawk();
//        FontsOverrideUtil.init(this);
    initAutoSize();
    lifecycle();
  }

  long time=0;
  private boolean firstin=true;
  private int mFinalCount;
  private void lifecycle(){

    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

      }

      @Override
      public void onActivityStarted(@NonNull Activity activity) {
        mFinalCount++;
        //如果mFinalCount ==1，说明是从后台到前台
        Logout.e("onActivityStarted", mFinalCount +"");
        /*if (mFinalCount == 1){
          //说明从后台回到了前台

          if (!firstin&&(System.currentTimeMillis()-time)>1000*60*10){
            if (new PatternHelper(MyApplication.this).hasStorage()&& SPUtils.isLogin(MyApplication.this)){
              Intent intent=new Intent(MyApplication.this, PatternLockCheckActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intent.putExtra("from","inapp");
              startActivity(intent);
//                        PatternLockCheckActivity.startCheckLock(MyApplication.this);
            }
            if (!new PatternHelper(MyApplication.this).hasStorage()&& SPUtils.isLogin(MyApplication.this)){
              logout();
//                        PatternLockCheckActivity.startCheckLock(MyApplication.this);
            }
          }
//            if (!firstin&&(System.currentTimeMillis()-time)>4)

        }*/
        firstin=false;
      }

      @Override
      public void onActivityResumed(@NonNull Activity activity) {

      }

      @Override
      public void onActivityPaused(@NonNull Activity activity) {

      }

      @Override
      public void onActivityStopped(@NonNull Activity activity) {
        mFinalCount--;
        time= System.currentTimeMillis();
        //如果mFinalCount ==0，说明是前台到后台
        Logout.i("onActivityStopped", mFinalCount +"");
        if (mFinalCount == 0){
          //说明从前台回到了后台
        }

      }

      @Override
      public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

      }

      @Override
      public void onActivityDestroyed(@NonNull Activity activity) {

      }
    });
  }

  private void initAutoSize(){
    /**
     * 以下是 AndroidAutoSize 可以自定义的参数, {@link AutoSizeConfig} 的每个方法的注释都写的很详细
     * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
     */
    AutoSizeConfig.getInstance()
            .setBaseOnWidth(false)
            .setCustomFragment(true)
            .getUnitsManager()
            .setSupportDP(true)
            .setSupportSP(true)
            .setSupportSubunits(Subunits.MM)
    ;
  }


  public  void logout(){
    //finish all activity
    //start logo activityl
    SPUtils.logout(MyApplication.this);
//    new PatternHelper(MyApplication.this).clearStorage();
    Intent intent=new Intent(MyApplication.this, Loginactivity.class);
    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);

  }

  /**
   * 初始化Hawk,核心是用SP存储的
   */
  private void initHawk() {
    Hawk.init(this)
            .build();
  }
}
