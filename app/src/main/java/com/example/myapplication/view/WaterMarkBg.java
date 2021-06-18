package com.example.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created by fulushan on 18/4/5.
 */

public class WaterMarkBg extends Drawable {

    private Paint paint = new Paint();
    private List<String> labels;
    private Context context;
    private int degress;//角度
    private int fontSize;//字体大小 单位sp
    private String colorMate20="#bbbbbb";
    private String textcolor="#AEAEAE";
    private boolean isMate20=false;

    /**
     * 初始化构造
     * @param context 上下文
     * @param labels 水印文字列表 多行显示支持
     * @param degress 水印角度
     * @param fontSize 水印文字大小
     */
    public  WaterMarkBg(Context context, List<String> labels, int degress, int fontSize, boolean isMate20) {
        this.labels = labels;
        this.context = context;
        this.degress = degress;
        this.fontSize = fontSize;
        this.isMate20=isMate20;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {


        int width = getBounds().right;
        int height = getBounds().bottom;

//        canvas.drawColor(Color.parseColor("#40F3F5F9"));
//        if (isMate20)
//            paint.setColor(Color.parseColor(colorMate20));
//        else
            paint.setColor(Color.parseColor(textcolor));
//        paint.setColor(Color.parseColor("#AEAEAE"));
        paint.setAlpha(60);

        paint.setAntiAlias(true);
        paint.setTextSize(sp2px(context,fontSize));
        canvas.save();
        canvas.rotate(degress);
        float textWidth = paint.measureText(labels.get(0));
        int index = 0;
        for (int positionY = -height; positionY <= height*2; positionY += height / 20+50) {
            float fromX =0+ (index++ % 2) * textWidth;
            for (float positionX = fromX; positionX <( width*2); positionX += textWidth*2 ) {
                int spacing  = 0;//间距
                for(String label:labels){
                    canvas.drawText(label, positionX, positionY+spacing, paint);
                    spacing = spacing+50;
                }

            }
        }
        canvas.restore();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        paint.setAlpha(60);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }


    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
