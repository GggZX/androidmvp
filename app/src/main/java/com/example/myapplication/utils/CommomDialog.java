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

public class CommomDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;
    private View divier;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private int positiveColor = 0;
    private boolean cancelClickable;
    private boolean isShowCancle = true;

    public CommomDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommomDialog(Context context, String content) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
    }

    public CommomDialog(Context context, int themeResId, String content) {
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

    public CommomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected CommomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommomDialog setCancelTextUnClickable(boolean cancelClickable) {
        this.cancelClickable = cancelClickable;
        return this;
    }

    public CommomDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public CommomDialog setPositiveButtonColor(int color) {
        this.positiveColor = color;
        return this;
    }

    public CommomDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        contentTxt = (TextView) findViewById(R.id.content);
        titleTxt = (TextView) findViewById(R.id.title);
        submitTxt = (TextView) findViewById(R.id.submit);
        divier = findViewById(R.id.divider_bar);
        submitTxt.setOnClickListener(this);
        cancelTxt = (TextView) findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        contentTxt.setText(content);
        if (positiveColor != 0) {
            submitTxt.setTextColor(mContext.getResources().getColor(positiveColor));
        }
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        } else {
            titleTxt.setVisibility(View.GONE);
        }
        if (cancelClickable) {
            cancelTxt.setTextColor(mContext.getResources().getColor(R.color.post_detail_content));
            cancelTxt.setClickable(false);
            this.setCancelable(false);

//            contentTxt.setGravity(Gravity.LEFT);
//            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.gravity=Gravity.LEFT;
//            contentTxt.setLayoutParams(layoutParams);
        }
        if (isShowCancle) {
            cancelTxt.setVisibility(View.VISIBLE);
        } else {
            cancelTxt.setVisibility(View.GONE);
        }
    }

    public CommomDialog withoutCancle() {
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
