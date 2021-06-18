package com.example.myapplication.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;


public class WaterMarkUtil {
    public static String mWaterMarkDesc;

    /**
     * 显示水印布局
     *
     * @param activity
     */
    public static boolean showWatermarkView(final Activity activity) {
        final ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        View framView = LayoutInflater.from(activity).inflate(R.layout.layout_watermark, null);
        //可对水印布局进行初始化操作
        rootView.addView(framView);
        return true;
    }

}
