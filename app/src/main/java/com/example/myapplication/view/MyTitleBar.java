package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.example.myapplication.R;
import com.example.myapplication.utils.CommonUtil;


/**
 * 自定义TitleBar
 * 默认显示左图标 ，标题，其余gone
 * 可以设置左边的图标，左边的文字，右边的图标，右边的文字，包括显示与隐藏
 * 可以设置出标题之外的其他控件的点击事件
 * 可以设置背景颜色
 */

public class MyTitleBar extends RelativeLayout {

    ImageView iv_left;
    TextView tv_left;
    public ImageView iv_right;
    public TextView tv_right;
    TextView tv_titleBar;

    public MyTitleBar(Context context) {
        super(context, null);
    }

    public MyTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.layout_titlebar, this, true);
        initView();
    }

    private void initView() {
        iv_left = findViewById(R.id.image_left);
        iv_right = findViewById(R.id.image_right);
        tv_left = findViewById(R.id.tv_left);
        tv_right = findViewById(R.id.tv_right);
        tv_titleBar = findViewById(R.id.tv_title);
    }


    public void setTitle(String title) {
        tv_titleBar.setText(title);
    }

    public void setTitleSize(float size) {
        tv_titleBar.setTextSize(size);
    }


    public void setTitleColor(String titleColor) {
        tv_titleBar.setTextColor(Color.parseColor(titleColor));
    }

    public void setOtherSize(float size) {
        tv_left.setTextSize(size);
        tv_right.setTextSize(size);
    }

    public void setLeftContent(String leftContent) {
        tv_left.setVisibility(VISIBLE);
        tv_left.setText(CommonUtil.getString(leftContent));
    }

    public void setRightContent(String rightContent) {
        tv_right.setVisibility(VISIBLE);
        tv_right.setText(CommonUtil.getString(rightContent));
    }

    public void setLeftTextColor(String leftColor) {
        tv_left.setTextColor(Color.parseColor(leftColor));
    }

    public void setRightTextColor(String rightColor) {
        tv_right.setTextColor(Color.parseColor(rightColor));
    }
    public void setRightTextSize(int size){
        tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    }
    /**
     * 设置右侧文字的字体颜色
     * @param colorid
     */
    public void setRightTextColor(@ColorRes int colorid){
        tv_right.setTextColor(getResources().getColor(colorid));
    }

    /**
     * 设置右侧文字的背景
     * @param drawableid
     */
    public void setRightTextBackGround(@DrawableRes int drawableid){
        tv_right.setBackgroundResource(drawableid);
    }
    public void setLeftTextListener(OnClickListener listener) {
        tv_left.setOnClickListener(listener);
    }

    public void setRightTextListener(OnClickListener listener) {
        tv_right.setOnClickListener(listener);
    }

    public void setLeftIcon(int resId) {
        iv_left.setVisibility(VISIBLE);
        iv_left.setImageResource(resId);
    }
    public void setLeftIconGone() {
        iv_left.setVisibility(GONE);
    }

    public void setRightIcon(int resId) {
        iv_right.setVisibility(VISIBLE);
        iv_right.setImageResource(resId);
    }

    public void setLeftImageListener(OnClickListener listener) {
        iv_left.setVisibility(VISIBLE);
        iv_left.setOnClickListener(listener);
    }

    public void setRightImageListener(OnClickListener listener) {
        iv_right.setVisibility(VISIBLE);
        iv_right.setOnClickListener(listener);
    }

    public void setBackgroundColor1(int color) {
        ((View) tv_titleBar.getParent()).setBackgroundColor(color);
    }

    public void showLeftMenu(boolean isShow) {
        tv_left.setVisibility(isShow ? VISIBLE : GONE);
        iv_left.setVisibility(isShow ? VISIBLE : GONE);
    }

    public void showRightMenu(boolean isShow) {
        tv_right.setVisibility(isShow ? VISIBLE : GONE);
        iv_right.setVisibility(isShow ? VISIBLE : GONE);
    }
    public void showOnlyRightContextMenu(boolean isShow){
        tv_right.setVisibility(isShow ? VISIBLE : GONE);
        iv_right.setVisibility(GONE);
    }
    public void showOnlyRightIconMenu(boolean isShow){
        iv_right.setVisibility(isShow ? VISIBLE : GONE);
        tv_right.setVisibility(GONE);
    }


}
