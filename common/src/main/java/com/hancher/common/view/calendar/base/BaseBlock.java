package com.hancher.common.view.calendar.base;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;

/**
 * Created by yujinzhao on 17/2/6.
 */

public abstract class BaseBlock {
    public float size;//方块尺寸
    public PointF startIndex;//整个日历面板起始坐标
    public Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public BaseBlock() {
    }

    public void draw(Canvas canvas,ArrayList<Dd1> datas,float size,PointF startIndex) {
        if(size == 0 || startIndex == null){
            return;
        }else{
            this.size = size;
            this.startIndex = startIndex;
            mPaint.setTextSize(size/2);
            mPaint.setColor(Color.BLACK);
            mPaint.setTextAlign(Paint.Align.CENTER);
        }
        for (int i = 0; i < datas.size(); i++) {
            float x = startIndex.x +size*(i%7);
            float y = startIndex.y +(i/7)*size;
            drawBlock(x,y,canvas,datas.get(i),i);
        }
    }

    /**
     * 写字
     * @param x
     * @param y
     */
    public abstract void drawBlock(float x, float y, Canvas canvas, Dd1 datas, int index);
    /**
     * 写文字
     * @param paint
     * @param canvas
     * @param str
     * @param x(字的中心x坐标)
     * @param rectTop
     * @param rectBottom
     */
    public void drawBlock(Paint paint, Canvas canvas, String str, float x, float rectTop, float rectBottom){
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) /2;
        canvas.drawText(str,x,baseline,paint);
    }
}
