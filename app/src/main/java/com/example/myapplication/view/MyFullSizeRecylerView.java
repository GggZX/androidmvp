package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.utils.Logout;


public class MyFullSizeRecylerView extends RecyclerView {

    public MyFullSizeRecylerView(Context context) {
        super(context);
    }

    public MyFullSizeRecylerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyFullSizeRecylerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Logout.i("touchevent","onFocusChanged"+gainFocus);
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);

    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }


/*    int mlastx;
    int mlasty;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x=(int)e.getX();
        int y=(int)e.getY();
        boolean intercept=false;

        Logout.i("touchevent","Math.abs(starty-(int) e.getY())========onInterceptTouchEvent======="+Math.abs((int) e.getY()));
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                Logout.i("touchevent","Math.abs(starty-(int) e.getY())=========ACTION_DOWN======"+Math.abs((int) e.getY()));
                intercept=false;
                y=(int) e.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int deltax=x-mlastx;
                int deltay=y-mlasty;
                Logout.i("touchevent","ACTION_MOVE  Math.abs(deltax)<Math.abs(deltay)="+(Math.abs(deltax)<Math.abs(deltay)));
                if (Math.abs(deltax)<Math.abs(deltay)){
                    intercept=true;
                }else intercept=false;
            *//*    if (Math.abs(y-(int) e.getY())>10){
                    intercept=true;
                }else intercept=false;*//*
                break;
            case MotionEvent.ACTION_UP:
                intercept=false;
                break;
            default:
                intercept=false;
                break;
        }
        mlasty=y;
        mlastx=x;
        Logout.i("touchevent","ACTION_MOVE intercept"+intercept);
        return intercept;
    }*/

    int mlastxx;
    int mlastyy;
    /*@Override
    public boolean dispatchTouchEvent(MotionEvent e) {
//        return super.dispatchTouchEvent(ev);
        int x=(int)e.getX();
        int y=(int)e.getY();
        boolean intercept=true;// false 返回给父类

        Logout.i("touchevent","Math.abs(starty-(int) e.getY())========onInterceptTouchEvent======="+Math.abs((int) e.getY()));
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                Logout.i("touchevent","ACTION_DOWN   Math.abs(starty-(int) e.getY())=========ACTION_DOWN======"+Math.abs((int) e.getY()));
                return super.dispatchTouchEvent(e);
            case MotionEvent.ACTION_MOVE:

                int deltax=x-mlastxx;
                int deltay=y-mlastyy;
                Logout.i("touchevent","ACTION_MOVE  Math.abs(deltax)<Math.abs(deltay)="+(Math.abs(deltax)<Math.abs(deltay)));
                Logout.i("touchevent","ACTION_MOVE  deltax="+deltax+"  deltay="+deltay);
                if (Math.abs(deltax)<Math.abs(deltay)){
                    if (Math.abs(deltax)<1000&&Math.abs(deltay)<1000){
                        return super.dispatchTouchEvent(e);
                    }else {
                        intercept=false;
                        return false;
                    }

                }else   return super.dispatchTouchEvent(e);

            case MotionEvent.ACTION_UP:
                return super.dispatchTouchEvent(e);

        }
        mlastyy=y;
        mlastxx=x;
        Logout.i("touchevent","ACTION_end intercept "+intercept);
        if (!intercept)return false;
        else
        return super.dispatchTouchEvent(e);
    }*/

    /*   @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return true;
    }*/
}
