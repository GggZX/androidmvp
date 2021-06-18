package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.myapplication.R;
import com.github.ihsg.patternlocker.CellBean;
import com.github.ihsg.patternlocker.IHitCellView;

import org.jetbrains.annotations.NotNull;

public class MPatterLockHitCellView implements IHitCellView {
    Paint innerCirclepaint;
    Paint outerCirclepaint;
    Paint paint;
    public MPatterLockHitCellView(Context context) {
        paint=new Paint();
        innerCirclepaint=new Paint();
        innerCirclepaint.setColor(context.getResources().getColor(R.color.mplp_inner_hitcolor));
        innerCirclepaint.setStyle(Paint.Style.FILL);
        innerCirclepaint.setAntiAlias(true);

        outerCirclepaint=new Paint();
        outerCirclepaint.setColor(context.getResources().getColor(R.color.mplp_outer_hitcolor));
        outerCirclepaint.setStyle(Paint.Style.FILL);
//        outerCirclepaint.setStrokeWidth((float) (0.2*mRadius));
        outerCirclepaint.setAntiAlias(true);

    }

    RectF rectF;
    @Override
    public void draw(@NotNull Canvas canvas, @NotNull CellBean cellBean, boolean b) {
        int count=canvas.save();
//        rectF=new RectF(cellBean.getX()-cellBean.getRadius(), cellBean.getY()-cellBean.getRadius(), cellBean.getX()+cellBean.getRadius(), cellBean.getY()+cellBean.getRadius());
//        rectF=new RectF(0,0,0,0);
        outerCirclepaint.setStrokeWidth((float) (0.2*cellBean.getRadius()));
//        canvas.drawArc(rectF,0,360,false,outerCirclepaint);
        canvas.drawCircle(cellBean.getX(), cellBean.getY(), cellBean.getRadius(), this.outerCirclepaint);
        canvas.drawCircle(cellBean.getX(), cellBean.getY(), (float) (cellBean.getRadius()*0.6), this.innerCirclepaint);
        canvas.restoreToCount(count);
    }
}
