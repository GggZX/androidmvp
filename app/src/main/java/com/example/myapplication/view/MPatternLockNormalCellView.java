package com.example.myapplication.view;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.ihsg.patternlocker.CellBean;
import com.github.ihsg.patternlocker.DefaultStyleDecorator;
import com.github.ihsg.patternlocker.INormalCellView;

import org.jetbrains.annotations.NotNull;

public class MPatternLockNormalCellView  implements INormalCellView {

/*    Paint innerCirclepaint;
    Paint outerCirclepaint;
    int mPPLinnerColor,mPPLiouterColor;
    int mRadius;

    private void init(Context context){
        innerCirclepaint=new Paint();
        innerCirclepaint.setColor(mPPLinnerColor);
        innerCirclepaint.setStyle(Paint.Style.FILL);
        innerCirclepaint.setAntiAlias(true);

        outerCirclepaint=new Paint();
        outerCirclepaint.setColor(mPPLiouterColor);
        outerCirclepaint.setStyle(Paint.Style.STROKE);
        outerCirclepaint.setStrokeWidth((float) (0.2*mRadius));
        outerCirclepaint.setAntiAlias(true);
    }

    public MPatternLockNormalCellView(Context context) {
        super(context);
        init(context);
    }

    public MPatternLockNormalCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MPatternLockNormalCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }*/
    DefaultStyleDecorator defaultStyleDecorator;
    Paint paint;
    public MPatternLockNormalCellView(DefaultStyleDecorator defaultStyleDecorator) {
        this.defaultStyleDecorator = defaultStyleDecorator;
        paint=new Paint();
    }


    @Override
    public void draw(@NotNull Canvas canvas, @NotNull CellBean cellBean) {

    }
}
