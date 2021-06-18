package com.example.myapplication.utils;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * Created by Administrator on 2018/12/14.
 */

public class LougoutDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private int positiveColor = 0;
    private boolean cancelClickable;
    private boolean isShowCancle = true;

    public LougoutDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public LougoutDialog(Context context, String content) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
    }

    public LougoutDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public boolean isShowing;

    @Override
    public boolean isShowing() {
        return isShowing;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShowing = false;
    }

    @Override
    public void show() {
        super.show();
        isShowing = true;
    }

    public LougoutDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected LougoutDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public LougoutDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public LougoutDialog setCancelTextUnClickable(boolean cancelClickable) {
        this.cancelClickable = cancelClickable;
        return this;
    }

    public LougoutDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public LougoutDialog setPositiveButtonColor(int color) {
        this.positiveColor = color;
        return this;
    }

    public LougoutDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logout);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        contentTxt = (TextView) findViewById(R.id.content);
        titleTxt = (TextView) findViewById(R.id.title);
        submitTxt = (TextView) findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);


        contentTxt.setText(content);
        if (positiveColor != 0) {
            submitTxt.setTextColor(mContext.getResources().getColor(positiveColor));
        }
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }



        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        } else {
            titleTxt.setVisibility(View.GONE);
        }

    }

    public LougoutDialog withoutCancle() {
        this.isShowCancle = false;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}

