package com.example.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;


import com.example.myapplication.MyApplication;

import java.math.BigDecimal;

/**
 * Created by liukang on 2018/5/23 09:15.
 * 屏幕相关工具类
 */

public class ScreenUtil {

    /**
     * 屏幕宽
     */
    public static int getScreenWidth(Context activity) {
        return getMetrics(activity).widthPixels;
    }

    /**
     * 屏幕高
     */
    public static int getScreenHeight(Activity activity) {
        return getMetrics(activity).heightPixels;
    }

    /**
     * 状态栏高度
     */
    public static int getStatusBarHeight() {
        return Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height", "dimen",
                        "android"));
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    @Deprecated
    public static int dip2px(Context context, float dpValue) {
        return dip2px(dpValue);
    }

    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕的尺寸大小
     *
     * @return
     */
    public static double getPingMuSize() {
        try {
            WindowManager wm = (WindowManager) MyApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(dm);
            } else {
                display.getMetrics(dm);
            }
            double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
            double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
            // 屏幕尺寸
            BigDecimal decimal = new BigDecimal(Math.sqrt(x + y));
            decimal = decimal.setScale(1, BigDecimal.ROUND_UP);
            double mScreenInches = decimal.doubleValue();
            return mScreenInches;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据手机尺寸设置 适配框架 对应的宽,这里默认是拿宽,之前是在AndroidManifest的<meta-data>里面配置的,发现平板和手机因为尺寸差别太大无法只设置一个宽.
     * Nexus 5x Api28--->当前手机尺寸为:5.3
     * 其它信息:DisplayMetrics{density=2.625, width=1080, height=1794, scaledDensity=2.625, xdpi=420.0, ydpi=420.0}
     * 375:667
     * <p>
     * Raindi ITAB-01 Api22--->当前手机尺寸为:7.5
     * 其它信息:DisplayMetrics{density=1.0, width=600, height=976, scaledDensity=1.0, xdpi=160.0, ydpi=160.0}
     * 482  820
     * <p>
     * KTE X20 Api26--->9.5
     * 其它信息:DisplayMetrics{density=2.0, width=1600, height=2464, scaledDensity=2.0, xdpi=320.0, ydpi=320.0}
     * <p>
     * 580  960
     *
     * @return 返回不同尺寸终端适应的宽，注意，你们自己需要匹配的平板的数值自己去尝试，这里只是参考。
     */
    public static int getAutoSizeWidth() {
        // 580是9.5寸的商务数据终端的适配值.
        int width = 580;
        try {
            double size = getPingMuSize();
            if (size < 7) {
                width = 375; // height = 667
            } else if (size >= 7 && size < 8) {
                width = 482; // height = 820
            } else {
                width = 580; // height = 960
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return width;
        }
    }

    /**
     * 获取当前屏幕的尺寸大小
     *
     * @return
     */
    public static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }


    /**
     * 去除状态栏的屏幕高度
     * @return
     */
    public static int getContentHeight(Context context){
        return getScreenHeight(context) - getStatusBarHeight();
    }

    /**
     * 获得屏幕宽度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
